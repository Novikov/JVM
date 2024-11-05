package design.patterns.strctural.facade

/**
 * Теперь создадим класс HomeTheaterFacade, который будет предоставлять упрощенный интерфейс для работы с этими подсистемами.
 * Клиент сможет взаимодействовать с одной точкой, не зная деталей реализации каждой подсистемы.
 * */
class HomeTheaterFacade(
    private val audioSystem: AudioSystem,
    private val videoSystem: VideoSystem,
    private val lightingSystem: LightingSystem
) {
    // Метод для подготовки домашнего кинотеатра
    fun startMovie() {
        println("Preparing home theater for movie...")
        lightingSystem.turnOn()       // Включаем освещение
        lightingSystem.adjustLighting() // Настроим освещение
        videoSystem.turnOn()         // Включаем видеосистему
        videoSystem.playVideo()      // Запускаем видео
        audioSystem.turnOn()         // Включаем аудиосистему
        audioSystem.playMusic()      // Воспроизводим музыку
    }

    // Метод для завершения сеанса
    fun endMovie() {
        println("Ending movie session...")
        audioSystem.turnOff()        // Выключаем аудиосистему
        videoSystem.turnOff()        // Выключаем видеосистему
        lightingSystem.turnOff()     // Выключаем освещение
    }
}