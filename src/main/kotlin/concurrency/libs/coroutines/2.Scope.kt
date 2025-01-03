package concurrency.libs.coroutines

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

suspend fun main() {
    //  coroutineScopeExample()
    // coroutineScopeExample2()
//    coroutineScopeExample3()
    // coroutineScopeExample3_1()
    // coroutineScopeExample4()
//    coroutineScopeExample5()
    //coroutineScopeExample6()
    //  coroutineScopeExample7()
    coroutineScopeExample8()


    //  nestedCoroutineExample1()
    //  nestedCoroutineExample2()
//    nestedCoroutineExample3()
    nestedCoroutineExample4()
}

/**
 * Любая корутина может выполняться только на coroutineScope.
 * Coroutine builders — это функции, которые создают и запускают корутины. Они определяют, как будет выполняться корутина.
 *  К основным coroutine builders относятся:
 *
 * launch: Запускает новую корутину, которая не возвращает результат. Используется для выполнения фоновых задач.
 * async: Запускает новую корутину и возвращает объект Deferred, который позволяет получить результат выполнения корутины.
 * runBlocking: Блокирует текущий поток, пока не завершится выполнение корутины. Обычно используется в тестах или в корне программы.
 *
 *
 * Scope содержит внутри себя контекст состоящий из 4 параметров: Job, Dispatcher, ErrorHandler, CoroutineName.
 *
 * Основные scope builders:
 * coroutineScope: Создает новый корутинный контекст, который может содержать дочерние корутины. Если одна из дочерних корутин завершится с ошибкой, остальные будут отменены.
 * supervisorScope: Похож на coroutineScope, но ошибки в одной корутине не приводят к отмене других корутин в этом скоупе.
 *
 *
 * */

/**
 * Основные термины
 *
 * Для запуска корутины необходимы:
 * 1)coroutine scope
 * 2)coroutine builder
 * 3)suspend function с выполняемым кодом
 *
 * Coroutine scope содержит внутри себя coroutineContext
 * coroutine scope можно создать с помощью scope builders:
 * +coroutineScope - Создает новый корутинный скоуп, который может содержать дочерние корутины. Если одна из дочерних корутин завершится с ошибкой, все остальные будут отменены
 * +supervisorScope - Похож на coroutineScope, но ошибки в одной корутине не приводят к отмене других корутин в этом скоупе. Это полезно, когда вам нужно, чтобы несколько корутин работали независимо
 * +globalScope - Предоставляет глобальный скоуп для корутин. Корутин в этом скоупе будет жить до завершения приложения. Используйте его с осторожностью, так как он не связан с жизненным циклом компонентов, например, Activity или ViewModel в Android.
 *
 * После получения coroutine scope - нам необходимо использовать coroutine builder для запуска корутины
 * он будет работать только при наличии scope
 * существует несколько coroutine builders:
 * +launch
 * +asynch
 * +runblocking
 * +withContext
 *
 * Каждый coroutineBuilder возвращает объект Job
 * Через этот объект можно управлять корутиной (например отменять ее) или следить за ее состоянием
 * Job один из элементов которые состовляют CoroutineContext. Job есть как у coroutine builders так и scope builders.
 * Через этот объект они связываются
 *
 * Помимо Job существуют доп параметры CoroutineContext
 * Dispatcher - Указывает, на каком потоке или потоке пуле будет выполняться корутина. Например, Dispatchers.IO для ввода-вывода, Dispatchers.Main для работы с UI в Android, и Dispatchers.Default для фоновых вычислений.
 * CoroutineName: позволяет присвоить корутине имя для удобства отладки.
 * CoroutineExceptionHandler: позволяет обрабатывать необработанные исключения в корутинах.
 * */

suspend fun coroutineScopeExample() {
    val scope = CoroutineScope(Job())
    val jobInstance = scope.launch {
        for (i in 0..5) {
            println(i)
            delay(400L)
        }
    }
    println("Hello Coroutines")
    println("${scope.coroutineContext[Job]} $jobInstance") // распечатает ссылку на job из текущего scope и parent корутины
    Thread.sleep(10000)
}

/**У Global scope отсутствует job, а это значит что не будет формироваться иерархия если мы создадим семейство корутин на данном scope.
 * Время жизни данного scope соответствует времени жизни приложения. Его нужно избегать. Отменить его можно только вручную.
 * Если отсутствует job то значит отсутствует механизм structured concurrency (отмены корутин и проброса исключений). Хоть и выполнится в асинхронной манере.
 * */
