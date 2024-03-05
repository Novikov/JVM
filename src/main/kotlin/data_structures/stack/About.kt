package data_structures.stack

/**
 * Stack LIFO структура данных. Для реализации используется ArrayList.
 * Вместо HEAD и TAIL указателей используется только один TOP.
 * Вместо Length переменной используется height.
 * Добавление (push) и удаление(pop) первого элемента занимает время O(1).
 * */

fun main() {
    val stack = Stack(1)
    stack.push(2)
    stack.push(3)
    stack.push(4)
    stack.push(5)
    stack.printStack()
}