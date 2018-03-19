package org.culpan.computersim.chips

import org.culpan.computersim.exceptions.InvalidConnectionException

class ExternalOutput extends Chip {
    private InputValue [] outputValues

    ExternalOutput() {
        super(1, 1)
    }

    ExternalOutput(int inputCount) {
        super(inputCount, 0)
    }

    @Override
    void initialize(int inputCount, int outputCount) {
        super.initialize(inputCount, outputCount)

        outputValues = new InputValue[outputCount]
        for (int i = 0; i < outputCount; i++) {
            outputValues[i] = InputValue.notset
        }
    }

    void setOutputCount(int outputCount) {
        initialize(outputCount, outputCount)
    }

    @Override
    protected void process() {
        for (int i = 0; i < outputCount(); i++) {
            output(i, getInput(i))
        }
    }
}
