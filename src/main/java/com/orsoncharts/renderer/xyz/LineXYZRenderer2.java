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
 * A renderer that draws 3D lines on an {@link XYZPlot} using data from an
 * {@link XYZDataset}.  Here is a sample:
 * <div>
 * <object id="ABC" data="../../../../doc-files/XYZLineChart3DDemo1.svg"  
 * type="image/svg+xml" width="500" height="359"></object>
 * </div>
 * (refer to <code>XYZLineChart3DDemo1.java</code> for the code to generate 
 * the above chart).
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 */
@SuppressWarnings("serial")
public class LineXYZRenderer2 extends AbstractXYZRenderer implements XYZRenderer,
        Serializable {

    @Override
    public void composeItem(XYZDataset dataset, int series, int item, World world, Dimension3D dimensions, double xOffset, double yOffset, double zOffset) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 
//    /** The width of the bars along the z-axis. */
//    private double lineWidth;
//
//    /**
//     * Creates a new default instance.
//     */
//    public LineXYZRenderer() {
//        this.lineWidth = 0.8;
//    }
//    
//    /**
//     * Returns the width of the lines in the direction of the z-axis, in the
//     * units of the z-axis.  The default value is <code>0.8</code>.
//     * 
//     * @return The width of the bars. 
//     */
//    public double getLineWidth() {
//        return this.lineWidth;
//    }
//    
//    /**
//     * Sets the width of the lines in the direction of the z-axis and sends a
//     * {@link Renderer3DChangeEvent} to all registered listeners.
//     * 
//     * @param width  the width. 
//     */
//    public void setLineWidth(double width) {
//        this.lineWidth = width;
//        fireChangeEvent(true);
//    }
//
//    
//    /**
//     * Returns the range to use for the z-axis to ensure that all data values
//     * are visible on the chart.  This method is overridden to account for the
//     * bar widths.
//     * 
//     * @param dataset  the dataset (<code>null</code> not permitted).
//     * 
//     * @return The range (<code>null</code> when there is no data). 
//     */
//    @Override
//    public Range findZRange(XYZDataset dataset) {
//        Range zRange = DataUtils.findZRange(dataset);
//        if (zRange == null) {
//            return null;
//        }
//        double delta = this.lineWidth / 2.0;
//        return new Range(zRange.getMin() - delta, zRange.getMax() + delta);
//    }
//
//    /**
//     * Adds a single line representing one item from the dataset.
//     * 
//     * @param dataset  the dataset.
//     * @param series  the series index.
//     * @param item  the item index.
//     * @param world  the world used to model the 3D chart.
//     * @param dimensions  the plot dimensions in 3D.
//     * @param xOffset  the x-offset.
//     * @param yOffset  the y-offset.
//     * @param zOffset  the z-offset.
//     */
//    @Override
//    public void composeItem(XYZDataset dataset, int series, int item, 
//            World world, Dimension3D dimensions, double xOffset, double yOffset, 
//            double zOffset) {
//
//        XYZPlot plot = getPlot();
//        Axis3D xAxis = plot.getXAxis();
//        Axis3D yAxis = plot.getYAxis();
//        Axis3D zAxis = plot.getZAxis();
//        double x = dataset.getX(series, item);
//        double y = dataset.getY(series, item);
//        double z = dataset.getZ(series, item);
//        double zdelta = this.lineWidth / 2.0;
//
//        double x0 = xAxis.getRange().peggedValue(x - xdelta);
//        double x1 = xAxis.getRange().peggedValue(x + xdelta);
//        double z0 = zAxis.getRange().peggedValue(z - zdelta);
//        double z1 = zAxis.getRange().peggedValue(z + zdelta);
//        if ((x1 <= x0) || (z1 <= z0)) {
//            return;
//        }
//        double ylow = Math.min(this.base, y);
//        double yhigh = Math.max(this.base, y);
//        Range range = yAxis.getRange();
//        if (!range.intersects(ylow, yhigh)) {
//            return; // the bar is not visible for the given axis range
//        }
//        double ybase = range.peggedValue(ylow);
//        double ytop = range.peggedValue(yhigh);
//        boolean inverted = this.base > y;
//        
//        double wx0 = xAxis.translateToWorld(x0, dimensions.getWidth());
//        double wx1 = xAxis.translateToWorld(x1, dimensions.getWidth());
//        double wy0 = yAxis.translateToWorld(ybase, dimensions.getHeight());
//        double wy1 = yAxis.translateToWorld(ytop, dimensions.getHeight());
//        double wz0 = zAxis.translateToWorld(z0, dimensions.getDepth());
//        double wz1 = zAxis.translateToWorld(z1, dimensions.getDepth());
//    
//        Color color = getColorSource().getColor(series, item);
//
//        Object3D bar = Object3D.createBar(wx1 - wx0, wz1 - wz0, 
//                ((wx0 + wx1) / 2.0) + xOffset, wy1 + yOffset, 
//                ((wz0 + wz1) / 2.0) + zOffset, wy0 + yOffset, color, 
//                baseColor, topColor, inverted);
//        world.add(bar);
//    }
//
//    /**
//     * Tests this renderer for equality with an arbitrary object.
//     * 
//     * @param obj  the object (<code>null</code> permitted).
//     * 
//     * @return A boolean. 
//     */
//    @Override
//    public boolean equals(Object obj) {
//        if (obj == this) {
//            return true;
//        }
//        if (!(obj instanceof BarXYZRenderer)) {
//            return false;
//        }
//        BarXYZRenderer that = (BarXYZRenderer) obj;
//        if (this.base != that.base) {
//            return false;
//        }
//        if (this.barXWidth != that.barXWidth) {
//            return false;
//        }
//        if (this.barZWidth != that.barZWidth) {
//            return false;
//        }
//        if (!ObjectUtils.equals(this.baseColorSource, that.baseColorSource)) {
//            return false;
//        }
//        if (!ObjectUtils.equals(this.topColorSource, that.topColorSource)) {
//            return false;
//        }
//        return super.equals(obj);
//    }

}

