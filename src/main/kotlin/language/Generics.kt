package language

fun main(){
    invarianceExample()
    //covarianceExample()
    //contraVariantExample()
    // reifiedExample()
}
/**
 * todo в Котлине есть DeclarationSide variance и useSideVariance. В Java такого нет
 * Star projection
 * */

/**
 * Класс vs Тип
 * Класс это одна из реализаций понятия тип
 * Тип это более широкое понятие
 * Например String подтип String?, но класс у них один todo спросить gpt
 * */

/** Вариативность измеряется только по параметрам
 *
 * 1) invariant: 'Container<A> is neither a subtype of Container<B>' nor a supertype (Arrays Kotlin)
 * 2) covariant: if 'A' <: 'B' then 'Container 'A'' <: 'Container<out B>' (List Kotlin)
 *  but you can't pass covariant parameters inside
 * 3) contravariant: if 'A' <: 'B' then 'Container<B>' <: 'Container 'in A'
 *  but you can't have methods that returns contravariant parameters
 *
 *  <: means "is a subtype"
 * */

/** Какие бы не были отношения между типом A и типом B
 * контейнер из A не может быть ни подтипом ни надтипом над контейнером B*/
private fun invarianceExample() {
    var arrInt = arrayOf<Any>(1, 2, 3, 4, 5)
    var arrString = arrayOf<String>("1", "2", "3", "4", "5")
    //arrInt = arrString
}

fun covarianceExample() {
    var listAny = listOf<Any>()
    var listString = listOf<String>("A","B","C")
    listAny = listString
    listAny.forEach { println(it) }
}

fun contraVariantExample(){
    val employerComparator = Comparator<Employer>{p1,p2 -> p1.name.compareTo(p2.name)}
    val programmerList = listOf<Programmer>().sortedWith(employerComparator)
}

open class Employer(val name: String, val lastname: String)

class Programmer(name: String, lastname: String, grade: Int) : Employer(name, lastname)

fun reifiedExample(){
    "Some string".printClassName()
}

/** Получаем доступ к использованию рефлексии из-за встраивания типа в место вызова*/
inline fun <reified T>T.printClassName(){
    println(T::class.simpleName)
}

//https://www.youtube.com/watch?v=vfewyonYXOw&list=PLlb7e2G7aSpQith1Z6xRpU8jFPgkh_Gvz&index=4

//todo type vs class