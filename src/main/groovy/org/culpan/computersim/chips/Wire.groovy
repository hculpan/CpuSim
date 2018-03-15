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
        if (toChipsAndIdxs.length == 1 && toChipsAndIdxs[0] instanceof ArrayList) {
            toChipsAndIdxs = ((ArrayList<Object>)toChipsAndIdxs[0]).toArray()
        }

        def toValues = new Tuple2<Chip, Integer>[toChipsAndIdxs.length / 2]
        for (int i = 0; i < toChipsAndIdxs.length / 2; i++) {
            int j = i * 2
            Tuple2<Chip, Integer> t = new Tuple2(toChipsAndIdxs[j], toChipsAndIdxs[j + 1])
            toValues[i] = t
        }
        Wire wire = new Wire(toValues)

        fromChip.setOutputWire(fromIdx, wire)
        return wire
    }
}
