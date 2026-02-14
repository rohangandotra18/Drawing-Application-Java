package drawingapp;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * Main frame for the Java 2D Drawing Application.
 * Provides a toolbar for selecting shape type, colors, stroke options,
 * and a canvas for drawing with mouse drag.
 */
public class DrawingApplicationFrame extends JFrame {

    // --- Toolbar widgets (top row) ---
    private final JComboBox<String> shapeSelector =
            new JComboBox<>(new String[]{"Line", "Oval", "Rectangle"});
    private final JButton color1Button = new JButton("1st Color\u2026");
    private final JButton color2Button = new JButton("2nd Color\u2026");
    private final JButton undoButton   = new JButton("Undo");
    private final JButton clearButton  = new JButton("Clear");

    // --- Toolbar widgets (bottom row) ---
    private final JCheckBox filledBox   = new JCheckBox("Filled");
    private final JCheckBox gradientBox = new JCheckBox("Use Gradient");
    private final JCheckBox dashedBox   = new JCheckBox("Dashed");
    private final JSpinner lineWidthSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
    private final JSpinner dashLengthSpinner = new JSpinner(new SpinnerNumberModel(15, 3, 100, 1));

    // --- Drawing state ---
    private final List<MyShapes> allShapes = new ArrayList<>();
    private Color color1 = Color.BLACK;
    private Color color2 = Color.BLACK;

    // --- Canvas and status ---
    private final DrawPanel canvas = new DrawPanel();
    private final JLabel statusLabel = new JLabel(" ");

    public DrawingApplicationFrame() {
        super("Java 2D Drawing Application");
        buildToolbar();
        buildLayout();
        attachListeners();
    }

    // ---------------------------------------------------------------
    //  UI Construction
    // ---------------------------------------------------------------

    private void buildToolbar() {
        filledBox.setBorderPainted(true);
        gradientBox.setBorderPainted(true);
        dashedBox.setBorderPainted(true);
    }

    private void buildLayout() {
        setLayout(new BorderLayout());

        // Top row
        JPanel topRow = new JPanel();
        topRow.add(new JLabel("Shape:"));
        topRow.add(shapeSelector);
        topRow.add(color1Button);
        topRow.add(color2Button);
        topRow.add(undoButton);
        topRow.add(clearButton);

        // Bottom row
        JPanel bottomRow = new JPanel();
        bottomRow.add(new JLabel("Options:"));
        bottomRow.add(filledBox);
        bottomRow.add(gradientBox);
        bottomRow.add(dashedBox);
        bottomRow.add(new JLabel("Line Width:"));
        bottomRow.add(lineWidthSpinner);
        bottomRow.add(new JLabel("Dash Length:"));
        bottomRow.add(dashLengthSpinner);

        // Combine rows
        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setBackground(new Color(200, 230, 240));
        topRow.setOpaque(false);
        bottomRow.setOpaque(false);
        toolbar.add(topRow, BorderLayout.NORTH);
        toolbar.add(bottomRow, BorderLayout.SOUTH);

        add(toolbar, BorderLayout.NORTH);
        add(canvas, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
    }

    // ---------------------------------------------------------------
    //  Event Listeners
    // ---------------------------------------------------------------

    private void attachListeners() {
        color1Button.addActionListener(e -> {
            Color chosen = JColorChooser.showDialog(this, "Select Color 1", color1);
            if (chosen != null) color1 = chosen;
        });

        color2Button.addActionListener(e -> {
            Color chosen = JColorChooser.showDialog(this, "Select Color 2", color2);
            if (chosen != null) color2 = chosen;
        });

        undoButton.addActionListener(e -> {
            if (!allShapes.isEmpty()) {
                allShapes.remove(allShapes.size() - 1);
                canvas.repaint();
            }
        });

        clearButton.addActionListener(e -> {
            allShapes.clear();
            canvas.repaint();
        });
    }

    // ---------------------------------------------------------------
    //  Shape Factory
    // ---------------------------------------------------------------

    private MyShapes buildShape(Point start, Point end) {
        // Build stroke
        int lineWidth = (int) lineWidthSpinner.getValue();
        BasicStroke stroke;
        if (dashedBox.isSelected()) {
            float dashLen = ((Number) dashLengthSpinner.getValue()).floatValue();
            stroke = new BasicStroke(lineWidth, BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND, 10, new float[]{dashLen}, 0);
        } else {
            stroke = new BasicStroke(lineWidth, BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND);
        }

        // Build paint
        Paint paint;
        if (gradientBox.isSelected()) {
            paint = new GradientPaint(0, 0, color1, 50, 50, color2, true);
        } else {
            paint = color1;
        }

        // Build shape
        String selected = (String) shapeSelector.getSelectedItem();
        if (selected == null) return null;

        switch (selected) {
            case "Line":      return new MyLine(start, end, paint, stroke);
            case "Oval":      return new MyOval(start, end, paint, stroke, filledBox.isSelected());
            case "Rectangle": return new MyRectangle(start, end, paint, stroke, filledBox.isSelected());
            default:          return null;
        }
    }

    // ---------------------------------------------------------------
    //  Draw Panel (inner class)
    // ---------------------------------------------------------------

    private class DrawPanel extends JPanel {

        private Point dragStart;
        private MyShapes previewShape;  // single preview, not an accumulating list

        DrawPanel() {
            setBackground(Color.WHITE);
            MouseHandler handler = new MouseHandler();
            addMouseListener(handler);
            addMouseMotionListener(handler);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw committed shapes
            for (MyShapes shape : allShapes) {
                shape.draw(g2d);
            }

            // Draw live preview
            if (previewShape != null) {
                previewShape.draw(g2d);
            }
        }

        private class MouseHandler extends MouseAdapter {

            @Override
            public void mousePressed(MouseEvent e) {
                dragStart = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                MyShapes shape = buildShape(dragStart, e.getPoint());
                if (shape != null) {
                    allShapes.add(shape);
                }
                previewShape = null;
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                statusLabel.setText(String.format("(%d, %d)", e.getX(), e.getY()));
                previewShape = buildShape(dragStart, e.getPoint());
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                statusLabel.setText(String.format("(%d, %d)", e.getX(), e.getY()));
            }
        }
    }
}
