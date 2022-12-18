fun main() {

}

fun simpleLambdaExample() {
    val lam = { println("Hello") }
}

class LambdaHolder(val lam: () -> Unit = { println("Hello") })