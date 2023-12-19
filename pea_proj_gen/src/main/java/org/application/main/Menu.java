package org.application.main;


import lombok.Getter;
import lombok.Setter;
import org.application.alg.AlgInterface;
import org.application.io.ReadFromFile;
import org.application.tests.TestingClass;

@Getter
@Setter
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

    public void mainMenu(){

    }

}
