package design.patterns.creational.prototype

interface Prototype {
    fun clone(): Prototype // Возвратный тип сам интерфейс
}