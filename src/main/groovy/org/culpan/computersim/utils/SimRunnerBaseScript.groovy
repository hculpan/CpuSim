package org.culpan.computersim.utils

import org.culpan.computersim.chips.Chip

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
        def params = (String [])args
        if (name.equals("inputs")) {
            if (params.length != 1) {
                throw new Exception("Invalid parameters for ExternalInput: ${args}")
            }
            getBinding().input.setInputCount(Integer.parseInt(params[0]))
            result = getBinding().input
        } else if (name.equals("outputs")) {
            if (params.length != 1) {
                throw new Exception("Invalid parameters for ExternalInput: ${args}")
            }
            getBinding().output.setOutputCount(Integer.parseInt(params[0]))
            result = getBinding().output
        } else if (name.equals("Out")) {
            result = getBinding().output
        } else {
            result = Chip.getChip(name)

//            println "Variables: ${getBinding().variables}"

            params.eachWithIndex { it, idx ->
/*                def o = getProperty(it)
                if (o != null) {
                    println "Found variable: ${it} = ${o}"
                } else {
                    println "Missing variable: ${it}"
                }*/
            }
        }

        return result
    }
}
