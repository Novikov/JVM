package algorithms.leetcode.string
//https://leetcode.com/problems/valid-parentheses/description/

fun main(){
    val isValidBrackets = isValid("[()]")
    println(isValidBrackets)
}

fun isValid(s: String): Boolean {
    //Делаем таблицу соответствий
    val bracketsMap = HashMap<Char, Char>()
    bracketsMap.put('{', '}')
    bracketsMap.put('(', ')')
    bracketsMap.put('[', ']')

    var isValid = true
    for (leftIndex in 0 until s.lastIndex/2) {
        val rightIndex = s.lastIndex - leftIndex
        val leftBracket = s[leftIndex]
        val rightBracketExpected = bracketsMap.get(leftBracket)
        val currentRightBracket = s[rightIndex]
        if (rightBracketExpected != currentRightBracket) {
            isValid = false
            break
        }
    }

    return isValid
}