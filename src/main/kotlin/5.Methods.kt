@file:JvmName("MyFileName")

import kotlin.reflect.KClass

fun main() {
    reifiedExample<String>()

    println("\n ------------- \n")

    printType(String::class)
}

/** Top level method. Вызов данного метода из java как статического из данного файла с добавленным окончанием kt
 * Если хотим изменить имя файла используем аннотацию @file:JvmName(NewName) */
fun catMethod(): String = "Hello from top level method!"

/** Extension method, где String это Тип получатель, а this - объект получатель.
 * Посмотри откуда берется this через decompiler. (Параметр) */
fun String.printSmth() = this.length - 1

class TextExtensionsClass() {
    val a = 1
    private val b = 2
    internal val c = 3
}

/** Extensions method не позволяют нарушать правил инкапсуляции. В отличие от методов, объявленных в классе
 * Функции расширения не имею доступа к закрытым членам класса*/
fun TextExtensionsClass.newExtension() {
//    this.b
}

/** Можно делать extensions не только на тип класс, но и тип коллекцию параметризованною каким то классом. Так же можно делать свойства расширения*/