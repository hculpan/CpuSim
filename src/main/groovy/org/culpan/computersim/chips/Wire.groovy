package org.culpan.computersim.chips

class Wire {
    Tuple2<Chip, Integer> [] outputs

    Wire(Tuple2<Chip, Integer> ... outputs) {
        this.outputs = outputs
    }

    void setValueOn() {
        outputs.each { it.first.input(it.second.intValue(), InputValue.on) }
    }

    void setValueOff() {
        outputs.each { it.first.input(it.second.intValue(), InputValue.off) }
    }

    static Wire createWire(Chip fromChip, int fromIdx, Chip toChip, int toIdx) {
        Wire wire = new Wire(new Tuple2(toChip, toIdx))
        fromChip.setOutputWire(fromIdx, wire)
        return wire
    }

    static Wire createWire(Chip fromChip, int fromIdx, Object []toChipsAndIdxs) {
        def toValues = [toChipsAndIdxs.length / 2]
        for (int i = 0; i < toChipsAndIdxs.length; i+=2) {
            toValues.add(new Tuple2(toChipsAndIdxs[i], toChipsAndIdxs[i+1]))
        }
        Wire wire = new Wire(toValues)

        fromChip.setOutputWire(fromIdx, wire)
        return wire
    }
}
