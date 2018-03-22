package org.culpan.computersim.utils

import org.culpan.computersim.chips.Chip
import org.culpan.computersim.chips.ChipStash
import org.junit.Test

class SimRunnerTest extends GroovyTestCase {
/*    @Test
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
    } */

    @Test
    void testLoadChipWithScript_On() {
        def resource = getClass().getResource("/chips/Xor.hdl")
        SimRunner simRunner = new SimRunner()
        Chip xor = simRunner.loadChipWithScript(resource.toURI())

        xor.setInputOn(0)
        xor.setInputOff(1)

        println "Xor: ${xor.getInput(0).binary} ${xor.getInput(1).binary} : ${xor.getOutput(0).binary}"
        assert xor.getOutput(0).binary == 1
    }

    @Test
    void testLoadChipWithScript_Off() {
        def resource = getClass().getResource("/chips/Xor.hdl")
        SimRunner simRunner = new SimRunner()
        Chip xor = simRunner.loadChipWithScript(resource.toURI())

        xor.setInputOn(0)
        xor.setInputOn(1)

        println "Xor: ${xor.getInput(0).binary} ${xor.getInput(1).binary} : ${xor.getOutput(0).binary}"
        assert xor.getOutput(0).binary == 0
    }

    @Test
    void testRunSim_ClasspathFile() {
        Chip xor = ChipStash.getChip("Xor")

        xor.setInputOn(0)
        xor.setInputOn(1)

        println "Xor: ${xor.getInput(0).binary} ${xor.getInput(1).binary} : ${xor.getOutput(0).binary}"
        assert xor.getOutput(0).binary == 0

        xor = ChipStash.getChip("Xor")

        xor.setInputOn(0)
        xor.setInputOff(1)

        println "Xor: ${xor.getInput(0).binary} ${xor.getInput(1).binary} : ${xor.getOutput(0).binary}"
        assert xor.getOutput(0).binary == 1
    }

    @Test
    void testRunSim_Filesystem() {
        Chip chip = ChipStash.getChip("And16")

        chip.setInputs( 0, "1011001000011111")
        chip.setInputs(16, "1011000001010111")

        print "And16: "
        for (int i = 0; i < 32; i+=2) {
            print "${chip.getInput(i).binary} ${chip.getInput(i + 1).binary} "
        }
//        println "Xor3: ${xor.getInput(0).binary} ${xor.getInput(1).binary} ${xor.getInput(2).binary}: ${xor.getOutput(0).binary}"

        assert chip.getOutputString() == "1011000000010111"
    }
}
