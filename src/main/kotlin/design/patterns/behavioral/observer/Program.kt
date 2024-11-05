package design.patterns.behavioral.observer
/**
 * Позволяет объекту оповещать другие объекты об изменениях своего состояния.
 * */
fun main(){
    // Создаем наблюдаемый объект
    val subject = Subject()

    // Создаем несколько наблюдателей
    val observer1 = ConcreteObserver("Наблюдатель 1")
    val observer2 = ConcreteObserver("Наблюдатель 2")
    val observer3 = ConcreteObserver("Наблюдатель 3")

    // Добавляем наблюдателей к наблюдаемому объекту
    subject.addObserver(observer1)
    subject.addObserver(observer2)
    subject.addObserver(observer3)

    // Уведомляем всех наблюдателей
    subject.notifyObservers("Событие произошло!")

    // Удаляем одного наблюдателя
    subject.removeObserver(observer2)

    // Уведомляем оставшихся наблюдателей
    subject.notifyObservers("Другое событие произошло!")
}