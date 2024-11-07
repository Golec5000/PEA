package org.application.alg;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@Setter
@Builder
public class GenAlgThreads implements AlgInterface {

    private TreeMap<Integer, Integer> bestSolutionMap;

    private int[][] matrix;

    private int[] bestPath;

    private int populationSize;
    private int numberOfVertex;
    private int bestSolution;
    private int tournamentSize;
    private int counter;
    private int maxGeneration;
    private int numThreads;

    private double crossRate;
    private double mutationRate;

    private CrossType crossType;
    private MutationType mutationType;

    private ExecutorService executor;

    private long sequenceTime = 0;
    private long parallelTime = 0;

    @Override
    public void solve() {

        try {

            long startTimeSequence1 = System.currentTimeMillis();
            executor = Executors.newFixedThreadPool(numThreads);

            bestSolutionMap = new TreeMap<>();

            // Podział populacji na chunki
            int[] chunkStarts = new int[numThreads];
            int[] chunkEnds = new int[numThreads];

            int chunkSize = populationSize / numThreads;
            int remainder = populationSize % numThreads;

            for (int i = 0; i < numThreads; i++) {
                chunkStarts[i] = i * chunkSize + Math.min(i, remainder);
                chunkEnds[i] = (i + 1) * chunkSize + Math.min(i + 1, remainder);
            }

            // Inicjalizacja populacji
            int[][] population = IntStream.range(0, getPopulationSize())
                    .mapToObj(i -> GenAlgHelper.creatRandomPath(getNumberOfVertex()))
                    .toArray(int[][]::new);

            int[][] nextPopulation = GenAlgHelper.createFilledDoubleTab(getPopulationSize(), getNumberOfVertex() - 1);

            sequenceTime = (System.currentTimeMillis() - startTimeSequence1);

            // Ewaluacja populacji

            int[] ratedPopulation = new int[populationSize];

            long startTimeParaell1 = System.currentTimeMillis();

            parallelEvaluate(population, ratedPopulation, chunkStarts, chunkEnds, numThreads);

            parallelTime += (System.currentTimeMillis() - startTimeParaell1);

            checkUpdateSolution(ratedPopulation, population);

            // Główna pętla algorytmu
            for (int generation = 1; generation <= getMaxGeneration(); generation++) {

                // Kod zapisujący generację
                getGeneration(generation);

                startTimeParaell1 = System.currentTimeMillis();

                // Równoległa selekcja
                parallelSelection(population, ratedPopulation, nextPopulation, chunkStarts, chunkEnds, numThreads);

                parallelTime += (System.currentTimeMillis() - startTimeParaell1);

                synchronized (this) {
                    startTimeSequence1 = System.currentTimeMillis();
                    population = nextPopulation;
                    nextPopulation = GenAlgHelper.createFilledDoubleTab(getPopulationSize(), getNumberOfVertex() - 1);
                    sequenceTime += (System.currentTimeMillis() - startTimeSequence1);
                }

                startTimeParaell1 = System.currentTimeMillis();

//                // Krzyżowanie
                parallelCrossover(population, numThreads, getPopulationSize() / 2, IntStream.range(0, getPopulationSize() / 2).boxed().collect(Collectors.toList()));


//                // Mutacja
                parallelMutation(population, numThreads, (int) (getMutationRate() * getPopulationSize()));


//                // Ewaluacja nowej populacji
                parallelEvaluate(population, ratedPopulation, chunkStarts, chunkEnds, numThreads);

                parallelTime += (System.currentTimeMillis() - startTimeParaell1);


//                // Aktualizacja najlepszego rozwiązania
                checkUpdateSolution(ratedPopulation, population);

            }


            System.out.println("Czas trwania algorytmu (część sekwencyjna): " + GenAlgHelper.formatTime(sequenceTime));
            System.out.println("Czas trwania algorytmu (część wielowątkowa): " + GenAlgHelper.formatTime(parallelTime));

            executor.shutdownNow();

        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Błąd w trakcie wykonywania algorytmu" + Arrays.toString(e.getStackTrace()));
        }
    }

