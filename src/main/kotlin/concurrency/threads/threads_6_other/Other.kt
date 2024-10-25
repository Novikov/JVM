package concurrency.threads.threads_6_other

import kotlinx.coroutines.sync.Mutex


fun main(){

}


/**

В Kotlin блокирующая операция — это операция, которая при выполнении останавливает выполнение текущего потока до тех пор, пока не будет завершена. Например, если поток выполняет запрос к сети или ждет получения данных из базы данных, он будет заблокирован до получения ответа.

Примеры блокирующих операций:
Сетевые запросы: Использование библиотек, которые делают запросы к API, может блокировать поток, пока не будет получен ответ.
Чтение из файла: Операции ввода-вывода, такие как чтение данных из файла, могут занять время, и поток будет ждать завершения операции.
Ожидание на семафоре: Если поток ожидает получения разрешения от семафора, он будет заблокирован до тех пор, пока разрешение не будет доступно.
Для асинхронного выполнения задач в Kotlin часто используются корутины, которые позволяют избежать блокировки потока, позволяя выполнять операции в фоновом режиме и возвращать управление основному потоку, не дожидаясь завершения операции.

 * */