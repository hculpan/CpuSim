package org.culpan.computersim.chips

class Wire {
    ArrayList<Tuple2<Chip, Integer>> outputs = new ArrayList<>()

    Wire(Chip fromChip, Integer fromIndex) {
        fromChip.setOutputWire(fromIndex, this)
    }

    Wire(Tuple2<Chip, Integer> ... outputs) {
        this.outputs.addAll(outputs)
    }

    void setValueOn() {
        outputs.each { it.first.input(it.second.intValue(), InputValue.on) }
    }

    void setValueOff() {
        outputs.each { it.first.input(it.second.intValue(), InputValue.off) }
    }

    void addOutput(Chip chip, Integer idx) {
        outputs.add(new Tuple2<Chip, Integer>(chip, idx))
    }

    static Wire createWire(Chip fromChip, int fromIdx, Chip toChip, int toIdx) {
        Wire wire = fromChip.getOutputWire(fromIdx)
        if (wire == null) {
            wire = new Wire()
        }
        wire.addOutput(toChip, toIdx)
        return wire
    }

    static Wire createWire(Chip fromChip, int fromIdx, Object []toChipsAndIdxs) {
        if (toChipsAndIdxs.length == 1 && toChipsAndIdxs[0] instanceof ArrayList) {
            toChipsAndIdxs = ((ArrayList<Object>)toChipsAndIdxs[0]).toArray()
        }

        Wire wire = new Wire(fromChip, fromIdx)
        for (int i = 0; i < toChipsAndIdxs.length; i+=2) {
            wire.addOutput((Chip)toChipsAndIdxs[i], (Integer)toChipsAndIdxs[i+1])
        }

        return wire
    }
}
