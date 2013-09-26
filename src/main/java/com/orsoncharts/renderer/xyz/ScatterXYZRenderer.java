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
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.World;

/**
 * A renderer for 3D scatter plots.
 */
public class ScatterXYZRenderer extends AbstractXYZRenderer 
        implements XYZRenderer {

    private double size;
    
    /**
     * Creates a new instance.
     */
    public ScatterXYZRenderer() {
        super();
        this.size = 0.05;
    }

    /**
     * Returns the size of the cubes used to display each data item.  The 
     * default value is 0.05.
     * 
     * @return The size.
     */
    public double getSize() {
        return this.size;
    }
    
    /**
     * Sets the size of the cubes used to represent each data item.
     * 
     * @param size  the size.
     */
    public void setSize(double size) {
        this.size = size; 
    }
    
    @Override
    public void composeItem(XYZDataset dataset, int series, int item, 
        World world, Dimension3D dimensions, double xOffset, double yOffset, 
        double zOffset) {
    
        double x = dataset.getX(series, item);
        double y = dataset.getY(series, item);
        double z = dataset.getZ(series, item);
    
        XYZPlot plot = getPlot();
        Axis3D xAxis = plot.getXAxis();
        Axis3D yAxis = plot.getYAxis();
        Axis3D zAxis = plot.getZAxis();
        if (!xAxis.getRange().contains(x) || !yAxis.getRange().contains(y) 
              || !zAxis.getRange().contains(z)) {
            return;
        }
    
        Dimension3D dim = plot.getDimensions();
        double xx = xAxis.translateToWorld(x, dim.getWidth());
        double yy = yAxis.translateToWorld(y, dim.getHeight());
        double zz = zAxis.translateToWorld(z, dim.getDepth());
    
        Paint paint = getPaintSource().getPaint(series, item);
        Object3D cube = Object3D.createCube(this.size, xx + xOffset, 
                yy + yOffset, zz + zOffset, (Color) paint);
        world.add(cube);
    }

}
