package concurrency.threads.threads_1_base_theory.utils

class ThreadCounterWorker(
    private val name: String,
    private val range: Int
) : Thread() {

    override fun run() {
        var counter = 0
        while (counter <= range) {
            println(name + ": " + counter++)
        }
    }
}