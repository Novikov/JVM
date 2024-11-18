package algorithms.leetcode.array

fun main(){
    val numsArray = intArrayOf(-1)
    val k = 3
    rotate(numsArray, k)
}

fun IntArray.printList(){
    println("\n")
    this.forEach {
        print(it)
    }
}

fun rotate(nums: IntArray, k: Int) {
    val n = nums.size

    // Если массив пустой или k равен нулю, ничего не делаем
    if (n == 0 || k == 0) return

    // Приводим k к значению, меньше чем размер массива
    val effectiveK = k % n

    // Если effectiveK == 0, то массив уже в нужном состоянии

    if (effectiveK == 0) return
    swap(nums,0,nums.lastIndex)
    swap(nums,0, effectiveK - 1)
    swap(nums,effectiveK,nums.lastIndex)
}

fun swap(nums: IntArray, start: Int, end: Int) {
    var startIndex  = start
    var endIndex = end
    while (startIndex < endIndex) {
        if(startIndex > nums.lastIndex || endIndex < 0 ) return
        val tmp = nums[endIndex]
        nums[endIndex] = nums[startIndex]
        nums[startIndex] = tmp
        startIndex++
        endIndex--
    }
}