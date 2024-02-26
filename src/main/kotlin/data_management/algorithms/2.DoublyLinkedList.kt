package data_management.algorithms


/**
 * Отличия:
 * Node хранит ссылку на предыдущий node;
 * RemoveLast выполняется за O(1);
 * В методах removeFirst()/removeLast() используется temp переменная.
 * */
fun main() {
    val myLinkedList = LinkedList(1)
    myLinkedList.append(2)
    myLinkedList.append(3)
    myLinkedList.reverse()
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

//    fun get(index: Int): DoubleNode<T>? {
//        if (index < 0 || index >= length) return null
//        var temp = head
//        for (i in 0 until index) {
//            temp = temp?.next
//        }
//        return temp
//    }
//
//    fun set(index: Int, value: T): Boolean {
//        var temp = get(index)
//        return if (temp != null) {
//            temp.value = value
//            true
//        } else {
//            false
//        }
//    }
//
//    fun insert(index: Int, value: T): Boolean {
//        if (index < 0 || index > length) return false
//        if (index == 0) {
//            prepend(value)
//            return true
//        }
//        if (index == length) {
//            append(value)
//            return true
//        }
//        val temp = get(index - 1)
//        val newNode = DoubleNode(value)
//        newNode.next = temp!!.next
//        temp.next = newNode
//        length++
//        return true
//    }
//
//    fun remove(index: Int): DoubleNode<T>? {
//        if (index < 0 || index > length) return null
//        if (index == 0) return removeFirst()
//        if (index == length) return removeLast()
//        val prev = get(index - 1)
//        val temp = prev!!.next
//        prev.next = temp!!.next
//        temp.next = null
//        length--
//        return temp
//    }
//
//    fun append(value: T) {
//        val newNode = DoubleNode(value = value)
//        if (length == 0) {
//            head = newNode
//            tail = newNode
//        } else {
//            tail?.next = newNode
//            tail = newNode
//        }
//        length++
//    }
//
//    fun removeLast(): DoubleNode<T>? {
//        if (length == 0) return null
//        var pre = head
//        var temp = head
//        while (temp?.next != null) {
//            pre = temp
//            temp = temp.next
//        }
//        tail = pre
//        tail?.next = null
//        length--
//        if (length == 0) { // Чтобы не уйти в отрицательные значения
//            tail = null
//            head = null
//        }
//        return temp
//    }
//
//    fun prepend(value: T) {
//        val newNode = DoubleNode(value)
//        if (length == 0) {
//            tail = newNode
//            head = newNode
//        } else {
//            newNode.next = head
//            head = newNode
//        }
//        length++
//    }
//
//    fun removeFirst(): DoubleNode<T>? {
//        if (length == 0) return null
//        val temp = head
//        head = head?.next
//        temp?.next = null
//        length--
//        if (length == 0) {
//            tail = null // head не нужно занулять потому что он занулится выше
//        }
//        return temp
//    }

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