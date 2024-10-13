package cs.leetcode.math

//https://leetcode.com/problems/reverse-integer/

fun main() {
    val reversedInteger = reverse(2147483647)
    println(reversedInteger)
}

fun reverse(digit: Int): Int {
    var reversedDigit = 0
    var leftPartDigit = digit

    while (leftPartDigit!= 0){
        val lastDigit = leftPartDigit % 10 // получаем последнее число через остаток от деления
        leftPartDigit = leftPartDigit / 10 // получаем левую часть числа без последнего

        // Проверка на переполнение происходит до добавления нового числа
        if (reversedDigit > Int.MAX_VALUE / 10 || reversedDigit == Int.MAX_VALUE / 10 && lastDigit > 7) {
            return 0 // Произошло переполнение
        }

        if (reversedDigit < Int.MIN_VALUE / 10 || reversedDigit == Int.MIN_VALUE / 10 && lastDigit < -8) {
            return 0 // Произошло переполнение
        }


        reversedDigit = reversedDigit * 10 + lastDigit
    }

    return reversedDigit
}