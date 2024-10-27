package concurrency.tasks

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap

/**
 * Есть 3 экрана которые вызывают requestAsync*
 * Нужно написать имплементацию интерфейсаCommandExecutor.
 * При этом нужно сделать так чтобы если мы имели несколько одинаковых запросов одновременно,
 * мы выполняли запрос один раз, а не несколько и затем просто дублировали информацию между экранами.
 * Но если новый запрос через время - мы его выполним
 *
 * todo К этой задаче нужно еще вернуться и позаботится об очистке ресурсов
 * */

interface CommandExecutor {
    fun requestAsync(url: String, onResponse: () -> Unit)
}

fun main() {
    //usingRx()
    //usingCoroutines()
    usingThreads()
}

fun usingThreads() {
    val executor = CommandExecutorThreadsImpl()

    // Пример вызова нескольких одинаковых запросов
    executor.requestAsync("https://api.example.com/data") {
        println("Response received for request 1: ${executor.getResult("https://api.example.com/data")}")
    }

    executor.requestAsync("https://api.example.com/data") {
        println("Response received for request 2: ${executor.getResult("https://api.example.com/data")}")
    }

    // Чтобы подождать завершения всех запросов
    Thread.sleep(2000)
}

class CommandExecutorThreadsImpl : CommandExecutor {
    private val ongoingRequests = ConcurrentHashMap<String, RequestWrapper>()
    private val results = ConcurrentHashMap<String, String>() // Кэшируем результаты

    override fun requestAsync(url: String, onResponse: () -> Unit) {
        ongoingRequests[url]?.let { requestWrapper ->
            // Добавляем обработчик, если запрос уже выполняется
            requestWrapper.addResponseHandler(onResponse)
            return
        }

        // Создаем новый запрос
        val requestWrapper = RequestWrapper(url, onResponse)
        ongoingRequests[url] = requestWrapper

        // Запускаем запрос в отдельном потоке
        Thread {
            try {
                // Выполняем сетевой запрос (замените на реальный запрос)
                val response = fakeNetworkRequest(url)
                results[url] = response // Кэшируем результат

                // Вызываем обработчики
                requestWrapper.notifyResponseHandlers()
            } catch (e: Exception) {
                e.printStackTrace() // Обработка ошибок
            } finally {
                ongoingRequests.remove(url) // Убираем завершенный запрос
            }
        }.start()
    }

    fun getResult(url: String): String? {
        return results[url]
    }
}

private class RequestWrapper(private val url: String, private val initialHandler: () -> Unit) {
    private val responseHandlers = mutableListOf<() -> Unit>()

    init {
        responseHandlers.add(initialHandler)
    }

    fun addResponseHandler(handler: () -> Unit) {
        responseHandlers.add(handler)
    }

    fun notifyResponseHandlers() {
        responseHandlers.forEach { it.invoke() }
    }
}

fun usingRx() {
    val executor = CommandExecutorRxImpl()

    // Пример вызова нескольких одинаковых запросов
    executor.requestAsync("https://api.example.com/data") {
        println("Response received for request 1: ${executor.getResult("https://api.example.com/data")}")
    }

    executor.requestAsync("https://api.example.com/data") {
        println("Response received for request 2: ${executor.getResult("https://api.example.com/data")}")
    }

    // Чтобы подождать завершения всех запросов
    Thread.sleep(2000)

    executor.clearDisposables()
}

class CommandExecutorRxImpl : CommandExecutor {
    private val ongoingRequests = ConcurrentHashMap<String, Observable<String>>()
    private val results = ConcurrentHashMap<String, String>()
    val currentDisposable = CompositeDisposable()

    override fun requestAsync(url: String, onResponse: () -> Unit) {
        ongoingRequests[url]?.let { observable ->
            observable.subscribe({ result ->
                onResponse() // добавляем подписку
            }, { error ->
                error.printStackTrace() // Обработка ошибок
            })
            return //non-local
        }

        // Создаем новый запрос
        val observable = createRequestObservable(url)
            .doOnNext { response ->
                results[url] = response // Кэшируем результат
            }
            .doFinally {
                ongoingRequests.remove(url) // Убираем завершенный запрос
            }

        ongoingRequests[url] = observable // Сохраняем ongoing request

        // Подписываемся на новый запрос
       val disposable =  observable.subscribe({ result ->
            onResponse() // Вызываем обработчик после завершения
        }, { error ->
            error.printStackTrace() // Обработка ошибок
        })

        currentDisposable.add(disposable)
    }

    fun getResult(url: String): String? {
        return results[url]
    }

    fun clearDisposables(){
        currentDisposable.clear()
    }
}

private fun createRequestObservable(url: String): Observable<String> {
    return Observable.create { emitter ->
        try {
            val response = fakeNetworkRequest(url)
            emitter.onNext(response)
            emitter.onComplete()
        } catch (e: Exception) {
            emitter.onError(e)
        }
    }
}


private fun usingCoroutines() {
    val executor = CommandExecutorCoroutinesImpl()

    // Пример вызова нескольких одинаковых запросов
    executor.requestAsync("https://api.example.com/data") {
        println("Response received for request 1: ${executor.getResult("https://api.example.com/data")}")
    }

    executor.requestAsync("https://api.example.com/data") {
        println("Response received for request 2: ${executor.getResult("https://api.example.com/data")}")
    }

    // Чтобы подождать завершения всех корутин
    Thread.sleep(2000)
}

class CommandExecutorCoroutinesImpl : CommandExecutor {
    private val ongoingRequests = ConcurrentHashMap<String, Job>() // Кэшируем запросы для избежания ненужных запросов
    private val results = ConcurrentHashMap<String, String>() // Кэшируем результаты для последующей отдачи

    override fun requestAsync(url: String, onResponse: () -> Unit) {
        // Проверяем, есть ли уже запущенный запрос для данного URL
        ongoingRequests[url]?.let { job ->
            job.invokeOnCompletion { onResponse() } //Строчка ниже добавит обработчик в список уже имеющихся обработчиков
            return // не выполняем код ниже
        }

        // Запроса на выполнения не было значит создаем новую корутину
        val job = GlobalScope.launch(Dispatchers.IO) {
            val response = fakeNetworkRequest(url)
            results[url] = response // Кэшируем результат
        }

        ongoingRequests[url] = job // Сохраняем текущий запрос для измежания дублей

        // Добавляем обработчик завершения корутины
        job.invokeOnCompletion {
            // Убираем завершенный запрос из ongoingRequests т.к бэк уже мог измениться
            ongoingRequests.remove(url)
            // Вызываем onResponse, если результат уже кэширован
            if (results.containsKey(url)) {
                onResponse()
            }
        }
    }

    // Добавляем метод для получения результата
    fun getResult(url: String): String? {
        return results[url]
    }
}

private fun fakeNetworkRequest(url: String): String {
    Thread.sleep(1000)
    return "Response from $url"
}