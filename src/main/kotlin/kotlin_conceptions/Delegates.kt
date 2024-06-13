package kotlin_conceptions

fun main() {
    InitSample1().printFields()


}

class InitSample1 {

    /** Гарантируем что инициализируем свойство до первого обращения, иначе получим UninitializedPropertyAccessException.
     * Тем самым уходим от nullable типов там где мы точно уверенны что придет значение, а не null.*/
    lateinit var lateinitField: String

    /** Инициализируем свойство при первом обращении. С помощью данной техники можем ускорить инициализацию класса, отложив работу по инициализации
     * некоторых сложно вычисляемых полей на потом. Не нужно использовать для тривиальных свойств.
     * Потокобезопасен и поддерживает различные режими работы в многопоточном режиме (параметр mode)*/
    private val lazyField by lazy { "LayField" }

    private fun initName() {
        lateinitField = "NameField"
    }

    init {
        initName() // Попробуй закоментировать
    }

    fun printFields() {
        println(lateinitField)
        println(lazyField)
    }
}


/** Делегирование методов **/

/** реализация паттерна декоратор выглядит громоздко*/
class DelegatingCollectionDecorator<T> : Collection<T> {
    private val innerList = arrayListOf<T>()
    override val size: Int get() = innerList.size
    override fun contains(element: T): Boolean = innerList.contains(element)
    override fun isEmpty(): Boolean = innerList.isEmpty()
    override fun iterator(): Iterator<T> = innerList.iterator()
    override fun containsAll(elements: Collection<T>): Boolean =
        innerList.containsAll(elements)
}


/** Уйти от этого мы можем с помощью делегирования.
 * Мы можем делегировать реализацию другому объекту, добавив ключевое слово by.
 * В результате компилятор сам сгенерит имплементации методов и избавит нас от boilerplate**/
class DelegatingCollectionMethods<T>(
    private val innerList: Collection<T> = ArrayList()
) : Collection<T> by innerList {

    // Но некоторые методы мы можем переопределить самостоятельно.
    override fun containsAll(elements: Collection<T>): Boolean =
        innerList.containsAll(elements)
}

/** Делегирование свойств*/

/** Эта особенность позволяет без труда реализоватьсвойства с логикой сложнее, чем хранение данных в соответствующих
полях, без дублирования кода в каждом методе доступа. Например, свойствамогут хранить свои значения в базе данных,
в сеансе браузера, в словаре и так далее.**/

/**
 * В основе этой особенности лежит делегирование: шаблон проектирования, согласно которому объект не сам выполняет
 * требуемое задание, а делегирует его другому вспомогательному объекту. Такой вспомогательный объект называется делегатом.
 * */


/**
 * Класс любого делегата хранит в себе скрытое свойство. Пример.
 * */
