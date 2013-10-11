/* ============
 * Orson Charts
 * ============
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
import com.orsoncharts.renderer.Renderer3DChangeEvent;
import java.io.Serializable;
import org.jfree.graphics2d.ObjectUtils;

/**
 * An area renderer for 3D charts.
 */
public class AreaRenderer3D extends AbstractCategoryRenderer3D 
        implements Serializable {
    
    /** The base for the areas (defaults to 0.0). */
    private double base;
    
    /** 
     * The color used to paint the underside of the area object (if 
     * <code>null</code>, the regular series color is used).
     */
    private Color baseColor;
    
    /** The width (depth in 3D) of the area. */
    private double width;
    
    /**
     * Default constructor.
     */
    public AreaRenderer3D() {
        this.base = 0.0;
        this.baseColor = null;
        this.width = 0.6;
    }

    /**
     * Returns the base value for the area.  The default value is 
     * <code>0.0</code>.
     * 
     * @return The base value. 
     */
    public double getBase() {
        return this.base;
    }
    
    /**
     * Sets the base value and sends a {@link RendererChangeEvent} to all
     * registered listeners.
     * 
     * @param base  the base value. 
     */
    public void setBase(double base) {
        this.base = base;
        fireChangeEvent();
    }
    
    /**
     * Returns the color used to paint the underside of the area polygons.
     * The default value is <code>null</code> (which means the undersides are
     * painted using the regular series color).
     * 
     * @return The color (possibly <code>null</code>). 
     * 
     * @see #setBaseColor(java.awt.Color) 
     */
    public Color getBaseColor() {
        return this.baseColor;
    }
    
    /**
     * Sets the color for the underside of the area polygons and sends a
     * {@link Renderer3DChangeEvent} to all registered listeners.  If you set
     * this to <code>null</code> the base will be painted with the regular
     * series color.
     * 
     * @param color  the color (<code>null</code> permitted). 
     */
    public void setBaseColor(Color color) {
        this.baseColor = color;
        fireChangeEvent();
    }
    
    /**
     * Returns the width (depth in 3D) for the area (in world units).  The 
     * default value is <code>0.6</code>.
     * 
     * @return The width.
     */
    public double getWidth() {
        return this.width;
    }
    
    /**
     * Sets the width (depth in 3D) and sends a {@link RendererChangeEvent} to 
     * all registered listeners.
     * 
     * @param width  the width. 
     */
    public void setWidth(double width) {
        this.width = width;
        fireChangeEvent();
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
        Comparable rowKey = dataset.getRowKey(row);
        Comparable columnKey = dataset.getColumnKey(column);
        Axis3D valueAxis = plot.getValueAxis();

        // for all but the last item, we add regular segments
        if (column < dataset.getColumnCount() - 1) {
            double delta = this.width / 2.0;
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
            Color bcol = this.baseColor != null ? this.baseColor : color;
            obj.addFace(new int[] {5, 6, 7, 4}, bcol);  // bottom side
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
        if (!ObjectUtils.equals(this.baseColor, that.baseColor)) {
            return false;
        }
        if (this.width != that.width) {
            return false;
        }
        return super.equals(obj);
    }
}
