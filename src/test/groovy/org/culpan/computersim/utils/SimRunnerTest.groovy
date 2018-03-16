package org.culpan.computersim.utils

import org.culpan.computersim.chips.Chip
import org.junit.Test

class SimRunnerTest extends GroovyTestCase {
    @Test
    void testRunSim_String() {
        def script = '''
            import org.culpan.computersim.chips.*
                
            def e1 = new ExternalInput(2)
    
            def c1 = new Not()
            def c2 = new Not()
    
            def c3 = new And()
            def c4 = new And()
    
            def c5 = new Or()
    
            def c6 = new DummyChip()
    
            Wire.createWire(e1, 0, [
                    c2, 0,
                    c3, 0
            ])
            Wire.createWire(e1, 1, [
                    c1, 0,
                    c4, 1
            ])
            Wire.createWire(c1, 0, c3, 1)
            Wire.createWire(c2, 0, c4, 0)
            Wire.createWire(c3, 0, c5, 0)
            Wire.createWire(c4, 0, c5, 1)
            Wire.createWire(c5, 0, c6, 0)
    
            e1.setInputOn(0)
            e1.setInputOn(1)
    
            println c6.value
        '''

        def simRunner = new SimRunner()
        simRunner.runSim(script)
    }

    @Test
    void testRunSim_ClasspathFile() {
        def simRunner = new SimRunner()
        simRunner.runBuiltInScript("/chips/Xor.hdl")
        Chip xor = simRunner.input

        xor.setInputOn(0)
        xor.setInputOn(1)

        println "Inputs: ${xor.getInputBinary(0)} ${xor.getInputBinary(1)} : ${simRunner.output.value.binaryValue}"

        xor.resetAll()

        xor.setInputOn(0)
        xor.setInputOff(1)

        println "Inputs: ${xor.getInputBinary(0)} ${xor.getInputBinary(1)} : ${simRunner.output.value.binaryValue}"

    }
}
