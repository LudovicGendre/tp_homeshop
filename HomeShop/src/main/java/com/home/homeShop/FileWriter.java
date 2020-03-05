package com.home.homeShop;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileWriter implements Writer {
    private String filename;
    private Path path;
    private String content;

    public FileWriter(String filename){
        this.filename = filename;
    }
    @Override
    public void start() {
    path = Paths.get(filename);
    content = "";
    }

    @Override
    public void writeLine(String line) {
    content += line + "%n";
    }

    @Override
    public void stop() {
        try {
            Files.write(path, String.format(content).getBytes());
        } catch (IOException e) {
            System.out.println("Impossible de rédiger la facture");;
        }

    }
}
