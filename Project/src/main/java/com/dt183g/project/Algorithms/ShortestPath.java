package com.dt183g.project.Algorithms;

import java.util.List;

import com.dt183g.project.Models.Node;

public class ShortestPath {
    private int cost;
    private List<Node> path;

    public ShortestPath(int cost, List<Node> path) {
        this.cost = cost;
        this.path = path;
    }

    public int getCost() {
        return cost;
    }

    public List<Node> getPath() {
        return path;
    }
}