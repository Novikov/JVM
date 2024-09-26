package cs.data_structures.list.linked_list

/**
 * Доступ по индексу O(n)
 * Поиск O(n)
 *
 * Вставка в середину O(n) (За счет поиска нужной позиции. Иногда эту операцию выносят в отдельную функцию)
 * Вставка в начало O(1)
 * Вставка в конец O(1)
 *
 * Удаление c начала 0(1)
 * Удаленние с середины и конца 0(n) т.к нужно найти предыдущий элемент
 * */

class LinkedList<T> {
    private var head: Node<T>? = null
    private var tail: Node<T>? = null
    private var size = 0

    fun isEmpty(): Boolean {
        return size == 0
    }

    /** Вставка значений*/
    fun push(value: T) {
        head = Node(value = value, next = head) // Создаем новый Node у которого next будет старый head. После этого перевычисляем head
        if (tail == null) {
            tail = head // Вычисляем tail
        }
        size++
    }

    // Улучшенная версия для chain-вызова
    fun push2(value: T): LinkedList<T> {
        head = Node(value = value, next = head)
        if (tail == null) {
            tail = head
        }
        size++
        return this
    }

    fun append(value: T){
        if (isEmpty()){
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


    override fun toString(): String {
        if (isEmpty()) {
            return "Empty list"
        } else {
            return head.toString()
        }
    }
}