package concurrency.threads.threads_1_base_theory.utils

class Service {
    fun readData() {
        println("Read data")
    }

    fun showGreetingMessage() {
        println("Hello!!!")
    }

    fun calculateFactorial(number: Int) {
        var result: Long = 1

        for (factor in 2..number) {
            result *= factor.toLong()
        }
        println("Factorial result: $result")
    }

    fun calculateSum(number: Int) {
        var sum: Long = 0
        for (i in 1..number) {
            sum = sum + i
            println("The current sum is $sum")
        }
        println("The total sum is $sum")
    }


    fun finishProgram() {
        println("Finish")
        System.exit(0)
    }
}