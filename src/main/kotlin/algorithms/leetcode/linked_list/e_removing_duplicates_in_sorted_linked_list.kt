package algorithms.leetcode.linked_list

//https://leetcode.com/problems/remove-duplicates-from-sorted-list/

fun main(){

}

fun deleteDuplicates(head: ListNode?): ListNode? {
    var current = head
    while (current != null && current.next != null) {
        if (current.`val` == (current.next?.`val` ?: false)) { // Если текущий и следующий элемент равны
            current.next = current.next?.next
        } else {
            current = current.next // переключаем на след элемент
        }
    }
    return head
}

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}