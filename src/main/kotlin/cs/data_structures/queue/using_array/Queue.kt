package cs.data_structures.queue.using_array

class Queue <T> {
    private val list = arrayListOf<T>()

    val count: Int
        get() = list.size

    val isEmpty: Boolean
        get() = list.size == 0

    fun peek(): T? = list.getOrNull(0)

    fun enqueue(element: T): Boolean {
        list.add(element)
        return true
    }

    fun dequeue(): T? =
        if (isEmpty) null else list.removeAt(0)

    override fun toString(): String {
        return list.toString()
    }
}