package design.patterns.strctural.delegate

// Интерфейс, который будет делегировать вызовы метода
interface Printer {
    fun print(message: String)
}