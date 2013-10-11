/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer.category;

import java.awt.Color;

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
 * A renderer that can be used with the {@link CategoryPlot3D} class to create
 * 3D stacked bar charts from data in a {@link CategoryDataset3D}.  The 
 * <code>createStackedBarChart()</code> method in the {@link Chart3DFactory} 
 * class will construct a chart that uses this renderer.
 */
public class StackedBarRenderer3D extends BarRenderer3D {

    /**
     * Creates a default constructor.
     */
    public StackedBarRenderer3D() {
        super();
    }
    
    @Override
    public Range findValueRange(Values3D data) {
        return DataUtils.findStackedValueRange(data);
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
        double[] stack = DataUtils.stackSubTotal(dataset, getBase(), series,
                row, column);

        CategoryPlot3D plot = getPlot();
        CategoryAxis3D rowAxis = plot.getRowAxis();
        CategoryAxis3D columnAxis = plot.getColumnAxis();
        Axis3D valueAxis = plot.getValueAxis();
   
        Comparable columnKey = dataset.getColumnKey(column);
        Comparable rowKey = dataset.getRowKey(row);
        double columnValue = columnAxis.getCategoryValue(columnKey);
        double rowValue = rowAxis.getCategoryValue(rowKey);
        double xx = columnAxis.translateToWorld(columnValue, 
                dimensions.getWidth());
        double zz = rowAxis.translateToWorld(rowValue, dimensions.getDepth());
        double lower = stack[1];
        if (value < 0.0) {
            lower = stack[0];
        }
        double upper = lower + value;
        double yy = valueAxis.translateToWorld(upper, dimensions.getHeight());
        double yylower = valueAxis.translateToWorld(lower, 
                dimensions.getHeight());
        double xw = getBarXWidth() * columnAxis.getCategoryWidth();
        double zw = getBarZWidth() * rowAxis.getCategoryWidth();
        double xxw = columnAxis.translateToWorld(xw, dimensions.getWidth());
        double xzw = rowAxis.translateToWorld(zw, dimensions.getDepth());
    
        Color color = getPaintSource().getPaint(series, row, column);
        Object3D bar = Object3D.createBar(xxw, xzw, 
                xx + xOffset, yy + yOffset, zz + zOffset, yylower + yOffset, 
                color);
        world.add(bar);
    }
    
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