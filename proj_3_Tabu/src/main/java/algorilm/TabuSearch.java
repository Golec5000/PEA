package algorilm;

import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.IntStream;

@Setter
@Getter
public class TabuSearch implements AlgInterface {

    private long millisActualTime;  //zmienne do pomiaru czasu
    private long executionTime;     //zmienna pomocnicza do pomiaru czasu oraz sprawdzania czy algorytm nie przekroczył czasu
    private long bestSolutionTime;  //zminne przechowująca czas dla najlepszego rozwiązania
    private final long timeLimit;   //zmienna przechowująca maksymalny czas wykonywania algorytmu, ustawiana jest w konstruktorze

    public int[] bestPath;          //najlepsza ścieżka
    public int bestCost;

    Random rand = new Random();
    private final int neighbor;

    private int[][] graph;
    private int numberOfCities;

    public TabuSearch(int[][] matrix, long timeLimit, int neighbor) {
        this.timeLimit = timeLimit;
        this.neighbor = neighbor;

        setGraph(matrix);
        setNumberOfCities(matrix.length);
        setBestCost(Integer.MAX_VALUE);

    }

    @Override
    public void solve() {

        int[] currentPath;
        int[] nextPath;
        int[] savePath;

        int[][] tabuMatrix = new int[getNumberOfCities()][getNumberOfCities()];

        int iterations = 4 * getNumberOfCities(); //liczba iteracji to 4 -krotnosc liczby miast
        int nextCost;
        int currentCost;

        setMillisActualTime(System.currentTimeMillis()); //rozpoczęcie pomiaru czasu

        //stworzenie pierwszej sciezki jako sciezki zachlannej
        currentPath = generateGreedyPath();

        while (true) {

            nextPath = currentPath;
            currentCost = calculatePathCost(currentPath);

            for (int a = 0; a < iterations; a++) {
                currentPath = nextPath.clone();
                nextCost = currentCost;
                savePath = currentPath.clone();//zachowanie ścieżki

                for (int i = 1; i < getNumberOfCities(); i++) {
                    for (int j = i + 1; j < getNumberOfCities(); j++) {

                        switch (getNeighbor()) {
                            case 1:
                                swap(i, j, currentPath);
                                break;
                            case 2:
                                insert(i, j, currentPath);
                                break;
                            case 3:
                                inverse(i, j, currentPath);
                                break;
                        }

                        currentCost = calculatePathCost(currentPath);

                        if ((currentCost < getBestCost())) {
                            setBestPath(currentPath.clone());
                            setBestCost(currentCost);

                            setBestSolutionTime(System.currentTimeMillis() - getMillisActualTime());    //zapisanie czasu dla najlepszego rozwiązania

                            if ((tabuMatrix[i][j] == 0)) {
                                nextCost = currentCost;
                                nextPath = currentPath.clone();
                            }
                        }
                        if ((currentCost < nextCost) && (tabuMatrix[i][j] < a)) {
                            nextCost = currentCost;
                            nextPath = currentPath.clone();
                            tabuMatrix[i][j] += getNumberOfCities();
                        }
                        currentPath = savePath.clone(); // przywrocenie sciezki
                    }
                }
                for (int x = 0; x < getNumberOfCities(); x++) {
                    for (int y = 0; y < getNumberOfCities(); y++) {
                        if (tabuMatrix[x][y] > 0) {
                            tabuMatrix[x][y]--;      //dekrementacja o 1
                        }
                    }
                }

                currentPath = shufflePath(currentPath); //dywersyfikacja sciezki

                setExecutionTime(System.currentTimeMillis() - getMillisActualTime()); //zapisanie czasu wykonania algorytmu

                if (getExecutionTime() > getTimeLimit())
                    return; //przekroczenie maksymalnego czasu, siłowe zakończenie algorytmu
            }
        }

    }

    private int[] shufflePath(int[] currentPath) {//przetasowanie ścieżki

        List<Integer> list = new ArrayList<>(Arrays.stream(currentPath).boxed().toList());
        Collections.shuffle(list);

        return list.stream().mapToInt(i -> i).toArray();
    }

    private int[] generateGreedyPath() {
        int[] greedyPath = new int[getNumberOfCities()];
        boolean[] visited = new boolean[getNumberOfCities()];

        // Zaczynamy od losowego miasta
        int startCity = getRand().nextInt(getNumberOfCities());
        greedyPath[0] = startCity;
        visited[startCity] = true;

        for (int i = 1; i < getNumberOfCities(); i++) {
            int best = -1;
            for (int j = 0; j < getNumberOfCities(); j++) {
                if (!visited[j] && (best == -1 || getGraph()[greedyPath[i - 1]][j] < getGraph()[greedyPath[i - 1]][best])) {
                    best = j;
                }
            }
            greedyPath[i] = best;
            visited[best] = true;
        }

        return greedyPath;
    }

    private int calculatePathCost(int[] path) {
        int cost = IntStream.range(0, path.length - 1)
                .map(i -> getGraph()[path[i]][path[i + 1]])
                .sum();
        cost += getGraph()[path[path.length - 1]][path[0]];
        return cost;
    }

    private void swap(int i, int j, int[] path) {
        int temp = path[i];
        path[i] = path[j];
        path[j] = temp;
    }

    private void insert(int i, int j, int[] path) {
        int[] tempTab = new int[path.length];
        int x;
        for (x = 0; x < i; x++) {
            tempTab[x] = path[x];
        }
        tempTab[i] = path[j];
        for (x = x + 1; x < j + 1; x++) {
            tempTab[x] = path[x - 1];
        }

        for (; x < path.length; x++) {
            tempTab[x] = path[x];
        }

        System.arraycopy(tempTab, 0, path, 0, path.length);
    }

    private void inverse(int i, int j, int[] path) {
        int temp;
        while (i < j) {
            temp = path[i];
            path[i] = path[j];
            path[j] = temp;
            i++;
            j--;
        }
    }

    @Override
    public String toString() {
        return getBestSolutionTime() +
                ";" + getBestCost() +
                ";" + Arrays.toString(getBestPath()).replace(",", " -");
    }
}
