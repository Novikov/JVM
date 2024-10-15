package algorithms.leetcode.string
//https://leetcode.com/problems/valid-parentheses/description/

fun main(){
    val isValidBrackets = isValid("[()]")
    println(isValidBrackets)
}

fun isValid(s: String): Boolean {
    //Делаем таблицу соответствий
    val bracketMap = mapOf('(' to ')', '{' to '}', '[' to ']')
    val stack = mutableListOf<Char>()

    for (char in s) {
        if (bracketMap.containsKey(char)) {// Если это открывающая скобка, добавляем в стек
            stack.add(char)
        } else if (bracketMap.containsValue(char)) { // Если это закрывающая скобка
            if (stack.isEmpty()) return false// проверяем на пустоту

            val lastBracketInStack = stack.removeAt(stack.size - 1) // берем последний элемент стека предварительно удалив его
            val isBracketValid = bracketMap[lastBracketInStack] == char // проверяем что соответствие по открытой скобке (закрытая скобка) равно текущей закрытой кнопке
            if (!isBracketValid) return false
        }
    }
    // Если стек пуст, все скобки закрыты корректно
    return stack.isEmpty()
}