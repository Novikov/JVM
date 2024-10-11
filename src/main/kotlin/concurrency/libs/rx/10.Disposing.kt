import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.SerialDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import java.util.concurrent.TimeUnit

fun main() {
    disposableExample()
//    compositeDisposableExample()
//    compositeDisposable2Example()
//    serialDisposableExample()
}

/** RXJava накладывает обязательство отписаться от источника данных после подписки. Иначе может быть memory leak или crash*/

fun disposableExample() {
    val observable: Observable<Long> = Observable.interval(1, TimeUnit.SECONDS);
    val disposable1 = observable.subscribeBy(
        onNext = { println("from 1 subscriber "+ it) },
        onError = { it.printStackTrace() },
        onComplete = { println("Done!") }
    )

    disposable1.dispose() //Метод по очистке подписчика. У disposable доступен только он.

    val disposable2 = observable.subscribeBy(
        onNext = { println("from 2 subscriber "+ it) },
        onError = { it.printStackTrace() },
        onComplete = { println("Done!") }
    )

    Thread.sleep(10000)

    /** В первой RxJava можно было отправлять данные после отписки, что странно*/
}

/**
 * Unlike Disposable, CompositeDisposable has two ways of disposing subscriptions:
 * dispose()
 * clear()
 * Both operations dispose the previously contained subscriptions, but dispose() operation assigns true to the disposed variable,
 * which later does not let you add, remove,or delete any other disposables.
 * */

fun compositeDisposableExample() {

    val list: List<Any> = listOf(1, 2, 3)
    val observable: Observable<Any> = list.toObservable();
    val disposable = observable.subscribeBy(
        onNext = { println(it) },
        onError = { it.printStackTrace() },
        onComplete = { println("Done!") }
    )

    val compositeDisposable = CompositeDisposable()
    compositeDisposable.add(disposable)

    println("number of disposable before dispose - ${compositeDisposable.size()}")
    compositeDisposable.dispose()
    println("number of disposable after dispose - ${compositeDisposable.size()}")

    //Вторая подписка отработает, но disposable2 не добавится в compositeDisposable
    val disposable2 = observable.subscribeBy(
        onNext = { println(it) },
        onError = { it.printStackTrace() },
        onComplete = { println("Done!") }
    )

    compositeDisposable.add(disposable2)
    println("number of disposable after adding one more disposable - ${compositeDisposable.size()}")
}

fun compositeDisposable2Example() {
    val list: List<Any> = listOf(1, 2, 3)
    val observable: Observable<Any> = list.toObservable();
    val disposable = observable.subscribeBy(
        onNext = { println(it) },
        onError = { it.printStackTrace() },
        onComplete = { println("Done!") }
    )

    val compositeDisposable = CompositeDisposable()
    compositeDisposable.add(disposable)

    println("number of disposable before dispose - ${compositeDisposable.size()}")
    compositeDisposable.clear()
    println("number of disposable after dispose - ${compositeDisposable.size()}")

    //Вторая подписка отработает, и после clear -  disposable2 добавится в compositeDisposable
    val disposable2 = observable.subscribeBy(
        onNext = { println(it) },
        onError = { it.printStackTrace() },
        onComplete = { println("Done!") }
    )

    compositeDisposable.add(disposable2)
    println("number of disposable after adding one more disposable - ${compositeDisposable.size()}")
}

// TODO: Разобраться почему compositedisposable.clear() нужно использовать во фрагментах, а compositedisposable.dispose() в activity? По описанию фрагмент не уничтожается. Не совсем понял что имеется ввиду. Дописать после подробного разбора транзакций навигации.

/***У данного типа disposable отсутствуют методы add, clear, size
 * disposable.set() - добавить новый disposable и выполнить dispose на предыдущем
 * disposable.replace() - добавить новый disposable без выполнения replace на предыдущем
 * соответственно может хранить только один disposable
 *
 * Disposable имеет странную tostring() реализацию и выводит вместо имени объекта - переменную isDisposed()*/
fun serialDisposableExample() {
    val serialDisposable = SerialDisposable()

    val list: List<Any> = listOf(1, 2, 3)
    val observable: Observable<Any> = list.toObservable();
    val disposable = observable.subscribeBy(
        onNext = { println(it) },
        onError = { it.printStackTrace() },
        onComplete = { println("Done!") }
    )


    serialDisposable.set(disposable)

    println("current disposable - ${serialDisposable.get()}")

    //Вторая подписка отработает, и после clear -  disposable2 добавится в compositeDisposable
    val disposable2 = observable.subscribeBy(
        onNext = { println(it) },
        onError = { it.printStackTrace() },
        onComplete = { println("Done!") }
    )

    serialDisposable.set(disposable2)
    println("current disposable - ${serialDisposable.get()}")

}

/** С помощью SerialObservable можно реализовать UseCase поиска без использования switchMap()
 * TODO посмотреть сюда еще раз после подробного разбора switchMap()
 * */

//private val serialDisposable by lazy {
//    SerialDisposable()
//}
//
//override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//    super.onViewCreated(view, savedInstanceState)
//
//    view.editText.doAfterTextChanged { text ->
//        serialDisposable.set(
//            api.searchFor(text)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ result ->
//                    println(result)
//                }, { throwable ->
//                    println(throwable)
//                })
//        )
//    }
//}