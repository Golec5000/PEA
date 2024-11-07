package org.application.main;

import lombok.Getter;
import lombok.Setter;
import org.application.alg.*;
import org.application.io.ReadFromFile;

import java.util.InputMismatchException;
import java.util.Scanner;

@Getter
@Setter
public class Menu {

    private ReadFromFile readFromFile;
    private AlgInterface alg;
//    private TestingClass testingClass;

    private Scanner scanner;

    private CrossType crossMethod;
    private MutationType mutationMethod;

    private int numberOfPopulation;
    private int tournamentSize;
    private int maxCounter;
    private int numThreads;

    private float crossRate;
    private float mutationRate;

    public Menu() {

        setReadFromFile(new ReadFromFile());
        setAlg(null);
//        setTestingClass(new TestingClass());

        setCrossRate(0.8f);
        setMutationRate(0.01f);

        setNumberOfPopulation(5000);
        setTournamentSize(5);

        setScanner(new Scanner(System.in));

        setCrossMethod(CrossType.PMX);
        setMutationMethod(MutationType.SWAP);

        setMaxCounter(2000);
        setNumThreads(2); // Default number of threads

    }

    private void mainMenuDisplay() {

        StringBuilder sb = new StringBuilder();

        sb.append("1 -> Wyczytanie macierzy z pliku .atsp\n");
        sb.append("2 -> Wyświelenie wczytanej macierzy\n");
        sb.append("3 -> Wybór metody krzyżowania\n");
        sb.append("4 -> Wybór metody mutacji\n");
        sb.append("5 -> Kryterium stopu\n");
        sb.append("6 -> Procent krzyżowania\n");
        sb.append("7 -> Procent mutacji\n");
        sb.append("8 -> Rozmiar populacji\n");
        sb.append("9 -> Ilość wierzchołków turniejowych\n");
        sb.append("10 -> Ilość wątków\n");
        sb.append("11 -> Uruchomienie algorytmu wielowątkowego\n");
        sb.append("12 -> Uruchomienie algorytmu sekwencyjnego\n");
        sb.append("0 -> Zakończenie programu\n");

        sb.append("\n");

        sb.append("Obecne ustawienia\n");
        sb.append("Metoda krzyżowania: ").append(getCrossMethod() == CrossType.PMX ? "PMX" : "OX").append("\n");
        sb.append("Metoda mutacji: ").append(getMutationMethod() == MutationType.SCRAMBLE ? "Scramble" : "Swap").append("\n");
        sb.append("Kryterium stopu: ").append("ilość generacji: ").append(getMaxCounter()).append("\n");
        sb.append("Procent krzyżowania: ").append(getCrossRate()).append("\n");
        sb.append("Procent mutacji: ").append(getMutationRate()).append("\n");
        sb.append("Rozmiar populacji: ").append(getNumberOfPopulation()).append("\n");
        sb.append("Ilość wierzchołków turniejowych: ").append(getTournamentSize()).append("\n");
        sb.append("Ilość wątków: ").append(getNumThreads()).append("\n");

        sb.append("Wybór opcji: ");

        System.out.println(sb);

    }

