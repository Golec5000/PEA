package org.application.tests;

import org.application.alg.CrossType;
import org.application.alg.GenAlg;
import org.application.alg.MutationType;
import org.application.io.ReadFromFile;
import org.application.io.WriterToFile;

import java.util.TreeMap;

public class TestingClass {


    private final ReadFromFile fileReader = new ReadFromFile();

    private final WriterToFile fileWriter = new WriterToFile();

    private final String file1 = "ftv47.atsp";

    private final String file2 = "ftv170.atsp";

    private final String file3 = "rbg403.atsp";

    public void testFile47() {

        fileReader.read(file1);

        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.8, MutationType.SWAP, CrossType.PMX, "file47_pmx_swap_2000.txt", 2000);
        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.8, MutationType.SWAP, CrossType.OX, "file47_ox_swap_2000.txt", 2000);
        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.8, MutationType.SCRAMBLE, CrossType.PMX, "file47_pmx_scramble_2000.txt", 2000);
        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.8, MutationType.SCRAMBLE, CrossType.OX, "file47_ox_scramble_2000.txt", 2000);

        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.8, MutationType.SWAP, CrossType.PMX, "file47_pmx_swap_1000.txt", 1000);
        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.8, MutationType.SWAP, CrossType.OX, "file47_ox_swap_1000.txt", 1000);
        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.8, MutationType.SCRAMBLE, CrossType.PMX, "file47_pmx_scramble_1000.txt", 1000);
        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.8, MutationType.SCRAMBLE, CrossType.OX, "file47_ox_scramble_1000.txt", 1000);

        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.8, MutationType.SWAP, CrossType.PMX, "file47_pmx_swap_500.txt", 500);
        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.8, MutationType.SWAP, CrossType.OX, "file47_ox_swap_500.txt", 500);
        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.8, MutationType.SCRAMBLE, CrossType.PMX, "file47_pmx_scramble_500.txt", 500);
        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.8, MutationType.SCRAMBLE, CrossType.OX, "file47_ox_scramble_500.txt", 500);

    }

    public void testFile170() {

        fileReader.read(file2);

        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.8, MutationType.SWAP, CrossType.PMX, "file170_pmx_swap_500.txt", 500);
        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.8, MutationType.SWAP, CrossType.OX, "file170_ox_swap_500.txt", 500);
        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.8, MutationType.SCRAMBLE, CrossType.PMX, "file170_pmx_scramble_500.txt", 500);
        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.8, MutationType.SCRAMBLE, CrossType.OX, "file170_ox_scramble_500.txt", 500);

        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.8, MutationType.SWAP, CrossType.PMX, "file170_pmx_swap_1000.txt", 1000);
        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.8, MutationType.SWAP, CrossType.OX, "file170_ox_swap_1000.txt", 1000);
        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.8, MutationType.SCRAMBLE, CrossType.PMX, "file170_pmx_scramble_1000.txt", 1000);
        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.8, MutationType.SCRAMBLE, CrossType.OX, "file170_ox_scramble_1000.txt", 1000);

        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.8, MutationType.SWAP, CrossType.PMX, "file170_pmx_swap_2000.txt", 2000);
        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.8, MutationType.SWAP, CrossType.OX, "file170_ox_swap_2000.txt", 2000);
        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.8, MutationType.SCRAMBLE, CrossType.PMX, "file170_pmx_scramble_2000.txt", 2000);
        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.8, MutationType.SCRAMBLE, CrossType.OX, "file170_ox_scramble_2000.txt", 2000);

    }

    public void testFile403() {

        fileReader.read(file3);

        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.8, MutationType.SWAP, CrossType.PMX, "file403_pmx_swap_500.txt", 500);
        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.8, MutationType.SWAP, CrossType.OX, "file403_ox_swap_500.txt", 500);
        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.8, MutationType.SCRAMBLE, CrossType.PMX, "file403_pmx_scramble_500.txt", 500);
        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.8, MutationType.SCRAMBLE, CrossType.OX, "file403_ox_scramble_500.txt", 500);

        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.8, MutationType.SWAP, CrossType.PMX, "file403_pmx_swap_1000.txt", 1000);
        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.8, MutationType.SWAP, CrossType.OX, "file403_ox_swap_1000.txt", 1000);
        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.8, MutationType.SCRAMBLE, CrossType.PMX, "file403_pmx_scramble_1000.txt", 1000);
        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.8, MutationType.SCRAMBLE, CrossType.OX, "file403_ox_scramble_1000.txt", 1000);

        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.8, MutationType.SWAP, CrossType.PMX, "file403_pmx_swap_2000.txt", 2000);
        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.8, MutationType.SWAP, CrossType.OX, "file403_ox_swap_2000.txt", 2000);
        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.8, MutationType.SCRAMBLE, CrossType.PMX, "file403_pmx_scramble_2000.txt", 2000);
        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.8, MutationType.SCRAMBLE, CrossType.OX, "file403_ox_scramble_2000.txt", 2000);

    }

    public void testMutationRatio() {

        fileReader.read(file1);

        testForPopulation(fileReader.getMatrix(), 2000, 0.05, 0.8, MutationType.SCRAMBLE, CrossType.OX, "file47_ox_scramble_1000_mutation_005.txt", 1000);
        testForPopulation(fileReader.getMatrix(), 2000, 0.1, 0.8, MutationType.SCRAMBLE, CrossType.OX, "file47_ox_scramble_1000_mutation_01.txt", 1000);

        fileReader.read(file2);

        testForPopulation(fileReader.getMatrix(), 2000, 0.05, 0.8, MutationType.SWAP, CrossType.OX, "file170_ox_swap_2000_mutation_005.txt", 2000);
        testForPopulation(fileReader.getMatrix(), 2000, 0.1, 0.8, MutationType.SWAP, CrossType.OX, "file170_ox_swap_2000_mutation_01.txt", 2000);

        fileReader.read(file3);

        testForPopulation(fileReader.getMatrix(), 2000, 0.05, 0.8, MutationType.SCRAMBLE, CrossType.OX, "file403_ox_scramble_2000_mutation_005.txt", 2000);
        testForPopulation(fileReader.getMatrix(), 2000, 0.1, 0.8, MutationType.SCRAMBLE, CrossType.OX, "file403_ox_scramble_2000_mutation_01.txt", 2000);
    }

    public void testCrossRatio() {

        fileReader.read(file1);

        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.5, MutationType.SCRAMBLE, CrossType.OX, "file47_ox_scramble_1000_cross_05.txt", 1000);
        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.7, MutationType.SCRAMBLE, CrossType.OX, "file47_ox_scramble_1000_cross_07.txt", 1000);
        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.9, MutationType.SCRAMBLE, CrossType.OX, "file47_ox_scramble_1000_cross_09.txt", 1000);

        fileReader.read(file2);

        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.5, MutationType.SWAP, CrossType.OX, "file170_ox_swap_2000_cross_05.txt", 2000);
        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.7, MutationType.SWAP, CrossType.OX, "file170_ox_swap_2000_cross_07.txt", 2000);
        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.9, MutationType.SWAP, CrossType.OX, "file170_ox_swap_2000_cross_09.txt", 2000);

        fileReader.read(file3);

        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.5, MutationType.SCRAMBLE, CrossType.OX, "file403_ox_scramble_2000_cross_05.txt", 2000);
        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.7, MutationType.SCRAMBLE, CrossType.OX, "file403_ox_scramble_2000_cross_07.txt", 2000);
        testForPopulation(fileReader.getMatrix(), 2000, 0.01, 0.9, MutationType.SCRAMBLE, CrossType.OX, "file403_ox_scramble_2000_cross_09.txt", 2000);

    }

    private void testForPopulation(int[][] matrix, int maxGenerations
            , double mutationRate, double crossoverRate
            , MutationType mutationType, CrossType crossType
            , String fileName, int populationSize) {

        System.out.println("Test dla pliku: " + fileName);

        fileWriter.save(fileName, "generacja;koszt_scierzki", true);

        int bestCost = Integer.MAX_VALUE;
        TreeMap<Integer, Integer> bestSolutionMap = new TreeMap<>();

        int iterations;

        if (fileName.contains("47")) iterations = 5;
        else iterations = 1;

        for (int i = 0; i < iterations; i++) {

            System.out.println("Test nr: " + i + "/" + iterations);

            GenAlg genAlg = new GenAlg(matrix, populationSize, crossoverRate, mutationRate, 5, crossType, mutationType, maxGenerations);
            genAlg.solve();

            if (genAlg.getBestSolutionMap().lastEntry().getValue() < bestCost) {

                bestCost = genAlg.getBestSolutionMap().lastEntry().getValue();
                bestSolutionMap = new TreeMap<>(genAlg.getBestSolutionMap());

            }

        }

        bestSolutionMap.forEach((gen, path) -> fileWriter.save(fileName, gen + ";" + path, true));

    }


}
