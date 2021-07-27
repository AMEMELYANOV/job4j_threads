package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final String dest;
    private final int speed;


    public Wget(String url, String dest, int speed) {
        this.url = url;
        this.dest = dest;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(dest)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long start, finish, elapsedTime, pauseTime;
            long expectedTime = 1000 * 1024 / speed;

            start = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                finish = System.currentTimeMillis();
                elapsedTime = finish - start;
                pauseTime = expectedTime - elapsedTime < 0 ? 0 : expectedTime - elapsedTime;
                Thread.sleep(pauseTime);
                start = System.currentTimeMillis();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean validateUrl(String url) {
        return url.matches("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
    }

    public static boolean validateSpeed(String speed) {
        return speed.matches("[-+]?\\d+");
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 2) {
            throw new IllegalArgumentException("Please check number of parameters."
                    + System.lineSeparator()
                    + "Use: java -jar target/Wget.jar with parameters, for example: "
                    + "java Wget.jar  \"url file\" \"speed of downloading\"");
        } else if (!validateUrl(args[0])) {
            throw new IllegalArgumentException("Not correct URL, please check");
        } else if (!validateSpeed(args[1])) {
            throw new IllegalArgumentException("Not correct speed, please check");
        }

        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String[] splitUrl = args[0].split("/");
        String dest = splitUrl[args[0].split("/").length - 1];
        Thread wget = new Thread(new Wget(url, dest, speed));
        wget.start();
        wget.join();
    }
}