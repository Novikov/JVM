import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.toObservable
import io.reactivex.rxkotlin.zipWith
import java.util.concurrent.TimeUnit

fun main() {
    /** Операторы преобразования. Все они возвращают новый Observable */

    //zipping (1 результирующее событие когда 2 источника сделали эмиссию)
    zippingExample1()
//    zippingExample2()
//    zippingExample3()

    //other zip operators (нужно помнить что если у второго источника нет данных то эмиссия не произойдет!!!)
//    combineLatestExample() //(менее строгий чем zip. 1 делает эмиссию и она комбинируется с последним из второго, без приоритета)
//    withLatestFromExample() //Тоже самое, но с приоритетом относительно того на кого применен оператор.

    //merging сразу выдает результирующее событие когда один из источников его эмитит. Не важно есть данные во втором или нет
//    mergingExample1()
//    mergingExample2()
//    mergingExample3()

    //grouping
//    groupingExample()

    //concatenating
//    concatenatingExample1()
//    concatenatingExample2()

    //scanning - аккомуляция событий. имеем предыдущее и текущее, можем использовать для накопления состояния
//    scanningExample1()
//    scanningExample2()
//    scanningExample3()

    //Остальные
//    bufferExample() //Кеширование
//    distinctExample() //Пропуск только уникальных элементов

    /** Необходимо расписать throttle операторы*/
}

/** Zipping */

/** Zip выдаст результирующее событие только когда все внутренние observable, использует Bifunction для указания того как соединять элементы двух эмиссий.*/
fun zippingExample1() {
    val observable1 = Observable.range(1, 10)
    val observable2 = Observable.range(11, 10)
    Observable.zip(observable1, observable2) { emissionO1, emissionO2 ->
        emissionO1 + emissionO2
    }.subscribe {
        println("Received $it")
    }
}

fun zippingExample2() {
    val observable1 = Observable.range(1, 10)
    val observable2 = listOf(
        "String 1",
        "String 2",
        "String 3",
        "String 4",
        "String 5",
        "String 6",
        "String 7",
        "String 8",
        "String 9",
        "String 10"
    ).toObservable()

    // Это котлиновская функция. Использует не bifunction, а функцию расширения/
    observable1.zipWith(observable2) { e1: Int, e2: String -> "$e2 $e1" }.subscribe {
        println("Received $it")
    }
}

/** Выпуск события когда вышла 2 события из каждого Observable */
fun zippingExample3() {
    val observable1 = Observable.interval(100, TimeUnit.MILLISECONDS)
    val observable2 = Observable.interval(250, TimeUnit.MILLISECONDS)

    Observable.zip(observable1, observable2, BiFunction { t1: Long, t2: Long -> "t1: $t1, t2: $t2" }).subscribe {
        println("Received $it")
    }

    Thread.sleep(1000)
}

/** Каждый новый элемент из первой эмиссии комбинируются с последним элементом из второй эмиссии (нет приоритета на один из observable)*/
fun combineLatestExample() {
    val observable1 = Observable.interval(100, TimeUnit.MILLISECONDS)
    val observable2 = Observable.interval(250, TimeUnit.MILLISECONDS)

    Observable.combineLatest(observable1, observable2, BiFunction { t1: Long, t2: Long -> "t1: $t1, t2: $t2" })
        .subscribe {
            println("Received $it")
        }

    Thread.sleep(1000)
}

/** В результате имеем событие на каждое событие из onservable1, а из observable2 будет браться последнее значение. Отличается от combineLatest тем то что
 * тут есть приоритет к observable на котором применен оператор. */
fun withLatestFromExample() {
    val observable1 = Observable.interval(100, TimeUnit.MILLISECONDS)
    val observable2 = Observable.interval(250, TimeUnit.MILLISECONDS)

    observable1.withLatestFrom(observable2, BiFunction { t1: Long, t2: Long -> "t1: ${t1 + 100}, t2: $t2" })
        .subscribe { println("Received $it") }

    Thread.sleep(1000)
}


/** Merging*/

/** Merge выдаст результирующее событие когда один из внутренние observable выпусутит событие. Этим и отличается от zip */
fun mergingExample1() {
    val observable1 = Observable.interval(100, TimeUnit.MILLISECONDS)
    val observable2 = Observable.interval(250, TimeUnit.MILLISECONDS)

    Observable.merge(observable1, observable2).subscribe {
        println("Received $it")
    }

    Thread.sleep(1500)
}

