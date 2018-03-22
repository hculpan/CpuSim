package org.culpan.computersim.chips

import org.junit.Test

class InputValueTest extends GroovyTestCase {
    @Test
    void testInputValue() {
        InputValue value = InputValue.notset
    }

    @Test
    void testConversionFromString() {
        assert InputValue.fromBinary("0") == InputValue.off
        assert InputValue.fromBinary("1") == InputValue.on
    }

    @Test
    void testConversionFromChar() {
        assert InputValue.fromBinary('0') == InputValue.off
        assert InputValue.fromBinary('1') == InputValue.on
    }
}
