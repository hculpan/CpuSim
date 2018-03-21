package org.culpan.computersim.chips

import org.culpan.computersim.exceptions.InvalidConnectionException
import org.culpan.computersim.exceptions.InvalidInputValueException

abstract class Chip {
    protected String name

    protected InputValue [] inputValues

    protected InputValue [] outputValues

    protected Wire []outputs

    private Chip outputChip = this

    private final static Map<Integer, Chip> chipMap = new HashMap<>()

    Chip(int inputCount, int outputCount) {
        initialize(inputCount, outputCount)
    }

    Chip(int inputCount, Wire ... outputs) {
        initialize(inputCount, outputs.length)
        for (int i = 0; i < outputs.length; i++) {
            setOutputWire(i, outputs[i])
        }
    }

    void initialize(int inputCount, int outputCount) {
        inputValues = new InputValue[inputCount]
        for (int i = 0; i < inputCount; i++) {
            inputValues[i] = InputValue.notset
        }

        outputValues = new InputValue[outputCount]
        outputs = new Wire[outputCount]
        for (int i = 0; i < outputs.length; i++) {
            outputValues[i] = InputValue.notset
            outputs[i] = new Wire()
        }
    }

    void printOutputs() {
        printOutputs("")
    }

    void printOutputs(String indent) {
        print indent + "${name} (${this.class.getName().substring(this.class.getName().lastIndexOf(".") + 1)}@${System.identityHashCode(this)}): "
        for (int i = 0; i < outputValues.length; i++) {
            print "${outputValues[i].binary} "
        }
        println ""

        for (int i = 0; i < outputs.size(); i++) {
            for (int j = 0; j < outputs[i].outputs.size(); j++) {
                outputs[i].outputs.get(j).first.printOutputs("  " + indent)
            }
        }
    }

    @Override
    Chip clone() {
        Chip result = this.class.newInstance()
        result.name = this.name
        result.initialize(this.inputCount(), this.outputCount())

        for (int i = 0; i < inputCount(); i++) {
            result.inputValues[i] = inputValues[i]
        }

        for (int i = 0; i < outputs.length; i++) {
            result.outputValues[i] = outputValues[i]
            result.outputs[i] = outputs[i].clone(result, i, chipMap)
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
            for (int i = 0; i < rootChip.outputs.length; i++) {
                for (int j = 0; j < rootChip.outputs[i].outputs.size(); j++) {
                    result = findChipType(rootChip.outputs[i].outputs.get(j).first, outputChipClass)
                    if (result) {
                        break
                    }
                }
            }

            return result
        }
    }

    void setOutputWire(int idx, Wire wire) throws InvalidConnectionException {
        if (idx < 0 || idx >= outputCount()) throw new InvalidConnectionException(idx)

        outputs[idx] = wire
    }

    Wire getOutputWire(int idx) throws InvalidConnectionException {
        if (idx < 0 || idx >= outputCount()) throw new InvalidConnectionException(idx)

        return outputs[idx]
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

        outputs.each { it.outputs.each { it.first.resetAll() } }
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
        return outputs.length
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
        outputs[idx].setValueOn()
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
        outputs[idx].setValueOff()
    }

    InputValue getOutput(int idx) {
        if (idx < 0 || idx >= outputCount()) throw new InvalidConnectionException(idx)

        getOutputChip().outputValues[idx]
    }

    /**
     * This is the internal logic of the chip that sets the outputValues based
     * on the inputValues.  This will only be called if readyToProcess is true.
     */
    abstract protected void process()

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