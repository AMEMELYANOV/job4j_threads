package ru.job4j;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SimpleBlockingQueueTest {

    @Test
    public void whenFinallyQueueSize0() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(() -> {
             for (int i = 0; i < 5; i++) {
                 queue.offer(i);
             }
        }, "Producer");
        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                queue.poll();
            }
        }, "Consumer");
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        int expectedQueueSize = 0;
        assertThat(expectedQueueSize, is(queue.size()));
    }

    @Test
    public void whenFinallyQueueSizeNot0() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(() -> {
             for (int i = 0; i < 10; i++) {
                 queue.offer(i);
             }
        }, "Producer");
        Thread consumer = new Thread(() -> {
             for (int i = 0; i < 5; i++) {
                 queue.poll();
             }
        }, "Consumer");
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        int expectedQueueSize = 5;
        assertThat(expectedQueueSize, is(queue.size()));
    }
}
