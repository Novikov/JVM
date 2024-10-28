package concurrency.libs.coroutines

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

suspend fun main() {
//    routineExample()
//    coroutineExample()
 //   threadSwitchingExample()
 //  suspendExample()
 //   asynchExample()
 //   blockingCoroutinesExample()

    underTheHoodExample()
}

/**
 * In computer programming, a function or subroutine (when it doesn't return a value) is a sequence of program instructions that performs a specific task,
 * packaged as a unit. This unit can then be used in programs wherever that particular task should be performed.
 * Метод это понятие связанное с классом и ООП.
 * */


/** Когда происходит вызов routine1 - control flow передается в routine1. После выполнения subroutine поток выполнения передается обратно в routineExample() */
fun routineExample() {
    println("main starts")
    routine(1, 300)
    routine(2, 500)
    println("main ends")
}

fun routine(number: Int, delay: Long) {
    println("Routine $number starts work")
    Thread.sleep(delay)
    println("Routine $number has finished")
}

/** В данном случае control flow передастся в coroutine1, но в тот момент когда он дойдет до suspend функции delay - поток выполнения будет отдан для coroutine2
 * coroutine2 запустится до того, выполнится coroutine1
 *
 * В этом кроется смысл корутин. Приставка co обозначает cooperative. Т.е возможность одной рутины передавать поток управления для другой пока первая
 * находится в состоянии suspended. В этом есть выйгрыш по производительности по сравнению с тем же RX. Там поток вызова функции сохраняется
 * */
suspend fun coroutineExample() = coroutineScope {
    println("main starts")
    joinAll(
        launch { coroutine(1, 500) },
        launch { coroutine(2, 300) }
    )
    println("main ends")
}

suspend fun coroutine(number: Int, delay: Long) {
    println("Routine $number starts work")
    delay(delay)
    println("Routine $number has finished")
}

/**
 * Определение из документации
 * A coroutine is an instance of suspendable computation. It is conceptually similar to a thread, in the sense that it takes a block of code to run
 * that works concurrently with the rest of the code. However, a coroutine is not bound to any particular thread. It may suspend its execution in one
 * thread and resume in another one.

 * Coroutines can be thought of as light-weight threads, but there is a number of important differences that make their real-life usage very different from threads.
 * */

/**
 * Любая корутина может выполняться только на coroutineScope. Способы получения scope в 11.Scope_Builders. Scope имеет контекст состоящий из 4 параметров:
 * Job, Dispatcher, ErrorHandler, CoroutineName.*/

/**
 * Suspend
 * Модификатор suspend не делает функцию асинхронной, но suspend функции можно вызывать только из других suspend функций или из переходника runblocking.
 * suspend обозначает что функция может приостановить свое выполнение и отдать control flow другой функции. После чего suspend может возобновить свое выполнение
 * и завершиться на другом потоке. Корутина выполняющая suspend функцию не привязана к конкретному потоку.
 * */

suspend fun threadSwitchingExample() = coroutineScope {
    println("Hello from ${Thread.currentThread().name}")
    withContext(Dispatchers.Default) {// Попробуй поменять на Unconfined
        println("Hello from ${Thread.currentThread().name}")
    }

    println("Welcome back to ${Thread.currentThread().name}")
}

/** Само по себе слово suspend не делает работу асинхронной. Функции будут засыпать на указанное время и выполняться последовательно*/
suspend fun suspendExample() {
    val time = measureTimeMillis {
        val one = doSomethingUsefulOne()
        val two = doSomethingUsefulTwo()
        println("The answer is ${one + two}")
    }
    println("time $time")
}

suspend fun doSomethingUsefulOne(): Int {
    delay(1500L) // pretend we are doing something useful here
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}

/** Пример асинхронного выполнения. */
suspend fun asynchExample() {
    val time = measureTimeMillis {
        coroutineScope {
            val one = async { doSomethingUsefulOne() }
            val two = async { doSomethingUsefulTwo() }
            println("The answer is ${one.await() + two.await()}")
        }
    }
    println("time $time")
}

/**
 * Корутины никак не блокируют друг друга. Что это значит?
 * */
private fun blockingCoroutinesExample() {
    val scope = CoroutineScope(Job())
    println("onRun, start")

    scope.launch {
        println("coroutine, start ${Thread.currentThread().name}")
        delay(1000)
        println("coroutine, end ${Thread.currentThread().name}")
    }

    println("onRun, middle")

    scope.launch {
        println("coroutine2, start ${Thread.currentThread().name}")
        delay(1000)
        println("coroutine2, end ${Thread.currentThread().name}")
    }

    println("onRun, end")

    Thread.sleep(3000)
}

/**
 * На каждый вызов корутины (suspend функции через корутин bilder в байткоде) создается:
 * 1. Класс-обертка для корутины
 * Когда вы объявляете suspend функцию или используете корутинный билдер (например, launch или async),
 * компилятор создает класс-обертку, который реализует интерфейс Continuation.
 * Этот класс содержит состояние корутины и необходимую информацию для её возобновления.
 *
 * 2. Метод invokeSuspend
 * Каждая suspend функция генерирует метод invokeSuspend, который отвечает за выполнение логики функции,
 * включая управление её состоянием. Этот метод включает в себя логику для переключения между состояниями,
 * что может выглядеть как switch-case конструкция, в зависимости от количества точек приостановки в функции.
 *
 * 3.Coroutine context
 * При создании корутины также создается объект CoroutineContext, который содержит информацию о контексте
 * выполнения (например, диспетчеры, родительские корутины и так далее).
 *
 * Под декомпиляцией мы ожидаем получить нечто похожее на это:
 * public final Object invokeSuspend(Object $result) {
 *     switch (label) {
 *         case 0:
 *             label = 1;
 *             return delay(1000);
 *         case 1:
 *             System.out.println("Завершено!");
 *             return Unit.INSTANCE;
 *     }
 * }
 *
 * По идее критерием разбиения на case конструкции являются точки где функция может быть приостановлена или блоки обработки ошибок и исключений
 * Но при просмотре байткода код после delay вообще уходит из switch ниже. Т.е компилятор сам оптимизирует этот код
 *
 * Почему нельзя вызывать suspend функцию в обычной функции?
 * Потому что suspend функция должна выполняться асинхронно. Нужено сделать переход из блокирующего мира в асинхронный.
 * Для корректного выполнения suspend функции нужен Coroutine context и класс обертка который реализует Continuation с методом invokeSuspend
 * Этого можно добиться только если вызывать suspend функцию внутри Coroutine builder.
 * */

fun underTheHoodExample() = runBlocking{
    launch {
        exampleFunction()
    }
}

suspend fun exampleFunction() {
    println("Начало")
    exampleFunction2()
    delay(1000)
    println("Завершено!")
}

suspend fun exampleFunction2(){
    println("Начало 2 функции")
    println("Конец 2 функции")
}
