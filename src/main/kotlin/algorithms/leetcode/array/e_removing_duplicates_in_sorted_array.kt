package algorithms.leetcode.array

//https://leetcode.com/problems/remove-duplicates-from-sorted-array/description/

fun main() {
    val numbers = arrayOf<String>("1", "1", "2", "3") // массив чисел отсортирован
    val correctLength = removeDuplicates(numbers)
    println(correctLength)
}

/** Используется метод двух указателей, где i на 1 больше чем j*/
fun removeDuplicates(nums: Array<String>): Int {
    var j = 1
    for (i in 1 until nums.size) { // проходом убираем дубликаты
        if (nums[i] != nums[i - 1]) {
            nums[j] = nums[i]
            j++
        }
        println(nums.toList().toString())
    }
    return j
}
// тут фишка в том что помимо количества уникальных элементов нужно изменить содержание массива так чтобы не было дубликатов до последнего числа