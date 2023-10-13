package org.algoritm;

import lombok.Getter;
import lombok.Setter;

import org.helpStructures.Node;

import java.util.List;

@Setter
@Getter
public class BnB {

    private int[][] matrix;

    private int numOfVertex;

    private int bestCost;

    private List<Integer> bestTour;

    public void solve(){

    }

    private Node createReducedMatrix(Node node, int currentVertex){


        return null;
    }

    public void displaySolution(){
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "BnB{" +
                "bestCost=" + bestCost +
                ", bestTour=" + bestTour +
                '}';
    }
}
