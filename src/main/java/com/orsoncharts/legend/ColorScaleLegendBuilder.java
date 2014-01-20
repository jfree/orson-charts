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

import java.io.Serializable;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.plot.Plot3D;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.renderer.ColorScale;
import com.orsoncharts.renderer.ColorScaleRenderer;
import com.orsoncharts.renderer.FixedColorScale;
import com.orsoncharts.renderer.category.CategoryRenderer3D;
import com.orsoncharts.renderer.xyz.XYZRenderer;
import com.orsoncharts.table.TableElement;
import com.orsoncharts.util.Anchor2D;
import com.orsoncharts.util.Orientation;
import com.orsoncharts.Chart3D;
import com.orsoncharts.style.ChartStyle;

/**
 * A legend builder that creates a legend representing a {@link ColorScale}.
 * This builder will only create a legend if the plot uses a renderer
 * that implements the {@link ColorScaleRenderer} interface.
 * <br><br>
 * The orientation and anchor point for the legend are properties of the 
 * {@link Chart3D} class.
 * 
 * @since 1.1
 */
public class ColorScaleLegendBuilder implements LegendBuilder, Serializable {
    
    /** The width of the bar showing the color scale (in Java2D units). */
    private double barWidth;
    
    /** The length of the bar showing the color scale (in Java2D units). */
    private double barLength;
    
    /** 
     * A flag to determine whether or not FixedColorScale is ignored (defaults 
     * to <code>true</code>). 
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
     * {@link ColorScaleRenderer} then this method will return <code>null</code>
     * and no legend will be drawn on the chart.
     * 
     * @param plot  the plot (<code>null</code> not permitted).
     * @param anchor  the anchor (<code>null</code> not permitted).
     * @param orientation  the orientation (<code>null</code> not permitted).
     * @param style  the chart style (<code>null</code> not permitted).
     * 
     * @return A color scale legend (possibly <code>null</code>). 
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
                this.barWidth, this.barLength, style.getLegendItemFont());
        element.setForegroundPaint(style.getLegendItemColor());
        element.setBackgroundPaint(style.getLegendItemBackgroundColor());
        element.setRefPoint(anchor.getRefPt());
        return element;
    }

    /**
     * Tests this builder for equality with an arbitrary object.
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
