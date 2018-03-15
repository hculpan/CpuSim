package org.culpan.computersim.chips

class ExternalOutput extends Chip {
    ExternalOutput() {
        super(1, 1)
    }

    ExternalOutput(int inputCount) {
        super(inputCount, 0)
    }

    @Override
    protected void process() {
        values.each { println "Result: ${it}" }
    }
}
