package org.culpan.computersim.chips

class Not extends Chip {
    Not() {
        super(1, 1)
    }

    Not(Wire output) {
        super(1, output)

        if (outputCount() != 1) {
          throw new RuntimeException("Invalid number of outputs: ${outputs.length}")
        }
    }

    @Override
    protected void process() {
        if (getInput(0) == InputValue.on) {
            setOutputOff(0)
        } else {
            setOutputOn(0)
        }
    }
}
