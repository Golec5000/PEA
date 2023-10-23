package org.tests;

import java.util.Random;

public class RandGen {

    public int [][] randMatrix(int size){
        Random rand = new Random();

        int[][] matrix = new int[size][size];

        int min = 1;
        int max = 300;

        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){

                if(i == j) matrix[i][j] = -1;
                else matrix[i][j] = rand.nextInt(max - min + 1) + min;

            }
        }



        return matrix;

    }

}
