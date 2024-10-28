package concurrency.libs.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

// intermediate + terminate

/**
 * Операторы flow делятся на 2 типа. Промежуточные и терминальные.
 * Промежуточные операторы - это все те, что модифицируют map, filter, take, zip, combine, withIndex, scan, debounce, distinctUntilChanged, drop, sample. Все они присутствуют в Rx.
 * Терминальные операторы. collect, single, reduce, count, first, toList, toSet, fold
 * Intermediate операторы берут Flow, добавляют к нему какое-либо преобразование его данных и возвращают новый Flow. Но они не запускают Flow и не получают результаты его работы.
 * А Terminal операторы запускают Flow так же, как это делает collect. Соответственно, результатом их работы является не Flow, а данные, полученные из Flow и обработанные определенным образом.
 *
 * Необходимо помнить что все терминальные операторы это suspend функции которые могут запускаться только в suspend функции*/

/**
 * RxJava operators analogs
 * flatMapMerge - flatMap
 * flatMapConcat - concatMap
 * flatMapLatest - switchMap
 * combine - combineLatest
 * drop - skip
 * catch - onError
 * ...
 * */


/**
 * Intermediate
 * map/mapnotnull
 * switchMap
 * combineLatest
 * debounce/sample
 * delayEach/delayFlow
 * filter/filterNot/filterIsInstance/filterNotNull
 * zip
 * catch
 * onEach/onCompleteon
 *
 *
 * отдельно
 * flatMapConcat
 * flatMapMerge
 *
 * !!!
 * flattenConcat/flattenMerge
 *
 *
 * todo Разобрать launchIn и Collect
 * todo Обязательное завершение флоу для терминальных операторов типо toList. Иначе он навсегда заснет
 * */

suspend fun main() {
//    flowOperatorsExample1()
    flowOperatorsExample2()
}

/**
 * Свой intermediate operator
 * */

fun <T, R> Flow<T>.customMap(transform: suspend (value: T) -> R): Flow<R> = flow {// Создаем новый flow
    collect {value -> //подписываемся на flow
        val newValue = transform(value) //применяем лямбду
        emit(newValue) //эмитим их
    }
}

fun intermediateOperatorExample1() {
    /**
     * В map или любой другой intermediate оператор код попадет только после вызова терминального оператора
     * и только если источник выбросит хотябы 1 элемент
     * */
}

suspend fun performRequest(request: Int): String {
    delay(1000) // imitate long-running asynchronous work
    return "response $request"
}

/**Transform похож на оператор map, но более глобальный*/
suspend fun flowOperatorsExample1() {
    (1..3).asFlow() // a flow of requests
        .transform { request ->
            emit("Making request $request")
            emit(performRequest(request))
        }
        .map { }
        .collect { response -> println(response) }
}

/**Все операции flow выполняются последовательно (событие проходит либо до терминального оператора, либо фильтруется)
 * Посмотри порядок вывода.
 * */
suspend fun flowOperatorsExample2() {
    (1..5).asFlow()
        .filter {
            println("Filter $it")
            it % 2 == 0
        }
        .map {
            println("Map $it")
            "string $it"
        }.collect {
            println("Collect $it")
        }
}