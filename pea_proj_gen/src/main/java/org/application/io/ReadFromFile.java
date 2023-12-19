package org.application.io;


import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

@Setter
@Getter
public class ReadFromFile {

    private int[][] matrix;

    public ReadFromFile() {
        setMatrix(null);
    }

    public void read(String fileName) {

        try (Scanner scanner = new Scanner(new File(fileName))) {

            scanner.nextLine();
            scanner.nextLine();
            scanner.nextLine();
            int len = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);
            scanner.nextLine();
            scanner.nextLine();
            scanner.nextLine();

            setMatrix(new int[len][len]);

            int colCounter = 0;

            String value = scanner.nextLine();

            int[] numbers = Arrays.stream(value.trim().split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            for (int i = 0; i < len; i++) {

                for (int j = 0; j < len; j++) {

                    getMatrix()[i][j] = numbers[colCounter];

                    colCounter++;

                    if (colCounter == numbers.length) {

                        colCounter = 0;

                        value = scanner.nextLine();

                        try {
                            numbers = Arrays.stream(value.trim().split("\\s+"))
                                    .mapToInt(Integer::parseInt)
                                    .toArray();

                        } catch (NumberFormatException ignored) {}
                    }

                }

            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
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
