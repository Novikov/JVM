package concurrency.libs.flow

import kotlinx.coroutines.flow.*

suspend fun main() {
    //flowTheoryExample1()
    //flowTheoryExample2()
    //flowTheoryExample3()
    //flowTheoryExample4()
    //flowTheoryExample5()
    flowTheoryExample6()
}

/**
 * Поток данных это труба по которой летят объекты и к которой мы можем добавлять промежуточные операторы
 * Создание промежуточных коллекций и вывод результата целиком, а не по одиночке
 * Блокируем поток для вычисления общего результата по всем значениям
 * */
fun flowTheoryExample1() {
    val numbers = listOf(3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    numbers.filter { it.isPrime() }
        .filter { it > 5 }
        .map { "Number : $it" }
        .forEach { println(it) }
}

fun Int.isPrime(): Boolean {
    if (this <= 1) return false
    for (i in 2..this / 2) {
        Thread.sleep(50)
        if (this % i == 0) return false
    }
    return true
}

/**
 * Избавляемся от промежуточных коллекций и применяем все операторы к каждому элементу по очереди
 * В этом случае мы так же блокируем поток, но для вычисления каждого значения
 * */
fun flowTheoryExample2() {
    val numbers = listOf(3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15).asSequence()
    numbers.filter { it.isPrime() }
        .filter { it > 5 }
        .map { "Number : $it" }
        .forEach { println(it) } // поток данных будет работать только если вызвать терминальный оператор (заверщающее действие)
    //Для коллекций и циклов терминальный оператор не нужен (Пример выше)

    /**
     * Промежуточный оператор возвращает - sequence
     * Если метод не возвращает sequence, а на пример Boolean или что то другое - он терминальный
     * */
}

/**
 * 1)Sequence это поток данных, но не поддерживающий асинхронную обработку данных / вычисление (попробуй вызвать isPrimeSuspend в example2)
 * 2)Flow это поток данных который мы можем обрабатывать/вычислять асинхронно, и во всех его промежуточных операторах мы можем вызывать suspend функции
 * -Не блокируем поток для вычисления/обработки каждого значения
 * */
suspend fun flowTheoryExample3() {
    val numbers = listOf(3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15).asFlow()
    numbers.filter { it.isPrimeSuspend() }
        .filter { it > 5 }
        .map { "Number : $it" }
        .collect { println(it) }
}

suspend fun Int.isPrimeSuspend(): Boolean {
    if (this <= 1) return false
    for (i in 2..this / 2) {
        Thread.sleep(50)
        if (this % i == 0) return false
    }
    return true
}

/**
 * Flow нужен для того потому что корутины поддерживают императивный подход, а не реактивный
 * Пример с перевызовом репозитория для получения одних и тех же данных с разных экранов, чтоб обновилась LiveData
 * */


/** Внутри не Suspend функции мы можем вернуть Flow, но сделать подписку, вызвав терминальный оператор можем только в suspend функции
 * Flow будет выполняться в том контексте в котором вызван терминальный оператор
 * */
fun flowTheoryExample4(): Flow<Int> {
    return flow<Int> {
        emit(1)
    }
}

/**
 * flow билдеры
 * */

fun flowTheoryExample5() {
    val flow1 = flowOf(1, 2, 3)
    val flow2 = listOf(1, 2, 3).asFlow()
    val flow3 = flow<Int> { repeat(3) { emit(it) } }
}

/**
 * Терминальные операторы
 * */

suspend fun flowTheoryExample6() {
    val testFlow =  flow {
        var i = 0
        while (true){
            println(i)
            emit(i++)
        }
    }

    //testFlow.collect() // бесконечный flow
    //testFlow.toList() // также бесконечный flow
    testFlow.first() // первый элемент будет получен и дальнейшие эмиты производиться не будут. flow завершит свою работу

    /** Необходимо избегать ситуаций с бесконечным циклами */
}