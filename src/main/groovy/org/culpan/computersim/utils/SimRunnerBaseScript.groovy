package org.culpan.computersim.utils

import org.culpan.computersim.chips.Chip
import org.culpan.computersim.exceptions.InvalidConnectionException

abstract class SimRunnerBaseScript extends Script {
/*    def getProperty(String propName)  {
        if (propName) {
            if (propName.matches("\\d+")) {
                return Integer.parseInt(propName)
            } else {
                Chip c = Chip.getChip(propName)
                if (!c) {
                    return super.getProperty(propName)
                } else {
                    return c
                }
            }
        }
    }
*/
    def invokeMethod(String name, args) {
        Chip result = null
        if (name.equals("inputs")) {
            if (args.length != 1) {
                throw new Exception("Invalid parameters for ExternalInput: ${args}")
            }
            getBinding().input.setInputCount(Integer.parseInt(args[0].toString()))
            result = getBinding().input
        } else if (name.equals("outputs")) {
            if (args.length != 1) {
                throw new Exception("Invalid parameters for ExternalInput: ${args}")
            }
            getBinding().output.setOutputCount(Integer.parseInt(args[0].toString()))
            result = getBinding().output
        } else {
            if (name.equals("Out")) {
                result = getBinding().output
            } else {
                result = Chip.getChip(name)
            }

            args.eachWithIndex { it, idx ->
                if (it instanceof Integer) {
                    getBinding().input.addOutputWire(it, result, idx)
                } else if (it instanceof Chip && it.outputCount() == 1) {
                    it.addOutputWire(0, result, idx)
                } else if (it instanceof Chip) {
                    for (int i = 0; i < it.outputCount(); i++) {
                        it.addOutputWire(i, result, (idx * it.outputCount()) + i)
                    }
                } else {
                    throw new InvalidConnectionException("Invalid type specified as parameter for ${name}")
                }
            }
        }

        return result
    }
}
