package data_structures.trees

data class Node<T>(var value: T, var left: Node<T>? = null, var right: Node<T>? = null)