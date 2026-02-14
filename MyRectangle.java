package drawingapp;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;

/**
 * A rectangle bounded by the area defined by two drag points.
 */
public class MyRectangle extends MyBoundedShapes {

    public MyRectangle(Point startPt, Point endPt, Paint paint, Stroke stroke, boolean filled) {
        super(startPt, endPt, paint, stroke, filled);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setPaint(getPaint());
        g2d.setStroke(getStroke());
        Rectangle2D.Double rect = new Rectangle2D.Double(
                getTopLeftX(), getTopLeftY(), getWidth(), getHeight());

        if (isFilled()) {
            g2d.fill(rect);
        } else {
            g2d.draw(rect);
        }
    }
}
