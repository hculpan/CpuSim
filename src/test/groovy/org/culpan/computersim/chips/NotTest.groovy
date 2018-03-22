package org.culpan.computersim.chips

import org.junit.Test

class NotTest extends GroovyTestCase {
    @Test
    void testNot0() {
        Chip not = new Not()

        assertFalse(not.readyToProcess())

        not.input(0, InputValue.off)
        assert not.readyToProcess()

        assert not.getOutput(0) == InputValue.on
    }

    @Test
    void testNot1() {
        Chip not = new Not()

        assertFalse(not.readyToProcess())

        not.input(0, InputValue.on)
        assert not.readyToProcess()

        assert not.getOutput(0) == InputValue.off
    }

}
