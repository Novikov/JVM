package concurrency.threads.threads_3_problems.starvation

import java.util.concurrent.atomic.AtomicInteger



class StarvationWorker(val threadCount: AtomicInteger) : Thread() {

   constructor (threadCount: AtomicInteger, name: String?) : this(threadCount) {
        this.name = name
    }

    override fun run() {
        threadCount.incrementAndGet()
        println("Thread: " + this.name + ", counter " + threadCount + " thread execution starts")
        println("Thread:" + this.name + " thread execution completes")
    }
}