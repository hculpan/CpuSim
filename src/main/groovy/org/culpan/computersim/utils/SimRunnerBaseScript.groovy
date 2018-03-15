package org.culpan.computersim.utils

import org.culpan.computersim.chips.Chip
import org.culpan.computersim.chips.Wire

abstract class SimRunnerBaseScript extends Script {
    def wire(Chip fromChip, int fromIdx, Chip toChip, int toIdx) {
        return Wire.createWire(fromChip, fromIdx, toChip, toIdx)
    }

    def wire(Chip fromChip, int fromIdx, Object []toChipsAndIdxs) {
        return Wire.createWire(fromChip, fromIdx, toChipsAndIdxs)
    }

    def invokeMethod(String name, args) {
        def params = (String [])args
        if (name.equals("ExternalInput")) {
            if (params.length != 1) {
                throw new Exception("Invalid parameters for ExternalInput: ${args}")
            }
            return Chip.getExternalInputChip(Integer.parseInt(params[0]))
        } else {
            return Chip.getChip(name)
        }
    }
}
