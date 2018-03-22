package org.culpan.computersim.chips

class ExternalInput extends Chip {
    ExternalInput() {
        super(1, 1)
    }

    ExternalInput(int inputCount) {
        super(inputCount, inputCount)
    }

    void setInputCount(int inputCount) {
        initialize(inputCount, inputCount)
    }

    @Override
    protected void process() {
        for (int i = 0; i < inputCount(); i++) {
            if (getInput(i) == InputValue.on) {
                setOutputOn(i)
            } else {
                setOutputOff(i)
            }
        }
    }
}
