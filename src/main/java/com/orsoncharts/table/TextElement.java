/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.table;

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
public class TextElement extends AbstractTableElement 
        implements TableElement, Serializable {

    /** The text (never <code>null</code>). */
    private String text;
    
    /** The font (never <code>null</code>). */
    private Font font;
    
    /** The horizontal alignment (never <code>null</code>). */
    private HAlign alignment;
   
    /**
     * Creates a new instance.
     * 
     * @param text  the text (<code>null</code> not permitted).
     */
    public TextElement(String text) {
        this(text, new Font("Dialog", Font.PLAIN, 12));
    }
    
    /**
     * Creates a new instance.
     * 
     * @param text  the text (<code>null</code> not permitted).
     * @param font  the font (<code>null</code> not permitted).
     */
    public TextElement(String text, Font font) {
        super();
        ArgChecks.nullNotPermitted(text, "text");
        ArgChecks.nullNotPermitted(font, "font");
        this.text = text;
        this.font = font;
        this.alignment = HAlign.LEFT;
    }
    
    /**
     * Returns the font.  The default is 
     * <code>Font("Dialog", Font.PLAIN, 12)</code>.
     * 
     * @return The font (never <code>null</code>). 
     */
    public Font getFont() {
        return this.font;
    }
    
    /**
     * Sets the font.
     * 
     * @param font  the font (<code>null</code> not permitted). 
     */
    public void setFont(Font font) {
        ArgChecks.nullNotPermitted(font, "font");
        this.font = font;
    }
    
    /**
     * Returns the horizontal alignment.  The default value is 
     * <code>LEFT</code>.
     * 
     * @return The horizontal alignment (never <code>null</code>). 
     */
    public HAlign getHorizontalAlignment() {
        return this.alignment;
    }
    
    /**
     * Sets the horizontal alignment.
     * 
     * @param align  the alignment (<code>null</code> not permitted). 
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
        Rectangle2D textBounds = TextUtils.getTextBounds(this.text, g2, 
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
     * @return A list of bounding rectangles. 
     */
    @Override
    public List<Rectangle2D> layoutElements(Graphics2D g2, Rectangle2D bounds, 
            Map<String, Object> constraints) {
        g2.setFont(this.font);
        Rectangle2D textBounds = TextUtils.getTextBounds(this.text, g2, 
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
                x = bounds.getMaxX() - width;
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
     * Draws the element within the specified bounds.
     * 
     * @param g2  the graphics target.
     * @param bounds  the bounds.
     */
    @Override
    public void draw(Graphics2D g2, Rectangle2D bounds) {
        List<Rectangle2D> layout = layoutElements(g2, bounds, null);
        Rectangle2D textBounds = layout.get(0);
        if (getBackgroundPaint() != null) {
            g2.setPaint(getBackgroundPaint());
            g2.fill(textBounds);
        }
        g2.setPaint(getForegroundPaint());
        g2.setFont(this.font);
        Insets insets = getInsets();
        TextUtils.drawAlignedString(this.text, g2, 
                (float) (textBounds.getX() + insets.left), 
                (float) (textBounds.getY() + insets.top), TextAnchor.TOP_LEFT);
    }
    
    /**
     * Tests this element for equality with an arbitrary object.
     * 
     * @param obj  the object (<code>null</code> permitted).
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
        if (this.alignment != that.alignment) {
            return false;
        }
        return super.equals(obj);
    }
    
}
