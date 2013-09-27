/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.table;

import com.orsoncharts.ArgChecks;
import com.orsonpdf.util.TextAnchor;
import com.orsonpdf.util.TextUtils;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.util.Map;

/**
 * A table element consisting of some text that will be drawn on one line.
 */
public class TextElement extends AbstractTableElement 
        implements TableElement {

    /** The text (never <code>null</code>). */
    private String text;
    
    /** The font. */
    private Font font;
   
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
    }
    
    /**
     * Returns the font.
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

    @Override
    public Dimension2D preferredSize(Graphics2D g2, Rectangle2D bounds, 
            Map<String, Object> constraints) {
        g2.setFont(this.font);
        Rectangle2D textBounds = TextUtils.getTextBounds(this.text, g2, 
                g2.getFontMetrics(this.font));
        return new Dimension((int) textBounds.getWidth(), 
                (int) textBounds.getHeight());
    }

    @Override
    public void draw(Graphics2D g2, Rectangle2D bounds) {
        if (getBackgroundPaint() != null) {
            g2.setPaint(getBackgroundPaint());
            g2.fill(bounds);
        }
        g2.setPaint(getForegroundPaint());
        g2.setFont(this.font);
        Insets insets = getInsets();
        TextUtils.drawAlignedString(this.text, g2, (float) (bounds.getX() + insets.left), 
                (float) (bounds.getY() + insets.top), TextAnchor.TOP_LEFT);
    }
    
}
