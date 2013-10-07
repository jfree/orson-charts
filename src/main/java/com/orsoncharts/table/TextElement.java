/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.table;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.orsoncharts.util.ArgChecks;
import com.orsonpdf.util.TextAnchor;
import com.orsonpdf.util.TextUtils;

/**
 * A table element consisting of some text that will be drawn on one line.
 */
public class TextElement extends AbstractTableElement 
        implements TableElement {

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
        super();
        ArgChecks.nullNotPermitted(text, "text");
        this.text = text;
        this.font = new Font("Dialog", Font.PLAIN, 12);
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
                break;
            case RIGHT:
                x = bounds.getMaxX() - width;
                break;
            default: 
                throw new IllegalStateException("HAlign: " + this.alignment);
        }
        double y = bounds.getY();
        double w = Math.min(width, bounds.getWidth());
        double h = Math.min(textBounds.getHeight(), bounds.getHeight());
        List<Rectangle2D> result = new ArrayList<Rectangle2D>(1);        
        Rectangle2D pos = new Rectangle2D.Double(x, y, w, h);
        result.add(pos);
        return result;
    }

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
    
}
