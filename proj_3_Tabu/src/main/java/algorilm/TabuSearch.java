package algorilm;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Random;

@Setter
@Getter
public class TabuSearch implements AlgInterface{

    private long millisActualTime; //zmienne do pomiaru czasu
    private long executionTime;
    private long bestSolutionTime;

    public int[] bestPath; //najlepsza ścieżka
    public int bestCost;

    Random rand = new Random();
    private final long timeLimit;
    private final int neighbor;

    private int[][] graph;
    private int numberOfCities;

    public TabuSearch(int [][]matrix, long timeLimit, int neighbor) {
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

        int iterations = 10 * getNumberOfCities(); //liczba iteracji to 10 krotnosc liczby miast
        int nextCost;
        int currentCost;

        setMillisActualTime(System.currentTimeMillis());

        while (true){

            currentPath = generateRandomPath();
            nextPath = currentPath;
            currentCost = calculatePathCost(currentPath);

            for (int a = 0; a < iterations; a++) {
                currentPath = nextPath.clone();
                nextCost = currentCost;
                savePath = currentPath.clone();//zachowanie ścieżki

                for (int i = 1; i < getNumberOfCities(); i++) {
                    for (int j = i + 1; j < getNumberOfCities(); j++) {

                        switch (getNeighbor()){
                            case 1:
                                swap(i, j, currentPath);
                                break;
                            case 2:
                                insert(i, j, currentPath);
                                break;
                            case 3:
                                makeReverse(i, j, currentPath);
                                break;
                        }

                        currentCost = calculatePathCost(currentPath);

                        if ((currentCost < getBestCost())) {
                            setBestPath(currentPath.clone());
                            setBestCost(currentCost);

                            setBestSolutionTime(System.currentTimeMillis() - getMillisActualTime());

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
                            tabuMatrix[x][y] -= 1;      //dekrementacja o 1
                        }
                    }
                }

                setExecutionTime(System.currentTimeMillis() - getMillisActualTime());

                if (getExecutionTime() > getTimeLimit()) return; //koniec algorytmu
            }
        }

    }

    private int[] generateRandomPath() { //generowanie losowej ścieżki
        int[] randomPath = new int[getNumberOfCities()];

        for (int i = 0; i < getNumberOfCities(); i++) randomPath[i] = i;

        for (int i = 1; i < randomPath.length; i++) {//funkcja losująca kolejność
            int randomIndexToSwap = getRand().nextInt(randomPath.length - 1) + 1;//wszystkie oprocz 0
            int temp = randomPath[randomIndexToSwap];
            randomPath[randomIndexToSwap] = randomPath[i];
            randomPath[i] = temp;
        }

        return randomPath;
    }

    private int calculatePathCost(int[] path) {
        int cost = 0;

        for (int i = 0; i < path.length - 1; i++) cost += getGraph()[path[i]][path[i + 1]];
        cost += getGraph()[(path[(path.length - 1)])][path[0]];

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

    private void makeReverse(int i, int j, int[] path) {
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
