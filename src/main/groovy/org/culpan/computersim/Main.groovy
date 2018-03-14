package org.culpan.computersim

class Main implements Runnable {
    static void main(String [] args) {
        try {
            Runnable r = new Main()
            r.run()
        } catch (Throwable t) {
            t.printStackTrace()
        }
    }

    @Override
    void run() {
        println "Hello world!"
    }
}

