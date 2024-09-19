package cs.data_structures.old.hash_table


/**
 * Есть структура данных типа key - value.
 * Хеш функция обрабатывает key и выдет адрес по которому сама структура будет храниться в ArrayList https://umbrellait.rapira.com/CRKUe5
 * Хеш функция однонаправленная и по адресу нельзя получить значение ключа
 * На один и тот же Key мы будем получать одинаковый адрес
 * При возникновении коллизии (когда на несколько разных key мы получили один и тот же адрес) есть несколько способов сохранения значения:
 * Сохранение дополнительного элемента в туже ячейку называется separate chaining
 * Поиск свободной ячейки ниже и сохранение в нее элемента называется linear probing
 *
 * Big O у HashTable O(1), но только в том случае когда у нас очень хорошо работает hash функция и большое количество ячеек в таблице.
 * Конечно можно сделать такую таблицу где на каждый добавленный элемент будет возникать коллизия тогда и время будет O(n) но этот вариант мы опускаем
 * т.е это просто неправильная реализация.
 *
 * С помощью HashTable можно более оптимально решать задачу поиска общего элемента в 2х массивах.
 * Как это делается можно посмотреть тут https://www.udemy.com/course/data-structures-and-algorithms-java/learn/lecture/29629662#learning-tools
 * */
fun main(){
val myHashTable = HashTable()
    myHashTable.set("Hello", 5)
    myHashTable.set("Oleg", 2)
    myHashTable.set("Igor", 3)
    myHashTable.printTable()

    println("value for key Igor ${myHashTable["Igor"]}")
    println("all keys ${myHashTable.keys()}")
}