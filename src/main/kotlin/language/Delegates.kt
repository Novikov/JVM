package language

import kotlin.properties.Delegates
import kotlin.reflect.KProperty


/**
 * Документация по делегатам - https://kotlinlang.org/docs/delegated-properties.html#translation-rules-for-delegated-properties
 * Там есть объяснение генерирования вспомогательных свойств. Под декомпиляцией его не видно, но оно есть.
 * */

fun main() {
    /** Пример делегирования методов*/
    val a = DelegatingCollectionMethods<String>().containsAll(listOf("HOHOHO"))
    println(a)

    println("---------------------------------------------")

    /** Делгирование свойств. Тут реализовано и получение значения через делегат и уведомление об изменении значения property */
    val propertyDelegationExample = PropertyDelegationExample()
    println(propertyDelegationExample.p)
    propertyDelegationExample.p = "new value"

    println("---------------------------------------------")

    /** Использование делегатов Kotlin*/
    val kotlinDelegatesExample = KotlinDelegatesExample()
    kotlinDelegatesExample.printLateFields() // lazy & lateinit
    kotlinDelegatesExample.observableField = "new value" //observable
    kotlinDelegatesExample.vetoableField = 0 // vetoable
}

/**1) Делегирование методов **/

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

    // Но некоторые методы мы можем переопределить самостоятельно. Приоритет вызова у них будет выше
    override fun containsAll(elements: Collection<T>): Boolean = true
//        innerList.containsAll(elements)
}

/**2) Делегирование свойств*/

/** Эта особенность позволяет без труда реализоватьсвойства с логикой сложнее, чем хранение данных в соответствующих
полях, без дублирования кода в каждом методе доступа. Например, свойствамогут хранить свои значения в базе данных,
в сеансе браузера, в словаре и так далее.**/

/**
 * В основе этой особенности лежит делегирование: шаблон проектирования, согласно которому объект не сам выполняет
 * требуемое задание, а делегирует его другому вспомогательному объекту. Такой вспомогательный объект называется делегатом.
 * После декомпиляции скрытое свойство не появляется.
 * */

class PropertyDelegationExample {
    var p: String by MyCustomDelegate()
}
class MyCustomDelegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }
}

class KotlinDelegatesExample {

    /** Гарантируем что инициализируем свойство до первого обращения, иначе получим UninitializedPropertyAccessException.
     * Тем самым уходим от nullable типов там где мы точно уверенны что придет значение, а не null. Не является делегатом*/
    lateinit var lateinitField: String

    /** Инициализируем свойство при первом обращении. С помощью данной техники можем ускорить инициализацию класса, отложив работу по инициализации
     * некоторых сложно вычисляемых полей на потом. Не нужно использовать для тривиальных свойств.
     * Потокобезопасен и поддерживает различные режими работы в многопоточном режиме (параметр mode)*/
    private val lazyField by lazy { "LayField" }

    /** Используем для уведомления об изменении поля. Листенер сработает после присвоения нового значения*/
    // TODO: Igor Novikov разберись как работает kProperty и что с ним можно делать в данном property
    var observableField by Delegates.observable("initial value") { kProperty, oldValue, newValue ->
        println("$oldValue -> $newValue")
    }

    /** Лямбда выполнится до того как присвоится значение. Значение присвоится только если лямбда вернет true.*/
    var vetoableField by Delegates.vetoable(1){ kProperty, oldValue, newValue ->
        newValue > oldValue
    }

    private fun initName() {
        lateinitField = "NameField"
    }

    init {
        initName() // Попробуй закоментировать
    }

    fun printLateFields() {
        println(lateinitField)
        println(lazyField)
    }
}


