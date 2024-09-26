package cs.data_structures.list.array_list

/**
 * Доступ по индексу O(1)
 * Поиск O(n)
 * Вставка O(n) (Осутствуют методы вставки в начало и конец списка)
 * Удаление O(n)
 * */

class ArrayList<T> {
    private var elements: Array<Any?> = arrayOfNulls<Any?>(10)
    private var size = 0

    fun add(element: T) {
        ensureCapacity()
        elements[size++] = element
    }

    fun get(index: Int): T {
        checkIndex(index)
        return elements[index] as T
    }

    fun remove(index: Int): T {
        checkIndex(index)
        val removedElement = elements[index] as T
        for (i in index until size - 1) { //двигаем элементы на ячейку влево
            elements[i] = elements[i + 1]
        }
        elements[--size] = null // Убираем ссылку на удаленный элемент и занижаем индекс
        return removedElement
    }

    fun size(): Int {
        return size
    }

    fun contains(element: T): Boolean {
        for (i in 0 until size) {
            if (elements[i] == element) {
                return true
            }
        }
        return false
    }

    private fun ensureCapacity() {
        if (size >= elements.size) {
            val newSize = elements.size * 2
            elements = elements.copyOf(newSize)
        }
    }

    private fun checkIndex(index: Int) {
        if (index < 0 || index >= size) {
            throw IndexOutOfBoundsException("Index: $index, Size: $size")
        }
    }

    fun printList() {
        for (i in 0 until size) {
            print("${elements[i]} ")
        }
        println()
    }
}