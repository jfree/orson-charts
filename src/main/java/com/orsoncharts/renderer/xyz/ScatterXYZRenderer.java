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
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.renderer.Renderer3DChangeEvent;

/**
 * A renderer for 3D scatter plots.
 */
public class ScatterXYZRenderer extends AbstractXYZRenderer 
        implements XYZRenderer {

    /** The size of the cubes to render for each data point. */
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
     * default value is <code>0.05</code>.
     * 
     * @return The size.
     */
    public double getSize() {
        return this.size;
    }
    
    /**
     * Sets the size of the cubes used to represent each data item and sends
     * a {@link Renderer3DChangeEvent} to all registered listeners.
     * 
     * @param size  the size.
     */
    public void setSize(double size) {
        this.size = size;
        fireChangeEvent();
    }
    
    /**
     * Constructs and places one item from the specified dataset into the given 
     * world.
     * 
     * @param dataset the dataset.
     * @param series  the series index.
     * @param item  the item index.
     * @param world  the world.
     * @param xOffset  the x-offset.
     * @param yOffset  the y-offset.
     * @param zOffset  the z-offset.
     */
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

    /**
     * Tests this renderer for equality with an arbitrary object.
     * 
     * @param obj  the object to test (<code>null</code> permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ScatterXYZRenderer)) {
            return false;
        }
        ScatterXYZRenderer that = (ScatterXYZRenderer) obj;
        if (this.size != that.size) {
            return false;
        }
        return super.equals(obj);
    }
}
