package design.patterns.behavioral.mediator

// Интерфейс медиатора
interface Mediator {
    fun sendMessage(message: String, colleague: Colleague)
}