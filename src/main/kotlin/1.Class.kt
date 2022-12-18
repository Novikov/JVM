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

//data class нельзя сделать open и унаследоваться
data class SampleDataClass(val a: Int = 5, val b: Int = 10) {
    val c = 20 // данное свойство не будет включено в equals и hash code в data class
}