/** перегрузка mergeWith*/
fun mergingExample2() {
    val observable1 = listOf("Kotlin", "Scala", "Groovy").toObservable()
    val observable2 = listOf("Python", "Java", "C++", "C").toObservable()
    observable1.mergeWith(observable2).subscribe {
        println("Received $it")
    }
}

/** merge для n observables*/
fun mergingExample3() {
    val observable1 = listOf("A", "B", "C").toObservable()
    val observable2 = listOf("D", "E", "F", "G").toObservable()
    val observable3 = listOf("I", "J", "K", "L").toObservable()
    val observable4 = listOf("M", "N", "O", "P").toObservable()
    val observable5 = listOf("Q", "R", "S", "T").toObservable()
    val observable6 = listOf("U", "V", "W", "X").toObservable()
    val observable7 = listOf("Y", "Z").toObservable()

    //принимает VarArgs
    Observable.mergeArray(
        observable1, observable2, observable3, observable4, observable5, observable6, observable7
    ).subscribe {
        println("Received $it")
    }
}

/** Grouping*/

/** Иногда возникает необходимость отправить сгруппированные данные
 *В коде сначала выделяется целевая группа с числами которые делятся на 5
 * затем выделяются другие группы*/
fun groupingExample() {
    val observable = Observable.range(1, 30)

    observable.groupBy {
        it % 5
    }.blockingSubscribe {
        println("Key ${it.key} ")
        it.subscribe {
            println("Received $it")
        }
    }
}

/** Concatenating*/

/** Эмиссия из второго observable начнется только после выпуска concat из первого observable.
 * Как и merge данный оператор имеет concatArray и concatWith варианты */
fun concatenatingExample1() {
    val observable1 = Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
//            .take(2)
        .map { "X $it" }

    val observable2 = Observable.just(11, 12, 13, 14, 15, 16, 17, 18, 19, 20).map { "Y $it" }

    Observable.concat(observable1, observable2).subscribe {
        println("Received $it")
    }

    Thread.sleep(50000)
}

/** Иногда работая с несколькими источниками данных возникает необходимость принимать события только от одного.
 * Для этого есть оператор amb() в переводе ambiguous(двусмысленный)
 * Будет работать тот эмиттер, который указан первым параметром.
 * Т.е если есть несколько источников мы можем переключаться между ними по нашему желанию.
 * Сначала принять и обработать данные с первого ситочника, затем еще раз зающать этот оператор и сделать тоже самое, но уже для второгоё
 * источника.
 * */

fun concatenatingExample2() {
    val observable1 = Observable.range(1, 10).map { "X $it" }
    val observable2 = Observable.range(50, 100).map { "Y $it" }

    Observable.amb(listOf(observable1, observable2)).subscribe {
        println("Received $it")
    }

    Thread.sleep(1500)
}

/** Scanning */

/** Оч похоже на котлиновскую функцию fold */
fun scanningExample1() {
    Observable.range(1, 10).scan { previousAccumulation, newEmission -> previousAccumulation + newEmission }
        .subscribe { println("Received $it") }
}

fun scanningExample2() {
    listOf("String 1", "String 2", "String 3", "String 4").toObservable()
        .scan { previousAccumulation, newEmission -> previousAccumulation + " " + newEmission }
        .subscribe { println("Received $it") }
}

fun scanningExample3() {
    Observable.range(1, 5).scan { previousAccumulation, newEmission -> previousAccumulation * 10 + newEmission }
        .subscribe { println("Received $it") }
}

fun bufferExample() {
    val observable1 = Observable.interval(100, TimeUnit.MILLISECONDS)
        .buffer(3) //Кеширование 3х элементов и отправка списка с результатом

    observable1
        .subscribe { println(it) }

    Thread.sleep(3000)
}

fun distinctExampleAndBufferExample() {
    val observable1 = Observable.fromIterable(listOf(1,2,3,4,5,6,7,8,9,1,2,3))
        .distinct()
        .buffer(3) //Кеширование 3х элементов и отправка списка с результатом

    observable1
        .subscribe { println(it) }

    Thread.sleep(3000)
}