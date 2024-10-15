package language

// lambda, lambda with receiver, function0, типы лямбд, утечки пямяти
fun main() {
//    lambdaExample1()
//    lambdaExample2()
//    lambdaExample3()
//    lambdaExample4()
    lambdaExample5()
}

/**
 * Lambda это небольшие фрагменты кода которые можно передавать другим функциям.
 * */
fun lambdaExample1() {
    val lambda = { println("Hello") }
    lambdaAsParam(lambda)
}

/** Функция принимающая другую функцию называется highOrder function*/
fun lambdaAsParam(lambda: () -> Unit) {
    lambda()
}

/**
 * Lambda с аргументом
 * */
fun lambdaExample2() {
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

    //Аналогичный подкапотный вызов. Lambda под декомпиляцией будет конвертироваться в определенный интерфейс Function в зависимости от количества параметров
    processList(fruits, object : Function1<String, Unit> {
        override fun invoke(fruit: String) {
            println("Fruit $fruit")
        }
    })
}

fun processList(items: List<String>, action: (String) -> Unit) {
    for (item in items) {
        action(item) // Вызов лямбды для каждого элемента
    }
}

/**
 * Lambda with Receiver
 * Позволяет использовать контекст объекта внутри лямбды, что упрощает доступ к его свойствам и методам.
 * */
fun lambdaExample3() {
    val person = Person("Igor")
    processPerson(person) {
        println("Hello ${name}")
    }
}

/**
 * Если переписать без Receiver то мы будем обязаны использовать параметр лямбды it или создавать свой
 * */
fun lambdaExample4() {
    val person = Person("Igor")
    processPerson2(person) {
        println("Hello ${it.name}")
    }
}

fun processPerson(person: Person, personAction: Person.() -> Unit) {
    personAction(person)
}

fun processPerson2(person: Person, personAction: (Person) -> Unit) {
    personAction(person)
}

class Person(val name: String)

/**
 * В Kotlin лямбды могут захватывать переменные из своей окружающей области видимости.
 * Это означает, что лямбда может использовать переменные, которые определены вне её тела.
 * Вот пример, который иллюстрирует это:
 *
 * Захват переменной: В этом примере переменная counter, определенная в main, захватывается лямбдой incrementCounter.Лямбда имеет доступ к counter и может изменять его значение.
 * Изменение значения: Каждый раз, когда мы вызываем incrementCounter(), значение counter увеличивается на 1, и это значение выводится на экран.
 * Состояние переменной: После нескольких вызовов лямбды мы можем увидеть, что переменная counter сохраняет своё состояние и его значение увеличивается.
 * Такой механизм захвата переменных позволяет создавать замыкания, которые могут хранить состояние и изменять его между вызовами.
 * */
fun lambdaExample5() {
    var counter = 0

    // Лямбда, захватывающая переменную counter
    val incrementCounter: () -> Unit = {
        counter++
        println("Counter: $counter")
    }

    incrementCounter() // Counter: 1
    incrementCounter() // Counter: 2
    incrementCounter() // Counter: 3

    // Показать, что переменная counter всё ещё доступна
    println("Final counter value: $counter") // Final counter value: 3
}

/**
 * Захват переменных в лямбдах может стать причиной утечек памяти в Android,
 * особенно если лямбда захватывает ссылки на объекты с длительным временем жизни, такие как Activity или Context.
 *
 * Как избежать утечек памяти:
 * Используйте WeakReference: Если необходимо сохранить ссылку на объект с длительным временем жизни, используйте WeakReference, чтобы избежать удерживания объекта в памяти.
 *
 * Избегайте захвата контекста: Вместо захвата Activity или Context передавайте только необходимые данные. Если вам нужен контекст, используйте getApplicationContext().
 *
 * Очистка ссылок: Убедитесь, что ссылки на лямбды очищаются, когда они больше не нужны, например, с помощью removeCallbacks() для Handler.
 *
 * Используйте lifecycleScope или viewModelScope: Для запуска корутин в Android используйте lifecycleScope или viewModelScope, чтобы автоматически управлять временем жизни.
 * */
