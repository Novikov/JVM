package concurrency.threads.threads_4_executors

import concurrency.threads.threads_4_executors.utils.GenerateRandomIntegerTask
import concurrency.threads.threads_4_executors.utils.GenerateRandomIntegerWithIdTask
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


fun main() {
    //executorsExample1()
    //executorsExample2()
    //schedulersExample3()
    schedulersExample4()
}

/**
 * Executor Framework позволяет легко оперировать десятками и сотнями потоков
 * Концепция пула потоков
 *
 * Преимущества
 * -Возможность переиспользовать уже созданные потоки
 * -Меньшие затраты по памяти
 * -Позволяют планировать и исполнять задачи
 * */

/**
 * SingleThreadExecutor - имеет только один поток в пуле
 * */
fun executorsExample1() {
    val start = System.currentTimeMillis()
    val executor = Executors.newSingleThreadExecutor()
    try {
        for (i in 0..99) {
            executor.submit(GenerateRandomIntegerTask())
        }
    } finally {
        executor.shutdown() // инициируем завершение работы executor
        try {
            // ждем завершения всех задач, ждем до 1 минуты
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                executor.shutdownNow() // принудительное завершение, если задачи не завершились
            }
        } catch (InterruptedException: Exception) {
            executor.shutdownNow() // принудительное завершение, если произошел interruption
        }

        val end = System.currentTimeMillis()
        val duration = end - start
        println("Processed in: $duration ms")
    }
}

/**
 *
 * */
fun executorsExample2() {
    val start = System.currentTimeMillis()
    val cores = Runtime.getRuntime().availableProcessors()
    val executor = Executors.newFixedThreadPool(cores - 1)
    /** Лучше эмпирически экспериминтировать с числом потоков*/
    try {
        for (i in 0..99) {
            executor.submit(GenerateRandomIntegerTask())
        }
    } finally {
        executor.shutdown() // инициируем завершение работы executor
        try {
            // ждем завершения всех задач, ждем до 1 минуты
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                executor.shutdownNow() // принудительное завершение, если задачи не завершились
            }
        } catch (InterruptedException: Exception) {
            executor.shutdownNow() // принудительное завершение, если произошел interruption
        }

        val end = System.currentTimeMillis()
        val duration = end - start
        println("Processed in: $duration ms")
    }

}

/**
 *ScheduledThreadPool позволяет задавать таймаут начала выполнения операции
 * */
fun schedulersExample3() {
    val cores = Runtime.getRuntime().availableProcessors()
    val start = System.currentTimeMillis()
    val executor = Executors.newScheduledThreadPool(cores - 1)
    try {
        val task1 = GenerateRandomIntegerWithIdTask(1)
        val task2 = GenerateRandomIntegerWithIdTask(2)
        val task3 = GenerateRandomIntegerWithIdTask(3)
        val task4 = GenerateRandomIntegerWithIdTask(4)
        val task5 = GenerateRandomIntegerWithIdTask(5)

        executor.schedule(task2, 10, TimeUnit.SECONDS)
        executor.schedule(task1, 3, TimeUnit.SECONDS)
        executor.schedule(task3, 2, TimeUnit.SECONDS)
        executor.schedule(task4, 1, TimeUnit.SECONDS)
        executor.schedule(task5, 0, TimeUnit.SECONDS)
    } finally {
        val end = System.currentTimeMillis()
        val duration = end - start
        println("Processed in: $duration ms")
    }
}

/**
 * CachedThreadPool
 * Не задаем число потоков, а делегируем это самому ExecutorService который будет менять это число в зависимости от нагрузки
 * */
fun schedulersExample4() {
    val start = System.currentTimeMillis()
    val executorService = Executors.newCachedThreadPool()
    try {
        for (i in 0..1000) {
            executorService.submit(GenerateRandomIntegerTask())
        }
        println("TEST")
    } finally {
        val end = System.currentTimeMillis()
        val duration = end - start
        println("Processed in: $duration ms")
    }
}