suspend fun coroutineScopeExample2() {
    val measuredTime = measureTimeMillis {
        val jobInstance = GlobalScope.launch {
            println("work in first coroutine")
            delay(1000L)
        }

        val jobInstance2 = GlobalScope.launch {
            println("work in second coroutine")
            delay(1000L)
        }
        joinAll(jobInstance, jobInstance2)

        println("Hello Coroutines")
        println("${GlobalScope.coroutineContext[Job]} $jobInstance") // распечатать ссылку на job из текущего scope и parent корутины
    }
    println(measuredTime)
}

/**
 * Функция runBlocking блокирует вызывающий поток, пока все корутины внутри вызова runBlocking { ... } не завершат свое выполнение.
 * Нет необходимости вызывать join(). Не является Scope билдером. Это переходник из синхронного мира в мир корутин т.е билдер корутин
 * */
fun coroutineScopeExample3() {
    runBlocking {
        launch {
            for (i in 0..5) {
                delay(400L)
                println(i)
            }
        }
    }
    println("Hello Coroutines")
}

/** Но нужно помнить, что runBlocking блокирует только поток выполнения пока не выполнится. Корутины внутри потока не блокирует.*/
fun coroutineScopeExample3_1() {
    runBlocking {
        launch {
            for (i in 0..5) {
                delay(400L)
                println(i)
            }
        }

        launch {
            for (i in 5..10) {
                delay(400L)
                println(i)
            }
        }
    }
    println("Hello Coroutines")
}

/**
 * CoroutineScope билдер.
 * Ниже есть поведение которого мы можем добиться с помощью coroutineScope билдера, что сделано в coroutineScopeExample5.
 * Т.е дочерние корутины
 * */
fun coroutineScopeExample4() {
    val scope = CoroutineScope(Job())
    scope.launch {
        val job1 = launch {
            println("Start task 1")
            delay(300)
            // throw Exception()  // тут это добавлено, чтобы сравнить поведение с примером 6
            println("End task 1")
        }
        val job2 = launch {
            println("Start task 2")
            delay(500)
            println("End task 2")
        }

        job1.join()
        job2.join()

        val job3 = scope.launch {
            println("Start task 3")
            delay(100)
            println("End task 3")
        }
    }

    Thread.sleep(2000)
}

/**
 * Есть еще другой вариант - поместить task1 и task2 в launch и вызывать на нем join() до выполнения task3.
 * Данный scope отдаст управление на task3 только когда все его child будут завершены. Т.е теряется конкурентное поведение.
 * В случае выброса exception launch 1,2 и 3 будут отменены.
 * */
suspend fun coroutineScopeExample5() {
    val scope = CoroutineScope(Job())
    scope.launch {
        coroutineScope {
            launch {
                println("Start task 1")
                delay(100)
                throw Exception()  // тут это добавлено, чтобы сравнить поведение с примером 6
                println("End task 1")
            }
            launch {
                println("Start task 2")
                delay(200)
                println("End task 2")
            }
        }

        val job3 = launch {
            println("Start task 3")
            delay(300)
            println("End task 3")
        }
    }

    Thread.sleep(2000)
}

/**
 * SupervisorScope builder
 * Исключение бросится, но launch2 и launch3 продолжат свое выполнение
 * */
suspend fun coroutineScopeExample6() {
    val scope = CoroutineScope(Job())
    scope.launch {
        supervisorScope {
            launch {
                println("Start task 1")
                delay(100)
                throw Exception()
                println("End task 1")
            }
            launch {
                println("Start task 2")
                delay(200)
                println("End task 2")
            }
        }

        val job3 = launch {
            println("Start task 3")
            delay(300)
            println("End task 3")
        }
    }

    Thread.sleep(2000)
}

/**
 * coroutineScope, как и supervisorScope билдеры с вызыванным join() по умолчанию. Код ниже будет ждать пока он перейдет в состояние completed
 * (когда выполнится все внутри и то что ниже)
 * */
suspend fun coroutineScopeExample7() {
    coroutineScope {
        launch {
            delay(5000L)  // имитация продолжительной работы
            println("Hello work!")
        }
    }

    println("string.StrPool has finished")
}

