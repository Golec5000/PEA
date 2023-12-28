package org.application.main;


import lombok.Getter;
import lombok.Setter;
import org.application.alg.AlgInterface;
import org.application.alg.CrossType;
import org.application.alg.GenAlg;
import org.application.alg.MutationType;
import org.application.io.ReadFromFile;
import org.application.tests.TestingClass;

import java.util.Scanner;

@Getter
@Setter
public class Menu {

    private ReadFromFile readFromFile;
    private AlgInterface alg;
    private TestingClass testingClass;

    private Scanner scanner;

    private CrossType crossMethod;
    private MutationType mutationMethod;

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

        setScanner(new Scanner(System.in));

        setCrossMethod(CrossType.PMX);
        setMutationMethod(MutationType.INVERSION);

    }

    private void mainMenuDisplay() {

        StringBuilder sb = new StringBuilder();

        sb.append("1 -> Wyczytanie macierzy z pliku .atsp\n");
        sb.append("2 -> Wyświelenie wczytanej macierzy\n");
        sb.append("3 -> Wybór metody krzyżowania\n");
        sb.append("4 -> Wybór metody mutacji\n");
        sb.append("5 -> czas stopu\n");
        sb.append("6 -> Procent krzyżowania\n");
        sb.append("7 -> Procent mutacji\n");
        sb.append("8 -> Rozmiar populacji\n");
        sb.append("9 -> Uruchomienie algorytmu\n");
        sb.append("0 -> Zakończenie programu\n");

        sb.append("\n");

        sb.append("Obecne ustawienia\n");
        sb.append("Metoda krzyżowania: ").append(getCrossMethod() == CrossType.PMX ? "PMX" : "OX").append("\n");
        sb.append("Metoda mutacji: ").append(getMutationMethod() == MutationType.INVERSION ? "Inversion" : "Swap").append("\n");
        sb.append("Czas stopu: ").append(getTimeLimit() / 1000).append(" s\n");
        sb.append("Procent krzyżowania: ").append(getCrossRate()).append("\n");
        sb.append("Procent mutacji: ").append(getMutationRate()).append("\n");
        sb.append("Rozmiar populacji: ").append(getNumberOfPopulation()).append("\n");

        sb.append("Wybór opcji: ");

        System.out.println(sb.toString());

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
                    String filename = getScanner().nextLine();
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
                    System.out.println("0 -> Inversion");
                    System.out.println("1 -> Swap");

                    int mutationMethodTmp = getScanner().nextInt();

                    switch (mutationMethodTmp) {
                        case 0:
                            setMutationMethod(MutationType.INVERSION);
                            break;
                        case 1:
                            setMutationMethod(MutationType.SWAP);
                            break;
                        default:
                            System.out.println("Brak takiej opcji, algorytm Inversion zostanie wybrany domyślnie");
                            setMutationMethod(MutationType.INVERSION);
                            break;
                    }

                    break;

                case 5:

                    System.out.println("Podaj czas stopu w sekundach");
                    long timeLimitTmp = getScanner().nextLong();
                    setTimeLimit(Math.abs(timeLimitTmp * 1000));

                    break;

                case 6:

                    System.out.println("Podaj procent krzyżowania");
                    float crossRateTmp = getScanner().nextFloat();
                    setCrossRate(Math.abs(crossRateTmp));

                    break;

                case 7:

                    System.out.println("Podaj procent mutacji");
                    float mutationRateTmp = getScanner().nextFloat();
                    setMutationRate(Math.abs(mutationRateTmp));

                    break;

                case 8:

                    System.out.println("Podaj rozmiar populacji");
                    int numberOfPopulationTmp = getScanner().nextInt();
                    setNumberOfPopulation(Math.abs(numberOfPopulationTmp));

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


