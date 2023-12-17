package app;

import algorilm.AlgInterface;
import algorilm.TabuSearch;
import io.ReadFromFile;
import lombok.Getter;
import lombok.Setter;
import tests.TestingClass;

import java.util.Scanner;

@Setter
@Getter
public class Menu {

    private ReadFromFile readFromFile;
    private AlgInterface alg;
    private TestingClass testingClass;

    public Menu() {

        setReadFromFile(new ReadFromFile());
        setAlg(null);
        setTestingClass(new TestingClass());
    }

    private void mainMenuDisplay() {

        System.out.println("1 -> Wyczytanie macierzy z pliku .atsp");
        System.out.println("2 -> Wyświelenie wczytanej macierzy");
        System.out.println("3 -> Rozwiązanie Tabu -> metoda sąsiedztwa: Swap()");
        System.out.println("4 -> Rozwiązanie Tabu -> metoda sąsiedztwa: Insert()");
        System.out.println("5 -> Rozwiązanie Tabu -> metoda sąsiedztwa: Inverse()");
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

                    System.out.println("Podaj czas w sekundach");
                    time = new Scanner(System.in).nextLong();

                    if(time <= 0) time = 120;

                    setAlg(new TabuSearch(getReadFromFile().getMatrix(), time * 1000, 1));
                    getAlg().solve();
                    System.out.println(getAlg());

                    break;
                case 4:
                    if (getReadFromFile().getMatrix() == null) {
                        System.out.println("Brak wczytanej macierzy");
                        break;
                    }
                    System.out.println("Podaj czas w sekundach");
                    time = new Scanner(System.in).nextLong();

                    if(time <= 0) time = 240;

                    setAlg(new TabuSearch(getReadFromFile().getMatrix(), time * 1000, 2));
                    getAlg().solve();
                    System.out.println(getAlg());
                    break;
                case 5:
                    if (getReadFromFile().getMatrix() == null) {
                        System.out.println("Brak wczytanej macierzy");
                        break;
                    }

                    System.out.println("Podaj czas w sekundach");
                    time = new Scanner(System.in).nextLong();

                    if(time <= 0) time = 360;

                    setAlg(new TabuSearch(getReadFromFile().getMatrix(), time * 1000, 3));
                    getAlg().solve();
                    System.out.println(getAlg());
                    break;

                case 100:
                    getTestingClass().testSwap();
                    getTestingClass().testInsert();
                    getTestingClass().testInverse();
                    break;

                default:
                    System.out.println("Brak takiej opcji");
                    break;

            }

        } while (num != 0);

    }


}
