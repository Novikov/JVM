package design.patterns.creational.builder

class Person private constructor(
    val name: String,
    val age: Int,
    val address: String?,
    val phone: String?
) {
    // Вложенный класс Builder c дефолтными полями
    data class Builder(
        var name: String = "",
        var age: Int = 0,
        var address: String? = null,
        var phone: String? = null
    ) {
        fun name(name: String) = apply { this.name = name }
        fun age(age: Int) = apply { this.age = age }
        fun address(address: String?) = apply { this.address = address }
        fun phone(phone: String?) = apply { this.phone = phone }

        // Метод для создания объекта Person
        fun build(): Person {
            if (name.isEmpty()) throw IllegalArgumentException("Name must be provided")
            return Person(name, age, address, phone)
        }
    }
}