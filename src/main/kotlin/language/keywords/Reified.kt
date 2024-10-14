package language.keywords

fun main(){
    reifiedExample()
}

fun reifiedExample(){
    "Some string".printClassName()
}

/** Получаем доступ к имени класса из-за встраивания типа в место вызова*/
inline fun <reified T>T.printClassName(){
    println(T::class.simpleName)
}