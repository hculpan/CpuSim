package org.culpan.computersim.chips

import org.culpan.computersim.exceptions.InvalidConnectionException
import org.culpan.computersim.exceptions.InvalidInputValueException

class Chip {
    class Wire {
        int fromIndex

        Chip toChip

        int toIndex

        Wire(int fromIndex, Chip toChip, int toIndex) {
            this.fromIndex = fromIndex
            this.toChip = toChip
            this.toIndex = toIndex
        }

        void outputValue(int fromIndex, InputValue inputValue) {
            if (this.fromIndex == fromIndex) {
                toChip.input(toIndex, inputValue)
            }
        }
    }

    String name

    protected InputValue [] inputValues

    protected InputValue [] outputValues

    protected final List<Wire> outputWires = new ArrayList<Wire>()

    private Chip outputChip = this

    private final static Map<String, Chip> chipMap = new HashMap<>()

    Chip() {
        initialize(0, 0)
    }

    Chip(int inputCount, int outputCount) {
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

    @Override
    Chip clone() {
        Chip result = this.class.newInstance()
        result.name = this.name
        result.initialize(this.inputCount(), this.outputCount())

        inputValues.eachWithIndex { InputValue entry, int i ->
            result.inputValues[i] = inputValues[i]
        }

        outputValues.eachWithIndex { InputValue entry, int i ->
            result.outputValues[i] = outputValues[i]
        }

        outputWires.eachWithIndex { Wire entry, int i ->
            Wire oWire = entry
            Chip newChip
            if (chipMap.containsKey(entry.toChip.toString())) {
                newChip = chipMap.get(entry.toChip.toString())
            } else {
                newChip = entry.toChip.clone()
                chipMap.put(entry.toChip.toString(), newChip)
            }
            result.addOutputWire(entry.fromIndex, newChip, entry.toIndex)
        }

        // Find correct output chip
        if (this == outputChip) {
            result.outputChip = result
        } else {
            result.outputChip = findChipType(result, this.outputChip.class)
        }

        result.resetAll()
        return result
    }

    Chip findChipType(Chip rootChip, Class outputChipClass) {
        if (rootChip.class == outputChipClass) {
            return rootChip
        } else {
            Chip result
            for (int i = 0; i < rootChip.outputWires.size(); i++) {
                result = findChipType(rootChip.outputWires.get(i).toChip, outputChipClass)
                if (result) {
                    break
                }
            }

            return result
        }
    }

    void addOutputWire(int fromOutput, Chip toChip, int toInput) {
        outputWires.add(new Wire(fromOutput, toChip, toInput))
    }

    void addOutputWire(Chip toChip) {
        addOutputWire(0, toChip, 0)
    }

    void addOutputWire(Chip toChip, int toInput) {
        addOutputWire(0, toChip, toInput)
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
        return outputValues.length
    }

    /**
     * Takes a single input value
     * @param idx
     */
    void input(int idx, InputValue value) {
        if (idx < 0 || idx >= inputCount())
            throw new InvalidConnectionException(idx)

        inputValues[idx] = value

        if (readyToProcess()) {
            process()
        }
    }

    /**
     * Sets a specific output to the On value
     * @param idx
     * @throws InvalidConnectionException
     */
    protected void setOutputOn(int idx) {
        if (idx < 0 || idx >= outputCount())
            throw new InvalidConnectionException(idx)

        outputValues[idx] = InputValue.on
        outputWires.each { it.outputValue(idx, InputValue.on) }
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
    protected void setOutputOff(int idx) {
        if (idx < 0 || idx >= outputCount()) throw new InvalidConnectionException(idx)

        outputValues[idx] = InputValue.off
        outputWires.each { it.outputValue(idx, InputValue.off) }
    }

    InputValue getOutput(int idx) {
        if (idx < 0 || idx >= outputCount()) throw new InvalidConnectionException(idx)

        getOutputChip().outputValues[idx]
    }

    InputValue getInput(int idx) {
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
     * Indicates that all inputs have been received and it is ready to process
     * @return
     */
    boolean readyToProcess() {
        return inputValues.find { it == InputValue.notset } == null
    }

    protected void process() {

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

        outputWires.each { it.toChip.resetAll() }
    }

    /**
     * Sets the input from a binary string (e.g., "0110"
     * starting at input 0.
     * Will throw an exception if the string of values
     * would exceed the number of inputs.  If the string
     * is shorter than the number of inputs, then the
     * extra inputs will not be set.
     *
     * @param binaryValues
     */
    void setInputs(String binaryValues) {
        setInputs(0, binaryValues)
    }

    /**
     * Sets the input from a binary string (e.g., "0110")
     *  starting at the given startingIndex.
     * Will throw an exception if the string of values
     * and the startingIndex would exceed the number of inputs
     *
     * @param startingIndex
     * @param binaryValues
     */
    void setInputs(int startingIndex, String binaryValues) {
        if (binaryValues.length() + startingIndex > inputCount()) {
            throw new InvalidInputValueException("Invalid input value count: Expecting ${inputCount()}, found ${binaryValues.length()}")
        }

        binaryValues.toCharArray().eachWithIndex { it, idx -> owner.input(idx + startingIndex, InputValue.fromBinary(it)) }
    }

    /**
     * Returns all output values as a binary string, e.g., "011010"
     *
     * @return
     */
    String getOutputString() {
        String result = ""

        outputChip.outputValues.eachWithIndex { it, idx -> result += Integer.toString(it.binary) }

        return result
    }

    Chip getOutputChip() {
        return outputChip
    }

    void setOutputChip(Chip outputChip) {
        this.outputChip = outputChip
    }

    /**
     * This method is a way
     * @param name
     * @return
     */
    static Chip getChip(String name) {
        return ChipStash.getChip(name)
    }

    List<Wire> getOutputWires() {
        return outputWires
    }
}