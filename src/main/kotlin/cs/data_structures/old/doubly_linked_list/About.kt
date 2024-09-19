package cs.data_structures.old.doubly_linked_list

import cs.data_structures.old.linked_list.LinkedList


/**
 * Отличия:
 * Node хранит ссылку на предыдущий node и за счет этого некоторые операции выполняются за O(1), а некоторые за O(n),
 * но более эффективно потому что итерация будет начинаться с более удобного края;
 * RemoveLast выполняется за O(1);
 * В методах removeFirst()/removeLast() используется temp переменная.
 * */
fun main() {
    val myLinkedList = LinkedList(1)
    myLinkedList.append(2)
    myLinkedList.append(3)
    myLinkedList.getInfo()
    myLinkedList.insert(1, 5)
    myLinkedList.getInfo()
}