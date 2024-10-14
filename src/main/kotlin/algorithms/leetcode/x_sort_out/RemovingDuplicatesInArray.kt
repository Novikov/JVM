package algorithms.leetcode.x_sort_out

fun main() {
    val numbers = arrayOf<Int>(1, 1, 2, 3)
    val correctLength = removeDuplicates(numbers)
    println(correctLength)
}

fun removeDuplicates(nums: Array<Int>): Int {
    var j = 1
    for (i in 1 until nums.size) {
        if (nums[i] != nums[i - 1]) {
            nums[j] = nums[i]
            j++
        }
    }
    return j
}