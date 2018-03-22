package org.culpan.computersim.chips

import org.culpan.computersim.exceptions.InvalidConnectionException
import org.culpan.computersim.exceptions.InvalidInputValueException

class OldChip {
    protected String name

    protected InputValue [] inputValues

    protected InputValue [] outputValues

    protected final List<Tuple<Integer, Chip, Integer>> outputWires = new ArrayList<>()

    private Chip outputChip = this

    private final static Map<Integer, Chip> chipMap = new HashMap<>()

    OldChip() {
        initialize(0, 0)
    }

    OldChip(int inputCount, int outputCount) {
        initialize(inputCount, outputCount)
    }

    void initialize(int inputCount, int outputCount) {
        inputValues = new InputValue[inputCount]
        for (int i = 0; i < inputCount; i++) {
            inputValues[i] = InputValue.notset
        }

        outputValues = new InputValue[outputCount]
        for (int i = 0; i < outputValues.length; i++) {
            outputValues[i] = InputValue.notset
        }
    }

    void printOutputs() {
        printOutputs("")
    }

    void addOutputWire(int fromOutput, Chip toChip, int toInput) {
        outputWires.add(new Tuple(fromOutput, toChip, toInput))
    }

    void addOutputWire(Chip toChip) {
        addOutputWire(0, toChip, 0)
    }

    void addOutputWire(Chip toChip, int toInput) {
        addOutputWire(0, toChip, toInput)
    }

    void printOutputs(String indent) {
        print indent + "${name} (${this.class.getName().substring(this.class.getName().lastIndexOf(".") + 1)}@${System.identityHashCode(this)}): "
        for (int i = 0; i < outputValues.length; i++) {
            print "${outputValues[i].binary} "
        }
        println ""

/*        for (int i = 0; i < outputWires.size(); i++) {
            for (int j = 0; j < outputWires.outputs.size(); j++) {
                outputs[i].outputs.get(j).first.printOutputs("  " + indent)
            }
        }*/
    }

    @Override
    Chip clone() {
        Chip result = this.class.newInstance()
        result.name = this.name
        result.initialize(this.inputCount(), this.outputCount())

        inputValues.eachWithIndex { InputValue entry, int i ->
            result.inputValues[i] = inputValues[i]
        }

        outputWires.eachWithIndex { Tuple entry, int i ->
            result.outputValues[i] = outputValues[i]
            result.outputWires.add(new Tuple(entry.first, entry.second.clone(), entry.third))
        }

        // Find correct output chip
        if (this == outputChip) {
            result.outputChip = result
        } else {
            result.outputChip = findChipType(result, this.outputChip.class)
        }

        result.resetAll()
        result
    }

    Chip findChipType(Chip rootChip, Class outputChipClass) {
        if (rootChip.class == outputChipClass) {
            return rootChip
        } else {
            Chip result
            for (int i = 0; i < rootChip.outputWires.size(); i++) {
                result = findChipType(rootChip.outputWires.get(i).second, outputChipClass)
                if (result) {
                    break
                }
            }

            return result
        }
    }

    /**
     * This resets all the incoming values to notset for current chip only
     */
    void reset() {
        inputValues.each { it = InputValue.notset }
        outputValues.each { it = InputValue.notset }
    }

    /**
     * This resets this chip and all chips connected on outputs
     */
    void resetAll() {
        reset()

        outputWires.each { it.second.resetAll() }
    }

    /**
     * Returns the number of inputs
     * @return
     */
    int inputCount() {
        return inputValues.length
    }

    /**
     * Returns the number of outputs
     * @return
     */
    int outputCount() {
        return outputWires.size()
    }

    Chip getOutputChip() {
        return outputChip
    }

    void setOutputChip(Chip outputChip) {
        this.outputChip = outputChip
    }

    /**
     * Takes a single input value
     * @param idx
     */
    void input(int idx, InputValue value) throws InvalidConnectionException {
        if (idx < 0 || idx >= inputCount()) throw new InvalidConnectionException(idx)

        inputValues[idx] = value

        if (readyToProcess()) {
            process()
        }
    }

    void setInputs(String binaryValues) {
        setInputs(0, binaryValues)
    }

    void setInputs(int startingIndex, String binaryValues) {
        if (binaryValues.length() + startingIndex > inputCount()) {
            throw new InvalidInputValueException("Invalid input value count: Expecting ${inputCount()}, found ${binaryValues.length()}")
        }

        binaryValues.toCharArray().eachWithIndex { it, idx -> owner.input(idx + startingIndex, InputValue.fromBinary(it)) }
    }

    String getOutputString() {
        String result = ""

        outputChip.outputValues.eachWithIndex { it, idx -> result += Integer.toString(it.binary) }

        return result
    }

    Chip [] asArray(int count) {
        Chip []result = new Chip[count]
        for (int i = 0; i < count; i++) {
            result[i] = this.clone()
        }
        return result
    }

    InputValue getInput(int idx) throws InvalidConnectionException {
        if (idx < 0 || idx >= inputCount()) throw new InvalidConnectionException(idx)

        inputValues[idx]
    }

    void setInputOn(int idx) throws InvalidConnectionException {
        input(idx, InputValue.on)
    }

    void setInputOff(int idx) throws InvalidConnectionException {
        input(idx, InputValue.off)
    }

    /**
     * Sets a specific output to the On value
     * @param idx
     * @throws InvalidConnectionException
     */
    protected void setOutputOn(int idx) {
        if (idx < 0 || idx >= outputCount()) throw new InvalidConnectionException(idx)

        outputValues[idx] = InputValue.on
        outputWires.findAll { it.first == idx }.each { it.second.setInputOn(it.third)}
    }

    protected void output(int idx, InputValue value) {
        switch (value) {
            case InputValue.on:
                setOutputOn(idx)
                break
            case InputValue.off:
                setOutputOff(idx)
                break
            default:
                throw new InvalidInputValueException(value)
        }
    }

    /**
     * Sets a specific output to the Off value
     * @param idx
     * @throws InvalidConnectionException
     */
    protected void setOutputOff(int idx) throws InvalidConnectionException {
        if (idx < 0 || idx >= outputCount()) throw new InvalidConnectionException(idx)

        outputValues[idx] = InputValue.off
        outputWires.findAll { it.first == idx }.each { it.second.setInputOff(it.third)}
    }

    InputValue getOutput(int idx) {
        if (idx < 0 || idx >= outputCount()) throw new InvalidConnectionException(idx)

        getOutputChip().outputValues[idx]
    }

    /**
     * This is the internal logic of the chip that sets the outputValues based
     * on the inputValues.  This will only be called if readyToProcess is true.
     */
    protected void process() {

    }

    /**
     * Indicates that all inputs have been received and it is ready to process
     * @return
     */
    boolean readyToProcess() {
        return inputValues.find { it == InputValue.notset } == null
    }

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    /**
     * This method is a way
     * @param name
     * @return
     */
    static Chip getChip(String name) {
        return ChipStash.getChip(name)
    }

    static Chip getExternalInputChip(int connections) {
        return new ExternalInput(connections)
    }

}
