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
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.Line3D;
import com.orsoncharts.graphics3d.World;

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
public class LineXYZRenderer extends AbstractXYZRenderer implements XYZRenderer,
        Serializable {

    /**
     * Creates a new default instance.
     */
    public LineXYZRenderer() {
    }

    /**
     * Adds a single line representing one item from the dataset.
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

        if (item == 0) {
            return;
        }
        XYZPlot plot = getPlot();
        Axis3D xAxis = plot.getXAxis();
        Axis3D yAxis = plot.getYAxis();
        Axis3D zAxis = plot.getZAxis();
        double x1 = dataset.getX(series, item);
        double y1 = dataset.getY(series, item);
        double z1 = dataset.getZ(series, item);

        double x0 = dataset.getX(series, item - 1);
        double y0 = dataset.getY(series, item - 1);
        double z0 = dataset.getZ(series, item - 1);
        
        double wx0 = xAxis.translateToWorld(x0, dimensions.getWidth());
        double wx1 = xAxis.translateToWorld(x1, dimensions.getWidth());
        double wy0 = yAxis.translateToWorld(y0, dimensions.getHeight());
        double wy1 = yAxis.translateToWorld(y1, dimensions.getHeight());
        double wz0 = zAxis.translateToWorld(z0, dimensions.getDepth());
        double wz1 = zAxis.translateToWorld(z1, dimensions.getDepth());
    
        Color color = getColorSource().getColor(series, item);
        Line3D line = new Line3D((float) (wx0 + xOffset), 
                (float) (wy0 + yOffset), (float) (wz0 + zOffset), 
                (float) (wx1 + xOffset), (float) (wy1 + yOffset), 
                (float) (wz1 + zOffset), color);
        world.add(line);
    }

    /**
     * Tests this renderer for equality with an arbitrary object.
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
        if (!(obj instanceof LineXYZRenderer)) {
            return false;
        }
        LineXYZRenderer that = (LineXYZRenderer) obj;
        return super.equals(obj);
    }

}

