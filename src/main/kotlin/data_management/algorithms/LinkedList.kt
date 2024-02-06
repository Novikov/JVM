package data_management.algorithms


/**
 *
 * https://static.javatpoint.com/ds/images/ds-linked-list.png
 *
 * Основные сведения
 * Хранится в памяти не упорядоченным способом, в отличие от массивов;
 * Node - элемент свиска хранит данные и ссылку на следующий элемент;
 * Head - указатель на начало списка;
 * Tail - указатель на конец списка;
 * Pre/Temp - указатели для итерации по элементам списка (локальные переменные методов);
 * Указатель последнего элемента равен null;
 *
 * Big(o)
 * 1)Добавление элементов в конец cписка занимает время O(1) потому что у нас есть указатель на последний элемент.
 * Мы лишь прибавляем еще 1;
 * 2)Удаление элемента из конца списка занимает время O(n) потому что после удаление необходимо вычислить позицию Tail,
 * а для этого необходимо итерироваться по всем элементам;
 * 3)Добавление элемента в начало списка занимает время O(1) по аналогии с удалением из конца. У нас есть все данные, чтобы
 * переместить HEAD.
 * 4)Добавление/Удаление элемента в середине списка занимает время O(1) потому что необходимо итерироваться по всему списку.
 * 5)Получение элемента по индексу так же займет время O(n) потому что заставляет итерироваться. Для этой операции лучше
 * использовать ArrayList
 *
 * Таблица сравнения https://umbrellait.rapira.com/kBIfVs
 * */
fun main() {
    val myLinkedList = LinkedList(2)
    myLinkedList.append(3)
    myLinkedList.prepend(1)
    myLinkedList.getInfo()
    myLinkedList.removeFirst()
    myLinkedList.getInfo()
}

// Визуализация https://umbrellait.rapira.com/iwOYN2

data class Node<T>(val value: T, var next: Node<T>? = null)

class LinkedList<T> {
    private var head: Node<T>? = null
    private var tail: Node<T>? = null
    private var length = 0

    constructor(value: T) {
        val newNode = Node(value = value)
        head = newNode
        tail = newNode
        length = 1
    }

    fun append(value: T) {
        val newNode = Node(value = value)
        if (length == 0) {
            head = newNode
            tail = newNode
        } else {
            head?.next = newNode
            tail = newNode
        }
        length++
    }

    fun removeLast(): Node<T>? {
        if (length == 0) return null
        var pre = head
        var temp = head
        while (temp?.next != null) {
            pre = temp
            temp = temp.next
        }
        tail = pre
        tail?.next = null
        length--
        if (length == 0) { // Чтобы не уйти в отрицательные значения
            tail = null
            head = null
        }
        return temp
    }

    fun prepend(value: T) {
        val newNode = Node(value)
        if (length == 0) {
            tail = newNode
            head = newNode
        } else {
            newNode.next = head
            head = newNode
        }
        length++
    }

    fun removeFirst(): Node<T>? {
        if (length == 0) return null
        val temp = head
        head = head?.next
        temp?.next = null
        length--
        if (length == 0) {
            tail = null // head не нужно занулять потому что он занулится выше
        }
        return temp
    }

    fun printList() {
        var temp = head
        while (temp != null) {
            println(temp.value)
            temp = temp.next
        }
    }

    fun getHead() {
        println("Head: " + head?.value)
    }

    fun getTail() {
        println("Tail: " + tail?.value)
    }

    fun getLength() {
        println("Length: $length")
    }

    fun getInfo() {
        println("Length: $length, Head: ${head?.value}, Tail: ${tail?.value}}")
        printList()
    }
}