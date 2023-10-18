package org.application;

import org.algoritm.BrandAndBounch;
import org.io.FileReader;

public class Application {

    public static void main(String[] args) {

        FileReader fileReader = new FileReader();

        fileReader.read("test_1.txt");


        BrandAndBounch bnb = new BrandAndBounch(fileReader.getMatrix());

        bnb.TSPSolution();
        System.out.println(bnb);

    }

}
