package org.culpan.computersim.chips

import org.culpan.computersim.utils.InvalidConnectionException

class Nand extends Chip {
    Nand(Wire output) {
        super(2, output)
    }

    Nand() {
        super(2, 1)
    }

    @Override
    protected void process() {
        if (values[0] == InputValue.on && values[1] == InputValue.on) {
            setOutputOff(0)
        } else {
            setOutputOn(0)
        }
    }
}
