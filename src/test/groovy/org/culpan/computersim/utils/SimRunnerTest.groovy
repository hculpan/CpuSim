package org.culpan.computersim.utils

import org.culpan.computersim.chips.Chip
import org.culpan.computersim.chips.ChipStash
import org.junit.Test

class SimRunnerTest extends GroovyTestCase {
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
        xor.resetAll()

        xor.setInputOn(0)
        xor.setInputOn(1)

        println "Xor: ${xor.getInput(0).binary} ${xor.getInput(1).binary} : ${xor.getOutput(0).binary}"
        assert xor.getOutput(0).binary == 0
    }

    @Test
    void testRunSim_ClasspathFile_11() {
        Chip xor = ChipStash.getChip("Xor")

        xor.setInputOn(0)
        xor.setInputOn(1)

        println "Xor: ${xor.getInput(0).binary} ${xor.getInput(1).binary} : ${xor.getOutput(0).binary}"
        assert xor.getOutput(0).binary == 0
    }

    @Test
    void testRunSim_ClasspathFile_01() {
        Chip xor = ChipStash.getChip("Xor")

        xor.setInputOff(0)
        xor.setInputOn(1)

        println "Xor: ${xor.getInput(0).binary} ${xor.getInput(1).binary} : ${xor.getOutput(0).binary}"
        assert xor.getOutput(0).binary == 1
    }

    @Test
    void testRunSim_ClasspathFile_00() {
        Chip xor = ChipStash.getChip("Xor")

        xor.setInputs("00")

        println "Xor: ${xor.getInput(0).binary} ${xor.getInput(1).binary} : ${xor.getOutput(0).binary}"
        assert xor.getOutput(0).binary == 0
    }

    @Test
    void testRunSim_ClasspathFile_10() {
        Chip xor = ChipStash.getChip("Xor")

        xor.setInputs("10")

        println "Xor: ${xor.getInput(0).binary} ${xor.getInput(1).binary} : ${xor.getOutput(0).binary}"
        assert xor.getOutput(0).binary == 1
    }

    @Test
    void testRunSim_And4() {
        Chip chip = ChipStash.getChip("And4")

        chip.setInputs(0, "0011")
        chip.setInputs(4, "1001")

        print "And4: "
        for (int i = 0; i < 8; i+=2) {
            print "${chip.getInput(i).binary}${chip.getInput(i + 1).binary}"
        }
        println " : ${chip.getOutputString()}"

        assert chip.getOutputString() == "0001"
    }

    @Test
    void testRunSim_And8() {
        Chip chip = ChipStash.getChip("And8")

        chip.setInputs(0, "00110110")
        chip.setInputs(8, "10011010")

        print "And8: "
        for (int i = 0; i < 16; i+=2) {
            print "${chip.getInput(i).binary}${chip.getInput(i + 1).binary}"
        }
        println " : ${chip.getOutputString()}"

        assert chip.getOutputString() == "00010010"
    }
}
