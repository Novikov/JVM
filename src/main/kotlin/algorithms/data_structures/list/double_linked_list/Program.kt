package algorithms.data_structures.list.double_linked_list

fun main() {
    val myLinkedList = DoubleLinkedList(1)
    myLinkedList.append(2)
    myLinkedList.append(3)
    myLinkedList.getInfo()
    myLinkedList.insert(1, 5)
    myLinkedList.getInfo()
}