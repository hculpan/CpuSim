package org.culpan.computersim.chips

class Or extends Chip {
    Or() {
        super(2, 1)
    }

    @Override
    protected void process() {
        if (getInput(0) == InputValue.on || getInput(1) == InputValue.on) {
            setOutputOn(0)
        } else {
            setOutputOff(0)
        }
    }
}
