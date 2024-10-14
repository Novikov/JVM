package language

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main(){
//    simpleInlineExample1()
//    coroutineExample2()
 //   genericsExample3()
}

/**
 * Lambda это небольшие фрагменты кода которые можно передавать другим функциям.
 * Под decompile создается аноимный класс? Его не видно
 * Улучшаем производительность путем ухода от создания анонимных классов, но жертвуем размером байткода.
 * Ее вызов встраивается в место вызова. Смотри функцию main
 * Убираем накладные расходы по вызову частоиспользуемых функций
 * */
fun simpleInlineExample1(){
    val list = listOf(1,2,3,4,5)
    list.normalForeach { println(it) }
    list.inlinedForeach { println(it) }
}

fun <T> List<T>.normalForeach(action: (T) -> Unit) {
    for (item in this){
        action(item)
    }
}


inline fun <T> List<T>.inlinedForeach(action: (T) -> Unit) {
    for (item in this){
        action(item)
    }
}

/**
 * Отсутствует возможность вызывать suspend функции внутри lambda
 * */
fun coroutineExample2(){
    val list = listOf(1,2,3,4,5)
    CoroutineScope(Dispatchers.Default).launch {
        delay(1000)
        list.normalForeach {
            //delay(1000) //doesn't work
            println(it)
        }
        list.inlinedForeach {
            delay(1000) // works
            println(it)
        }
    }

    Thread.sleep(3000)
}


fun genericsExample3(){
    "".printClassName()
}

/** Получаем доступ к имени класса из-за встраивания типа в место вызова*/
inline fun <reified T>T.printClassName(){
    println(T::class.simpleName)
}

//todo
//нелокальные ретерны и ретерны в корутинах через crossinline
