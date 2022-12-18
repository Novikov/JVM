import kotlin.reflect.KClass

fun main() {
    val highOrderClass = HighOrderClass()
    highOrderClass.noInlineHighOrderMethodExample { 5 }
    highOrderClass.noInlineHighOrderMethodExample { 5 }

    //Данный код будет встраиваться прям в функцию main без создания объектов под каждую лямбду
    highOrderClass.inlineHighOrderMethodExample { 5 }
    highOrderClass.inlineHighOrderMethodExample { 5 }

    //------------------------

    printType(String::class)

    //Тоже самое. Ключевое слово inline встраивает тип в место вызова и позволяет обойти ограничения types erasure
    printReifiedType<String>()
}

/** High Order methods */
class HighOrderClass() {
    fun noInlineHighOrderMethodExample(callback: (Int) -> Int): Int {
        callback.invoke(5)
        return 5
    }

    inline fun inlineHighOrderMethodExample(callback: (Int) -> Int): Int {
        callback.invoke(10)
        return 10
    }
}

fun <T : Any> printType(type: KClass<T>) {
    println("type is $type")
}

inline fun <reified T> printReifiedType() {
    println(T::class.java)
}