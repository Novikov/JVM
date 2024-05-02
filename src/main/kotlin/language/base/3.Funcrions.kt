package language.base

import kotlin.random.Random

fun main() {
    val a = nothingFunction2()
}

/**
 * Возвращаемый тип Unit
 * Не все функции возвращают значение. Некоторые выполняют работу за счет побочных эффектов.
 * Например изменения состояния глобальной переменной или вызова других функции которые обеспечивают ввод данных
 * */

/**
 * Возвращаемый тип Nothing
 * Cообщает компилятору что функция гарантированно будет выполнена успешно. Функция либо породит исключение, либо по другой
 * Причине никогда не вернет управление в точку вызова
 * */

fun nothingFunction(): Nothing {
    TODO("Please implement function")
}
fun nothingFunction2(): Nothing {
    throw RuntimeException("An error occurred")
}

