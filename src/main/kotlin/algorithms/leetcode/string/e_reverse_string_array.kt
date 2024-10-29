package algorithms.leetcode.string

//https://leetcode.com/problems/reverse-string/description/

fun main(){
    val charArray = charArrayOf('h','e','l','l','o')
    val reversed = reverseString(charArray)
}

fun reverseString(s: CharArray): Unit {
    var firstIndex = 0
    for (secondIndex in s.lastIndex downTo 0) {
        if (firstIndex >= secondIndex) {
            return
        }
        val tmp = s[firstIndex]
        s[firstIndex] = s[secondIndex]
        s[secondIndex] = tmp
        firstIndex++
        println(s)
    }
}