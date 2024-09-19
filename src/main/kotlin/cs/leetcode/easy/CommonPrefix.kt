package cs.leetcode.easy

fun main(){
    val arr = arrayOf("world", "word")
    println(longestCommonPrefix(arr))
}

fun longestCommonPrefix(strs: Array<String>): String {
    // Проверяем, пуст ли массив строк
    if (strs.isEmpty()) return ""

    // Проходим по индексам символов первой строки
    for (i in strs[0].indices) {
        // Сравниваем текущий символ первой строки с символами остальных строк
        for (s in strs) {
            // Проверяем, если индекс выходит за границы текущей строки
            // или символы не совпадают
            if (i >= s.length || s[i] != strs[0][i]) {
                // Возвращаем общий префикс до текущего индекса
                return strs[0].substring(0, i)
            }
        }
    }

    // Если мы прошли все символы первой строки, она является общим префиксом
    return strs[0]
}