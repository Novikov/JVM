package concurrency.threads.threads_2_synchronization.utils

import java.util.concurrent.Exchanger
import java.util.concurrent.atomic.AtomicInteger


class PingWorker(
    val counter: AtomicInteger,
    val exchanger: Exchanger<AtomicInteger> // тут указан тип данным обмен которым будет происходить
) : Runnable {
    override fun run() {
        while (true) {
            try {
                val exchangedCounter = exchanger.exchange(counter)
                println("PING: " + exchangedCounter.getAndIncrement())
            } catch (e: InterruptedException) {
                throw RuntimeException(e)
            }
        }
    }
}