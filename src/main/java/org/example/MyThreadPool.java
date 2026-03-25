package org.example;

import java.util.LinkedList;
import java.util.List;

public class MyThreadPool {
    private final List<Runnable> tasks = new LinkedList<>(); // Список задач
    private final Thread[] workers;
    private boolean isShutdown = false;

    public MyThreadPool(int capacity) {
        workers = new Thread[capacity];
        for (int i = 0; i < capacity; i++) {
            workers[i] = new Thread(this::runWorker); // Ссылка на метод для краткости
            workers[i].start();
        }
    }

    public void execute(Runnable task) {
        synchronized (tasks) {
            if (isShutdown) {
                throw new IllegalStateException("Пул закрыт");
            }
            tasks.add(task);
            tasks.notify(); // Сообщаем потоку, что появилась задача
        }
    }

    public void shutdown() {
        synchronized (tasks) {
            isShutdown = true;
            tasks.notifyAll(); // Будим все потоки, чтобы они увидели закрытие
        }
    }

    public void awaitTermination() throws InterruptedException {
        for (Thread worker : workers) {
            worker.join(); // Ждем завершения каждого потока
        }
    }

    private void runWorker() {
        while (true) {
            Runnable task;
            synchronized (tasks) {
                // Пока задач нет и пул работает — ждем
                while (tasks.isEmpty() && !isShutdown) {
                    try {
                        tasks.wait();
                    } catch (InterruptedException e) {
                        return; // Завершаем поток при прерывании
                    }
                }

                // Если пул закрыт и задачи кончились — выходим из цикла
                if (isShutdown && tasks.isEmpty()) {
                    return;
                }

                task = tasks.remove(0); // Берем первую задачу (аналог poll)
            }

            try {
                task.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}