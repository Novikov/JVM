package design.patterns.creational.factory_abstract

class UsaFactory : TransportFactory {
    override fun createCar(): Car = FordMustang()
    override fun createAirplane(): Airplane = Boeng747()
}