package drawingapp;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;

/**
 * Abstract base class for all drawable shapes.
 * Stores start/end points, paint (color or gradient), and stroke.
 */
public abstract class MyShapes {

    private Point startPoint;
    private Point endPoint;
    private Paint paint;
    private Stroke stroke;

    protected MyShapes() {
        this.startPoint = new Point();
        this.endPoint = new Point();
        this.stroke = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        this.paint = Color.BLACK;
    }

    protected MyShapes(Point startPoint, Point endPoint, Paint paint, Stroke stroke) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.paint = paint;
        this.stroke = stroke;
    }

    /** Renders this shape onto the given Graphics2D context. */
    public abstract void draw(Graphics2D g2d);

    // --- Getters and Setters ---

    public Point  getStartPoint() { return startPoint; }
    public Point  getEndPoint()   { return endPoint; }
    public Paint  getPaint()      { return paint; }
    public Stroke getStroke()     { return stroke; }

    public void setStartPoint(Point startPoint) { this.startPoint = startPoint; }
    public void setEndPoint(Point endPoint)     { this.endPoint = endPoint; }
    public void setPaint(Paint paint)           { this.paint = paint; }
    public void setStroke(Stroke stroke)        { this.stroke = stroke; }
}
