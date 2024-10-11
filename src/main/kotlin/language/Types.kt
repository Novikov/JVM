package language

fun main(){

}

fun nothingExample(){
    //Нельзя создать эксезмляр
    //val nothing : Nothing = Nothing()

    //Но можно использовать в качестве типа
    val nothingList = listOf<Nothing>()

    //Бесконечный цикл или Exception

}