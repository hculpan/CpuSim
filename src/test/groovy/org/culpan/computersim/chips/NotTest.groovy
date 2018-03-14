package org.culpan.computersim.chips

import org.junit.Test

class NotTest extends GroovyTestCase {
    @Test
    void testNot0() {
        TestWire testWire = new TestWire()
        Chip not = new Not(testWire)

        assertFalse(not.readyToProcess())

        not.input(0, InputValue.off)
        assert not.readyToProcess()

        assert testWire.value == InputValue.on
    }

    @Test
    void testNot1() {
        TestWire testWire = new TestWire()
        Chip not = new Not(testWire)

        assertFalse(not.readyToProcess())

        not.input(0, InputValue.on)
        assert not.readyToProcess()

        assert testWire.value == InputValue.off
    }

}
