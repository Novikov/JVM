package language

fun main() {
//    dataClassExample1()
//    dataClassExample2()
    dataClassExample3()
}

/**
 * Зачем они нужны
 * Для классов только с состоянием без поведения
 * Раньше для них необходимо было писать геттеры сеттыы equals и hashcode
 * Теперь это делает сам дата класс
 *
 * Equal сравнивает классы по значению
 * HashCode генерируется на основе полей
 * ComponentN генерируется для деструктуризации
 * Copy генерирует новый инстенс дата класса
 *
 * Хитрые моменты
 * Все методы генерируются на основе полей которые находятся в конструкторе
 * data класс может наследовать другой класс, но от него самого нельзя отнаследоваться и сделать его open
 * */



/**
 * В этом примере есть подвох.
 * Апкаст не меняет тип объекта, а сообщает компилятору что с этим экземпляром мы хотим работать как с его родителем
 * А значит будет вызываться equals и hashcode из потомков где не учитываются родительские поля
 * Нужно это учитывать и прописывать дополнительную проверку на родительский класс if (other is Animal) return kind == other.kind
 * Получается что генерация equals и hashCode немного нарушает принцип барбары лисков переопределяя метод equals без учета родительских свойств
 * */
fun dataClassExample1(){
    val cat:Animal = Cat("Муська") as Animal
    val tiger: Animal = Tiger("Муська") as Animal
    println("${cat.kind} ${cat.javaClass} ${cat.makeVoice()}")
    println("${tiger.kind} ${tiger.javaClass} ${tiger.makeVoice()}")
    println(tiger == cat)
}

open class Animal(val kind: String) {
    override fun equals(other: Any?): Boolean {
        println("here 0")
        println("$javaClass ${other?.javaClass}")
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Animal
        return kind == other.kind
    }

    override fun hashCode(): Int {
        return kind.hashCode()
    }

    open fun makeVoice() = "I'm animal"
}

data class Cat(val name: String) : Animal("Feline") {
    override fun equals(other: Any?): Boolean {
        println("here 1")
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as Cat

        return name == other.name
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }

    override fun makeVoice() = "I'm cat"
}
data class Tiger(val name: String) : Animal("Feline") {
    override fun equals(other: Any?): Boolean {
        println("here 2")
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as Tiger

        return name == other.name
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }

    override fun makeVoice() = "I'm tiger"
}

/**
 * Сама IDE подсвечивает что для дата классов у которых поля типа массив нужно самому переопределить equals и hashcode
 * */
fun dataClassExample2() {
    val person = Person2(arrayOf(8,9,5,1,0,2,9,1,8,2,3))
    val person2 = Person2(arrayOf(8,9,5,1,0,2,9,1,8,2,3))
    println(person == person2) // Если провалиться в байткод на 3 этажа то можно будет увидеть что метод equals на самом деле сравнивает только ссылки без значений (equals из java)

    // Хотя для обычных полей будут сравниваться значения
    val person3 = Person3(name = "Igor", lastName = "Novikov")
    val person4 = Person3(name = "Igor", lastName = "Novikov")
    println(person3 == person4)

    /**
     * Потому что внутри Person2 и Person3 по разному генерятся equals и hashCode.
     * В Person3 учитываются поля, а в Person2 нет.
     * */
}

data class Person2(val number: Array<Int>)
data class Person3(val name: String, val lastName: String)

/**
 * Пример демонстрирует почему необходимо при любой возможности использовать val свойства в data class
 * */
fun dataClassExample3(){
    val map = HashMap<Person4, String>()
    val person1 = Person4("John", 30)
    // Добавляем person1 в HashMap
    map[person1] = "Engineer"
    println(map[person1])  // до изменения
    // Изменяем поле name после добавления в HashMap
    person1.name = "Jack"
    // Пытаемся извлечь объект из HashMap
    println(map[person1])  // после изменения ожидаемый результат: "Engineer"
}

data class Person4(var name: String, var age: Int)

/**
 * Использование val свойств в Data классе дает нам гарантию видимости актуальных значений в нескольких потоках
 * Мы точно знаем что не будет race condition
 * */