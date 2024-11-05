package design.patterns.creational.builder
/**
 * Гибкий способ создания экзепляров класса
 *
 * Пример использования в Android Framework:
 * AllertDialog Builder
 * Notification Builder
 * */
fun main(){
    val person = Person.Builder()
        .name("John Doe")
        .age(30)
        .address("123 Main St")
        .phone("555-1234")
        .build()

    println(person)
}