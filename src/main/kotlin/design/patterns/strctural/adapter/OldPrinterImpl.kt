package design.patterns.strctural.adapter

class OldPrinterImpl : OldPrinter {
    override fun printOldMessage(message: String) {
        println("Old Printer: $message")
    }
}