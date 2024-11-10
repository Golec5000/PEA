package org.application.tests;

import org.application.alg.CrossType;
import org.application.alg.GenAlg;
import org.application.alg.MutationType;
import org.application.io.ReadFromFile;
import org.application.io.WriterToFile;

import java.util.TreeMap;

public class TestingClassSequence {


    private final ReadFromFile fileReader = new ReadFromFile();

    private final WriterToFile fileWriter = new WriterToFile();

    private final String file1 = "ftv47.atsp";

    private final String file2 = "ftv170.atsp";

    private final String file3 = "rbg403.atsp";

    public void testFile47PMX() {

        fileReader.read(file1);

        testForPopulationSequence(fileReader.getMatrix(), MutationType.SWAP, CrossType.PMX, "file47_pmx_swap_2000.txt", 2000);
        testForPopulationSequence(fileReader.getMatrix(), MutationType.SCRAMBLE, CrossType.PMX, "file47_pmx_scramble_2000.txt", 2000);

        testForPopulationSequence(fileReader.getMatrix(), MutationType.SWAP, CrossType.PMX, "file47_pmx_swap_1000.txt", 1000);
        testForPopulationSequence(fileReader.getMatrix(), MutationType.SCRAMBLE, CrossType.PMX, "file47_pmx_scramble_1000.txt", 1000);

        testForPopulationSequence(fileReader.getMatrix(), MutationType.SWAP, CrossType.PMX, "file47_pmx_swap_500.txt", 500);
        testForPopulationSequence(fileReader.getMatrix(), MutationType.SCRAMBLE, CrossType.PMX, "file47_pmx_scramble_500.txt", 500);

    }

    public void testFile47OX() {

        fileReader.read(file1);

        testForPopulationSequence(fileReader.getMatrix(), MutationType.SWAP, CrossType.OX, "file47_ox_swap_2000.txt", 2000);
        testForPopulationSequence(fileReader.getMatrix(), MutationType.SCRAMBLE, CrossType.OX, "file47_ox_scramble_2000.txt", 2000);

        testForPopulationSequence(fileReader.getMatrix(), MutationType.SWAP, CrossType.OX, "file47_ox_swap_1000.txt", 1000);
        testForPopulationSequence(fileReader.getMatrix(), MutationType.SCRAMBLE, CrossType.OX, "file47_ox_scramble_1000.txt", 1000);

        testForPopulationSequence(fileReader.getMatrix(), MutationType.SWAP, CrossType.OX, "file47_ox_swap_500.txt", 500);
        testForPopulationSequence(fileReader.getMatrix(), MutationType.SCRAMBLE, CrossType.OX, "file47_ox_scramble_500.txt", 500);

    }

    public void testFile170PMX() {

        fileReader.read(file2);

        testForPopulationSequence(fileReader.getMatrix(), MutationType.SWAP, CrossType.PMX, "file170_pmx_swap_500.txt", 500);
        testForPopulationSequence(fileReader.getMatrix(), MutationType.SCRAMBLE, CrossType.PMX, "file170_pmx_scramble_500.txt", 500);

        testForPopulationSequence(fileReader.getMatrix(), MutationType.SWAP, CrossType.PMX, "file170_pmx_swap_1000.txt", 1000);
        testForPopulationSequence(fileReader.getMatrix(), MutationType.SCRAMBLE, CrossType.PMX, "file170_pmx_scramble_1000.txt", 1000);

        testForPopulationSequence(fileReader.getMatrix(), MutationType.SWAP, CrossType.PMX, "file170_pmx_swap_2000.txt", 2000);
        testForPopulationSequence(fileReader.getMatrix(), MutationType.SCRAMBLE, CrossType.PMX, "file170_pmx_scramble_2000.txt", 2000);

    }

    public void testFile170OX() {

        fileReader.read(file2);

        testForPopulationSequence(fileReader.getMatrix(), MutationType.SWAP, CrossType.OX, "file170_ox_swap_500.txt", 500);
        testForPopulationSequence(fileReader.getMatrix(), MutationType.SCRAMBLE, CrossType.OX, "file170_ox_scramble_500.txt", 500);

        testForPopulationSequence(fileReader.getMatrix(), MutationType.SWAP, CrossType.OX, "file170_ox_swap_1000.txt", 1000);
        testForPopulationSequence(fileReader.getMatrix(), MutationType.SCRAMBLE, CrossType.OX, "file170_ox_scramble_1000.txt", 1000);

        testForPopulationSequence(fileReader.getMatrix(), MutationType.SWAP, CrossType.OX, "file170_ox_swap_2000.txt", 2000);
        testForPopulationSequence(fileReader.getMatrix(), MutationType.SCRAMBLE, CrossType.OX, "file170_ox_scramble_2000.txt", 2000);

    }

