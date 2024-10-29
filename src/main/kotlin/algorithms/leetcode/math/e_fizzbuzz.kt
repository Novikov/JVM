package algorithms.leetcode.math

import algorithms.data_structures.list.linked_list.LinkedList

//https://leetcode.com/problems/fizz-buzz/description/

fun main() {
    val stringList = fizzBuzz(10)
    println(stringList)
}

fun fizzBuzz(n: Int): List<String> {
    val resultArr = mutableListOf<String>()
    for(element in 1..n) {
        val mod3 = element % 3 == 0
        val mod5 = element % 5 == 0
        val result = when {
            mod5 && mod3 -> "FizzBuzz"
            mod5 -> "Buzz"
            mod3 -> "Fizz"
            else -> "$element"
        }
        resultArr.add(result)
    }
    return resultArr
}

/**
 *     // Проход в цикле до n
 *     // В каждой итерации проверяем остаток от деления
 *     // Двойное условие FizzBuzz
 *     // Если остаток от деления на 3 равен 0 то печатаем fizz
 *     // Если остаток от деления на 5 равен 0 то печатаем buzz
 *     // Иначе печатаем index
 * */