package data_structures.hash_tables


class HashTable {
    private val size = 7
    private var dataMap: Array<Node?> = Array(size) { null }

    fun getDataMap(): Array<Node?> {
        return dataMap
    }

    /** Реализация может быть другой*/
    private fun hash(key: String): Int {
        var hash = 0
        val keyChars = key.toCharArray()
        for (item in keyChars) {
            val asciiValue = item.code
            hash = (hash + asciiValue * 23) % dataMap.size
        }
        return hash
    }

    fun set(key: String, value: Int) {
        val index = hash(key)
        val newNode = Node(key, value)
        if (dataMap[index] == null) {
            dataMap[index] = newNode
        } else { //separate chaining
            var temp = dataMap[index]
            while (temp!!.next != null) {
                temp = temp.next
            }
            temp.next = newNode
        }
    }

    operator fun get(key: String): Int {
        val index = hash(key)
        var temp = dataMap[index]
        while (temp != null) {
            if (temp.key == key) return temp.value
            temp = temp.next
        }
        return 0
    }

    fun keys(): ArrayList<String> {
        val allKeys = ArrayList<String>()
        for (element in dataMap) {
            var temp = element
            while (temp != null) {
                allKeys.add(temp.key)
                temp = temp.next
            }
        }
        return allKeys
    }

    fun printTable() {
        for (i in dataMap.indices) {
            println("$i:")
            if (dataMap[i] != null) {
                var temp = dataMap[i]
                while (temp != null) {
                    println("   {" + temp.key + ", " + temp.value + "}")
                    temp = temp.next
                }
            }
        }
    }
}