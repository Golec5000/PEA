package org.helpStructures;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Node {

    private Node parent;
    private int idNode;
    private int numberOfConnections;
    private int level;
    private int pathCost;
    List<Integer> visitedVertex;

}
