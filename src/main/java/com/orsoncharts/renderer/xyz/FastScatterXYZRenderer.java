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
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.Dot3D;
import com.orsoncharts.graphics3d.World;

/**
 * A renderer for 3D scatter plots.
 */
public class FastScatterXYZRenderer extends AbstractXYZRenderer 
        implements XYZRenderer {

    /**
     * Creates a new instance.
     */
    public FastScatterXYZRenderer() {
        super();
    }

    /**
     * Constructs and places one item from the specified dataset into the given 
     * world.
     * 
     * @param dataset the dataset.
     * @param series  the series index.
     * @param item  the item index.
     * @param world  the world.
     * @param dimensions  the dimensions.
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
        Paint paint = getPaintSource().getPaint(series, item);
        Dot3D point = new Dot3D((float) (x + xOffset), (float) (y + yOffset), 
                (float) (z + zOffset), (Color) paint);
        world.add(point);
    }

}

