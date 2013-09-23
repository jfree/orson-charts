/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.renderer;

import com.orsoncharts.axis.Axis3D;
import com.orsoncharts.axis.Range;
import com.orsoncharts.data.CategoryDataset3D;
import com.orsoncharts.data.DataUtilities;
import com.orsoncharts.data.Values3D;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.plot.CategoryPlot3D;
import java.awt.Color;

/**
 * A stacked bar renderer in 3D
 */
public class StackedBarRenderer3D extends AbstractCategoryRenderer3D 
            implements CategoryRenderer3D {

    private double base;
    
    private double barThickness = 0.8;
    
    /**
     * Creates a default constructor.
     */
    public StackedBarRenderer3D() {
        this.base = 0.0;
    }
    
    @Override
    public Range findValueRange(Values3D data) {
        return DataUtilities.findStackedValueRange(data);
    }
    
    /**
     * Composes a single item from the dataset to the 3D world.
     * 
     * @param world
     * @param dimensions
     * @param dataset
     * @param series
     * @param row
     * @param column
     * @param xOffset
     * @param yOffset
     * @param zOffset 
     */
    @Override
    public void composeItem(World world, Dimension3D dimensions, 
            CategoryDataset3D dataset, int series, int row, int column, 
            double xOffset, double yOffset, double zOffset) {
        
        double value = dataset.getDoubleValue(series, row, column);
        if (Double.isNaN(value)) {
            return;
        }
        double[] stack = DataUtilities.stackSubTotal(dataset, this.base, series,
                row, column);

        CategoryPlot3D plot = getPlot();
        Axis3D rowAxis = plot.getRowAxis();
        Axis3D columnAxis = plot.getColumnAxis();
        Axis3D valueAxis = plot.getValueAxis();
   
        double xx = columnAxis.translateToWorld(column + 1, dimensions.getWidth());
        double zz = rowAxis.translateToWorld(row + 1, dimensions.getDepth());
        double lower = stack[1];
        if (value < 0.0) {
            lower = stack[0];
        }
        double upper = lower + value;
        double yy = valueAxis.translateToWorld(upper, dimensions.getHeight());
        double yylower = valueAxis.translateToWorld(lower, dimensions.getHeight());
    
        Color color = getPaintSource().getPaint(series, row, column);
        Object3D bar = Object3D.createBar(this.barThickness, xx + xOffset, yy + yOffset, 
                zz + zOffset, yylower + yOffset, color);
        world.add(bar);
    }
    
}