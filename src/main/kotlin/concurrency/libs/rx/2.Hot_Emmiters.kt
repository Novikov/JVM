import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

fun main() {
   // coldObserverProblemsExample()
   // publish_hotObservableExample()
//    replay_hotObservableExample()
    refcount_hotObservableExample()
//    cache_hotObservableExample()
//    connectableObservableExample2()
//    connectableObservableExample3()
//    connectableObservableExample4()
}

/** Этот пример демонстрирует две проблемы:
 * 1)Observable для каждого нового подписчика начинает генерировать данные с начала.И observer1 и observer2 получали
 * данные, начиная с 0, хотя подписались в разное время.
 * 2)Observable начинает свою работу в момент подписки. observer1 подписался на Observable через 3 секунды после создания.
 * И он начал получать данные с начала, т.е. с 0. Значит, все эти три секунды, Observable ничего не делал
 *
 * Т.е. просто создание Observable не приводит ни к чему. Этот созданный Observable ничего не будет делать.
 * Он начнет работу только когда кто-либо подпишется на него. И для каждого нового подписчика он будет начинать работу заново,
 * независимо от предыдущих подписчиков. Такой Observable называется Cold Observable.
 * */
fun coldObserverProblemsExample() {
    val executor = Executors.newSingleThreadScheduledExecutor()

    val coldObservable = Observable.interval(1, TimeUnit.SECONDS)

    val task1 = Runnable { coldObservable.subscribe { println("Subscription 1: $it") } }
    val task2 = Runnable { coldObservable.subscribe { println("Subscription 2: $it") } }

    executor.schedule(task1, 3000, TimeUnit.MILLISECONDS)
    executor.schedule(task2, 5500, TimeUnit.MILLISECONDS)

    Thread.sleep(10500)

    executor.shutdown()
}

/** Connectable Observable - разновидность hot emitter
 * Эмиссия выполнится только после вызова connect(), все проблемы cold observable уйдут
 * 1)Не будет вызова событий с самого начала
 * 2)До момента подписки observable уже будет работать и испускать события
 * Как я понимаю это простейший тип HotObservable. Не настроенный. Далее будут более узкоспециализированные примеры.
 * */

// TODO: Расписать как работает autoconnect()
// TODO: Подумать как это сочетается с различными типами Subjects
fun publish_hotObservableExample() {
    val executor = Executors.newSingleThreadScheduledExecutor()

    val hotObservable = Observable.interval(1, TimeUnit.SECONDS).publish()

    val disposable = hotObservable.connect()

    //connect - для запуска эмиссии
    //dispose - для выключения эмиссии

    val task1 = Runnable { hotObservable.subscribe { println("Subscription 1: $it") } }
    val task2 = Runnable { hotObservable.subscribe { println("Subscription 2: $it") } }

    executor.schedule(task1, 3000, TimeUnit.MILLISECONDS)
    executor.schedule(task2, 5500, TimeUnit.MILLISECONDS)

    Thread.sleep(10500)

    executor.shutdown()
}

/**
 * REFCOUNT
 * Для ConnectableObservable существует возможность сделать так, чтобы он начинал работать при первом появившемся
 * подписчике, и заканчивал после того, как отпишется последний */
fun refcount_hotObservableExample() {
    val executor = Executors.newSingleThreadScheduledExecutor()

    val hotObservable = Observable.interval(1, TimeUnit.SECONDS)
            .publish()
            .refCount() //!!! вернет обычный Observable, но hot
//        .share() //publish().refcount() == share()

    /**
     * Hot Observable - это вовсе необязательно ConnectableObservable. Это может быть и обычный Observable.
     * Главное, что все подписчики получают одни и те же данные. И Observable не стартует работу заново для каждого нового подписчика.
     * */

    var disposable1: Disposable? = null
    var disposable2: Disposable? = null

    val task1 = Runnable { disposable1 = hotObservable.subscribe { println("Subscription 1: $it") } }
    val task2 = Runnable { disposable2 = hotObservable.subscribe { println("Subscription 2: $it") } }
    val task3 = Runnable { disposable1?.dispose() }
    val task4 = Runnable { disposable2?.dispose() }
    val task5 = Runnable { disposable1 = hotObservable.subscribe { println("Subscription 3: $it") } }


    executor.schedule(task1, 1500, TimeUnit.MILLISECONDS)
    executor.schedule(task2, 3000, TimeUnit.MILLISECONDS)
    executor.schedule(task3, 5000, TimeUnit.MILLISECONDS)
    executor.schedule(task4, 6000, TimeUnit.MILLISECONDS)
    executor.schedule(task5, 6500, TimeUnit.MILLISECONDS)

    Thread.sleep(10500)

    executor.shutdown()
}

