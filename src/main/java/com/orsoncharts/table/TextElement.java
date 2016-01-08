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
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.util.TextAnchor;
import com.orsoncharts.util.TextUtils;

/**
 * A table element consisting of some text that will be drawn on one line.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 */
@SuppressWarnings("serial")
public class TextElement extends AbstractTableElement 
        implements TableElement, Serializable {

    /**
     * The default font.
     * 
     * @since 1.1
     */
    public static final Font DEFAULT_FONT = new Font("Dialog", Font.PLAIN, 
            12);
    
    /** The text (never {@code null}). */
    private String text;
    
    /** The font (never {@code null}). */
    private Font font;
    
    /** The color for the text (never {@code null}). */
    private Color color;
    
    /** The horizontal alignment (never {@code null}). */
    private HAlign alignment;
   
    /**
     * Creates a new element that will display the specified text using the
     * default font ({@link #DEFAULT_FONT}).
     * 
     * @param text  the text ({@code null} not permitted).
     */
    public TextElement(String text) {
        this(text, DEFAULT_FONT);
    }
    
    /**
     * Creates a new instance.
     * 
     * @param text  the text ({@code null} not permitted).
     * @param font  the font ({@code null} not permitted).
     */
    public TextElement(String text, Font font) {
        super();
        ArgChecks.nullNotPermitted(text, "text");
        ArgChecks.nullNotPermitted(font, "font");
        this.text = text;
        this.font = font;
        this.color = Color.BLACK;
        this.alignment = HAlign.LEFT;
    }
    
    /**
     * Returns the font.  The default value is {@link #DEFAULT_FONT}.
     * 
     * @return The font (never {@code null}). 
     */
    public Font getFont() {
        return this.font;
    }
    
    /**
     * Sets the font.
     * 
     * @param font  the font ({@code null} not permitted). 
     */
    public void setFont(Font font) {
        ArgChecks.nullNotPermitted(font, "font");
        this.font = font;
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public void setColor(Color color) {
        ArgChecks.nullNotPermitted(color, "color");
        this.color = color;
    }
    
    /**
     * Returns the horizontal alignment that will be used when rendering the
     * text.  The default value is {@code LEFT}.
     * 
     * @return The horizontal alignment (never {@code null}). 
     */
    public HAlign getHorizontalAlignment() {
        return this.alignment;
    }
    
    /**
     * Sets the horizontal alignment.
     * 
     * @param align  the alignment ({@code null} not permitted). 
     */
    public void setHorizontalAligment(HAlign align) {
        ArgChecks.nullNotPermitted(align, "align");
        this.alignment = align;
    }

    /**
     * Returns the preferred size of the element (including insets).
     * 
     * @param g2  the graphics target.
     * @param bounds  the bounds.
     * @param constraints  the constraints (ignored for now).
     * 
     * @return The preferred size. 
     */
    @Override
    public Dimension2D preferredSize(Graphics2D g2, Rectangle2D bounds, 
            Map<String, Object> constraints) {
        g2.setFont(this.font);
        Rectangle2D textBounds = TextUtils.getTextBounds(this.text, 
                g2.getFontMetrics(this.font));
        Insets insets = getInsets();
        double w = Math.min(textBounds.getWidth() + insets.left + insets.right, 
                bounds.getWidth());
        double h = Math.min(textBounds.getHeight() + insets.top + insets.bottom,
                bounds.getHeight());
        return new ElementDimension(w, h);
    }

    /**
     * Performs a layout of this table element, returning a list of bounding
     * rectangles for the element and its subelements.
     * 
     * @param g2  the graphics target.
     * @param bounds  the bounds.
     * @param constraints  the constraints (if any).
     * 
     * @return A list containing the bounding rectangle for the text (as the
     *     only item in the list). 
     */
    @Override
    public List<Rectangle2D> layoutElements(Graphics2D g2, Rectangle2D bounds, 
            Map<String, Object> constraints) {
        g2.setFont(this.font);
        Rectangle2D textBounds = TextUtils.getTextBounds(this.text, 
                g2.getFontMetrics(this.font));
        Insets insets = getInsets();
        double width = textBounds.getWidth() + insets.left + insets.right;
        double x = bounds.getX();
        switch (this.alignment) {
            case LEFT: 
                x = bounds.getX();
                break;
            case CENTER:
                x = bounds.getCenterX() - width / 2.0 - insets.left;
                break;
            case RIGHT:
                x = bounds.getMaxX() - width - insets.right;
                break;
            default: 
                throw new IllegalStateException("HAlign: " + this.alignment);
        }
        double y = bounds.getY();
        double w = Math.min(width, bounds.getWidth());
        double h = Math.min(textBounds.getHeight() + insets.top + insets.bottom,
                bounds.getHeight());
        List<Rectangle2D> result = new ArrayList<Rectangle2D>(1);        
        result.add(new Rectangle2D.Double(x, y, w, h));
        return result;
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
     * Draws the element within the specified bounds.
     * 
     * @param g2  the graphics target.
     * @param bounds  the bounds.
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
     */
    @Override
    public void draw(Graphics2D g2, Rectangle2D bounds, 
            TableElementOnDraw onDrawHandler) {
        if (onDrawHandler != null) {
            onDrawHandler.beforeDraw(this, g2, bounds);
        }
        List<Rectangle2D> layout = layoutElements(g2, bounds, null);
        Rectangle2D textBounds = layout.get(0);
        if (getBackground() != null) {
            getBackground().fill(g2, textBounds);
        }
        g2.setPaint(this.color);
        g2.setFont(this.font);
        Insets insets = getInsets();
        TextUtils.drawAlignedString(this.text, g2, 
                (float) (textBounds.getX() + insets.left), 
                (float) (textBounds.getY() + insets.top), TextAnchor.TOP_LEFT);
        if (onDrawHandler != null) {
            onDrawHandler.afterDraw(this, g2, bounds);
        }
    }
    
    /**
     * Tests this element for equality with an arbitrary object.
     * 
     * @param obj  the object ({@code null} permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TextElement)) {
            return false;
        }
        TextElement that = (TextElement) obj;
        if (!this.text.equals(that.text)) {
            return false;
        }
        if (!this.font.equals(that.font)) {
            return false;
        }
        if (!this.color.equals(that.color)) {
            return false;
        }
        if (this.alignment != that.alignment) {
            return false;
        }
        return super.equals(obj);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TextElement[text=").append(this.text).append("]");
        return sb.toString();
    }
}
