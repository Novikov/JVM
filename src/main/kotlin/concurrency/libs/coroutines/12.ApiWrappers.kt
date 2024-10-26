package concurrency.libs.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.coroutines.suspendCoroutine

fun main() {

}

/** Иногда возникает необходимость обернуть callback api в suspend функции.
 * Такие задачи часто встречаются у разработчиков API которые хотят подружить его с корутинами
 * и обернуть в suspend функции. Для этого необходимо получить continuation и не забыть возобновить
 * работку корутины иначе она потеряется.
 * */

suspend fun suspendApiCallExample() {
    val scope = CoroutineScope(Dispatchers.Unconfined)

    scope.launch() {
        println("start coroutine ${Thread.currentThread().name}")
        val data = getDataFromApi()
        println("end coroutine ${Thread.currentThread().name}")
    }

    delay(2000)
}

suspend fun getDataFromApi(): Int = suspendCoroutine {
    println("suspend function, start")
    thread {
        println("suspend function, background work")
        TimeUnit.MILLISECONDS.sleep(1000)
//        it.resume(5) // Возобновление работы корутины. Если не вызвать то корутина не завершится
    }
}

// todo Разобрать все функции которые лежат рядом с suspendCoroutine из примера выше. Я так понимаю они все нужны для обертки Api в suspend