/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2016, by Object Refinery Limited.  All rights reserved.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 * 
 * If you do not wish to be bound by the terms of the GPL, an alternative
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts home page:
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
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
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 */
@SuppressWarnings("serial")
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
     * @param shape  the shape ({@code null} not permitted).
     * @param fillColor  the fill color ({@code null} not permitted).
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
     * @param color  the fill color ({@code null} not permitted).
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

    /**
     * Draws the shape element within the specified bounds.
     * 
     * @param g2  the graphics target ({@code null} not permitted).
     * @param bounds  the bounds ({@code null} not permitted).
     */
    @Override
    public void draw(Graphics2D g2, Rectangle2D bounds) {
        draw(g2, bounds, null);
    }
    
    /**
     * Draws the element within the specified bounds.  If the 
     * {@code recordBounds} flag is set, this element and each of its
     * children will have their {@code BOUNDS_2D} property updated with 
     * the current bounds.
     * 
     * @param g2  the graphics target ({@code null} not permitted).
     * @param bounds  the bounds ({@code null} not permitted).
     * @param onDrawHandler  an object that will receive notification before 
     *     and after the element is drawn ({@code null} permitted).
     * 
     * @since 1.3
     */
    @Override
    public void draw(Graphics2D g2, Rectangle2D bounds, 
            TableElementOnDraw onDrawHandler) {
        if (onDrawHandler != null) {
            onDrawHandler.beforeDraw(this, g2, bounds);
        }
        AffineTransform saved = g2.getTransform();
        RectanglePainter background = getBackground();
        if (background != null) {
            background.fill(g2, bounds);
        }
        g2.translate(bounds.getCenterX(), bounds.getCenterY());
        g2.setPaint(this.fillColor);
        g2.fill(shape);
        g2.setTransform(saved);
        if (onDrawHandler != null) {
            onDrawHandler.afterDraw(this, g2, bounds);
        }
    }
    
    /**
     * Receives a visitor.
     * 
     * @param visitor  the visitor ({@code null} not permitted).
     * 
     * @since 1.2
     */
    @Override
    public void receive(TableElementVisitor visitor) {
        visitor.visit(this);
    }
    
    /**
     * Returns a string representation of this element, primarily for
     * debugging purposes.
     * 
     * @return A string representation of this element. 
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ShapeElement[shape=").append(this.shape).append("]");
        return sb.toString();
    }
    
}
