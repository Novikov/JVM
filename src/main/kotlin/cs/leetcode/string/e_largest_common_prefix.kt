package cs.leetcode.string

//https://leetcode.com/problems/longest-common-prefix/description/

fun main(){
    //Нужно понимать как работает indexOf. Если есть вхождение то вернется его позиция или -1
//    val searchedString = "StringTest"
//    val searchedPrefix = "Test"
//    val invalidString = "ASD"
//    println(searchedString.indexOf(searchedPrefix))
//    println(searchedString.indexOf(invalidString))
//    println(searchedString.indexOf(searchedString))

    val arr = arrayOf("world", "word", "wow")
    println("result "+longestCommonPrefix(arr))
}

fun longestCommonPrefix(strs: Array<String>): String {
    if (strs.isEmpty()) return ""
    //Решаем через постоянное сравнение префикса и слова пока префиксы не будут совпадать
    var commonPrefix = strs.first()

    for (index in strs.indices) {//Проход по словам от 0 до 2
        while (strs[index].indexOf(commonPrefix) != 0) { // 0 будет только если префикс содержится в слове в остальных случаях будет -1. Если будет числа большие 0 то это значит что префикс вообще то не префикс и не подходит под условия нашей задачи
            commonPrefix = commonPrefix.substring(0, commonPrefix.length - 1)
            if (commonPrefix.isEmpty()) return ""
        }
    }

    return commonPrefix
}