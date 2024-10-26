package concurrency.libs.coroutines

import kotlinx.coroutines.*
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.coroutines.Continuation
import kotlin.coroutines.suspendCoroutine

fun main() {
    underTheHoodExample()
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