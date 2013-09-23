/**
 * ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */
package com.orsoncharts.renderer;

import java.awt.Color;

import com.orsoncharts.axis.Axis3D;
import com.orsoncharts.data.CategoryDataset3D;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.plot.CategoryPlot3D;

/**
 * A line renderer for 3D (category) charts.
 * 
 * TODO: handling null values, and values that are isolated
 */
public class LineRenderer3D extends AbstractCategoryRenderer3D {
    
    private double thickness = 0.4;
    
    /**
     * Default constructor.
     */
    public LineRenderer3D() { 
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
        Axis3D rowAxis = plot.getRowAxis();
        Axis3D columnAxis = plot.getColumnAxis();
        Axis3D valueAxis = plot.getValueAxis();

        // for all but the last item, we add regular segments
        if (column < dataset.getColumnCount() - 1) {
            double delta = this.thickness / 2.0;
            double x0 = columnAxis.translateToWorld(column + 1, 
                    dimensions.getWidth()) + xOffset;
            double y0 = valueAxis.translateToWorld(value, 
                    dimensions.getHeight()) + yOffset;
            double z0 = rowAxis.translateToWorld(row + 1, 
                    dimensions.getDepth()) + zOffset;
    
            double x1 = columnAxis.translateToWorld(column + 2, 
                    dimensions.getWidth()) + xOffset;
            double value1 = dataset.getDoubleValue(series, row, column + 1);
            double y1 = valueAxis.translateToWorld(value1, 
                    dimensions.getHeight()) + yOffset;
            
            Color color = getPaintSource().getPaint(series, row, column);
            
            // create an area shape
            Object3D obj = new Object3D();
            obj.addVertex(x0, y0, z0 - delta);
            obj.addVertex(x0, y0, z0 + delta);
            obj.addVertex(x1, y1, z0 + delta);
            obj.addVertex(x1, y1, z0 - delta);
            
            obj.addFace(new int[] {0, 1, 2, 3}, color);
            obj.addFace(new int[] {3, 2, 1, 0}, Color.GRAY);
            world.add(obj);
   
        } else {
            // we have the last item, so we can put the end caps on
        }
        
    }

}

