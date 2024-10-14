package algorithms.data_structures.tree.binary

data class BinaryNode<T>(var value: T, var left: BinaryNode<T>? = null, var right: BinaryNode<T>? = null)