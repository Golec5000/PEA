package org.algoritm;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BruteForce {

    private int[][] matrix;

    private int numOfVertex;

    private int bestCost;

    private List<Integer> bestTour;


    public BruteForce(int[][] matrix) {

        setMatrix(matrix);
        setNumOfVertex(matrix.length);
        setBestCost(Integer.MAX_VALUE);
        setBestTour(new ArrayList<>());
    }

    public void solve() {

        List<Integer> arr = new ArrayList<>();

        for (int i = 0; i < getNumOfVertex(); i++) arr.add(i);

        List<Integer> tmp_permutation = new ArrayList<>(arr);
        tmp_permutation.add(tmp_permutation.get(0));

        int cost = calcPath(tmp_permutation);

        if (cost < getBestCost()) {

            setBestCost(cost);
            setBestTour(new ArrayList<>(tmp_permutation));

        }

        int n = arr.size();
        int[] c = new int[n];
        int i = 0;


        while (i < n) {
            if (c[i] < i) {
                if (i % 2 == 0) {
                    int temp = arr.get(0);
                    arr.set(0, arr.get(i));
                    arr.set(i, temp);
                } else {
                    int temp = arr.get(c[i]);
                    arr.set(c[i], arr.get(i));
                    arr.set(i, temp);
                }

                tmp_permutation = new ArrayList<>(arr);         //ze stworzonej permutacji tworzymy pomoniczą liste
                // zawierającą etap powtotu do wierzchołka startowego
                tmp_permutation.add(tmp_permutation.get(0));

                cost = calcPath(tmp_permutation);

                if (cost < getBestCost()) {                     //przypisanie najlepszej znalezionej ścierzki

                    setBestCost(cost);
                    setBestTour(new ArrayList<>(tmp_permutation));

                }

                c[i]++;
                i = 0;
            } else {
                c[i] = 0;
                i++;
            }
        }

    }

    public void displaySolution() {
        System.out.println(this);
    }

    private int calcPath(List<Integer> tmpPermutation) {

        int sum = 0;

        for (int i = 0; i < tmpPermutation.size() - 1; i++) {

            int u = tmpPermutation.get(i);                    //wierzchołek wyjściowy
            int v = tmpPermutation.get(i + 1);                //wierzchołek do którego idziemy na ścierzce

            sum += getMatrix()[u][v];

        }

        return sum;
    }

    @Override
    public String toString() {
        return bestCost + ";" + bestTour.toString().replace(",", " -");
    }
}
