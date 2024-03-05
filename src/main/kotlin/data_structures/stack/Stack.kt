package data_structures.stack

class Stack<T> {
    private var top: Node<T>?
    private var height: Int

    constructor(value: T) {
        val newNode = Node(value)
        top = newNode
        height = 1
    }

    fun getTop(): Node<T>? {
        return top
    }

    fun getHeight(): Int {
        return height
    }

    fun makeEmpty() {
        top = null
        height = 0
    }

    fun printStack() {
        var temp: Node<*>? = top
        while (temp != null) {
            println(temp.value)
            temp = temp.next
        }
    }

    fun printAll() {
        if (height == 0) {
            println("Top: null")
        } else {
            println("Top: " + top!!.value)
        }
        println("Height:$height")
        println("\nStack:")
        if (height == 0) {
            println("empty")
        } else {
            printStack()
        }
    }

    fun push(value: T) {
        val newNode = Node(value)
        if (height == 0) {
            top = newNode
        } else {
            newNode.next = top
            top = newNode
        }
        height++
    }
}