package concurrency.libs.coroutines

/**
 * Расписать приколы supervisor scope когда он не родительский и приколы передачи Job() в launch
 * как ловить исключения при этом не ломая cancelation exception
 * deadlock Mutex
 *
 *
 * 1. Переписать Coroutine Creation в 1 файл
 * 2. coroutineScope {} и withContext{} функции. Когда исопльзовать и в чем разница
 * 3. Расписать Structured concurrency и особенно ожидание скоупом своих дочерних корутин
 * 4. Расписать StateFlow и SharedFlow и найти параллели с Rx
 * 5. Cnannels и типы буферов, Механизмы работы с BackPressure. Лушче всего рассказал Розов https://www.youtube.com/watch?v=8PwSawWSPBw&list=PL0SwNXKJbuNmsKQW9mtTSxNn00oJlYOLA&index=6
 * 6. Channels - Producer and Actor
 * 7.Channels vs Flow
 * */