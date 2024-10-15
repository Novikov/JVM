package algorithms.leetcode.array

// https://leetcode.com/problems/plus-one/

fun main() {
 val plusOne1 = plusOne(intArrayOf(1,9,9))
}

fun plusOne(digits: IntArray): IntArray {
    var index = digits.lastIndex
    var digitsCopy = digits

    // Сначала обрабатываем кейс девятки на конце или когда массив содержит все девятки
    while (digitsCopy[index] == 9){
        digitsCopy[index] = 0
        index--
        if (index < 0) {
            digitsCopy = IntArray(digitsCopy.size + 1)
            digitsCopy[0] = 1
            break
        }
    }

    // Затем обрабатываем кейс инкремента числа после девяток если их нет или до девяток
    if (index >= 0){
        digitsCopy[index]++
    }

    return digitsCopy
}