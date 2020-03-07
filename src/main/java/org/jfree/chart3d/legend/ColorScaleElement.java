/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2020, by Object Refinery Limited.  All rights reserved.
 * 
 * https://github.com/jfree/orson-charts
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

package org.jfree.chart3d.legend;

import java.awt.Shape;
import java.util.ArrayList;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Map;
import java.awt.FontMetrics;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.jfree.chart3d.Orientation;
import org.jfree.chart3d.data.Range;
import org.jfree.chart3d.graphics2d.Fit2D;
import org.jfree.chart3d.graphics2d.TextAnchor;
import org.jfree.chart3d.internal.Args;
import org.jfree.chart3d.internal.TextUtils;
import org.jfree.chart3d.renderer.ColorScale;
import org.jfree.chart3d.table.AbstractTableElement;
import org.jfree.chart3d.table.ElementDimension;
import org.jfree.chart3d.table.TableElement;
import org.jfree.chart3d.table.TableElementOnDraw;
import org.jfree.chart3d.table.TableElementVisitor;

/**
 * A {@link TableElement} that displays a {@link ColorScale}.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 * 
 * @since 1.1
 */
@SuppressWarnings("serial")
public class ColorScaleElement extends AbstractTableElement 
        implements TableElement {

    /** The color scale. */
    private final ColorScale scale;
    
    /** The orientation (horizontal or vertical). */
    private final Orientation orientation;
    
    /** The length of the bar. */
    private final double barLength;
    
    /** The width of the bar. */
    private final double barWidth;
    
    /** The gap between the color scale bar and the text labels. */
    private final double textOffset;
    
    /** The font for the text labels. */
    private final Font font;
    
    /** The text color. */
    private final Color textColor;
    
    /** The number formatter. */
    private final NumberFormat formatter;
    
    /**
     * Creates a new {@code ColorScaleElement} with the specified 
     * attributes.
     * 
     * @param scale  the color scale ({@code null} not permitted).
     * @param orientation  the orientation ({@code null} not permitted).
     * @param barWidth  the bar width (in Java2D units).
     * @param barLength  the bar length (in Java2D units).
     * @param font  the font ({@code null} not permitted).
     * @param textColor  the text color ({@code null} not permitted).
     * 
     * @since 1.2
     */
    public ColorScaleElement(ColorScale scale, Orientation orientation, 
            double barWidth, double barLength, Font font, Color textColor) {
        super();
        Args.nullNotPermitted(scale, "scale");
        Args.nullNotPermitted(orientation, "orientation");
        Args.nullNotPermitted(font, "font");
        this.scale = scale;
        this.orientation = orientation;
        this.barWidth = barWidth;
        this.barLength = barLength;
        this.textOffset = 2;
        this.font = font;
        this.textColor = textColor;
        this.formatter = new DecimalFormat("0.00");
    }
    
    /**
     * Returns the color scale.
     * 
     * @return The color scale (never {@code null}). 
     */
    public ColorScale getColorScale() {
        return this.scale;
    }
    
    /**
     * Returns the orientation.
     * 
     * @return The orientation (never {@code null}). 
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
     * @return The font (never {@code null}). 
     */
    public Font getFont() {
        return this.font;
    }
    
    /**
     * Returns the text color.
     * 
     * @return The text color (never {@code null}). 
     */
    public Color getTextColor() {
        return this.textColor;
    }
    
    /**
     * Receives a visitor.  This is part of a general mechanism to perform
     * operations on an arbitrary hierarchy of table elements.  You will not 
     * normally call this method directly.
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
     * Returns the preferred size for this element.
     * 
     * @param g2  the graphics target.
     * @param bounds  the available drawing space.
     * 
     * @return The preferred size (never {@code null}). 
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
     * @return The preferred size (never {@code null}). 
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
        List<Rectangle2D> result = new ArrayList<>(1);
        Dimension2D prefDim = preferredSize(g2, bounds);
        Fit2D fitter = Fit2D.getNoScalingFitter(getRefPoint());
        Rectangle2D dest = fitter.fit(prefDim, bounds);
        result.add(dest);
        return result;
    }

    /**
     * Draws the element within the specified bounds.
     * 
     * @param g2  the graphics target ({@code null} not permitted).
     * @param bounds  the bounds ({@code null} not permitted).
     */
    @Override
    public void draw(Graphics2D g2, Rectangle2D bounds) {
        draw(g2, bounds, null);
    }
    
    /**
     * Draws the element within the specified bounds.
     * 
     * @param g2  the graphics target ({@code null} not permitted).
     * @param bounds  the bounds ({@code null} not permitted).
     * @param onDrawHandler  receives notification before and after the element
     *     is drawn ({@code null} permitted);
     * 
     * @since 1.3
     */
    @Override
    public void draw(Graphics2D g2, Rectangle2D bounds, 
            TableElementOnDraw onDrawHandler) {
        
        if (onDrawHandler != null) {
            onDrawHandler.beforeDraw(this, g2, bounds);
        }
        
        Shape savedClip = g2.getClip();
        g2.clip(bounds);
        List<Rectangle2D> layoutInfo = layoutElements(g2, bounds, null);
        Rectangle2D dest = layoutInfo.get(0);        
        if (getBackground() != null) {
            getBackground().fill(g2, dest);
        }
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
            g2.setPaint(this.textColor);
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
            g2.setPaint(this.textColor);
            TextUtils.drawAlignedString(minStr, g2, 
                    (float) (x1 + this.textOffset), (float) y1, 
                    TextAnchor.HALF_ASCENT_LEFT);
            TextUtils.drawAlignedString(maxStr, g2, 
                    (float) (x1 + this.textOffset), (float) y0, 
                    TextAnchor.HALF_ASCENT_LEFT);
        }
        g2.setClip(savedClip);

        if (onDrawHandler != null) {
            onDrawHandler.afterDraw(this, g2, bounds);
        }
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
