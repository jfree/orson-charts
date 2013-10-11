/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer.category;

import java.awt.Color;
import java.io.Serializable;
import com.orsoncharts.axis.Axis3D;
import com.orsoncharts.axis.CategoryAxis3D;
import com.orsoncharts.Range;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.data.DataUtils;
import com.orsoncharts.data.Values3D;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.plot.CategoryPlot3D;

/**
 * A bar renderer for use with a {@link CategoryPlot3D}.
 * 
 * <div>
 * <object id="ABC" data="doc-files/BarRenderer3D.svg"  type="image/svg+xml" 
 * width="700" height="400"> 
 * </object>
 * </div>
 */
public class BarRenderer3D extends AbstractCategoryRenderer3D 
                implements Serializable {

    /** The base of the bars - defaults to 0.0. */
    private double base;
    
    /** The bar width as a percentage of the column width. */
    private double barXWidth;
    
    /** The bar width as a percentage of the row width. */
    private double barZWidth;
    
    /**
     * Creates a new renderer with default attribute values.
     */
    public BarRenderer3D() {
        this.base = 0.0;
        this.barXWidth = 0.8;
        this.barZWidth = 0.5;
    }
    
    /**
     * Returns the base value for the bars.  The default value 
     * is <code>0.0</code>.
     * 
     * @return The base value for the bars.
     * 
     * @see #setBase(double) 
     */
    public double getBase() {
        return this.base;
    }
    
    /**
     * Sets the base value for the bars and fires a 
     * {@link com.orsoncharts.renderer.Renderer3DChangeEvent}.
     * 
     * @param base  the new base value.
     * 
     * @see #getBase() 
     */
    public void setBase(double base) {
        this.base = base;
        fireChangeEvent();
    }
    
    /**
     * Returns the bar width as a percentage of the column width.
     * The default value is <code>0.8</code>.
     * 
     * @return The bar width. 
     */
    public double getBarXWidth() {
        return this.barXWidth;
    }
    
    /**
     * Sets the the bar width as a percentage of the column width and
     * fires a {@link com.orsoncharts.renderer.Renderer3DChangeEvent}.
     * 
     * @param barXWidth  the new width.
     */
    public void setBarXWidth(double barXWidth) {
        this.barXWidth = barXWidth;
        fireChangeEvent();
    }

    /**
     * Returns the bar width as a percentage of the row width.
     * The default value is <code>0.8</code>.
     * 
     * @return The bar width. 
     */
    public double getBarZWidth() {
        return this.barZWidth;
    }
    
    /**
     * Sets the the bar width as a percentage of the row width and
     * fires a {@link com.orsoncharts.renderer.Renderer3DChangeEvent}.
     * 
     * @param barZWidth  the new width.
     */
    public void setBarZWidth(double barZWidth) {
        this.barZWidth = barZWidth;
        fireChangeEvent();
    }

    /**
     * Returns the range of values that will be required on the value axis
     * to see all the data from the dataset.  We override the method to 
     * include in the range the base value for the bars.
     * 
     * @param data  the data (<code>null</code> not permitted).
     * 
     * @return The range (possibly <code>null</code>) 
     */
    @Override
    public Range findValueRange(Values3D data) {
        return DataUtils.findValueRange(data, this.base);
    }

    /**
     * Constructs and places one item from the specified dataset into the given 
     * world.  This method will be called by the {@link CategoryPlot3D} class
     * while iterating over the items in the dataset.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param series  the series index.
     * @param row  the row index.
     * @param column  the column index.
     * @param world  the world (<code>null</code> not permitted).
     * @param dimensions  the plot dimensions (<code>null</code> not permitted).
     * @param xOffset  the x-offset.
     * @param yOffset  the y-offset.
     * @param zOffset  the z-offset.
     */
    @Override
    public void composeItem(CategoryDataset3D dataset, int series, int row, 
            int column, World world, Dimension3D dimensions, 
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

        double xx = columnAxis.translateToWorld(columnValue, 
                dimensions.getWidth());
        double yy = valueAxis.translateToWorld(value, dimensions.getHeight());
        double zz = rowAxis.translateToWorld(rowValue, dimensions.getDepth());

        double xw = this.barXWidth * columnAxis.getCategoryWidth();
        double zw = this.barZWidth * rowAxis.getCategoryWidth();
        double xxw = columnAxis.translateToWorld(xw, dimensions.getWidth());
        double xzw = rowAxis.translateToWorld(zw, dimensions.getDepth());
        double zero = valueAxis.translateToWorld(this.base, 
                dimensions.getHeight());
    
        Color color = getPaintSource().getPaint(series, row, column);
        Object3D bar = Object3D.createBar(xxw, xzw, xx + xOffset, 
                yy + yOffset, zz + zOffset, zero + yOffset, color);
        world.add(bar);
    }

}
