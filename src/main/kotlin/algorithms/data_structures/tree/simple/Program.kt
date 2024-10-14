package algorithms.data_structures.tree.simple

fun main(){
    val hot = TreeNode("Hot")
    val cold = TreeNode("Cold")
    val beverages = TreeNode("Beverages").run {
        add(hot)
        add(cold)
    }

    val tree = makeBeverageTree()
    //tree.forEachDepthFirst { println(it.value) } // обход в глубину
    //tree.forEachLevelOrder { println(it.value) } // обход в ширину

    tree.search("ginger ale")?.let {
        println("Found node: ${it.value}")
    }
    tree.search("WKD Blue")?.let {
        println(it.value)
    } ?: println("Couldn't find WKD Blue")
}

fun makeBeverageTree(): TreeNode<String> {
    val tree = TreeNode("Beverages")
    val hot = TreeNode("hot")
    val cold = TreeNode("cold")
    val tea = TreeNode("tea")
    val coffee = TreeNode("coffee")
    val chocolate = TreeNode("cocoa")
    val blackTea = TreeNode("black")
    val greenTea = TreeNode("green")
    val chaiTea = TreeNode("chai")
    val soda = TreeNode("soda")
    val milk = TreeNode("milk")
    val gingerAle = TreeNode("ginger ale")
    val bitterLemon = TreeNode("bitter lemon")
    tree.add(hot)
    tree.add(cold)
    hot.add(tea)
    hot.add(coffee)
    hot.add(chocolate)
    cold.add(soda)
    cold.add(milk)
    tea.add(blackTea)
    tea.add(greenTea)
    tea.add(chaiTea)
    soda.add(gingerAle)
    soda.add(bitterLemon)
    return tree
}