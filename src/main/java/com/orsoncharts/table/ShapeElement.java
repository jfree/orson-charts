/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of this source file is prohibited.
 * 
 */

package com.orsoncharts.table;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.orsoncharts.util.ArgChecks;

/**
 * A table element that displays a shape.
 */
public class ShapeElement extends AbstractTableElement 
        implements TableElement {

    /** 
     * The shape (by convention, the shape should be centered on the point
     * (0, 0)). 
     */
    private Shape shape;
    
    private Color fillColor;
    
    /**
     * Creates a new shape element.
     * 
     * @param shape  the shape (<code>null</code> not permitted).
     * @param fillColor  the fill color (<code>null</code> not permitted).
     */
    public ShapeElement(Shape shape, Color fillColor) {
        super();
        ArgChecks.nullNotPermitted(shape, "shape");
        ArgChecks.nullNotPermitted(fillColor, "fillColor");
        this.shape = shape;
        this.fillColor = fillColor;
    }

    /**
     * Returns the fill color.
     * 
     * @return The fill color.
     * 
     * @since 1.2
     */
    public Color getFillColor() {
        return this.fillColor;
    }
    
    /**
     * Sets the fill color.
     * 
     * @param color  the fill color (<code>null</code> not permitted).
     * 
     * @since 1.2
     */
    public void setFillColor(Color color) {
        ArgChecks.nullNotPermitted(color, "color");
        this.fillColor = color;
    }
    
    @Override
    public Dimension2D preferredSize(Graphics2D g2, Rectangle2D bounds, 
            Map<String, Object> constraints) {
        Insets insets = getInsets();
        Rectangle2D shapeBounds = shape.getBounds2D();
        return new ElementDimension(Math.min(shapeBounds.getWidth() 
                + insets.left + insets.right, bounds.getWidth()), 
                Math.min(shapeBounds.getHeight() + insets.top + insets.bottom, 
                bounds.getHeight()));
    }

    @Override
    public List<Rectangle2D> layoutElements(Graphics2D g2, Rectangle2D bounds, 
            Map<String, Object> constraints) {
        List<Rectangle2D> result = new ArrayList<Rectangle2D>(1);
        Insets insets = getInsets();
        Rectangle2D shapeBounds = this.shape.getBounds2D();
        double w = Math.min(shapeBounds.getWidth() + insets.left + insets.right,
                bounds.getWidth());
        double h = Math.min(shapeBounds.getHeight() + insets.top 
                + insets.bottom, bounds.getHeight());
        Rectangle2D pos = new Rectangle2D.Double(bounds.getCenterX() - w / 2.0,
                bounds.getCenterY() - h / 2.0, w, h);
        result.add(pos);
        return result;
    }

    @Override
    public void draw(Graphics2D g2, Rectangle2D bounds) {
        AffineTransform saved = g2.getTransform();
        RectanglePainter background = getBackground();
        if (background != null) {
            background.fill(g2, bounds);
        }
        g2.translate(bounds.getCenterX(), bounds.getCenterY());
        g2.setPaint(this.fillColor);
        g2.fill(shape);
        g2.setTransform(saved);
    }
    
    /**
     * Receives a visitor.
     * 
     * @param visitor  the visitor (<code>null</code> not permitted).
     * 
     * @since 1.2
     */
    @Override
    public void receive(TableElementVisitor visitor) {
        visitor.visit(this);
    }
    
}
