package language

fun main() {

    val list = mutableListOf(1, 2, 3, 4, 5)

    /** Принимает lambda with receiver и взаращает receiver, обращение к элементам по this*/
    val applyResult = list.apply {
        add(6)
    }

    /** Принимает lambda with receiver и взаращает receiver, обращение к элементам по it*/
    val alsoResult = list.also {
        it.add(7)
    }

    /** Принимает receiver и lambda with receiver, а возвращает последнюю строку из lambda*/
    val withResult = with(list) {
        add(8)
        "result"
    }

    /** Принимает lambda with receiver, а возвращает последнюю строку из lambda*/
    val runResult = list.run {
        add(8)
        "result"
    }

    /** Принимает lambda with receiver, а возвращает последнюю строку из lambda*/
    val letResult = list.let {
        it.add(8)
        "result"
    }
}



