package org.application.main;


import lombok.Getter;
import lombok.Setter;
import org.application.alg.AlgInterface;
import org.application.alg.GenAlg;
import org.application.io.ReadFromFile;
import org.application.tests.TestingClass;

import java.util.Scanner;

@Getter
@Setter
public class Menu {

    private ReadFromFile readFromFile;
    private AlgInterface alg;
    private TestingClass testingClass;
    private int numberOfPopulation;
    private float crossRate;
    private float mutationRate;
    private int tournamentSize;
    private long timeLimit;

    public Menu() {

        setReadFromFile(new ReadFromFile());
        setAlg(null);
        setTestingClass(new TestingClass());
    }

    private void mainMenuDisplay() {

        System.out.println("1 -> Wyczytanie macierzy z pliku .atsp");
        System.out.println("2 -> Wyświelenie wczytanej macierzy");
        System.out.println("0 -> Zakończenie programu");

    }

    public void mainMenu() {

        int num;
        long time;

        do {

            mainMenuDisplay();
            num = new Scanner(System.in).nextInt();

            switch (num) {
                case 0:
                    break;

                case 1:

                    System.out.println("Podaj nazwę pliku do wczytania (bez .atsp)");
                    String filename = new Scanner(System.in).nextLine();
                    getReadFromFile().read(filename + ".atsp");

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

                    setAlg(new GenAlg(getReadFromFile().getMatrix(), 15, 0.8, 0.2, 120000, 5,1,2));
                    System.out.println("Algorytm genetyczny");
                    alg.solve();
                    System.out.println(alg.toString());

                    break;

                default:
                    System.out.println("Brak takiej opcji");
                    break;

            }

        } while (num != 0);

    }


}


