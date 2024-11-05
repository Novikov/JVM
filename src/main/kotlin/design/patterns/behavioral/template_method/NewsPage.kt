package design.patterns.behavioral.template_method

class NewsPage : WebsiteTemplate() {

    override fun showPageContent() {
        println("News")
    }
}