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
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;
import java.util.Map;

/**
 * A table element consisting of some text that will be drawn on one line.
 */
public class SimpleTextElement implements TableElement {

    /** The text (never <code>null</code>). */
    private String text;
    
    /** The font. */
    private Font font;
    
    /** The foreground paint. */
    private Paint foregroundPaint;
    
    /** The background paint (this can be <code>null</code>). */
    private Paint backgroundPaint;
    
    /** The insets. */
    private Insets insets;
   
    /**
     * Creates a new instance.
     * 
     * @param text  the text (<code>null</code> not permitted).
     */
    public SimpleTextElement(String text) {
        ArgChecks.nullNotPermitted(text, "text");
        this.text = text;
        this.font = new Font("Dialog", Font.PLAIN, 12);
        this.foregroundPaint = Color.BLACK;
        this.backgroundPaint = null;
        this.insets = new Insets(2, 2, 2, 2);
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
    
    /**
     * Returns the foreground paint.
     * 
     * @return The foreground paint (never <code>null</code>). 
     */
    public Paint getForegroundPaint() {
        return this.foregroundPaint;
    }

    /**
     * Sets the foreground paint.
     * 
     * @param paint  the paint (<code>null</code> not permitted). 
     */
    public void setForegroundPaint(Paint paint) {
        ArgChecks.nullNotPermitted(paint, "paint");
        this.foregroundPaint = paint;
    }
    
    /**
     * Returns the background paint.
     * 
     * @return The background paint (never <code>null</code>). 
     */
    public Paint getBackgroundPaint() {
        return this.backgroundPaint;
    }

    /**
     * Sets the background paint.
     * 
     * @param paint  the paint (<code>null</code> not permitted). 
     */
    public void setBackgroundPaint(Paint paint) {
        ArgChecks.nullNotPermitted(paint, "paint");
        this.backgroundPaint = paint;
    }
    
    @Override
    public Rectangle2D preferredSize(Graphics2D g2, Rectangle2D bounds) {
        return preferredSize(g2, bounds, null);
    }

    @Override
    public Rectangle2D preferredSize(Graphics2D g2, Rectangle2D bounds, 
            Map<String, Object> constraints) {
        g2.setFont(this.font);
        Rectangle2D textBounds = TextUtils.getTextBounds(this.text, g2, 
                g2.getFontMetrics(this.font));
        return new Rectangle2D.Double(textBounds.getX(), bounds.getY(), 
                textBounds.getWidth(), textBounds.getHeight());
    }

    @Override
    public void render(Graphics2D g2, Rectangle2D bounds) {
        if (this.backgroundPaint != null) {
            g2.setPaint(this.backgroundPaint);
            g2.fill(bounds);
        }
        g2.setPaint(this.foregroundPaint);
        g2.setFont(this.font);
        TextUtils.drawAlignedString(this.text, g2, (float) (bounds.getX() + insets.left), 
                (float) (bounds.getY() + insets.top), TextAnchor.TOP_LEFT);
    }
    
}
