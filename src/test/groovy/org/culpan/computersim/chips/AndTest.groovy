package org.culpan.computersim.chips

import org.junit.Test

class AndTest extends GroovyTestCase {
    @Test
    void testAnd01() {
        TestWire testWire = new TestWire()
        Chip chip = new And(testWire)

        assertFalse(chip.readyToProcess())

        chip.input(0, InputValue.off)
        assertFalse(chip.readyToProcess())

        chip.input(1, InputValue.on)

        assert chip.readyToProcess()
        assert testWire.value == InputValue.off
    }

    @Test
    void testAnd00() {
        TestWire testWire = new TestWire()
        Chip chip = new And(testWire)

        assertFalse(chip.readyToProcess())

        chip.input(0, InputValue.off)
        assertFalse(chip.readyToProcess())

        chip.input(1, InputValue.off)

        assert chip.readyToProcess()
        assert testWire.value == InputValue.off
    }

    @Test
    void testAnd10() {
        TestWire testWire = new TestWire()
        Chip chip = new And(testWire)

        assertFalse(chip.readyToProcess())

        chip.input(0, InputValue.on)
        assertFalse(chip.readyToProcess())

        chip.input(1, InputValue.off)

        assert chip.readyToProcess()
        assert testWire.value == InputValue.off
    }

    @Test
    void testAnd11() {
        TestWire testWire = new TestWire()
        Chip chip = new And(testWire)

        assertFalse(chip.readyToProcess())

        chip.input(0, InputValue.on)
        assertFalse(chip.readyToProcess())

        chip.input(1, InputValue.on)

        assert chip.readyToProcess()
        assert testWire.value == InputValue.on
    }
}
