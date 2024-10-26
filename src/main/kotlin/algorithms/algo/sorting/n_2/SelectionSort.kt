package algorithms.algo.sorting.n_2

fun main(){
    val array = arrayOf(64, 34, 25, 12)
    println("Исходный массив: ${array.joinToString(", ")}")
    array.selectionSort()
    println("Отсортированный массив: ${array.joinToString(", ")}")
}

/**
 * Находим минимальный элемент и меняем его с первым
 * Увеличиваем индекс прохода по массиву на 1 и снова ищем минимальный элемент но уже с индекса 1
 * меняем менмальный элемент со вторым элементом и так до конца
 * */

fun <T: Comparable<T>> Array<T>.selectionSort(){
    for (index in 0 until size - 1){
        val minIndex = this.findMinElement(index, size - 1)
        val tmp = this[index]
        this[index] = this[minIndex]
        this[minIndex] = tmp
    }
}

fun <T: Comparable<T>> Array<T>.findMinElement(startIndex: Int, endIndex: Int): Int {
    var minIndex = startIndex
    var minValue = this[startIndex]
    for (i in startIndex + 1..<endIndex){
        if (this[i] < minValue){
            minIndex = i
            minValue = this[i]
        }
    }
    return minIndex
}