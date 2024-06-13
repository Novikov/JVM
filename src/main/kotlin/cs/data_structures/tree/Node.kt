package cs.data_structures.tree

data class Node<T>(var value: T, var left: Node<T>? = null, var right: Node<T>? = null)