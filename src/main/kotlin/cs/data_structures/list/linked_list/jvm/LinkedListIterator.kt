package cs.data_structures.list.linked_list.jvm

import cs.data_structures.list.linked_list.Node

class LinkedListIterator<T>(private val list: LinkedListJvm<T>) : Iterator<T>, MutableIterator<T> {
    private var index = 0
    private var lastNode: Node<T>? = null

    override fun next(): T {
        if (index >= list.size) throw IndexOutOfBoundsException() // кейс который никогда не должен возникнуть т.к есть проверака hasnext
        lastNode = if (index == 0) {
            list.nodeAt(0) // это такой способ итерации, я бы всегда брал через индекс
        } else {
            lastNode?.next
        }
        index++
        return lastNode!!.value
    }

    // Реализация MutableIterator интерфейса, нужна для удаления элементов через итератор во время прохода по коллекции
    override fun remove() {
        if (index == 1) {
            list.pop()
        } else {
            val prevNode = list.nodeAt(index - 2) ?: return
            list.removeAfter(prevNode)
            lastNode = prevNode
        }
        index--
    }

    //Сообщает о том достигли ли мы конца коллекции
    override fun hasNext(): Boolean {
        return index < list.size
    }
}