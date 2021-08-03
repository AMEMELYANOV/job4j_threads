package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int limit;
    private final Object monitor = this;

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    public void offer(T value) throws InterruptedException {
        synchronized (monitor) {
            while (queue.size() == limit) {
                monitor.wait();
            }
            queue.offer(value);
            System.out.println("Добавление в очередь элемента - " + value);
            System.out.println("Текущий размер очереди - " + queue.size());
            monitor.notifyAll();
        }
    }

    public T poll() throws InterruptedException {
        synchronized (monitor) {
            while (queue.size() == 0) {
                monitor.wait();
            }
            T value = queue.poll();
            System.out.println("Чтение из очереди элемента - " + value);
            System.out.println("Текущий размер очереди - " + queue.size());
            monitor.notifyAll();
            return value;
        }
    }

    public int size() {
        return queue.size();
    }
}