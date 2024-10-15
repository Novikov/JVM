package algorithms.leetcode.tree

fun main() {

}

fun maxDepth(root: TreeNode?): Int {
    if (root == null) return 0 // Если узел пустой, глубина равна 0
    // Рекурсивно находим глубину левого и правого поддерева и берем максимум + текущая позиция
    val leftMaxDepth = maxDepth(root.left)
    val rightMaxDepth = maxDepth(root.right)
    return if (leftMaxDepth > rightMaxDepth) {
        leftMaxDepth + 1
    }
    else{
        rightMaxDepth + 1
    }
}