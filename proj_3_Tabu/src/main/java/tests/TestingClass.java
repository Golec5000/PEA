package tests;

import algorilm.AlgInterface;
import algorilm.TabuSearch;
import io.ReadFromFile;
import io.WriterToFile;


public class TestingClass {

    private static final ReadFromFile fileReader = new ReadFromFile();

    private static final WriterToFile fileWriter = new WriterToFile();

    private static AlgInterface alg;

    private final String[] files1 = {
            "br17.atsp",
            "ftv38.atsp",
            "ftv47.atsp",
            "ft53.atsp"

    };

    private final String[] files2 = {
//            "ftv64.atsp",
            "ft70.atsp",
            "kro124p.atsp",
            "ftv170.atsp"

    };

    private final String[] files3 = {
            "rbg323.atsp",
            "rbg358.atsp",
            "rbg403.atsp",
            "rbg443.atsp"

    };

    public void testSwap() {

        fileWriter.save("TS_Swap.txt", "Nazwa_pliku;" + "czas_wykonania[ms];" + "koszt_ścierzki;" + "ścierzka");

        System.out.println("Testowanie algorytmu Tabu Search dla metody sąsiedztwa Swap()");

        for (String file : files1) {
            swapTest(file, 120000);
        }

        for (String file : files2) {
            swapTest(file, 240000);
        }

        for (String file : files3) {
            swapTest(file, 360000);
        }

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

        for (String file : files1) {
            insertTest(file, 120000);
        }

        for (String file : files2) {
            insertTest(file, 240000);
        }

        for (String file : files3) {
            insertTest(file, 360000);
        }


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

//        fileWriter.save("TS_Inverse.txt", "Nazwa_pliku;" + "czas_wykonania[ms];" + "koszt_ścierzki;" + "ścierzka");
        System.out.println("Testowanie algorytmu Tabu Search dla metody sąsiedztwa Inverse()");

//        for (String file : files1) {
//            inverseTest(file, 120000);
//        }

        for (String file : files2) {
            inverseTest(file, 240000);
        }

        for (String file : files3) {
            inverseTest(file, 360000);
        }
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
