package design.patterns.strctural.decorator

class TeammateKotlinDeveloper(developer: Developer): KotlinDeveloperDecorator(developer) {
    private fun makeWeekly(): String {
        return "Make weekly meeting. "
    }

    override fun makeJob(): String {
        return developer.makeJob() + makeWeekly()
    }
}