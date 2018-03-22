package org.culpan.computersim.chips

import org.junit.Test

class NandTest extends GroovyTestCase {

    @Test
    void testNand01() {
        Chip nand = new Nand()

        assertFalse(nand.readyToProcess())

        nand.input(0, InputValue.off)
        assertFalse(nand.readyToProcess())

        nand.input(1, InputValue.on)

        assert nand.readyToProcess()
        assert nand.getOutput(0) == InputValue.on
    }

    @Test
    void testNand00() {
        Chip nand = new Nand()

        assertFalse(nand.readyToProcess())

        nand.input(0, InputValue.off)
        assertFalse(nand.readyToProcess())

        nand.input(1, InputValue.off)

        assert nand.readyToProcess()
        assert nand.getOutput(0) == InputValue.on
    }

    @Test
    void testNand10() {
        Chip nand = new Nand()

        assertFalse(nand.readyToProcess())

        nand.input(0, InputValue.on)
        assertFalse(nand.readyToProcess())

        nand.input(1, InputValue.off)

        assert nand.readyToProcess()
        assert nand.getOutput(0) == InputValue.on
    }

    @Test
    void testNand11() {
        Chip nand = new Nand()

        assertFalse(nand.readyToProcess())

        nand.input(0, InputValue.on)
        assertFalse(nand.readyToProcess())

        nand.input(1, InputValue.on)

        assert nand.readyToProcess()
        assert nand.getOutput(0) == InputValue.off
    }

    @Test
    void testCompareNotAnd_and_Nand() {
        Chip chipNot = new Not()
        Chip chipAnd = new And()
        chipAnd.addOutputWire(chipNot)

        chipAnd.input(0, InputValue.on)
        chipAnd.input(1, InputValue.on)

        Chip chipNand = new Nand()
        chipNand.input(0, InputValue.on)
        chipNand.input(1, InputValue.on)

        assert chipNot.getOutput(0) == chipNand.getOutput(0)

    }
}
