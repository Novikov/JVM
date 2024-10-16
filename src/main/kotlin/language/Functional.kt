package language

// lambda, lambda with receiver, function0, типы лямбд, утечки пямяти
fun main() {
//    lambdaExample1()
//    lambdaExample2()
//    lambdaExample3()
//    lambdaExample4()
//    lambdaExample5()
//    lambdaExample6()
//    lambdaExample7()
    lambdaExample8()
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

/** Ключевое слово inline и noInline
 * Посмотри на bitecode lambdaExample7
 * myFunction превращается в Function0 и создается его экземпляр
 * myInlineFucntion встраивается в место вызова
 * Происходит оптимизация за счет отказа от конвертации в анонимный класс
 * Это снижает нагрузку на сборщик мусора, но увеличивает размер байткода
 *
 * Встраивание функций может увеличить количество сгенерированного кода,
 * но если вы будете делать это в разумных пределах (избегая встраивания больших функций),
 * то получите прирост производительности, особенно при вызове функций с параметрами разного типа внутри циклов.
 *
 * noInline же может применяться только к параметру лямбды
 * Когда мы помечаем лямбду этим ключевым словом то она перестанет инлайниться и будет как обычно
 * создаваться экземпляр анонимного класса, а вот само тело функции (принимающей лямбду) будет инлайниться (смотри байткод)
 * */
fun lambdaExample6() {
    myFunction {
        val a = 10
        println("No Inline")
        //return@myFunction в обычной лямбде доступен только ретерн по метке
    }

    myInlineFunction {
        val b = 20
        println("inline function")
        //retrun в inline лямбде доступен ретерн без метки и с меткой чтобы изюежать нелокального ретерна
    }

    myNoInlineFunction {
        val c = 30
        println("inline function")
        //return@myNoInlineFunction в noInline лямбде доступен только ретерн по метке
    }
}

fun myFunction(action: () -> Unit) {
    action.invoke()
    println("Inside MyFunction")
}

inline fun myInlineFunction(action: () -> Unit) {
    action.invoke()
    println("Inside MyInlineFunction")
}

inline fun myNoInlineFunction(noinline action: () -> Unit) {
    action.invoke()
    println("Inside MyNoInlineFunction")
}

inline fun myCrossInlineFunction(crossinline action: () -> Unit) {
    myFunction {
        action() // Если убрать crossinline то тут будет ошибка
    }
    println("Inside MyNoInlineFunction")
}

/** Локальный и не локальынй return
 *
 * Нужно правильно понимать смысл этих слов. Local значит внутри лямбды, non local значит возврат из внешней функции
 *
 * local return используется для выхода из лямбды, когда выполнение возвращается в контекст, в котором была вызвана лямбда.
 * local return возможен в только в НЕ inline функциях.
 *
 * non-local возврат используется для выхода из окружающей функции, в которой объявлена лямбда.
 * Это возможно, если лямбда используется в контексте inline функции.
 * */
fun lambdaExample7() {
    myFunction {
        println("Локальный возврат из лямбды")
        return@myFunction // Требует метки. Выходит из лямбды и продолжает выполнение функции lambdaExample7
    }
    println("Это после вызова myFunction")

    myInlineFunction {
        println("Не локальный возврат из лямбды")
        return // Не требует метки Выходит из функции lambdaExample7
    }
    println("Эта строка не будет напечатана") // Эта строка не выполнится
}


/**
 * crossinline — ключевое слово, которое используется для указания,
 * что лямбда-выражение не может содержать нелокальных return, даже если оно передано в inline-функцию.
 * */
fun lambdaExample8() {
    myInlineFunction {
        println("Не локальный возврат из лямбды 1")
        return // доступ к non local return есть
    }

    myCrossInlineFunction {
        println("Не локальный возврат из лямбды 2")
        //return запрет
    }
}

/**
 * А все потому что non-local return в этом случае будет по lambdaExample8, что невозможно т.к в вложенной на
 * втором этаже лямдбе в myFunction будет создаваться анонимный класс из которого будет этот return.
 * Об этом говорит тип в видосе https://www.youtube.com/watch?v=a2jPA_h9ltM&t=195s
 * */

/**
 * Когда использовать noInline, а когда crossInline?
 *  noInline отключает оптимизацию лямбды
 *  crossinline means we would like to inline if possible, but we can’t guarantee we can do it or we can only inline it partially
 *
 *
 * todo доработать https://stackoverflow.com/questions/38827186/what-is-the-difference-between-crossinline-and-noinline-in-kotlin
 * */

