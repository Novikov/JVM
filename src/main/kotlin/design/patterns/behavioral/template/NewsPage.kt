package design.patterns.behavioral.template

class NewsPage : WebsiteTemplate() {

    override fun showPageContent() {
        println("News")
    }
}