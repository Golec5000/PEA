package org.application.alg;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GenAlgHelper {


    public static void swapMutation(int i, int j, int[] path) {

        path[i] ^= path[j];
        path[j] ^= path[i];
        path[i] ^= path[j];

    }

    public static void scrambleMutation(int pos1, int pos2, int[] path) {
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

    public static int[] creatRandomPath(int getNumberOfVertex) {
        List<Integer> list = IntStream.range(0, getNumberOfVertex)
                .boxed()
                .collect(Collectors.toList());

        Collections.shuffle(list);

        return list.stream()
                .mapToInt(i -> i)
                .toArray();
    }

    public static int calculatePathLength(int[] path, int[][] matrix) {
        return IntStream.range(0, path.length - 1)
                .map(i -> matrix[path[i]][path[i + 1]])
                .sum() + matrix[path[path.length - 1]][path[0]];
    }

    public static boolean isNotInPath(int value, int[] path) {
        return Arrays.stream(path)
                .noneMatch(i -> i == value);
    }

    public static int selectLastUnfilled(int[][] tab) {
        return IntStream.range(0, tab.length)
                .filter(i -> tab[i][1] == -1)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nie znaleziono wolnego miejsca w tablicy"));
    }

    public static int[] createFilledTab(int tabSize) {
        return IntStream.range(0, tabSize)
                .map(i -> -1)
                .toArray();
    }

    public static int[][] createFilledDoubleTab(int populationSize, int graphSize) {
        return IntStream.range(0, populationSize)
                .mapToObj(i -> createFilledTab(graphSize))
                .toArray(int[][]::new);
    }

    public static String formatTime(long millis) {
        long hours = millis / 3600000;
        millis %= 3600000;
        long minutes = millis / 60000;
        millis %= 60000;
        long seconds = millis / 1000;
        millis %= 1000;
        return String.format("%02d:%02d:%02d:%03d", hours, minutes, seconds, millis);
    }

    public static int[] PMXCross(int[] parent1, int[] parent2) {
        return cross(parent1, parent2, CrossType.PMX);
    }

    public static int[] OXCross(int[] parent1, int[] parent2) {
        return cross(parent1, parent2, CrossType.OX);
    }

    private static int[] cross(int[] parent1, int[] parent2, CrossType crossType) {

        int size = parent1.length;
        int[] offspring = createFilledTab(size); // Initialize with -1

        // Step 1: Select a random subset of the first parent's path
        int start;
        int end;

        do {

            start = new Random().nextInt(size);
            end = new Random().nextInt(size);

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

}
