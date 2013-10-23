/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer.xyz;

import java.awt.Color;
import com.orsoncharts.axis.Axis3D;
import com.orsoncharts.Range;
import com.orsoncharts.data.DataUtils;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.renderer.Renderer3DChangeEvent;

/**
 * A renderer that draws 3D bars on an {@link XYZPlot}.
 */
public class BarXYZRenderer extends AbstractXYZRenderer implements XYZRenderer {
 
    /** The base value (normally 0.0, but can be modified). */
    private double base;
    
    /** The width of the bars along the x-axis. */
    private double barXWidth;
    
    /** The width of the bars along the z-axis. */
    private double barZWidth;
    
    /**
     * Creates a new default instance.
     */
    public BarXYZRenderer() {
        this.base = 0.0;
        this.barXWidth = 0.8;
        this.barZWidth = 0.8;
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
        if (!range.containsInterval(ylow, yhigh)) {
            return;
        }
        double ybase = range.peggedValue(ylow);
        double ytop = range.peggedValue(yhigh);
        boolean inverted = ybase > ytop;
        
        double wx0 = xAxis.translateToWorld(x0, dimensions.getWidth());
        double wx1 = xAxis.translateToWorld(x1, dimensions.getWidth());
        double wy0 = yAxis.translateToWorld(this.base, dimensions.getHeight());
        double wy1 = yAxis.translateToWorld(y, dimensions.getHeight());
        double wz0 = zAxis.translateToWorld(z0, dimensions.getDepth());
        double wz1 = zAxis.translateToWorld(z1, dimensions.getDepth());
    
        Color color = getPaintSource().getPaint(series, item);
        Color baseColor = null;
        if (this.basePaintSource != null && !range.contains(this.base)) {
            baseColor = this.basePaintSource.getPaint(series, item);
        }
        if (baseColor == null) {
            baseColor = color;
        }

        Color topColor = null;
        if (this.topPaintSource != null && !range.contains(ytop)) {
            topColor = this.topPaintSource.getPaint(series, item);
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

    private XYZPaintSource basePaintSource = null;
    private XYZPaintSource topPaintSource = null;
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
        return super.equals(obj);
    }

}
