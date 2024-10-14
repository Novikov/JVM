package algorithms.leetcode.x_sort_out

import java.util.*

/** Given an array nums of size n, return the majority element.
The majority element is the element that appears more than ⌊n / 2⌋ times.
You may assume that the majority element always exists in the array.
n/2 это формула по которой можно вычислить элемент который встречается большинство раз.
например 2233322 7/2 = 3.5 Значит элемент большинства должен встречаться как минимум 4 раза и это 2
 */

fun main() {
    val array = arrayOf(2,2,3,3,3,2,2).toIntArray()
    val majority = majorityElement(array)
    println(majority)
}

fun majorityElement(nums: IntArray): Int {
    Arrays.sort(nums)
    val n: Int = nums.size
    return nums[n / 2]
}