package concurrency.threads.threads_3_problems.livelock


/**
 * Постоянная передача ресурсов без полезной работы
 * */
fun main(){
    val husband = Eater("Bob")
    val wife = Eater("Alice")

    val s = Spoon(husband)

    Thread { husband.eatWith(s, wife) }.start()
    Thread { wife.eatWith(s, husband) }.start()
}