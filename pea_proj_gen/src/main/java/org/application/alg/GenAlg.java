package org.application.alg;

import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.IntStream;


@Setter
@Getter
public class GenAlg implements AlgInterface {

    private Random rand = new Random();

    private int[][] matrix;

    private int[] bestPath;

    private int populationSize;
    private int numberOfVertex;
    private int bestSolution;

    private double crossRate;
    private double mutationRate;

    private long millisActualTime;
    private long executionTime;
    private long bestSolutionTime;
    private long timeLimit;


    public GenAlg(int[][] matrix, int populationSize, double crossRate, double mutationRate, long timeLimit) {
        setMatrix(matrix);
        setNumberOfVertex(matrix.length);
        setBestSolution(Integer.MAX_VALUE);
        setPopulationSize(populationSize);
        setCrossRate(crossRate);
        setMutationRate(mutationRate);
        setTimeLimit(timeLimit);
    }

    @Override
    public void solve() {

        int[][] population = new int[populationSize][getNumberOfVertex()];
        int[][] nextPopulation = new int[populationSize][getNumberOfVertex()];
        int[] ratedPopulation = new int[getNumberOfVertex()];
        int[] permutation;
        int tournamentSize = 5;

        for (int i = 0; i < populationSize; i++) population[i] = creatRandomPath();

        for (int i = 0; i < populationSize; i++) ratedPopulation[i] = calculatePathLength(population[i]);

        if (ratedPopulation[findMinIndex(ratedPopulation)] < getBestSolution()) {
            setBestSolution(ratedPopulation[findMinIndex(ratedPopulation)]);
            setBestPath(population[findMinIndex(ratedPopulation)]);
        }

        setMillisActualTime(System.currentTimeMillis());


    }

    private int[] creatRandomPath() {

        int[] path = new int[getNumberOfVertex()];

        for (int i = 0; i < getNumberOfVertex(); i++) path[i] = i;

        List<Integer> list = new ArrayList<>(Arrays.stream(path).boxed().toList());
        Collections.shuffle(list);

        return list.stream().mapToInt(i -> i).toArray();

    }

    private void swapMutation(int[] path) {

        int index1 = rand.nextInt(getNumberOfVertex());
        int index2 = rand.nextInt(getNumberOfVertex());

        int temp = path[index1];
        path[index1] = path[index2];
        path[index2] = temp;

    }

    private int calculatePathLength(int[] path) {

        return IntStream.range(0, path.length - 1)
                .map(i -> getMatrix()[path[i]][path[i + 1]])
                .sum() + getMatrix()[path[path.length - 1]][path[0]];
    }

    private void insertMutation(int[] path) {

        int index1 = rand.nextInt(getNumberOfVertex());
        int index2 = rand.nextInt(getNumberOfVertex());

        int temp = path[index1];

        if (index1 < index2) {
            for (int i = index1; i < index2; i++) {
                path[i] = path[i + 1];
            }
        } else {
            for (int i = index1; i > index2; i--) {
                path[i] = path[i - 1];
            }
        }
        path[index2] = temp;

    }

    private void OXCross() {

    }

    private void PMXCross() {

    }

    private boolean isInPath(int value, int[] path) {
        return Arrays.stream(path).anyMatch(i -> i == value);
    }

    private int findMinIndex(int[] array) {
        return IntStream.range(0, array.length)
                .boxed()
                .min(Comparator.comparingInt(i -> array[i]))
                .orElse(-1);
    }
}
