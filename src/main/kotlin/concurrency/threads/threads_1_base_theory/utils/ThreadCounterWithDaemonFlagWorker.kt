package concurrency.threads.threads_1_base_theory.utils

class ThreadCounterWithDaemonFlagWorker(private val name: String, private val range: Int) : Thread() {

    constructor(name: String, range: Int, isDaemon: Boolean) : this(name, range) {
        super.setDaemon(isDaemon)
    }

    override fun run() {
        var counter = 0
        while (counter <= range) {
            println(name + ": " + counter++)
        }
        println("$name COUNTER FINISHED THE WORK")
    }
}