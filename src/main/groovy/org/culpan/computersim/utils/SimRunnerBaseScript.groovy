package org.culpan.computersim.utils

import org.culpan.computersim.chips.Chip
import org.culpan.computersim.chips.Wire

abstract class SimRunnerBaseScript extends Script {
    def wire(Chip fromChip, int fromIdx, Chip toChip, int toIdx) {
        return Wire.createWire(fromChip, fromIdx, toChip, toIdx)
    }

    def wireInput(int fromIdx, Chip toChip, int toIdx) {
        return Wire.createWire(getBinding().input, fromIdx, toChip, toIdx)
    }

    def wireOutput(Chip fromChip, int fromIdx, int toIdx) {
        return Wire.createWire(fromChip, fromIdx, getBinding().output, toIdx)
    }

    def invokeMethod(String name, args) {
        def params = (String [])args
        if (name.equals("inputs")) {
            if (params.length != 1) {
                throw new Exception("Invalid parameters for ExternalInput: ${args}")
            }
            getBinding().input.setInputCount(Integer.parseInt(params[0]))
        } else if (name.equals("outputs")) {
            if (params.length != 1) {
                throw new Exception("Invalid parameters for ExternalInput: ${args}")
            }
            getBinding().output.setOutputCount(Integer.parseInt(params[0]))
        } else if (name.equals("wire")) {
            if (params.length != 4) {
                throw new Exception("Invalid parameters for wire: ${args}")
            }
            wire(getBinding().getVariable(params[0]), Integer.parseInt(params[1]), getBinding().getVariable(params[2]), Integer.parseInt(params[3]))
        } else if (name.equals("wireInput")) {
            if (params.length != 3) {
                throw new Exception("Invalid parameters for wireInput: ${args}")
            }
            wireInput(Integer.parseInt(params[0]), getBinding().getVariable(params[1]), Integer.parseInt(params[2]))
        } else if (name.equals("wireOutput")) {
            if (params.length != 3) {
                throw new Exception("Invalid parameters for wireOutput: ${args}")
            }
            wireOutput(getBinding().getVariable(params[0]), Integer.parseInt(params[1]), Integer.parseInt(params[2]))
        } else {
            Chip.getChip(name)
        }
    }
}
