package concurrency.threads.threads_2_synchronization

import concurrency.threads.threads_2_synchronization.utils.*
import java.util.concurrent.Exchanger
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong




fun main() {
    //synchronizationExample1()
    //synchronizationExample2()
    //synchronizationExample3()
    //synchronizationExample4()
    //synchronizationExample5()
    //synchronizationExample6()
    //synchronizationExample7()
    //synchronizationExample8()
    //synchronizationExample9()
    //synchronizationExample10()
    synchronizationExample11()
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

/**
 * Synchronized blocks с objects в примере выше лишины гибкости
 * Мы не контролируем кто захватил ресурс и кто освободил
 * Для того чтобы дать нам эту гибкость были созданы локи и семафоры
 * В примере ниже демонстрируется как сделать синхронизацию без synchronized block через Reentrant lock
 * */

fun synchronizationExample5() {
    val worker = IncrementCounterReentrantLockWorker()
    worker.startWorker()
}

/** С помощью Reentrant lock можно решить проблему вызова порядка Threads, которая демонстрируется ниже
 * При вызове этого метода нет гарантии порядка исполнения потоков, запусти и убедись.
 * join решает задачу блокировки main потока*/
fun synchronizationExample6() {
    val foo = FooUnsafe()
    val t1 = Thread { foo.first() }
    val t2 = Thread { foo.second() }
    val t3 = Thread { foo.third() }

    t1.start()
    t2.start()
    t3.start()

    try {
        t1.join()
        t2.join()
        t3.join()
    } catch (e: InterruptedException) {
        throw RuntimeException(e)
    }
}

/**
 * С помощью conditions у Reentrant lock мы решаем данную проблему
 * */
fun synchronizationExample7() {
    val foo = FooSafe()

    val t1 = Thread { foo.second() }
    val t2 = Thread { foo.first() }
    val t3 = Thread { foo.third() }

    t1.name = "T1"
    t2.name = "T2"
    t3.name = "T3"

    t1.start()
    t2.start()
    t3.start()

    try {
        t1.join()
        t2.join()
        t3.join()
    } catch (e: InterruptedException) {
        throw java.lang.RuntimeException(e)
    }
}

/**
 * Lock
 * Принцип работы: Lock обеспечивает эксклюзивный доступ к критической секции. Если один поток захватывает блокировку, другие потоки должны ждать, пока она не будет освобождена.
 * Использование: Lock предоставляет более гибкие механизмы управления, чем synchronized. Например, его можно прерывать, пробовать захватить блокировку с таймаутом, а также позволяет реализовать блокировку с несколькими условиями (Condition).
 * Состояние блокировки: Lock можно явно захватывать и освобождать, что позволяет более точно управлять поведением потоков.
 * */

/**
 * Другим механизмом обеспечения синхронизации являются Семафоры
 * Семафоры - это переменные которые используются для контроля доступа к общему ресурсу
 * Существуют бинарные семафоры и семафоры со счетчиком
 * Бинарные впускают только 1 поток
 * Со счетчиком впускают определенное количество потоков
 * Контролируют ТОЛЬКО количество входов
 * */

fun synchronizationExample8() {
    val foo = FooSemaphoreSafe()

    val t1 = Thread { foo.second() }
    val t2 = Thread { foo.first() }
    val t3 = Thread { foo.third() }

    t1.name = "T1"
    t2.name = "T2"
    t3.name = "T3"

    t1.start()
    t2.start()
    t3.start()

    try {
        t1.join()
        t2.join()
        t3.join()
    } catch (e: InterruptedException) {
        throw java.lang.RuntimeException(e)
    }
}

/**
 * Semaphore
 * Принцип работы: Semaphore используется для управления доступом к ограниченному числу ресурсов. Он может разрешать доступ нескольким потокам одновременно, в зависимости от заданного количества разрешений.
 * Использование: Подходит для ситуаций, когда нужно ограничить количество потоков, которые могут одновременно выполнять определённый участок кода или использовать ресурс.
 * Состояние разрешений: Semaphore имеет счетчик разрешений, который уменьшается при захвате и увеличивается при освобождении. Если счетчик достигает нуля, потоки, пытающиеся захватить семафор, будут блокироваться до тех пор, пока не освободится хотя бы одно разрешение.
 *
 * Lock vs Semaphore
 * Тип синхронизации: Lock предоставляет эксклюзивный доступ, тогда как Semaphore управляет количеством разрешений.
 * Управление потоками: Lock более гибок и позволяет реализовывать более сложные механизмы синхронизации, в то время как Semaphore подходит для управления доступом к ресурсам с ограниченной пропускной способностью.
 * Упрощение использования: Lock требует явного захвата и освобождения, а Semaphore также требует управления количеством разрешений.
 * */

/**
 * Exchanger - Механизм обмена данными между потоками
 * Пример когда потоки изменяют состояние обхекта и передают его друг другу
 * Проблема в этом примере в том что в выводе нарушена синхронизация.
 * Проскакивают примеры с двумя и более PING или PONG
 * Exchanger позволяет обмениваться данными, но синхронизацию должны обеспечить именно мы
 * */
fun synchronizationExample9() {
    val counter = AtomicInteger()

    val exchanger = Exchanger<AtomicInteger>()
    val pingWorker = PingWorker(counter, exchanger)
    val pongWorker = PongWorker(counter, exchanger)

    val t1 = Thread(pingWorker)
    val t2 = Thread(pongWorker)

    t1.start()
    t2.start()
}

/**
 * Atomic
 * Атомарная операция - операция которая либо выполняется целиком, либо не выполняется вовсе
 * С помощью Atomic ресурсов мы решаем проблему синхронизации
 * Существуют обертки для всех примитивов и также существует Generic класс AtomicReference
 *
 * Atomic переменные
 * Атомарные операции: Классы из java.util.concurrent.atomic (например, AtomicInteger, AtomicBoolean и т.д.) предоставляют методы для выполнения атомарных операций, таких как incrementAndGet(), compareAndSet() и другие. Эти методы гарантируют, что операции выполняются без прерываний другими потоками.
 * Использование в многопоточных средах: Atomic переменные предназначены для ситуаций, где необходимо несколько операций на переменной, которые должны выполняться атомарно. Это делает их более подходящими для сложных алгоритмов, требующих безопасного обновления состояния.
 * Простота использования: Они обычно проще и безопаснее в использовании для выполнения частых операций, таких как инкременты, в многопоточной среде, не требуя явной синхронизации
 * */
fun synchronizationExample10() {
    val atomicWorker = AtomicWorker()
    atomicWorker.startWorker()
}

/**
 * Volatile
 * Ключевое слово volatile сигнализирует о том, что не нужно кешировать переменную, а писать непосредственно в нее
 * Если убрать это слово то поток MyThreadRed не отработает т.к он будет думать что i=0;
 *
 * Volatile переменные
 * Область видимости: Когда переменная объявлена как volatile, это означает, что любые изменения, сделанные в этой переменной в одном потоке, будут видны всем другим потокам немедленно. Это обеспечивает видимость изменений.
 * Отсутствие блокировок: volatile переменные не требуют блокировок, что делает доступ к ним быстрее по сравнению с синхронизированными методами или блоками.
 * Отсутствие атомарности: Однако volatile не гарантирует атомарность операций. Например, если вы хотите увеличить значение переменной, нужно использовать дополнительную синхронизацию, так как операция x++ не атомарна (включает чтение, изменение и запись).
 * */
fun synchronizationExample11() {
    val atomicWorker = VolatileWorker()
    atomicWorker.startWorker()
}

//todo Разобрать отличия мьютекса и монитора. Остановился на 34.26