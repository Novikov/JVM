package algorithms.algo.searching

/**
 *  The collection must be able to perform index manipulation in constant time.
 *  Kotlin collections that can do this include the Array and the ArrayList.
 *  The collection must be sorted.
 * */
fun main(){
    val array = arrayListOf(1, 5, 15, 17, 19, 22, 24, 31, 105,
        150)
    val search31 = array.indexOf(31)
    val binarySearch31 = array.binarySearch(31)
    println("indexOf(): $search31")
    println("binarySearch(): $binarySearch31")
}

fun <T : Comparable<T>> ArrayList<T>.binarySearch(
    value: T,
    range: IntRange = indices
): Int? {
    //Проверка на заполненность Range
    if (range.first > range.last) {
        return null
    }
    //Поиск серединного элемента с учетом Range
    val size = range.last - range.first + 1
    val middle = range.first + size / 2

    return when {
        this[middle] == value -> middle
        this[middle] > value -> binarySearch(value, range.first until middle)
        else -> binarySearch(value, (middle + 1)..range.last)
    }
}

fun binarySearch(arr: IntArray, target: Int, left: Int, right: Int): Int {
    if (left > right) {
        return -1 // Элемент не найден
    }

    val mid = left + (right - left) / 2

    return when {
        arr[mid] == target -> mid // Элемент найден
        arr[mid] > target -> binarySearch(arr, target, left, mid - 1) // Поиск в левой половине
        else -> binarySearch(arr, target, mid + 1, right) // Поиск в правой половине
    }
}