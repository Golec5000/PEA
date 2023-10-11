package org.application;

import org.algoritm.BF;


public class Aprpplication {

    public static void main(String[] args) {

        int[][] graph = {
                {-1, 81, 50, 18, 75, 39, 107, 77, 87, 43},
                {81, -1, 76, 21, 37, 26, 34, 58, 66, 15},
                {50, 76, -1, 24, 14, 58, 100, 68, 33, 30},
                {18, 21, 24, -1, 19, 58, 68, 62, 84, 81},
                {75, 37, 14, 19, -1, 31, 60, 65, 29, 91},
                {39, 26, 58, 58, 31, -1, 64, 21, 42, 46},
                {107, 34, 100, 68, 60, 64, -1, 15, 55, 16},
                {77, 58, 68, 62, 65, 21, 15, -1, 17, 34},
                {87, 66, 33, 84, 29, 42, 55, 17, -1, 68},
                {43, 15, 30, 81, 91, 46, 16, 34, 68, -1}
        };


        BF bf = new BF(graph);

        bf.solve();

        System.out.println(bf.getBestTour());
        System.out.println(bf.getBestCost());


    }

}