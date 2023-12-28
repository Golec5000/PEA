package org.application.alg;

import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Setter
@Getter
public class GenAlg implements AlgInterface {

    private Random rand;

    private int[][] matrix;

    private int[] bestPath;

    private int populationSize;
    private int numberOfVertex;
    private int bestSolution;
    private int tournamentSize;

    private double crossRate;
    private double mutationRate;

    private long millisActualTime;
    private long executionTime;
    private long bestSolutionTime;
    private long timeLimit;

    private CrossType crossType;
    private MutationType mutationType;

    public GenAlg(int[][] matrix, int populationSize, double crossRate, double mutationRate, long timeLimit, int tournamentSize, CrossType crossType, MutationType mutationType) {

        setMatrix(matrix);
        setNumberOfVertex(matrix.length);

        setTournamentSize(tournamentSize);
        setBestSolution(Integer.MAX_VALUE);

        setPopulationSize(populationSize);
        setCrossRate(crossRate);

        setMutationRate(mutationRate);
        setTimeLimit(timeLimit);
        setExecutionTime(0);

        setCrossType(crossType);
        setMutationType(mutationType);

        setRand(new Random());

    }

    @Override
    public void solve() {

        int[][] population;
        int[][] nextPopulation;
        int[] ratedPopulation;
        int[] permutation;
        int index, p1, p2, p3;

        population = createFilledDoubleTab(getPopulationSize(), getNumberOfVertex() - 1);
        nextPopulation = createFilledDoubleTab(getPopulationSize(), getNumberOfVertex() - 1);
        permutation = createFilledTab(getNumberOfVertex() - 1);

        for (int i = 0; i < getPopulationSize(); i++) population[i] = creatRandomPath();

        ratedPopulation = createFilledTab(getPopulationSize());

        for (int i = 0; i < getPopulationSize(); i++) ratedPopulation[i] = calculatePathLength(population[i]);

        if (ratedPopulation[findMinIndex(ratedPopulation)] < getBestSolution()) {
            setBestSolutionTime(System.currentTimeMillis());
            setBestSolution(ratedPopulation[findMinIndex(ratedPopulation)]);
            setBestPath(population[findMinIndex(ratedPopulation)]);
        }

        setMillisActualTime(System.currentTimeMillis());

        while (getExecutionTime() < getTimeLimit()) {

            ratedPopulation = createFilledTab(getPopulationSize());


            for (int i = 0; i < getPopulationSize(); i++) ratedPopulation[i] = calculatePathLength(population[i]);

            // Tworzenie nowej populacji na drodze selekcji
            for (int j = 0; j < getPopulationSize(); j++) {
                int result = Integer.MAX_VALUE;

                // Organizacja turnieju
                for (int k = 0; k < getTournamentSize(); k++) {

                    index = getRand().nextInt(getPopulationSize());

                    if (ratedPopulation[index] < result) {
                        result = ratedPopulation[index];
                        permutation = population[index].clone();
                    }
                }
                nextPopulation[selectLastUnfilled(nextPopulation)] = permutation.clone();
            }

            // Podmiana pokoleń
            population = nextPopulation;
            nextPopulation = createFilledDoubleTab(getPopulationSize(), getNumberOfVertex() - 1);

            int rotate = getPopulationSize() - (int) (getCrossRate() * (float) getPopulationSize());
            rotate = getRand().nextInt(rotate);

            // Rozpatrywanie krzyżowania
            for (int j = rotate; j < ((int) (getCrossRate() * (float) getPopulationSize()) + rotate); j += 2) {
                switch (getCrossType()) {
                    case PMX:
                        population[j] = PMXCross(population[j], population[j + 1]);
                        population[j + 1] = PMXCross(population[j + 1], population[j]);
                        break;
                    case OX:
                        population[j] = OXCross(population[j], population[j + 1]);
                        population[j + 1] = OXCross(population[j + 1], population[j]);
                        break;
                }
            }

            //Rozpatrywanie mutacji
            for (int j = 0; j < (int) (getMutationRate() * (float) getPopulationSize()) + 1; j++) {
                do {
                    p1 = getRand().nextInt(getNumberOfVertex() - 1);
                    p2 = getRand().nextInt(getNumberOfVertex() - 1);
                    p3 = getRand().nextInt(getPopulationSize());
                } while (p1 == p2);

                switch (getMutationType()) {
                    case INVERSION:
                        inversionMutation(p1, p2, population[p3]);
                        break;
                    case SWAP:
                        swapMutation(p1, p2, population[p3]);
                        break;
                }
            }

            for (int i = 0; i < getPopulationSize(); i++) ratedPopulation[i] = calculatePathLength(population[i]);

            if (ratedPopulation[findMinIndex(ratedPopulation)] < getBestSolution()) {
                setBestSolutionTime(System.currentTimeMillis() - getMillisActualTime());
                setBestSolution(ratedPopulation[findMinIndex(ratedPopulation)]);
                setBestPath(population[findMinIndex(ratedPopulation)]);
            }

            setExecutionTime(System.currentTimeMillis() - getMillisActualTime());

        }

    }

