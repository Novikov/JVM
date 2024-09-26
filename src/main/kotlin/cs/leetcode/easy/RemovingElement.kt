package cs.leetcode.easy

fun main() {
    val a1 = removeElement(intArrayOf(3, 2, 5), 3)
    println(a1)
}

fun removeElement(nums: IntArray, removingNumber: Int): Int {
    var index = 0
    for (i in 0 until nums.size) {
        if (nums[i] != removingNumber) {
            nums[index] = nums[i] //Если элемент массива не совпадает с удаляемым, то сдвигаем его вливо и увеличиваем индекс
            index++
        }
    }
    val remainingElements = index
    return remainingElements
}

fun countBits(n: Int): IntArray {
    // Массив для хранения количества единиц для каждого числа
    val result = IntArray(n + 1)

    for (i in 1..n) {
        // Используем формулу: result[i] = result[i / 2] + (i % 2)
        result[i] = result[i shr 1] + (i and 1)
    }

    return result
}