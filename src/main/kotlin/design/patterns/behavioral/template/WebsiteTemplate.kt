package design.patterns.behavioral.template

abstract class WebsiteTemplate {

    fun showPage(){
        println("Header")
        showPageContent()
        println("Footer")
    }

    abstract fun showPageContent()
}