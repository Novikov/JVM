package concurrency.threads.threads_1_base_theory.utils

class RunnableCounterWorker(private val name: String, private val range: Int) : Runnable {

    override fun run() {
        var counter = 0
        while (counter <= range) {
            println(name + ": " + counter++)
        }
    }
}