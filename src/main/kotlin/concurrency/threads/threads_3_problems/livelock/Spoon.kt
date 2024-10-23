package concurrency.threads.threads_3_problems.livelock

class Spoon(var owner: Eater?) {

    @Synchronized
    fun changeOwner(d: Eater) {
        owner = d
    }

    @Synchronized
    fun use() {
        System.out.printf("%s has eaten!", owner?.name)
    }
}