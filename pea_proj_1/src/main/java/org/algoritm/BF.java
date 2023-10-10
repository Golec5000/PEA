package org.algoritm;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BF {

    private int[][] matrix;

    private int numOfVertex;

    private int bestCost;

    private int firstVertex;

    private List<Integer> bestTour;

    public BF(int[][] matrix) {

        setMatrix(matrix);
        setNumOfVertex(matrix.length);
        setBestCost(Integer.MAX_VALUE);
        setFirstVertex(0);

    }

    public List<Integer> solve(int vertexNumber, int sumCost, @NotNull List<Integer> arrayOfVisitedVertex) {

        if (arrayOfVisitedVertex.size() > getNumOfVertex()) return getBestTour();

        for (int i = 0; i < getNumOfVertex(); i++) {

            int repeated = 0;

            if (getMatrix()[vertexNumber][i] == -1) continue;
            if (getBestCost() < sumCost + getMatrix()[vertexNumber][i]) continue;

            if (arrayOfVisitedVertex.size() == getNumOfVertex() && i == getFirstVertex()) {

                arrayOfVisitedVertex.add(i);
                sumCost += getMatrix()[vertexNumber][i];

                if(sumCost < getBestCost()){
                    setBestCost(sumCost);
                    setBestTour(arrayOfVisitedVertex);
                }

            }

            if(arrayOfVisitedVertex.size() == getNumOfVertex()) continue;

            for(int j = 0; j < arrayOfVisitedVertex.size(); j++){

                if(arrayOfVisitedVertex.get(j) == i) continue;

                repeated = 1;
                break;

            }

            if(repeated == 1) continue;

            arrayOfVisitedVertex.add(i);
            solve(i, sumCost + getMatrix()[vertexNumber][i], arrayOfVisitedVertex);
            arrayOfVisitedVertex.remove(arrayOfVisitedVertex.size() - 1);


        }

        return new ArrayList<>();
    }


    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int getNumOfVertex() {
        return numOfVertex;
    }

    public void setNumOfVertex(int numOfVertex) {
        this.numOfVertex = numOfVertex;
    }

    public int getBestCost() {
        return bestCost;
    }

    public void setBestCost(int bestCost) {
        this.bestCost = bestCost;
    }

    public int getFirstVertex() {
        return firstVertex;
    }

    public void setFirstVertex(int firstVertex) {
        this.firstVertex = firstVertex;
    }

    public List<Integer> getBestTour() {
        return bestTour;
    }

    public void setBestTour(List<Integer> bestTour) {
        this.bestTour = bestTour;
    }
}
