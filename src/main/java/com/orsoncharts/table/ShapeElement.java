/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.table;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.util.Map;

/**
 * A table element that displays a shape.
 */
public class ShapeElement implements TableElement {

    private Shape shape;
    
    private Paint fillPaint;
    
    public ShapeElement(Shape shape, Paint fillPaint) {
        super();
        this.shape = shape;
        this.fillPaint = fillPaint;
    }
    
    @Override
    public Dimension2D preferredSize(Graphics2D g2, Rectangle2D bounds) {
        return preferredSize(g2, bounds, null);
    }

    @Override
    public Dimension2D preferredSize(Graphics2D g2, Rectangle2D bounds, 
            Map<String, Object> constraints) {
        Rectangle2D shapeBounds = shape.getBounds2D();
        return new Dimension((int) Math.min(shapeBounds.getWidth(), 
                bounds.getWidth()), (int) Math.min(shapeBounds.getHeight(), 
                shapeBounds.getHeight()));
    }

    @Override
    public void draw(Graphics2D g2, Rectangle2D bounds) {
        AffineTransform saved = g2.getTransform();
        g2.translate(bounds.getX(), bounds.getY());
        g2.setPaint(this.fillPaint);
        g2.fill(shape);
        g2.setTransform(saved);
    }
    
}
