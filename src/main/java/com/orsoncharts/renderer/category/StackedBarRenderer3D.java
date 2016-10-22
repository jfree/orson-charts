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

package com.orsoncharts.renderer.category;

import com.orsoncharts.Range;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.data.DataUtils;
import com.orsoncharts.data.Values3D;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.data.KeyedValues3DItemKey;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.Offset3D;
import com.orsoncharts.label.ItemLabelPositioning;

/**
 * A renderer that can be used with the {@link CategoryPlot3D} class to create
 * 3D stacked bar charts from data in a {@link CategoryDataset3D}.  The 
 * {@code createStackedBarChart()} method in the {@link Chart3DFactory} 
 * class will construct a chart that uses this renderer.  Here is a sample:
 * <div>
 * <object id="ABC" data="../../../../doc-files/StackedBarChart3DDemo1.svg"  
 * type="image/svg+xml" width="500" height="359"></object>
 * </div>
 * (refer to {@code StackedBarChart3DDemo1.java} for the code to generate 
 * the above chart).
 * <br><br> 
 * There is a factory method to create a chart using this renderer - see
 * {@link Chart3DFactory#createStackedBarChart(String, String, CategoryDataset3D, String, String, String)}.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 */
@SuppressWarnings("serial")
public class StackedBarRenderer3D extends BarRenderer3D {

    /**
     * Creates a default constructor.
     */
    public StackedBarRenderer3D() {
        super();
        setItemLabelPositioning(ItemLabelPositioning.FRONT_AND_BACK);
        setItemLabelOffsets(new Offset3D(0.0, 0.0, -1.0));
    }
    
    /**
     * Returns the range of values that will be required on the value axis
     * to see all the data from the dataset.  We override the method to 
     * account for the bars from each series being stacked on top of one 
     * another.
     * 
     * @param data  the data ({@code null} not permitted).
     * 
     * @return The range (possibly {@code null}) 
     */
    @Override
    public Range findValueRange(Values3D<? extends Number> data) {
        return DataUtils.findStackedValueRange(data);
    }
    
    /**
     * Constructs and places one item from the specified dataset into the given 
     * world.  This method will be called by the {@link CategoryPlot3D} class
     * while iterating over the items in the dataset.
     * 
     * @param dataset  the dataset ({@code null} not permitted).
     * @param series  the series index.
     * @param row  the row index.
     * @param column  the column index.
     * @param world  the world ({@code null} not permitted).
     * @param dimensions  the plot dimensions ({@code null} not permitted).
     * @param xOffset  the x-offset.
     * @param yOffset  the y-offset.
     * @param zOffset  the z-offset.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void composeItem(CategoryDataset3D dataset, int series, int row, 
            int column, World world, Dimension3D dimensions,  
            double xOffset, double yOffset, double zOffset) {
        
        double value = dataset.getDoubleValue(series, row, column);
        if (Double.isNaN(value)) {
            return;
        }        
        double[] stack = DataUtils.stackSubTotal(dataset, getBase(), series,
                row, column);
        double lower = stack[1];
        if (value < 0.0) {
            lower = stack[0];
        }
        double upper = lower + value;
        composeItem(upper, lower, dataset, series, row, column, world, 
                dimensions, xOffset, yOffset, zOffset);
        
    }
    
    @Override
    protected void drawItemLabels(World world, CategoryDataset3D dataset, 
            KeyedValues3DItemKey itemKey, double xw, double yw, double zw, 
            double basew, boolean inverted) {
        ItemLabelPositioning positioning = getItemLabelPositioning();
        if (getItemLabelGenerator() != null) {
            String label = getItemLabelGenerator().generateItemLabel(dataset, 
                   itemKey.getSeriesKey(), itemKey.getRowKey(), 
                   itemKey.getColumnKey());
            if (label != null) {
                Dimension3D dimensions = getPlot().getDimensions();
                double dx = getItemLabelOffsets().getDX();
                double dy = getItemLabelOffsets().getDY() 
                        * dimensions.getHeight();
                double dz = getItemLabelOffsets().getDZ() * getBarZWidth();
                if (positioning.equals(ItemLabelPositioning.CENTRAL)) {
                    double yy = yw;
                    if (inverted) {
                        yy = basew;
                        dy = -dy;
                    }
                    Object3D labelObj = Object3D.createLabelObject(label, 
                            getItemLabelFont(), getItemLabelColor(), 
                            getItemLabelBackgroundColor(), xw + dx, 
                            yy + dy, zw, false, true);
                    labelObj.setProperty(Object3D.ITEM_KEY, itemKey);
                    world.add(labelObj);
                } else if (positioning.equals(
                        ItemLabelPositioning.FRONT_AND_BACK)) {
                    double yy = (yw + basew) / 2.0;
                    Object3D labelObj1 = Object3D.createLabelObject(label, 
                            getItemLabelFont(), getItemLabelColor(), 
                            getItemLabelBackgroundColor(), xw + dx, 
                            yy + dy, zw + dz, false, false);
                    labelObj1.setProperty(Object3D.ITEM_KEY, itemKey);
                    world.add(labelObj1);
                    Object3D labelObj2 = Object3D.createLabelObject(label, 
                            getItemLabelFont(), getItemLabelColor(), 
                            getItemLabelBackgroundColor(), xw + dx, 
                            yy + dy, zw - dz, true, false);
                    labelObj2.setProperty(Object3D.ITEM_KEY, itemKey);
                    world.add(labelObj2);
                }
            }
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
        if (!(obj instanceof StackedBarRenderer3D)) {
            return false;
        }
        return super.equals(obj);
    }
    
}