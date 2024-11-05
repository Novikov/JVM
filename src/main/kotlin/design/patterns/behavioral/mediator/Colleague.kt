package design.patterns.behavioral.mediator

// Абстрактный класс коллеги
abstract class Colleague(val mediator: Mediator) {
    abstract fun send(message: String)
    abstract fun receive(message: String)
}