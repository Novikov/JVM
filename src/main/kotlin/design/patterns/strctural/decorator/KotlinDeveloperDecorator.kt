package design.patterns.strctural.decorator

open class KotlinDeveloperDecorator(val developer: Developer): Developer {
    override fun makeJob(): String {
        return developer.makeJob()
    }
}