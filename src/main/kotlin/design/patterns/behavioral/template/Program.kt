package design.patterns.behavioral.template

/** Определяет основу класса и позволяет подклассам переопределять некоторые его части,
 * не изменяя его структуру в целом
 *
 * Грубо говоря мы выносим в абстрактный класс всю общую логику,
 * заставляя наследников переопределить только уникальную логину
 *
 * Пример в Android:
 * BaseViewModel, BaseFragment
 * */
fun main() {
    val newsPage = NewsPage()
    val welcomePage = WelcomePage()

    welcomePage.showPage()

    println("----------------------")

    newsPage.showPage()
}
