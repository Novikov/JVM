package algorithms.data_structures.list.array_list

/**
    Добавление элемента:
    В конец: O(1) (амортизированное время), так как массив может расширяться.
    В середину или начало: O(n), так как нужно сдвинуть элементы.

    Удаление элемента:
    По индексу: O(n), так как нужно сдвинуть элементы после удаляемого.
    По значению: O(n), так как требуется поиск элемента.

    Поиск элемента:
    По индексу: O(1), так как доступ по индексу мгновенный.
    По значению: O(n), так как нужно пройти все элементы.

    Изменение элемента:
    O(1), доступ по индексу также мгновенный.
    Итерация:

    O(n), так как нужно пройти все элементы.
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