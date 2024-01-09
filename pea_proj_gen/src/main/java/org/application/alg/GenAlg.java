package org.application.alg;

import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Setter
@Getter
public class GenAlg implements AlgInterface {

    private Random rand = new Random();

    private TreeMap<Integer, Integer> bestSolutionMap = new TreeMap<>();

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

    private boolean testing;

    public GenAlg(int[][] matrix, int populationSize
            , double crossRate, double mutationRate
            , int tournamentSize, CrossType crossType
            , MutationType mutationType, int maxGeneration
            , boolean testing) {

        setMatrix(matrix);
        setNumberOfVertex(matrix.length);

        setTournamentSize(tournamentSize);
        setBestSolution(Integer.MAX_VALUE);

        setPopulationSize(populationSize);
        setCrossRate(crossRate);

        setMutationRate(mutationRate);

        setCrossType(crossType);
        setMutationType(mutationType);

        setMaxGeneration(maxGeneration);
        setCounter(0);

        setTesting(testing);
    }

    @Override
    public void solve() {

        int[][] population;
        int[][] nextPopulation = createFilledDoubleTab(getPopulationSize(), getNumberOfVertex() - 1);
        int[] permutation = createFilledTab(getNumberOfVertex() - 1);
        int[] ratedPopulation;

        long startTime = System.currentTimeMillis();

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

        for (int generation = 1; getCounter() < getMaxGeneration(); generation++) {

            if(isTesting()) displayPath(generation);

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
                        permutation = population[index];
                    }

                }

                nextPopulation[selectLastUnfilled(nextPopulation)] = permutation;

            }

            // Podmiana pokoleń
            population = nextPopulation;
            nextPopulation = createFilledDoubleTab(getPopulationSize(), getNumberOfVertex() - 1);

            int rotate = getPopulationSize() - (int) (getCrossRate() * (float) getPopulationSize());
            rotate = getRand().nextInt(rotate);

            // Rozpatrywanie krzyżowania
            for (int j = rotate; j < ((int) (getCrossRate() * (float) getPopulationSize()) + rotate); j += 2) {

                int[] child1 = new int[0];
                int[] child2 = new int[0];

                switch (getCrossType()) {

                    case PMX -> {
                        child1 = PMXCross(population[j], population[j + 1]);
                        child2 = PMXCross(population[j + 1], population[j]);
                    }

                    case OX -> {
                        child1 = OXCross(population[j], population[j + 1]);
                        child2 = OXCross(population[j + 1], population[j]);
                    }

                }

                population[j] = child1;
                population[j + 1] = child2;

            }

            int index1;
            int index2;
            int pathIndex;

            //Rozpatrywanie mutacji
            for (int j = 0; j < (int) (getMutationRate() * (float) getPopulationSize()) + 1; j++) {

                do {

                    index1 = getRand().nextInt(getNumberOfVertex() - 2) + 1;
                    index2 = getRand().nextInt(getNumberOfVertex() - 2) + 1;
                    pathIndex = getRand().nextInt(getPopulationSize());

                } while (index1 == index2);

                switch (getMutationType()) {

                    case SWAP:
                        swapMutation(index1, index2, population[pathIndex]);
                        break;

                    case SCRAMBLE:
                        scrambleMutation(index1, index2, population[pathIndex]);
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

            setCounter(getCounter() + 1);

        }

        System.out.println("Czas trwania algorytmu: " + (System.currentTimeMillis() - startTime) + " ms");

    }

    private int[] PMXCross(int[] parent1, int[] parent2) {
        return cross(parent1, parent2, CrossType.PMX);
    }

    private int[] OXCross(int[] parent1, int[] parent2) {
        return cross(parent1, parent2, CrossType.OX);
    }

    private int[] cross(int[] parent1, int[] parent2, CrossType crossType) {

        int size = parent1.length;
        int[] offspring = createFilledTab(size); // Initialize with -1

        // Step 1: Select a random subset of the first parent's path
        int start;
        int end;

        do {

            start = getRand().nextInt(size);
            end = getRand().nextInt(size);

        } while (start == end);

        if (start > end) {

            start ^= end;
            end ^= start;
            start ^= end;

        }

        // Step 2: Copy this subset directly to the offspring
        System.arraycopy(parent1, start, offspring, start, end - start);


        // Step 3: Copy the remaining genes to the offspring in the order they appear in the second parent

        switch (crossType) {

            case PMX:

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

                break;

            case OX:

                int current = end;
                for (int i = end; i < end + size; i++) {
                    int gene = parent2[i % size];
                    if (isNotInPath(gene, offspring)) {
                        offspring[current % size] = gene;
                        current++;
                    }
                }

                break;

        }

        return offspring;

    }


    private void swapMutation(int i, int j, int[] path) {

        path[i] ^= path[j];
        path[j] ^= path[i];
        path[i] ^= path[j];

    }

    private void scrambleMutation(int pos1, int pos2, int[] path) {
        // Ensure pos1 is before pos2
        if (pos1 > pos2) {
            pos1 ^= pos2;
            pos2 ^= pos1;
            pos1 ^= pos2;
        }

        // Extract the subset from the path
        int[] subset = Arrays.copyOfRange(path, pos1, pos2);

        // Convert the subset to a list and shuffle it
        List<Integer> subsetList = Arrays.stream(subset)
                .boxed()
                .collect(Collectors.toList());

        Collections.shuffle(subsetList);

        // Convert the shuffled list back to an array
        subset = subsetList.stream()
                .mapToInt(i -> i)
                .toArray();

        // Insert the shuffled subset back into the path
        System.arraycopy(subset, 0, path, pos1, subset.length);
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
                .orElseThrow(() -> new RuntimeException("Nie znaleziono wolnego miejsca w tablicy"));
    }

    private int[] createFilledTab(int tabSize) {
        return IntStream.range(0, tabSize)
                .map(i -> -1)
                .toArray();
    }

    private int[][] createFilledDoubleTab(int populationSize, int graphSize) {
        return IntStream.range(0, populationSize)
                .parallel()
                .mapToObj(i -> createFilledTab(graphSize))
                .toArray(int[][]::new);
    }

    private void checkUpdateSolution(int[] ratedPopulation, int[][] population) {

        int bestIndex = IntStream.range(0, ratedPopulation.length)
                .reduce((i, j) -> ratedPopulation[i] < ratedPopulation[j] ? i : j)
                .orElseThrow(() -> new RuntimeException("Błąd w tablicy"));

        if (ratedPopulation[bestIndex] < getBestSolution()) {

            setBestSolution(ratedPopulation[bestIndex]);
            setBestPath(population[bestIndex]);
            setCounter(0);

        }

    }

    private void displayPath(int gen) {
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
