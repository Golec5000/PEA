package org.algoritm;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Setter
@Getter
public class DynamicProgramming implements AlgInterface {

    private int numOfVertex;
    private int startVertex;
    private int[][] graph;
    private List<Integer> tour;
    private int bestCost;
    private boolean ranSolver;

    public DynamicProgramming(int startVertex, int[][] matrix) {

        setGraph(matrix);
        setStartVertex(startVertex);
        setNumOfVertex(matrix.length);
        setBestCost(Integer.MAX_VALUE);
        setTour(new ArrayList<>());
        setRanSolver(false);

    }


    @Override
    public void solve() {

        if (isRanSolver()) return;

        int endState = (1 << getNumOfVertex()) - 1;

        int[][] memo = new int[getNumOfVertex()][1 << getNumOfVertex()];

        // Add all outgoing edges from the starting node to memo table.
        for (int end = 0; end < getNumOfVertex(); end++) {

            if (end == getStartVertex()) continue;

            memo[end][(1 << getStartVertex()) | (1 << end)] = getGraph()[getStartVertex()][end];

        }

        for (int i = 3; i <= getNumOfVertex(); i++) {
            for (int subset : combinations(i, getNumOfVertex())) {

                if (notIn(getStartVertex(), subset)) continue;

                for (int next = 0; next < getNumOfVertex(); next++) {

                    if (next == getStartVertex() || notIn(next, subset)) continue;

                    int minDist = getMinDist(subset, next, memo);

                    memo[next][subset] = minDist;

                }

            }
        }

        // Connect tour back to starting node and minimize cost.
        for (int i = 0; i < getNumOfVertex(); i++) {

            if (i == getStartVertex()) continue;

            int tourCost = memo[i][endState] + getGraph()[i][getStartVertex()];

            if (tourCost < getBestCost()) setBestCost(tourCost);

        }

        int lastIndex = getStartVertex();
        int state = endState;
        getTour().add(getStartVertex());

        // Reconstruct TSP path from memo table.
        for (int i = 1; i < getNumOfVertex(); i++) {

            int index = -1;

            for (int j = 0; j < getNumOfVertex(); j++) {

                if (j == getStartVertex() || notIn(j, state)) continue;

                if (index == -1) index = j;

                int prevDist = memo[index][state] + getGraph()[index][lastIndex];
                int newDist = memo[j][state] + getGraph()[j][lastIndex];

                if (newDist < prevDist) {
                    index = j;
                }
            }

            getTour().add(index);
            state ^= (1 << index);
            lastIndex = index;
        }

        getTour().add(getStartVertex());
        Collections.reverse(getTour());

        setRanSolver(true);

    }

    private boolean notIn(int elem, int subset) {
        return ((1 << elem) & subset) == 0;
    }

    // To find all the combinations of size r we need to recurse until we have
    // selected r elements (aka r = 0), otherwise if r != 0 then we still need to select
    // an element which is found after the position of our last selected element
    private static void combinations(int set, int at, int r, int n, List<Integer> subsets) {

        // Return early if there are more elements left to select than what is available.
        int elementsLeftToPick = n - at;
        if (elementsLeftToPick < r) return;

        // We selected 'r' elements so we found a valid subset!
        if (r == 0) {
            subsets.add(set);
        } else {
            for (int i = at; i < n; i++) {
                // Try including this element
                set |= 1 << i;

                combinations(set, i + 1, r - 1, n, subsets);

                // Backtrack and try the instance where we did not include this element
                set &= ~(1 << i);
            }
        }
    }

    // This method generates all bit sets of size n where r bits
    // are set to one. The result is returned as a list of integer masks.
    private List<Integer> combinations(int r, int n) {
        List<Integer> subsets = new ArrayList<>();
        combinations(0, 0, r, n, subsets);
        return subsets;
    }

    private int getMinDist(int subset, int next, int[][] memo) {

        int subsetWithoutNext = subset ^ (1 << next);

        int minDist = Integer.MAX_VALUE;

        for (int end = 0; end < getNumOfVertex(); end++) {

            if (end == getStartVertex() || end == next || notIn(end, subset)) continue;

            int newDistance = memo[end][subsetWithoutNext] + getGraph()[end][next];

            if (newDistance < minDist) minDist = newDistance;

        }
        return minDist;
    }

    @Override
    public String toString() {
        return getBestCost() + ";" + getTour().toString().replace(",", " -");
    }
}
