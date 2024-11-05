package design.patterns.behavioral.template_method

class WelcomePage : WebsiteTemplate(){

    override fun showPageContent() {
        println("Welcome")
    }
}