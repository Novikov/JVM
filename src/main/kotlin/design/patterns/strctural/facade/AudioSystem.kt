package design.patterns.strctural.facade

class AudioSystem {
    fun turnOn() {
        println("Audio system is now ON.")
    }

    fun playMusic() {
        println("Playing music...")
    }

    fun turnOff() {
        println("Audio system is now OFF.")
    }
}