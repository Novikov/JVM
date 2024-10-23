package concurrency.threads.threads_3_problems.starvation

import java.util.concurrent.atomic.AtomicInteger

/**
 * Смысл в том, что мы пятому потому задали наименьший приоритет и система не выдает ресурс на его исполнение и он завершается последним
 * */
var atomicInteger = AtomicInteger(0)

fun main() {
    println("Main thread execution starts")

    // Thread priorities are set in a way that thread5
    // gets least priority.
    val thread1 = StarvationWorker(atomicInteger, "T1")
    thread1.priority = 10
    val thread2 = StarvationWorker(atomicInteger, "T2")
    thread2.priority = 9
    val thread3 = StarvationWorker(atomicInteger, "T3")
    thread3.priority = 8
    val thread4 = StarvationWorker(atomicInteger, "T4")
    thread4.priority = 7

    val thread5 = StarvationWorker(atomicInteger, "T5")
    thread5.priority = 1

    thread1.start()
    thread2.start()
    thread3.start()
    thread4.start()


    // Here thread5 have to wait because of the
    // other thread. But after waiting for some
    // interval, thread5 will get the chance of
    // execution. It is known as Starvation
    thread5.start()
    println("Main thread execution completes")
}