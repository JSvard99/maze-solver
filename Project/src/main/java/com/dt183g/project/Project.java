package com.dt183g.project;

import com.dt183g.project.Controllers.AppController;
import javax.swing.SwingUtilities;

/**
 * The main starting point for the project.
 * @author Morgan Teräs
 */
public class Project {
    private Project() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Starts the program.
     * @param args command arguments.
     */
    public static void main( String[] args ) {
        SwingUtilities.invokeLater(AppController::new);
    }
}