/**
 * REPLAY
 * Этот Hot Observable кэширует данные и отправляет их всем новым подписчикам, чтобы они ничего не пропустили*/
fun replay_hotObservableExample() {
    val executor = Executors.newSingleThreadScheduledExecutor()

    val hotObservable = Observable.interval(1, TimeUnit.SECONDS)
//        .replay(1) воспроизведет предпоследнее значение
            .replay() // воспроизведет все значения которые были до подписки

    val disposable = hotObservable.connect()

    val task1 = Runnable { hotObservable.subscribe { println("Subscription 1: $it") } }
    val task2 = Runnable { hotObservable.subscribe { println("Subscription 2: $it") } }

    executor.schedule(task1, 3000, TimeUnit.MILLISECONDS)
    executor.schedule(task2, 5500, TimeUnit.MILLISECONDS)

    Thread.sleep(10500)

    executor.shutdown()
}

/**
 * Еще один пример, как можно получить Hot Observable - это оператор cache.
 * Observable, который будет получен в результате работы этого оператора, будет похож на результат операторов replay().autoConnect().
 * Он начинает работу при первом подписчике, хранит все элементы и выдает их каждому новому подписчику (даже если он пропустил).
 *
 * Observable начинает работу после подписки observer1. Далее observer2, после того как подписался, сразу же получает элементы,
 * которые он пропустил. Затем оба отписываются. Но Observable не прекращает работу. Это становится видно, когда observer1 снова подписывается.
 * Он получает все элементы, которые уже были созданы ранее и продолжает получать их, пока последовательность не завершается вызовом onCompleted.
 * Т.е. видно, что Observable не останавливает работу, когда не остается подписчиков.
 * */
fun cache_hotObservableExample() {
    val executor = Executors.newSingleThreadScheduledExecutor()

    val hotObservable = Observable.interval(1, TimeUnit.SECONDS)
            .take(5)
            .cache() //!!! также как и refcount вернет обычный Observable, но hot

    /**
     * Hot Observable - это вовсе необязательно ConnectableObservable. Это может быть и обычный Observable.
     * Главное, что все подписчики получают одни и те же данные. И Observable не стартует работу заново для каждого нового подписчика.
     * */

    var disposable1: Disposable? = null
    var disposable2: Disposable? = null

    val task1 = Runnable { disposable1 = hotObservable.subscribe { println("Subscription 1: $it") } }
    val task2 = Runnable { disposable2 = hotObservable.subscribe { println("Subscription 2: $it") } }
    val task3 = Runnable { disposable1?.dispose() }
    val task4 = Runnable { disposable2?.dispose() }
    val task5 = Runnable { disposable1 = hotObservable.subscribe { println("Subscription 3: $it") } }

    executor.schedule(task1, 1500, TimeUnit.MILLISECONDS)
    executor.schedule(task2, 4000, TimeUnit.MILLISECONDS)
    executor.schedule(task3, 5500, TimeUnit.MILLISECONDS)
    executor.schedule(task4, 6500, TimeUnit.MILLISECONDS)
    executor.schedule(task5, 7500, TimeUnit.MILLISECONDS)

    Thread.sleep(10500)

    executor.shutdown()
}

// TODO: //Выше расписать разницу между replay() и cache() c виду делают тоже самое

// TODO: Расписать disposing и autoConnect() + проставить lifecycle методы для каждого observable.
/** Данный метод возвращает Observable, который будет жить даже после вызова dispose() на всех подписках
 * Если снова создать новую подписку то эмиссия элементов продолжится с последнего успешно-отправленного!
 */
fun connectableObservableExample4() {

    val observable = Observable.interval(1, TimeUnit.SECONDS)
            .doOnDispose { println("Observable dispose") }
            .doOnComplete { println("Observable complete") }
            .publish().autoConnect()

    val sub1 = observable.subscribe({
        println("sub 1 received $it")
        Thread.sleep(500)
    }, {
        println("sub 1 error")
    }, {
        println("sub 1 complete")
    })

    Thread.sleep(3000)


    sub1.dispose()

    val sub2 = observable.subscribe({
        println("sub 2 received $it")
        Thread.sleep(500)

    }, {
        println("sub 2 error")
    }, {
        println("sub 2 complete")
    })

    Thread.sleep(3000)

    sub2.dispose()

    val sub3 = observable.subscribe({
        println("sub 3 received $it")
        Thread.sleep(500)
    }, {
        println("sub 3 error")
    }, {
        println("sub 3 complete")
    })

    Thread.sleep(3000)

    sub3.dispose()

    Thread.sleep(20000)

}

// TODO: Расписать операторы buffer