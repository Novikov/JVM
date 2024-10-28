import io.reactivex.Observable
import java.util.*
import java.util.concurrent.TimeUnit

fun main() {
    //flatting

 //   flatmapExample()
//    flatmapExample1()
//    flatmapExample2()

   // concatMapExample()

//    switchMapExample()
 //   switchMapExample2()
    switchMapExample3()
}

/** flatting */
/** map возвращает Collection с примененной лямбдой на каждый элемент этой Collection */
/** flatMap возвращает Observable c 0,1 или n данными на каждый элемент эмиссии по цепочке выше, не поддерживает порядок эмиссии
 * Очень важно помнить что возвращает не несколько Observable на 1 эмиссию, а 1 Observable с 0...n данными внутри */

//empty
fun flatmapExample() {
    Observable.range(1, 10).flatMap {
        return@flatMap Observable.just(it)
    }.blockingSubscribe {
        println("Received $it")
    }

    Thread.sleep(10000)
}

//1 элемент на каждую эмиссию
fun flatmapExample1() {
    Observable.range(1, 10).flatMap {
        val randDelay = Random().nextInt(10)
        return@flatMap Observable.just(it).delay(randDelay.toLong(), TimeUnit.MILLISECONDS)
    }.blockingSubscribe {
        println("Received $it")
    }
}

//Несколько элементов на каждую эмиссию
fun flatmapExample2() {
    Observable.range(1, 10).flatMap {
        val randDelay = Random().nextInt(10)
        return@flatMap Observable.just("A", it).delay(randDelay.toLong(), TimeUnit.MILLISECONDS)
    }.blockingSubscribe {
        println("Received $it")
    }
}

/** Сoncat map брат близнец flatmap, но с поддержкой порядка эмиссии. Выполняется медленнее чем flatmap*/
fun concatMapExample() {
    Observable.range(1, 10).concatMap {
        val randDelay = Random().nextInt(10)
        return@concatMap Observable.just(it).delay(randDelay.toLong(), TimeUnit.MILLISECONDS)
    }.blockingSubscribe {
        println("Received $it")
    }
}

/**
 * Содержит в себе поведение flatmap и concatmap. Т.е может вернуть Observable с 0, 1 ... N количеством данных на каждый элемент эмиссии по цепочке выше.
 * + Поддерживает порядок эмиссии. Но если возвращаемый observable будет с задержкой - вернется последний, а предыдущие пропустятся.
 *
 * Он лучше всего подходит, для ситуаций где необходимо проигнорировать промежуточные результаты и рассмотреть последний (поиск).
 * SwitchMap отписывается от предыдущего источника Observable всякий раз, когда новый элемент начинает излучать данные,
 * тем самым всегда эмитит данные из текущего Observable */
fun switchMapExample() {
    println("Without delay \n")
    Observable.range(1, 10) //данная эмиссия будет происходить последовательно, как в concatMap.
        .switchMap {
            return@switchMap Observable.just(it, "ASD")
        }.blockingSubscribe { println("Received $it") }
}

fun switchMapExample2() {
    println("\nWith delay \n")
    Observable.range(1, 10).switchMap {
        val randDelay = Random().nextInt(10)
        return@switchMap Observable.just(it, "XYZ").delay(randDelay.toLong(), TimeUnit.MILLISECONDS)
    }.blockingSubscribe {
        println("Received $it")
    }
}

fun switchMapExample3() {
    println("\nWith delay and error \n")
    Observable.range(1, 10).switchMap { counter ->
        val randDelay = Random().nextInt(10)
        return@switchMap Observable.create {
            it.onNext(counter)
            it.onError(RuntimeException("Error"))
            it.onNext("ASD")
        }
            .onErrorReturn { "Error from onErrorReturn" } // обязательно должен присутствовать иначе приложение крашнится даже если есть subscbe с обработкой ошибок.
            .delay(randDelay.toLong(), TimeUnit.MILLISECONDS)
    }.blockingSubscribe({
        println("Received $it")
    }, {
        println("Error")
    })
}