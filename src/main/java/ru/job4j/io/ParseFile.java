package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    final private File file;

    public ParseFile(File file) {
        this.file = file;
    }

    private String content(Predicate<Character> filter) throws IOException {
        String output = "";
        int data;
        try (BufferedInputStream bis = new BufferedInputStream(
                new FileInputStream(file))) {
            while ((data = bis.read()) > 0) {
                if (filter.test((char) data)) {
                    output += (char) data;
                }
            }
        }
        return output;
    }

    public String getContent() throws IOException {
        return content(c -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return content(c -> c < 0x80);
    }
}