    private synchronized void checkUpdateSolution(int[] ratedPopulation, int[][] population) {

        long startTimeSequence1 = System.currentTimeMillis();
        int bestIndex = IntStream.range(0, ratedPopulation.length)
                .reduce((i, j) -> ratedPopulation[i] < ratedPopulation[j] ? i : j)
                .orElseThrow(() -> new RuntimeException("Błąd w tablicy"));
        sequenceTime += (System.currentTimeMillis() - startTimeSequence1);

        if (ratedPopulation[bestIndex] < getBestSolution()) {
            setBestSolution(ratedPopulation[bestIndex]);
            setBestPath(population[bestIndex]);
            System.out.println("Znaleziono nowe rozwiązanie: " + getBestSolution());
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

    private void parallelEvaluate(int[][] population, int[] ratedPopulation, int[] chunkStarts, int[] chunkEnds, int numThreads)
            throws InterruptedException, ExecutionException {

        List<Callable<Void>> evaluationTasks = new ArrayList<>();

        for (int i = 0; i < numThreads; i++) {
            int start = chunkStarts[i];
            int end = chunkEnds[i];

            evaluationTasks.add(() -> {
                for (int j = start; j < end; j++) {
                    ratedPopulation[j] = GenAlgHelper.calculatePathLength(population[j], getMatrix());
                }
                return null;
            });
        }

        List<Future<Void>> futures = executor.invokeAll(evaluationTasks);

        for (Future<Void> future : futures) {
            future.get();
        }

    }

    private void parallelSelection(int[][] population, int[] ratedPopulation, int[][] nextPopulation, int[] chunkStarts, int[] chunkEnds, int numThreads)
            throws InterruptedException, ExecutionException {

        List<Callable<Void>> selectionTasks = new ArrayList<>();

        for (int i = 0; i < numThreads; i++) {
            int start = chunkStarts[i];
            int end = chunkEnds[i];

            selectionTasks.add(() -> {

                for (int j = start; j < end; j++) {
                    int result = Integer.MAX_VALUE;
                    int[] permutation = null;

                    for (int k = 0; k < getTournamentSize(); k++) {
                        int index = new Random().nextInt(getPopulationSize());

                        if (ratedPopulation[index] < result) {
                            result = ratedPopulation[index];
                            permutation = population[index];
                        }
                    }

                    synchronized (nextPopulation) {
                        nextPopulation[GenAlgHelper.selectLastUnfilled(nextPopulation)] = permutation;
                    }
                }
                return null;
            });
        }

        List<Future<Void>> futures = executor.invokeAll(selectionTasks);

        for (Future<Void> future : futures) {
            future.get();
        }

    }

    private void parallelCrossover(int[][] population, int numThreads, int numCrossovers, List<Integer> crossoverIndices)
            throws InterruptedException, ExecutionException {

        List<List<Integer>> crossoverChunks = new ArrayList<>();
        int currentIndex = 0;
        for (int i = 0; i < numThreads; i++) {
            int chunkEnd = currentIndex + (numCrossovers / numThreads) + (i < numCrossovers % numThreads ? 1 : 0);
            crossoverChunks.add(crossoverIndices.subList(currentIndex, chunkEnd));
            currentIndex = chunkEnd;
        }

        List<Callable<Void>> crossoverTasks = new ArrayList<>();

        for (List<Integer> chunk : crossoverChunks) {
            crossoverTasks.add(() -> {
                for (int idx : chunk) {
                    int[] child1 = new int[numberOfVertex - 1];
                    int[] child2 = new int[numberOfVertex - 1];

                    switch (getCrossType()) {
                        case PMX -> {
                            child1 = GenAlgHelper.PMXCross(population[idx], population[(idx + 1) % populationSize]);
                            child2 = GenAlgHelper.PMXCross(population[(idx + 1) % populationSize], population[idx]);
                        }
                        case OX -> {
                            child1 = GenAlgHelper.OXCross(population[idx], population[(idx + 1) % populationSize]);
                            child2 = GenAlgHelper.OXCross(population[(idx + 1) % populationSize], population[idx]);
                        }
                    }

                    synchronized (population) {
                        population[idx] = child1;
                        population[(idx + 1) % populationSize] = child2;
                    }
                }
                return null;
            });
        }

        List<Future<Void>> futures = executor.invokeAll(crossoverTasks);

        for (Future<Void> future : futures) {
            future.get();
        }
    }

    private void parallelMutation(int[][] population, int numThreads, int numMutations)
            throws InterruptedException, ExecutionException {

        List<Callable<Void>> mutationTasks = new ArrayList<>();

        for (int i = 0; i < numThreads; i++) {
            int mutationsPerThread = numMutations / numThreads + (i < numMutations % numThreads ? 1 : 0);

            mutationTasks.add(() -> {
                Random localRand = new Random();

                for (int j = 0; j < mutationsPerThread; j++) {
                    int index1, index2, pathIndex;

                    do {
                        index1 = localRand.nextInt(numberOfVertex - 2) + 1;
                        index2 = localRand.nextInt(numberOfVertex - 2) + 1;
                        pathIndex = localRand.nextInt(populationSize);
                    } while (index1 == index2);

                    synchronized (population) {
                        switch (getMutationType()) {
                            case SWAP:
                                GenAlgHelper.swapMutation(index1, index2, population[pathIndex]);
                                break;
                            case SCRAMBLE:
                                GenAlgHelper.scrambleMutation(index1, index2, population[pathIndex]);
                                break;
                        }
                    }
                }
                return null;
            });
        }

        List<Future<Void>> futures = executor.invokeAll(mutationTasks);

        for (Future<Void> future : futures) {
            future.get();
        }
    }
}
