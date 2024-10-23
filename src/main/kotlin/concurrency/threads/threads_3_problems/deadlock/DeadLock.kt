package concurrency.threads.threads_3_problems.deadlock


fun main(){
    val obj1 = Any()
    val obj2 = Any()
    val obj3 = Any()

    val t1 = Thread(SyncThread(obj1, obj2), "t1")
    val t2 = Thread(SyncThread(obj2, obj3), "t2")
    val t3 = Thread(SyncThread(obj3, obj1), "t3") // Обрати внимание на эту строчку

    t1.start()
    try {
        Thread.sleep(1000)
        t2.start()
        Thread.sleep(1000)
        t3.start()
    } catch (e: InterruptedException) {
        throw RuntimeException(e)
    }
}