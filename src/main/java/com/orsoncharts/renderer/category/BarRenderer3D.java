/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer.category;

import java.awt.Color;
import com.orsoncharts.axis.Axis3D;
import com.orsoncharts.axis.CategoryAxis3D;
import com.orsoncharts.axis.Range;
import com.orsoncharts.data.CategoryDataset3D;
import com.orsoncharts.data.DataUtilities;
import com.orsoncharts.data.Values3D;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.renderer.Renderer3DChangeListener;

/**
 * A bar renderer in 3D
 */
public class BarRenderer3D extends AbstractCategoryRenderer3D 
                implements CategoryRenderer3D {

    private double base;
    
    private double barThickness;
    
    /**
     * Creates a default instance.
     */
    public BarRenderer3D() {
        this.base = 0.0;
        this.barThickness = 0.8;    
    }
    
    /**
     * Returns the bar thickness.  Normally bars will be created at unit 
     * intervals, so a bar thickness of 1.0 will result in the bars touching
     * each other.  For lower values, there will be gaps between the bars.
     * The default value is <code>0.8</code>.
     * 
     * @return The bar thickness. 
     */
    public double getBarThickness() {
        return this.barThickness;
    }
    
    /**
     * Sets the bar thickness and fires a renderer change event.
     * 
     * @param thickness  the new thickness.
     */
    public void setBarThickness(double thickness) {
        this.barThickness = thickness;
        // TODO : change event
    }

    @Override
    public Range findValueRange(Values3D data) {
        return DataUtilities.findValueRange(data, this.base);
    }

    @Override
    public void composeItem(World world, Dimension3D dimensions, 
            CategoryDataset3D dataset, int series, int row, int column, 
            double xOffset, double yOffset, double zOffset) {
        
        double value = dataset.getDoubleValue(series, row, column);
        if (Double.isNaN(value)) {
            return;
        }

        CategoryPlot3D plot = getPlot();
        CategoryAxis3D rowAxis = plot.getRowAxis();
        CategoryAxis3D columnAxis = plot.getColumnAxis();
        Axis3D valueAxis = plot.getValueAxis();
   
        Comparable rowKey = dataset.getRowKey(row);
        Comparable columnKey = dataset.getColumnKey(column);
        double rowValue = rowAxis.getCategoryValue(rowKey);
        double columnValue = columnAxis.getCategoryValue(columnKey);

        double xx = columnAxis.translateToWorld(columnValue, dimensions.getWidth());
        double yy = valueAxis.translateToWorld(value, dimensions.getHeight());
        double zz = rowAxis.translateToWorld(rowValue, dimensions.getDepth());

        double zero = valueAxis.translateToWorld(this.base, 
                dimensions.getHeight());
    
        Color color = getPaintSource().getPaint(series, row, column);
        Object3D bar = Object3D.createBar(this.barThickness, xx + xOffset, 
                yy + yOffset, zz + zOffset, zero + yOffset, color);
        world.add(bar);
    }

}
