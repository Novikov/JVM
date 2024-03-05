package data_structures.linked_list


/**
 *
 * https://static.javatpoint.com/ds/images/ds-linked-list.png
 *
 * Основные сведения
 * Хранится в памяти не упорядоченным способом, в отличие от массивов;
 * Node - элемент свиска хранит данные и ссылку на следующий элемент;
 * Head - указатель на начало списка;
 * Tail - указатель на конец списка;
 * Pre/Temp - указатели для итерации по элементам списка (локальные переменные методов) (там где нужны дополнительные head и tail);
 * Указатель последнего элемента равен null;
 *
 * Big(o)
 * 1)Добавление элементов в конец cписка занимает время O(1) потому что у нас есть указатель на последний элемент.
 * Мы лишь прибавляем еще 1;
 * 2)Удаление элемента из конца списка занимает время O(n) потому что после удаление необходимо вычислить позицию Tail,
 * а для этого необходимо итерироваться по всем элементам;
 * 3)Добавление элемента в начало списка занимает время O(1) по аналогии с удалением из конца. У нас есть все данные, чтобы
 * переместить HEAD.
 * 4)Добавление/Удаление элемента в середине списка занимает время O(n) потому что необходимо итерироваться по всему списку.
 * 5)Получение элемента по индексу так же займет время O(n) потому что заставляет итерироваться. Для этой операции лучше
 * использовать ArrayList
 *
 * Данный list содержит методы которые используются в других методах. append и prepend, removeFirst и removeLast используются в
 * insert и remove.
 *
 * todo Разобраться почему remove методы возвращают Node, а add методы bolean.
 *
 * Таблица сравнения https://umbrellait.rapira.com/kBIfVs
 * */
fun main() {
    val myLinkedList = LinkedList(1)
    myLinkedList.append(2)
    myLinkedList.append(3)
    myLinkedList.reverse()
    myLinkedList.getInfo()
}

// Визуализация https://umbrellait.rapira.com/iwOYN2

