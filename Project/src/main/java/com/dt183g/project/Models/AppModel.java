package com.dt183g.project.Models;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.dt183g.project.Algorithms.AStar;
import com.dt183g.project.Algorithms.Dijkstra;
import com.dt183g.project.Algorithms.ShortestPath;

public class AppModel {
    private final int threshold = 50;
    private final int WALL = 0;
    private final int PATH = 1;
    private final int START = 2;
    private final int END = 3;
    private final int NODE = 4;
    private final int RESULT = 5;
    private int[][] maze;
    private long executionTime;

    public int[][] processImageFile(File selectedFile) {
        try {
            // Read the image file
            BufferedImage originalImage = ImageIO.read(selectedFile);

            // Convert to binary image
            BufferedImage binaryImage = thresholdImage(originalImage, threshold);

            // Find the bounding box of black pixels
            Rectangle boundingBox = findBoundingBox(binaryImage);

            // Create a cropped version of the image
            BufferedImage croppedImage = binaryImage.getSubimage(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);

            File outputFile = new File(System.getProperty("user.home") + "/Downloads/cropped_maze.jpg");
            ImageIO.write(croppedImage, "jpg", outputFile);

            return maze = parseMaze(croppedImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BufferedImage thresholdImage(BufferedImage image, int threshold) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        result.getGraphics().drawImage(image, 0, 0, null);
        WritableRaster raster = result.getRaster();
        int[] pixels = new int[image.getWidth()];
        for (int y = 0; y < image.getHeight(); y++) {
            raster.getPixels(0, y, image.getWidth(), 1, pixels);
            for (int i = 0; i < pixels.length; i++) {
                if (pixels[i] < threshold) pixels[i] = 0;
                else pixels[i] = 255;
            }
            raster.setPixels(0, y, image.getWidth(), 1, pixels);
        }
        return result;
    }

    private int[][] parseMaze(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int pathSize = determineSize(image, true);
        int wallSize = determineSize(image, false);

        int rows = ((width - wallSize) / (pathSize + wallSize) * 2) + 1;
        int cols = ((height - wallSize) / (pathSize + wallSize) * 2) + 1;

        int[][] maze = new int[cols][rows];

        boolean isNodeH = false;
        boolean isNodeV = false;
        int x = 0;
        int y = 0;
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                maze[col][row] = isBlack(image.getRGB(x, y)) ? WALL : PATH;
                x += isNodeH ? pathSize : wallSize;
                isNodeH = !isNodeH;
            }
            x = 0;
            y += isNodeV ? pathSize : wallSize;
            isNodeV = !isNodeV;
        }

        // Add padding to center the maze
        int maxSize = Math.max(cols, rows);
        int offsetX = (maxSize - cols) / 2;
        int offsetY = (maxSize - rows) / 2;
        int[][] paddedMaze = new int[maxSize][maxSize];
        for (int i = 0; i < maxSize; i++) {
            for (int j = 0; j < maxSize; j++) {
                if (i >= offsetX && i < offsetX + cols && j >= offsetY && j < offsetY + rows) {
                    paddedMaze[i][j] = maze[i - offsetX][j - offsetY];
                } else {
                    paddedMaze[i][j] = WALL;
                }
            }
        }

        return paddedMaze;
    }

