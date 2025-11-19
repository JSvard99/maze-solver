package com.dt183g.project.Models;

import java.util.ArrayList;
import java.util.List;

public class Neighbour {
    private Node neighbour;
    private int cost;
    private List<Node> nodesBetween = new ArrayList<>();

    public Neighbour(Node neighbour, int cost, List<Node> nodesBetween) {
        this.neighbour = neighbour;
        this.cost = cost;
        if (nodesBetween != null) {
            this.nodesBetween = nodesBetween;
        }
    }

    public Node getNode() {
        return neighbour;
    }

    public int getCost() {
        return cost;
    }

    public List<Node> getNodesBetween() {
        return nodesBetween;
    }
}