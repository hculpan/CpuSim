package org.culpan.computersim.chips

import org.junit.Test

class AndTest extends GroovyTestCase {
    @Test
    void testAnd01() {
        Chip chip = new And()

        assertFalse(chip.readyToProcess())

        chip.input(0, InputValue.off)
        assertFalse(chip.readyToProcess())

        chip.input(1, InputValue.on)

        assert chip.readyToProcess()
        assert chip.getOutput(0) == InputValue.off
    }

    @Test
    void testAnd00() {
        Chip chip = new And()

        assertFalse(chip.readyToProcess())

        chip.input(0, InputValue.off)
        assertFalse(chip.readyToProcess())

        chip.input(1, InputValue.off)

        assert chip.readyToProcess()
        assert chip.getOutput(0) == InputValue.off
    }

    @Test
    void testAnd10() {
        Chip chip = new And()

        assertFalse(chip.readyToProcess())

        chip.input(0, InputValue.on)
        assertFalse(chip.readyToProcess())

        chip.input(1, InputValue.off)

        assert chip.readyToProcess()
        assert chip.getOutput(0) == InputValue.off
    }

    @Test
    void testAnd11() {
        Chip chip = new And()

        assertFalse(chip.readyToProcess())

        chip.input(0, InputValue.on)
        assertFalse(chip.readyToProcess())

        chip.input(1, InputValue.on)

        assert chip.readyToProcess()
        assert chip.getOutput(0) == InputValue.on
    }

    @Test
    void testAnd01_inputs() {
        Chip chip = new And()

        chip.setInputs("01")

        assert chip.readyToProcess()
        assert chip.getOutput(0) == InputValue.off
    }

    @Test
    void testAnd00_inputs() {
        Chip chip = new And()

        chip.setInputs("00")

        assert chip.readyToProcess()
        assert chip.getOutput(0) == InputValue.off
    }

    @Test
    void testAnd10_inputs() {
        Chip chip = new And()

        chip.setInputs("10")

        assert chip.readyToProcess()
        assert chip.getOutput(0) == InputValue.off
    }

    @Test
    void testAnd11_inputs() {
        Chip chip = new And()

        chip.setInputs("11")

        assert chip.readyToProcess()
        assert chip.getOutput(0) == InputValue.on
    }
}

