package concurrency.threads.threads_4_executors.utils

import kotlin.random.Random

class GenerateRandomIntegerWithIdTask(val id: Int) : Runnable {
    private val random: Random = Random

    override fun run() {
        val randomInt = random.nextInt(1000) + 1
        println("SingleThreadPoolTask ID: $id, value: $randomInt")
    }
}