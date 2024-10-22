package algorithms.leetcode.recursion

import algorithms.leetcode.common_data.ListNode

// https://leetcode.com/problems/reverse-linked-list/description/

fun main() {
    val ln1 = ListNode(1)
    val ln2 = ListNode(2)
    val ln3 = ListNode(3)
    val ln4 = ListNode(4)
    val ln5 = ListNode(5)

    ln1.next = ln2
    ln2.next = ln3
    ln3.next = ln4
    ln4.next = ln5
    ln5.next = null

    printNodes(ln1)
    val reversedHead = reverseListUsingLoop(ln1)
    println("---------------------------------------")
    printNodes(reversedHead)
}

fun printNodes(head: ListNode?) {
    var node = head
    while (node != null) {
        println(node.`val`)
        node = node.next
    }
}

fun reverseListUsingLoop(head: ListNode?): ListNode? {
    var currentNode = head
    var previousNode: ListNode? = null

    while (currentNode != null) {
        val nextNode = currentNode.next
        currentNode.next = previousNode
        previousNode = currentNode
        currentNode = nextNode
    }

    return previousNode
}

fun reverseListUsingRecursion(head: ListNode?): ListNode? {
    if (head?.next == null) {
        return head
    }

    val reversedHead = reverseListUsingRecursion(head.next)

    head.next?.next = head // Предотвращение зацикливания
    head.next = null

    return reversedHead
}