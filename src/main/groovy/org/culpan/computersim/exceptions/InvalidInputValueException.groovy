package org.culpan.computersim.exceptions

import org.culpan.computersim.chips.InputValue

class InvalidInputValueException extends Exception {
    InvalidInputValueException(InputValue inputValue) {
        super("Cannot set the state at this time: ${inputValue}")
    }
}
