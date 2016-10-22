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

import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.axis.Axis3D;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.data.xyz.XYZItemKey;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.Offset3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.renderer.Renderer3DChangeEvent;
import com.orsoncharts.util.ArgChecks;

/**
 * A renderer for 3D scatter plots.  This renderer is used with an
 * {@link XYZPlot} and any {@link XYZDataset} instance.  Here is a sample:
 * <div>
 * <object id="ABC" data="../../../../doc-files/ScatterPlot3DDemo2.svg"  
 * type="image/svg+xml" width="500" height="359"></object>
 * </div>
 * (refer to {@code ScatterPlot3DDemo2.java} for the code to generate 
 * the above chart).
 * <br><br>
 * TIP: to create a chart using this renderer, you can use the
 * {@link Chart3DFactory#createScatterChart(String, String, XYZDataset, String, String, String)}
 * method.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 */
@SuppressWarnings("serial")
public class ScatterXYZRenderer extends AbstractXYZRenderer 
        implements XYZRenderer, Serializable {

    /** The size of the cubes to render for each data point (in world units). */
    private double size;
    
    /** The offsets for item labels, as a percentage of the size. */
    private Offset3D itemLabelOffsetPercent;
    
    /**
     * Creates a new instance with default attribute values.
     */
    public ScatterXYZRenderer() {
        super();
        this.size = 0.10;
        this.itemLabelOffsetPercent = new Offset3D(0.0, 1.0, 0.0);
    }

    /**
     * Returns the size of the cubes (in world units) used to display each data
     * item.  The default value is {@code 0.10}.
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
        fireChangeEvent(true);
    }
    
    /**
     * Returns the item label offsets.
     * 
     * @return The item label offsets (never {@code null}).
     * 
     * @since 1.3
     */
    public Offset3D getItemLabelOffsetPercent() {
        return this.itemLabelOffsetPercent;
    }
    
    /**
     * Sets the item label offsets and sends a change event to all registered
     * listeners.
     * 
     * @param offset  the new offset ({@code null} not permitted).
     * 
     * @since 1.3
     */
    public void setItemLabelOffsetPercent(Offset3D offset) {
        ArgChecks.nullNotPermitted(offset, "offset");
        this.itemLabelOffsetPercent = offset;
        fireChangeEvent(true);
    }
    
    /**
     * Constructs and places one item from the specified dataset into the given 
     * world.  The {@link XYZPlot} class will iterate over its dataset and
     * and call this method for each item (in other words, you don't need to 
     * call this method directly).
     * 
     * @param dataset the dataset ({@code null} not permitted).
     * @param series  the series index.
     * @param item  the item index.
     * @param world  the world ({@code null} not permitted).
     * @param dimensions  the dimensions ({@code null} not permitted).
     * @param xOffset  the x-offset.
     * @param yOffset  the y-offset.
     * @param zOffset  the z-offset.
     */
    @Override
    @SuppressWarnings("unchecked")
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
        Color color = getColorSource().getColor(series, item);
        double cx = (xmax + xmin) / 2.0 + xOffset;
        double cy = (ymax + ymin) / 2.0 + yOffset;
        double cz = (zmax + zmin) / 2.0 + zOffset;
        Object3D cube = Object3D.createBox(cx, xmax - xmin, cy, ymax - ymin, 
                cz, zmax - zmin, color);
        Comparable<?> seriesKey = dataset.getSeriesKey(series);
        XYZItemKey itemKey = new XYZItemKey(seriesKey, item);
        cube.setProperty(Object3D.ITEM_KEY, itemKey);
        world.add(cube);
        
        if (getItemLabelGenerator() != null) {
            String label = getItemLabelGenerator().generateItemLabel(dataset,
                    seriesKey, item);
            if (label != null) {
                double dx = this.itemLabelOffsetPercent.getDX() * this.size;
                double dy = this.itemLabelOffsetPercent.getDY() * this.size;
                double dz = this.itemLabelOffsetPercent.getDZ() * this.size;
                Object3D labelObj = Object3D.createLabelObject(label, 
                        getItemLabelFont(), getItemLabelColor(), 
                        getItemLabelBackgroundColor(), cx + dx, cy + dy, 
                        cz + dz, false, true);
                labelObj.setProperty(Object3D.ITEM_KEY, itemKey);
                world.add(labelObj);
            }
        }

    }

    /**
     * Tests this renderer for equality with an arbitrary object.
     * 
     * @param obj  the object to test ({@code null} permitted).
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
        if (!this.itemLabelOffsetPercent.equals(that.itemLabelOffsetPercent)) {
            return false;
        }
        return super.equals(obj);
    }
}
