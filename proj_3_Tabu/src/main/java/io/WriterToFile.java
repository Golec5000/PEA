package io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriterToFile {

    public void save(String path, String message) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {

            writer.write(message);
            writer.newLine();

        } catch (IOException e) {
            System.out.println("Wystąpił błąd podczas zapisu do pliku: " + e.getMessage());
        }

    }

}
