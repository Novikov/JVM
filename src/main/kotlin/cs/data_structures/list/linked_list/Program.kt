package cs.data_structures.list.linked_list

fun main(){
    val list = LinkedList<Int>()
    list.push(3)
    list.push(2)
    list.push(1)
    println("Before inserting: $list")
    var middleNode = list.nodeAt(1)!!

    for (i in 1..3) {
        middleNode = list.insert(-1 * i, middleNode)
    }

    println("After inserting: $list")
}