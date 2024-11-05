package design.patterns.creational.factory_abstract
/**
 * Фабрика по созданию семейства обхектов разных типов
 * Объекты внутри фабрики связаны по смыслу или по задаче, но не связаны по типу
 * Используется когда нужно создать большое количество разнотипных объектов в одном месте программы
 *
 * Пример реализации в Android Framework отсутствует. В основном этим шаблоном пользуются непосредстенно разработчики
 * */
fun main(){
    val russianFactory: TransportFactory = RussianFactory()
    val car = russianFactory.createCar()
    val airplane = russianFactory.createAirplane()
}
