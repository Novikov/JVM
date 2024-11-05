package design.patterns.behavioral.observer

// Наблюдаемый объект (Subject)
class Subject {
    private val observers = mutableListOf<Observer>()

    // Добавить наблюдателя
    fun addObserver(observer: Observer) {
        observers.add(observer)
    }

    // Удалить наблюдателя
    fun removeObserver(observer: Observer) {
        observers.remove(observer)
    }

    // Уведомить всех наблюдателей
    fun notifyObservers(message: String) {
        for (observer in observers) {
            observer.update(message)
        }
    }
}