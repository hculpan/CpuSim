package org.culpan.computersim.chips

import org.culpan.computersim.exceptions.InvalidConnectionException

class ExternalOutput extends Chip {
    ExternalOutput() {
        super(1, 1)
    }

    ExternalOutput(int inputCount) {
        super(inputCount, 0)
    }

    void setOutputCount(int outputCount) {
        initialize(outputCount, outputCount)
    }

    @Override
    protected void process() {
        print "${name} "
        for (int i = 0; i < outputCount(); i++) {
            output(i, getInput(i))
            print "${outputValues[i].binary} "
        }
    }
}
