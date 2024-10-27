package concurrency.libs.coroutines

import kotlinx.coroutines.*

suspend fun main() {
  //  dispatcherExample1()
   // dispatcherExample2()
//    dispatcherExample3()
    dispatcherExample4()
//    dispatcherExample4_1()
//    dispatcherExample5()
}

/**
 * Очень важный момент касательно разделения потоков. Код внутри launch будет выполняться на собственном потоке, а код внутри coroutineScope
 * на отдельном.
 * */
suspend fun dispatcherExample1() = coroutineScope {
    launch {
        println("Корутина выполняется на потоке: ${Thread.currentThread().name}")
    }
    println("Функция checkThread выполняется на потоке: ${Thread.currentThread().name}")
}

/** Мы сами можем задать диспатчер передав его в билдер*/
suspend fun dispatcherExample2() = coroutineScope {
    launch(Dispatchers.Default) {
        // явным образом определяем диспетчер Dispatcher.Default
        println("Корутина выполняется на потоке: ${Thread.currentThread().name}")
    }
    println("Функция main выполняется на потоке: ${Thread.currentThread().name}")
}

/**
 * При указании Dispatcher мы определяем границы потоков в которых будет выполняться работа.
 * Default - число потоков ограничено ядрами процессора
 * IO - Число потоков до 64 или числу ядер процессора
 * Default используем для высоконагруженных операций, IO не требуют больших затрат CPU поэтому мы их можем создать больше количество.
 * Они не будут ждать друг друга. Если мы будем выполнять тяжелые вычисления в IO то это создаст большую нагрузку на CPU.
 * Важный момеент - при завершении операции. Т.е когда придет ответ - корутина может сменить поток.
 */

/** Main dispatcher можно использовать для выполнения операций на главном потоке. Т.к suspend функции не блокируют поток то это не будет
 * тормозить UI.
 * Смены потока при завершении операции не будет т.к у Main диспатчера только один поток.
 * Т.е мы запускаем операцию на главном потоке. Например, запрос в сеть. Функция приостановится, дожидаясь результата. В это время UI поток
 * продолжит заниматься отрисовкой UI. После прихода результата - функция возобновляется и выполняет операции на главном потоке.
 * */

/** Методы Dispatcher
 * 1)isDispatcherNeeded() - возвращает true если работа корутины должно быть выполнена с помощью dispatch метода. Все диспатчеры кроме unconfined
 * вернут true.
 * 2)dispatch() - отвечает за выполнение переданного callback в другом потоке. Он вызовется самостоятельно корутиной. Из-за вызова dispath() произойдет
 * смена потока. Соответственно выполнение Unconfined диспатчера произойдет в этом же потоке.
 * */
@OptIn(ExperimentalStdlibApi::class)
suspend fun dispatcherExample3() = coroutineScope {
    launch {
        println("${this.coroutineContext[CoroutineDispatcher]?.isDispatchNeeded(this.coroutineContext)}")
    }.join()
}

/**
 * Unconfined (Неограниченный) dispatcher
 * Корутина не закреплена четко за определенным потоком или пулом потоков.
 * Она запускается в текущем потоке до первой приостановки. После возобновления работы корутина продолжает работу
 * в одном из потоков, который сторого не фиксирован. Разработчики языка Kotlin в обычной ситуации не рекомендуют
 * использовать данный тип.
 * */

suspend fun dispatcherExample4() = coroutineScope {
    launch(Dispatchers.Unconfined) { // not confined -- will work with main thread
        println("Unconfined      : I'm working in thread ${Thread.currentThread().name}")
        delay(500)
        println("Unconfined      : After delay in thread ${Thread.currentThread().name}")
    }
    launch (Dispatchers.IO){ // context of the parent, main runBlocking coroutine
        println("main runBlocking: I'm working in thread ${Thread.currentThread().name}")
        delay(1000)
        println("main runBlocking: After delay in thread ${Thread.currentThread().name}")
    }
}

/**
 * Пример задачи: Локальное вычисление на основе пользовательского ввода
 * Предположим, у вас есть приложение, в котором пользователь вводит данные,
 * и вам нужно выполнить легковесное вычисление на основе этого ввода, например, посчитать факториал числа.
 * Поскольку операция не требует долгого выполнения и не взаимодействует с сетью или базой данных, использование
 * Unconfined будет оптимальным.
 *
 * Ну т.е во всех ситуациях когда мы можем начать работу на main потоке и завершить на другом потоке
 *
 * class FactorialActivity : AppCompatActivity() {
 *
 *     private lateinit var inputField: EditText
 *     private lateinit var calculateButton: Button
 *     private lateinit var resultTextView: TextView
 *
 *     override fun onCreate(savedInstanceState: Bundle?) {
 *         super.onCreate(savedInstanceState)
 *         setContentView(R.layout.activity_factorial)
 *
 *         inputField = findViewById(R.id.inputField)
 *         calculateButton = findViewById(R.id.calculateButton)
 *         resultTextView = findViewById(R.id.resultTextView)
 *
 *         calculateButton.setOnClickListener {
 *             calculateFactorial(inputField.text.toString().toIntOrNull())
 *         }
 *     }
 *
 *     private fun calculateFactorial(number: Int?) {
 *         CoroutineScope(Dispatchers.Unconfined).launch {
 *             if (number != null && number >= 0) {
 *                 val result = performFactorial(number)
 *                 resultTextView.text = "Factorial: $result"
 *             } else {
 *                 resultTextView.text = "Please enter a valid number."
 *             }
 *         }
 *     }
 *
 *     private suspend fun performFactorial(n: Int): Long {
 *         return if (n == 0) 1 else n * performFactorial(n - 1) // Рекурсивный расчет факториала
 *     }
 * }
 * */