    private Rectangle findBoundingBox(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int top = height;
        int bottom = 0;
        int left = width;
        int right = 0;
        // Iterate over each pixel to find the bounding box
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                if (isBlack(pixel)) {
                    // Update the bounding box
                    if (x < left) left = x;
                    if (x > right) right = x;
                    if (y < top) top = y;
                    if (y > bottom) bottom = y;
                }
            }
        }
        // Create and return the bounding box rectangle
        return new Rectangle(left, top, right - left + 1, bottom - top + 1);
    }

    private boolean isBlack(int pixel) {
        int red = (pixel >> 16) & 0xff, green = (pixel >> 8) & 0xff, blue = pixel & 0xff;
        return red < threshold && green < threshold && blue < threshold;
    }

    private int determineSize(BufferedImage image, boolean isWhite) {
        int width = image.getWidth();
        int height = image.getHeight();
        int currentWidth = 0;

        // Map to store width occurrences
        Map<Integer, Integer> widthCounts = new HashMap<>();

        // Horizontal check
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (isWhite && !isBlack(image.getRGB(x, y))) {
                    currentWidth++;
                } else if (!isWhite && isBlack(image.getRGB(x, y))) {
                    currentWidth++;
                } else {
                    // Check pixel width at the end of sequence
                    if (currentWidth > 0) {
                        // Increment occurrence count for currentWidth
                        widthCounts.put(currentWidth, widthCounts.getOrDefault(currentWidth, 0) + 1);
                    }
                    currentWidth = 0;
                }
            }
            // Check pixel width at the end of each row
            if (currentWidth > 0) {
                // Increment occurrence count for currentWidth
                widthCounts.put(currentWidth, widthCounts.getOrDefault(currentWidth, 0) + 1);
            }
            currentWidth = 0;
        }

        // Vertical check
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (isWhite && !isBlack(image.getRGB(x, y))) {
                    currentWidth++;
                } else if (!isWhite && isBlack(image.getRGB(x, y))) {
                    currentWidth++;
                } else {
                    // Check pixel width at the end of sequence
                    if (currentWidth > 0) {
                        // Increment occurrence count for currentWidth
                        widthCounts.put(currentWidth, widthCounts.getOrDefault(currentWidth, 0) + 1);
                    }
                    currentWidth = 0;
                }
            }
            // Check pixel width at the end of each column
            if (currentWidth > 0) {
                // Increment occurrence count for currentWidth
                widthCounts.put(currentWidth, widthCounts.getOrDefault(currentWidth, 0) + 1);
            }
            currentWidth = 0;
        }

        // Find the most common width
        int mostCommonWidth = 0;
        int maxCount = 0;
        for (Map.Entry<Integer, Integer> entry : widthCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                mostCommonWidth = entry.getKey();
                maxCount = entry.getValue();
            }
        }

        return mostCommonWidth;
    }

    public int[][] solveMaze(String algorithm, int startX, int startY, int endX, int endY) {
        int[][] mazeCopy = new int[maze.length][];
        for (int i = 0; i < maze.length; i++) {
            mazeCopy[i] = Arrays.copyOf(maze[i], maze[i].length);
        }

        mazeCopy[startX][startY] = START;
        mazeCopy[endX][endY] = END;

        MazeGraph mazeGraph = new MazeGraph(mazeCopy);
        List<Node> nodes = mazeGraph.getNodes();
        Node source = mazeGraph.getSource();
        Node destination = mazeGraph.getDestination();

        long startTime, endTime;

        // Start measuring time
        startTime = System.nanoTime();

        List<Node> pathNodes = null;
        ShortestPath shortestPath = null;

        switch (algorithm) {
            case "Array":
                shortestPath = Dijkstra.simpleArrayDijkstra(nodes, source, destination);
                break;
            case "PriorityQueue":
                shortestPath = Dijkstra.priorityQueueDijkstra(nodes, source, destination);
                break;
            default:
                shortestPath = AStar.aStar(nodes, source, destination);
                break;
        }

        if (shortestPath == null)
            return null;
        else {
            pathNodes = shortestPath.getPath();
        }

        // Stop measuring time
        endTime = System.nanoTime();

        // Calculate execution time
        executionTime = endTime - startTime;

        // Draw connected nodes
        for (int i = 0; i < pathNodes.size() - 1; i++) {
            Node node = pathNodes.get(i);
            Node next = pathNodes.get(i + 1);
            for (Neighbour neighbour : node.getNeighbours()) {
                if (neighbour.getNode() == next) {
                    for (Node neighbourBetween : neighbour.getNodesBetween()) {
                        mazeCopy[neighbourBetween.getY()][neighbourBetween.getX()] = RESULT;
                    }
                }
            }
        }

        // Draw vertex nodes
        for (int i = 1; i < pathNodes.size() - 1; i++) {
            Node node = pathNodes.get(i);
            mazeCopy[node.getY()][node.getX()] = NODE;
        }

        return mazeCopy;
    }

    public long getExecutionTime() {
        return executionTime;
    }
}