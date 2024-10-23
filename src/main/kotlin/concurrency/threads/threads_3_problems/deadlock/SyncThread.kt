package concurrency.threads.threads_3_problems.deadlock

class SyncThread(val lock1: Any, val lock2: Any) : Runnable {
    override fun run() {
        val name = Thread.currentThread().name
        println("Thread: $name acquiring lock on $lock1")
        synchronized(lock1) {
            println("$name acquired lock on $lock1")
            work()
            println("$name acquiring lock on $lock2")
            synchronized(lock2) {
                println("$name acquired lock on $lock2")
                work()
            }
            println("$name released lock on $lock2")
        }
        println("$name released lock on $lock1")
        println("$name finished execution.")
    }

    private fun work() {
        try {
            Thread.sleep(5000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}