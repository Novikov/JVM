package kotlin_conceptions

/** расписать типы ссылок и способы передачи в методы для разных типов, хотя в котлин похоже все по ссылке*/

fun main() {
    var nums = intArrayOf(1, 2, 3)
    modifyArray(nums)

    println(nums.joinToString(", ")) // Вывод: 99, 2, 3
}

fun modifyArray(arr: IntArray) {
    arr[0] = 99 // Изменяем содержимое массива, передача по ссылке
   // arr = intArrayOf(1, 2, 3) // Это не изменит исходный массив
}