package org.culpan.computersim.chips

enum InputValue {
    notset(-1), on(1), off(0);

    InputValue(byte binary) {
        this.binary = binary
    }

    private final byte binary

    byte getBinary() { return binary }

}

