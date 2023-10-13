package org.application;

import org.algoritm.BF;
import org.io.FileReader;


public class Aprpplication {

    public static void main(String[] args) {

        FileReader fileReader = new FileReader();

        fileReader.read("tsp_10.txt");

        fileReader.display();

        BF bf = new BF(fileReader.getMatrix());

        bf.solve();

        bf.displaySolution();


    }

}
