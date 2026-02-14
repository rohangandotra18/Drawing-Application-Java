package drawingapp;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;

/**
 * An oval (ellipse) bounded by the rectangle defined by two drag points.
 */
public class MyOval extends MyBoundedShapes {

    public MyOval(Point startPt, Point endPt, Paint paint, Stroke stroke, boolean filled) {
        super(startPt, endPt, paint, stroke, filled);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setPaint(getPaint());
        g2d.setStroke(getStroke());
        Ellipse2D.Double ellipse = new Ellipse2D.Double(
                getTopLeftX(), getTopLeftY(), getWidth(), getHeight());

        if (isFilled()) {
            g2d.fill(ellipse);
        } else {
            g2d.draw(ellipse);
        }
    }
}
