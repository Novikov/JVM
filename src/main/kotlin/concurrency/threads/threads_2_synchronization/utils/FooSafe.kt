package concurrency.threads.threads_2_synchronization.utils

import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock



class FooSafe {
    private val lock = ReentrantLock()
    private val firstMethodCalled: Condition = lock.newCondition()
    private val secondMethodCalled: Condition = lock.newCondition()

    fun first() {
        lock.lock()
        try {
            println("first")
            firstMethodCalled.signal()
        } finally {
            lock.unlock()
        }
    }

    fun second() {
        lock.lock()
        try {
            firstMethodCalled.await()
            println("second")
            secondMethodCalled.signal()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } finally {
            lock.unlock()
        }
    }

    fun third() {
        lock.lock()
        try {
            secondMethodCalled.await()
            println("third")
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } finally {
            lock.unlock()
        }
    }
}