fun main() {

}

// Свойство это сочетание поля и методов доступа. В Kotlin это все генерируется под капотом
class SimpleClass(val a: Int)

//Мы можем переопределить методы доступа к полю/
class SimpleClass1(val a: Int) {
    var b: Int = 0
        get() = field
        set(value) {
            field = value
        }
}

// TODO: Расписать Sealed (+ интерфейс и множественное наследование) и Data class