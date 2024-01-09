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
        testForPopulation(fileReader.getMatrix(), 200, 0.01, 0.8, MutationType.SWAP, CrossType.PMX, "file47_pmx_swap_5000.txt", 5000);
//        testForPopulation(fileReader.getMatrix(), 200, 0.01, 0.8, MutationType.SWAP, CrossType.OX, "file47_ox_swap_5000.txt", 5000);
//        testForPopulation(fileReader.getMatrix(), 200, 0.01, 0.8, MutationType.SCRAMBLE, CrossType.PMX, "file47_pmx_scramble_5000.txt", 5000);
//        testForPopulation(fileReader.getMatrix(), 200, 0.01, 0.8, MutationType.SCRAMBLE, CrossType.OX, "file47_ox_scramble_5000.txt", 5000);

//        testForPopulation(fileReader.getMatrix(), 200, 0.01, 0.8, MutationType.SWAP, CrossType.PMX, "file47_pmx_swap_10000.txt", 10000);
//        testForPopulation(fileReader.getMatrix(), 200, 0.01, 0.8, MutationType.SWAP, CrossType.OX, "file47_ox_swap_10000.txt", 10000);
//        testForPopulation(fileReader.getMatrix(), 200, 0.01, 0.8, MutationType.SCRAMBLE, CrossType.PMX, "file47_pmx_scramble_10000.txt", 10000);
//        testForPopulation(fileReader.getMatrix(), 200, 0.01, 0.8, MutationType.SCRAMBLE, CrossType.OX, "file47_ox_scramble_10000.txt", 10000);

