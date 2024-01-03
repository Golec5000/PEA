package org.application.alg;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
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
        setMillisActualTime(0);

        setCrossType(crossType);
        setMutationType(mutationType);

    }

    @Override
    public void solve() {

        int[][] population;
        int[][] nextPopulation;
        int[] ratedPopulation;
        int[] permutation;

        nextPopulation = createFilledDoubleTab(getPopulationSize(), getNumberOfVertex() - 1);
        permutation = createFilledTab(getNumberOfVertex() - 1);

        population = IntStream.range(0, getPopulationSize())
                .parallel()
                .mapToObj(i -> creatRandomPath())
                .toArray(int[][]::new);

        ratedPopulation = Arrays.stream(population)
                .parallel()
                .mapToInt(this::calculatePathLength)
                .toArray();

        // Ocena populacji i aktualizacja najlepszego rozwiązania po pierwszym pokoleniu
        checkUpdateSolution(ratedPopulation, population);

        setMillisActualTime(System.currentTimeMillis());

        while (getExecutionTime() < getTimeLimit()) {

            ratedPopulation = Arrays.stream(population)
                    .parallel()
                    .mapToInt(this::calculatePathLength)
                    .toArray();

            // Tworzenie nowej populacji na drodze selekcji
            for (int j = 0; j < getPopulationSize(); j++) {

                int result = Integer.MAX_VALUE;

                // Organizacja turnieju
                for (int k = 0; k < getTournamentSize(); k++) {

                    int index = getRand().nextInt(getPopulationSize());

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

            int p1;
            int p2;
            int p3;

            //Rozpatrywanie mutacji
            for (int j = 0; j < (int) (getMutationRate() * (float) getPopulationSize()) + 1; j++) {

                do {

                    p1 = getRand().nextInt(getNumberOfVertex() - 1);
                    p2 = getRand().nextInt(getNumberOfVertex() - 1);
                    p3 = getRand().nextInt(getPopulationSize());

                } while (p1 == p2);

                switch (getMutationType()) {

                    case INSERTION:
                        insertionMutation(p1, p2, population[p3]);
                        break;

                    case SWAP:
                        swapMutation(p1, p2, population[p3]);
                        break;

                }

            }

            // Ocena populacji i aktualizacja najlepszego rozwiązania po powtaniu nowego pokolenia i mutacji
            ratedPopulation = Arrays.stream(population)
                    .parallel()
                    .mapToInt(this::calculatePathLength)
                    .toArray();

            //Sprawdzenie czy nie znaleziono lepszego rozwiązania
            checkUpdateSolution(ratedPopulation, population);

            setExecutionTime(System.currentTimeMillis() - getMillisActualTime());

        }

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

    private void swapMutation(int i, int j, int[] path) {

        path[i] ^= path[j];
        path[j] ^= path[i];
        path[i] ^= path[j];

    }

    private void insertionMutation(int pos1, int pos2, int[] path) {

        int size = path.length;

        // Ensure pos1 is before pos2
        if (pos1 > pos2) {
            pos1 ^= pos2;
            pos2 ^= pos1;
            pos1 ^= pos2;
        }

        // Remove element at pos2 and shift elements
        int removedElement = path[pos2];
        System.arraycopy(path, pos2 + 1, path, pos2, size - pos2 - 1);

        // Shift elements to make space at pos1 and insert removed element
        System.arraycopy(path, pos1, path, pos1 + 1, size - pos1 - 1);
        path[pos1] = removedElement;

    }

    private int[] creatRandomPath() {
        List<Integer> list = IntStream.range(0, getNumberOfVertex())
                .boxed()
                .collect(Collectors.toList());

        Collections.shuffle(list);

        return list.stream()
                .mapToInt(i -> i)
                .toArray();
    }

    private int calculatePathLength(int[] path) {
        return IntStream.range(0, path.length - 1)
                .map(i -> getMatrix()[path[i]][path[i + 1]])
                .sum() + getMatrix()[path[path.length - 1]][path[0]];
    }

    private boolean isNotInPath(int value, int[] path) {
        return Arrays.stream(path)
                .noneMatch(i -> i == value);
    }

    private int selectLastUnfilled(int[][] tab) {
        return IntStream.range(0, tab.length)
                .filter(i -> tab[i][1] == -1)
                .findFirst()
                .orElse(-2);
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

    private void checkUpdateSolution(int[] ratedPopulation, int[][] population) {

        int bestIndex = IntStream.range(0, ratedPopulation.length)
                .reduce((i, j) -> ratedPopulation[i] < ratedPopulation[j] ? i : j)
                .orElse(-1);

        if (ratedPopulation[bestIndex] < getBestSolution()) {

            setBestSolutionTime(System.currentTimeMillis() - getMillisActualTime());
            setBestSolution(ratedPopulation[bestIndex]);
            setBestPath(population[bestIndex]);

        }

    }

    @Override
    public String toString() {
        return getBestSolutionTime() +
                ";" + getBestSolution() +
                ";" + Arrays.toString(getBestPath()).replace(",", " -");
    }
}
