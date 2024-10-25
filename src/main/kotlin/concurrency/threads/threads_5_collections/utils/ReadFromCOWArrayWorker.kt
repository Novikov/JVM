package concurrency.threads.threads_5_collections.utils

class ReadFromCOWArrayWorker(val list: List<Int>) : Runnable {

    override fun run() {
        while (true) {
            try {
                Thread.sleep(70)
                println(list)
            } catch (e: InterruptedException) {
                throw RuntimeException(e)
            }
        }
    }
}