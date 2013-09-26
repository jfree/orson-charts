/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer.xyz;

import java.awt.Color;
import java.awt.Paint;
import com.orsoncharts.axis.Axis3D;
import com.orsoncharts.axis.Range;
import com.orsoncharts.data.DataUtilities;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.World;

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
    }

    @Override
    public Range findYRange(XYZDataset dataset) {
        return DataUtilities.findYRange(dataset, 0.0);
    }
    
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

        double zero = yAxis.translateToWorld(0.0, dimensions.getHeight());
    
        Object3D bar = Object3D.createBar(0.8, wx + xOffset, wy + yOffset, 
                wz + zOffset, zero + yOffset, (Color) paint);
        world.add(bar);
    }

}

