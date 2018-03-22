package org.culpan.computersim.chips

enum InputValue {
    notset((byte)-1), on((byte)1), off((byte)0)

    private final byte binary

    InputValue(byte binary) {
        this.binary = binary
    }

    byte getBinary() { return binary }

    static InputValue fromBinary(String s) {
        if (s.equals("0")) {
            return off
        } else if (s.equals("1")) {
            return on
        } else {
            return notset
        }
    }

    static InputValue fromBinary(char s) {
        if (s == '0') {
            return off
        } else if (s == '1') {
            return on
        } else {
            return notset
        }
    }
}

