package language.keywords

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {
//    inlineExample1()
//    inlineExample2()
//    inlineExample3()

//    noInlineExample()

    crossInlineExample()

//    coroutineExample()
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

fun d() {
    println("Hello from d")
    c { "HelloFrom c " }
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

fun f() {
    println("Hello from d")
    e { "HelloFrom c " }
}

/**
 * ---------------------------------------------------------------------------------------------------------------------
 * Сама лябда не инлайнится, а тело функции встраивается в место вызова
 *  */
fun noInlineExample() {
    g()
}

inline fun h(noinline text: () -> String) {
    println(text.invoke())
}

fun g() {
    println("Hello from g")
    h { "HelloFrom h " }
}

/*** ---------------------------------------------------------------------------------------------------------------------
Локальный ретерн: Выход из текущей функции. Можно использовать в любых функциях.
Не локальный ретерн: Выход из родительской функции, в которой была вызвана лямбда. Поддерживается только в inline функциях.

crossinline — ключевое слово, которое используется для указания, что лямбда-выражение не может содержать нелокальных return, даже если оно передано в inline-функцию.

Когда мы передаем лямбда-выражение в функцию в качестве параметра, мы можем использовать оператор return внутри лямбды,
чтобы выйти из цикла или функции, в которой вызывается лямбда. Однако, если мы передаем лямбда-выражение в inline-функцию,
код лямбда-выражения может быть вставлен прямо в место вызова функции. В этом случае, если в лямбде используется оператор return,
это может привести к выходу из внешней функции, что не всегда желательно.

более подробное объяснение тут https://www.youtube.com/watch?v=a2jPA_h9ltM&t=195s
**/

fun crossInlineExample() {
    doSomething()
}

fun doSomething(){
    println("Before action")
    firstAction {
        return@doSomething // если будет использоваться crossInline то компилятор запретит делать нелокальный ретерн
        "Hello from a"
    }
}

inline fun firstAction(crossinline text: () -> String) { // сотри crossInline и посмотри что изменится
    secondAction { text() }
}

fun secondAction(text: () -> String){
    println("??? ${text}")
}

/**
 * Отсутствует возможность вызывать suspend функции внутри lambda
 * */
fun coroutineExample() {
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
