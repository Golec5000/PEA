package tests;

import algorilm.AlgInterface;
import algorilm.TabuSearch;
import io.ReadFromFile;
import io.WriterToFile;


public class TestingClass {

    private static final ReadFromFile fileReader = new ReadFromFile();

    private static final WriterToFile fileWriter = new WriterToFile();

    private static AlgInterface alg;

    private final int timeLimit = 120000;

    private final String[] files = {
            "br17.atsp",
            "ftv38.atsp",
            "ftv47.atsp",
            "ftv53.atsp",
            "ftv64.atsp",
            "ftv70.atsp",
            "kro124p.atsp",
            "ftv170.atsp",
            "rbg323.atsp",
            "rbg358.atsp",
            "rbg403.atsp",
            "rbg443.atsp"
    };

    public void testSwap() {

        fileWriter.save("TS_Swap.txt", "Nazwa_pliku;" + "czas_wykonania[ms];" + "koszt_ścierzki;" + "ścierzka");

        for (String file : files) {
            swapTest(file, timeLimit, 1);
        }

    }

    public void swapTest(String filename, int timeLimit, int neighbor) {

        fileReader.read(filename);

        for (int i = 0; i < 10; i++) {

            alg = new TabuSearch(fileReader.getMatrix(), timeLimit, neighbor);
            alg.solve();
            fileWriter.save("TS_Swap.txt", filename + ";" + alg.toString());
        }

    }

    public void testInsert() {

        fileWriter.save("TS_Insert.txt", "Nazwa_pliku;" + "czas_wykonania[ms];" + "koszt_ścierzki;" + "ścierzka");

        for (String file : files) {
            insertTest(file, timeLimit, 2);
        }

    }

    private void insertTest(String file, int timeLimit, int neighbor) {


        fileReader.read(file);

        for (int j = 0; j < 10; j++) {

            alg = new TabuSearch(fileReader.getMatrix(), timeLimit, neighbor);
            alg.solve();
            fileWriter.save("TS_Insert.txt", file + ";" + alg.toString());
        }
    }

    public void testInverse() {

        fileWriter.save("TS_Inverse.txt", "Nazwa_pliku;" + "czas_wykonania[ms];" + "koszt_ścierzki;" + "ścierzka");

        for (String file : files) {
            inverseTest(file, timeLimit, 3);
        }

    }

    private void inverseTest(String file, int timeLimit, int neighbor) {


        fileReader.read(file);

        for (int j = 0; j < 10; j++) {

            alg = new TabuSearch(fileReader.getMatrix(), timeLimit, neighbor);
            alg.solve();
            fileWriter.save("TS_Inverse.txt", file + ";" + alg.toString());
        }
    }


}
