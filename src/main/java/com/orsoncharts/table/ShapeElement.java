/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.table;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A table element that displays a shape.
 */
public class ShapeElement implements TableElement {

    /** 
     * The shape (by convention, the shape should be centered on the point
     * (0, 0)). 
     */
    private Shape shape;
    
    /** The fill paint. */
    private Paint fillPaint;
    
    private Paint backgroundPaint;
    
    /**
     * Creates a new shape element.
     * 
     * @param shape  the shape.
     * @param fillPaint  the fill paint.
     */
    public ShapeElement(Shape shape, Paint fillPaint) {
        super();
        this.shape = shape;
        this.fillPaint = fillPaint;
        this.backgroundPaint = Color.WHITE;
    }
    
    @Override
    public Dimension2D preferredSize(Graphics2D g2, Rectangle2D bounds) {
        return preferredSize(g2, bounds, null);
    }

    @Override
    public Dimension2D preferredSize(Graphics2D g2, Rectangle2D bounds, 
            Map<String, Object> constraints) {
        Rectangle2D shapeBounds = shape.getBounds2D();
        return new ElementDimension(Math.min(shapeBounds.getWidth(), 
                bounds.getWidth()), Math.min(shapeBounds.getHeight(), 
                shapeBounds.getHeight()));
    }

    @Override
    public List<Rectangle2D> layoutElements(Graphics2D g2, Rectangle2D bounds, 
            Map<String, Object> constraints) {
        List<Rectangle2D> result = new ArrayList<Rectangle2D>();
        Rectangle2D shapeBounds = this.shape.getBounds2D();
        double w = Math.min(shapeBounds.getWidth(), bounds.getWidth());
        double h = Math.min(shapeBounds.getHeight(), bounds.getHeight());
        Rectangle2D pos = new Rectangle2D.Double(bounds.getCenterX() - w / 2.0,
                bounds.getCenterY() - h / 2.0, w, h);
        result.add(pos);
        return result;
    }

    @Override
    public void draw(Graphics2D g2, Rectangle2D bounds) {
        AffineTransform saved = g2.getTransform();
        g2.setPaint(this.backgroundPaint);
        g2.fill(bounds);
        g2.translate(bounds.getCenterX(), bounds.getCenterY());
        g2.setPaint(this.fillPaint);
        g2.fill(shape);
        g2.setTransform(saved);
    }
    
}
