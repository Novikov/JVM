package algorithms.leetcode.string

//https://leetcode.com/problems/valid-anagram/description/

fun main() {
    val s = "rat"
    val t = "car"
    println(isAnagram(s, t))
}

fun isAnagram(s: String, t: String): Boolean {
    val counterMap = hashMapOf<Char, Int>()

    //Считаем совпадения
    for(char in s){
        counterMap[char] = counterMap.getOrDefault(char, 0) + 1
    }

    //Вычитаем совпадения
    for (char in t){
        counterMap[char] = counterMap.getOrDefault(char, 0) - 1
    }

    return counterMap.values.all {counter -> counter == 0 }
}