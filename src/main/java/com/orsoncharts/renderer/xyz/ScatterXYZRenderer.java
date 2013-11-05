/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer.xyz;

import java.awt.Color;
import java.io.Serializable;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.axis.Axis3D;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.renderer.Renderer3DChangeEvent;
import com.orsoncharts.util.ArgChecks;

/**
 * A renderer for 3D scatter plots.  This renderer is used with an
 * {@link XYZPlot} and any {@link XYZDataset} instance.
 * <br><br>
 * TIP: to create a chart using this renderer, you can use the
 * {@link Chart3DFactory#createScatterPlot(String, XYZDataset, String, String, String)}
 * method.
 */
public class ScatterXYZRenderer extends AbstractXYZRenderer 
        implements XYZRenderer, Serializable {

    /** The size of the cubes to render for each data point (in world units). */
    private double size;
    
    /**
     * Creates a new instance with default attribute values.
     */
    public ScatterXYZRenderer() {
        super();
        this.size = 0.10;
    }

    /**
     * Returns the size of the cubes (in world units) used to display each data
     * item.  The default value is <code>0.10</code>.
     * 
     * @return The size (in world units).
     */
    public double getSize() {
        return this.size;
    }
    
    /**
     * Sets the size (in world units) of the cubes used to represent each data 
     * item and sends a {@link Renderer3DChangeEvent} to all registered 
     * listeners.
     * 
     * @param size  the size (in world units, must be positive).
     */
    public void setSize(double size) {
        ArgChecks.positiveRequired(size, "size");
        this.size = size;
        fireChangeEvent();
    }
    
    /**
     * Constructs and places one item from the specified dataset into the given 
     * world.  The {@link XYZPlot} class will iterate over its dataset and
     * and call this method for each item (in other words, you don't need to 
     * call this method directly).
     * 
     * @param dataset the dataset (<code>null</code> not permitted).
     * @param series  the series index.
     * @param item  the item index.
     * @param world  the world (<code>null</code> not permitted).
     * @param dimensions  the dimensions (<code>null</code> not permitted).
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
    
        double delta = this.size / 2.0;
        Dimension3D dim = plot.getDimensions();
        double xx = xAxis.translateToWorld(x, dim.getWidth());
        double xmin = Math.max(0.0, xx - delta);
        double xmax = Math.min(dim.getWidth(), xx + delta);
        double yy = yAxis.translateToWorld(y, dim.getHeight());
        double ymin = Math.max(0.0, yy - delta);
        double ymax = Math.min(dim.getHeight(), yy + delta);
        double zz = zAxis.translateToWorld(z, dim.getDepth());
        double zmin = Math.max(0.0, zz - delta);
        double zmax = Math.min(dim.getDepth(), zz + delta);
        if ((xmin >= xmax) || (ymin >= ymax) || (zmin >= zmax)) {
            return;
        }
        Color paint = getPaintSource().getPaint(series, item);
        Object3D cube = Object3D.createBox((xmax + xmin) / 2.0 + xOffset, xmax - xmin,
                (ymax + ymin) / 2.0 + yOffset, ymax - ymin,
                (zmax + zmin) / 2.0 + zOffset, zmax - zmin, paint);
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
