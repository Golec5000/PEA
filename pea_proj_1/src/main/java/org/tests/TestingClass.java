package org.tests;

import org.algoritm.BruteForce;
import org.io.ReadFromFile;
import org.io.WriterToFile;
import org.timer.TimeWatch;

import java.util.Arrays;

public class TestingClass {

    private static final ReadFromFile fileReader = new ReadFromFile();

    private static final TimeWatch timer = new TimeWatch();

    private static final WriterToFile fileWriter = new WriterToFile();

    private static final RandGen randGen = new RandGen();

    public static void main(String[] args) {

        testForExitsFiles();
        testForRandomMatrix();

    }

    public static void testForExitsFiles() {

        //macierze testowe z eportalu
//        testingFile("matrix_6x6.txt");
//        testingFile("matrix_8x8.txt");
//        testingFile("matrix_11x11.txt");

        //macierze testowe ze strony Dr. Jarosława Mierzwy
        //http://jaroslaw.mierzwa.staff.iiar.pwr.wroc.pl/pea-stud/tsp/

//        testingFile("tsp_6_1.txt");
//        testingFile("tsp_6_2.txt");
//        testingFile("tsp_10.txt");
//        testingFile("tsp_12.txt");
//        testingFile("tsp_13.txt");
//        testingFile("tsp_14.txt");

    }

    public static void testForRandomMatrix() {

        //dla 7
//        testForRandomMatrix(7);
        //dla 8
//        testForRandomMatrix(8);
        //dla 9
//        testForRandomMatrix(9);
        //dla 10
//        testForRandomMatrix(10);
        //dla 11
//        testForRandomMatrix(11);
        //dla 12
//        testForRandomMatrix(12);
        //dla 13
//        testForRandomMatrix(13);
        //dla 14
        testForRandomMatrix(14);

    }

    private static void testingFile(String filename) {

        BruteForce bruteForce;

        for (int i = 0; i < 40; i++) {

            fileReader.read(filename);
            bruteForce = new BruteForce(fileReader.getMatrix());

            timer.startTimer();
            bruteForce.solve();
            double time = timer.stopTimer();

            fileWriter.save("test_1.txt", time + ";" + bruteForce);
        }

    }

    private static void testForRandomMatrix(int size) {

        BruteForce bruteForce;

        System.out.println("Dla macierzy " + size);

        for (int i = 47; i < 100; i++) {

            System.out.println((i + 1) + "/100");

            bruteForce = new BruteForce(randGen.randMatrix(size));

            timer.startTimer();
            bruteForce.solve();
            double time = timer.stopTimer();

            fileWriter.save("test_2.txt", (i + 1) + ";" + time + ";" + bruteForce);
        }

    }


}
