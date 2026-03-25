package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // 1. Создаем пул с 3 рабочими потоками
        MyThreadPool pool = new MyThreadPool(3);

        // 2. Отправляем 10 задач в пул
        for (int i = 1; i <= 10; i++) {
            final int taskId = i;
            pool.execute(() -> {
                String threadName = Thread.currentThread().getName();
                System.out.println("Задача " + taskId + " выполняется потоком " + threadName);
                try {
                    Thread.sleep(1000); // Имитация работы
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // 3. Останавливаем прием новых задач
        System.out.println("Вызываем shutdown...");
        pool.shutdown();

        try {
            // Попытка добавить задачу после shutdown вызовет исключение
             pool.execute(() -> System.out.println("Эта задача не пройдет"));

            // Ждем, пока все текущие задачи в очереди завершатся
            pool.awaitTermination();
            System.out.println("Все задачи выполнены, пул полностью остановлен.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}