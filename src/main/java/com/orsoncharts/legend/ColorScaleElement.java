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

package com.orsoncharts.legend;

import java.awt.Shape;
import java.util.ArrayList;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.BasicStroke;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Map;
import java.awt.FontMetrics;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import com.orsoncharts.renderer.ColorScale;
import com.orsoncharts.table.AbstractTableElement;
import com.orsoncharts.table.ElementDimension;
import com.orsoncharts.table.TableElement;
import com.orsoncharts.util.Orientation;
import com.orsoncharts.util.TextAnchor;
import com.orsoncharts.util.TextUtils;
import com.orsoncharts.Range;
import com.orsoncharts.table.TableElementVisitor;
import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.util.Fit2D;

/**
 * A {@link TableElement} that displays a {@link ColorScale}.
 * 
 * @since 1.1
 */
public class ColorScaleElement extends AbstractTableElement 
        implements TableElement {

    /** The color scale. */
    private ColorScale scale;
    
    /** The orientation (horizontal or vertical). */
    private Orientation orientation;
    
    /** The length of the bar. */
    private double barLength;
    
    /** The width of the bar. */
    private double barWidth;
    
    /** The gap between the color scale bar and the text labels. */
    private double textOffset;
    
    /** The font for the text labels. */
    private Font font;
    
    /** The number formatter. */
    private NumberFormat formatter;
    
    /**
     * Creates a new <code>ColorScaleElement</code> with the specified 
     * attributes.
     * 
     * @param scale  the color scale (<code>null</code> not permitted).
     * @param orientation  the orientation (<code>null</code> not permitted).
     * @param barWidth  the bar width (in Java2D units).
     * @param barLength  the bar length (in Java2D units).
     * @param font  the font (<code>null</code> not permitted).
     */
    public ColorScaleElement(ColorScale scale, Orientation orientation, 
            double barWidth, double barLength, Font font) {
        super();
        ArgChecks.nullNotPermitted(scale, "scale");
        ArgChecks.nullNotPermitted(orientation, "orientation");
        ArgChecks.nullNotPermitted(font, "font");
        this.scale = scale;
        this.orientation = orientation;
        this.barWidth = barWidth;
        this.barLength = barLength;
        this.textOffset = 2;
        this.font = font;
        this.formatter = new DecimalFormat("0.00");
    }
    
    /**
     * Returns the color scale.
     * 
     * @return The color scale (never <code>null</code>). 
     */
    public ColorScale getColorScale() {
        return this.scale;
    }
    
    /**
     * Returns the orientation.
     * 
     * @return The orientation (never <code>null</code>). 
     */
    public Orientation getOrientation() {
        return this.orientation;
    }
    
    /**
     * Returns the bar width.
     * 
     * @return The bar width.
     */
    public double getBarWidth() {
        return this.barWidth;
    }
    
    /**
     * Returns the bar length.
     * 
     * @return The bar length. 
     */
    public double getBarLength() {
        return this.barLength;
    }
    
    /**
     * Returns the font used to display the labels on the color scale.
     * 
     * @return The font (never <code>null</code>). 
     */
    public Font getFont() {
        return this.font;
    }
    
    /**
     * Receives a visitor.  This is part of a general mechanism to perform
     * operations on an arbitrary hierarchy of table elements.  You will not 
     * normally call this method directly.
     * 
     * @param visitor  the visitor (<code>null</code> not permitted). 
     * 
     * @since 1.2
     */
    @Override
    public void receive(TableElementVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Returns the preferred size for this element.
     * 
     * @param g2  the graphics target.
     * @param bounds  the available drawing space.
     * 
     * @return The preferred size (never <code>null</code>). 
     */
    @Override
    public Dimension2D preferredSize(Graphics2D g2, Rectangle2D bounds) {
        return preferredSize(g2, bounds, null); 
    }

    /**
     * Returns the preferred size for this element.
     * 
     * @param g2  the graphics target.
     * @param bounds  the available drawing space.
     * @param constraints  layout constraints (ignored here).
     * 
     * @return The preferred size (never <code>null</code>). 
     */
    @Override
    public Dimension2D preferredSize(Graphics2D g2, Rectangle2D bounds, 
            Map<String, Object> constraints) {
        g2.setFont(this.font);
        FontMetrics fm = g2.getFontMetrics();
        Range r = this.scale.getRange();
        String minStr = this.formatter.format(r.getMin());
        String maxStr = this.formatter.format(r.getMax());
        Rectangle2D minStrBounds = TextUtils.getTextBounds(minStr, fm);
        Rectangle2D maxStrBounds = TextUtils.getTextBounds(maxStr, fm);
        double maxStrWidth = Math.max(minStrBounds.getWidth(),
                maxStrBounds.getWidth());
        Insets insets = getInsets();
        double w, h;
        if (this.orientation == Orientation.HORIZONTAL) {
            w = Math.min(this.barLength + insets.left + insets.right, 
                bounds.getWidth());
            h = Math.min(insets.top + this.barWidth + this.textOffset 
                    + minStrBounds.getHeight() + insets.bottom,
                bounds.getHeight());
        } else {
            w = Math.min(insets.left + this.barWidth + this.textOffset 
                    + maxStrWidth + insets.right, bounds.getWidth());
            h = Math.min(insets.top + this.barLength + this.textOffset 
                    + minStrBounds.getHeight() + insets.bottom,
                bounds.getHeight());
           
        }
        return new ElementDimension(w, h);
    }

    @Override
    public List<Rectangle2D> layoutElements(Graphics2D g2, Rectangle2D bounds, 
            Map<String, Object> constraints) {
        List<Rectangle2D> result = new ArrayList<Rectangle2D>(1);
        Dimension2D prefDim = preferredSize(g2, bounds);
        Fit2D fitter = Fit2D.getNoScalingFitter(getRefPoint());
        Rectangle2D dest = fitter.fit(prefDim, bounds);
        result.add(dest);
        return result;
    }

    /**
     * Draws the element within the specified bounds.
     * 
     * @param g2  the graphics target (<code>null</code> not permitted).
     * @param bounds  the bounds (<code>null</code> not permitted).
     */
    @Override
    public void draw(Graphics2D g2, Rectangle2D bounds) {
        Shape savedClip = g2.getClip();
        g2.clip(bounds);

        List<Rectangle2D> layoutInfo = layoutElements(g2, bounds, null);
        Rectangle2D dest = layoutInfo.get(0);
        
        if (getBackgroundPaint() != null) {
            g2.setPaint(getBackgroundPaint());
            g2.fill(dest);
        }
        g2.setPaint(getForegroundPaint());
        g2.setFont(this.font);
        FontMetrics fm = g2.getFontMetrics();
        Range r = this.scale.getRange();
        String minStr = this.formatter.format(r.getMin());
        String maxStr = this.formatter.format(r.getMax());
        Rectangle2D minStrBounds = TextUtils.getTextBounds(minStr, fm);
        Rectangle2D maxStrBounds = TextUtils.getTextBounds(maxStr, fm);
        Insets insets = getInsets();
        if (this.orientation == Orientation.HORIZONTAL) {
            double x0 = dest.getX() + insets.left 
                    + minStrBounds.getWidth() / 2.0;
            double x1 = dest.getMaxX() - insets.right 
                    - maxStrBounds.getWidth() / 2.0;
            double y0 = dest.getY() + insets.top;
            double y1 = y0 + this.barWidth;
            
            drawHorizontalScale(this.scale, g2, new Rectangle2D.Double(
                    (int) x0, (int) y0, (int) (x1 - x0), (int) this.barWidth));
            // fill the bar with the color scale
            g2.setPaint(getForegroundPaint());
            TextUtils.drawAlignedString(minStr, g2, (float) x0, 
                    (float) (y1 + this.textOffset), TextAnchor.TOP_CENTER);
            TextUtils.drawAlignedString(maxStr, g2, (float) x1, 
                    (float) (y1 + this.textOffset), TextAnchor.TOP_CENTER);
            
        } else { // VERTICAL
            double maxStrWidth = Math.max(minStrBounds.getWidth(), 
                    maxStrBounds.getWidth());
            double x1 = dest.getMaxX() - insets.right - maxStrWidth 
                    - this.textOffset;
            double x0 = x1 - this.barWidth;
            double y0 = dest.getY() + insets.top 
                    + maxStrBounds.getHeight() / 2.0;
            double y1 = y0 + this.barLength;            
            
            drawVerticalScale(this.scale, g2, new Rectangle2D.Double(
                    (int) x0, (int) y0, (int) (x1 - x0), (int) this.barLength));
            g2.setPaint(getForegroundPaint());
            TextUtils.drawAlignedString(minStr, g2, 
                    (float) (x1 + this.textOffset), (float) y1, 
                    TextAnchor.HALF_ASCENT_LEFT);
            TextUtils.drawAlignedString(maxStr, g2, 
                    (float) (x1 + this.textOffset), (float) y0, 
                    TextAnchor.HALF_ASCENT_LEFT);
        }
        g2.setClip(savedClip);
    }
    
    /**
     * Draws the color scale horizontally within the specified bounds.
     * 
     * @param colorScale  the color scale.
     * @param g2  the graphics target.
     * @param bounds  the bounds.
     */
    private void drawHorizontalScale(ColorScale colorScale, Graphics2D g2, 
            Rectangle2D bounds) {
        g2.setStroke(new BasicStroke(1.0f));
        for (int x = (int) bounds.getX(); x < bounds.getMaxX(); x++) {
            double p = (x - bounds.getX()) / bounds.getWidth();
            double value = colorScale.getRange().value(p);
            g2.setColor(colorScale.valueToColor(value));
            g2.drawLine(x, (int) bounds.getMinY(), x, (int) bounds.getMaxY());
        }    
    }
    
    /**
     * Draws the color scale vertically within the specified bounds.
     * 
     * @param colorScale  the color scale.
     * @param g2  the graphics target.
     * @param bounds  the bounds.
     */
    private void drawVerticalScale(ColorScale colorScale, Graphics2D g2, 
            Rectangle2D bounds) {
        g2.setStroke(new BasicStroke(1.0f));
        for (int y = (int) bounds.getY(); y < bounds.getMaxY(); y++) {
            double p = (y - bounds.getY()) / bounds.getHeight();
            double value = colorScale.getRange().value(1 - p);
            g2.setColor(this.scale.valueToColor(value));
            g2.drawLine((int) bounds.getX(), y, (int) bounds.getMaxX(), y);
        }    
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ColorScaleElement)) {
            return false;
        }
        ColorScaleElement that = (ColorScaleElement) obj;
        if (!this.scale.equals(that.scale)) {
            return false;
        }
        if (!this.orientation.equals(that.orientation)) {
            return false;
        }
        if (this.barLength != that.barLength) {
            return false;
        }
        if (this.barWidth != that.barWidth) {
            return false;
        }
        if (!this.font.equals(that.font)) {
            return false;
        }
        return super.equals(obj);
    }
}
