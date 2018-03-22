package org.culpan.computersim.chips

import org.junit.Test

class OrTest extends GroovyTestCase {
    @Test
    void testOr01() {
        Chip chip = new Or()

        assertFalse(chip.readyToProcess())

        chip.input(0, InputValue.off)
        assertFalse(chip.readyToProcess())

        chip.input(1, InputValue.on)

        assert chip.readyToProcess()
        assert chip.getOutput(0) == InputValue.on
    }

    @Test
    void testOr00() {
        Chip chip = new Or()

        assertFalse(chip.readyToProcess())

        chip.input(0, InputValue.off)
        assertFalse(chip.readyToProcess())

        chip.input(1, InputValue.off)

        assert chip.readyToProcess()
        assert chip.getOutput(0) == InputValue.off
    }

    @Test
    void testOr10() {
        Chip chip = new Or()

        assertFalse(chip.readyToProcess())

        chip.input(0, InputValue.on)
        assertFalse(chip.readyToProcess())

        chip.input(1, InputValue.off)

        assert chip.readyToProcess()
        assert chip.getOutput(0) == InputValue.on
    }

    @Test
    void testOr11() {
        Chip chip = new Or()

        assertFalse(chip.readyToProcess())

        chip.input(0, InputValue.on)
        assertFalse(chip.readyToProcess())

        chip.input(1, InputValue.on)

        assert chip.readyToProcess()
        assert chip.getOutput(0) == InputValue.on
    }
}
