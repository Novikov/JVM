import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.*


fun main() {
 //   publishSubjectExample()
//    behaviourSubjectExample()
//    replaySubjectExample()
  //  asyncSubjectExample()
//    unicastSubjectExample()
    twoThreadsProblemExample()
//    serializedSubjectExample()
//    eventBusExample()
    // TODO: Реализовать EventBus на subjects и описать назначение его использования. Так же прописать задачу его реализации на корутинах
}

/** Hot emitter способен распространять данные без активных подписок.
 * Subject - hot emitters, которые обладают возможностью быть не только поставщиком событий, но и их потребителем.
 * Как пример можно сделать агрегатор событий, который подпишется на несколько Observable, но в добавок к этому мы сами можем отправлять ему данные
 * Все работает так как в примере с Hot_Emmiters. Данные не будут приходить с самого начала.
 * Отправит следующее событие в каждую подписку. После onComplete прекращает отправку событий и любая новая подписка ничего не получит. */
fun publishSubjectExample() {
    val subject = PublishSubject.create<Int>()
    subject.onNext(1)
    subject.onNext(2)

    subject.subscribeBy(onError = { println(it.message) },
        onComplete = { println("subscriber 1 onComplete") },
        onNext = { println("subscriber 1 onNext - $it") })

    subject.onNext(3)
    subject.onNext(4)

    subject.subscribeBy(onError = { println(it.message) },
        onComplete = { println("subscriber 2 onComplete") },
        onNext = { println("subscriber 2 onNext - $it") })

    subject.onNext(5)
    subject.onNext(6)

    subject.onComplete() //После complete event - subscriber 3 и subscriber 1,2 более не получат событий

    subject.subscribeBy(onError = { println(it.message) },
        onComplete = { println("subscriber 3 onComplete") },
        onNext = { println("subscriber 3 onNext - $it") })

    subject.onNext(7)
    subject.onNext(8)
}

/**
 * Каждая новая подписка получит предыдущее значение и текущее. Предыдущее значение будет получено подписчиком только один раз.
 * Далее данный subject будет отправлять данные в этот subscriber, получивший ранее данные, как publishSubject.
 *
 * behaviourSubjectExample
 * Самое очевидное применение BehaviorSubject - хранение какого-либо статуса. При подключении вы всегда получите либо текущее значение, либо дефолтное*/
fun behaviourSubjectExample() {
    val subject = BehaviorSubject.createDefault(5)
    subject.onNext(1) //Перезатрет дефолтное значение 5

    subject.subscribeBy(onError = { println(it.message) },
        onComplete = { println("subscriber 1 onComplete") },
        onNext = { println("subscriber 1 onNext - $it") })
    subject.onNext(2)

//    subject.subscribeBy(onError = { println(it.message) },
//        onComplete = { println("subscriber 2 onComplete") },
//        onNext = { println("subscriber 2 onNext - $it") })
//    subject.onNext(3)
//
//    subject.onComplete() //После complete event - subscriber 3 более не получит событий
//
//    subject.subscribeBy(onError = { println(it.message) },
//        onComplete = { println("subscriber 3 onComplete") },
//        onNext = { println("subscriber 3 onNext - $it") })
}

/** Для нового подписчика воспроизводит всю предыдущую последовательность событий. Аналогичное поведение как в Behaviour subject, только буфер больше 1
 * После отправки последовательности в подписчик - начинает работать как Publish subject.
 *
 * Данный subject создается с динамическим буфером и может хранить довольно большое число элементов. С помощью createWith() можно ограничить буфер до
 * определенного числа и подписчики увидят только определенное количество последних элементов.
 *
 * Можно конвертнуть Subject в Flowable, но тогда мы потеряем возможность добавлять значения.
 * */
fun replaySubjectExample() {
    val subject = ReplaySubject.create<Int>()
    subject.onNext(1)

    subject.subscribeBy(onError = { println(it.message) },
        onComplete = { println("subscriber 1 onComplete") },
        onNext = { println("subscriber 1 onNext - $it") })
    subject.onNext(2)

    subject.subscribeBy(onError = { println(it.message) },
        onComplete = { println("subscriber 2 onComplete") },
        onNext = { println("subscriber 2 onNext - $it") })
    subject.onNext(3)

    subject.onComplete() //После complete event - subscriber 3 более не получит событий

    subject.subscribeBy(onError = { println(it.message) },
        onComplete = { println("subscriber 3 onComplete") },
        onNext = { println("subscriber 3 onNext - $it") })
}

