package design.patterns.behavioral.mediator

// Конкретный медиатор, который управляет общением коллег
class ChatRoom : Mediator {
    private val colleagues = mutableListOf<Colleague>()

    fun addColleague(colleague: Colleague) {
        colleagues.add(colleague)
    }

    override fun sendMessage(message: String, colleague: Colleague) {
        // Отправляем сообщение всем коллегам, кроме того, кто отправил сообщение
        for (c in colleagues) {
            if (c != colleague) {
                c.receive(message)
            }
        }
    }
}