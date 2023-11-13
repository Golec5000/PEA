package org.application;

import lombok.Getter;
import lombok.Setter;
import org.algoritm.AlgInterface;
import org.algoritm.BrandAndBounch;
import org.algoritm.DynamicProgramming;
import org.io.ReadFromFile;

import java.util.Scanner;

@Setter
@Getter
public class Menu {

    private ReadFromFile readFromFile;
    private AlgInterface alg;

    public Menu() {

        setReadFromFile(new ReadFromFile());

    }

    private void mainMenuDisplay() {

        System.out.println("1 -> Wyczytanie macierzy z pliku .txt");
        System.out.println("2 -> Wyświelenie wczytanej macierzy");
        System.out.println("3 -> Rozwiązanie Bround and Bounch");
        System.out.println("4 -> Rozwiązanie Dynamic Programming");
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

                    System.out.println("Podaj nazwę pliku do wczytania (bez .txt)");
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

                    setAlg(new BrandAndBounch(getReadFromFile().getMatrix()));
                    getAlg().solve();
                    System.out.println(getAlg());

                    break;

                case 4:

                    if (getReadFromFile().getMatrix() == null) {
                        System.out.println("Brak wczytanej macierzy");
                        break;
                    }

                    setAlg(new DynamicProgramming(0, getReadFromFile().getMatrix()));
                    getAlg().solve();
                    System.out.println(getAlg());

                    break;

                default:
                    System.out.println("Brak takiej opcji");
                    break;

            }

        } while (num != 0);

    }


}
