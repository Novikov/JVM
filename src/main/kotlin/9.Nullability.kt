fun main() {
    saveCallExample()
}

/** Если выражение вернет null то в b попадет null*/
fun saveCallExample() {
    val a: Int? = null
    val b = a?.minus(2)
    println(b)
}

class NullableClass(val a: Int?)

