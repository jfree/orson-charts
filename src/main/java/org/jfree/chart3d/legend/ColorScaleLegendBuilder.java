/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-present, by David Gilbert.  All rights reserved.
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

package org.jfree.chart3d.legend;

import java.io.Serializable;

import org.jfree.chart3d.Chart3D;
import org.jfree.chart3d.Orientation;
import org.jfree.chart3d.graphics2d.Anchor2D;
import org.jfree.chart3d.plot.CategoryPlot3D;
import org.jfree.chart3d.plot.Plot3D;
import org.jfree.chart3d.plot.XYZPlot;
import org.jfree.chart3d.renderer.ColorScale;
import org.jfree.chart3d.renderer.ColorScaleRenderer;
import org.jfree.chart3d.renderer.FixedColorScale;
import org.jfree.chart3d.renderer.category.CategoryRenderer3D;
import org.jfree.chart3d.renderer.xyz.XYZRenderer;
import org.jfree.chart3d.style.ChartStyle;
import org.jfree.chart3d.table.TableElement;

/**
 * A legend builder that creates a legend representing a {@link ColorScale}.
 * This builder will only create a legend if the plot uses a renderer
 * that implements the {@link ColorScaleRenderer} interface.
 * <br><br>
 * The orientation and anchor point for the legend are properties of the 
 * {@link Chart3D} class.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 * 
 * @since 1.1
 */
@SuppressWarnings("serial")
public class ColorScaleLegendBuilder implements LegendBuilder, Serializable {
    
    /** The width of the bar showing the color scale (in Java2D units). */
    private double barWidth;
    
    /** The length of the bar showing the color scale (in Java2D units). */
    private double barLength;
    
    /** 
     * A flag to determine whether or not FixedColorScale is ignored (defaults 
     * to {@code true}). 
     */
    private boolean ignoreFixedColorScale;
    
    /**
     * Creates a new instance.
     */
    public ColorScaleLegendBuilder() {
        this.barWidth = 16.0;
        this.barLength = 140.0;
        this.ignoreFixedColorScale = true;
    }
    
    /**
     * Returns the width of the bar displaying the color scale.
     * 
     * @return The width (in Java2D units). 
     */
    public double getBarWidth() {
        return this.barWidth;
    }
    
    /**
     * Sets the width of the bar displaying the color scale.
     * 
     * @param width  the width (in Java2D units). 
     */
    public void setBarWidth(double width) {
        this.barWidth = width;
    }
    
    /**
     * Returns the length of the bar displaying the color scale.
     * 
     * @return The length (in Java2D units). 
     */
    public double getBarLength() {
        return this.barLength;
    }
    
    /**
     * Sets the length of the bar displaying the color scale.
     * 
     * @param length  the length (in Java2D units). 
     */
    public void setBarLength(double length) {
        this.barLength = length;
    }
    
    /**
     * Returns the flag that controls whether or not a {@link FixedColorScale}
     * will be ignored for the purposes of generating a legend.
     * 
     * @return A boolean. 
     */
    public boolean getIgnoreFixedColorScale() {
        return this.ignoreFixedColorScale;
    }
    
    /**
     * Sets the flag that controls whether or not a {@link FixedColorScale}
     * will be ignored for the purposes of generating a legend.
     * 
     * @param ignore  the new flag value. 
     */
    public void setIgnoreFixedColorScale(boolean ignore) {
        this.ignoreFixedColorScale = ignore;
    }
    
    /**
     * Creates a new color scale legend with the specified orientation.
     * If the plot does not use a renderer that implements 
     * {@link ColorScaleRenderer} then this method will return {@code null}
     * and no legend will be drawn on the chart.
     * 
     * @param plot  the plot ({@code null} not permitted).
     * @param anchor  the anchor ({@code null} not permitted).
     * @param orientation  the orientation ({@code null} not permitted).
     * @param style  the chart style ({@code null} not permitted).
     * 
     * @return A color scale legend (possibly {@code null}). 
     */
    @Override
    public TableElement createLegend(Plot3D plot, Anchor2D anchor,
            Orientation orientation, ChartStyle style) {
        ColorScaleRenderer renderer = null;
        if (plot instanceof CategoryPlot3D) {
            CategoryRenderer3D r = ((CategoryPlot3D) plot).getRenderer();
            if (r instanceof ColorScaleRenderer) {
                renderer = (ColorScaleRenderer) r;
            }
        } else if (plot instanceof XYZPlot) {
            XYZRenderer r = ((XYZPlot) plot).getRenderer();
            if (r instanceof ColorScaleRenderer) {
                renderer = (ColorScaleRenderer) r;
            }
        }
        if (renderer == null) {
            return null;
        }
        // it doesn't make much sense to display a color scale legend for a
        // FixedColorScale so we check for that and ignore it (unless the
        // developer switched the ignoreFixedColorScale flag, in which case
        // you can have your legend)...
        if (this.ignoreFixedColorScale 
                && renderer.getColorScale() instanceof FixedColorScale) {
            return null;
        }
        return createColorScaleLegend(renderer, orientation, anchor, style);  
    }

    private TableElement createColorScaleLegend(ColorScaleRenderer r, 
            Orientation orientation, Anchor2D anchor, ChartStyle style) {
        ColorScale scale = r.getColorScale();
        ColorScaleElement element = new ColorScaleElement(scale, orientation, 
                this.barWidth, this.barLength, style.getLegendItemFont(),
                style.getLegendItemColor());
        element.setBackgroundColor(style.getLegendItemBackgroundColor());
        element.setRefPoint(anchor.getRefPt());
        return element;
    }

    /**
     * Tests this builder for equality with an arbitrary object.
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
        if (!(obj instanceof ColorScaleLegendBuilder)) {
            return false;
        }
        ColorScaleLegendBuilder that = (ColorScaleLegendBuilder) obj;
        if (this.barWidth != that.barWidth) {
            return false;
        }
        if (this.barLength != that.barLength) {
            return false;
        }
        return true;
    }
}
