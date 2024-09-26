package cs.data_structures.queue.using_array

class Stack<T: Any> {
    private val storage = arrayListOf<T>()

    val count: Int
        get () = storage.size

    val isEmpty: Boolean
        get() = count == 0

    fun push(element: T) {
        storage.add(element)
    }

    fun pop(): T? {
        if (storage.size == 0) {
            return null
        }
        return storage.removeAt(storage.size - 1)
    }

    override fun toString() = buildString {
        println(storage.toString())
    }
}