package concurrency.threads

/**
 * Что такое блокирующая операция и почему не daemon поток завершается до конца а корутина нет.
 * Как добиться парралельного!!! выполнения программы с помощью ForkJoin/StreamApi/ExecutorService?
  Что такое suspendCoroutine и continuation
 Добавить пример с InterruptedException
 *   + Расписать почему suspend функцию можно вызвать только из suspend функции или корутины?
 * 0)Конспект по многопоточности, deadlock
 * 1)вызов корутин на разных скоуп. Пример Миши на runBlocking
 * 2)Старт корутины на main + Проверить будет ли фризиться UI если запускать на main потоке множество запросов в сеть.
 * 3)RunCatching
 * 4)Дочитать документацию
 * 5)Повтор
 * 6)Почему используется channel в guide. Насколько я понимаю тот пример можно переписать на flow, используя операторы flatmapMerge.
 * 7)Если в доке не будет рассказано за операторы flow - повторить Rx аналоги
 * */