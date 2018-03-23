package org.culpan.computersim.chips

import org.junit.Test

class NotTest extends GroovyTestCase {
    @Test
    void testNot0() {
        Chip not = new Not()

        assertFalse(not.readyToProcess())

        not.setInputOff(0)
        assert not.readyToProcess()

        assert not.getOutput(0) == InputValue.on
    }

    @Test
    void testNot1() {
        Chip not = new Not()

        assertFalse(not.readyToProcess())

        not.setInputOn(0)
        assert not.readyToProcess()

        assert not.getOutput(0) == InputValue.off
    }

    @Test
    void testNot1_WithExternal() {
        Chip not = new Not()
        Chip externalOutput = new ExternalOutput()
        not.addOutputWire(externalOutput)

        assertFalse(not.readyToProcess())

        not.setInputOn(0)
        assert not.readyToProcess()

        assert externalOutput.getOutput(0) == InputValue.off
    }

}
