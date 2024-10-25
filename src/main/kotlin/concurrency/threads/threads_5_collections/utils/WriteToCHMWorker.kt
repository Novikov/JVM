package concurrency.threads.threads_5_collections.utils



class WriteToCHMWorker(val map: MutableMap<String, Int>) : Runnable {

    override fun run() {
        try {
            map["A"] = 1
            Thread.sleep(100)
            map["B"] = 2
            Thread.sleep(100)
            map["C"] = 3
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
    }
}