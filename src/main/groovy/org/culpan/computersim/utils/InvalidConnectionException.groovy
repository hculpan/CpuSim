package org.culpan.computersim.utils

class InvalidConnectionException extends Exception {
    InvalidConnectionException(int inputIndex) {
        super("Invalid connection parameter specified: ${inputIndex} ")
    }
}
