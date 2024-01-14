package async.coroutines

import kotlinx.coroutines.*

suspend fun main() {
//    dispatcherExample1()
//    dispatcherExample2()
//    dispatcherExample3()
    dispatcherExample4()
//    dispatcherExample4_1()
//    dispatcherExample5()
}

/**
 * Очень важный момент касательно разделения потоков. Код внутри launch будет выполняться на собственном потоке, а код внутри coroutineScope
 * на отдельном.
 * */
suspend fun dispatcherExample1() = coroutineScope {
    launch {
        println("Корутина выполняется на потоке: ${Thread.currentThread().name}")
    }
    println("Функция checkThread выполняется на потоке: ${Thread.currentThread().name}")
}

/** Мы сами можем задать диспатчер передав его в билдер*/
suspend fun dispatcherExample2() = coroutineScope {
    launch(Dispatchers.Default) {
        // явным образом определяем диспетчер Dispatcher.Default
        println("Корутина выполняется на потоке: ${Thread.currentThread().name}")
    }
    println("Функция main выполняется на потоке: ${Thread.currentThread().name}")
}

/**
 * При указании Dispatcher мы определяем границы потоков в которых будет выполняться работа.
 * Default - число потоков ограничено ядрами процессора
 * IO - Число потоков до 64 или числу ядер процессора
 * Default используем для высоконагруженных операций, IO не требуют больших затрат CPU поэтому мы их можем создать больше количество.
 * Они не будут ждать друг друга. Если мы будем выполнять тяжелые вычисления в IO то это создаст большую нагрузку на CPU.
 * Важный момеент - при завершении операции. Т.е когда придет ответ - корутина может сменить поток.
 */

/** Main dispatcher можно использовать для выполнения операций на главном потоке. Т.к suspend функции не блокируют поток то это не будет
 * тормозить UI.
 * Смены потока при завершении операции не будет т.к у Main диспатчера только один поток.
 * Т.е мы запускаем операцию на главном потоке. Например, запрос в сеть. Функция приостановится, дожидаясь результата. В это время UI поток
 * продолжит заниматься отрисовкой UI. После прихода результата - функция возобновляется и выполняет операции на главном потоке.
 * */

/** Методы Dispatcher
 * 1)isDispatcherNeeded() - возвращает true если работа корутины должно быть выполнена с помощью dispatch метода. Все диспатчеры кроме unconfined
 * вернут true.
 * 2)dispatch() - отвечает за выполнение переданного callback в другом потоке. Он вызовется самостоятельно корутиной. Из-за вызова dispath() произойдет
 * смена потока. Соответственно выполнение Unconfined диспатчера произойдет в этом же потоке.
 * */
@OptIn(ExperimentalStdlibApi::class)
suspend fun dispatcherExample3() = coroutineScope {
    launch {
        println("${this.coroutineContext[CoroutineDispatcher]?.isDispatchNeeded(this.coroutineContext)}")
    }.join()
}

/**
 * Unconfined (Неограниченный) dispatcher
 * Работа корутины будет начата на потоке вызывающей функции, но только до первого suspend point.
 * После suspend point работа корутины будет возобновлена в потоке, полностью определенным вызванной suspend функцией (Там всегда будет DefaultExecutor)
 * Этот default executor приходит из delay функции.
 * */

suspend fun dispatcherExample4() = coroutineScope {
    launch(Dispatchers.Unconfined) { // not confined -- will work with main thread
        println("Unconfined      : I'm working in thread ${Thread.currentThread().name}")
        delay(500)
        println("Unconfined      : After delay in thread ${Thread.currentThread().name}")
    }
    launch { // context of the parent, main runBlocking coroutine
        println("main runBlocking: I'm working in thread ${Thread.currentThread().name}")
        delay(1000)
        println("main runBlocking: After delay in thread ${Thread.currentThread().name}")
    }
}

/**
 * The unconfined dispatcher is an advanced mechanism that can be helpful in certain corner cases where dispatching of a coroutine for its execution
 * later is not needed or produces undesirable side-effects, because some operation in a coroutine must be performed right away.
 * The unconfined dispatcher should not be used in general code.
 * */

/** Можно создать диспатчер с помощью метода newSingleThreadContext().
 * Вся работа будет выполнена на новом потоке*/
@OptIn(DelicateCoroutinesApi::class)
suspend fun dispatcherExample5() = coroutineScope {
    println("method start ${Thread.currentThread().name}")

    launch(newSingleThreadContext("New thread")) {
        println("coroutine start ${Thread.currentThread().name}")
        delay(300)
        println("coroutine end ${Thread.currentThread().name}")
    }.join()

    println("method end ${Thread.currentThread().name}")
}

