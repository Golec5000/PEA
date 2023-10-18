package org.algoritm;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
public class BrandAndBounch {

    private int numOfVertexes;

    private int[][] matrix;

    private boolean[] visited;

    private int bestCost;

    private int[] bestTour;

    public BrandAndBounch(int[][] matrix) {

        setMatrix(matrix);

        setVisited(new boolean[matrix.length]);

        setBestTour(new int[matrix.length + 1]);

        setBestCost(Integer.MAX_VALUE);

        setNumOfVertexes(matrix.length);

    }

    public void TSPSolution() {

        int[] currentPath = new int[getNumOfVertexes() + 1];

        int currentBound = 0;

        Arrays.fill(currentPath, -1);
        Arrays.fill(getVisited(), false);

        for (int i = 0; i < getNumOfVertexes(); i++) currentBound += (firstMin(i) + secondMin(i));

        currentBound = (currentBound == 1) ? (currentBound / 2) + 1 : currentBound / 2;

        getVisited()[0] = true;

        currentPath[0] = 0;

        TSPMatrix(currentBound, 0, 1, currentPath);

    }

    private void TSPMatrix(int currentBound, int currentWeight, int level, int[] currentPath) {

        if (level == getNumOfVertexes()) {


            if (getMatrix()[currentPath[level - 1]][currentPath[0]] != 0) {


                int currRes = currentWeight + getMatrix()[currentPath[level - 1]][currentPath[0]];


                if (currRes < getBestCost()) {

                    creatFinalPath(currentPath);
                    setBestCost(currRes);
                }
            }
            return;
        }


        for (int i = 0; i < getNumOfVertexes(); i++) {


            if (getMatrix()[currentPath[level - 1]][i] != 0 && !visited[i]) {
                int temp = currentBound;
                currentWeight += getMatrix()[currentPath[level - 1]][i];


                if (level == 1)
                    currentBound -= ((firstMin(currentPath[level - 1]) + firstMin(i)) / 2);
                else
                    currentBound -= ((secondMin(currentPath[level - 1]) + firstMin(i)) / 2);


                if (currentBound + currentWeight < getBestCost()) {
                    currentPath[level] = i;

                    visited[i] = true;

                    TSPMatrix(currentBound, currentWeight, level + 1, currentPath);
                }

                currentWeight -= getMatrix()[currentPath[level - 1]][i];
                currentBound = temp;


                Arrays.fill(getVisited(), false);
                for (int j = 0; j <= level - 1; j++)
                    getVisited()[currentPath[j]] = true;
            }
        }

    }


    private void creatFinalPath(int[] currentPath) {
        if (getNumOfVertexes() >= 0) System.arraycopy(currentPath, 0, getBestTour(), 0, getNumOfVertexes());
        getBestTour()[getNumOfVertexes()] = currentPath[0];
    }

    private int firstMin(int i) {
        int min = Integer.MAX_VALUE;

        for (int k = 0; k < getNumOfVertexes(); k++)
            if (getMatrix()[i][k] < min && i != k)
                min = getMatrix()[i][k];

        return min;
    }

    private int secondMin(int i) {

        int first = Integer.MAX_VALUE;
        int second = Integer.MAX_VALUE;

        for (int j = 0; j < getNumOfVertexes(); j++) {

            if (i == j)
                continue;

            if (getMatrix()[i][j] <= first) {

                second = first;
                first = getMatrix()[i][j];

            } else if (getMatrix()[i][j] <= second && getMatrix()[i][j] != first)
                second = getMatrix()[i][j];
        }
        return second;
    }

    @Override
    public String toString() {
        return "BrandAndBounch{" +
                "bestCost=" + bestCost +
                ", bestTour=" + Arrays.toString(bestTour) +
                '}';
    }
}
