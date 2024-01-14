import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


fun main() {
//    debounceExample()
//    throttleFirstExample()
    throttleLastExample()
    throttleLatestExample()
//    distinctExample()
//    distinctUntilChangedExample()
}

/**
 * Каждое событие поставляется с таймером. Если выходит новое событие до того, как истечет таймер - оно перезатрется.
 * В итоге отправится последний элемент во время действия таймера которого не пришел еще один элемент.
 * Используется для поиска, чтобы сократить количество отправляемых запросов.
 * */
fun debounceExample() {

    // Diagram:
// -A--------------B----C-D-------------------E-|---->
//  a---------1s
//                 b---------1s
//                      c---------1s
//                        d---------1s
//                                            e-|---->
// -----------A---------------------D-----------E-|-->

    val source = Observable.create { emitter: ObservableEmitter<String> ->
        emitter.onNext("A")
        Thread.sleep(1500)
        emitter.onNext("B")
        Thread.sleep(500)
        emitter.onNext("C")
        Thread.sleep(250)
        emitter.onNext("D")
        Thread.sleep(2000)
        emitter.onNext("E")
        emitter.onComplete()
    }

    source.subscribeOn(Schedulers.io())
            .debounce(1, TimeUnit.SECONDS)
            .blockingSubscribe(
                    { item: String -> println("onNext: $item") },
                    { obj: Throwable -> obj.printStackTrace() }
            ) { println("onComplete") }
}

fun throttleFirstExample() {
    // Diagram:
// -A----B-C-------D-----E-|-->
//  a---------1s
//                 d-------|-->
// -A--------------D-------|-->

    val source = Observable.create { emitter: ObservableEmitter<String> ->
        emitter.onNext("A")
        Thread.sleep(500)
        emitter.onNext("B")
        Thread.sleep(200)
        emitter.onNext("C")
        Thread.sleep(800)
        emitter.onNext("D")
        Thread.sleep(600)
        emitter.onNext("E")
        emitter.onComplete()
    }

    source.subscribeOn(Schedulers.io())
            .throttleFirst(1, TimeUnit.SECONDS)
            .blockingSubscribe(
                    { item: String -> println("onNext: $item") },
                    { obj: Throwable -> obj.printStackTrace() }
            ) { println("onComplete") }
}

/**
 * Инверсия throttleFirst.
 * В ThrottleFirst таймер включается после выпуска первого элемента и пропускается первое событие.
 * В ThrottleLast таймер включается после выпуска первого элемента и пропускается первое событие после таймера!!!
 * Грубо говоря получаем последнее событие после N секунд.
 * Практический пример - отправка данных GPS локации каждые N секунд. Это Позволит снизить энергопотребление устройства
 * и нагрузку на бэк
 * */
fun throttleLastExample() {
    // Diagram:
// -A----B-C-------D-----E-|-->
// -0s-----c--1s---d----2s-|-->
// -----------C---------D--|-->
    val source = Observable.create { emitter: ObservableEmitter<String> ->
        emitter.onNext("A")
        Thread.sleep(500)
        emitter.onNext("B")
        Thread.sleep(200)
        emitter.onNext("C")
        Thread.sleep(800)
        emitter.onNext("D")
        Thread.sleep(600)
        emitter.onNext("E")
        emitter.onComplete()
    }

    source.subscribeOn(Schedulers.io())
            .throttleLast(1, TimeUnit.SECONDS)
            .blockingSubscribe(
                    { item: String -> println("onNext: $item") },
                    { obj: Throwable -> obj.printStackTrace() }
            ) { println("onComplete") }
}

/**
 * Отличается от throttleFirst и throttleLast тем что в они в промежуток могут прокинуть несколько элементов
 * Этот же как я понимаю прокинет только последнее
 * todo Доработать
 * */
fun throttleLatestExample() {
// -A----B-C-------D-----E-|-->
// -a------c--1s
//            -----d----1s
//                      -e-|-->
// -A---------C---------D--|-->
    val source = Observable.create { emitter: ObservableEmitter<String> ->
        emitter.onNext("A")
        Thread.sleep(500)
        emitter.onNext("B")
        Thread.sleep(200)
        emitter.onNext("C")
        Thread.sleep(800)
        emitter.onNext("D")
        Thread.sleep(600)
        emitter.onNext("E")
        emitter.onComplete()
    }

    source.subscribeOn(Schedulers.io())
            .throttleLatest(1, TimeUnit.SECONDS)
            .blockingSubscribe(
                    { item: String -> println("onNext: $item") }, { obj: Throwable -> obj.printStackTrace() }
            ) { println("onComplete") }
}


/**
 * Отфильтруются все уникальные элементы
 * */
fun distinctExample() {
    val observable1 = Observable.fromIterable(listOf(1, 1, 1, 2, 3, 1, 3))
            .distinct()

    observable1
            .blockingSubscribe { println(it) }
}

/**
 * Учитывается порядок. Сравнивается текущий и предыдущий элемент. Если они одинаковые, то значение отфильтруется.
 * */
fun distinctUntilChangedExample() {
    val observable1 = Observable.fromIterable(listOf(1, 1, 1, 2, 3, 1, 3))
            .distinctUntilChanged()

    observable1
            .blockingSubscribe { println(it) }
}

/**
 * Каждый элемент последовательности приходит с таймером, но в отличие от debounce он не перезатирается после эмита
 * нового элемента. Каждый новый элемент будет пропускаться до тех пор, пока не истечет таймер предыдущего элемента.
 * Пример - фикс спама кнопок.
 * */
