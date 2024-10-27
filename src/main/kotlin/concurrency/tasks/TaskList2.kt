package concurrency.tasks

fun main(){

}

/**
 * 1. Скачивание файлов
 * Создание программы для параллельного скачивания нескольких файлов. Каждый файл скачивается в отдельном потоке, что ускоряет процесс.
 *
 * java
 * Копировать код
 * import java.io.BufferedInputStream;
 * import java.io.FileOutputStream;
 * import java.io.IOException;
 * import java.net.URL;
 *
 * class FileDownloader extends Thread {
 *     private String fileURL;
 *
 *     public FileDownloader(String fileURL) {
 *         this.fileURL = fileURL;
 *     }
 *
 *     public void run() {
 *         try (BufferedInputStream in = new BufferedInputStream(new URL(fileURL).openStream());
 *              FileOutputStream out = new FileOutputStream(fileURL.substring(fileURL.lastIndexOf('/') + 1))) {
 *             byte[] dataBuffer = new byte[1024];
 *             int bytesRead;
 *             while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
 *                 out.write(dataBuffer, 0, bytesRead);
 *             }
 *             System.out.println("Скачивание завершено: " + fileURL);
 *         } catch (IOException e) {
 *             e.printStackTrace();
 *         }
 *     }
 * }
 *
 * public class DownloadManager {
 *     public static void main(String[] args) {
 *         String[] urls = {
 *             "https://example.com/file1.zip",
 *             "https://example.com/file2.zip",
 *             "https://example.com/file3.zip"
 *         };
 *
 *         for (String url : urls) {
 *             new FileDownloader(url).start();
 *         }
 *     }
 * }
 * 2. Обработка больших объемов данных
 * Создание программы для параллельной обработки большого объема данных, например, фильтрации списка.
 *
 * java
 * Копировать код
 * import java.util.Arrays;
 * import java.util.List;
 * import java.util.stream.Collectors;
 *
 * class DataProcessor extends Thread {
 *     private List<Integer> data;
 *     private List<Integer> results;
 *
 *     public DataProcessor(List<Integer> data) {
 *         this.data = data;
 *     }
 *
 *     public List<Integer> getResults() {
 *         return results;
 *     }
 *
 *     public void run() {
 *         results = data.stream().filter(x -> x % 2 == 0).collect(Collectors.toList());
 *         System.out.println("Обработка завершена потоком " + Thread.currentThread().getName());
 *     }
 * }
 *
 * public class ParallelDataProcessing {
 *     public static void main(String[] args) throws InterruptedException {
 *         List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
 *
 *         DataProcessor processor1 = new DataProcessor(numbers.subList(0, 5));
 *         DataProcessor processor2 = new DataProcessor(numbers.subList(5, 10));
 *
 *         processor1.start();
 *         processor2.start();
 *
 *         processor1.join();
 *         processor2.join();
 *
 *         System.out.println("Результаты обработки:");
 *         System.out.println(processor1.getResults());
 *         System.out.println(processor2.getResults());
 *     }
 * }
 * 3. Игровой движок
 * Создание простого игрового движка, где обновление состояния игры и рендеринг происходят в разных потоках.
 *
 * java
 * Копировать код
 * class GameLoop extends Thread {
 *     private boolean running = true;
 *
 *     public void run() {
 *         while (running) {
 *             updateGame();
 *             try {
 *                 Thread.sleep(16); // Обновление 60 раз в секунду
 *             } catch (InterruptedException e) {
 *                 e.printStackTrace();
 *             }
 *         }
 *     }
 *
 *     private void updateGame() {
 *         System.out.println("Обновление состояния игры...");
 *     }
 *
 *     public void stopGame() {
 *         running = false;
 *     }
 * }
 *
 * class RenderLoop extends Thread {
 *     private boolean running = true;
 *
 *     public void run() {
 *         while (running) {
 *             renderGame();
 *             try {
 *                 Thread.sleep(16); // Рендеринг 60 раз в секунду
 *             } catch (InterruptedException e) {
 *                 e.printStackTrace();
 *             }
 *         }
 *     }
 *
 *     private void renderGame() {
 *         System.out.println("Рендеринг кадра...");
 *     }
 *
 *     public void stopRender() {
 *         running = false;
 *     }
 * }
 *
 * public class GameExample {
 *     public static void main(String[] args) throws InterruptedException {
 *         GameLoop gameLoop = new GameLoop();
 *         RenderLoop renderLoop = new RenderLoop();
 *
 *         gameLoop.start();
 *         renderLoop.start();
 *
 *         Thread.sleep(2000); // Играть 2 секунды
 *
 *         gameLoop.stopGame();
 *         renderLoop.stopRender();
 *         gameLoop.join();
 *         renderLoop.join();
 *
 *         System.out.println("Игра завершена.");
 *     }
 * }
 * 4. Веб-сервер
 * Создание простого многопоточного веб-сервера, который обрабатывает входящие запросы в отдельных потоках.
 *
 * java
 * Копировать код
 * import java.io.IOException;
 * import java.io.OutputStream;
 * import java.net.ServerSocket;
 * import java.net.Socket;
 *
 * class ClientHandler extends Thread {
 *     private Socket socket;
 *
 *     public ClientHandler(Socket socket) {
 *         this.socket = socket;
 *     }
 *
 *     public void run() {
 *         try {
 *             OutputStream out = socket.getOutputStream();
 *             String response = "HTTP/1.1 200 OK\r\n\r\nHello, World!";
 *             out.write(response.getBytes());
 *             out.flush();
 *             socket.close();
 *         } catch (IOException e) {
 *             e.printStackTrace();
 *         }
 *     }
 * }
 *
 * public class SimpleWebServer {
 *     public static void main(String[] args) {
 *         try (ServerSocket serverSocket = new ServerSocket(8080)) {
 *             System.out.println("Сервер запущен на порту 8080...");
 *             while (true) {
 *                 Socket clientSocket = serverSocket.accept();
 *                 new ClientHandler(clientSocket).start();
 *             }
 *         } catch (IOException e) {
 *             e.printStackTrace();
 *         }
 *     }
 * }
 * Эти примеры демонстрируют, как можно использовать многопоточность в различных прикладных задачах, от скачивания файлов до создания простого веб-сервера. Если тебе нужны дополнительные примеры или пояснения, дай знать!
 * */