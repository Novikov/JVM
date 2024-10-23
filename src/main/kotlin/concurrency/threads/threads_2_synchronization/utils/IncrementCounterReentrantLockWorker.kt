package concurrency.threads.threads_2_synchronization.utils

import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock


class IncrementCounterReentrantLockWorker {
    var counter: Int = 0

    val counterLock: Lock = ReentrantLock()

    fun startWorker(){
        val start = System.currentTimeMillis()
        val t1 = Thread {
            for (i in 0..9999) {
                increment()
            }
        }

        val t2 = Thread {
            for (i in 0..9999) {
                increment()
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
        val end = System.currentTimeMillis()
        val duration = end - start
        println("Counter: $counter")
        println("Time elapsed: $duration")
    }

    private fun increment() {
        counterLock.lock();
        counter++;
        counterLock.unlock();
    }
}