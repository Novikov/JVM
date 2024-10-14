package algorithms.leetcode.math
//https://leetcode.com/problems/palindrome-number/description/

fun main(){
val isPalindrome = isPalindrome(12)
    println("$isPalindrome")
}

fun isPalindrome(digit: Int): Boolean {
    if (digit < 0) return false

    var reversedDigit = 0
    var copyOfDigit = digit

    while (copyOfDigit != 0) {
        val rightPartDigit = copyOfDigit % 10
        copyOfDigit = copyOfDigit / 10
        reversedDigit = reversedDigit * 10 + rightPartDigit
    }

    return digit == reversedDigit
}