    private int selectLastUnfilled(int[][] tab) {
        return IntStream.range(0, tab.length)
                .filter(i -> tab[i][1] == -1)
                .findFirst()
                .orElse(-2);
    }

    private int[] creatRandomPath() {
        List<Integer> list = IntStream.range(0, getNumberOfVertex()).boxed().collect(Collectors.toList());
        Collections.shuffle(list);
        return list.stream().mapToInt(i -> i).toArray();
    }

    private void swapMutation(int i, int j, int[] path) {
        path[i] = path[i] ^ path[j];
        path[j] = path[i] ^ path[j];
        path[i] = path[i] ^ path[j];
    }

    private void inversionMutation(int x, int z, int[] path) {

        int start = x;
        int end = z;

        if (x > z) {
            start = z;
            end = x;
        }

        for (int i = start, j = end; i < j; i++, j--) {
            path[i] = path[i] ^ path[j];
            path[j] = path[i] ^ path[j];
            path[i] = path[i] ^ path[j];
        }
    }

    private int calculatePathLength(int[] path) {
        return IntStream.range(0, path.length - 1)
                .map(i -> getMatrix()[path[i]][path[i + 1]])
                .sum() + getMatrix()[path[path.length - 1]][path[0]];
    }

    private int[] PMXCross(int[] parent1, int[] parent2) {
        return cross(parent1, parent2, true);
    }

    private int[] OXCross(int[] parent1, int[] parent2) {
        return cross(parent1, parent2, false);
    }

    private int[] cross(int[] parent1, int[] parent2, boolean isPMX) {
        int size = parent1.length;
        int[] offspring = createFilledTab(size); // Initialize with -1

        // Step 1: Select a random subset of the first parent's path
        int start = getRand().nextInt(size);
        int end = getRand().nextInt(size - start) + start;

        // Step 2: Copy this subset directly to the offspring
        System.arraycopy(parent1, start, offspring, start, end - start);

        // Step 3: Copy the remaining genes to the offspring in the order they appear in the second parent
        if (isPMX) {

            for (int i = 0; i < size; i++) {
                if (offspring[i] == -1) {
                    for (int j = 0; j < size; j++) {
                        int gene = parent2[j];
                        if (isNotInPath(gene, offspring)) {
                            offspring[i] = gene;
                            break;
                        }
                    }
                }
            }

        } else {

            int current = end;
            for (int i = end; i < end + size; i++) {
                int gene = parent2[i % size];
                if (isNotInPath(gene, offspring)) {
                    offspring[current % size] = gene;
                    current++;
                }
            }

        }

        return offspring;
    }

    private boolean isNotInPath(int value, int[] path) {
        return Arrays.stream(path)
                .noneMatch(i -> i == value);
    }

    private int findMinIndex(int[] array) {
        return IntStream.range(0, array.length)
                .reduce((i, j) -> array[i] < array[j] ? i : j)
                .orElse(-1);
    }

    private int[] createFilledTab(int tabSize) {
        int[] tab = new int[tabSize];
        Arrays.fill(tab, -1);
        return tab;
    }

    private int[][] createFilledDoubleTab(int populationSize, int graphSize) {
        int[][] tab = new int[populationSize][graphSize];
        for (int i = 0; i < populationSize; i++) tab[i] = createFilledTab(graphSize);
        return tab;
    }

    @Override
    public String toString() {
        return getBestSolutionTime() +
                ";" + getBestSolution() +
                ";" + Arrays.toString(getBestPath()).replace(",", " -");
    }
}
