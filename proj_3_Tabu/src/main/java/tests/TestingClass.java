package tests;

import algorilm.AlgInterface;
import algorilm.TabuSearch;
import io.ReadFromFile;
import io.WriterToFile;


public class TestingClass {

    private static final ReadFromFile fileReader = new ReadFromFile();

    private static final WriterToFile fileWriter = new WriterToFile();

    private static AlgInterface alg;

    private final String file1 = "ftv47.atsp";

    private final String file2 = "ftv170.atsp";

    private final String file3 = "rbg403.atsp";

    public void testSwap() {

        fileWriter.save("TS_Swap.txt", "Nazwa_pliku;" + "czas_wykonania[ms];" + "koszt_ścierzki;" + "ścierzka");

        System.out.println("Testowanie algorytmu Tabu Search dla metody sąsiedztwa Swap()");

        swapTest(file1, 120000);

        swapTest(file2, 240000);

        swapTest(file3, 360000);

    }

    public void swapTest(String filename, int timeLimit) {

        fileReader.read(filename);

        for (int i = 0; i < 10; i++) {
            System.out.println(filename + " " + (i + 1));
            alg = new TabuSearch(fileReader.getMatrix(), timeLimit, 1);
            alg.solve();
            fileWriter.save("TS_Swap.txt", filename + ";" + alg.toString());
        }

    }

    public void testInsert() {

        fileWriter.save("TS_Insert.txt", "Nazwa_pliku;" + "czas_wykonania[ms];" + "koszt_ścierzki;" + "ścierzka");

        System.out.println("Testowanie algorytmu Tabu Search dla metody sąsiedztwa Insert()");

        insertTest(file1, 120000);

        insertTest(file2, 240000);

        insertTest(file3, 360000);


    }

    private void insertTest(String file,int timeLimit) {


        fileReader.read(file);

        for (int j = 0; j < 10; j++) {

            System.out.println(file + " " + (j + 1));
            alg = new TabuSearch(fileReader.getMatrix(), timeLimit, 2);
            alg.solve();
            fileWriter.save("TS_Insert.txt", file + ";" + alg.toString());
        }
    }

    public void testInverse() {

        fileWriter.save("TS_Inverse.txt", "Nazwa_pliku;" + "czas_wykonania[ms];" + "koszt_ścierzki;" + "ścierzka");
        System.out.println("Testowanie algorytmu Tabu Search dla metody sąsiedztwa Inverse()");

        inverseTest(file1, 120000);

        inverseTest(file2, 240000);

        inverseTest(file3, 360000);
    }

    private void inverseTest(String file,int timeLimit) {

        fileReader.read(file);

        for (int j = 0; j < 10; j++) {

            System.out.println(file + " " + (j + 1));
            alg = new TabuSearch(fileReader.getMatrix(), timeLimit, 3);
            alg.solve();
            fileWriter.save("TS_Inverse.txt", file + ";" + alg.toString());
        }
    }




}
