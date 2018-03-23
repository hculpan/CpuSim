package org.culpan.computersim.exceptions

class InvalidConnectionException extends Exception {
    InvalidConnectionException(String msg) {
        super(msg)
    }

    InvalidConnectionException(int inputIndex) {
        super("Invalid connection parameter specified: ${inputIndex} ")
    }
}
