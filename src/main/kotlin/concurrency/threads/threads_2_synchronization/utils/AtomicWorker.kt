package concurrency.threads.threads_2_synchronization.utils

import java.util.concurrent.atomic.AtomicLong


class AtomicWorker {

    val atomicLongCounter = AtomicLong(0)

    fun startWorker(){
        val t1 = Thread {
            for (i in 0..1000000 - 1) {
                incrementAtomicLong()
            }
        }

        val t2 = Thread {
            for (i in 0..1000000 - 1) {
                incrementAtomicLong()
            }
        }

        t1.start()
        t2.start()

        try {
            t1.join()
            t2.join()
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }

        println("atomicLongCounter: " + atomicLongCounter.get())
    }

    private fun incrementAtomicLong() {
        atomicLongCounter.incrementAndGet()
    }
}