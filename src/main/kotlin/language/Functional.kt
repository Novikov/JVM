package language

// lambda, lambda with receiver, function0, типы лямбд, утечки пямяти
fun main(){
//    lambdaExample1()
//    lambdaExample2()
}
/**
 * Lambda это небольшие фрагменты кода которые можно передавать другим функциям.
 * */
fun lambdaExample1() {
    val lambda = { println("Hello")}
    lambdaAsParam(lambda)
}

/** Функция принимающая другую функцию называется highOrder function*/
fun lambdaAsParam(lambda: () -> Unit) {
    lambda()
}

/**
 * Lambda с аргументом
 * */
fun lambdaExample2(){
    val fruits = listOf("Apple", "Banana", "Cherry")

    // Вызываем функцию и передаем лямбду
    processList(fruits) { fruit ->
        println("Fruit: $fruit") // Используем параметр в лямбде
    }

    //Аналогичный вызов через анонимный класс и объяснение откуда берется параметр fruit
    processList(fruits, object : (String) -> Unit {
        override fun invoke(fruit: String) {
            println("Fruit: $fruit")
        }
    })

    //Аналогичный подкапотный вызов
    processList(fruits, object : Function1<String, Unit>{
        override fun invoke(p1: String) {
            println("Fruit $fruits")
        }
    })
}

fun processList(items: List<String>, action: (String) -> Unit) {
    for (item in items) {
        action(item) // Вызов лямбды для каждого элемента
    }
}
