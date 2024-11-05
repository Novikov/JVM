package design.patterns.strctural.adapter

// Адаптер для старого принтера
class PrinterAdapter(private val oldPrinter: OldPrinter) : Printer {
    override fun printMessage(message: String) {
        // Делегируем вызов методу старого принтера
        oldPrinter.printOldMessage(message)
    }
}