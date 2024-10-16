package language

fun main(){
    reifiedExample()
}

fun reifiedExample(){
    "Some string".printClassName()
}

/** Получаем доступ к использованию рефлексии из-за встраивания типа в место вызова*/
inline fun <reified T>T.printClassName(){
    println(T::class.simpleName)
}