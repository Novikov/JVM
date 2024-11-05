package design.patterns.creational.prototype

data class UserProfile(
    var name: String,
    var age: Int,
    var email: String
) : Prototype {

    // Реализация метода clone()
    override fun clone(): Prototype {
        // Возвращаем новый объект UserProfile с теми же данными
        return UserProfile(name, age, email)
    }

    override fun toString(): String {
        return "UserProfile(name='$name', age=$age, email='$email')"
    }
}