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
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.Dot3D;
import com.orsoncharts.graphics3d.World;

/**
 * A renderer for 3D scatter plots.  // FIXME : this doesn't actually work at the moment
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 */
public class FastScatterXYZRenderer extends AbstractXYZRenderer 
        implements XYZRenderer, Serializable {

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
        Color color = getColorSource().getColor(series, item);
        Dot3D point = new Dot3D((float) (x + xOffset), (float) (y + yOffset), 
                (float) (z + zOffset), color);
        world.add(point);
    }

}

