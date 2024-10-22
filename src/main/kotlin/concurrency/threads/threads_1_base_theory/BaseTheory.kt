package concurrency.threads.threads_1_base_theory

import concurrency.threads.threads_1_base_theory.utils.*


fun main(){
//    consistentBehaviourExample()

//    concurrencyBehaviourExample()
//    concurrencyBehaviourExample2()
//    concurrencyBehaviourExample3()
//    concurrencyBehaviourExample4()
//    concurrencyBehaviourExample5()
    concurrencyBehaviourExample6()
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

    // Помни что у thread можно запустить метод run который отработает в ui потоке
    // tcw1.run() компилятор подсказывает что делать этого не надо
}

/**
 * Тоже самое только уже через Runable
 * */
fun concurrencyBehaviourExample2(){
    val tcw1 = RunnableCounterWorker("A", 15)
    val tcw2 = RunnableCounterWorker("B", 15)

    val thread1 = Thread(tcw1)
    val thread2 = Thread(tcw2)

    thread1.start()
    thread2.start()
}

/**
 * Пример блокирующей операции
 *
 * Метод Thread.sleep(long millis) в Java обычно не выбрасывает исключения, кроме одного случая.
 * Вот его подробности:
 *
 * Исключение InterruptedException
 * Когда: Thread.sleep() может выбросить InterruptedException, если поток был прерван во время ожидания.
 * Как: Это происходит, когда другой поток вызывает метод interrupt() на текущем потоке. Если поток находится
 * в состоянии сна, вызов interrupt() приведет к выбросу InterruptedException, и поток выйдет из состояния сна.
 * todo добавить пример с InterruptedException
 * */
fun concurrencyBehaviourExample3(){
    val tcw1 = ThreadWithSleepCounterWorker("A", 15)
    val tcw2 = ThreadWithSleepCounterWorker("B", 15)

    val thread1 = Thread(tcw1)
    val thread2 = Thread(tcw2)

    thread1.start()
    thread2.start()
}


/**
 * Демонстрация работы join() метода
 * Главный поток освободится только после отработки двух дополнительных потоков
 * И только после этого распечатается Process is finished!!!
 *
 * Этот метод так же как и в примере выше со sleep может бросить exception в случае вызова
 * другим потоком interrupt() на текущем потоке.
 * */
fun concurrencyBehaviourExample4() {
    val tcw1 = ThreadCounterWorker("A", 15)
    val tcw2 = ThreadCounterWorker("B", 1000)

    // МНОГОПОТОЧНАЯ ОБРАБОТКА
    tcw1.start()
    tcw2.start()

    try {
        tcw1.join()
      //  tcw2.join() всеровно завершится
    } catch (e: InterruptedException) {
        throw RuntimeException(e)
    }

    println("Process is finished!!!")
}

/**
 * Установка приоритета не влияет на приоритет. Операционная система решает сама.
 *
 * -Установка приоритета потоков в Java действительно может не всегда работать так, как ожидается. Вот несколько причин, почему это происходит:
 * -Платформозависимость: Реализация управления потоками в Java зависит от операционной системы. Не все ОС следуют указаниям по приоритетам потоков, и некоторые могут игнорировать эти установки.
 * -Модель планирования: Java использует планировщик потоков ОС, который может иметь свои собственные правила и алгоритмы. Приоритеты потоков в Java могут не совпадать с приоритетами на уровне ОС.
 * -Необходимость: В большинстве приложений нет строгой необходимости в тонком управлении приоритетами потоков. Приоритеты могут быть полезны для обозначения важности потоков, но многие задачи можно решить без их использования.
 * -Сложности с отладкой: Изменение приоритетов может привести к сложностям с отладкой и производительностью, особенно если потоки начинают конкурировать за ресурсы.
 * */
fun concurrencyBehaviourExample5() {
    val tcw1 = ThreadCounterWithPriorityWorker("A", 5, 10)
    val tcw2 = ThreadCounterWithPriorityWorker("B", 5, 1)

    // МНОГОПОТОЧНАЯ ОБРАБОТКА
    tcw1.start()
    tcw2.start()

    // НЕ ГАРАНТИРУЕТСЯ ПОРЯДОК
    println("Process is finished!!!")
}

/**
 * Daemon потоки
 * Поток всегда завершает выполнение
 * */
fun concurrencyBehaviourExample6() {
    val tcw1 = ThreadCounterWithDaemonFlagWorker("A", 1000, true)
    val tcw2 = ThreadCounterWithDaemonFlagWorker("B", 100, false)

    // МНОГОПОТОЧНАЯ ОБРАБОТКА
    tcw1.start()
    tcw2.start()

    // НЕ ГАРАНТИРУЕТСЯ ПОРЯДОК
    println("Process is finished!!! ")
}