package concurrency.threads.threads_1_base_theory.utils

class ThreadCounterWithPriorityWorker(
    private val name: String,
    private val range: Int
) : Thread() {

    constructor(name: String, range: Int, priority: Int) : this(name, range) {
        super.setPriority(priority)
    }

    override fun run() {
        var counter = 0
        while (counter <= range!!) {
            println(name + ": " + counter++)
        }
    }
}