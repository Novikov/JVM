package design.oop

fun main(){
    val car = Car(ElectroEngineImpl())
    val engine: Engine = DieselEngineImpl()
}

class Car(val engine: Engine){

}

interface Engine{
    fun run()
}

class DieselEngineImpl : Engine {
    override fun run() {
        println("diesel run")
    }
}

class ElectroEngineImpl : Engine {
    override fun run() {
        println("electro run")
    }
}