package org.application.alg;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Setter
@Getter
@Builder
public class GenAlg implements AlgInterface {

    private SecureRandom rand;

    private TreeMap<Integer, Integer> bestSolutionMap;

    private int[][] matrix;

    private int[] bestPath;

    private int populationSize;
    private int numberOfVertex;
    private int bestSolution;
    private int tournamentSize;
    private int counter;
    private int maxGeneration;

    private double crossRate;
    private double mutationRate;

    private CrossType crossType;
    private MutationType mutationType;

    @Override
    public void solve() {

        // Initialize the population and the rated population
        int[][] population;
        int[][] nextPopulation = GenAlgHelper.createFilledDoubleTab(getPopulationSize(), getNumberOfVertex() - 1);
        int[] permutation = GenAlgHelper.createFilledTab(getNumberOfVertex() - 1);
        int[] ratedPopulation;

        rand = new SecureRandom();
        bestSolutionMap = new TreeMap<>();

        // Record the start time for performance measurement
        long startTime = System.currentTimeMillis();

        // Generate the initial population
        population = IntStream.range(0, getPopulationSize())
                .mapToObj(i -> GenAlgHelper.creatRandomPath(getNumberOfVertex()))
                .toArray(int[][]::new);

        // Evaluate the initial population
        ratedPopulation = Arrays.stream(population)
                .mapToInt(ints -> GenAlgHelper.calculatePathLength(ints, getMatrix()))
                .toArray();

        // Check and update the best solution found so far
        checkUpdateSolution(ratedPopulation, population);

        // Start the main loop of the genetic algorithm
        for (int generation = 1; generation <= getMaxGeneration(); generation++) {

            // Record the current generation
            getGeneration(generation);

            // Perform selection to create a new population
            for (int j = 0; j < getPopulationSize(); j++) {

                int result = Integer.MAX_VALUE;

                // Perform a tournament for selection
                for (int k = 0; k < getTournamentSize(); k++) {

                    int index = getRand().nextInt(getPopulationSize());

                    if (ratedPopulation[index] < result) {
                        result = ratedPopulation[index];
                        permutation = population[index];
                    }

                }

                // Add the winner of the tournament to the new population
                nextPopulation[GenAlgHelper.selectLastUnfilled(nextPopulation)] = permutation;

            }

            // Replace the old population with the new one
            population = nextPopulation;
            nextPopulation = GenAlgHelper.createFilledDoubleTab(getPopulationSize(), getNumberOfVertex() - 1);

            // Determine the number of individuals that will undergo crossover
            int rotate = getPopulationSize() - (int) (getCrossRate() * (float) getPopulationSize());

            // Randomly select a point in the population up to the number of individuals that will not be crossed
            rotate = getRand().nextInt(rotate);

            // Perform crossover
            for (int j = rotate; j < ((int) (getCrossRate() * (float) getPopulationSize()) + rotate); j += 2) {

                int[] child1 = new int[0];
                int[] child2 = new int[0];

                // Perform the appropriate type of crossover
                switch (getCrossType()) {

                    case PMX -> {
                        child1 = GenAlgHelper.PMXCross(population[j], population[j + 1]);
                        child2 = GenAlgHelper.PMXCross(population[j + 1], population[j]);
                    }

                    case OX -> {
                        child1 = GenAlgHelper.OXCross(population[j], population[j + 1]);
                        child2 = GenAlgHelper.OXCross(population[j + 1], population[j]);
                    }

                }

                // Replace the parents with the offspring
                population[j] = child1;
                population[j + 1] = child2;

            }

            // Perform mutation
            for (int j = 0; j < (int) (getMutationRate() * (float) getPopulationSize()) + 1; j++) {

                int index1;
                int index2;
                int pathIndex;

                do {

                    index1 = getRand().nextInt(getNumberOfVertex() - 2) + 1;
                    index2 = getRand().nextInt(getNumberOfVertex() - 2) + 1;
                    pathIndex = getRand().nextInt(getPopulationSize());

                } while (index1 == index2);

                // Perform the appropriate type of mutation
                switch (getMutationType()) {

                    case SWAP:
                        GenAlgHelper.swapMutation(index1, index2, population[pathIndex]);
                        break;

                    case SCRAMBLE:
                        GenAlgHelper.scrambleMutation(index1, index2, population[pathIndex]);
                        break;

                }

            }

            // Evaluate the new population
            ratedPopulation = Arrays.stream(population)
                    .mapToInt(ints -> GenAlgHelper.calculatePathLength(ints, getMatrix()))
                    .toArray();

            // Check and update the best solution found so far
            checkUpdateSolution(ratedPopulation, population);

        }

        // Print the total running time of the algorithm

        long endTime = System.currentTimeMillis() - startTime;

        System.out.println("Czas trwania algorytmu: " + GenAlgHelper.formatTime(endTime));

    }


    private void checkUpdateSolution(int[] ratedPopulation, int[][] population) {

        int bestIndex = IntStream.range(0, ratedPopulation.length)
                .reduce((i, j) -> ratedPopulation[i] < ratedPopulation[j] ? i : j)
                .orElseThrow(() -> new RuntimeException("Błąd w tablicy"));

        if (ratedPopulation[bestIndex] < getBestSolution()) {
            setBestSolution(ratedPopulation[bestIndex]);
            setBestPath(population[bestIndex]);
        }
    }

    private void getGeneration(int gen) {
        getBestSolutionMap().put(gen, getBestSolution());
    }

    @Override
    public String toString() {
        return getBestSolution() +
                ";" + Arrays.stream(getBestPath())
                .mapToObj(Integer::toString)
                .reduce((str1, str2) -> str1 + " - " + str2)
                .orElseThrow(() -> new RuntimeException("Błąd w wyniku"));
    }


}
