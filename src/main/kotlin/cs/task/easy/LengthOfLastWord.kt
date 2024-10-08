package cs.task.easy


fun main() {
    val length = lengthOfLastWord("Hello world")
    println(length)
}

fun lengthOfLastWord(s: String): Int {
    val trimmedString = s.trim()
    var length = 0
    for (i in trimmedString.length - 1 downTo 0) {
        if (trimmedString[i] != ' ') {
            length++
        } else if (length > 0) {
            break
        }
    }
    return length
}
