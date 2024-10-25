package concurrency.threads.threads_5_collections

import concurrency.threads.threads_5_collections.utils.ReadFromCHMWorker
import concurrency.threads.threads_5_collections.utils.ReadFromCOWArrayWorker
import concurrency.threads.threads_5_collections.utils.WriteToCHMWorker
import concurrency.threads.threads_5_collections.utils.WriteToCOWArrayWorker
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.CopyOnWriteArrayList


fun main() {
    //collectionsExample1()
    //collectionsExample2()
    collectionsExample2_2()
    //collectionsExample3()
    //collectionsExample4()
}

/**
 * При работе с непотокобезопастными коллекциями так же как и с разделяемым ресурсом может возникнуть наложение операций
 * Мы одновременно и читаем и пишем в одну область памяти
 * */
fun collectionsExample1() {
    val threadUnsafeList: MutableList<Int> = ArrayList()

    val t1 = Thread {
        for (i in 0..1000000 - 1) {
            threadUnsafeList.add(i)
        }
    }

    val t2 = Thread {
        for (i in 0..1000000 - 1) {
            threadUnsafeList.add(i)
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

    println("threadUnsafeList size: " + threadUnsafeList.size)
}


/**
 * Используем потокобезопастную коллекцию
 * */
fun collectionsExample2() {
    val threadUnsafeList: MutableList<Int> = Collections.synchronizedList(ArrayList())

    val t1 = Thread {
        for (i in 0..1000000 - 1) {
            threadUnsafeList.add(i)
        }
    }

    val t2 = Thread {
        for (i in 0..1000000 - 1) {
            threadUnsafeList.add(i)
        }
    }

    t1.start()
    t2.start()

    try {
        t1.join()
        t2.join()
    } catch (e: InterruptedException) {
        throw java.lang.RuntimeException(e)
    }

    println("threadUnsafeList size: " + threadUnsafeList.size)
}

/**
 * Используется оптимизированный ArrayList.
 * Любой поток имеет доступ к коллекции при чтении!!! Блокировка применяться не будет.
 * Изменение коллекции требует блокировки и потоки которые хотят внести изменения будут ждать освобождения ресурса
 *
 * В примере ниже операция на изменении коллекции иногда будет ждать чтения
 * Это можно заметить при печати строки с одинаковыми на чтении 2 раза
 * */
fun collectionsExample2_2() {
    val list: MutableList<Int> = CopyOnWriteArrayList()
    list.addAll(mutableListOf(1, 2, 3))

    val writeToCOWArrayWorker  = WriteToCOWArrayWorker(list)
    val readFromCOWArrayWorker = ReadFromCOWArrayWorker(list)

    val t1 = Thread(writeToCOWArrayWorker)
    val t2 = Thread(writeToCOWArrayWorker)
    val t3 = Thread(readFromCOWArrayWorker)

    t1.start()
    t2.start()
    t3.start()
}

/**
 * Используется внутренний монитор и только один поток может работать с данными
 * */
fun collectionsExample3() {
    val concurrentMap = Collections.synchronizedMap<String, Int>(hashMapOf())

    val writeToCHMWorker = WriteToCHMWorker(concurrentMap)
    val readFromCHMWorker = ReadFromCHMWorker(concurrentMap)

    val t1 = Thread(writeToCHMWorker)
    val t2 = Thread(writeToCHMWorker)
    val t3 = Thread(readFromCHMWorker)

    t1.start()
    t2.start()
    t3.start()
}

/**
 * Оптимизированная HashMap
 * Используется отдельный монитор на каждый сегмент, а не всю коллекцию
 * */
fun collectionsExample4() {
    val concurrentMap: ConcurrentMap<String, Int> = ConcurrentHashMap()

    val writeToCHMWorker = WriteToCHMWorker(concurrentMap)
    val readFromCHMWorker = ReadFromCHMWorker(concurrentMap)

    val t1 = Thread(writeToCHMWorker)
    val t2 = Thread(writeToCHMWorker)
    val t3 = Thread(readFromCHMWorker)

    t1.start()
    t2.start()
    t3.start()
}



