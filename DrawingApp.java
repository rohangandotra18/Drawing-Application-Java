package drawingapp;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Entry point for the Java 2D Drawing Application.
 */
public class DrawingApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DrawingApplicationFrame frame = new DrawingApplicationFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(750, 550);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
