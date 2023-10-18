package org.application;

import org.algoritm.BF;
import org.io.FileReader;


public class Application {

    public static void main(String[] args) {

        FileReader fileReader = new FileReader();

        fileReader.read("test_1.txt");

        BF bf = new BF(fileReader.getMatrix());

        bf.solve();
        bf.displaySolution();


    }

}