/** Еще один способ создания supervisor scope*/
suspend fun coroutineScopeExample8() {
    val superVisorJob = SupervisorJob()
    val supervisorScope = CoroutineScope(superVisorJob + Dispatchers.Default)
    supervisorScope.launch(superVisorJob) { //Обязательно пробрасывать supervisor job иначе не будет работать supervisor scope behaviour
        launch {
            println("Start task 1")
            delay(100)
            throw Exception()
            println("End task 1")
        }
        launch(superVisorJob) {
            println("Start task 2")
            delay(200)
            println("End task 2")
        }

        val job3 = launch(superVisorJob) {
            println("Start task 3")
            delay(300)
            println("End task 3")
        }
    }

    Thread.sleep(2000)
}

/**
 * todo отредактировать документ
 * расписать функции coroutineScope и supervisorScope. Они возвращают job а не scopoe и не являются билдерами скоупа а выступают как изменителя контекста
 *
 * */


/**
 * Секция вложенных корутин
 * */

/*** Launch билдер создает свой внутренний scope.
 * Родительская корутина выполнила весь свой код не дожидаясь выполнения дочерней. А дочерняя корутина в отдельном потоке выполнила свой код.
 * Хоть родительская корутина и выполнила сразу же весь свой код, но ее статус поменяется на Completed только когда выполнится дочерняя корутина.
 * */
suspend fun nestedCoroutineExample1() = coroutineScope {
    launch {
        println("Outer coroutine start ${Thread.currentThread().name}")
        launch {
            println("Inner coroutine start ${Thread.currentThread().name}")
            delay(400L)
            println("Inner coroutine end ${Thread.currentThread().name}")
        }
        println("Outer coroutine end ${Thread.currentThread().name}")
    }
}

/**
 * Возможно, этот пример кажется вам бессмысленным.
 * Зачем запускать дочернюю корутину в новом фоновом потоке, если родительская корутина и так уже выполняется в фоновом потоке.
 * Можно было бы всю работу сделать в родительской корутине и не плодить потоки.

 * И вот тут я вам должен сказать очень интересную вещь, которая, может поменять ваше текущее представление о корутинах.
 * Постарайтесь понять следующее утверждение, т.к. это очень важно:

 * Корутина может выполняться в main потоке.

 * Выше рассмотренный пример станет выглядеть гораздо более осмысленным и полезным, если код родительской корутины выполняется в main потоке,
 * а код дочерней - в фоновом. Получается, что из main потока мы запустили фоновую работу и подождали ее окончания без каких-либо блокировок и колбэков.
 * join не заблокирует main поток, а только приостановит
 * */
suspend fun nestedCoroutineExample2() = coroutineScope {
    launch {
        println("parent coroutine, start")

        val job = launch {
            println("child coroutine, start")
            delay(1000)
            println("child coroutine, end")
        }

        println("parent coroutine, wait until child completes")
        job.join() //в этом случае будет suspend, а не блокировка потока

        println("parent coroutine, end")
    }
}

/**
 * Если возникает вопрос, почему то же самое нельзя без родительской корутины провернуть, то вспомните, что join - это suspend функция, которая не
 * может быть вызвана вне scope. Todo join может быть вызвана вне scope. Переписать.
 * */

/**
 * В следующем примере показан пример паралельной работы дочерних корутин.
 * Родительская корутина будет их ждать. Общее время выполнения будет 1500
 * */
suspend fun nestedCoroutineExample3() = coroutineScope {
    launch {
        val begin = System.currentTimeMillis()
        println("parent coroutine, start")

        val job = launch {
            delay(1000)
        }

        val job2 = launch {
            delay(1500)
        }

        println("parent coroutine, wait until children complete")
        job.join()
        job2.join()

        val end = System.currentTimeMillis()

        println("parent coroutine, end")
        println("Elapsed time in miliseconds: ${end - begin}")
    }
}

/** Родительская корутина выполнится первой, но будет иметь статус isActive = true до тех пор пока дочерние корутины выполняются.
 * */
suspend fun nestedCoroutineExample4() = coroutineScope {
    val job = launch {
        println("parent start")
        launch {
            println("child start")
            delay(1000)
            println("child end")
        }
        println("parent end")
    }

    launch {
        delay(500)
        println("parent job is active: ${job.isActive}")
        delay(1000)
        println("parent job is active: ${job.isActive}")
    }
}

/**
 * Можно переопределять контекст дочерних корутин путем передачи других параметров.
 * */