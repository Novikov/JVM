package design.patterns.strctural.decorator

class SeniorKotlinDeveloper(developer: Developer): KotlinDeveloperDecorator(developer) {
    private fun makeCodeReview(): String {
        return "Make code review. "
    }

    override fun makeJob(): String {
        return developer.makeJob() + makeCodeReview()
    }
}