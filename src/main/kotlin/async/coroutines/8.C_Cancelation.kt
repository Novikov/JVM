package async.coroutines

import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.suspendCoroutine

suspend fun main() {
//    cancellationExample1()
//    cancellationExample1_1()
//    cancellationExample1_2()
//    cancellationExample1_3()
//    cancellationExample1_4()
//    cancellationExample2()
    cancellationExample3()
//    cancellationExample3_1()
}

/**
 * Корутина может находиться в различных состояниях. Все они перечислены здесь https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/
 * */

/**
 *Пример отмены корутины.
 * job.cancel() не является suspend функцией и может быть вызывана где угодно.
 * Тоже самое можно сделать если вручную создать scope и вызывать cancel на нем.
 * */
suspend fun cancellationExample1() = coroutineScope {
    val job = launch {
        repeat(10) { index ->
            println("operation number $index")
            delay(100)
        }
    }
    delay(450)
    println("canceling coroutine")
    job.cancel()
}

/**
 * Если заменить delay внутри launch на Thread sleep, то отмена не произойдет. Причина в том, что если вызывается suspend функция (например delay)
 * на coroutine scope который уже в состоянии cancelling то будет брошен особый CancellationException, который прервет выполнение корутины.
 * Данный Exception обрабатывать не обязательно, но можно если надо. В примере ниже нет suspend функции поэтому выполнение не прервется.
 * Все suspend функции в библиотеке корутин - cancelable (генерят Cancellation exception если job isActive == false)
 * */
suspend fun cancellationExample1_1() = coroutineScope {
    val job = launch {
        repeat(10) { index ->
            delay(1)
            println("operation number $index")
            Thread.sleep(100)
        }
    }
    delay(250)
    println("canceling coroutine")
    job.cancel()
}

/**
 * Способы остановки работы внутри корутины
 * 1.Вызов функций ensureActive() или suspend функций yield(), delay(). Не уверен что такое поведение имеют все suspend функции. В лекции услышал что delay() стоит в одном ряду с join() await() и lock()
 * 2.Проверка состояния корутины с помощью оборота всего блока в if(isActive)
 * */

suspend fun cancellationExample1_2() = coroutineScope {
    val job = launch {
        repeat(10) { index ->
            ensureActive()
            yield()
            delay(1)
            println("operation number $index")
            Thread.sleep(100)
        }
    }
    delay(250)
    println("canceling coroutine")
    job.cancel()
}

/*** Данный способ имеет преимущество в том, что не выбрасывает мгновенно cancellation exception и позволяет выполнить некоторые cleanUp operations
 * В обычных тредах есть нечто похожее. Метод interrupt() перевод флаг isInterrupted в положение true, на который мы должны завязываться.
 * */
suspend fun cancellationExample1_3() = coroutineScope {
    val job = launch {
        repeat(10) { index ->
            if (isActive) {
                delay(1)
                println("operation number $index")
                Thread.sleep(100)
            } else {
                //perform cleanup operations
                println("Clean up")
                throw CancellationException()
            }
        }
    }
    delay(250)
    println("canceling coroutine")
    job.cancel()
}

/**
 * После того, как выполнился cancel и job корутины перешел в состояние isActive == false - вызов любой функции приведет к выбросу cancellation exception.
 * Поэтому если необходимо выполнить некоторый suspend код (например в блоке else clenUp operations) - оборачиваем все это дело в
 * withContext(NonCancelable)
 * */
suspend fun cancellationExample1_4() = coroutineScope {
    val job = launch {
        repeat(10) { index ->
            if (isActive) {
                delay(1)
                println("operation number $index")
                Thread.sleep(100)
            } else {
                //perform cleanup operations
                withContext(NonCancellable) {
                    delay(100)
                    println("Clean up")
                }
                throw CancellationException()
            }
        }
    }
    delay(250)
    println("canceling coroutine")
    job.cancel()
}

