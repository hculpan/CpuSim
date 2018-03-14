package org.culpan.computersim.chips

import org.culpan.computersim.utils.InvalidConnectionException

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

        outputs.each { it.setValueOn() }
    }

    /**
     * Sets a specific output to the Off value
     * @param idx
     * @throws InvalidConnectionException
     */
    protected void setOutputOff(int idx) throws InvalidConnectionException {
        if (idx < 0 || idx >= outputCount()) throw new InvalidConnectionException(idx)

        outputs.each { it.setValueOff() }
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
}