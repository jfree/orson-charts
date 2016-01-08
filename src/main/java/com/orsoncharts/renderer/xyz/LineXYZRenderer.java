/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2016, by Object Refinery Limited.  All rights reserved.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 * 
 * If you do not wish to be bound by the terms of the GPL, an alternative
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts home page:
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
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
import com.orsoncharts.graphics3d.LineObject3D;
import com.orsoncharts.graphics3d.World;

/**
 * A renderer that draws 3D lines on an {@link XYZPlot} using data from an
 * {@link XYZDataset}.  Here is a sample:
 * <div>
 * <object id="ABC" data="../../../../doc-files/XYZLineChart3DDemo1.svg"  
 * type="image/svg+xml" width="500" height="359"></object>
 * </div>
 * (refer to {@code XYZLineChart3DDemo1.java} for the code to generate 
 * the above chart).
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 * 
 * @since 1.5
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

        if (item == 0) { // we are connecting lines between points, so there
            return;      // is nothing to do for item 0
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
        Line3D line = new Line3D(wx0, wy0, wz0, wx1, wy1, wz1);
        line = Line3D.cropLineToAxisAlignedBoundingBox(line, 0, 
                dimensions.getWidth(), 0, dimensions.getHeight(), 0,
                dimensions.getDepth());
        if (line != null) {
            Color color = getColorSource().getColor(series, item);
            LineObject3D line3D = new LineObject3D(
                    (float) (line.getStart().getX() + xOffset), 
                    (float) (line.getStart().getY() + yOffset), 
                    (float) (line.getStart().getZ() + zOffset), 
                    (float) (line.getEnd().getX() + xOffset), 
                    (float) (line.getEnd().getY() + yOffset), 
                    (float) (line.getEnd().getZ() + zOffset), color);
            world.add(line3D);
        }
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

