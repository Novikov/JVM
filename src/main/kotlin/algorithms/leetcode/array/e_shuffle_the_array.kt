package algorithms.leetcode.array

//https://leetcode.com/problems/shuffle-the-array/
fun main(){
val arr = intArrayOf(1,2,3,4,5,6,7,8)
    val shuffledArr = shuffle(arr, 3)
    println(shuffledArr.toList().toString())
}

fun shuffle(nums: IntArray, step: Int): IntArray {
    val resultArray = IntArray(nums.size)
    var leftSubsetPointer = 0
    var rightSubsetPointer = step
    for (index in 1..nums.lastIndex step 2){
        resultArray[index - 1] = nums[leftSubsetPointer] // заполняем первый элемент
        resultArray[index] = nums[rightSubsetPointer] //Заполняем второй элемент
        leftSubsetPointer++
        rightSubsetPointer++
    }
    return resultArray
}