package cs.data_structures.old.stack_queue

internal class Queue<T> {
    var first: Node<T>?
    var last: Node<T>?
    var length = 0

    constructor(value: T) {
        val newNode = Node(value)
        first = newNode
        last = newNode
        length = 1
    }


    @JvmName("getFirstQueue")
    fun getFirst(): Node<T>? {
        return first
    }

    @JvmName("getLastQueue")
    fun getLast(): Node<T>? {
        return last
    }

    @JvmName("getLengthQueue")
    fun getLength(): Int {
        return length
    }


    fun printQueue() {
        var temp = first
        while (temp != null) {
            println(temp.value)
            temp = temp.next
        }
    }

    fun printAllQueue() {
        if (length == 0) {
            println("First: null")
            println("Last: null")
        } else {
            println("First: " + first!!.value)
            println("Last: " + last!!.value)
        }
        println("Length:$length")
        println("\nQueue:")
        if (length == 0) {
            println("empty")
        } else {
            printQueue()
        }
    }

    @JvmName("makeEmptyQueue")
    fun makeEmpty() {
        first = null
        last = null
        length = 0
    }

    fun enqueue(value: T) {
        val newNode = Node(value)
        if (length == 0) {
            first = newNode
            last = newNode
        } else {
            last?.next = newNode
            last = newNode
        }
        length++
    }

    fun dequeue(): Node<T>? {
        if (length == 0) return null
        val temp = first
        if (length == 1) {
            first = null
            last = null
        } else {
            first = temp?.next
            temp?.next = null
        }
        length--
        return temp
    }

}