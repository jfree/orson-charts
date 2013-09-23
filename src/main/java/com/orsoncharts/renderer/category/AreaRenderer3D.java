/**
 * ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */
package com.orsoncharts.renderer.category;

import com.orsoncharts.renderer.category.AbstractCategoryRenderer3D;
import java.awt.Color;

import com.orsoncharts.axis.Axis3D;
import com.orsoncharts.axis.Range;
import com.orsoncharts.data.CategoryDataset3D;
import com.orsoncharts.data.DataUtilities;
import com.orsoncharts.data.Values3D;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.renderer.RendererType;

/**
 * An area renderer for 3D charts.
 */
public class AreaRenderer3D extends AbstractCategoryRenderer3D {
    
    private double base;
    
    private double thickness = 0.6;
    
    /**
     * Default constructor.
     */
    public AreaRenderer3D() {
        this.base = 0.0;    
    }

    @Override
    public RendererType getRendererType() {
        return RendererType.BY_ITEM;
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

            double zero = valueAxis.translateToWorld(this.base, 
                    dimensions.getHeight()) + yOffset;
    
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
            
            obj.addVertex(x1, zero, z0 - delta);
            obj.addVertex(x1, zero, z0 + delta);
            obj.addVertex(x0, zero, z0 + delta);
            obj.addVertex(x0, zero, z0 - delta);

            obj.addFace(new int[] {0, 1, 2, 3}, color);
            //obj.addFace(new int[] {3, 2, 1, 0}, Color.GRAY);
            obj.addFace(new int[] {0, 3, 4, 7}, color);
            obj.addFace(new int[] {6, 5, 2, 1}, color);
            
            obj.addFace(new int[] {5, 6, 7, 4}, Color.GRAY);
            world.add(obj);
   
        } else {
            // we have the last item, so we can put the end caps on
        }
        
    }

}
