package cs.data_structures.doubly_linked_list

data class Node<T>(var value: T, var next: Node<T>? = null, var prev: Node<T>? = null)