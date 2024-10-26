package algorithms.algo.sorting.nlogn


fun main() {
    val array = arrayOf(64, 34, 25, 12, 49, 54, 13, 1, 2, 0)
    println("Исходный массив: ${array.joinToString(", ")}")
    array.quickSort(0, array.size - 1)
    println("Отсортированный массив: ${array.joinToString(", ")}")
}

/**
 *
 * */

fun <T : Comparable<T>> Array<T>.quickSort(startIndex: Int, endIndex: Int) {
    if (startIndex < endIndex) {
        val divideIndex: Int = findPartitionIndexAndSwap(startIndex, endIndex)
        quickSort(startIndex, divideIndex - 1)
        quickSort(divideIndex, endIndex)
    }
}


fun <T : Comparable<T>> Array<T>.findPartitionIndexAndSwap(startIndex: Int, endIndex: Int): Int {
    var leftIndex = startIndex
    var rightIndex = endIndex

    val pivot = this[startIndex + (endIndex - startIndex) / 2] // опорный элемент (не середина). в некоторых алгоритмах выбирают первый элемент

    while (leftIndex <= rightIndex) {
        while (this[leftIndex] < pivot) { // Поиск любого элемента который меньше pivot для последующего swap
            leftIndex++
        }

        while (this[rightIndex] > pivot) { // Поиск любого элемента который больше pivot для последующего swap
            rightIndex--
        }

        if (leftIndex <= rightIndex) {
            this.swap(rightIndex, leftIndex);
            leftIndex++;
            rightIndex--;
        }
    }
    return leftIndex // разбиение остановится на элементе pivot todo Посмотреть другие реализации (Не уверен)
}

fun <T : Comparable<T>> Array<T>.swap(index1: Int, index2: Int) {
    val tmp = this[index1]
    this[index1] = this[index2]
    this[index2] = tmp
}