package data_management.algorithms


/**
 * Отличия:
 * Node хранит ссылку на предыдущий node и за счет этого некоторые операции выполняются за O(1), а некоторые за O(n),
 * но более эффективно потому что итерация будет начинаться с более удобного края;
 * RemoveLast выполняется за O(1);
 * В методах removeFirst()/removeLast() используется temp переменная.
 * */
fun main() {
    val myLinkedList = LinkedList(1)
    myLinkedList.append(2)
    myLinkedList.append(3)
    myLinkedList.getInfo()
    myLinkedList.insert(1, 5)
    myLinkedList.getInfo()
}


data class DoubleNode<T>(var value: T, var next: DoubleNode<T>? = null, var prev: DoubleNode<T>? = null)

class DoubleLinkedList<T> {
    private var head: DoubleNode<T>? = null
    private var tail: DoubleNode<T>? = null
    private var length = 0

    constructor(value: T) {
        val newNode = DoubleNode(value = value)
        head = newNode
        tail = newNode
        length = 1
    }

    //Более эффективный get за счет выбора позиции начала итериации. От говоры или от хвоста
    fun get(index: Int): DoubleNode<T>? {
        if (index < 0 || index >= length) {
            return null
        }
        var temp = head
        if (index < length / 2) {
            for (i in 0 until index) {
                temp = temp!!.next
            }
        } else {
            temp = tail
            for (j in length - 1 downTo index + 1) {
                temp = temp?.prev
            }
        }
        return temp
    }

    fun set(index: Int, value: T): Boolean {
        val temp = get(index)
        return if (temp != null) {
            temp.value = value
            true
        } else {
            false
        }
    }

    fun insert(index: Int, value: T): Boolean {
        if (index < 0 || index > length) {
            return false
        }
        if (index == 0) {
            prepend(value)
            return true
        }
        if (index == length) {
            append(value)
            return true
        }
        val newNode = DoubleNode(value)
        val before = get(index - 1)
        val after = before?.next
        newNode.prev = before
        newNode.next = after
        after?.prev = newNode
        before?.next = newNode
        length++
        return true
    }

    //Более эффективный способ через одну переменную без использование before и after
    fun remove(index: Int): DoubleNode<T>? {
        if (index < 0 || index >= length) {
            return null
        }
        if (index == 0) {
            return removeFirst()
        }
        if (index == length - 1) {
            return removeLast()
        }
        val temp = get(index)
        temp?.next?.prev = temp?.prev
        temp?.prev?.next = temp?.next
        temp?.next = null
        temp?.prev = null
        length--
        return temp
    }

    fun append(value: T) {
        val newNode = DoubleNode(value)
        if (length == 0) {
            head = newNode
            tail = newNode
        } else {
            tail?.next = newNode
            newNode.prev = tail
            tail = newNode
        }
        length++
    }

    fun removeLast(): DoubleNode<T>? {
        if (length == 0) return null
        val temp = tail
        if (length == 1) {
            head = null
            tail = null
        } else {
            tail = tail?.prev
            tail?.next = null
            temp?.prev = null
        }
        length--
        return temp
    }

    fun prepend(value: T) {
        val newNode = DoubleNode(value)
        if (length == 0) {
            head = newNode
            tail = newNode
        } else {
            newNode.next = head
            head?.prev = newNode
            head = newNode
        }
        length++
    }

    fun removeFirst(): DoubleNode<T>? {
        if (length == 0) return null
        val temp = head
        if (length == 1) {
            head = null
            tail = null
        } else {
            head = head!!.next
            head?.prev = null
            temp?.next = null
        }
        length--
        return temp
    }

    fun printList() {
        var temp = head
        while (temp != null) {
            println(temp.value)
            temp = temp.next
        }
    }

    fun getHead() {
        println("Head: " + head?.value)
    }

    fun getTail() {
        println("Tail: " + tail?.value)
    }

    fun getLength() {
        println("Length: $length")
    }

    fun getInfo() {
        println("Length: $length, Head: ${head?.value}, Tail: ${tail?.value}}")
        printList()
    }

    fun reverse() {
        var temp = head
        head = tail
        tail = temp
        var after = temp?.next
        var before: DoubleNode<T>? = null
        for (i in 0..<length) {
            after = temp?.next
            temp?.next = before
            before = temp
            temp = after
        }
    }
}