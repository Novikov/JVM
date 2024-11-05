package design.patterns.creational.singleton

/** Создание единственного экземпляра определенного класса*/
fun main() {
    /** Первый способ */
    val firstSingleton = FirstSingleton

    /** Второй способ*/
    val secondSingleton = SecondSingleton.getSecondSingleTon()
}