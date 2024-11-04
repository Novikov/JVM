package language

/**
 * Разобрать
 * -Конструкторы, вторичные конструкторы, getters, setters
 * -Порядок инициализации
 * -ClassLoader
 * -Reflection
 * -Аннотации
 * https://www.youtube.com/watch?v=9OsrWQn8bbI
 * */
fun main() {
    classExample1()
}

/**
 * Первый параметр это property
 * Второй параметр это параметр конструктора
 * В Котлин понятие переменной относится к локальным переменным методов. Все остальное это Property
 * для которых генерится getter. Setter не генерится, но значение задается в конструкторе
 * */
fun classExample1() {
    val propExample = PropertyClass(
        firstParam = "firstParam",
        secondParam = "secondParam",
        thirdParam = "thirdParam"
    )
}

class PropertyClass(
    val firstParam: String, // сгенерится только геттер
    secondParam: String, // ничего не сгенерится
    var thirdParam: String // сгенерится геттер и сеттер
)