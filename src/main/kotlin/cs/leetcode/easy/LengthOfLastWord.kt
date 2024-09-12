package cs.leetcode.easy

fun main() {
    val length = lengthOfLastWord("Hello world")
}


// Можно оптимизировать сразу возвращаясь назад справа
fun lengthOfLastWord(s: String): Int {
    // после слова может быть пробел
    // критерий конца строки это отсутствие символа слова до пробела и после пробела
    // найдя конец строки можно пройтись циклом назад по строке и полностью взять слово
    // затем возвращаем его длину
    val spaceSymbol = ' '
    var lastWordIndex = 0
    var firstWordIndex = 0
    for (i in 0 until s.length) {
        if (s[i] != spaceSymbol) {
            lastWordIndex = i
        }
    }
    firstWordIndex = lastWordIndex
    for (i in lastWordIndex downTo 0) {
        if (s[i] != spaceSymbol) {
            firstWordIndex = i
        } else {
            firstWordIndex = i
            break
        }
    }

    return lastWordIndex - firstWordIndex
}
