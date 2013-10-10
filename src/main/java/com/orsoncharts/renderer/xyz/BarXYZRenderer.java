/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer.xyz;

import java.awt.Color;
import java.awt.Paint;
import com.orsoncharts.axis.Axis3D;
import com.orsoncharts.Range;
import com.orsoncharts.data.DataUtilities;
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
 
    private double base;
    
    private double barXWidth;
    
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
        Range xRange = DataUtilities.findXRange(dataset);
        if (xRange == null) {
            return null;
        }
        double delta = this.barXWidth / 2.0;
        return new Range(xRange.getMin() - delta, xRange.getMax() + delta);
    }

    @Override
    public Range findYRange(XYZDataset dataset) {
        return DataUtilities.findYRange(dataset, this.base);
    }
    
    @Override
    public Range findZRange(XYZDataset dataset) {
        Range zRange = DataUtilities.findZRange(dataset);
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
        Paint paint = getPaintSource().getPaint(series, item);

        double wx = xAxis.translateToWorld(x, dimensions.getWidth());
        double wy = yAxis.translateToWorld(y, dimensions.getHeight());
        double wz = zAxis.translateToWorld(z, dimensions.getDepth());
        double wxw = xAxis.translateToWorld(this.barXWidth, 
                dimensions.getWidth());
        double wzw = zAxis.translateToWorld(this.barZWidth, 
                dimensions.getDepth());
        double zero = yAxis.translateToWorld(this.base, dimensions.getHeight());
    
        Object3D bar = Object3D.createBar(wxw, wzw, wx + xOffset, wy + yOffset, 
                wz + zOffset, zero + yOffset, (Color) paint);
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
        return super.equals(obj);
    }

}
