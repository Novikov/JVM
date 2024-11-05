package design.patterns.strctural.facade

class LightingSystem {
    fun turnOn() {
        println("Lighting system is now ON.")
    }

    fun adjustLighting() {
        println("Adjusting lighting...")
    }

    fun turnOff() {
        println("Lighting system is now OFF.")
    }
}