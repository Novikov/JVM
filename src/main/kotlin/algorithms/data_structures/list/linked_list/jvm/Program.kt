package algorithms.data_structures.list.linked_list.jvm

fun main(){
    val list = LinkedListJvm<Int>()
    list.push(3)
    list.push(2)
    list.push(1)
    println(list)
    for (item in list) {
        println("Double: ${item * 2}")
    }
}