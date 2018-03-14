package org.culpan.computersim.chips

class TestWire extends Wire {
    InputValue value = InputValue.notset

    TestWire() {
        super()
    }

    @Override
    void setValueOn() {
        value = InputValue.on
    }

    @Override
    void setValueOff() {
        value = InputValue.off
    }
}

