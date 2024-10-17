package language

fun main(){
    objectExample1()
    objectExample2()
    objectExample3()
    objectExample4()
}

/**
 * Интересный способ создания анонимного класса
 * */
fun objectExample1(){
    val myObj = object {
        val name = "Класс-одиночка"
        fun printName() {
            println(name)
        }
    }
}

/**
 * Имплементация интерфейса через анонимный класс
 * */
fun objectExample2(){
    val myObj = object : TestInterface {
        override fun testMethod(): Int {
            return 0
        }
    }
}

interface TestInterface {
    fun testMethod() : Int
}

/**
 * Создание статических полей и методов внутри класса
 * */
fun objectExample3(){
    val testClassWithStaticMembers = TestClassWithStaticMembers.Companion
}

class TestClassWithStaticMembers(){
    object TestInnerObject // static final class

    val innerObj = object {
        val lastName : String = ""
    }

    companion object { // Этому компаньену можно задать имя
        const val STATIC_CONST = "STATIC_CONST"
        fun staticMethod(): Int {
            return 0
        }
    }
}

/**
 * Создание объекта синглтона
 * */
fun objectExample4(){
    val singleTone = SingleToneExample
}

object SingleToneExample {
    val name: String = ""
}