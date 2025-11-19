package com.dt183g.project.Views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AppView {
    private JFrame frame;
    private JPanel mazePanel;

    private JButton loadButton;
    private JButton selectStartButton;
    private JButton selectEndButton;
    private JButton solveButton;

    private JButton simpleArrayButton;
    private JButton priorityQueueButton;
    private JButton aStarButton;
    private JButton selectedButton;

    private JLabel executionTimeLabel;
    private NodePanel selectedStartNodePanel;
    private NodePanel selectedEndNodePanel;
    private boolean toggleSelectStart = false;
    private boolean toggleSelectEnd = false;

    public void createGUI() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);

        // Initialize components
        JPanel mainPanel = new JPanel();
        JPanel settingsPanel = new JPanel();
        JPanel executionTimePanel = new JPanel();
        mazePanel = new JPanel();
        JLabel settingsLabel = new JLabel("SETTINGS");
        loadButton = new JButton("LOAD IMAGE");
        selectStartButton = new JButton("SELECT START POINT");
        selectEndButton = new JButton("SELECT END POINT");
        solveButton = new JButton("SOLVE MAZE");
        JLabel algorithmLabel = new JLabel("ALGORITHM");
        aStarButton = new JButton("A-STAR");
        simpleArrayButton = new JButton("DIJKSTRA ARRAY");
        priorityQueueButton = new JButton("DIJKSTRA PRIORITY QUEUE");
        JLabel durationLabel = new JLabel("EXECUTION TIME");
        executionTimeLabel = new JLabel("000000000000 ns", SwingConstants.CENTER);

        // Set components layouts
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        FlowLayout settingsFlowLayout = new FlowLayout();
        settingsFlowLayout.setHgap(10);
        settingsFlowLayout.setVgap(12);
        settingsPanel.setLayout(settingsFlowLayout);
        executionTimePanel.setLayout(new BorderLayout());

        // Set components settings
        mazePanel.setBorder(null);
        loadButton.setFocusable(false);
        loadButton.setBorder(null);
        selectStartButton.setFocusable(false);
        selectStartButton.setBorder(null);
        selectEndButton.setFocusable(false);
        selectEndButton.setBorder(null);
        solveButton.setFocusable(false);
        solveButton.setBorder(null);
        aStarButton.setBorder(null);
        aStarButton.setFocusable(false);
        simpleArrayButton.setBorder(null);
        simpleArrayButton.setFocusable(false);
        priorityQueueButton.setBorder(null);
        priorityQueueButton.setFocusable(false);
        executionTimeLabel.setHorizontalAlignment(JLabel.CENTER);

        // Set components size
        mainPanel.setPreferredSize(new Dimension(1440, 960));
        settingsPanel.setPreferredSize(new Dimension(460, 960));
        mazePanel.setPreferredSize(new Dimension(960, 960));
        executionTimePanel.setPreferredSize(new Dimension(420, 80));

        Dimension buttonDimension = new Dimension(420, 60);
        loadButton.setPreferredSize(buttonDimension);
        selectStartButton.setPreferredSize(buttonDimension);
        selectEndButton.setPreferredSize(buttonDimension);
        solveButton.setPreferredSize(buttonDimension);
        aStarButton.setPreferredSize(buttonDimension);
        simpleArrayButton.setPreferredSize(buttonDimension);
        priorityQueueButton.setPreferredSize(buttonDimension);

        // Set components font
        Font labelFont = new Font("Roboto Mono", Font.BOLD, 32);
        Font buttonFont = new Font("Roboto Mono", Font.BOLD, 24);
        settingsLabel.setFont(labelFont);
        loadButton.setFont(buttonFont);
        selectStartButton.setFont(buttonFont);
        selectEndButton.setFont(buttonFont);
        solveButton.setFont(buttonFont);
        algorithmLabel.setFont(labelFont);
        aStarButton.setFont(buttonFont);
        simpleArrayButton.setFont(buttonFont);
        priorityQueueButton.setFont(buttonFont);
        durationLabel.setFont(labelFont);
        executionTimeLabel.setFont(labelFont);

        // Set components color
        frame.getContentPane().setBackground(Color.BLACK);
        mainPanel.setBackground(Color.ORANGE);
        settingsPanel.setBackground(Color.GRAY);
        mazePanel.setBackground(Color.DARK_GRAY);
        loadButton.setBackground(Color.WHITE);
        selectStartButton.setBackground(Color.WHITE);
        selectEndButton.setBackground(Color.WHITE);
        solveButton.setBackground(Color.WHITE);
        aStarButton.setBackground(Color.WHITE);
        simpleArrayButton.setBackground(Color.WHITE);
        priorityQueueButton.setBackground(Color.WHITE);
        executionTimePanel.setBackground(Color.BLACK);
        executionTimeLabel.setForeground(Color.GREEN);

        // Add component listeners
        aStarButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                selectButton(aStarButton);
            }
        });
        simpleArrayButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                selectButton(simpleArrayButton);
            }
        });
        priorityQueueButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                selectButton(priorityQueueButton);
            }
        });
        selectStartButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                selectStartButton.setBackground(new Color(184, 207, 229));
                toggleSelectStart = true;
                toggleButtons(false);
            }
        });
        selectEndButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                selectEndButton.setBackground(new Color(184, 207, 229));
                toggleSelectEnd = true;
                toggleButtons(false);
            }
        });

        // Add components
        executionTimePanel.add(executionTimeLabel, BorderLayout.CENTER);
        settingsPanel.add(settingsLabel);
        settingsPanel.add(Box.createVerticalStrut(60));
        settingsPanel.add(loadButton);
        settingsPanel.add(selectStartButton);
        settingsPanel.add(selectEndButton);
        settingsPanel.add(solveButton);
        settingsPanel.add(algorithmLabel);
        settingsPanel.add(Box.createVerticalStrut(60));
        settingsPanel.add(aStarButton);
        settingsPanel.add(simpleArrayButton);
        settingsPanel.add(priorityQueueButton);
        settingsPanel.add(durationLabel);
        settingsPanel.add(Box.createVerticalStrut(60));
        settingsPanel.add(executionTimePanel);
        mainPanel.add(mazePanel);
        mainPanel.add(settingsPanel);
        frame.add(mainPanel);
        frame.pack();
        selectButton(aStarButton);
        toggleButtons(false);
        toggleSolveButton(false);
        loadButton.setEnabled(true);
    }

    public void addLoadButtonListener(ActionListener actionListener) {
        loadButton.addActionListener(actionListener);
    }

    public void addSelectStartButtonListener(ActionListener actionListener) {
        selectStartButton.addActionListener(actionListener);
    }

    public void addSelectEndButtonListener(ActionListener actionListener) {
        selectEndButton.addActionListener(actionListener);
    }

    public void addSolveButtonListener(ActionListener actionListener) {
        solveButton.addActionListener(actionListener);
    }

    public void toggleButtons(boolean setting) {
        loadButton.setEnabled(setting);
        selectStartButton.setEnabled(setting);
        selectEndButton.setEnabled(setting);
        simpleArrayButton.setEnabled(setting);
        priorityQueueButton.setEnabled(setting);
        aStarButton.setEnabled(setting);
        selectedButton.setEnabled(setting);
    }

    public void toggleSolveButton(boolean setting) {
        solveButton.setEnabled(setting);
    }

    public File chooseImageFile() {
        JFileChooser fileChooser = new JFileChooser();
        // Get the current working directory
        File workingDirectory = new File(System.getProperty("user.dir"));
        // Navigate to the desired folder within the working directory
        File resourcesFolder = new File(workingDirectory, "Project/src/main/java/com/dt183g/Resources");
        fileChooser.setCurrentDirectory(resourcesFolder);
        fileChooser.setFileFilter(new FileNameExtensionFilter("JPEG files", "jpg", "jpeg"));
        int result = fileChooser.showOpenDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (!isCorrectFileType(selectedFile)) {
                displayErrorMessage("Please select a JPEG file.");
                return chooseImageFile();
            }
            return selectedFile;
        } else if (result == JFileChooser.CANCEL_OPTION) {
            displayErrorMessage("Please select a JPEG file.");
            return chooseImageFile();
        }

        return null;
    }

    private boolean isCorrectFileType(File file) {
        String fileName = file.getName();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        return extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg");
    }

    public void displayErrorMessage(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void setExecutionTime(long executionTime) {
        String paddedNanoTimeStr = String.format("%012d", executionTime);
        executionTimeLabel.setText(paddedNanoTimeStr + " ns");
    }

    public void drawMaze(int[][] maze) {
        int rows = maze.length;
        int cols = maze[0].length;

        mazePanel.removeAll();
        mazePanel.setLayout(new GridLayout(rows, cols));

        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                NodePanel nodePanel = new NodePanel(x, y);
                nodePanel.addMouseListener(new MouseAdapter() {
                    @Override public void mouseClicked(MouseEvent e) {
                        selectNode(nodePanel);
                    }
                });
                switch (maze[x][y]) {
                    case 1:
                        nodePanel.setNodeType(NodeType.PATH);
                        break;
                    case 2:
                        nodePanel.setNodeType(NodeType.START);
                        break;
                    case 3:
                        nodePanel.setNodeType(NodeType.END);
                        break;
                    case 4:
                        nodePanel.setNodeType(NodeType.NODE);
                        break;
                    case 5:
                        nodePanel.setNodeType(NodeType.RESULT);
                        break;
                    default:
                        nodePanel.setNodeType(NodeType.WALL);
                }
                mazePanel.add(nodePanel);
            }
        }
        mazePanel.revalidate();
        mazePanel.repaint();
        toggleButtons(true);
    }

    public String getSelectedAlgorithm() {
        if (selectedButton == simpleArrayButton) {
            return "Array";
        } else if (selectedButton == priorityQueueButton) {
            return "PriorityQueue";
        } else {
            return "AStar";
        }
    }

    private void selectButton(JButton button) {
        if (selectedButton != null) {
            selectedButton.setBackground(Color.WHITE);
        }
        selectedButton = button;
        selectedButton.setBackground(new Color(184, 207, 229));
    }

    private void selectNode(NodePanel nodePanel) {
        NodeType targetType = toggleSelectStart ? NodeType.START : (toggleSelectEnd ? NodeType.END : null);

        if (targetType != null && nodePanel.getNodeType() == NodeType.PATH) {
            if (toggleSelectStart) {
                if (selectedStartNodePanel != null) {
                    selectedStartNodePanel.setNodeType(NodeType.PATH);
                }
                selectedStartNodePanel = nodePanel;
            } else if (toggleSelectEnd) {
                if (selectedEndNodePanel != null) {
                    selectedEndNodePanel.setNodeType(NodeType.PATH);
                }
                selectedEndNodePanel = nodePanel;
            }
            nodePanel.setNodeType(targetType);
            toggleSelectStart = toggleSelectEnd = false;
            selectStartButton.setBackground(Color.WHITE);
            selectEndButton.setBackground(Color.WHITE);
            toggleButtons(true);

            if (selectedStartNodePanel != null && selectedEndNodePanel != null) {
                toggleSolveButton(true);
            }
        }
    }

    public void clearStartAndEndPoints() {
        selectedEndNodePanel = null;
        selectedStartNodePanel = null;
    }

    public int getStartNodeX() {
        return selectedStartNodePanel.getRow();
    }

    public int getStartNodeY() {
        return selectedStartNodePanel.getCol();
    }

    public int getEndNodeX() {
        return selectedEndNodePanel.getRow();
    }

    public int getEndNodeY() {
        return selectedEndNodePanel.getCol();
    }
}