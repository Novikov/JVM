package algorithms.data_structures.tree.binary_from_book

fun main(){
    val zero = BinaryNode(0)
    val one = BinaryNode(1)
    val five = BinaryNode(5)
    val seven = BinaryNode(7)
    val eight = BinaryNode(8)
    val nine = BinaryNode(9)
    seven.leftChild = one
    one.leftChild = zero
    one.rightChild = five
    seven.rightChild = nine
    nine.leftChild = eight
    val tree = seven

    //println(seven.toString())

    tree.traverseInOrder { println(it) }

    tree.traversePreOrder { println(it) }

    tree.traversePostOrder { println(it) }
}