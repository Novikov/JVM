package kotlinhard

fun main() {
    val initSample = InitSample()
    println(initSample.lateinitField)
    println(initSample.lazyField)
}

class InitSample {

    /** Гарантируем что инициализируем свойство до первого обращения, иначе получим UninitializedPropertyAccessException.
     * Тем самым уходим от nullable типов там где мы точно уверенны что придет значение, а не null.*/
    lateinit var lateinitField: String

    /** Инициализируем свойство при первом обращении. С помощью данной техники можем ускорить инициализацию класса, отложив работу по инициализации
     * некоторых сложно вычисляемых полей на потом. Не нужно использовать для тривиальных свойств.*/
    val lazyField by lazy { "LayField" }

    private fun initName(){
        lateinitField = "NameField"
    }

    init {
        initName()
    }
}