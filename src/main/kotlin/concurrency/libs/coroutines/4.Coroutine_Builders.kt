package concurrency.libs.coroutines

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

suspend fun main() {

    //Нет вызывов Launch и Async

  //  coroutineBuilders2Example1()
//    coroutineBuilders2Example2()
//    coroutineBuilders2Example3()
    coroutineBuilders2Example4()
//    coroutineBuilders2Example5()
}

/**
 * Прежде всего, launch(), как правило, применяется,
 * когда нам не надо возвращать результат из корутины и когда нам ее надо выполнять одновременно с другим кодом.
 * */

suspend fun launchExample1() = coroutineScope {

    launch {
        for (i in 1..5) {
            println(i)
            delay(400L)
        }
    }

    println("Start")
    println("End")
}

/**
 * launch билдер возвращает объект job. Вызов join() по ссылке job заставит подождать кода ниже до тех пор пока job выше, на котором произошел вызов не перейдет
 * в состояние completed (т.е выполнится все внутри и дочерниие корутинын).
 * */

suspend fun launchExample2() = coroutineScope {

    val job = launch {
        for (i in 1..5) {
            println(i)
            delay(400L)
        }
    }

    println("Start")
    job.join()
    println("End")
}

/**
 * Запуск нескольких корутин.
 * Подобным образом можно запускать в одной функции сразу несколько корутин. И они будут выполняться одновременно.
 * Попробуй запустить с join и без него в разных вариациях.
 * Понимание join() - код ниже подождет, пока job выше, на котором вызван join() перейдет в состояние completed (т.е когда выполнится все внутри + дочерние корутины)
 * */

suspend fun launchExample3() = coroutineScope {
    val job1 = launch {
        for (i in 0..5) {
            delay(400L)
            println(i)
        }
    }

    //вот тут также можно вызвать join и поведение изменится

    val job2 = launch {
        for (i in 6..10) {
            delay(400L)
            println(i)
        }
    }

//    job1.join()

//    joinAll(job1, job2) // Принимает varags из job.

    println("Hello Coroutines")
}

/**
 * Отложенное выполнение
 * По умолчанию построитель корутин launch создает и сразу же запускает корутину.
 * Однако Kotlin также позволяет применять технику отложенного запуска корутины (lazy-запуск), при котором корутина запускается при вызове метода start() объекта Job.
 * Для установки отложенного запуска в функцию launch() передается значение start = CoroutineStart.LAZY
 * */

suspend fun launchExample4() = coroutineScope {

    // корутина создана, но не запущена
    val job = launch(start = CoroutineStart.LAZY) {
        delay(200L)
        println("Coroutine has started")
    }

    delay(5000L)
    job.start() // запускаем корутину
    println("Other actions in main method")
}

/**
 * Все тоже самое можно проделать с помощью async
 * */


/**
 * Async emulation
 * 1)С помощью launch можно сделать 2 simultaneously запроса. Для этого нужно убрать join()
 * 2)С помощью launch нельзя вернуть результат, но можно класть его в общий разделяемый ресурс. Но это shared mutable state. Что есть костыль и может выстрелить.
 * Есть способы как это обойти, но это тоже костыли. Лучше этого избегать.
 * 3)Мы можем влиять на время выполнения с помощью join(). В текущем примере ниже с помощью join() мы говорим внешней корутине подождать выполнения
 * дочерних корутин. Время выполнения будет немного больше чем 400 милисекунд. Если вызвать jon1.join() сразу после его launch билдера (закомментированный код),
 * то время работы увеличится потому что вторая корутина будет ждать выполнения первой.
 * */

suspend fun launchExample5() = coroutineScope {

    val startTime = System.currentTimeMillis()
    val resultList = mutableListOf<Int>()

    val job1 = launch {
        delay(200)
        println("work from job1")
        resultList.add(1)
        println("end work from job 1")
    }

    job1.join()

    val job2 = launch {
        delay(400)
        println("work from job2")
        resultList.add(2)
        println("end work from job 2")
    }

//    job1.join()
//    job2.join()

    val endTime = System.currentTimeMillis() - startTime

    println("coroutine end with time - $endTime and result list - $resultList")
}

/**
 * Способ заставить parent launch подождать выпполнения дочерних корутин
 * S
 * */
suspend fun launchExample6() {
    val scope = CoroutineScope(Dispatchers.Default)

    val parentCoroutineJob = scope.launch {
        launch {
            delay(1000)
            println("Child coroutine 1 has completed")
        }
        launch {
            delay(1000)
            println("Child coroutine 2 has completed")
        }
    }
//    parentCoroutineJob.join() //заставляет parent launch подождать
    println("Parent coroutine has been completed")
}

/**----------------------------------------------------------------------------------------------------------------------------------------------------*/

/**
 * Async
 * */

/** async запускает отдельную корутину, которая выполняется параллельно с остальными корутинами. Тоже самое что и Launch*
 * Тут важно запомнить что async всеровно начнет выполнение и без await(). Await - это аналог join но только для того, чтобы вернуть результат.
 * Интерфейс Deferred унаследован от интерфейса Job, поэтому для также доступны весь функционал, определенный для интерфейса Job
 * */
suspend fun asyncExample1() {
    val scope = CoroutineScope(Job())
    val deferred = scope.async {
        delay(500L)  // имитация продолжительной работы
        println("Hello work!")
    }
    deferred.join() //Так же может использоваться потому что deferred наследник job
    println("string.StrPool has finished")
}

/** Для получения результата из объекта Deferred применяется функция await(). */
suspend fun asyncExample2() = coroutineScope {
    val message: Deferred<String> = async { getMessage() }
    println("message: ${message.await()}")
    println("string.StrPool has finished")
}

