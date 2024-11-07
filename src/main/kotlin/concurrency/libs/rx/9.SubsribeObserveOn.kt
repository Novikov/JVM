import io.reactivex.Observable
import io.reactivex.ObservableOperator
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

fun main() {
//    withoutChangingThreadExample()
    subscribeOnExample()
//    observeOnExample()
//    unionSubscribeOnObserveOn()
//    doubleObserveOnExample()
  //  doubleSubscribeOnExample()
 //   doubleSubscribeOnExample2()
//    operatorsWithSchedulers()
//    subjectsRulesExample()
  //  subjectsRulesExample2()
}

/**
 * Небольшая обнова. В start android хорошо объяснен смысл различий операторов subscribeOn/observeOn
 * subscribeOn применяется к источнику. Он может применен в любом месте цепочки, применится один раз.
 * observeOn применяется к предыдущему оператору и переключит поток для всех нижестоящих операторов. Позиция важна.
 * */

/** Вся теория рассказана тут https://habr.com/ru/company/rambler_and_co/blog/280388 */

/** Без вызова методов observeOn/subscribeOn все элементы эмиссии будут создаваться и потребляться на главном потоке */
fun withoutChangingThreadExample() {
    val observable: Observable<String> = Observable.create<String> {
        println("Inside start observable [thread] - ${Thread.currentThread().name}")
        it.onNext("Emit 1")
        it.onNext("Emit 2")
        println("Inside intermediate observable [thread] - ${Thread.currentThread().name}")
        it.onNext("Emit 3")
        it.onNext("Emit 4")
        it.onComplete()
    }

    val observer: Observer<String> = object : Observer<String> {
        override fun onComplete() {
            println("Consumption all Completed [thread] - ${Thread.currentThread().name}\"")
        }

        override fun onNext(item: String) {
            println("Consumption next $item [thread] - ${Thread.currentThread().name}")
        }

        override fun onError(e: Throwable) {
            println("Consumption error Occured ${e.message} [thread] - ${Thread.currentThread().name}\"")
        }

        override fun onSubscribe(d: Disposable) {
            println("onSubscribe [thread] - ${Thread.currentThread().name}\"")
        }
    }
    observable.subscribe(observer)

    Thread.sleep(3000)
}

/** subscribeOn() - поток, в котором будет выполняться работа Observable
 *
 * При использовании без observeOn() - влияет как на поток эмиссии так и на поток потребления. На main выполнится только onSubscribe()
 */
fun subscribeOnExample() {
    val observable: Observable<String> = Observable.create<String> {
        println("Inside start observable [thread] - ${Thread.currentThread().name}")
        it.onNext("Emit 1")
        it.onNext("Emit 2")
        Thread.sleep(10000)
        println("Inside intermediate observable [thread] - ${Thread.currentThread().name}")
        it.onNext("Emit 3")
        it.onNext("Emit 4")
        it.onComplete()
    }

    val observer: Observer<String> = object : Observer<String> {
        override fun onComplete() {
            println("Consumption all Completed [thread] - ${Thread.currentThread().name}\"")
        }

        override fun onNext(item: String) {
            println("Consumption next $item [thread] - ${Thread.currentThread().name}")
        }

        override fun onError(e: Throwable) {
            println("Consumption error Occured ${e.message} [thread] - ${Thread.currentThread().name}\"")
        }

        override fun onSubscribe(d: Disposable) {
            println("onSubscribe [thread] - ${Thread.currentThread().name}\"")
        }
    }
    observable
        .subscribe(observer)

    Thread.sleep(3000)
}

/** observeOn() - поток, в котором будет выполняться эмиссия данных из Observable
 * При использовании без subscribeOn() - влияет только на поток потребления данных. Выпуск и onSubscribe будет на main scheduler*/
fun observeOnExample() {
    val observable: Observable<String> = Observable.create<String> {
        println("Inside start observable [thread] - ${Thread.currentThread().name}")
        it.onNext("Emit 1")
        it.onNext("Emit 2")
        println("Inside intermediate observable [thread] - ${Thread.currentThread().name}")
        it.onNext("Emit 3")
        it.onNext("Emit 4")
        it.onComplete()
    }

    val observer: Observer<String> = object : Observer<String> {
        override fun onComplete() {
            println("Consumption all Completed [thread] - ${Thread.currentThread().name}\"")
        }

        override fun onNext(item: String) {
            println("Consumption next $item [thread] - ${Thread.currentThread().name}")
        }

        override fun onError(e: Throwable) {
            println("Consumption error Occured ${e.message} [thread] - ${Thread.currentThread().name}\"")
        }

        override fun onSubscribe(d: Disposable) {
            println("onSubscribe [thread] - ${Thread.currentThread().name}\"")
        }
    }
    observable
        .observeOn(Schedulers.io())
        .subscribe(observer)

    Thread.sleep(3000)
}

