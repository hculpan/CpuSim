package org.culpan.computersim.chips

class Nand extends Chip {
    Nand(Wire output) {
        super(2, output)
    }

    Nand() {
        super(2, 1)
    }

    @Override
    protected void process() {
        if (getInput(0) == InputValue.on && getInput(1) == InputValue.on) {
            setOutputOff(0)
        } else {
            setOutputOn(0)
        }
    }
}
