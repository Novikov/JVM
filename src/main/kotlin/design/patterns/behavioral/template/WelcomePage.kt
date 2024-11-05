package design.patterns.behavioral.template

class WelcomePage : WebsiteTemplate(){

    override fun showPageContent() {
        println("Welcome")
    }
}