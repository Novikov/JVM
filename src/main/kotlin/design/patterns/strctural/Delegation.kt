package design.patterns.strctural

/**
 * Возможность изменить поведение конкретного экземпляра объекта вместо создания нового класса путём наследования.
 * */


class Rectangle(val width: Int, val height: Int) {
    fun area() = width * height
}

class Window(val bounds: Rectangle) {
    // Delegation (The class Window delegates the area() call to its internal Rectangle object (its delegate))
    fun area() = bounds.area()
}