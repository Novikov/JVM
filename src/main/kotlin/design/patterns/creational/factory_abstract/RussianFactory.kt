package design.patterns.creational.factory_abstract

class RussianFactory : TransportFactory {
    override fun createCar(): Car = Lada()
    override fun createAirplane(): Airplane = Tu154()
}