package concurrency.libs.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.coroutines.suspendCoroutine

suspend fun main(){
    suspendApiCallExample()
}

/**
 * Тут расписать что такое Continuation и suspendCoroutine функция ||| ДОРАБОТАТЬ
 * Сейчас понимание следующее suspendCoroutine - это особый билдер для API. Например его могут использовать
 * разработчики Retrofit для того чтобы сделать свои методы suspend.
 *
 * Внутри coroutineScope есть объект continuation который используется для возобновления работы корутины, но
 * startAndroid говорит что это класс в который конвертнется suspend функция в Java. Я смотрел байткод и такого не увидел.
 * */

/**
 * Способы реализации асинхронщины в Android:
 * 1)Callback функции
 * 2)RX
 * 3)Croroutines
 *
 * Иногда возникает необходимость обернуть 1 подход в suspend функции. Такие задачи часто встречаются у разработчиков API которые хотят подружить его с
 * корутинами и обернуть в suspend функции. Для этого необходимо получить continuation и не забыть возобновить работку корутины иначе она потеряется.
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

// TODO:  Расписать почему suspend функцию можно вызвать только из suspend функции или корутины?
/** Подсказка. В работе я всегда вызываю функции Retrofit, но не вижу всей картины.
 * Нужно сделать запрос в сеть без retrofit и тогда все будет ясно.
 * */