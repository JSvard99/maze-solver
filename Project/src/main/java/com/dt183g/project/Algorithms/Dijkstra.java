package com.dt183g.project.Algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import com.dt183g.project.Models.Neighbour;
import com.dt183g.project.Models.Node;

public class Dijkstra {
    /**
     * Dijkstra function using a simple array.
     *
     * @param graph the graph containing all nodes in an array.
     * @param source the start node of the path.
     * @param destination the goal node of the path.
     * @return the path and cost.
     */
    // TIME COMPLEXITY: O(E + N^2), N = Nodes, E = Edge.
    // E since each edge can be evaluated once,
    // N^2 since for each node we need to find the next lowest cost, which is N in an array.
    public static ShortestPath simpleArrayDijkstra(List<Node> graph, Node source, Node destination) {
        // Initialize distances from source to all nodes as maximum integer value
        Map<Node, Integer> distances = new HashMap<>();
        for (Node node : graph) {
            if (node == source) {
                distances.put(node, 0);
            } else {
                distances.put(node, Integer.MAX_VALUE);
            }
        }

        // Initialize map to store previous node for each node in the shortest path
        Map<Node, Node> previous = new HashMap<>();
        // List to track visited nodes
        List<Node> visited = new ArrayList<>();

        // Initialize current node as source
        Node currentNode = source;
        // Initialize previous node as null
        Node previousNode = null;

        // Loop until previous node is destination or there are no more unvisited nodes
        while (previousNode != destination && currentNode != null) {
            // Update distances and previous nodes for unvisited neighbors of current node
            for (Neighbour neighbour : currentNode.getNeighbours()) {
                if (!visited.contains(neighbour.getNode())) {
                    int neighbourDistanceFromSource = neighbour.getCost() + distances.get(currentNode);
                    if (distances.get(neighbour.getNode()) > neighbourDistanceFromSource) {
                        distances.put(neighbour.getNode(), neighbourDistanceFromSource);
                        previous.put(neighbour.getNode(), currentNode);
                    }
                }
            }
            // Mark current node as visited
            visited.add(currentNode);

            // Find the unvisited node with the minimum distance from source
            int minDistance = Integer.MAX_VALUE;
            Node nextNode = null;
            for (Node node : graph) {
                if (!visited.contains(node) && distances.get(node) < minDistance) {
                    minDistance = distances.get(node);
                    nextNode = node;
                }
            }
            // If no next unvisited node, break the loop
            if (nextNode == null)
                return null;
            // Update previous node and move to next node
            previousNode = currentNode;
            currentNode = nextNode;
        }

        // Reconstruct the shortest path from destination to source using previous nodes
        LinkedList<Node> path = new LinkedList<>();
        Node pathNode = destination;
        while (pathNode != null) {
            path.addFirst(pathNode);
            pathNode = previous.get(pathNode);
        }

        // Return the shortest path and its cost
        return new ShortestPath(distances.get(destination), path);
    }

    /**
     * Dijkstra function using a PriorityQueue data structure.
     *
     * @param graph the graph containing all nodes in an array.
     * @param source the start node of the path.
     * @param destination the goal node of the path.
     * @return the path and cost.
     */
    // TIME COMPLEXITY: O(E + N * logN), N = Nodes, E = Edge.
    // E since each edge can be evaluated once,
    // N * logN since for each node we need to find the next lowest cost, which is log N in a priority queue.
    public static ShortestPath priorityQueueDijkstra(List<Node> graph, Node source, Node destination) {
        // Initialize distances from source to all nodes as maximum integer value
        Map<Node, Integer> distances = new HashMap<>();
        for (Node node : graph) {
            if (node == source) {
                distances.put(node, 0);
            } else {
                distances.put(node, Integer.MAX_VALUE);
            }
        }

        // Initialize map to store previous node for each node in the shortest path
        Map<Node, Node> previous = new HashMap<>();
        // Priority queue to store nodes based on their distance from source
        PriorityQueue<DijkstraNodeWrapper> priorityQueue = new PriorityQueue<>();
        // List to track visited nodes
        List<Node> visited = new ArrayList<>();

        // Initialize current node as source
        Node currentNode = source;
        // Initialize previous node as null
        Node previousNode = null;

        // Loop until previous node is destination
        while (previousNode != destination && currentNode != null) {
            // Update distances and previous nodes for unvisited neighbors of current node
            for (Neighbour neighbour : currentNode.getNeighbours()) {
                if (!visited.contains(neighbour.getNode())) {
                    int neighbourDistanceFromSource = neighbour.getCost() + distances.get(currentNode);
                    if (distances.get(neighbour.getNode()) > neighbourDistanceFromSource) {
                        distances.put(neighbour.getNode(), neighbourDistanceFromSource);
                        previous.put(neighbour.getNode(), currentNode);
                        priorityQueue.add(new DijkstraNodeWrapper(neighbour.getNode(), neighbourDistanceFromSource));
                    }
                }
            }
            // Mark current node as visited.
            visited.add(currentNode);
            // Update previous node and move to next node with minimum distance from source
            previousNode = currentNode;
            currentNode = priorityQueue.poll().getNode();
        }

        // If no path exists to destination, return null
        if (currentNode == null) {
            return null;
        }

        // Reconstruct the shortest path from destination to source using previous nodes
        LinkedList<Node> path = new LinkedList<>();
        Node pathNode = destination;
        while (pathNode != null) {
            path.addFirst(pathNode);
            pathNode = previous.get(pathNode);
        }

        // Return the shortest path and its cost
        return new ShortestPath(distances.get(destination), path);
    }

    private static class DijkstraNodeWrapper implements Comparable<DijkstraNodeWrapper>{
        private Node node;
        private int distanceFromSource;

        public DijkstraNodeWrapper(Node node, int distanceFromSource) {
            this.node = node;
            this.distanceFromSource = distanceFromSource;
        }

        public Node getNode() {
            return node;
        }

        @Override public int compareTo(DijkstraNodeWrapper o) {
            return Integer.compare(distanceFromSource, o.distanceFromSource);
        }
    }
}