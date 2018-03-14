package org.culpan.computersim.chips

import org.junit.Test

class NandTest extends GroovyTestCase {

    @Test
    void testNand01() {
        TestWire testWire = new TestWire()
        Chip nand = new Nand(testWire)

        assertFalse(nand.readyToProcess())

        nand.input(0, InputValue.off)
        assertFalse(nand.readyToProcess())

        nand.input(1, InputValue.on)

        assert nand.readyToProcess()
        assert testWire.value == InputValue.on
    }

    @Test
    void testNand00() {
        TestWire testWire = new TestWire()
        Chip nand = new Nand(testWire)

        assertFalse(nand.readyToProcess())

        nand.input(0, InputValue.off)
        assertFalse(nand.readyToProcess())

        nand.input(1, InputValue.off)

        assert nand.readyToProcess()
        assert testWire.value == InputValue.on
    }

    @Test
    void testNand10() {
        TestWire testWire = new TestWire()
        Chip nand = new Nand(testWire)

        assertFalse(nand.readyToProcess())

        nand.input(0, InputValue.on)
        assertFalse(nand.readyToProcess())

        nand.input(1, InputValue.off)

        assert nand.readyToProcess()
        assert testWire.value == InputValue.on
    }

    @Test
    void testNand11() {
        TestWire testWire = new TestWire()
        Chip nand = new Nand(testWire)

        assertFalse(nand.readyToProcess())

        nand.input(0, InputValue.on)
        assertFalse(nand.readyToProcess())

        nand.input(1, InputValue.on)

        assert nand.readyToProcess()
        assert testWire.value == InputValue.off
    }

    @Test
    void testCompareNotAnd_and_Nand() {
        TestWire testWireAnd = new TestWire()
        Chip chipNot = new Not(testWireAnd)
        Wire wireAnd = new Wire(new Tuple2(chipNot, 0))
        Chip chipAnd = new And(wireAnd)
        chipAnd.input(0, InputValue.on)
        chipAnd.input(1, InputValue.on)

        TestWire testWireNand = new TestWire()
        Chip chipNand = new Nand(testWireNand)
        chipNand.input(0, InputValue.on)
        chipNand.input(1, InputValue.on)

        assert testWireNand.value == testWireAnd.value

    }
}
