package drawingapp;

import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;

/**
 * Abstract base for shapes that have a bounding box (ovals, rectangles).
 * Provides helper methods to compute the top-left corner, width, and height
 * regardless of which direction the user dragged.
 */
public abstract class MyBoundedShapes extends MyShapes {

    private boolean filled;

    protected MyBoundedShapes(Point startPt, Point endPt, Paint paint, Stroke stroke, boolean filled) {
        super(startPt, endPt, paint, stroke);
        this.filled = filled;
    }

    public boolean isFilled()                { return filled; }
    public void    setFilled(boolean filled)  { this.filled = filled; }

    /** X-coordinate of the bounding box's top-left corner. */
    public int getTopLeftX() {
        return Math.min((int) getStartPoint().getX(), (int) getEndPoint().getX());
    }

    /** Y-coordinate of the bounding box's top-left corner. */
    public int getTopLeftY() {
        return Math.min((int) getStartPoint().getY(), (int) getEndPoint().getY());
    }

    /** Width of the bounding box. */
    public int getWidth() {
        return Math.abs((int) getStartPoint().getX() - (int) getEndPoint().getX());
    }

    /** Height of the bounding box. */
    public int getHeight() {
        return Math.abs((int) getStartPoint().getY() - (int) getEndPoint().getY());
    }
}
