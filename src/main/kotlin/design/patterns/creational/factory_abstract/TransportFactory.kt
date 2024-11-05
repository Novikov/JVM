package design.patterns.creational.factory_abstract

interface TransportFactory {
    fun createCar(): Car
    fun createAirplane(): Airplane
}