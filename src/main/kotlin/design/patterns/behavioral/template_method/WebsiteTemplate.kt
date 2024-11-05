package design.patterns.behavioral.template_method

abstract class WebsiteTemplate {

    fun showPage(){
        println("Header")
        showPageContent()
        println("Footer")
    }

    abstract fun showPageContent()
}