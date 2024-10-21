package concurrency.threads.threads_1_base_theory.utils

class ThreadWithSleepCounterWorker(
    private val name: String,
    private val range: Int
) : Thread() {

    override fun run() {
        var counter = 0
        while (counter <= range) {
            println(name + ": " + counter++)
            try {
                sleep(1000)
            }catch (ex: InterruptedException) {
                throw RuntimeException()
            }
        }
    }
}