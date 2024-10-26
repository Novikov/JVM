package algorithms.algo.sorting.n_2

fun main(){
    val array = arrayOf(64, 34, 25, 12)
    println("Исходный массив: ${array.joinToString(", ")}")

    array.bubbleSort()

    println("Отсортированный массив: ${array.joinToString(", ")}")
}

fun <T: Comparable<T>> Array<T>.bubbleSort(){
    for (i in 0 until size - 1){
        println("outer for")
        for (j in 0 until size - 1 - i) { //-i сдесь для оптимизации т.к после отработки внутренниго for в конце всегда будет самый большой элемент
            if (this[j] > this[j + 1]) {
                val temp: T = this[j]
                this[j] = this[j+1]
                this[j+1] = temp
                println("inner for "+this.joinToString ((", ")))
            }
        }
    }
}