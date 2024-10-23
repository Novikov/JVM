package concurrency.threads.threads_2_synchronization.utils

import kotlin.concurrent.Volatile


class VolatileWorker {

    @Volatile
    var i: Int = 0

    fun startWorker(){
        val t1 = Thread {
            while (i < 5) {
                println("increment i to " + (++i))
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }

        }

        val t2 = Thread {
            var localVar = i
            while (localVar < 5) {
                if (localVar != i) {
                    println("new value of i is $i")
                    localVar = i
                }
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

        println("volitile counter: " + i)
    }
}