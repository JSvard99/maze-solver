package com.dt183g.project.Algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import com.dt183g.project.Models.Neighbour;
import com.dt183g.project.Models.Node;

public class AStar {

    /**
     * Function to get the shortest path in a graph if one exists, using the A* algorithm.
     *
     * @param graph the graph containing all nodes in a list.
     * @param source the starting point of the path.
     * @param destination the destination of the path.
     * @return the shortest path and cost.
     */
    // TIME COMPLEXITY: O(E + N * logN), N = Amount of nodes in graph, E = amount of edges in graph.
    // E since each edge can be considered while evaluating neighbours. 
    // N * logN since each node can be considered and added to the priority queue once, and insertion is logN.
    public static ShortestPath aStar(List<Node> graph, Node source, Node destination) {
        // Priority queue to store open nodes sorted by their estimated total cost from start to destination
        PriorityQueue<AStarNodeWrapper> openNodes = new PriorityQueue<>();
        // Map to store the previous node for each node in the shortest path
        Map<Node, Node> previous = new HashMap<>();
        // Map to store the actual cost from start to each node
        Map<Node, Integer> gScores = new HashMap<>();
        // Initialize current node with source node and its cost from start
        AStarNodeWrapper current = new AStarNodeWrapper(source, 0);
        current.setF(0);

        // Loop until destination is reached or no more nodes to explore
        while (current != null && current.getNode() != destination) {
            // Store the cost from start to current node
            gScores.put(current.getNode(), current.g);

            // Iterate over neighbors of current node
            for (Neighbour neighbour: current.getNode().getNeighbours()) {
                // Calculate heuristic value (estimated cost from current node to destination)
                int h = heuristic(neighbour.getNode(), destination);
                // Calculate actual cost from start to neighbor node via current node
                int g = current.getG() + neighbour.getCost();
                // Calculate total cost from start to neighbor node via current node
                int f = h + g;

                // Check if neighbor node has not been visited or if the new path is shorter
                if (!gScores.containsKey(neighbour.getNode()) || gScores.getOrDefault(neighbour.getNode(), Integer.MAX_VALUE) > g) {
                    // Update the actual cost from start to neighbor node
                    gScores.put(neighbour.getNode(), g);
                    // Create wrapper for neighbor node with its total cost
                    AStarNodeWrapper neighbourWrapper = new AStarNodeWrapper(neighbour.getNode(), g);
                    neighbourWrapper.setF(f);
                    // Add neighbor node to open nodes queue
                    openNodes.add(neighbourWrapper);
                    // Update previous node for neighbor node
                    previous.put(neighbour.getNode(), current.getNode());
                }
            }
            // Get next node with minimum total cost from start in open nodes queue
            current = openNodes.poll();
        }

        // If no path exists to destination, return null
        if (current == null) {
            return null;
        }

        // Reconstruct the shortest path from destination to source using previous nodes
        List<Node> path = new ArrayList<>();
        Node pathNode = destination;
        while (pathNode != null) {
            path.add(pathNode);
            pathNode = previous.get(pathNode);
        }

        // Return the shortest path and its cost
        return new ShortestPath(current.g, path);
    }

    private static int heuristic(Node current, Node destination) {
        return Math.abs(current.getX() - destination.getX()) + Math.abs(current.getY() - destination.getY());
    }

    private static class AStarNodeWrapper implements Comparable<AStarNodeWrapper>{
        private Node node;
        private int g;
        private int f;

        public AStarNodeWrapper(Node node, int g) {
            this.node = node;
            this.g = g;
        }

        public Node getNode() {
            return node;
        }

        public int getG() {
            return g;
        }

        public void setF(int f) {
            this.f = f;
        }

        @Override public int compareTo(AStarNodeWrapper o) {
            return Integer.compare(f, o.f);
        }
    }
}