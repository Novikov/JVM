package cs.data_structures.list.linked_list.jvm

import cs.data_structures.list.linked_list.Node

class LinkedListJvm<T> : Iterable<T>, Collection<T>, MutableIterable<T>, MutableCollection<T> {
    private var head: Node<T>? = null
    private var tail: Node<T>? = null
    override var size = 0
        private set

    /** Collection 2 метода*/
    override fun containsAll(elements: Collection<T>): Boolean {
        for (searched in elements) {
            if (!contains(searched)) return false // пробегаемся по всем элементам и проверяем наличие
        }
        return true
    }

    override fun contains(element: T): Boolean {
        for (item in this) {
            if (item == element) return true // пробегаемся по всем элементам и проверяем наличие
        }
        return false
    }

    override fun isEmpty(): Boolean {
        return size == 0
    }

    /** Вставка значений*/
    fun push(value: T) {
        head = Node(
            value = value,
            next = head
        ) // Создаем новый Node у которого next будет старый head. После этого перевычисляем head
        if (tail == null) {
            tail = head // Вычисляем tail
        }
        size++
    }

    // Улучшенная версия для chain-вызова
    fun push2(value: T): LinkedListJvm<T> {
        head = Node(value = value, next = head)
        if (tail == null) {
            tail = head
        }
        size++
        return this
    }

    fun append(value: T) {
        if (isEmpty()) {
            push(value)
            return
        }
        tail?.next = Node(value)
        tail = tail?.next
        size++
    }

    fun insert(value: T, afterNode: Node<T>): Node<T> {
        if (tail == afterNode) {
            append(value)
            return tail!!
        }
        val newNode = Node(value = value, next = afterNode.next)
        afterNode.next = newNode
        size++
        return newNode
    }

    //Поисковой метод. Нужен для insert/remove по индексу операции
    fun nodeAt(index: Int): Node<T>? {
        var currentNode = head
        var currentIndex = 0
        while (currentNode != null && currentIndex < index) {
            currentNode = currentNode.next
            currentIndex++
        }
        return currentNode
    }

    /** удаление значений*/
    fun pop(): T? {
        if (!isEmpty()) size--
        val result = head?.value
        head = head?.next
        if (isEmpty()) {
            tail = null
        }
        return result
    }

    // будет вычисляться за O(n)
    fun removeLast(): T? {
        // 1
        val head = head ?: return null
        // 2
        if (head.next == null) return pop()
        // 3
        size--

        // Если мы удаляем последний элемент то необходимо найти предыдущий
        var prev = head
        var current = head
        var next = current.next
        while (next != null) {
            prev = current
            current = next
            next = current.next
        }
        // 5
        prev.next = null
        tail = prev
        return current.value
    }

    fun removeAfter(removingNode: Node<T>): T? {
        val result = removingNode.next?.value // Сохраняем удаляемое значение для последующего возврата
        //Если мы выбрали предпоследний элемент то меняем tail
        if (removingNode.next == tail) {
            tail = removingNode
        }
        //Уменьшаем размер всегда только если удаляемый node не последний т.к ничего не поменяется
        if (removingNode.next != null) {
            size--
        }
        removingNode.next = removingNode.next?.next
        return result
    }

    /** MutableIterable<T>*/

    override fun iterator(): MutableIterator<T> {
        return LinkedListIterator(this)
    }

    /** Методы MutableCollection*/

    override fun add(element: T): Boolean {
        append(element)
        return true
    }

    override fun addAll(elements: Collection<T>): Boolean {
        for (element in elements) {
            append(element)
        }
        return true
    }

    override fun clear() {
        head = null
        tail = null
        size = 0
    }

    override fun remove(element: T): Boolean {
        val iterator = iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            if (item == element) {
                iterator.remove()
                return true
            }
        }
        return false
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        var result = false
        for (item in elements) {
            result = remove(item) || result
        }
        return result
    }

    // Удаляет все элементы списка кроме тех что переданы в качестве параметра
    override fun retainAll(elements: Collection<T>): Boolean {
        var result = false
        val iterator = this.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            if (!elements.contains(item)) {
                iterator.remove()
                result = true
            }
        }
        return result
    }


    override fun toString(): String {
        if (isEmpty()) {
            return "Empty list"
        } else {
            return head.toString()
        }
    }
}