package org.culpan.computersim.chips

import org.junit.Test

class OrTest extends GroovyTestCase {
    @Test
    void testOr01() {
        TestWire testWire = new TestWire()
        Chip chip = new Or(testWire)

        assertFalse(chip.readyToProcess())

        chip.input(0, InputValue.off)
        assertFalse(chip.readyToProcess())

        chip.input(1, InputValue.on)

        assert chip.readyToProcess()
        assert testWire.value == InputValue.on
    }

    @Test
    void testOr00() {
        TestWire testWire = new TestWire()
        Chip chip = new Or(testWire)

        assertFalse(chip.readyToProcess())

        chip.input(0, InputValue.off)
        assertFalse(chip.readyToProcess())

        chip.input(1, InputValue.off)

        assert chip.readyToProcess()
        assert testWire.value == InputValue.off
    }

    @Test
    void testOr10() {
        TestWire testWire = new TestWire()
        Chip chip = new Or(testWire)

        assertFalse(chip.readyToProcess())

        chip.input(0, InputValue.on)
        assertFalse(chip.readyToProcess())

        chip.input(1, InputValue.off)

        assert chip.readyToProcess()
        assert testWire.value == InputValue.on
    }

    @Test
    void testOr11() {
        TestWire testWire = new TestWire()
        Chip chip = new Or(testWire)

        assertFalse(chip.readyToProcess())

        chip.input(0, InputValue.on)
        assertFalse(chip.readyToProcess())

        chip.input(1, InputValue.on)

        assert chip.readyToProcess()
        assert testWire.value == InputValue.on
    }
}
