import kotlin.reflect.KProperty

fun main() {
    val docha = Docha()
    docha.sound()
    println(docha.state)
}

//Посмотри как выглядит под декомпилятором

/** class delegator
 * Благодаря нему можно вынести переопределение некоторых методов интерфейса в отдельный класс не раздувая основной.
 * Это можно использовать например в активити, чтоб она не разрасталась*/
interface Animal {
    fun sound()
}

class Cat : Animal {
    override fun sound() {
        println("Mey")
    }
}

class Docha : Animal by Cat() {
    val state by MyFirstDelegate()
}

/** field delegation
 * Можно вынести логику инициализации свойства.
 * В примерах часто показаны делегаты свойств которые переопределяют интерфейс ReadWriteProperty, но это не обязательно
 * Главное чтоб были методы с правильной сигнатурой*/
class MyFirstDelegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }
}
