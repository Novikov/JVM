package design.patterns.creational.prototype

/**
 * Шаблон проектирования Prototype используется для создания новых объектов путём клонирования существующих,
 * а не путём создания новых с нуля. Этот паттерн особенно полезен, когда создание нового объекта является дорогим
 * или трудоёмким процессом, или когда нужно создавать множество объектов, похожих на существующие, но с небольшими изменениями.
 * */
fun main(){
    // Создаем исходный объект UserProfile
    val originalProfile = UserProfile("John Doe", 25, "john.doe@example.com")
    println("Original Profile: $originalProfile")

    // Создаем клон объекта UserProfile
    val clonedProfile = originalProfile.clone() as UserProfile
    clonedProfile.name = "Jane Doe"
    clonedProfile.email = "jane.doe@example.com"

    // Выводим оба профиля
    println("Cloned Profile: $clonedProfile")
}