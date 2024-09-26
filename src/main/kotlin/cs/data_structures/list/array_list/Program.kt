package cs.data_structures.list.array_list

fun main(){
    val myList = ArrayList<String>()
    myList.add("яблоко")
    myList.add("банан")
    myList.add("апельсин")

    myList.printList()

    val searchFruit = "банан"
    println("Список содержит $searchFruit: ${myList.contains(searchFruit)}")

    val anotherFruit = "киви"
    println("Список содержит $anotherFruit: ${myList.contains(anotherFruit)}")
}