package design.patterns.behavioral.observer

// Конкретный наблюдатель
class ConcreteObserver(private val name: String) : Observer {
    override fun update(message: String) {
        println("$name получил сообщение: $message")
    }
}