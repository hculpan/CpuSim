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

    @Override
    protected void process() {
        for (int i = 0; i < values.length; i++) {
            if (values[i] == InputValue.on) {
                setOutputOn(i)
            } else {
                setOutputOff(i)
            }
        }
    }
}
