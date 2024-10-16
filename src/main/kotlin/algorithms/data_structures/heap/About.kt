package algorithms.data_structures.heap

/**
 *  Внешне напоминает BinarySearch tree.
 *  В отличие от Tree Heap не может иметь дубликатов. https://umbrellait.rapira.com/sNr8yp
 *  Heap всегда отсортирована по убыванию или возрастанию.
 *  Все наследники определенного нода должны быть больше или меньше своего родителя.
 *
 *  Все это дерево в итоге хранится в ArrayList, начиная с индекса 0 или 1. Есть несколько вариантов. https://umbrellait.rapira.com/T1ghbz
 *  Ключевая характеристика этой структуры в том, что это дерево всегда complete иначе будут пробелы в ArrayList https://umbrellait.rapira.com/hatbeY
 *  Complete значит что все ноды расположены слева направо.
 *
 *  Т.е при добавление нового Node мы должны сравнивать его с уже добавленными. Очень интересный момент поиска пары для сравнения.
 *  Мы берем индекс нового добавленного элемента, делим на 2 и отбрасываем остаток от деления (если он есть). В результате получим индекс в
 *  массиве того элемента с которым мы будем сравнивать по значению. И если наше текущее значение больше, то мы сделаем swap.
 *
 *  current это указатель который всегда стремиться к вершине
 *
 *  todo Перенести все крлы скринов на свой диск.
 * */
fun main() {
    val heap = Heap()
    heap.insert(1)
    heap.insert(5)
    heap.insert(100)
    heap.insert(2)
    println(heap.getHeap())
}