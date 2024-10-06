package cs.algorithms.searching

fun main(){
    val array = intArrayOf(64, 34, 25, 12)
    println("Исходный массив: ${array.joinToString(", ")}")

    bubbleSort(array)

    println("Отсортированный массив: ${array.joinToString(", ")}")
}

fun bubbleSort(arr: IntArray) {
    val n = arr.size
    var swapped: Boolean

    for (i in 0 until n - 1) {
        swapped = false
        for (j in 0 until n - i - 1) {
            if (arr[j] > arr[j + 1]) {
                // Меняем элементы местами
                val temp = arr[j]
                arr[j] = arr[j + 1]
                arr[j + 1] = temp
                swapped = true

                println("iterartion ${arr.joinToString(", ")}")
            }
        }
        // Если за проход не было обменов, массив уже отсортирован
        if (!swapped) break
    }
}