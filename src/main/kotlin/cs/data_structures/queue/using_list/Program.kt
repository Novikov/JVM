package cs.data_structures.queue.using_list

/**
 * Для реализации обеих структур данных может использоваться как LinkedList, так и ArrayList.
 * Вследствие этого время выполнения big (O) некоторых методов будет отличаться.
 *
 * Stack - LIFO структура данных.
 * Вместо HEAD и TAIL указателей используется только один TOP.
 * Вместо Length переменной используется height.
 * Добавление (push) и удаление(pop) первого элемента занимает время O(1).
 * todo подумать о времени других реализаций этой структуры данных.
 *
 * Queue - FIFO структура данных.
 * Вместо HEAD и TAIL указателей используется FIRST и LAST.
 * length остается.
 * Добавление элемента в очередь (enqueue), удаление (dequeue)
 *
 * */

fun main() {
    val stack = Stack(1)
    stack.push(2)
    stack.push(3)
    stack.push(4)
    stack.push(5)
    stack.pop()
    stack.pop()
    stack.printStack()

    println("-----")

    val queue = Queue(1)
    queue.enqueue(2)
    queue.enqueue(3)
    queue.enqueue(4)
    queue.enqueue(5)
    queue.dequeue()
    queue.dequeue()
    queue.printQueue()
}