    public void testFile403PMX() {

        fileReader.read(file3);

        testForPopulationSequence(fileReader.getMatrix(), MutationType.SWAP, CrossType.PMX, "file403_pmx_swap_500.txt", 500);
        testForPopulationSequence(fileReader.getMatrix(), MutationType.SCRAMBLE, CrossType.PMX, "file403_pmx_scramble_500.txt", 500);

        testForPopulationSequence(fileReader.getMatrix(), MutationType.SWAP, CrossType.PMX, "file403_pmx_swap_1000.txt", 1000);
        testForPopulationSequence(fileReader.getMatrix(), MutationType.SCRAMBLE, CrossType.PMX, "file403_pmx_scramble_1000.txt", 1000);

        testForPopulationSequence(fileReader.getMatrix(), MutationType.SWAP, CrossType.PMX, "file403_pmx_swap_2000.txt", 2000);
        testForPopulationSequence(fileReader.getMatrix(), MutationType.SCRAMBLE, CrossType.PMX, "file403_pmx_scramble_2000.txt", 2000);

    }

    public void testFile403OX() {

        fileReader.read(file3);

        testForPopulationSequence(fileReader.getMatrix(), MutationType.SWAP, CrossType.OX, "file403_ox_swap_500.txt", 500);
        testForPopulationSequence(fileReader.getMatrix(), MutationType.SCRAMBLE, CrossType.OX, "file403_ox_scramble_500.txt", 500);

        testForPopulationSequence(fileReader.getMatrix(), MutationType.SWAP, CrossType.OX, "file403_ox_swap_1000.txt", 1000);
        testForPopulationSequence(fileReader.getMatrix(), MutationType.SCRAMBLE, CrossType.OX, "file403_ox_scramble_1000.txt", 1000);

        testForPopulationSequence(fileReader.getMatrix(), MutationType.SWAP, CrossType.OX, "file403_ox_swap_2000.txt", 2000);
        testForPopulationSequence(fileReader.getMatrix(), MutationType.SCRAMBLE, CrossType.OX, "file403_ox_scramble_2000.txt", 2000);

    }

    private void testForPopulationSequence(int[][] matrix, MutationType mutationType,
                                           CrossType crossType, String fileName,
                                           int populationSize) {

        System.out.println("Test dla pliku: " + fileName);
        int bestCost = Integer.MAX_VALUE;
        TreeMap<Integer, Integer> bestSolutionMap = new TreeMap<>();
        long time = 0;

        int iterations = 3;

        for (int i = 0; i < iterations; i++) {

            System.out.println("Test nr: " + i + "/" + iterations);

            GenAlg genAlg = GenAlg.builder()
                    .matrix(matrix)
                    .numberOfVertex(matrix.length)
                    .populationSize(populationSize)
                    .crossRate(0.8)
                    .mutationRate(0.01)
                    .tournamentSize(5)
                    .crossType(crossType)
                    .mutationType(mutationType)
                    .maxGeneration(2000)
                    .bestSolution(Integer.MAX_VALUE)
                    .counter(0)
                    .build();

            genAlg.solve();

            if (genAlg.getBestSolutionMap().lastEntry().getValue() < bestCost) {

                bestCost = genAlg.getBestSolutionMap().lastEntry().getValue();
                bestSolutionMap = new TreeMap<>(genAlg.getBestSolutionMap());
                time = genAlg.getTime();

            }

        }

        fileWriter.save(fileName, "generacja;koszt_sciezki;czas_wykonania[ms]", true);
        int lastKey = bestSolutionMap.lastKey();
        for (int key : bestSolutionMap.keySet()) {
            if (key == lastKey) {
                fileWriter.save(fileName, key + ";" + bestSolutionMap.get(key) + ";" + time, true);
            } else {
                fileWriter.save(fileName, key + ";" + bestSolutionMap.get(key) + ";", true);
            }
        }

    }


}
