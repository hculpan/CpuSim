package org.culpan.computersim.chips

enum InputValue {
    notset((byte)-1), on((byte)1), off((byte)0)

    private final byte binary

    InputValue(byte binary) {
        this.binary = binary
    }

    byte getBinary() { return binary }
}