suspend fun getMessage(): String {
    delay(500L)  // имитация продолжительной работы
    return "Hello"
}

/** С помощью async можно запустить несколько корутин, которые будут выполняться параллельно */
suspend fun asyncExample3() = coroutineScope {

    val numDeferred1 = async { sum(1, 2) }
    val numDeferred2 = async { sum(3, 4) }
    val numDeferred3 = async { sum(5, 6) }
    val num1 = numDeferred1.await()
    val num2 = numDeferred2.await()
    val num3 = numDeferred3.await()

    println("number1: $num1  number2: $num2  number3: $num3")
}

suspend fun sum(a: Int, b: Int): Int {
    delay(500L) // имитация продолжительной работы
    return a + b
}

/** Отложенный запуск
 * По умолчанию построитель корутин async создает и сразу же запускает корутину.
 * Но как и при создании корутины с помощью launch для async-корутин можно применять технику отложенного запуска.
 * Только в данном случае корутина запускается не только при вызове метода start объекта Deferred (который унаcледован от интерфейса Job),
 * но также и с помощью метода await() при обращении к результу корутины
 * */

suspend fun asyncExample4() = coroutineScope {
    // корутина создана, но не запущена
    val sum = async(start = CoroutineStart.LAZY) { sumLazy(1, 2) }
//    sum.start()                      // запуск корутины, если необходимо начать выполнение до вызова await()
    delay(1000L)
    println("Actions after the coroutine creation")
    println("sum: ${sum.await()}")   // запуск и выполнение корутины
}

fun sumLazy(a: Int, b: Int): Int {
    println("Coroutine has started")
    return a + b
}

/**
 * todo Дополнить примеры для launch билдера с параметрами Defauld и Atomic и чем это отличается от поведения диспатчера
 * */


/** Полезная техника маппинга списка через async
 * Позволяет выполнить асинхронный запрос на каждом элементе списка и возвращает в результате список с обновленными данными.
 * */
suspend fun coroutineBuilders2Example1() = coroutineScope {
    val idList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val measuredTime = measureTimeMillis {
        val dataList = idList.toMutableList().map { async { getDataById(it) } }.awaitAll()
        println(dataList)
    }
    println(measuredTime)
}

suspend fun getDataById(id: Int): String {
    delay(1000)
    return "Data by id - $id"
}

/** Тоже самое можно сделать с помощью launch
 * Здесь есть отличие от async в том, что если в launch передать start параметр CoroutineStart.Lazy - то асинхронная работа прекратится и все будет
 * выполняться последовательно. Это происходит потому что в launch в это случае Lazy старта необходимо руками вызвать start() на экземпляре job.
 * В Async start вызовется без нас при вызове await. Соответственно чтоб работало нужно руками вызывать start. Смотри закоментированный пример.
 * */
suspend fun coroutineBuilders2Example2() = coroutineScope {
    val idList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val measuredTime = measureTimeMillis {
        idList.toMutableList().map { launch { makeRequestByValue(it) } }.joinAll()
//        idList.toMutableList().map { launch(start = CoroutineStart.LAZY) { makeRequestByValue(it) }.apply { start() } }.joinAll()
    }
    println(measuredTime)
}

suspend fun makeRequestByValue(id: Int) {
    delay(1000)
    println("request for id $id")
}

/** Если мы сделаем 3 корутины с lazy запуском и будем в привычной манере вызывать await - потеряется асинхронное поведение. Время выполнения каждой
 * корутины будет складываться. Это можно исправить, вызвая на каждом async start до того как вызываем await.*/
suspend fun coroutineBuilders2Example3() = coroutineScope {
    val measuredTime = measureTimeMillis {
        val def1 = async(start = CoroutineStart.LAZY) { getDataById(1) }
        val def2 = async(start = CoroutineStart.LAZY) { getDataById(2) }
        val def3 = async(start = CoroutineStart.LAZY) { getDataById(3) }
//        val def1 = async(start = CoroutineStart.LAZY) { getDataById(1) }.apply { start() }
//        val def2 = async(start = CoroutineStart.LAZY) { getDataById(2) }.apply { start() }
//        val def3 = async(start = CoroutineStart.LAZY) { getDataById(3) }.apply { start() }
        println("val1: ${def1.await()}, val2: ${def2.await()}, val3: ${def3.await()}")
    }
    println(measuredTime)
}

/** Coroutine start atomic похож на Default. Различие в том, что корутина с Atomic не может быть отменена до того как начнет свое выполнение*/
@OptIn(ExperimentalCoroutinesApi::class)
suspend fun coroutineBuilders2Example4() = coroutineScope {
    val job1 = launch(start = CoroutineStart.ATOMIC) { bcgWork(500, "work 1") }
    job1.cancel()
    val job2 = launch(start = CoroutineStart.DEFAULT) { bcgWork(500, "work 2") }
    job2.cancel()
    delay(1000)
}

suspend fun bcgWork(workDuration: Long, text: String) {
    println(text)
    delay(workDuration)
}

/** Coroutine start unconfined немедленно выполняет корутину до первого suspend point так же как и корутина запущенная с Unconfined dispather,
 * далее будет переключение потока. Смотри Unconfined диспатчер*/
suspend fun coroutineBuilders2Example5(){
    println("method start work on thread ${Thread.currentThread().name}")
    val scope = CoroutineScope(Job())
    val job1 = scope.launch(start = CoroutineStart.UNDISPATCHED) {
        bcgWork(
            500,
            "coroutine works on {${Thread.currentThread().name}} with context: ${contextToString(coroutineContext)}"
        )
    }
    job1.join()
    delay(100)
    println("method ends work on thread ${Thread.currentThread().name}")
}