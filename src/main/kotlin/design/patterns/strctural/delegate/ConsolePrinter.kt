package design.patterns.strctural.delegate

// Класс, который выполняет реальную печать
class ConsolePrinter : Printer {
    override fun print(message: String) {
        println("Printing to console: $message")
    }
}