package ru.job4j;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

public class CASCountTest {

    @Test
    public void when0IncrementAndGet() {
        CASCount count = new CASCount();
        int expect = 0;
        assertThat(expect, is(count.get()));
    }

    @Test
    public void when1IncrementAndGet() {
        CASCount count = new CASCount();
        count.increment();
        int expect = 1;
        assertThat(expect, is(count.get()));
    }

    @Test
    public void when10IncrementAndGetWith2Threads() throws InterruptedException {
        CASCount count = new CASCount();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                count.increment();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                count.increment();
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        int expect = 10;
        assertThat(expect, is(count.get()));
    }

    @Test
    public void when9IncrementAndGetWith3Threads() throws InterruptedException {
        CASCount count = new CASCount();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                count.increment();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                count.increment();
            }
        });
        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                count.increment();
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();
        int expect = 9;
        assertThat(expect, is(count.get()));
    }
}

