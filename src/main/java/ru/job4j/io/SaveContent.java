package ru.job4j.io;

import java.io.*;

public class SaveContent {
    final private File file;

    public SaveContent(File file) {
        this.file = file;
    }

    public void saveContent(String content) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(file))) {
                bos.write(content.getBytes());
        }
    }
}
