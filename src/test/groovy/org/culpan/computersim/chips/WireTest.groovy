package org.culpan.computersim.chips

import org.junit.Test

class WireTest extends GroovyTestCase {
    @Test
    void testWireWithNot() {
        TestWire w3 = new TestWire()
        Chip c3 = new Not(w3)
        Wire w2 = new Wire(new Tuple2(c3, 0))
        Chip c2 = new Not(w2)
        Wire w1 = new Wire(new Tuple2(c2, 0))
        Chip c1 = new Not(w1)

        c1.setInputOn(0)
    }

    @Test
    void testWireWithNandAndNot_On() {
        TestWire w2 = new TestWire()
        Chip c2 = new Not(w2)
        Wire w1 = new Wire(new Tuple2(c2, 0))
        Chip c1 = new Nand(w1)

        c1.setInputOn(0)
        c1.setInputOn(1)

        assert w2.value.equals(InputValue.on)
    }

    @Test
    void testWireWithNandAndNot_Off() {
        TestWire w2 = new TestWire()
        Chip c2 = new Not(w2)
        Wire w1 = new Wire(new Tuple2(c2, 0))
        Chip c1 = new Nand(w1)

        c1.setInputOn(0)
        assert !c1.readyToProcess()
        assert !c2.readyToProcess()
        c1.setInputOff(1)


        assert w2.value == InputValue.off
    }

    @Test
    void testWireWithNandAndNot_Off_UsingFactory() {
        TestWire w2 = new TestWire()
        Chip c2 = new Not(w2)
        Chip c1 = new Nand()
        Wire.createWire(c1, 0, c2, 0)

        c1.setInputOn(0)
        assert !c1.readyToProcess()
        assert !c2.readyToProcess()
        c1.setInputOff(1)

        assert w2.value == InputValue.off
    }

    @Test
    void testWireWithNandAndNot_On_Multiple() {
        DummyChip d1 = new DummyChip()
        DummyChip d2 = new DummyChip()
        DummyChip d3 = new DummyChip()

        Wire w2 = new Wire(
                new Tuple2(d1, 0),
                new Tuple2(d2, 0),
                new Tuple2(d3, 0)
        )
        Chip c2 = new Not(w2)
        Wire w1 = new Wire(new Tuple2(c2, 0))
        Chip c1 = new Nand(w1)

        c1.setInputOn(0)
        c1.setInputOn(1)

        assert d1.value.equals(InputValue.on)
        assert d2.value.equals(InputValue.on)
        assert d3.value.equals(InputValue.on)
    }

    @Test
    void testWireWithNandAndNot_On_Multiple_UsingFactory() {
        Chip c1 = new Nand()
        Chip c2 = new Not()

        DummyChip d1 = new DummyChip()
        DummyChip d2 = new DummyChip()
        DummyChip d3 = new DummyChip()

        Wire.createWire(c1, 0, c2, 0)
        Wire.createWire(c2, 0, [
            d1, 0,
            d2, 0,
            d3, 0
        ].toArray())

        c1.setInputOn(0)
        c1.setInputOn(1)

        assert d1.value.equals(InputValue.on)
        assert d2.value.equals(InputValue.on)
        assert d3.value.equals(InputValue.on)
    }

    @Test
    void testXor11_Off() {
        def e1 = new ExternalInput(2)

        def c1 = new Not()
        def c2 = new Not()

        def c3 = new And()
        def c4 = new And()

        def c5 = new Or()

        def c6 = new DummyChip()

        Wire.createWire(e1, 0, [
                c2, 0,
                c3, 0
        ])
        Wire.createWire(e1, 1, [
                c1, 0,
                c4, 1
        ])
        Wire.createWire(c1, 0, c3, 1)
        Wire.createWire(c2, 0, c4, 0)
        Wire.createWire(c3, 0, c5, 0)
        Wire.createWire(c4, 0, c5, 1)
        Wire.createWire(c5, 0, c6, 0)

        e1.setInputOn(0)
        e1.setInputOn(1)

        assert c6.value == InputValue.off
    }

    @Test
    void testXor00_Off() {
        def e1 = new ExternalInput(2)

        def c1 = new Not()
        def c2 = new Not()

        def c3 = new And()
        def c4 = new And()

        def c5 = new Or()

        def c6 = new DummyChip()

        Wire.createWire(e1, 0, [
                c2, 0,
                c3, 0
        ])
        Wire.createWire(e1, 1, [
                c1, 0,
                c4, 1
        ])
        Wire.createWire(c1, 0, c3, 1)
        Wire.createWire(c2, 0, c4, 0)
        Wire.createWire(c3, 0, c5, 0)
        Wire.createWire(c4, 0, c5, 1)
        Wire.createWire(c5, 0, c6, 0)

        e1.setInputOff(0)
        e1.setInputOff(1)

        assert c6.value == InputValue.off
    }
    @Test
    void testXor01_On() {
        def e1 = Chip.getExternalInputChip(2)

        def c1 = Chip.getChip("Not")
        def c2 = Chip.getChip("Not")

        def c3 = Chip.getChip("And")
        def c4 = Chip.getChip("And")

        def c5 = Chip.getChip("Or")

        def c6 = Chip.getChip("DummyChip")

        Wire.createWire(e1, 0, [
                c2, 0,
                c3, 0
        ])
        Wire.createWire(e1, 1, [
                c1, 0,
                c4, 1
        ])
        Wire.createWire(c1, 0, c3, 1)
        Wire.createWire(c2, 0, c4, 0)
        Wire.createWire(c3, 0, c5, 0)
        Wire.createWire(c4, 0, c5, 1)
        Wire.createWire(c5, 0, c6, 0)

        e1.setInputOff(0)
        e1.setInputOn(1)

        assert c6.value == InputValue.on
    }
    @Test
    void testXor10_On() {
        def e1 = new ExternalInput(2)

        def c1 = new Not()
        def c2 = new Not()

        def c3 = new And()
        def c4 = new And()

        def c5 = new Or()

        def c6 = new DummyChip()

        Wire.createWire(e1, 0, [
                c2, 0,
                c3, 0
        ])
        Wire.createWire(e1, 1, [
                c1, 0,
                c4, 1
        ])
        Wire.createWire(c1, 0, c3, 1)
        Wire.createWire(c2, 0, c4, 0)
        Wire.createWire(c3, 0, c5, 0)
        Wire.createWire(c4, 0, c5, 1)
        Wire.createWire(c5, 0, c6, 0)

        e1.setInputOn(0)
        e1.setInputOff(1)

        assert c6.value == InputValue.on
    }
}