    public void mainMenu() {

        int num;

        do {

            mainMenuDisplay();

            num = getScanner().nextInt();

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

                    int crossMethodTmp = getScanner().nextInt();

                    switch (crossMethodTmp) {
                        case 0:
                            setCrossMethod(CrossType.PMX);
                            break;
                        case 1:
                            setCrossMethod(CrossType.OX);
                            break;
                        default:
                            System.out.println("Brak takiej opcji, algorytm PMX zostanie wybrany domyślnie");
                            setCrossMethod(CrossType.PMX);
                            break;
                    }

                    break;

                case 4:

                    System.out.println("Wybór metody mutacji");
                    System.out.println("0 -> Scramble");
                    System.out.println("1 -> Swap");

                    int mutationMethodTmp = getScanner().nextInt();

                    switch (mutationMethodTmp) {
                        case 0:
                            setMutationMethod(MutationType.SCRAMBLE);
                            break;
                        case 1:
                            setMutationMethod(MutationType.SWAP);
                            break;
                        default:
                            System.out.println("Brak takiej opcji, algorytm Swap zostanie wybrany domyślnie");
                            setMutationMethod(MutationType.SWAP);
                            break;
                    }

                    break;

                case 5:

                    System.out.println("Podaj ilość generacji bez zmian");
                    int maxCounterTmp;

                    do {
                        maxCounterTmp = getScanner().nextInt();
                    } while (maxCounterTmp == 0);

                    setMaxCounter(Math.abs(maxCounterTmp));

                    break;

                case 6:

                    System.out.println("Podaj procent krzyżowania");
                    float crossRateTmp;
                    try {
                        crossRateTmp = getScanner().nextFloat();
                    } catch (InputMismatchException e) {
                        System.out.println("Błędny format: " + e.getMessage());
                        break;
                    }
                    setCrossRate(Math.abs(crossRateTmp));

                    break;

                case 7:

                    System.out.println("Podaj procent mutacji");
                    float mutationRateTmp;
                    try {
                        mutationRateTmp = getScanner().nextFloat();
                    } catch (InputMismatchException e) {
                        System.out.println("Błędny format: " + e.getMessage());
                        break;
                    }
                    setMutationRate(Math.abs(mutationRateTmp));

                    break;

                case 8:

                    System.out.println("Podaj rozmiar populacji");
                    int numberOfPopulationTmp = getScanner().nextInt();
                    setNumberOfPopulation(Math.abs(numberOfPopulationTmp));

                    break;

                case 9:

                    System.out.println("Podaj ilość wierzchołków turniejowych");
                    int tournamentSizeTmp = getScanner().nextInt();
                    setTournamentSize(Math.abs(tournamentSizeTmp));

                    break;

                case 10:

                    System.out.println("Podaj ilość wątków");
                    int numThreadsTmp = getScanner().nextInt();
                    setNumThreads(Math.abs(numThreadsTmp));

                    break;

                case 11:

                    if (getReadFromFile().getMatrix() == null) {
                        System.out.println("Brak wczytanej macierzy");
                        break;
                    }

                    AlgInterface genAlgThreads = GenAlgThreads.builder()
                            .matrix(getReadFromFile().getMatrix())
                            .numberOfVertex(getReadFromFile().getMatrix().length)
                            .crossRate(getCrossRate())
                            .mutationRate(getMutationRate())
                            .tournamentSize(getTournamentSize())
                            .crossType(getCrossMethod())
                            .mutationType(getMutationMethod())
                            .maxGeneration(getMaxCounter())
                            .bestSolution(Integer.MAX_VALUE)
                            .counter(0)
                            .populationSize(getNumberOfPopulation())
                            .numThreads(getNumThreads())
                            .build();

                    setAlg(genAlgThreads);

                    System.out.println("Algorytm genetyczny - wielowątkowy");
                    alg.solve();
                    System.out.println(alg.toString());
                    System.out.println("\n");

                    break;

                case 12:

                    if (getReadFromFile().getMatrix() == null) {
                        System.out.println("Brak wczytanej macierzy");
                        break;
                    }

                    AlgInterface genAlgSeq = GenAlg.builder()
                            .matrix(getReadFromFile().getMatrix())
                            .numberOfVertex(getReadFromFile().getMatrix().length)
                            .crossRate(getCrossRate())
                            .mutationRate(getMutationRate())
                            .tournamentSize(getTournamentSize())
                            .crossType(getCrossMethod())
                            .mutationType(getMutationMethod())
                            .maxGeneration(getMaxCounter())
                            .bestSolution(Integer.MAX_VALUE)
                            .counter(0)
                            .populationSize(getNumberOfPopulation())
                            .build();

                    setAlg(genAlgSeq);

                    System.out.println("Algorytm genetyczny - sekwencyjny");
                    alg.solve();
                    System.out.println(alg.toString());
                    System.out.println("\n");

                    break;

//                case 100:
//                    testingClass.testFile47();
//                    break;
//
//                case 101:
//                    testingClass.testFile170();
//                    break;
//
//                case 102:
//                    testingClass.testFile403();
//                    break;
//
//                case 103:
//                    testingClass.testMutationRatio();
//                    break;
//
//                case 104:
//                    testingClass.testCrossRatio();
//                    break;

                default:
                    System.out.println("Brak takiej opcji");
                    break;

            }

        } while (num != 0);

    }
}