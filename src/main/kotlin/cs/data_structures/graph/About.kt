package cs.data_structures.graph


/**
 * Граф это структура данных, состоящая из вершин (узлов) и мостов между ними. Мосты так же как и вершины имеют числовые значения (длина)
 * С помощью графа удобно моделировать устройство сети интернет или социальную сеть. Мосты могут иметь длину, а могут не иметь.
 * Дерево это ограниченная форма графа. Каждый следующий Node может указывать на ограниченное число других.
 * Так же LinkedList это форма дерева. Но когда мы говорим о графах то представляем нечто похожее на это https://umbrellait.rapira.com/ZygwIH
 *
 * Из теории графов нужно знать что они бывают симметричные и ассиметричные (возможно тут ошибаюсь). Конкретно эти свойства устанавливаются по
 * матрице в которую можно конвертировать граф. Есть связь - будет 1. Если нет то 0. Как это делается показано вот тут https://www.udemy.com/course/data-structures-and-algorithms-java/learn/lecture/29629648#learning-tools
 * Помимо этих свойств у графов существуют и другие.
 * Так же граф можно представить в виде Списка.
 *
 * Два варианта представлений: https://umbrellait.rapira.com/RQSNg8
 *
 * На скринах буквы E и V обозначают Edge(ребро) и Vertex(вершина).
 * Матрица очень сильно проигрывает по space complexity для всех манипуляций с вершинами и ребрами (Space complexity) https://umbrellait.rapira.com/k6BEP6
 * Добавлении одной вершины - матрицу нужно заполнить нулями https://umbrellait.rapira.com/gmTajF
 * Удаление вершины https://umbrellait.rapira.com/Os4nMU
 * Добавление ребра между вершинами https://umbrellait.rapira.com/oB3xKD
 * Удаление ребра https://umbrellait.rapira.com/wCh9JM
 * Поэтому в данном примере рассматривается вариант со списком.
 *
 * */
fun main() {
    val graph = Graph()
    graph.addVertex("A")
    graph.addVertex("B")
    graph.addVertex("C")
    graph.addEdge("A", "B")
    graph.addEdge("A", "C")

    graph.printGraph()
    graph.removeEdge("A", "C")
    graph.printGraph()

    graph.removeVertex("A")
    graph.printGraph()

}