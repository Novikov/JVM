package cs.algorithms.searching

fun main(){
    val numbers = listOf(4, 35, 2, 1, 5)
}

fun linearContains(value: Int, numbers: List<Int>): Boolean {
    for (element in numbers) {
        if (element == value) {
            return true
        }
    }
    return false
}