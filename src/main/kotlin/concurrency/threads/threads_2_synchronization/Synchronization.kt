package concurrency.threads.threads_2_synchronization

import concurrency.threads.threads_2_synchronization.utils.IncrementCounterSynchronizedBlockWorker
import concurrency.threads.threads_2_synchronization.utils.IncrementCounterSynchronizedOnObjectWorker
import concurrency.threads.threads_2_synchronization.utils.IncrementCounterSynchronizedWorker
import concurrency.threads.threads_2_synchronization.utils.IncrementCounterUnsafeWorker

fun main() {
    //synchronizationExample1()
    //synchronizationExample2()
    //synchronizationExample3()
    synchronizationExample4()
}

/**
 * Процесс имеет stack и heap память
 * Stack хранит стэк вызовов методов, локальные переменные и аргументы методов.
 * Heap хранит объекты
 * У каждого потока есть своя Stack память
 * Все потоки имеют общую heap память
 * https://indrabasak.github.io/images/jvmruntime/jvm-runtime-memory.png?style=centerme
 *
 * */

/**
 * Пример отсутствия синхронизации при работе двух потоков с общим ресурсом
 *
 * Операция инкремента состоит из 3 частей:
 * -Чтение переменной из памяти
 * -Изменение значений переменной
 * -Запись нового значения в память
 *
 * Проблема заключается в том что читаемое значение кешируется потоком и при записи инкрементится именно оно,
 * даже если оно уже было изменено
 * */
fun synchronizationExample1() {
    val worker = IncrementCounterUnsafeWorker()
    worker.startWorker()
}

/**
 * Мьютекс (Mutual Exclusion Objects)
 * Механизм блокировки
 * Любой поток должен захватить мьютекс для доступа к ресурсу, который находится под его защитой.
 * Контролирует доступ только к одному ресурсу.
 * Для решения это проблемы мы можем использовать ключевое слово synchronized
 * Оно не позволяет использовать ресурс более чем одному потоку в каждый момент времени.
 * Отработает медленнее чем предыдущий пример
 * */

fun synchronizationExample2() {
    val worker = IncrementCounterSynchronizedWorker()
    worker.startWorker()
}

/**
 * Каждый объект в Java имеет встроенный монитор (intristic lock)
 * Когда поток захватывает этот монитор - второй ожидает (как светофор - красный стой - зеленый иди)
 * Но монитор связан именно с объектом
 * Ниже аналогичный пример но переписанный через synchronized block
 * */

fun synchronizationExample3() {
    val worker = IncrementCounterSynchronizedBlockWorker()
    worker.startWorker()
}

/**
 * Synchronized block принимает Any тип
 * Мы туда передаем или класс или объект пренадлежащий этому классу
 * Должно работать быстрее, но у меня почему то не быстрее. Возможно неправильно считается время
 * Тут будет выйгрыш в том случае если будет 2 таких объекта с которыми будут работать 2 отдельных потока
 * Если будем в synchronized блок передавать класс, то оба потока будут захватывать сам класс
 * */
fun synchronizationExample4() {
    val worker = IncrementCounterSynchronizedOnObjectWorker()
    worker.startWorker()
}

//todo Разобрать отличия мьютекса и монитора. Остановился на 34.26