/** Отправит последнее событие перед Complete и сам Complete втч для новых подписок*/
fun asyncSubjectExample() {
    val subject = AsyncSubject.create<Int>()
    subject.onNext(1)
    subject.onNext(2)
    subject.onNext(3)
    subject.onNext(4)

    subject.subscribeBy(onError = { println(it.message) },
        onComplete = { println("subscriber 1 onComplete") },
        onNext = { println("subscriber 1 onNext - $it") })
    subject.onNext(5)

    subject.onComplete()

    subject.subscribeBy(onError = { println(it.message) },
        onComplete = { println("subscriber 2 onComplete") },
        onNext = { println("subscriber 2 onNext - $it") })

}

/** Subject, на который можно подписать лишь одного получателя. И даже после того как этот один получатель отписался, никто больше не сможет подписаться.
 * При попытке подписаться получим exception в onError 2 подписчика с сообщением "Only a single observer allowed" */
private fun unicastSubjectExample() {
    val subject = UnicastSubject.create<Int>()

    subject.onNext(1)

    subject.subscribeBy(onError = { println(it.message) },
        onComplete = { println("subscriber 1 onComplete") },
        onNext = { println("subscriber 1 onNext - $it") })

    subject.onNext(2)

    subject.subscribeBy(onError = { println(it.message) },
        onComplete = { println("subscriber 2 onComplete") },
        onNext = { println("subscriber 2 onNext - $it") })

    subject.onNext(3)
}


/** При отправке значений в subject из нескольких потоков возникают коллизии (2 потока читают/пишут одну переменную).
 * В подписку приходят не все значения
 * И тут можно или доработать Consumer так чтобы он был потокобезопасен либо можно использовать SerializedSubject,
 * который сам со своей стороны сделает вызов метода onNext потокобезопасным*/
private fun twoThreadsProblemExample() {
    val subject = BehaviorSubject.create<Long>()

    val consumer = object : Consumer<Long> {

        private var sum: Long = 0

        override fun accept(aLong: Long) {
            sum += aLong
        }

        override fun toString(): String {
            return "sum = " + sum;
        }
    }

    subject.subscribe(consumer)

    object : Thread() {
        override fun run() {
            super.run()
            for (i in 0..99999) {
                subject.onNext(1L)
            }
            println("first thread done")
        }
    }.start()

    object : Thread() {
        override fun run() {
            super.run()
            for (i in 0..99999) {
                subject.onNext(1L)
            }
            println("second thread done")
        }
    }.start()

    Thread.sleep(5000)
    println(consumer.toString())
    Thread.sleep(5000)
}

/** Как видно из примера ниже - проблема ушла*/
private fun serializedSubjectExample() {

    val subject = BehaviorSubject.create<Long>().toSerialized()

    val consumer = object : Consumer<Long> {

        private var sum: Long = 0

        override fun accept(aLong: Long) {
            sum += aLong
        }

        override fun toString(): String {
            return "sum = " + sum;
        }
    }

    subject.subscribe(consumer)

    object : Thread() {
        override fun run() {
            super.run()
            for (i in 0..99999) {
                subject.onNext(1L)
            }
            println("first thread done")
        }
    }.start()

    object : Thread() {
        override fun run() {
            super.run()
            for (i in 0..99999) {
                subject.onNext(1L)
            }
            println("second thread done")
        }
    }.start()

    Thread.sleep(5000)
    println(consumer.toString())
    Thread.sleep(5000)
}

/** На основе subjects можно построить event bus и сделать коммуникацию между компонентами android приложения*/
private fun eventBusExample() {
    val eventBus = FinQuotesEventBus()

    eventBus.listen().subscribe { println(it) }

    eventBus.emit(1)
    eventBus.emit(2)
    eventBus.emit(3)
    eventBus.emit(4)
    eventBus.emit(5)

    Thread.sleep(3000)
}

interface UpdateListener {
    fun listen(): Observable<Int>
}

interface UpdateEmitter {
    fun emit(result: Int)
}

class FinQuotesEventBus : UpdateListener, UpdateEmitter {

    private val subject = BehaviorSubject.create<Int>()

    override fun listen(): Observable<Int> = subject.hide()

    override fun emit(result: Int) {
        subject.onNext(result)
    }
}