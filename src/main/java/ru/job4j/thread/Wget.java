package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        String file = "https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml";
        try (BufferedInputStream in = new BufferedInputStream(new URL(file).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("tmp.file")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long start, finish, elapsedTime, pauseTime;
            long expectedTime = 1000 * 1024 / speed;
            boolean haveData = true;

            while (haveData) {
                start = System.currentTimeMillis();
                bytesRead = in.read(dataBuffer, 0, 1024);
                if (bytesRead == -1) {
                    haveData = false;
                } else {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                    finish = System.currentTimeMillis();
                    elapsedTime = finish - start;
                    pauseTime = expectedTime - elapsedTime < 0 ? 0 : expectedTime - elapsedTime;
                    Thread.sleep(pauseTime);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}