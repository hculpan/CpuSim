package org.culpan.computersim.chips

class Not extends Chip {
    Not() {
        super(1, 1)
    }

    Not(Wire output) {
        super(1, output)

        if (outputs.length != 1) {
          throw new RuntimeException("Invalid number of outputs: ${outputs.length}")
        }
    }

    @Override
    protected void process() {
        if (values[0] == InputValue.on) {
            outputs[0].setValueOff()
        } else {
            outputs[0].setValueOn()
        }
    }
}
