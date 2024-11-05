package design.patterns.behavioral.iteator

// Итератор для обхода коллекции
class MyIterator<T>(private val items: List<T>) : Iterator<T> {
    private var index = 0

    // Проверяем, есть ли следующий элемент
    override fun hasNext(): Boolean = index < items.size

    // Возвращаем следующий элемент и увеличиваем индекс
    override fun next(): T {
        if (!hasNext()) {
            throw NoSuchElementException("No more elements in the collection")
        }
        return items[index++]
    }
}