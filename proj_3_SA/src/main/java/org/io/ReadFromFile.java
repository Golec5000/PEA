package org.io;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;

@Getter
@Setter
public class ReadFromFile {

    private int[][] matrix;

    public ReadFromFile() {
        setMatrix(null);
    }

    public void read(String fileName) {

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


        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    public void display() {

        Arrays.stream(matrix)
                .map(row -> Arrays.stream(row)
                        .mapToObj(Integer::toString)
                        .reduce((str1, str2) -> str1 + " " + str2).get())
                .forEach(System.out::println);

    }

}
