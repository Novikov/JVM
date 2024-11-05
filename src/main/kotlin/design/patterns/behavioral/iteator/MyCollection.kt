package design.patterns.behavioral.iteator

// Коллекция, которая будет хранить элементы
class MyCollection<T>(private val items: List<T>) {
    // Метод для получения итератора
    fun iterator(): MyIterator<T> = MyIterator(items)
}