package algorithms.leetcode.x_sort_out

import java.util.*


fun main() {
    val nums = intArrayOf(3, 2, 1, 5, 6, 4)
    val k = 3
    println("The $k-th largest element is ${findKthLargest(nums, k)}")
}

fun findKthLargest(nums: IntArray, k: Int): Int {
    val data = PriorityQueue<Int>(k + 1)

    for (i in nums) {
        data.add(i)
        if (data.size > k) { // k это ограничитель размера queue
            data.poll()
        }
    }
    return data.poll()
}

/**
 * 1)Создаем минимальную кучу из размера K.
 * 2)Итерируем по всем элементам массива:
 * 3)Если текущий элемент больше самого маленького элемента в куче,
 * удаляем этот самый маленький элемент и добавляем текущий элемент.
 * После завершения итерации, элемент на вершине кучи будет K-м по величине элементом.
 *
 * Метод poll у PriorityQueue в Java удаляет и возвращает элемент с наивысшим приоритетом из очереди.
 * В контексте PriorityQueue, которая представляет собой минимальную кучу по умолчанию, это будет элемент
 * с наименьшим значением. Если вы используете минимальную кучу, то poll удаляет и возвращает самый маленький
 * элемент, а все остальные элементы в очереди автоматически перестраиваются так, чтобы сохранить свойство кучи.
 *
 * */