/** В результате объединения subscribeOn + observeOn - эмиссия будет происходить на Computation, а поглощение на IO*/

// TODO: Все эти примеры нужно доработать на поход в сеть с помощью Subjects.
//  Потому что выпуск, выполнение работы и подтребление это 3 состояния а тут описано только 2
// Уходить события по идее должны на главном потоке.
//Короче да, рассмотреть все примеры с сетевыми запросами.

fun unionSubscribeOnObserveOn() {
    val observable: Observable<String> = Observable.create<String> {
        println("Inside start observable [thread] - ${Thread.currentThread().name}")
        it.onNext("Emit 1")
        it.onNext("Emit 2")
        println("Inside intermediate observable [thread] - ${Thread.currentThread().name}")
        it.onNext("Emit 3")
        it.onNext("Emit 4")
        it.onComplete()
    }

    val observer: Observer<String> = object : Observer<String> {
        override fun onComplete() {
            println("Consumption all Completed [thread] - ${Thread.currentThread().name}\"")
        }

        override fun onNext(item: String) {
            println("Consumption next $item [thread] - ${Thread.currentThread().name}")
        }

        override fun onError(e: Throwable) {
            println("Consumption error Occured ${e.message} [thread] - ${Thread.currentThread().name}\"")
        }

        override fun onSubscribe(d: Disposable) {
            println("onSubscribe [thread] - ${Thread.currentThread().name}\"")
        }
    }
    observable
        .subscribeOn(Schedulers.computation())
        .observeOn(Schedulers.io())
        .subscribe(observer)

    Thread.sleep(3000)
}

/** Второй observeOn изменит поток потребления который был установлен до этого по цепочке выше*/
fun doubleObserveOnExample() {
    val observable: Observable<String> = Observable.create<String> {
        println("Inside start observable [thread] - ${Thread.currentThread().name}")
        it.onNext("Emit 1")
        it.onNext("Emit 2")
        println("Inside intermediate observable [thread] - ${Thread.currentThread().name}")
        it.onNext("Emit 3")
        it.onNext("Emit 4")
        it.onComplete()
    }

    val observer: Observer<String> = object : Observer<String> {
        override fun onComplete() {
            println("Consumption all Completed [thread] - ${Thread.currentThread().name}\"")
        }

        override fun onNext(item: String) {
            println("Consumption next $item [thread] - ${Thread.currentThread().name}")
        }

        override fun onError(e: Throwable) {
            println("Consumption error Occured ${e.message} [thread] - ${Thread.currentThread().name}\"")
        }

        override fun onSubscribe(d: Disposable) {
            println("onSubscribe [thread] - ${Thread.currentThread().name}\"")
        }
    }
    observable
        .observeOn(Schedulers.io())
        .observeOn(Schedulers.computation())
        .subscribe(observer)

    Thread.sleep(3000)
}

/** Второй subscribeOn никак не повлияет на поток эмиссии и потребления данных*/
fun doubleSubscribeOnExample() {
    val observable: Observable<String> = Observable.create<String> {
        println("Inside start observable [thread] - ${Thread.currentThread().name}")
        it.onNext("Emit 1")
        it.onNext("Emit 2")
        println("Inside intermediate observable [thread] - ${Thread.currentThread().name}")
        it.onNext("Emit 3")
        it.onNext("Emit 4")
        it.onComplete()
    }

    val observer: Observer<String> = object : Observer<String> {
        override fun onComplete() {
            println("Consumption all Completed [thread] - ${Thread.currentThread().name}\"")
        }

        override fun onNext(item: String) {
            println("Consumption next $item [thread] - ${Thread.currentThread().name}")
        }

        override fun onError(e: Throwable) {
            println("Consumption error Occured ${e.message} [thread] - ${Thread.currentThread().name}\"")
        }

        override fun onSubscribe(d: Disposable) {
            println("onSubscribe [thread] - ${Thread.currentThread().name}\"")
        }
    }
    observable
        .subscribeOn(Schedulers.io())
        .subscribeOn(Schedulers.computation())
        .subscribe(observer)

    Thread.sleep(3000)
}

/** с lyft можно добиться желаемого поведения как с doubleObserveOn()*/
fun doubleSubscribeOnExample2() {
    val observable: Observable<String> = Observable.create<String> {
        println("Inside start observable [thread] - ${Thread.currentThread().name}")
        it.onNext("Emit 1")
        it.onNext("Emit 2")
        println("Inside intermediate observable [thread] - ${Thread.currentThread().name}")
        it.onNext("Emit 3")
        it.onNext("Emit 4")
        it.onComplete()
    }

    val observer: Observer<String> = object : Observer<String> {
        override fun onComplete() {
            println("Consumption all Completed [thread] - ${Thread.currentThread().name}\"")
        }

        override fun onNext(item: String) {
            println("Consumption next $item [thread] - ${Thread.currentThread().name}")
        }

        override fun onError(e: Throwable) {
            println("Consumption error Occured ${e.message} [thread] - ${Thread.currentThread().name}\"")
        }

        override fun onSubscribe(d: Disposable) {
            println("onSubscribe [thread] - ${Thread.currentThread().name}\"")
        }
    }
    observable
        .subscribeOn(Schedulers.io())
        .lift(ObservableOperator {
            println("inside lift [thread] - ${Thread.currentThread().name}\"")
            /** Тут можно получить поток из Scheduler указанного после lift. Насколько я понял тут с помощью list можно трансфортировать Observable. Для этого нужно разобрать принцип работы оператора*/
            it
        })
        .subscribeOn(Schedulers.computation())
        .subscribe(observer)

    Thread.sleep(3000)
}

