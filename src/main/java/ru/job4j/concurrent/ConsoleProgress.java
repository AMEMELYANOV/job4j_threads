package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        String[] process = {"\\", "|", "/"};
        int count = 0;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                    if (count % process.length == 0) {
                        System.out.print("\r load: " + process[0]);
                    } else if (count % process.length == 1) {
                        System.out.print("\r load: " + process[1]);
                    } else {
                        System.out.print("\r load: " + process[2]);
                    }
                    Thread.sleep(500);
                    count++;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();
    }
}
