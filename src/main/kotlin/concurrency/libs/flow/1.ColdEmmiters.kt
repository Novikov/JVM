package concurrency.libs.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.*

suspend fun main() {
    builderExample()

    //   flowExample1()
    //  flowExample2()
    //  flowExample3()
//    flowExample4()

    flowContextExample()
}

suspend fun builderExample() {
    // Если нужно эмитить значения то используем оператор flow{ }
    val flow = flow {
        emit("a")
        emit("b")
        emit("c")
    }

    // Создаем Flow из набора значений
    val flow2 = listOf("a", "b", "c").asFlow()

    val flow3 = flowOf("a", "b", "c")
}

/**
 * Flow является холодным источником данных. Он для каждого получателя будет генерировать данные заново.
 * Обычно suspend функция возвращает нам одно значение. И пока мы ждем это значение, корутина приостанавливается.
 * Flow позволяет расширить это поведение. Он делает так, что мы можем получать последовательность (поток) данных вместо одного значения.
 * И это будет происходит в suspend режиме.Т.е. корутина будет приостанавливаться на время ожидания каждого элемента.
 * */
suspend fun flowExample1() {
    getData().onEach { println(it) }.collect()
}

fun getData(): Flow<Int> {
    return flow {
        for (i in 0..10) {
            delay(1000)
            emit(i)
        }
    }
}

/**
 * Выпуск значений flow должен быть в одной корутине иначе будет crash. Пример ниже упадет.
 * */
suspend fun flowExample2() {
    wrongWayGetData().onEach { println(it) }.collect()
}

fun wrongWayGetData(): Flow<Int> {
    return flow {
        coroutineScope {
            launch {
                delay(1000)
                emit(1)
            }

            launch {
                delay(1000)
                emit(2)
            }

            launch {
                delay(1000)
                emit(3)
            }
        }
    }
}

/**
 * Основная проблема, с которой мы столкнулись в прошлом примере - это то, что в блоке flow нельзя вызывать emit из другой корутины.
 * Т.е. можно запустить другую корутину и создать там данные, а вот отправить их оттуда получателю методом emit нельзя, т.к., тем самым, мы меняем корутину, в которой будет запущен код получателя (блок collect).
 * Это значит, что нам надо вызов emit вернуть в нашу текущую корутину.
 * Есть способ решить данную проблему. Для этого используется channel.
 * */

suspend fun flowExample3() {
    getDataRightWay().onEach { println(it) }.collect()
}

suspend fun getDataRightWay(): Flow<Int> {
    return flow {
        coroutineScope {
            val channel = produce<Int> {
                launch {
                    delay(1000)
                    send(1)
                }
                launch {
                    delay(1000)
                    send(2)
                }
                launch {
                    delay(1000)
                    send(3)
                }
            }
            channel.consumeEach {
                emit(it)
            }
        }
    }
}

/**
 * Но есть внутренний оператор, который позволит добиться того же еффекта что и при использовании Channel - ChannelFlow.
 * */

suspend fun flowExample4() {
    getDataChannelFlow().onEach { println(it) }.collect()
}

suspend fun getDataChannelFlow(): Flow<Int> {
    return channelFlow {
        launch {
            delay(1000)
            send(1)
        }
        launch {
            delay(1000)
            send(2)
        }
        launch {
            delay(1000)
            send(3)
        }
    }
}

/**
 * ---------------------------------Context----------------------------------------------------------
 * */

suspend fun getSmallFlow() = flow {
    for (i in 1..10) {
        //withContext(..){} // если обернуть код ниже в withContext - бдует краш.
        emit(i)
        println(currentCoroutineContext())
        delay(500)
    }
}

/** Flow примет контекст того места откуда был вызван collect. Это называется context preservation (Сохранение контекста)
 * Если это будет не так (Например обернуть эмиссию с помощью withContext) - будет краш
 * Контекст нужно менять особым образом, а не так как мы привыкли в корутинах*/
suspend fun flowContextExample() = coroutineScope {
    withContext(CoroutineName("Caller method context") + Dispatchers.Default) {
        getSmallFlow().collect {
            println(it)
        }
    }
}

/**Если добавим оператор flowOn - исключения не будет. Это единственный способ изменить поток на котором будет происходить эмиссия, с условием того
 * что наблюдать будем на другом потоке*/
suspend fun getSmallFlowException() = flow {
    for (i in 1..10) {
        //withContext(..){} // если обернуть код ниже в withContext - бдует краш.
        emit(i)
        println(currentCoroutineContext())
        delay(500)
    }
}.flowOn(Dispatchers.IO)
