package com.dt183g.project.Views;

import java.awt.Color;
import javax.swing.JPanel;

enum NodeType {
    WALL,
    PATH,
    START,
    END,
    NODE,
    RESULT
}

class NodePanel extends JPanel {
    private int row;
    private int col;
    private NodeType type;

    public NodePanel(int row, int col) {
        this.row = row;
        this.col = col;
        this.type = NodeType.PATH;
        setColorByType();
    }

    public void setNodeType(NodeType type) {
        this.type = type;
        setColorByType();
    }

    public NodeType getNodeType() {
        return type;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    private void setColorByType() {
        switch (type) {
            case WALL:
                setBackground(Color.BLACK);
                break;
            case PATH:
                setBackground(Color.WHITE);
                break;
            case START:
                setBackground(Color.GREEN);
                break;
            case END:
                setBackground(Color.RED);
                break;
            case NODE:
                setBackground(Color.MAGENTA);
                break;
            case RESULT:
                setBackground(Color.CYAN);
                break;
        }
    }
}