sealed class MySealedClass {
    class FirstChild : MySealedClass() //Обрати внимание тут скобки есть
}

sealed interface MySealedInterface {
    class SecondChild : MySealedInterface //А тут скобоки не нужны
    class SecondChild2 :
        MySealedInterface // При добавлении еще одного элемента выражение when попросит прописать else branch.
}

class SealedTestClass() {
    fun testClass(mySealedInterface: MySealedInterface) {
        when (mySealedInterface) {
            is MySealedInterface.SecondChild -> TODO()
            else -> {} // без else ветки будет ошибка компиляции
        }
    }
}