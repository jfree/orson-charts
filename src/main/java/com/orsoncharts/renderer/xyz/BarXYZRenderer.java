/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of these source files is prohibited.
 * 
 */

package com.orsoncharts.renderer.xyz;

import java.awt.Color;
import java.io.Serializable;
import com.orsoncharts.axis.Axis3D;
import com.orsoncharts.Range;
import com.orsoncharts.data.DataUtils;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.renderer.Renderer3DChangeEvent;
import com.orsoncharts.util.ObjectUtils;

/**
 * A renderer that draws 3D bars on an {@link XYZPlot} using data from an
 * {@link XYZDataset}.  Here is a sample:
 * <div>
 * <object id="ABC" data="../../../../doc-files/XYZBarChart3DDemo1.svg"  
 * type="image/svg+xml" width="500" height="359"></object>
 * </div>
 * (refer to <code>XYZBarChart3DDemo1.java</code> for the code to generate 
 * the above chart).
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 */
public class BarXYZRenderer extends AbstractXYZRenderer implements XYZRenderer,
        Serializable {
 
    /** The base value (normally 0.0, but can be modified). */
    private double base;
    
    /** The width of the bars along the x-axis. */
    private double barXWidth;
    
    /** The width of the bars along the z-axis. */
    private double barZWidth;

    /** 
     * The color source used to fetch the color for the base of bars where
     * the actual base of the bar is *outside* of the current axis range 
     * (that is, the bar is "cropped").  If this is <code>null</code>, then 
     * the regular bar color is used.
     */
    private XYZColorSource baseColorSource;
    
    /**
     * The color source used to fetch the color for the top of bars where
     * the actual top of the bar is *outside* of the current axis range 
     * (that is, the bar is "cropped"). If this is <code>null</code> then the 
     * bar top is always drawn using the series paint.
     */
    private XYZColorSource topColorSource;

    /**
     * Creates a new default instance.
     */
    public BarXYZRenderer() {
        this.base = 0.0;
        this.barXWidth = 0.8;
        this.barZWidth = 0.8;
        this.baseColorSource = new StandardXYZColorSource(Color.WHITE);
        this.topColorSource = new StandardXYZColorSource(Color.BLACK);
        
    }
    
    /** 
     * Returns the value for the base of the bars.  The default is 
     * <code>0.0</code>.
     * 
     * @return The value for the base of the bars.
     */
    public double getBase() {
        return this.base;
    }
    
    /**
     * Sets the base value for the bars and sends a 
     * {@link Renderer3DChangeEvent} to all registered listeners.
     * 
     * @param base  the base. 
     */
    public void setBase(double base) {
        this.base = base;
        fireChangeEvent();
    }
    
    /**
     * Returns the width of the bars in the direction of the x-axis, in the
     * units of the x-axis.  The default value is <code>0.8</code>.
     * 
     * @return The width of the bars. 
     */
    public double getBarXWidth() {
        return this.barXWidth;
    }
    
    /**
     * Sets the width of the bars in the direction of the x-axis and sends a
     * {@link Renderer3DChangeEvent} to all registered listeners.
     * 
     * @param width  the width. 
     */
    public void setBarXWidth(double width) {
        this.barXWidth = width;
        fireChangeEvent();
    }

    /**
     * Returns the width of the bars in the direction of the z-axis, in the
     * units of the z-axis.  The default value is <code>0.8</code>.
     * 
     * @return The width of the bars. 
     */
    public double getBarZWidth() {
        return this.barZWidth;
    }
    
    /**
     * Sets the width of the bars in the direction of the z-axis and sends a
     * {@link Renderer3DChangeEvent} to all registered listeners.
     * 
     * @param width  the width. 
     */
    public void setBarZWidth(double width) {
        this.barZWidth = width;
        fireChangeEvent();
    }
 
    /**
     * Returns the object used to fetch the color for the base of bars
     * where the base of the bar is "cropped" (on account of the base value
     * falling outside of the bounds of the y-axis).  This is used to give a
     * visual indication to the end-user that the bar on display is cropped.
     * If this paint source is <code>null</code>, the regular series color
     * will be used for the top of the bars.
     * 
     * @return A paint source (possibly <code>null</code>).
     */
    public XYZColorSource getBaseColorSource() {
        return this.baseColorSource;
    }
    
    /**
     * Sets the object that determines the color to use for the base of bars
     * where the base value falls outside the axis range, and sends a
     * {@link Renderer3DChangeEvent} to all registered listeners.  If you set 
     * this to <code>null</code>, the regular series color will be used to draw
     * the base of the bar, but it will be harder for the end-user to know that 
     * only a section of the bar is visible in the chart.  Note that the 
     * default base paint source returns <code>Color.WHITE</code> always.
     * 
     * @param source  the source (<code>null</code> permitted).
     * 
     * @see #getBaseColorSource() 
     * @see #getTopColorSource()
     */
    public void setBaseColorSource(XYZColorSource source) {
        this.baseColorSource = source;
        fireChangeEvent();
    }
    
    /**
     * Returns the object used to fetch the color for the top of bars
     * where the top of the bar is "cropped" (on account of the data value
     * falling outside of the bounds of the y-axis).  This is used to give a
     * visual indication to the end-user that the bar on display is cropped.
     * If this paint source is <code>null</code>, the regular series color
     * will be used for the top of the bars.
     * 
     * @return A paint source (possibly <code>null</code>).
     */
    public XYZColorSource getTopColorSource() {
        return this.topColorSource;
    }
    
    /**
     * Sets the object used to fetch the color for the top of bars where the 
     * top of the bar is "cropped", and sends a {@link Renderer3DChangeEvent}
     * to all registered listeners.
     * 
     * @param source  the source (<code>null</code> permitted).
     * 
     * @see #getTopColorSource() 
     * @see #getBaseColorSource() 
     */
    public void setTopColorSource(XYZColorSource source) {
        this.topColorSource = source;
        fireChangeEvent();
    }

    /**
     * Returns the range that needs to be set on the x-axis in order for this
     * renderer to be able to display all the data in the supplied dataset.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * 
     * @return The range (<code>null</code> if there is no data in the dataset). 
     */
    @Override
    public Range findXRange(XYZDataset dataset) {
        // delegate argument check...
        Range xRange = DataUtils.findXRange(dataset);
        if (xRange == null) {
            return null;
        }
        double delta = this.barXWidth / 2.0;
        return new Range(xRange.getMin() - delta, xRange.getMax() + delta);
    }

    /**
     * Returns the range to use for the y-axis to ensure that all data values
     * are visible on the chart.  This method is overridden to ensure that the
     * base value is included.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * 
     * @return The range (<code>null</code> when there is no data). 
     */
    @Override
    public Range findYRange(XYZDataset dataset) {
        return DataUtils.findYRange(dataset, this.base);
    }
    
    /**
     * Returns the range to use for the z-axis to ensure that all data values
     * are visible on the chart.  This method is overridden to account for the
     * bar widths.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * 
     * @return The range (<code>null</code> when there is no data). 
     */
    @Override
    public Range findZRange(XYZDataset dataset) {
        Range zRange = DataUtils.findZRange(dataset);
        if (zRange == null) {
            return null;
        }
        double delta = this.barZWidth / 2.0;
        return new Range(zRange.getMin() - delta, zRange.getMax() + delta);
    }

    /**
     * Adds a single bar representing one item from the dataset.
     * 
     * @param dataset  the dataset.
     * @param series  the series index.
     * @param item  the item index.
     * @param world  the world used to model the 3D chart.
     * @param dimensions  the plot dimensions in 3D.
     * @param xOffset  the x-offset.
     * @param yOffset  the y-offset.
     * @param zOffset  the z-offset.
     */
    @Override
    public void composeItem(XYZDataset dataset, int series, int item, 
            World world, Dimension3D dimensions, double xOffset, double yOffset, 
            double zOffset) {

        XYZPlot plot = getPlot();
        Axis3D xAxis = plot.getXAxis();
        Axis3D yAxis = plot.getYAxis();
        Axis3D zAxis = plot.getZAxis();
        double x = dataset.getX(series, item);
        double y = dataset.getY(series, item);
        double z = dataset.getZ(series, item);
        double xdelta = this.barXWidth / 2.0;
        double zdelta = this.barZWidth / 2.0;

        double x0 = xAxis.getRange().peggedValue(x - xdelta);
        double x1 = xAxis.getRange().peggedValue(x + xdelta);
        double z0 = zAxis.getRange().peggedValue(z - zdelta);
        double z1 = zAxis.getRange().peggedValue(z + zdelta);
        if ((x1 <= x0) || (z1 <= z0)) {
            return;
        }
        double ylow = Math.min(this.base, y);
        double yhigh = Math.max(this.base, y);
        Range range = yAxis.getRange();
        if (!range.intersects(ylow, yhigh)) {
            return; // the bar is not visible for the given axis range
        }
        double ybase = range.peggedValue(ylow);
        double ytop = range.peggedValue(yhigh);
        boolean inverted = this.base > y;
        
        double wx0 = xAxis.translateToWorld(x0, dimensions.getWidth());
        double wx1 = xAxis.translateToWorld(x1, dimensions.getWidth());
        double wy0 = yAxis.translateToWorld(ybase, dimensions.getHeight());
        double wy1 = yAxis.translateToWorld(ytop, dimensions.getHeight());
        double wz0 = zAxis.translateToWorld(z0, dimensions.getDepth());
        double wz1 = zAxis.translateToWorld(z1, dimensions.getDepth());
    
        Color color = getColorSource().getColor(series, item);
        Color baseColor = null;
        if (this.baseColorSource != null && !range.contains(this.base)) {
            baseColor = this.baseColorSource.getColor(series, item);
        }
        if (baseColor == null) {
            baseColor = color;
        }

        Color topColor = null;
        if (this.topColorSource != null && !range.contains(y)) {
            topColor = this.topColorSource.getColor(series, item);
        }
        if (topColor == null) {
            topColor = color;
        }

        Object3D bar = Object3D.createBar(wx1 - wx0, wz1 - wz0, 
                ((wx0 + wx1) / 2.0) + xOffset, wy1 + yOffset, 
                ((wz0 + wz1) / 2.0) + zOffset, wy0 + yOffset, color, 
                baseColor, topColor, inverted);
        world.add(bar);
    }

    /**
     * Tests this renderer for equality with an arbitrary object.
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
        if (!(obj instanceof BarXYZRenderer)) {
            return false;
        }
        BarXYZRenderer that = (BarXYZRenderer) obj;
        if (this.base != that.base) {
            return false;
        }
        if (this.barXWidth != that.barXWidth) {
            return false;
        }
        if (this.barZWidth != that.barZWidth) {
            return false;
        }
        if (!ObjectUtils.equals(this.baseColorSource, that.baseColorSource)) {
            return false;
        }
        if (!ObjectUtils.equals(this.topColorSource, that.topColorSource)) {
            return false;
        }
        return super.equals(obj);
    }

}
