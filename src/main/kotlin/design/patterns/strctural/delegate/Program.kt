package design.patterns.strctural.delegate

/**
 * Возможность изменить поведение конкретного экземпляра объекта вместо создания нового класса путём наследования.
 *
 * Android
 * Пример реального использования делегирования можно найти в View,
 * где используются различные делегаты для управления состояниями и поведением UI элементов.
 * Рассмотрим использование View и View.OnClickListener, где Android делегирует обработку кликов на другой объект через паттерн Delegate.
 * */

fun main(){
    // Создаем объект реального принтера
    val consolePrinter = ConsolePrinter()

    // Используем делегирование для делегирования вызовов на реальный объект
    val userPrinter = UserPrinter(consolePrinter)

    // Вызов метода, который делегируется объекту ConsolePrinter
    userPrinter.print("Hello, Kotlin!")
}

