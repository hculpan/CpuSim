package org.culpan.computersim.exceptions

class InvalidConnectionException extends Exception {
    InvalidConnectionException(int inputIndex) {
        super("Invalid connection parameter specified: ${inputIndex} ")
    }
}
