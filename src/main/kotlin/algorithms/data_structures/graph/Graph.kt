package algorithms.data_structures.graph


class Graph {
    private val adjList = HashMap<String, ArrayList<String>>()

    fun getAdjList(): HashMap<String, ArrayList<String>> {
        return adjList
    }

    fun printGraph() {
        println(adjList)
    }

    fun addVertex(vertex: String): Boolean {
        if (adjList[vertex] == null) {
            adjList[vertex] = ArrayList()
            return true
        }
        return false
    }

    fun addEdge(vertex1: String?, vertex2: String?): Boolean {
        if (adjList[vertex1] != null && adjList[vertex2] != null) {
            adjList[vertex1]?.add(vertex2!!)
            adjList[vertex2]?.add(vertex1!!)
            return true
        }
        return false
    }

    fun removeEdge(vertex1: String?, vertex2: String?): Boolean {
        if (adjList[vertex1] != null && adjList[vertex2] != null) {
            adjList[vertex1]?.remove(vertex2)
            adjList[vertex2]?.remove(vertex1)
            return true
        }
        return false
    }

    fun removeVertex(vertex: String): Boolean {
        if (adjList[vertex] == null) return false
        for (otherVertex in adjList[vertex]!!) { //Сложная логика удаления ссылок на удаляемую вершину из других вершин https://www.udemy.com/course/data-structures-and-algorithms-java/learn/lecture/29859254#notes
            adjList[otherVertex]?.remove(vertex)
        }
        adjList.remove(vertex)
        return true
    }
}