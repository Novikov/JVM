package design.patterns.behavioral.iteator
/**
 * предоставляет способ последовательного доступа ко всем элементам коллекции, не раскрывая её внутренней структуры.
 * */
fun main(){
    // Создаем коллекцию
    val collection = MyCollection(listOf(1, 2, 3, 4, 5))

    // Получаем итератор и проходим по всем элементам
    val iterator = collection.iterator()
    while (iterator.hasNext()) {
        println(iterator.next())
    }
}