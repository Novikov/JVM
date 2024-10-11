import io.reactivex.Observable
import io.reactivex.functions.Consumer

/**
 * deffred
 * processor
 * */

fun main() {
//    consumerExample()
    optionalExample()
}

fun optionalExample() {
    /** Если в runtime в цепочку попадет null значение то она упадет*/
    val observable = Observable.fromIterable(listOf(1, 2, 3, null))
    observable
        .subscribe { println(it) }

    Thread.sleep(3000)

    /** компилятор иногда защищает, но не всегда, как видно из предыдущего примера.
     * В примере ниже компилятор подсветит*/

    Observable.create<Int?> {
        it.onNext(1)
//        it.onNext(null)
    }
}

private fun consumerExample() {
    // аналог doOnNext в Observer.
    // Используется когда нужно выполнить какую либо операцию над каждым элементом последовательности
    // Если прийдет ошибка, то все упадет


    val observer = Observable.create<Int> {
        it.onNext(1)
        it.onNext(2)
        it.onNext(3)
    }

    val consumer = Consumer<Int>({ println(it) })

    observer.subscribe(consumer)

    Thread.sleep(3000)
}