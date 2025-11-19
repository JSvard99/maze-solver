package com.dt183g.project.Models;

import java.util.ArrayList;
import java.util.List;

public class MazeGraph {
    List<Node> nodes;
    Node source;
    Node destination;

    public MazeGraph(int[][] maze) {
        List<Node> graph = createGraph(maze);
        this.nodes = trimGraph(graph, source, destination);
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public Node getSource() {
        return source;
    }

    public Node getDestination() {
        return destination;
    }

    private List<Node> createGraph(int[][] maze) {
        List<Node> graph = new ArrayList<>();
        Node[][] nodes = new Node[maze.length][maze[0].length];

        // Iterate through the maze to create nodes and find the source and destination nodes
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] < 1)
                    continue; // Skip walls
                Node node = new Node(j, i); // Create a new node
                if (maze[i][j] == 2)
                    this.source = node; // If it's the source node, assign it
                else if (maze[i][j] == 3)
                    this.destination = node; // If it's the destination node, assign it
                nodes[i][j] = node; // Add the node to the 2D array
                graph.add(node); // Add the node to the graph
            }
        }

        // Connect nodes based on adjacency
        for (Node node : graph) {
            int i = node.getY();
            int j = node.getX();
            int[][] directions = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}}; // Possible movement directions
            for (int[] direction : directions) {
                int y = i + direction[0];
                int x = j + direction[1];
                // Check if adjacent node is within bounds and not a wall
                if (y >= 0 && y < nodes.length && x >= 0 && x < nodes[y].length && nodes[y][x] != null) {
                    node.addNeighbour(new Neighbour(nodes[y][x], 1, null)); // Add adjacent node as neighbour
                }
            }
        }
        return graph;
    }

    private List<Node> trimGraph(List<Node> nodes, Node source, Node destination) {
        List<Node> graph = new ArrayList<>();

        // Iterate through nodes to trim the graph
        for (Node node : nodes) {
            // Keep source, destination, and nodes with more than 2 neighbours
            if (node == source || node == destination || node.getNeighbours().size() != 2) {
                graph.add(node); // Add node to the trimmed graph
                continue;
            }

            // If node has exactly 2 neighbours, merge them into one
            Node firstNeighbour = node.getNeighbours().get(0).getNode();
            Node secondNeighbour = node.getNeighbours().get(1).getNode();

            // Remove node from its neighbours' neighbour lists
            Neighbour first = firstNeighbour.removeNeighbour(node);
            Neighbour second = secondNeighbour.removeNeighbour(node);

            // Calculate the new cost after merging the two neighbours
            int cost = first.getCost() + second.getCost();

            // Combine the nodes between the two neighbours
            List<Node> nodesBetween = new ArrayList<>(first.getNodesBetween());
            nodesBetween.addAll(second.getNodesBetween());
            nodesBetween.add(node);

            // Add the merged neighbour back to each neighbour's neighbour list
            firstNeighbour.addNeighbour(new Neighbour(secondNeighbour, cost, nodesBetween));
            secondNeighbour.addNeighbour(new Neighbour(firstNeighbour, cost, nodesBetween));
        }

        return graph; // Return the trimmed graph
    }
}