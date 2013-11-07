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
 * {@link Chart3DFactory#createAreaChart(String, String, CategoryDataset3D, String, String, String)} 
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
    
    /** The depth of the area. */
    private double depth;
    
    /**
     * Default constructor.
     */
    public AreaRenderer3D() {
        this.base = 0.0;
        this.baseColor = null;
        this.depth = 0.6;
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
     * Returns the depth (in 3D) for the area (in world units).  The 
     * default value is <code>0.6</code>.
     * 
     * @return The depth.
     */
    public double getDepth() {
        return this.depth;
    }
    
    /**
     * Sets the depth (in 3D) and sends a {@link Renderer3DChangeEvent} to 
     * all registered listeners.
     * 
     * @param depth  the depth. 
     */
    public void setDepth(double depth) {
        this.depth = depth;
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
            if (!isBaselineCrossed(value0, value1, this.base)) {
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
        Color color = getPaintSource().getPaint(series, row, column);
        CategoryAxis3D rowAxis = plot.getRowAxis();
        CategoryAxis3D columnAxis = plot.getColumnAxis();
        Axis3D valueAxis = plot.getValueAxis();
        Comparable rowKey = dataset.getRowKey(row);
        Comparable columnKey = dataset.getColumnKey(column);
        Comparable nextColumnKey = dataset.getColumnKey(column + 1);
       
        double x0 = columnAxis.getCategoryValue(columnKey);
        double x1 = columnAxis.getCategoryValue(nextColumnKey);
        Range range = valueAxis.getRange();
        double y00 = range.peggedValue(y0);
        double y11 = range.peggedValue(y1);
        double ybb = range.peggedValue(this.base);
   
        if (y0 > this.base) {
            double x00 = x0;
            if (y0 < range.getMin()) {
                x00 = x0 + (x1 - x0) * fraction(y00, y0, y1);
            }
            double x11 = x1;
            if (y1 < range.getMin()) {
                x11 = x1 - (x1 - x0) * fraction(y11, y1, y0);
            }
            double x22 = (x00 + x11) / 2.0;  // bogus
            boolean p2required = spans(range.getMax(), y0, y1);  // only required when range max is spanned
            if (p2required) {
                x22 = x0 + (x1 - x0) * fraction(range.getMax(), y0, y1);
            }
        
            double delta = this.depth / 2.0;
            double wx00 = columnAxis.translateToWorld(x00, dimensions.getWidth()) 
                    + xOffset;
            double wx11 = columnAxis.translateToWorld(x11, dimensions.getWidth()) 
                    + xOffset;
            double wx22 = columnAxis.translateToWorld(x22, dimensions.getWidth()) 
                    + xOffset;
            double wy0 = valueAxis.translateToWorld(y00, dimensions.getHeight()) 
                    + yOffset;
            double wy1 = valueAxis.translateToWorld(y11, dimensions.getHeight()) 
                    + yOffset;
            double wymax = valueAxis.translateToWorld(range.getMax(), 
                    dimensions.getHeight()) + yOffset;
            double wbase = valueAxis.translateToWorld(ybb, 
                    dimensions.getHeight()) + yOffset;
         
            double wz = rowAxis.translateToWorld(
                    rowAxis.getCategoryValue(rowKey), 
                    dimensions.getDepth()) + zOffset;
                        
            // create an area shape
            Object3D obj = new Object3D();
            Point3D v0 = new Point3D(wx00, wbase, wz - delta);
            Point3D v1 = new Point3D(wx00, wy0, wz - delta);
            Point3D v2 = v1;
            if (p2required) {
                v2 = new Point3D(wx22, wymax, wz - delta);
            }
            Point3D v3 = new Point3D(wx11, wy1, wz - delta);
            Point3D v4 = new Point3D(wx11, wbase, wz - delta);
            obj.addVertex(v0);
            obj.addVertex(v1);
            obj.addVertex(v2);
            obj.addVertex(v3);
            obj.addVertex(v4);
        
            Point3D v5 = new Point3D(wx00, wbase, wz + delta);
            Point3D v6 = new Point3D(wx00, wy0, wz + delta);
            Point3D v7 = v6;
            if (p2required) {
                v7 = new Point3D(wx22, wymax, wz + delta);
            }
            Point3D v8 = new Point3D(wx11, wy1, wz + delta);
            Point3D v9 = new Point3D(wx11, wbase, wz + delta);
            obj.addVertex(v5);
            obj.addVertex(v6);
            obj.addVertex(v7);
            obj.addVertex(v8);
            obj.addVertex(v9);

            if (p2required) {
                obj.addFace(new int[] {0, 1, 2, 3, 4}, color, true);
                obj.addFace(new int[] {9, 8, 7, 6, 5}, color, true);
                obj.addFace(new int[] {0, 4, 9, 5}, color, true);
                obj.addFace(new int[] {6, 7, 2, 1}, color, true);
                obj.addFace(new int[] {3, 2, 7, 8}, color, true);
                
            } else {
                obj.addFace(new int[] {0, 1, 3, 4}, color, true);
                obj.addFace(new int[] {9, 8, 6, 5}, color, true);
                obj.addFace(new int[] {0, 4, 9, 5}, color, true);
                obj.addFace(new int[] {1, 6, 8, 3}, color, true);
            }
            if (column == 0) {
                obj.addFace(new int[] {0, 5, 6, 1}, color, true);
            }
            if (column == dataset.getColumnCount() - 2) {
                obj.addFace(new int[] {8, 9, 4, 3}, color, true);
            }
            world.add(obj);
//            Color bcol = this.baseColor != null ? this.baseColor : color;
//            obj.addFace(new int[] {5, 6, 7, 4}, bcol);  // bottom side
        } else {
            // let's do the case for negative areas
            double x00 = x0;
            if (y0 > range.getMax()) {
                x00 = x0 + (x1 - x0) * fraction(y00, y0, y1);
            }
            double x11 = x1;
            if (y1 > range.getMax()) {
                x11 = x1 - (x1 - x0) * fraction(y11, y1, y0);
            }
            double x22 = (x00 + x11) / 2.0;  // bogus
            boolean p2required = spans(range.getMin(), y0, y1);  // only required when range max is spanned
            if (p2required) {
                x22 = x0 + (x1 - x0) * fraction(range.getMin(), y0, y1);
            }
        
            double delta = this.depth / 2.0;
            double wx00 = columnAxis.translateToWorld(x00, dimensions.getWidth()) 
                    + xOffset;
            double wx11 = columnAxis.translateToWorld(x11, dimensions.getWidth()) 
                    + xOffset;
            double wx22 = columnAxis.translateToWorld(x22, dimensions.getWidth()) 
                    + xOffset;
            double wy0 = valueAxis.translateToWorld(y00, dimensions.getHeight()) 
                    + yOffset;
            double wy1 = valueAxis.translateToWorld(y11, dimensions.getHeight()) 
                    + yOffset;
            double wymin = valueAxis.translateToWorld(range.getMin(), 
                    dimensions.getHeight()) + yOffset;
            double wbase = valueAxis.translateToWorld(ybb, 
                    dimensions.getHeight()) + yOffset;
         
            double wz = rowAxis.translateToWorld(
                    rowAxis.getCategoryValue(rowKey), 
                    dimensions.getDepth()) + zOffset;
                        
            // create an area shape
            Object3D obj = new Object3D();
            Point3D v0 = new Point3D(wx00, wbase, wz - delta);
            Point3D v1 = new Point3D(wx00, wy0, wz - delta);
            Point3D v2 = v1;
            if (p2required) {
                v2 = new Point3D(wx22, wymin, wz - delta);
            }
            Point3D v3 = new Point3D(wx11, wy1, wz - delta);
            Point3D v4 = new Point3D(wx11, wbase, wz - delta);
            obj.addVertex(v0);
            obj.addVertex(v1);
            obj.addVertex(v2);
            obj.addVertex(v3);
            obj.addVertex(v4);
        
            Point3D v5 = new Point3D(wx00, wbase, wz + delta);
            Point3D v6 = new Point3D(wx00, wy0, wz + delta);
            Point3D v7 = v6;
            if (p2required) {
                v7 = new Point3D(wx22, wymin, wz + delta);
            }
            Point3D v8 = new Point3D(wx11, wy1, wz + delta);
            Point3D v9 = new Point3D(wx11, wbase, wz + delta);
            obj.addVertex(v5);
            obj.addVertex(v6);
            obj.addVertex(v7);
            obj.addVertex(v8);
            obj.addVertex(v9);

            if (p2required) {
                obj.addFace(new int[] {4, 3, 2, 1, 0}, color, true);
                obj.addFace(new int[] {5, 6, 7, 8, 9}, color, true);
                obj.addFace(new int[] {5, 9, 4, 0}, color, true);
                obj.addFace(new int[] {1, 2, 7, 6}, color, true);
                obj.addFace(new int[] {8, 7, 2, 3}, color, true);
//                obj.addFace(new int[] {0, 1, 2, 3, 4}, color);
//                obj.addFace(new int[] {9, 8, 7, 6, 5}, color);
//                obj.addFace(new int[] {0, 4, 9, 5}, color);
//                obj.addFace(new int[] {6, 7, 2, 1}, color);
//                obj.addFace(new int[] {3, 2, 7, 8}, color);
                
            } else {
                obj.addFace(new int[] {4, 3, 1, 0}, color, true);
                obj.addFace(new int[] {5, 6, 8, 9}, color, true);
                obj.addFace(new int[] {5, 9, 4, 0}, color, true);
                obj.addFace(new int[] {3, 8, 6, 1}, color, true);
//                obj.addFace(new int[] {0, 1, 3, 4}, color);
//                obj.addFace(new int[] {9, 8, 6, 5}, color);
//                obj.addFace(new int[] {0, 4, 9, 5}, color);
//                obj.addFace(new int[] {1, 6, 8, 3}, color);
            }
            if (column == 0) {
                obj.addFace(new int[] {1, 6, 5, 0}, color, true);
            }
            if (column == dataset.getColumnCount() - 2) {
                obj.addFace(new int[] {3, 4, 9, 8}, color, true);
            }
            world.add(obj);
//            Color bcol = this.baseColor != null ? this.baseColor : color;
//            obj.addFace(new int[] {5, 6, 7, 4}, bcol);  // bottom side
        }
        
    }
        
    private boolean spans(double value, double bound1, double bound2) {
        return (bound1 < value && bound2 > value)
                || (bound1 > value && bound2 < value);
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
        if (this.depth != that.depth) {
            return false;
        }
        return super.equals(obj);
    }
}
