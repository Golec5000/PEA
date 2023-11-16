package org.algoritm;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Getter
@Setter
public class SA implements AlgInterface {

    private int[][] matrix;

    private int numOfVertex;
    private int bestCost;
    private int currentCost;
    private int bestEpochCost;
    private int epoch;

    private int type;

    private List<Integer> epochBestPath;
    private List<Integer> bestPath;
    private List<Integer> currentPath;
    private List<Integer> nextPath;

    private Random randomGenerator;

    public SA(int[][] matrix, int type) {

        setMatrix(matrix);

        setNumOfVertex(matrix.length);
        setEpoch(0);
        setBestCost(Integer.MAX_VALUE);
        setBestEpochCost(Integer.MAX_VALUE);
        setCurrentCost(Integer.MAX_VALUE);

        setType(type);

        setEpochBestPath(new ArrayList<>());
        setBestPath(new ArrayList<>());
        setCurrentPath(new ArrayList<>());
        setNextPath(new ArrayList<>());

        setRandomGenerator(new Random());

    }

    @Override
    public void solve() {

        if (getType() < 0 || getType() > 2) {
            System.out.println("Brak obsługi takiej opcji");
            return;
        }

        for (int i = 0; i < getNumOfVertex() - 1; i++) getCurrentPath().add(i + 1);

        setNextPath(new ArrayList<>(getCurrentPath()));
        setBestPath(new ArrayList<>(getCurrentPath()));

        setBestCost(getCost());
        setCurrentCost(getBestCost());

        double temperature = Math.pow(getNumOfVertex(), 2) * 1000.0;
        double coolingRate = 0.999999999;
        double absoluteTemperature = 0.00000000001;

        int v1;
        int v2;

        int newCost;

        while (temperature > absoluteTemperature) {

            setEpoch(getEpoch() + 1);

            setEpochBestPath(new ArrayList<>(getCurrentPath()));
            setBestEpochCost(getCurrentCost());

            for (int i = 0; i < Math.max(getNumOfVertex() / 10, 5); i++) {

                setNextPath(new ArrayList<>(getEpochBestPath()));

                v1 = getRandomGenerator().nextInt(getNumOfVertex() - 1);
                v2 = getRandomGenerator().nextInt(getNumOfVertex() - 1);

                while (v1 == v2) v2 = getRandomGenerator().nextInt(getNumOfVertex() - 1);


                switch (getType()){

                    case 0:
                        swapPathInNext(v1, v2);
                        break;
                    case 1:
                        invert(v1, v2);
                        break;
                    case 2:
                        shuffle(v1,v2);
                        break;
                }

                newCost = getCost();

                if (newCost < getBestEpochCost()) {

                    setBestEpochCost(newCost);
                    setEpochBestPath(new ArrayList<>(getNextPath()));

                }


            }

            setNextPath(new ArrayList<>(getEpochBestPath()));

            newCost = getCost();

            if (getCurrentCost() > newCost || (Math.exp(getCurrentCost() - newCost / temperature)) > getRandomGenerator().nextDouble()) {

                setCurrentCost(newCost);
                setCurrentPath(new ArrayList<>(getNextPath()));

                if (getCurrentCost() < getBestCost()) {

                    setBestCost(getCurrentCost());
                    setBestPath(new ArrayList<>(getCurrentPath()));

                } else if (getCurrentCost() > (getBestCost() * 1.5)) {

                    setCurrentPath(new ArrayList<>(getBestPath()));
                    setCurrentCost(getBestCost());

                }

            }

            temperature *= Math.pow(coolingRate, getEpoch());

        }
    }

    private int getCost() {

        int cost = getMatrix()[0][0];

        for (int i = 0; i < getNumOfVertex() - 2; i++)
            cost += getMatrix()[getNextPath().get(i)][getNextPath().get(i + 1)];

        cost += getMatrix()[getNextPath().get(numOfVertex - 2)][0];

        return cost;

    }

    private void swapPathInNext(int index1, int index2) {
        Collections.swap(getNextPath(), index1, index2);
    }

    private void invert(int index1, int index2) {

        if (index2 < index1) {

            int temp = index1;
            index1 = index2;
            index2 = temp;

        }

        while (index1 < index2) {

            Collections.swap(getNextPath(), index1, index2);
            index1++;
            index2--;

        }


    }

    private void shuffle(int index1, int index2) {

        if (index2 < index1) {

            int temp = index1;
            index1 = index2;
            index2 = temp;

        }

        List<Integer> subList = getNextPath().subList(index1, index2 + 1);
        Collections.shuffle(subList);

    }

    @Override
    public String toString() {
        return getBestCost() + ";" + getBestPath().toString().replace(",", " -");
    }

}
