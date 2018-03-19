package org.culpan.computersim.chips

import org.culpan.computersim.exceptions.MissingChipDefinitionException
import org.culpan.computersim.utils.SimRunner

class ChipStash {
    private static final Map<String, Chip> chips = new HashMap<>()

    static Chip getChip(String name) {
        Chip result = chips.get(name)

        if (!result) {
            switch (name) {
                case "And":
                    result = new And()
                    break
                case "Or":
                    result = new Or()
                    break
                case "Not":
                    result = new Not()
                    break
                case "DummyChip":
                    result = new DummyChip()
                    break
                case "ExternalOutput":
                    result = new ExternalOutput()
                    break
                case "ExternalInput":
                    result = new ExternalInput()
                    break
                default:
                    result = loadFromClasspath(name)
                    if (!result) {
                        throw new MissingChipDefinitionException(name)
                    }
                    chips.put(name, result)
                    result = result.clone()
            }
        } else {
            result = result.clone()
        }

        return result
    }

    static Chip loadFromClasspath(String name) {
        Chip result = null
        def resource = getClass().getResource("/chips/${name}.hdl")
        if (resource) {
            SimRunner simRunner = new SimRunner()
            result = simRunner.loadChipWithBuiltInScript(resource.toURI())
            result = result.clone()
        }
        result
    }
}
