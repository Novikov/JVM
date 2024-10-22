package design.patterns.strctural

/**
 * Динамическое добавление новых обязанностей объекту
 * Используется в качестве альтернативы порождению подклассов для расширения функциональности
 *
 * Примеры использования
 * -Динамическое и понятное клиентам добавление обязанностей объектам.
 * -Реализация обязанностей, которые могут быть сняты с объекта.
 * -Расширенеи класса путем порождения подклассов невозможно по каким либо причинам.
 * */
fun main(){
    val developer = TeammateKotlinDeveloper(SeniorKotlinDeveloper(KotlinDeveloper()))
    println(developer.makeJob())
}

interface Developer {
    fun makeJob(): String
}

class KotlinDeveloper : Developer {
    override fun makeJob()  = "Make kotlin code. "
}

open class KotlinDeveloperDecorator(val developer: Developer): Developer {
    override fun makeJob(): String {
       return developer.makeJob()
    }
}


class SeniorKotlinDeveloper(developer: Developer): KotlinDeveloperDecorator(developer) {
    private fun makeCodeReview(): String {
        return "Make code review. "
    }

    override fun makeJob(): String {
        return developer.makeJob() + makeCodeReview()
    }
}

class TeammateKotlinDeveloper(developer: Developer): KotlinDeveloperDecorator(developer) {
    private fun makeWeekly(): String {
        return "Make weekly meeting. "
    }

    override fun makeJob(): String {
        return developer.makeJob() + makeWeekly()
    }
}