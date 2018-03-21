package org.culpan.computersim.chips

class ExternalInput extends Chip {
    ExternalInput() {
        super(1, 1)
    }

    ExternalInput(int inputCount) {
        super(inputCount, inputCount)
    }

    ExternalInput(Wire output) {
        super(1, output)

        if (outputs.length != 1) {
            throw new RuntimeException("Invalid number of outputs: ${outputs.length}")
        }
    }

    void setInputCount(int inputCount) {
        initialize(inputCount, inputCount)
    }

    @Override
    protected void process() {
        print "${name}: "

        for (int i = 0; i < inputCount(); i++) {
            print "${outputValues[i].binary} "
            if (getInput(i) == InputValue.on) {
                setOutputOn(i)
            } else {
                setOutputOff(i)
            }
        }
    }
}
