package org.application;

import lombok.Getter;
import lombok.Setter;
import org.algoritm.BruteForce;
import org.io.ReadFromFile;

import java.util.Scanner;

@Setter
@Getter
public class Menu {

    private ReadFromFile readFromFile;
    private BruteForce bf;

    public Menu() {

        setReadFromFile(new ReadFromFile());

    }

    private void mainMenuDisplay() {

        System.out.println("1 -> Wyczytanie macierzy z pliku .txt");
        System.out.println("2 -> Wyświelenie wczytanej macierzy");
        System.out.println("3 -> Rozwiązanie Brute Force");
        System.out.println("0 -> Zakończenie programu");

    }

    public void mainMenu() {

        int num;

        do {

            mainMenuDisplay();
            num = new Scanner(System.in).nextInt();

            switch (num) {
                case 0:
                    break;

                case 1:

                    System.out.println("Podaj nazwę pliku do wczytania (bex .txt)");
                    String filename = new Scanner(System.in).nextLine();
                    getReadFromFile().read(filename + ".txt");

                    break;

                case 2:

                    if (getReadFromFile().getMatrix() == null) {
                        System.out.println("Brak wczytanej macierzy");
                        break;
                    }

                    System.out.println("Macierz z pliku");
                    getReadFromFile().display();

                    break;

                case 3:

                    if (getReadFromFile().getMatrix() == null) {
                        System.out.println("Brak wczytanej macierzy");
                        break;
                    }

                    setBf(new BruteForce(getReadFromFile().getMatrix()));
                    getBf().solve();
                    getBf().displaySolution();

                    break;

                default:
                    System.out.println("Brak takiej opcji");
                    break;

            }

        } while (num != 0);

    }


}
