package concurrency.threads.threads_5_collections.utils

class ReadFromCHMWorker(val map: MutableMap<String, Int>) : Runnable {
    override fun run() {
        try {
            Thread.sleep(50)
            println("A: " + map["A"])
            println("B: " + map["B"])
            Thread.sleep(5000)
            println("C: " + map["C"])
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
    }
}