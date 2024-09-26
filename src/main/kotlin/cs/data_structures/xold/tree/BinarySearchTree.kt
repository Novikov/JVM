package cs.data_structures.xold.tree

class BinarySearchTree {
    var root: Node<Int>? = null

    fun insert(value: Int): Boolean {
        val newNode = Node(value)
        if (root == null) {
            root = newNode
            return true
        }
        var temp = root
        while (true) {
            if (newNode.value == temp?.value) return false
            if (newNode.value < temp?.value!!) {
                if (temp.left == null) {
                    temp.left = newNode
                    return true
                }
                temp = temp.left
            } else {
                if (temp.right == null) {
                    temp.right = newNode
                    return true
                }
                temp = temp.right
            }
        }
    }

    operator fun contains(value: Int): Boolean {
        var temp = root
        while (temp != null) {
            temp = if (value < temp.value) {
                temp.left
            } else if (value > temp.value) {
                temp.right
            } else {
                return true
            }
        }
        return false
    }
}