package concurrency.threads.threads_4_executors

import concurrency.threads.threads_4_executors.utils.GenerateRandomIntegerTask
import concurrency.threads.threads_4_executors.utils.GenerateRandomIntegerWithIdTask
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


fun main() {
    //executorsExample1()
    //executorsExample2()
    //executorsExample3()
    //executorsExample4()
    executorsExample5()
}

/**
 * Концепция пула потоков:
 * Создание: Вместо создания нового потока для каждой задачи, пул потоков создаёт фиксированное количество потоков (или динамически управляет их количеством) и использует их для выполнения задач.
 * Переиспользование: Потоки из пула могут повторно использоваться для выполнения различных задач, что снижает накладные расходы на создание и уничтожение потоков.
 *
 * Преимущества
 * -Возможность переиспользовать уже созданные потоки
 * -Меньшие затраты по памяти
 * -Позволяют планировать и исполнять задачи
 *
 * Executor/ExecutorService это реализация концепции ThreadPool
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
 * FixedThreadPool создает фиксированное число потоков в пуле. Чаще всего это, это количество ядер процессора - 1
 * Задачи накаплитваются в LinkedBlockingQueue
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
 *  ScheduledThreadPool позволяет выполнять задачи на регулярной основе или с отложенным стартом
 * */
fun executorsExample3() {
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
fun executorsExample4() {
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


/**
 * Пример выполнения Url запроса
 * */
fun executorsExample5() {
    // Создание пула потоков
    val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    // URL для запроса
    val urlString = "https://jsonplaceholder.typicode.com/posts/1"

    // Отправка запроса на сервер
    executorService.submit {
        try {
            // Выполнение HTTP-запроса
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            // Получение ответа от сервера
            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = StringBuilder()
                var inputLine: String?

                while (reader.readLine().also { inputLine = it } != null) {
                    response.append(inputLine)
                }
                reader.close()

                // Логирование ответа
                println("Response: ${response.toString()}")
            } else {
                println("GET request not worked. Response Code: $responseCode")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Завершение работы пула
    executorService.shutdown()
}