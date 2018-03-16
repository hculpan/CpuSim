package org.culpan.computersim.chips

class DummyChip extends Chip {
    InputValue value = InputValue.notset

    DummyChip() {
        super(1)
    }

    void setOutputCount(int outputCount) {
        initialize(outputCount, outputCount)
    }

    @Override
    protected void process() {
        value = values[0]
    }
}
