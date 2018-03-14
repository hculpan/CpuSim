package org.culpan.computersim.chips

class Or extends Chip {
    Or(Wire output) {
        super(2, output)
    }

    @Override
    protected void process() {
        if (values[0] == InputValue.on || values[1] == InputValue.on) {
            setOutputOn(0)
        } else {
            setOutputOff(0)
        }
    }
}
