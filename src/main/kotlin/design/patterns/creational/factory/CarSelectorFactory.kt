package design.patterns.creational.factory

//Фабрика со статичным методов
class CarSelectorFactory {
    companion object {
        fun getCar(roadType: RoadType): Car {
            return when (roadType) {
                RoadType.CITY -> SportCar()
                RoadType.OFF_ROAD -> Geep()
            }
        }
    }
}