package language.keywords

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {
//    inlineExample1()
//    inlineExample2()
    inlineExample3()
}

/**
 * Слово inline у метода a будет влиять на то встроится его тело в метод b или нет
 * */
fun inlineExample1() {
    b()
}

inline fun a(text: String) {
    println(text)
}

fun b() {
    println("Hello from b")
    a("Hello from a")
}

/**
 * ---------------------------------------------------------------------------------------------------------------------
 * Лямбда превратится в Function0
 * В байткоде видно как создается его экземпляр
 * */
fun inlineExample2() {
    d()
}

fun c(text: () -> String) {
    println(text)
}

fun d(){
    println("Hello from d")
    c{ "HelloFrom c "}
}

/**
 * ---------------------------------------------------------------------------------------------------------------------
 * Тело лямбды подставится в мето вызова. Не происходит создание эксемпляра Function0
 * Уменьшим количество классов в программе и уменьшим нагрузку на сборщик мусора, но пожертвуем размером байткода
 *  */
fun inlineExample3() {
    f()
}

inline fun e(text: () -> String) {
    println(text.invoke())
}

fun f(){
    println("Hello from d")
    e{ "HelloFrom c "}
}


/**
 * Отсутствует возможность вызывать suspend функции внутри lambda
 * */
fun coroutineExample2() {
    val list = listOf(1, 2, 3, 4, 5)
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

fun <T> List<T>.normalForeach(action: (T) -> Unit) {
    for (item in this) {
        action(item)
    }
}


inline fun <T> List<T>.inlinedForeach(action: (T) -> Unit) {
    for (item in this) {
        action(item)
    }
}

//todo
//нелокальные ретерны и ретерны в корутинах через crossinline
