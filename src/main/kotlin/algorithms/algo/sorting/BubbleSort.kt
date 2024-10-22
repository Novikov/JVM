package algorithms.algo.sorting

fun main() {
    val list = arrayListOf(9, 4, 10, 3)
    println("Original: $list")
    list.bubbleSort(true)
    println("Bubble sorted: $list")
}

fun <T> ArrayList<T>.swapAt(first: Int, second: Int) {
    val tmp = this[first]
    this[first] = this[second]
    this[second] = tmp
}

fun <T : Comparable<T>> ArrayList<T>.bubbleSort(
    showPasses: Boolean = false
) {
    if (this.size == 1) return
    for (end in (1 until this.size).reversed()) { // Проход по индексам массива с конца
        var swapped = false
        for (current in 0 until end) { // Проход по индексам массива сначала и выталкивание наибольшего значения в конец
            if (this[current] > this[current + 1]) {
                this.swapAt(current, current + 1)
                swapped = true
            }
        }
        if (showPasses) println(this)
        if (!swapped) return
    }
}