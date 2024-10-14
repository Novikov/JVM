package algorithms.data_structures.queue.using_array

fun main(){
    val stack = Stack<Int>()
    stack.push(1)
    stack.push(2)
    stack.push(3)
    stack.toString()
    stack.pop()
    stack.pop()
    stack.toString()

    val queue = Queue<Int>()
    queue.enqueue(1)
    queue.enqueue(2)
    queue.enqueue(3)
    println(queue.toString())
    println("peek ${queue.peek()}") //без удаления
    queue.dequeue()
    println(queue.toString())
}