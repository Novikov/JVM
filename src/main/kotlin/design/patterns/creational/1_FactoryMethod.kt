package design.patterns.creational

/**
 * Цель - создание интерфейса который создает объект. При этом выбор того экземпляр какого класса
 * создавать остается за классами который имплементирует данный интерфейс.
 *
 * Используется для делегирования создания экземпляров другому классу.
 *
 * Известен интерфейс, но заранее не известно какая из реализаций будет использоваться
 * Полученеи экземпляра класса в зависимости от входящих условий (параметром может быть сам класс)
 *
 * Примеры из Android
 * -Фабричный метод newInstance фрагмента (Возвращает конкретную реализацию интерфйса Fragment)
 * -ViewModelsProvider.Factory create метод
 *
 * В Android он используется в урезанном варианте. Обычно нет ветвлений на входящие условия. Мы возвращаем
 * заранее известную конкретную реализацию абстрактного типа.
 * */

fun main() {
    val car = CarSelector.getCar(RoadType.CITY)
    car.drive()
}

interface Car {
    fun drive()
}

class Geep : Car {
    override fun drive() {
        println("The Geep is moving at speed 50 km/h")
    }
}

class SportCar : Car {
    override fun drive() {
        println("The SportCar is moving at speed 100 km/h")
    }
}

enum class RoadType {
    CITY, OFF_ROAD
}

//Фабрика со статичным методов
class CarSelector {
    companion object {
        fun getCar(roadType: RoadType): Car {
            return when (roadType) {
                RoadType.CITY -> SportCar()
                RoadType.OFF_ROAD -> Geep()
            }
        }
    }
}