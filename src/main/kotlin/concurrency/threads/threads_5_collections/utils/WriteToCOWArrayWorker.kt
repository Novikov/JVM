package concurrency.threads.threads_5_collections.utils

import java.util.*

class WriteToCOWArrayWorker(val list: MutableList<Int>) : Runnable {
    private val random: Random = Random()

    override fun run() {
        while (true) {
            try {
                Thread.sleep(100)
                list[random.nextInt(list.size)] = random.nextInt(100)
            } catch (e: InterruptedException) {
                throw RuntimeException(e)
            }
        }
    }
}