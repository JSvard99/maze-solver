package com.dt183g.project.Controllers;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.SwingUtilities;

import com.dt183g.project.Models.AppModel;
import com.dt183g.project.Views.AppView;

public class AppController {
    private final AppView view = new AppView();
    private final AppModel model = new AppModel();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public AppController() {
        view.createGUI();
        view.addLoadButtonListener(e -> {
            processImageFile();
        });
        view.addSolveButtonListener(e -> {
            solveMaze();
        });
    }

    private void processImageFile() {
        view.toggleButtons(false);
        view.toggleSolveButton(false);

        File selectedFile = view.chooseImageFile();
        executor.submit(() -> {
            int[][] maze = model.processImageFile(selectedFile);

            SwingUtilities.invokeLater(() -> {
                view.drawMaze(maze);
                view.clearStartAndEndPoints();
            });
        });
    }

    private void solveMaze() {
        view.toggleButtons(false);

        int startX, startY, endX, endY;
        startX = view.getStartNodeX();
        startY = view.getStartNodeY();
        endX = view.getEndNodeX();
        endY = view.getEndNodeY();

        String selectedAlgorithm = view.getSelectedAlgorithm();

        executor.submit(() -> {
            int[][] maze = model.solveMaze(selectedAlgorithm, startX, startY, endX, endY);
            long executionTime = model.getExecutionTime();

            SwingUtilities.invokeLater(() -> {
                if (maze == null) {
                    view.displayErrorMessage("A solution was not found.");
                    view.toggleButtons(true);
                } else {
                    view.drawMaze(maze);
                    view.setExecutionTime(executionTime);
                }
            });
        });
    }
}