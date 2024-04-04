package data_structures.heap


class Heap {
    private var heap: MutableList<Int> = ArrayList()
    fun getHeap(): List<Int> {
        return ArrayList(heap)
    }

    private fun leftChild(index: Int): Int {
        return 2 * index + 1
    }

    private fun rightChild(index: Int): Int {
        return 2 * index + 2
    }

    private fun parent(index: Int): Int {
        return (index - 1) / 2
    }

    private fun swap(index1: Int, index2: Int) {
        val temp = heap[index1]
        heap[index1] = heap[index2]
        heap[index2] = temp
    }

    fun insert(value: Int) {
        heap.add(value)
        var current = heap.size - 1
        while (current > 0 && heap[current] > heap[parent(current)]) {
            swap(current, parent(current))
            current = parent(current)
        }
    }
}