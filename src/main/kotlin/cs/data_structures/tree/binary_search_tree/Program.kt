package cs.data_structures.tree.binary_search_tree

fun main(){
    // Несбалансированное дерево
    val bst = BinarySearchTree<Int>()
    (0..4).forEach {
        bst.insert(it)
    }
    println(bst)

    // Сбалансированное дерево
    val exampleTree = BinarySearchTree<Int>().apply {
        insert(3)
        insert(1)
        insert(4)
        insert(0)
        insert(2)
        insert(5)
    }
    println(exampleTree)

    //Поиск элемента через обход по порядку
    if (exampleTree.contains(5)) {
        println("Found 5!")
    } else {
        println("Couldn't find 5")
    }

    //Удаление элемента с балансировкой
    println("Tree before removal:")
    println(exampleTree)
    exampleTree.remove(3)
    println("Tree after removing root:")
    println(exampleTree)

}