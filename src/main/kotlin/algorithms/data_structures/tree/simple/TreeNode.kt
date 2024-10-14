package algorithms.data_structures.tree.simple

import algorithms.data_structures.queue.using_array.Queue


class TreeNode<T>(val value: T) {
    private val children: MutableList<TreeNode<T>> = mutableListOf()

    fun add(child: TreeNode<T>) = children.add(child)


    // Обход в глубину через рекурсивный вызов
    fun forEachDepthFirst(visit: (TreeNode<T>) -> Unit) {
        visit(this)
        children.forEach {
            it.forEachDepthFirst(visit)
        }
    }

    // Обход в ширину
    fun forEachLevelOrder(visit: (TreeNode<T>) -> Unit) {
        visit(this)
        val queue = Queue<TreeNode<T>>()
        children.forEach { queue.enqueue(it) }
        var node = queue.dequeue()
        while (node != null) {
            visit(node)
            node.children.forEach { queue.enqueue(it) }
            node = queue.dequeue()
        }
    }

    // Поиск в ширину
    fun search(value: T): TreeNode<T>? {
        var result: TreeNode<T>? = null
        forEachLevelOrder {
            if (it.value == value) {
                result = it
            }
        }
        return result
    }
}