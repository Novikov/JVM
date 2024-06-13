package cs.leetcode

fun main() {
    val a1 = removeElement(intArrayOf(3, 2, 2, 3), 3)
    println(a1)
}

fun removeElement(nums: IntArray, `val`: Int): Int {
    val resultArray = nums.filter { it != `val` }
    println(resultArray)
    return resultArray.size
}

fun removeElement2(nums: IntArray, `val`: Int): Int {
    var count = 0
    var start = 0
    var end = nums.size - 1

    while (start <= end) {
        if (nums[start] == `val`) {
            count++
            //swap
            nums[start] = nums[end].also {
                nums[end] = nums[start]
            }
            end--
        } else {
            start++
        }
    }

    println(nums.toList().toString())
    return nums.size - count
}