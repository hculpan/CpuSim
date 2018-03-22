package org.culpan.computersim.chips

class Not extends Chip {
    Not() {
        super(1, 1)
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
