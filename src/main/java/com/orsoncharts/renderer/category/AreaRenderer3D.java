/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer.category;

import java.awt.Color;

import java.io.Serializable;
import com.orsoncharts.axis.Axis3D;
import com.orsoncharts.axis.CategoryAxis3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.Range;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.data.DataUtils;
import com.orsoncharts.data.Values3D;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.Point3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.renderer.Renderer3DChangeEvent;
import com.orsoncharts.util.ObjectUtils;

/**
 * A renderer for creating 3D area charts from data in a 
 * {@link CategoryDataset3D} (for use with a {@link CategoryPlot3D}).
 * <br><br>
 * TIP: to create a chart using this renderer, you can use the
 * {@link Chart3DFactory#createAreaChart(String, CategoryDataset3D, String, String, String)} 
 * method.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
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
     * Sets the base value and sends a {@link Renderer3DChangeEvent} to all
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
     * Sets the width (depth in 3D) and sends a {@link Renderer3DChangeEvent} to 
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
        
        double value0 = dataset.getDoubleValue(series, row, column);
        if (Double.isNaN(value0)) {
            return;
        }
        CategoryPlot3D plot = getPlot();
        Axis3D valueAxis = plot.getValueAxis();
        double baseline = valueAxis.getRange().peggedValue(this.base);
        
        // for all but the last item, we add regular segments
        if (column < dataset.getColumnCount() - 1) {
            double value1 = dataset.getDoubleValue(series, row, column + 1);
            if (Double.isNaN(value1)) {
                return;  // we can't do anything
            }
            if (!valueAxis.getRange().intersects(value0, value1)) {
                return;
            }
            if (!isBaselineCrossed(value0, value1, baseline)) {
                composeItemWithoutCrossing(value0, value1, dataset, series,
                        row, column, world, dimensions, xOffset, yOffset, 
                        zOffset);   
            } else {
                composeItemWithCrossing();
            }
        }
    }

    /**
     * Returns <code>true</code> if the two values are on opposite sides of 
     * the baseline.  If the data values cross the baseline, then we need
     * to construct two 3D objects to represent the data, whereas if there is
     * no crossing, a single 3D object will be sufficient.
     * 
     * @param y0  the first value.
     * @param y1  the second value.
     * @param baseline  the baseline.
     * 
     * @return A boolean. 
     */
    private boolean isBaselineCrossed(double y0, double y1, double baseline) {
        return (y0 > baseline && y1 < baseline) 
                || (y0 < baseline && y1 > baseline);
    }
    
    private double fraction(double x, double x0, double x1) {
        double dist = Math.abs(x - x0);
        double length = Math.abs(x1 - x0);
        return dist / length;
    }

    /**
     * Handles an item where the current value and the next value are both on 
     * the same side of the baseline (no crossing) which means we can use a
     * single 3D object to represent the item.
     * 
     * @param value0
     * @param value1
     * @param dataset
     * @param series
     * @param row
     * @param column
     * @param world
     * @param dimensions
     * @param xOffset
     * @param yOffset
     * @param zOffset 
     */
    private void composeItemWithoutCrossing(double y0, double y1,
            CategoryDataset3D dataset, int series, int row, int column, 
            World world, Dimension3D dimensions, 
            double xOffset, double yOffset, double zOffset) {

        CategoryPlot3D plot = getPlot();
        CategoryAxis3D rowAxis = plot.getRowAxis();
        CategoryAxis3D columnAxis = plot.getColumnAxis();
        Axis3D valueAxis = plot.getValueAxis();
        Comparable rowKey = dataset.getRowKey(row);
        Comparable columnKey = dataset.getColumnKey(column);
        Comparable nextColumnKey = dataset.getColumnKey(column + 1);
       
        double x0 = columnAxis.getCategoryValue(columnKey);
        double x1 = columnAxis.getCategoryValue(nextColumnKey);
        double ybase = valueAxis.getRange().peggedValue(this.base);
        double y00 = valueAxis.getRange().peggedValue(y0);
        double x00 = x0;
        if (y0 != y1) {
            x00 = x0 + (x1 - x0) * fraction(y00, y0, y1);
        }
        double y11 = valueAxis.getRange().peggedValue(y1);
        double x11 = x1;
        if (y0 != y1) {
            x11 = x0 + (x1 - x0) * fraction(y11, y0, y1);
        }
        
        // if y0 is closer to base than y00 then move x0 to x00
        if (Math.abs(y0 - this.base) < Math.abs(y00 - this.base)) {
            x0 = x00;
        }
        // if y1 is closer to base than y11 then move x1 to x11
        if (Math.abs(y1 - this.base) < Math.abs(y11 - this.base)) {
            x1 = x11;
        }
        
        double delta = this.width / 2.0;
        double wx0 = columnAxis.translateToWorld(x0, dimensions.getWidth()) 
                + xOffset;
        double wx00 = columnAxis.translateToWorld(x00, dimensions.getWidth()) 
                + xOffset;
        double wx1 = columnAxis.translateToWorld(x1, dimensions.getWidth()) 
                + xOffset;
        double wx11 = columnAxis.translateToWorld(x11, dimensions.getWidth()) 
                + xOffset;
        double wy0 = valueAxis.translateToWorld(y00, dimensions.getHeight()) 
                + yOffset;
        double wy1 = valueAxis.translateToWorld(y11, dimensions.getHeight()) 
                + yOffset;
        double wbase = valueAxis.translateToWorld(ybase, 
                dimensions.getHeight()) + yOffset;
        
        double wz = rowAxis.translateToWorld(
                rowAxis.getCategoryValue(rowKey), 
                dimensions.getDepth()) + zOffset;
            
        Color color = getPaintSource().getPaint(series, row, column);
            
        // create an area shape
        Object3D obj = new Object3D();
        Point3D v0 = new Point3D(wx0, wbase, wz - delta);
        Point3D v1 = new Point3D(wx1, wbase, wz - delta);
        Point3D v2 = new Point3D(wx1, wy0, wz - delta);
        Point3D v3 = new Point3D(wx11, wy1, wz - delta);
        Point3D v4 = new Point3D(wx00, wy1, wz - delta);
        Point3D v5 = new Point3D(wx0, wy0, wz - delta);
        obj.addVertex(v0);
        obj.addVertex(v1);
        obj.addVertex(v2);
        obj.addVertex(v3);
        obj.addVertex(v4);
        obj.addVertex(v5);
        
        Point3D v6 = new Point3D(wx0, wbase, wz + delta);
        Point3D v7 = new Point3D(wx1, wbase, wz + delta);
        Point3D v8 = new Point3D(wx1, wy0, wz + delta);
        Point3D v9 = new Point3D(wx11, wy1, wz + delta);
        Point3D v10 = new Point3D(wx00, wy1, wz + delta);
        Point3D v11 = new Point3D(wx0, wy0, wz + delta);
        obj.addVertex(v6);
        obj.addVertex(v7);
        obj.addVertex(v8);
        obj.addVertex(v9);
        obj.addVertex(v10);
        obj.addVertex(v11);
           
//        Point3D v4 = new Point3D(wx1, wybase, wz0 - delta);
//        Point3D v5 = new Point3D(wx1, wybase, wz0 + delta);
//        Point3D v6 = new Point3D(wx0, wybase, wz0 + delta);
//        Point3D v7 = new Point3D(wx0, wybase, wz0 - delta);
//        
//        obj.addVertex(v4);
//        obj.addVertex(v5);
//        obj.addVertex(v6);
//        obj.addVertex(v7);

        if (y0 > this.base) {
            obj.addFace(new int[] {2, 3, 4, 5}, color);
            //obj.addFace(new int[] {11, 10, 9, 8}, color);
//            obj.addFace(new int[] {0, 3, 4, 7}, color);
//            obj.addFace(new int[] {6, 5, 2, 1}, color);
//            
//            if (column == 0) {
//                obj.addFace(new int[] {0, 7, 6, 1}, color);
//            }
//            if (column == dataset.getColumnCount() - 2) {
//                obj.addFace(new int[] {5, 4, 3, 2}, color);
//            }
//            Color bcol = this.baseColor != null ? this.baseColor : color;
//            obj.addFace(new int[] {5, 6, 7, 4}, bcol);  // bottom side
        } else {
//            obj.addFace(new int[] {5, 4, 3, 2}, color);
//            obj.addFace(new int[] {8, 9, 10, 11}, color);
//            obj.addFace(new int[] {7, 4, 3, 0}, color);
//            obj.addFace(new int[] {1, 2, 5, 6}, color);
//            
//            if (column == 0) {
//                obj.addFace(new int[] {1, 6, 7, 0}, color);
//            }
//            if (column == dataset.getColumnCount() - 2) {
//                obj.addFace(new int[] {2, 3, 4, 5}, color);
//            }
//            Color bcol = this.baseColor != null ? this.baseColor : color;
//            obj.addFace(new int[] {4, 7, 6, 5}, bcol);  // bottom side            
        }
        world.add(obj);
    }
    
    private void composeItemWithCrossing() {
    }
    
    /**
     * Tests this renderer for equality with an arbitrary object.
     * 
     * @param obj  the object (<code>null</code> permitted).
     * 
     * @return A boolean. 
     */
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
