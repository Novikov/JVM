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

//https://www.youtube.com/watch?v=vfewyonYXOw&list=PLlb7e2G7aSpQith1Z6xRpU8jFPgkh_Gvz&index=4

//todo type vs class