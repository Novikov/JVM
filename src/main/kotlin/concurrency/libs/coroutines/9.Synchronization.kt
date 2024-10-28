package concurrency.libs.coroutines

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

suspend fun main(){
    //synchExample()
    //synchExample2()
    synchExample3()
}
/**
 * Отсутствие синхронизации
 * */
suspend fun synchExample() = coroutineScope {
    val commonResource = CommonResource()
    val job1 = List(100) {
        launch{
            repeat(1_000) {
                commonResource.counter++
            }
        }
    }
    job1.joinAll()
    println(commonResource.counter)
}

class CommonResource(){
    var counter: Int = 0
    val mutex = Mutex()
}

/**
 * Через Mutex
 * */
suspend fun synchExample2() = coroutineScope {
    val commonResource = CommonResource()
    val job1 = List(100) {
        launch{
            repeat(1_000) {
                commonResource.mutex.withLock {
                    commonResource.counter++
                }
            }
        }
    }
    job1.joinAll()
    println(commonResource.counter)
}

/**
 * Через 1 поток
 * */
suspend fun synchExample3() = coroutineScope {
    val context = newSingleThreadContext("counter")
    val commonResource = CommonResource()
    val job1 = List(100) {
        launch(context){
            repeat(1_000) {
                    commonResource.counter++

            }
        }
    }
    job1.joinAll()
    println(commonResource.counter)
}

/**
 * Есть еще способ через Producer и Actor из Channels.
 * todo Расписать
 * */