/**
 * TimeOut
 * Есть специальная функция которая генерит наследника cancelation exception если выполнение корутины занимает больше по времени, чем наши ограничения.
 * Есть аналог который возвращает вместо CancelationException - null (withTimeoutOrNull())
 * */
suspend fun cancellationExample2() = coroutineScope {
    launch {
        withTimeout(1300L) {
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
        }
    }
}

/**
 * Cancel and join.
 * Вызов метода join() у job заставляет подождать выполнения того, что ниже пока job не перейдет в состояние completed или canceled.
 * В данное состояние job переходит не сразу. После вызова cancel() - сначала переходит в состояние canceling.
 * Блок finally выполнится только после перехода в состояния canceled.
 * */

suspend fun cancellationExample3() = coroutineScope {
    val job = launch {
        try {
            repeat(1000) { i ->
                println("job: I'm sleeping $i ...")
                delay(500L)
            }
        } finally {
            println("job: I'm running finally")
        }
    }

    job.invokeOnCompletion {
        println("Coroutine has been canceled")
    }

    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancel()
    job.join()

    //вызов двух методов можно заменить одним job.cancelAndJoin()

    println("main: Now I can quit.")
}

suspend fun cancellationExample3_1() = coroutineScope {
    val job = launch {
        try {
            repeat(1000) { i ->
                println("job: I'm sleeping $i ...")
                delay(500L)
            }
        } catch (ex: Exception) {
            throw ex // если отлавливаем cancellation exception чтоб очисть какие-то ресурсы - не забываем прокинуть его дальше чтобы функции выше по иерархии перестали делать лишнюю работу.
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin()
    println("main: Now I can quit.")
}

/**
 * Способы отмены корутины:
 * 1)Если вызвать cancel на scope - отменяются все дочерние job и его более нельзя использовать для запуска корутин.
 * 2)Если нам все-таки нужен данный scope - для работы в будущем - можно вызвать метод cancelChildren(). Он отменит только детей.
 * 3)Для отмены конкретной дочерней корутины нужно сохранить на нее lateinit или нулабельную ссылку. Вызвать на ней cancel и затем занулить в onCleared() viewModel например.
 *
 * Способы закончить работы врутри корутиины
 * 1)Проверка на статус и локальный return
 * 2)Выбросить cancellation exception
 * 3)EnsureActive()/yield()
 *
 * Опции определить отмену корутины
 * 1)Try-catch - отловить отмену локально
 * 2)InvokeOnCompletion - отловить общую отмену корутины / скоупа.
 * */

/**
 * Если мы оборачиваем в suspend какой либо внешний api то есть 2 варианта получения continuation.
 * Не что похожее реализовано на стороне retrofit. Там колбеки которые необходимо обернуть. Об этом в конце лекции про отмену.
 * Второй вариант насколько я понимаю поддерживает возможность отмены, а первый нет. Разобраться если придет задача сделать нечто похожее.
 * */
suspend fun cancellationExample4() {
    suspendCoroutine<Int> {}
    suspendCancellableCoroutine<Int> {}
}


/**Проверить есть ли похожий пример
 * При переопределении Job() нарушается связь родитель наследник.
 * Child с переопределенным job не будет реагировать на отмену parent
 * */
suspend fun cancellationExample5() = coroutineScope{
    // launch a coroutine to process some kind of incoming request
    val request = launch {
        // it spawns two other jobs
        launch(Job()) {
            println("job1: I run in my own Job and execute independently!")
            delay(1000)
            println("job1: I am not affected by cancellation of the request")
        }
        // and the other inherits the parent context
        launch {
            delay(100)
            println("job2: I am a child of the request coroutine")
            delay(1000)
            println("job2: I will not execute this line if my parent request is cancelled")
        }
    }
    delay(500)
    request.cancel() // cancel processing of the request
    println("main: Who has survived request cancellation?")
    delay(1000) // delay the main thread for a second to see what happens
}