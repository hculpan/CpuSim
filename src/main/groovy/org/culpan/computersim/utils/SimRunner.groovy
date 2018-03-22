package org.culpan.computersim.utils

import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.ImportCustomizer
import org.culpan.computersim.chips.Chip
import org.culpan.computersim.chips.ExternalInput
import org.culpan.computersim.chips.ExternalOutput

class SimRunner {
    void runSim(String script) {
        def shell = new GroovyShell(this.class.classLoader)
        shell.evaluate script
    }

    Chip input = new ExternalInput()

    Chip output = new ExternalOutput()

    class CustomBinding extends Binding {
        Map<String, Object> chipVariables = new HashMap<>()

        CustomBinding() {
            chipVariables["input"] = input
            input.setName("input")
            chipVariables["output"] = output
            output.setName("output")
            chipVariables["out"] = System.out
        }

        def getVariable(String name) {
            if (name == null) return

            int idx
            if ( (idx = name.lastIndexOf('.')) > -1) {
                name = name.substring(idx + 1)
            }
            if (name.matches("\\d+")) {
                return Integer.parseInt(name)
            } else if (!chipVariables[name]) {
                println "Instantiating variable: ${name}"
                chipVariables[name] = Chip.getChip(name)
            }
            chipVariables[name]
        }
    }

    void runBuiltInScript(String scriptFileName) {
        def binding = new CustomBinding()

        def importCustomizer = new ImportCustomizer()
        importCustomizer.addStarImports 'org.culpan.computersim.chips'

        def config = new CompilerConfiguration()
        config.addCompilationCustomizers importCustomizer
        config.scriptBaseClass = SimRunnerBaseScript.name

        def shell = new GroovyShell(this.class.classLoader, binding, config)
        def script = SimRunner.getResource(scriptFileName)

        shell.evaluate new File(script.toURI())
    }

    Chip loadChipWithScript(URI uri) {
        def binding = new CustomBinding()

        def importCustomizer = new ImportCustomizer()
        importCustomizer.addStarImports 'org.culpan.computersim.chips'

        def config = new CompilerConfiguration()
        config.addCompilationCustomizers importCustomizer
        config.scriptBaseClass = SimRunnerBaseScript.name

        def shell = new GroovyShell(this.class.classLoader, binding, config)

        shell.evaluate uri

        input.setOutputChip(output)

        return input
    }
}
