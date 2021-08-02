package ru.job4j;

public class CountBarrier {
    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
            monitor.notifyAll();
            System.out.println(count);
        }
    }

    public void await() {
        synchronized (monitor) {
            while (count < total) {
                System.out.println("Проверка count");
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
class TestCountBarrier {
    public static void main(String[] args) throws InterruptedException {
        CountBarrier barrier = new CountBarrier(10);

        Thread thread1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " started");
            for (int i = 0; i < 10; i++) {
                barrier.count();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            }, "Thread 1");
        Thread thread2 = new Thread(() -> {
            barrier.await();
            System.out.println(Thread.currentThread().getName() + " started");
            }, "Thread 2");
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
    }

}