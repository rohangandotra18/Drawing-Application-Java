package drawingapp;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Line2D;

/**
 * A straight line segment between two points.
 */
public class MyLine extends MyShapes {

    public MyLine(Point startPt, Point endPt, Paint paint, Stroke stroke) {
        super(startPt, endPt, paint, stroke);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setPaint(getPaint());
        g2d.setStroke(getStroke());
        g2d.draw(new Line2D.Double(
                getStartPoint().getX(), getStartPoint().getY(),
                getEndPoint().getX(), getEndPoint().getY()));
    }
}
