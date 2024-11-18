package concurrency.libs.flow

import kotlinx.coroutines.flow.*

suspend fun main(){
    MutableSharedFlow<Int>()
    MutableStateFlow<Int>(1)
    coldFlowExample()
}

suspend fun coldFlowExample(){
    val flow = flow<Int> {
        var i = 0
        while (true){
            emit(i++)
        }
    }

    flow.collect{
        println("Flow 1 $it")
    }

    flow.collect{
        println("Flow 2 $it")
    }
}

/**
 * SharedFlow
 * StateFlow
 * ColdFlow > HotFlow (stateIn + sharedIn)
 * */