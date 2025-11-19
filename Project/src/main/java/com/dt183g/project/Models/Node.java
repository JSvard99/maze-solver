package com.dt183g.project.Models;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private static int created = 0;
    private int id;
    private int x;
    private int y;
    private List<Neighbour> neighbours = new ArrayList<>();

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        id = created;
        created++;
    }

    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void addNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }

    public Neighbour removeNeighbour(Node neighbourNode) {
        for (Neighbour neighbour: neighbours) {
            if (neighbour.getNode() == neighbourNode) {
                neighbours.remove(neighbour);
                return neighbour;
            }
        }
        return null;
    }

    @Override public String toString() {
        return "Node " + id;
    }
}