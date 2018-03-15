package org.culpan.computersim.chips

import org.culpan.computersim.exceptions.InvalidConnectionException
import org.culpan.computersim.exceptions.MissingChipDefinitionException

enum InputValue { notset, on, off }

abstract class Chip {
    protected InputValue [] values

    protected Wire []outputs

    Chip(int inputCount, int outputCount) {
        values = new InputValue[inputCount]
        for (int i = 0; i < inputCount; i++) {
            values[i] = InputValue.notset
        }

        outputs = new Wire[outputCount]
    }

    Chip(int inputCount, Wire ... outputs) {
        values = new InputValue[inputCount]
        for (int i = 0; i < inputCount; i++) {
            values[i] = InputValue.notset
        }

        this.outputs = outputs
    }

    void setOutputWire(int idx, Wire wire) throws InvalidConnectionException {
        if (idx < 0 || idx >= outputCount()) throw new InvalidConnectionException(idx)

        outputs[idx] = wire
    }

    /**
     * This resets all the incoming values to notset for current chip only
     */
    void reset() {
        values.each { it = InputValue.notset }
    }

    /**
     * This resets this chip and all chips connected on outputs
     */
    void resetAll() {
        reset();

        outputs.each { it.outputs.each { it.first.resetAll() } }
    }

    /**
     * Returns the number of inputs
     * @return
     */
    int inputCount() {
        return values.length
    }

    /**
     * Returns the number of outputs
     * @return
     */
    int outputCount() {
        return outputs.length
    }

    /**
     * Takes a single input value
     * @param idx
     */
    void input(int idx, InputValue value) throws InvalidConnectionException {
        if (idx < 0 || idx >= inputCount()) throw new InvalidConnectionException(idx)

        values[idx] = value

        if (readyToProcess()) {
            process()
        }
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
    protected void setOutputOn(int idx) throws InvalidConnectionException {
        if (idx < 0 || idx >= outputCount()) throw new InvalidConnectionException(idx)

        outputs[idx].setValueOn()
    }

    /**
     * Sets a specific output to the Off value
     * @param idx
     * @throws InvalidConnectionException
     */
    protected void setOutputOff(int idx) throws InvalidConnectionException {
        if (idx < 0 || idx >= outputCount()) throw new InvalidConnectionException(idx)

        outputs[idx].setValueOff()
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
        return values.find { it == InputValue.notset } == null
    }

    /**
     * This method is a way
     * @param name
     * @return
     */
    static Chip getChip(String name) {
        Chip result = null
        switch (name) {
            case "And":
                result = new And()
                break
            case "Or":
                result = new Or()
                break
            case "Not":
                result = new Not()
                break
            case "DummyChip":
                result = new DummyChip()
                break
            case "ExternalOutput":
                result = new ExternalOutput()
                break
            default:
                throw new MissingChipDefinitionException(name)
        }
        return result
    }

    static Chip getExternalInputChip(int connections) {
        return new ExternalInput(connections)
    }
}