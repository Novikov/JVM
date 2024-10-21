package concurrency.threads.threads_1_base_theory

import concurrency.threads.threads_1_base_theory.utils.Service
import concurrency.threads.threads_1_base_theory.utils.ThreadCounterWorker


fun main(){
//    consistentBehaviourExample()
    concurrencyBehaviourExample()
}

/**
 * Пример последовательного выполнения кода
 * */

fun consistentBehaviourExample(){
    val service = Service()
    service.readData()
    service.showGreetingMessage()
    service.calculateFactorial(50)
    service.calculateSum(100)
    service.finishProgram()
}

/**
 * Процесс - это экземпляр выполняемой программы, а так же текущиен значения счетчика комманд, регистров и переменных
 * Поток - это основная еденица которой операционная система выделяет время процессора.
 *
 * Многопоточность (Concurrency) это свойство платформы (например операционной системы, виртуально машины и итд) или
 * приложения, состоящее в том что процесс порожденный в операционной системе, может состоять из нескольких потоков,
 * выполняющихся парралельно, то есть без предписанного порядка во времени.
 *
 * Пример
 * JVM с точки зрения ОС это процесс
 * ОС выделяет регистры, stack память, и память heap для каждого процесса
 * Внутри процесса у нас есть рабочие юниты (Потоки)
 * Каждый поток использует память процессора и его ресурсы
 * Создать поток намного легче чем процесс
 *
 * В случае 1 CPU и 3 задач процессор будет выполнять эти задачи переключаясь между ними, но в 1 момент времени будет выполнятья только 1 задача. Это и есть многопоточность (Concurrency)
 * В случае нескольких CPU и нескольких потоков мы достигаем парралелизма (парралельное выполнение).
 *
 * Преимущества многопоточности
 * -Меньшее время ответа на запрос (например аплоад файла)
 * -Повышение производительности приложения
 * -Эффективное использование возможностей аппаратной части
 *
 * Недостатки
 * -Сложность поддержания многопоточного кода
 * -Трудности при работе с общими ресурсами
 * -Необходимость мониторинга производительности и тестирования
 *
 * ЖЦ потока
 * - New
 * - Active
 * - Blocked
 * - Terminated
 * */

/**
 * Пример многопоточного выполнения кода
 * Здесь 2 класса будут печатать в разнобой
 * Это именно многопоточное, а не парралельное выполнение.
 * Для достижения парралельного выполнения необходимо использовать инструменты наподобие ForkJoinPool
 * */

fun concurrencyBehaviourExample(){
    val tcw1 = ThreadCounterWorker("A", 15)
    val tcw2 = ThreadCounterWorker("B", 15)

    tcw1.start()
    tcw2.start()
}