package algorithms.leetcode.array

/**
 * Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.
 *
 * You may assume that each input would have exactly one solution, and you may not use the same element twice.
 *
 * You can return the answer in any order.
 *
 * Example 1:
 *
 * Input: nums = [2,7,11,15], target = 9
 * Output: [0,1]
 * Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].
 * Example 2:
 *
 * Input: nums = [3,2,4], target = 6
 * Output: [1,2]
 * Example 3:
 *
 * Input: nums = [3,3], target = 6
 * Output: [0,1]
 *
 *
 * Constraints:
 *
 * 2 <= nums.length <= 104
 * -109 <= nums[i] <= 109
 * -109 <= target <= 109
 * Only one valid answer exists.
 * */

fun main() {
    val nums = intArrayOf(3, 2, 4)
    val target = 6
    val twoSum = twoSum2(nums, target)
    twoSum.forEach { println(it) }
}

// N2 time
fun twoSum(nums: IntArray, target: Int): IntArray {
    for (first in nums.indices) {
        for (second in nums.indices) {
            if (first == second) break
            val sum = nums[first] + nums[second]
            if (sum == target) {
                return intArrayOf(first, second)
            }
        }
    }
    throw IllegalArgumentException("No two sum solution")
}

fun twoSumElegant(nums: IntArray, target: Int): IntArray {
    // Создаем hashmap для хранения чисел и их индексов
    val numToIndex = mutableMapOf<Int, Int>()

    // Проходим по массиву nums с индексами
    for ((index, num) in nums.withIndex()) {
    // Вычисляем необходимое значение для достижения целевого (target) числа
        val difference = target - num

        // Проверяем, есть ли это значение (complement) уже в хэш-карте
        if (numToIndex.containsKey(difference)) {
            // Если да, возвращаем массив с индексами: индекс complement и текущий индекс
            return intArrayOf(numToIndex[difference]!!, index)
        }

        // Если complement не найден, добавляем текущее число и его индекс в HashMap
        numToIndex[num] = index
    }

    throw IllegalArgumentException("No two sum solution")
}


    fun twoSum2(nums: IntArray, target: Int): IntArray {
        //Пройтись по массиву и считать difference
        //Если difference + currentDigit == target вернуть индексы
        //HashMap используем потому чтобы уйти от n^2

        val digitsMap = mutableMapOf<Int, Int>() // digit, index

        //Заполняем HashMap
        nums.forEachIndexed { index, digit ->
            digitsMap[digit] = index
        }

       val resultArr = IntArray(2)

       nums.forEachIndexed { index, currentDigit ->
            val difference = target - currentDigit
           if (digitsMap.keys.contains(difference) ) {
                val differenceIndex = digitsMap[difference] ?: 0
               return intArrayOf(index, differenceIndex)
            }
        }

        return resultArr
    }
