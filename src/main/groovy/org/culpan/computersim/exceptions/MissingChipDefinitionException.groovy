package org.culpan.computersim.exceptions

class MissingChipDefinitionException extends Exception {
    MissingChipDefinitionException(String chipName) {
        super("Invalid chip name specified: ${chipName} ")
    }
}
