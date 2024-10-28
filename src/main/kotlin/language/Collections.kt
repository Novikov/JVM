package language

fun main(){
    val animalMap = hashMapOf<String, MyAnimal>()
    val animal1 = MyAnimal("Docha")
    val animal2 = MyAnimal("Manusha")

    println("${animal1.hashCode()} ${animal2.hashCode() }")

    animalMap.put("Key1",animal1)
    animalMap.put("Key2",animal2)
    println(animalMap)

    animalMap.containsValue(animal1)
}

data class MyAnimal(val name: String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as MyAnimal
        return name == other.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}

