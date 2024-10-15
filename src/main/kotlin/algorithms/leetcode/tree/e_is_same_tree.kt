package algorithms.leetcode.tree

fun main(){

}

fun isSameTree(p: TreeNode?, q: TreeNode?): Boolean {
    // Если оба дерева пустые, они идентичны
    if (p == null && q == null) return true
    // Если одно из деревьев пустое, а другое нет, они не идентичны
    if (p == null || q == null) return false
    // Сравниваем значения текущих узлов
    if (p.`val` != q.`val`) return false
    // Сравниваем поддеревья
    return isSameTree(p.left, q.left) && isSameTree(p.right, q.right)
}