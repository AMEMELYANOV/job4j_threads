package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        System.out.println("Thread first has status - " + first.getState());
        System.out.println("Thread second has status - " + first.getState());
        first.start();
        second.start();
        while (second.getState() != Thread.State.TERMINATED && first.getState() != Thread.State.TERMINATED) {
            System.out.println(first.getState());
            System.out.println(second.getState());
        }
        System.out.println("Thread first has status - " + first.getState());
        System.out.println("Thread second has status - " + second.getState());
        System.out.println("Thread " + Thread.currentThread().getName() + ": All threads have done!");
    }
}