package org.io;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Getter
@Setter
public class FileReader {

    private int[][] matrix;

    public FileReader() {
        setMatrix(null);
    }

    public int[][] read(String fileName) {

        try (Scanner scanner = new Scanner(new File(fileName))) {

            int len = scanner.nextInt();

            setMatrix(new int[len][len]);

            for (int i = 0; i < len; i++) {

                for (int j = 0; j < len; j++) {
                    getMatrix()[i][j] = scanner.nextInt();
                }

                if (i == len - 1) break;

                scanner.nextLine();
            }


        } catch (FileNotFoundException e) {
            System.err.println("Nie udało się odnaleźć pliku.");
        }

        return getMatrix();
    }

    public void display() {

        for (int i = 0; i < getMatrix().length; i++) {

            for (int j = 0; j < getMatrix().length; j++) {
                System.out.print(getMatrix()[i][j] + " ");
            }

            System.out.println();
        }

    }

}
