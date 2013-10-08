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
import com.orsoncharts.Range;
import com.orsoncharts.data.category.CategoryDataset3D;
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
    
    /** The base for the areas (defaults to 0.0). */
    private double base;
    
    /** The thickness (depth in 3D) of the area. */
    private double thickness;
    
    /**
     * Default constructor.
     */
    public AreaRenderer3D() {
        this.base = 0.0;
        this.thickness = 0.6;
    }

    @Override
    public RendererType getRendererType() {
        return RendererType.BY_ITEM;
    }

    /**
     * Returns the range (for the value axis) that is required for this 
     * renderer to show all the values in the specified data set.  This method
     * is overridden to ensure that the range includes the base value (normally
     * 0.0) set for the renderer.
     * 
     * @param data  the data (<code>null</code> not permitted).
     * 
     * @return The range. 
     */
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
        Comparable rowKey = dataset.getRowKey(row);
        Comparable columnKey = dataset.getColumnKey(column);
        Axis3D valueAxis = plot.getValueAxis();

        // for all but the last item, we add regular segments
        if (column < dataset.getColumnCount() - 1) {
            double delta = this.thickness / 2.0;
            double x0 = columnAxis.translateToWorld(
                    columnAxis.getCategoryValue(columnKey), 
                    dimensions.getWidth()) + xOffset;
            double y0 = valueAxis.translateToWorld(value, 
                    dimensions.getHeight()) + yOffset;
            double z0 = rowAxis.translateToWorld(
                    rowAxis.getCategoryValue(rowKey), 
                    dimensions.getDepth()) + zOffset;

            double zero = valueAxis.translateToWorld(this.base, 
                    dimensions.getHeight()) + yOffset;
    
            Comparable nextColumnKey = dataset.getColumnKey(column + 1);
            double x1 = columnAxis.translateToWorld(
                    columnAxis.getCategoryValue(nextColumnKey), 
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
            obj.addFace(new int[] {0, 3, 4, 7}, color);
            obj.addFace(new int[] {6, 5, 2, 1}, color);
            
            if (column == 0) {
                obj.addFace(new int[] {0, 7, 6, 1}, color);
            }
            if (column == dataset.getColumnCount() - 2) {
                obj.addFace(new int[] {5, 4, 3, 2}, color);
            }
            obj.addFace(new int[] {5, 6, 7, 4}, Color.GRAY);  // bottom side
            world.add(obj);
   
        } else {
            // we have the last item, so we can put the end caps on
        }
        
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AreaRenderer3D)) {
            return false;
        }
        AreaRenderer3D that = (AreaRenderer3D) obj;
        if (this.base != that.base) {
            return false;
        }
        if (this.thickness != that.thickness) {
            return false;
        }
        return super.equals(obj);
    }
}
