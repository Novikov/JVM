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