package org.tests;

import org.algoritm.AlgInterface;
import org.algoritm.BrandAndBounch;
import org.algoritm.DynamicProgramming;
import org.io.ReadFromFile;
import org.io.WriterToFile;
import org.timer.TimeWatch;

public class TestingClass {

    private static final ReadFromFile fileReader = new ReadFromFile();

    private static final TimeWatch timer = new TimeWatch();

    private static final WriterToFile fileWriter = new WriterToFile();

    private static final RandGen randGen = new RandGen();

    private static AlgInterface alg;

    public static void main(String[] args) {

        //BnB

//        testsForExitsFilesBnB();
//        testsForRandomMatrixBnB();

        //Dp

//        testsForExitsFilesDP();
//        testsForRandomMatrixDP();

    }

    private static void testsForExitsFilesBnB() {

        //macierze testowe z eportalu
        testingFileBnB("matrix_6x6.txt");
        testingFileBnB("matrix_8x8.txt");
        testingFileBnB("matrix_11x11.txt");

        //macierze testowe ze strony Dr. Jarosława Mierzwy oraz z github
        //http://jaroslaw.mierzwa.staff.iiar.pwr.wroc.pl/pea-stud/tsp/
        //https://github.com/SuperCiuper/PEAproject/tree/main/data

        testingFileBnB("tsp_6_1.txt");
        testingFileBnB("tsp_6_2.txt");
        testingFileBnB("tsp_10.txt");
        testingFileBnB("tsp_12.txt");
        testingFileBnB("tsp_13.txt");
        testingFileBnB("tsp_14.txt");
        testingFileBnB("tsp_15.txt");
        testingFileBnB("m16.txt");
        testingFileBnB("tsp_17.txt");


    }

    private static void testsForRandomMatrixBnB() {
        System.out.println("test_2_bnb.txt");

        fileWriter.save("test_2_bnb.txt", "rozmiar_macierzy;" + "średni_czas_wykonania[ms];");

        testingRandomMatrixBnB(10);
        testingRandomMatrixBnB(11);
        testingRandomMatrixBnB(12);
        testingRandomMatrixBnB(13);
        testingRandomMatrixBnB(14);
        testingRandomMatrixBnB(15);
        testingRandomMatrixBnB(16);
        testingRandomMatrixBnB(17);
        testingRandomMatrixBnB(18);
        testingRandomMatrixBnB(19);
        testingRandomMatrixBnB(20);
        testingRandomMatrixBnB(21);
        testingRandomMatrixBnB(22);
        testingRandomMatrixBnB(23);
        testingRandomMatrixBnB(24);
        testingRandomMatrixBnB(25);
        testingRandomMatrixBnB(26);
        testingRandomMatrixBnB(27);

    }

    private static void testsForExitsFilesDP() {

        //macierze testowe z eportalu
        testingFileDP("matrix_6x6.txt");
        testingFileDP("matrix_8x8.txt");
        testingFileDP("matrix_11x11.txt");

        //macierze testowe ze strony Dr. Jarosława Mierzwy oraz z github
        //http://jaroslaw.mierzwa.staff.iiar.pwr.wroc.pl/pea-stud/tsp/
        //https://github.com/SuperCiuper/PEAproject/tree/main/data

        testingFileDP("tsp_6_1.txt");
        testingFileDP("tsp_6_2.txt");
        testingFileDP("tsp_10.txt");
        testingFileDP("tsp_12.txt");
        testingFileDP("tsp_13.txt");
        testingFileDP("tsp_14.txt");
        testingFileDP("tsp_15.txt");
        testingFileDP("m16.txt");
        testingFileDP("tsp_17.txt");
        testingFileDP("gr21.txt");
        testingFileDP("gr24.txt");


    }

    private static void testsForRandomMatrixDP() {

//        fileWriter.save("test_2_dp.txt", "rozmiar_macierzy;" + "średni_czas_wykonania[ms];");

        testingRandomMatrixDP(14);
        testingRandomMatrixDP(15);
        testingRandomMatrixDP(16);
        testingRandomMatrixDP(17);
        testingRandomMatrixDP(18);
        testingRandomMatrixDP(19);
        testingRandomMatrixDP(20);
        testingRandomMatrixDP(21);
        testingRandomMatrixDP(22);
        testingRandomMatrixDP(23);
        testingRandomMatrixDP(24);
        testingRandomMatrixDP(25);
        testingRandomMatrixDP(26);
        testingRandomMatrixDP(27);
        testingRandomMatrixDP(28);
        testingRandomMatrixDP(29);


    }

    private static void testingFileBnB(String filename) {

        for (int i = 0; i < 40; i++) {

            fileReader.read(filename);

            alg = new BrandAndBounch(fileReader.getMatrix());

            timer.startTimer();
            alg.solve();
            double time = timer.stopTimer();

            fileWriter.save("test_1_bnb.txt", time + ";" + alg);
        }

    }

    private static void testingFileDP(String filename) {

        for (int i = 0; i < 40; i++) {

            fileReader.read(filename);

            alg = new DynamicProgramming(0, fileReader.getMatrix());

            timer.startTimer();
            alg.solve();
            double time = timer.stopTimer();

            fileWriter.save("test_1_dp.txt", time + ";" + alg);
        }

    }

    private static void testingRandomMatrixBnB(int size) {

        System.out.println("Dla macierzy " + size);

        double time = 0.0;

        for (int i = 0; i < 100; i++) {

            System.out.println((i + 1) + "/100");

            alg = new BrandAndBounch(randGen.randMatrix(size));

            timer.startTimer();
            alg.solve();
            time += timer.stopTimer();

        }

        fileWriter.save("test_2_bnb.txt", size + ";" + (time / 100.0) + ";");

    }

    private static void testingRandomMatrixDP(int size) {

        System.out.println("Dla macierzy " + size);

        try {

            double time = 0.0;

            for (int i = 0; i < 100; i++) {

                System.out.println((i + 1) + "/100");

                alg = new DynamicProgramming(0, randGen.randMatrix(size));

                timer.startTimer();
                alg.solve();
                time += timer.stopTimer();

            }

            fileWriter.save("test_2_dp.txt", size + ";" + (time / 100.0) + ";");

        } catch (OutOfMemoryError error) {
            System.out.println("memo + " + error.getMessage());
        } catch (NegativeArraySizeException exception) {
            System.out.println("size " + exception.getMessage());
        }

    }

}