//        testForPopulation(fileReader.getMatrix(), 200, 0.01, 0.8, MutationType.SWAP, CrossType.PMX, "file47_pmx_swap_15000.txt", 15000);
//        testForPopulation(fileReader.getMatrix(), 200, 0.01, 0.8, MutationType.SWAP, CrossType.OX, "file47_ox_swap_15000.txt", 15000);
//        testForPopulation(fileReader.getMatrix(), 200, 0.01, 0.8, MutationType.SCRAMBLE, CrossType.PMX, "file47_pmx_scramble_15000.txt", 15000);
//        testForPopulation(fileReader.getMatrix(), 200, 0.01, 0.8, MutationType.SCRAMBLE, CrossType.OX, "file47_ox_scramble_15000.txt", 15000);
    }

    public void testFile170() {

        fileReader.read(file2);
        testForPopulation(fileReader.getMatrix(), 200, 0.01, 0.8, MutationType.SWAP, CrossType.PMX, "file170_pmx_swap_5000.txt", 5000);
//        testForPopulation(fileReader.getMatrix(), 200, 0.01, 0.8, MutationType.SWAP, CrossType.OX, "file170_ox_swap_5000.txt", 5000);
//        testForPopulation(fileReader.getMatrix(), 200, 0.01, 0.8, MutationType.SCRAMBLE, CrossType.PMX, "file170_pmx_scramble_5000.txt", 5000);
//        testForPopulation(fileReader.getMatrix(), 200, 0.01, 0.8, MutationType.SCRAMBLE, CrossType.OX, "file170_ox_scramble_5000.txt", 5000);

//        testForPopulation(fileReader.getMatrix(), 200, 0.01, 0.8, MutationType.SWAP, CrossType.PMX, "file170_pmx_swap_10000.txt", 10000);
//        testForPopulation(fileReader.getMatrix(), 200, 0.01, 0.8, MutationType.SWAP, CrossType.OX, "file170_ox_swap_10000.txt", 10000);
//        testForPopulation(fileReader.getMatrix(), 200, 0.01, 0.8, MutationType.SCRAMBLE, CrossType.PMX, "file170_pmx_scramble_10000.txt", 10000);
//        testForPopulation(fileReader.getMatrix(), 200, 0.01, 0.8, MutationType.SCRAMBLE, CrossType.OX, "file170_ox_scramble_10000.txt", 10000);

//        testForPopulation(fileReader.getMatrix(), 200, 0.01, 0.8, MutationType.SWAP, CrossType.PMX, "file170_pmx_swap_15000.txt", 15000);
//        testForPopulation(fileReader.getMatrix(), 200, 0.01, 0.8, MutationType.SWAP, CrossType.OX, "file170_ox_swap_15000.txt", 15000);
//        testForPopulation(fileReader.getMatrix(), 200, 0.01, 0.8, MutationType.SCRAMBLE, CrossType.PMX, "file170_pmx_scramble_15000.txt", 15000);
//        testForPopulation(fileReader.getMatrix(), 200, 0.01, 0.8, MutationType.SCRAMBLE, CrossType.OX, "file170_ox_scramble_15000.txt", 15000);


    }

    public void testFile403() {

        fileReader.read(file3);
        testForPopulation(fileReader.getMatrix(), 200, 0.01, 0.8, MutationType.SWAP, CrossType.PMX, "file403_pmx_swap_5000.txt", 5000);
//        testForPopulation(fileReader.getMatrix(), 200, 0.01, 0.8, MutationType.SWAP, CrossType.OX, "file403_ox_swap_5000.txt", 5000);
//        testForPopulation(fileReader.getMatrix(), 200, 0.01, 0.8, MutationType.SCRAMBLE, CrossType.PMX, "file403_pmx_scramble_5000.txt", 5000);
//        testForPopulation(fileReader.getMatrix(), 200, 0.01, 0.8, MutationType.SCRAMBLE, CrossType.OX, "file403_ox_scramble_5000.txt", 5000);

//        testForPopulation(fileReader.getMatrix(), 200, 0.01, 0.8, MutationType.SWAP, CrossType.PMX, "file403_pmx_swap_10000.txt", 10000);
//        testForPopulation(fileReader.getMatrix(), 200, 0.01, 0.8, MutationType.SWAP, CrossType.OX, "file403_ox_swap_10000.txt", 10000);
//        testForPopulation(fileReader.getMatrix(), 200, 0.01, 0.8, MutationType.SCRAMBLE, CrossType.PMX, "file403_pmx_scramble_10000.txt", 10000);
//        testForPopulation(fileReader.getMatrix(), 200, 0.01, 0.8, MutationType.SCRAMBLE, CrossType.OX, "file403_ox_scramble_10000.txt", 10000);

//        testForPopulation(fileReader.getMatrix(), 200, 0.01, 0.8, MutationType.SWAP, CrossType.PMX, "file403_pmx_swap_15000.txt", 15000);
//        testForPopulation(fileReader.getMatrix(), 200, 0.01, 0.8, MutationType.SWAP, CrossType.OX, "file403_ox_swap_15000.txt", 15000);
//        testForPopulation(fileReader.getMatrix(), 200, 0.01, 0.8, MutationType.SCRAMBLE, CrossType.PMX, "file403_pmx_scramble_15000.txt", 15000);
//        testForPopulation(fileReader.getMatrix(), 200, 0.01, 0.8, MutationType.SCRAMBLE, CrossType.OX, "file403_ox_scramble_15000.txt", 15000);


    }

    private void testForPopulation(int[][] matrix, int maxGenerations
            , double mutationRate, double crossoverRate
            , MutationType mutationType, CrossType crossType
            , String fileName, int populationSize) {

        fileWriter.save(fileName, "generacja;koszt_scierzki", true);

        int bestCost = Integer.MAX_VALUE;
        TreeMap<Integer, Integer> bestSolutionMap = new TreeMap<>();

        int iterations;

        if(fileName.contains("47")) iterations = 10;
        else if(fileName.contains("170")) iterations = 5;
        else iterations = 2;

        for (int i = 0; i < iterations; i++) {

            System.out.println("Test nr: " + i + "/10");

            GenAlg genAlg = new GenAlg(matrix, populationSize, crossoverRate, mutationRate, 5, crossType, mutationType, maxGenerations, true);
            genAlg.solve();

            if (genAlg.getBestSolutionMap().lastEntry().getValue() < bestCost) {

                bestCost = genAlg.getBestSolutionMap().lastEntry().getValue();
                bestSolutionMap = new TreeMap<>(genAlg.getBestSolutionMap());

            }

        }

        bestSolutionMap.forEach((gen, path) -> fileWriter.save(fileName, gen + ";" + path, true));

    }


}
