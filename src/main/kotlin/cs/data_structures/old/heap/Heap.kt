package cs.data_structures.old.heap


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

    private fun sinkDown(index: Int) {
        var index = index
        var maxIndex = index
        while (true) {
            val leftIndex = leftChild(index)
            val rightIndex = rightChild(index)
            if (leftIndex < heap.size && heap[leftIndex] > heap[maxIndex]) {
                maxIndex = leftIndex
            }
            if (rightIndex < heap.size && heap[rightIndex] > heap[maxIndex]) {
                maxIndex = rightIndex
            }
            index = if (maxIndex != index) {
                swap(index, maxIndex)
                maxIndex
            } else {
                return
            }
        }
    }

    //Обрати внимание remove без значения. Он всегда удалит максимальный элемент.
    fun remove(): Int? {
        if (heap.size == 0) {
            return null
        }
        if (heap.size == 1) {
            return heap.removeAt(0)
        }
        val maxValue = heap[0]
        heap[0] = heap.removeAt(heap.size - 1)
        sinkDown(0)
        return maxValue
    }
}