/** Несмотря на то, что в примере не используются observeOn/SubscribeOn с Schedulres - delay меняет поток потребления данных*/
fun operatorsWithSchedulers() {
    val observer: Observer<String> = object : Observer<String> {
        override fun onComplete() {
            println("Consumption all Completed [thread] - ${Thread.currentThread().name}\"")
        }

        override fun onNext(item: String) {
            println("Consumption next $item [thread] - ${Thread.currentThread().name}")
        }

        override fun onError(e: Throwable) {
            println("Consumption error Occured ${e.message} [thread] - ${Thread.currentThread().name}\"")
        }

        override fun onSubscribe(d: Disposable) {
            println("onSubscribe [thread] - ${Thread.currentThread().name}\"")
        }
    }

    val observable = Observable.just("A", "B", "C").delay(1, TimeUnit.SECONDS)

    observable.subscribe(observer)

    Thread.sleep(2000)
}


/** При использовании Subjects стоит учесть то, что по умолчанию потребление данных будет выполняться в том же потоке,
 * в котором был вызван метод onNext(), если в цепи не присутствует observeOn() */
fun subjectsRulesExample() {

    val observer: Observer<String> = object : Observer<String> {
        override fun onComplete() {
            println("Consumption onComplete [thread] - ${Thread.currentThread().name}\"")
        }

        override fun onNext(item: String) {
            println("Consumption onNext $item [thread] - ${Thread.currentThread().name}")
        }

        override fun onError(e: Throwable) {
            println("Consumption onError ${e.message} [thread] - ${Thread.currentThread().name}\"")
        }

        override fun onSubscribe(d: Disposable) {
            println("Consumption onSubscribe [thread] - ${Thread.currentThread().name}\"")
        }
    }

    val subject = BehaviorSubject.create<String>()

    subject
        .doOnNext { println("doOnNext $it [thread] - ${Thread.currentThread().name}\"") }
        .observeOn(Schedulers.computation()) // Посмотри разницу с и без observeOn()
        .subscribe(observer)

    subject.onNext("str")

    Executors.newCachedThreadPool().execute(Runnable {
        Thread.sleep(1000)
        subject.onNext("str2")
    })

    Thread.sleep(4000)
}


/**
 * C Subject есть ньюанс. Поток эмиссии событий всегда будет равено потоку на котором был вызван onNext
 * Если потоком поглощения мы можем управлять через добавление observeOn оператора в цепочку Subject,
 * то на поток эмиссии мы можем влиять только оборачиванием места вызова onNext в другой поток.
 * Об этом нужно всегда помнить при работе с Subjects.
 * */
fun subjectsRulesExample2() {

    val observer: Observer<String> = object : Observer<String> {
        override fun onComplete() {
            println("Consumption onComplete [thread] - ${Thread.currentThread().name}\"")
        }

        override fun onNext(item: String) {
            println("Consumption onNext $item [thread] - ${Thread.currentThread().name}")
        }

        override fun onError(e: Throwable) {
            println("Consumption onError ${e.message} [thread] - ${Thread.currentThread().name}\"")
        }

        override fun onSubscribe(d: Disposable) {
            println("Consumption onSubscribe [thread] - ${Thread.currentThread().name}\"")
        }
    }

    val subject = BehaviorSubject.create<String>()

    subject.onNext("str 0 ")

    subject
        .doOnNext { println("doOnNext $it [thread] - ${Thread.currentThread().name}\"") }
        .subscribeOn(Schedulers.io())
        .doOnEach { println("doOnEach before observeOn $it [thread] - ${Thread.currentThread().name}\"") }
        .observeOn(Schedulers.computation())
        .doOnEach { println("doOnEach after observeOn $it [thread] - ${Thread.currentThread().name}\"") }
        /**
         * Это значит что все flatMap должны быть после этой строчки или заварачивать onNext в цепочку на другом потоке
         *    Observable.just("Событие 1", "Событие 2")
         *             .subscribeOn(Schedulers.io()) // Эмиссия на io-потоке
         *             .subscribe(subject::onNext);
         * */
        .subscribe(observer)

    subject.onNext("str 1")

    Thread.sleep(2000)

    subject.onNext("str2")

    Thread.sleep(6000)
}