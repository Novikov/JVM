package design.patterns.strctural.delegate

// Класс, использующий делегирование
class UserPrinter(printer: Printer) : Printer by printer {
    // Можно добавлять дополнительные свойства или методы
}