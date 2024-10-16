package algorithms.data_structures.tree.binary_search_tree

import algorithms.data_structures.tree.binary_from_book.BinaryNode

class BinarySearchTree<T: Comparable<T>>() {
    var root: BinaryNode<T>? = null

    override fun toString() = root?.toString() ?: "empty tree"

    fun insert(value: T) {
        root = insert(root, value)
    }

    private fun insert(
        node: BinaryNode<T>?,
        value: T
    ): BinaryNode<T> {
        node ?: return BinaryNode(value)
        if (value < node.value) {
            node.leftChild = insert(node.leftChild, value)
        } else {
            node.rightChild = insert(node.rightChild, value)
        }
        return node
    }

    /** неоптимизированный поиск тк обходим дерево в глубину O(n)
    fun contains(value: T): Boolean {
        root ?: return false
        var found = false
        root?.traverseInOrder {
            if (value == it) {
                found = true
            }
        }
        return found
    }
    */

    // Оптимизированный поиск т.к ищем по значениям
    fun contains(value: T): Boolean {
        var current = root
        while (current != null) {
            if (current.value == value) {
                return true
            }
            current = if (value < current.value) {
                current.leftChild
            } else {
                current.rightChild
            }
        }
        return false
    }

    fun remove(value: T) {
        root = remove(root, value)
    }

    private fun remove(
        node: BinaryNode<T>?,
        value: T
    ): BinaryNode<T>? {
        node ?: return null
        when {
            value == node.value -> {
                if (node.leftChild == null && node.rightChild == null) {
                    return null
                }
                if (node.leftChild == null) {
                    return node.rightChild
                }
                if (node.rightChild == null) {
                    return node.leftChild
                }
                node.rightChild?.min?.value?.let {
                    node.value = it
                }
                node.rightChild = remove(node.rightChild, node.value)
            }
            value < node.value -> node.leftChild =
                remove(node.leftChild, value)
            else -> node.rightChild = remove(node.rightChild, value)
        }
        return node
    }

}