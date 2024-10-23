package concurrency.threads.threads_2_synchronization.utils

import java.util.concurrent.Semaphore

class FooSemaphoreSafe {
    private val betweenFirstAndSecond: Semaphore = Semaphore(0)
    private val betweenSecondAndThird: Semaphore = Semaphore(0)

    fun first() {
        println("first")
        betweenFirstAndSecond.release()
    }

    fun second() {
        try {
            betweenFirstAndSecond.acquire()
            println("second")
            betweenSecondAndThird.release()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun third() {
        try {
            betweenSecondAndThird.acquire()
            println("third")
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}