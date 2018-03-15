package org.culpan.computersim.utils

import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.ImportCustomizer
import org.culpan.computersim.chips.Chip

class SimRunner {
    void runSim(String script) {
        def shell = new GroovyShell(this.class.classLoader)
        shell.evaluate script
    }

    void runBuiltInScript(String scriptFileName) {
        def binding = new Binding()

        def importCustomizer = new ImportCustomizer()
        importCustomizer.addStarImports 'org.culpan.computersim.chips'

        def config = new CompilerConfiguration()
        config.addCompilationCustomizers importCustomizer
        config.scriptBaseClass = SimRunnerBaseScript.name

        def shell = new GroovyShell(this.class.classLoader, binding, config)
        def script = SimRunner.getResource(scriptFileName)

        shell.evaluate new File(script.toURI())
    }
}
