fun main() {

//    expressionVsInstruction()
    smartCastExample()
}

/** В языке KotLin оператор if - это выражение, а не инструкция. Разница между выражениями
и инструкциями состоит в том, что выражение имеет значение, которое можно
использовать в других выражениях, в то время как инструкции всегда являются элементами
верхнего уровня в охватывающем блоке и не имеют собственного значения. В Java
все управляющие структуры - инструкции. В Коtlin большинство управляющих структур,
кроме циклов (for, do и do/while). Другие выражения when, try*/

fun expressionVsInstruction() {
    val a = 5
    val b = 10

    val c = if (a > b) a else b
}

/** Функция, возвращающая выражение напрямую, имеет тело-выражение ( expression body).*/

fun expressionMethod() = 5 + 2

/** Если тело функции заключено в фигурные скобки, мы говорим, что такая
функция имеет тело-блок (Ьlock body)*/

fun bodyBlockMethod(): Int {
    return 5
}

/** Так же, как в функциях с телом-выражением, если тип не указан явно,
компилятор проанализирует инициализирующее выражение и присвоит
его тип переменной.*/
fun variableType() {
    val a = 5
}

/** Smart Cast */
fun smartCastExample() {
    val obj: Any = "The variable obj is automatically cast to a String in this scope"
    if (obj is String) {
        // No Explicit Casting needed. А вот в Java надо это делать после instanceOf.
        println("String length is ${obj.length}")
    }
}

