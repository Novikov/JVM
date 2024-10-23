package concurrency.threads.threads_4_executors.utils

import kotlin.random.Random

class GenerateRandomIntegerTask : Runnable {
    private val random: Random = Random

    override fun run() {
        try {
            Thread.sleep(100)
            val randomInt: Int = random.nextInt(1000) + 1
            println("SingleThreadPoolTask: $randomInt")
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
    }
}