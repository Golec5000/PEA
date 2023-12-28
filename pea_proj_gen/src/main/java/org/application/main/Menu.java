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

    private int crossMethod;
    private int mutationMethod;
    private int numberOfPopulation;
    private int tournamentSize;

    private float crossRate;
    private float mutationRate;

    private long timeLimit;

    public Menu() {

        setReadFromFile(new ReadFromFile());
        setAlg(null);
        setTestingClass(new TestingClass());

        setCrossRate(0.8f);
        setMutationRate(0.01f);

        setNumberOfPopulation(250);
        setTournamentSize(5);

        setTimeLimit(120000);

    }

    private void mainMenuDisplay() {

        System.out.println("1 -> Wyczytanie macierzy z pliku .atsp");
        System.out.println("2 -> Wyświelenie wczytanej macierzy");
        System.out.println("3 -> Wybór metody krzyżowania");
        System.out.println("4 -> Wybór metody mutacji");
        System.out.println("5 -> czas stopu");
        System.out.println("6 -> Procent krzyżowania");
        System.out.println("7 -> Procent mutacji");
        System.out.println("8 -> Rozmiar populacji");
        System.out.println("9 -> Uruchomienie algorytmu");
        System.out.println("0 -> Zakończenie programu");

        System.out.println();

        System.out.println("Obecne ustawienia");
        System.out.println("Metoda krzyżowania: " + (getCrossMethod() == 0 ? "PMX" : "OX"));
        System.out.println("Metoda mutacji: " + (getMutationMethod() == 0 ? "Inversion" : "Swap"));
        System.out.println("Czas stopu: " + (getTimeLimit() / 1000) + " s");
        System.out.println("Procent krzyżowania: " + getCrossRate());
        System.out.println("Procent mutacji: " + getMutationRate());
        System.out.println("Rozmiar populacji: " + getNumberOfPopulation());

        System.out.print("Wybór opcji: ");

    }

    public void mainMenu() {

        int num;

        do {

            mainMenuDisplay();
            num = new Scanner(System.in).nextInt();

            System.out.println("\n");

            switch (num) {
                case 0:
                    System.out.println("Zakończenie programu");
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

                    System.out.println("Wybór metody krzyżowania");
                    System.out.println("0 -> PMX");
                    System.out.println("1 -> OX");

                    int crossMethod = new Scanner(System.in).nextInt();

                    switch (crossMethod) {
                        case 0:
                            setCrossMethod(0);
                            break;
                        case 1:
                            setCrossMethod(1);
                            break;
                        default:
                            System.out.println("Brak takiej opcji, algorytm PMX zostanie wybrany domyślnie");
                            setCrossMethod(0);
                            break;
                    }

                    break;

                case 4:

                    System.out.println("Wybór metody mutacji");
                    System.out.println("0 -> Inversion");
                    System.out.println("1 -> Swap");

                    int mutationMethod = new Scanner(System.in).nextInt();

                    switch (mutationMethod) {
                        case 0:
                            setMutationMethod(0);
                            break;
                        case 1:
                            setMutationMethod(1);
                            break;
                        default:
                            System.out.println("Brak takiej opcji, algorytm Inversion zostanie wybrany domyślnie");
                            setMutationMethod(0);
                            break;
                    }

                    break;

                case 5:

                    System.out.println("Podaj czas stopu w sekundach");
                    long timeLimit = new Scanner(System.in).nextLong();
                    setTimeLimit(Math.abs(timeLimit * 1000));

                    break;

                case 6:

                    System.out.println("Podaj procent krzyżowania");
                    float crossRate = new Scanner(System.in).nextFloat();
                    setCrossRate(Math.abs(crossRate));

                    break;

                case 7:

                    System.out.println("Podaj procent mutacji");
                    float mutationRate = new Scanner(System.in).nextFloat();
                    setMutationRate(Math.abs(mutationRate));

                    break;

                case 8:

                    System.out.println("Podaj rozmiar populacji");
                    int numberOfPopulation = new Scanner(System.in).nextInt();
                    setNumberOfPopulation(Math.abs(numberOfPopulation));

                    break;

                case 9:

                    if (getReadFromFile().getMatrix() == null) {
                        System.out.println("Brak wczytanej macierzy");
                        break;
                    }

                    setAlg(new GenAlg(getReadFromFile().getMatrix(), getNumberOfPopulation(), getCrossRate(), getMutationRate(), getTimeLimit(), getTournamentSize(), getCrossMethod(), getMutationMethod()));
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


