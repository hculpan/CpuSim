package org.culpan.computersim.chips

import org.culpan.computersim.exceptions.MissingChipDefinitionException
import org.culpan.computersim.utils.SimRunner

class ChipStash {
    private static final Map<String, Chip> chips = new HashMap<>()

    private static final ArrayList<String> paths = new ArrayList<>()

    static {
        String path = System.getProperty("chippath")
        if (!path || path.isEmpty()) {
            path = "./chips"
        }
        paths.addAll(path.split(File.pathSeparator))
    }

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
                        result = loadFromFilesystem(name)
                        if (!result) {
                            throw new MissingChipDefinitionException(name)
                        }
                    }
                    chips.put(name, result)
                    result = result.clone()
            }
            result.setName(name)
            result.resetAll()
        } else {
            result = result.clone()
            result.resetAll()
        }

        return result
    }

    static Chip loadFromClasspath(String name) {
        Chip result = null
        def resource = getClass().getResource("/chips/${name}.hdl")
        if (resource) {
            SimRunner simRunner = new SimRunner()
            result = simRunner.loadChipWithScript(resource.toURI())
            result = result.clone()
        }
        result
    }

    static Chip loadFromFilesystem(String name) {
        Chip result = null
        for (int i = 0; i < paths.size(); i++) {
            def resource = new File(paths.get(i) + File.separator + "${name}.hdl")
            if (resource) {
                SimRunner simRunner = new SimRunner()
                result = simRunner.loadChipWithScript(resource.toURI())
                result = result.clone()
            }
        }
        result
    }
}
