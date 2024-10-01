package cs.data_structures.tree.binary

class BinarySearchTree {
    var root: BinaryNode<Int>? = null

    fun insert(value: Int): Boolean {
        val newBinaryNode = BinaryNode(value)
        if (root == null) {
            root = newBinaryNode
            return true
        }
        var temp = root
        while (true) {
            if (newBinaryNode.value == temp?.value) return false
            if (newBinaryNode.value < temp?.value!!) {
                if (temp.left == null) {
                    temp.left = newBinaryNode
                    return true
                }
                temp = temp.left
            } else {
                if (temp.right == null) {
                    temp.right = newBinaryNode
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