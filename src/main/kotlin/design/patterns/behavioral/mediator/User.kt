package design.patterns.behavioral.mediator

// Конкретная реализация коллеги
class User(private val name: String, mediator: Mediator) : Colleague(mediator) {
    override fun send(message: String) {
        println("$name отправил сообщение: $message")
        mediator.sendMessage(message, this)
    }

    override fun receive(message: String) {
        println("$name получил сообщение: $message")
    }

    fun getName(): String = name
}