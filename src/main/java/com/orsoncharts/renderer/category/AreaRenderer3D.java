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
import com.orsoncharts.graphics3d.Utils2D;
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
     * Returns the y-value for the base of the area.  The default value is 
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
        
        double y0 = dataset.getDoubleValue(series, row, column);
        if (Double.isNaN(y0)) {
            return;
        }
        CategoryPlot3D plot = getPlot();
        CategoryAxis3D xAxis = plot.getColumnAxis();
        CategoryAxis3D zAxis = plot.getRowAxis();
        Axis3D valueAxis = plot.getValueAxis();
        
        // for all but the last item, we add regular segments
        if (column < dataset.getColumnCount() - 1) {
            double y1 = dataset.getDoubleValue(series, row, column + 1);
            if (Double.isNaN(y1)) {
                return;  // we can't do anything
            }
            if (!valueAxis.getRange().intersects(this.base, y0) 
                    && !valueAxis.getRange().intersects(this.base, y1)) {
                return;
            }
            if (!isBaselineCrossed(y0, y1, this.base)) {
                composeItemWithoutCrossing(y0, y1, dataset, series,
                        row, column, world, dimensions, xOffset, yOffset, 
                        zOffset);   
            } else {
                double x0 = xAxis.getCategoryValue(dataset.getColumnKey(column));
                double x1 = xAxis.getCategoryValue(dataset.getColumnKey(column + 1));
                
                double ww = dimensions.getWidth();
                double hh = dimensions.getHeight();
                double wx0 = xAxis.translateToWorld(x0, ww) + xOffset;
                double wx1 = xAxis.translateToWorld(x1, ww) + xOffset;
                double wy0 = valueAxis.translateToWorld(y0, hh) + yOffset;
                double wy1 = valueAxis.translateToWorld(y1, hh) + yOffset;
                double wbase = valueAxis.translateToWorld(this.base, hh) + yOffset;
                double wz = zAxis.translateToWorld(zAxis.getCategoryValue(dataset.getRowKey(row)), dimensions.getDepth()) + zOffset;
                double wmin = valueAxis.translateToWorld(valueAxis.getRange().getMin(), hh) + yOffset;
                double wmax = valueAxis.translateToWorld(valueAxis.getRange().getMax(), hh) + yOffset;
                Color color = getColorSource().getColor(series, row, column);
                boolean openingFace = (column == 0);
                boolean closingFace = (column == dataset.getColumnCount() - 2);
                composeItemWithCrossing(world, wx0, wy0, wx1, wy1, wbase, wz, 
                        new Range(wmin, wmax), color, openingFace, closingFace);
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
     * @param y0
     * @param y1
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
        Color color = getColorSource().getColor(series, row, column);
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
   
        double ww = dimensions.getWidth();
        double hh = dimensions.getHeight();
        boolean positive = y0 > this.base || y1 > this.base;
        if (positive) {            
            double x00 = x0;
            if (y0 < range.getMin()) {
                x00 = x0 + (x1 - x0) * fraction(y00, y0, y1);
            }
            double x11 = x1;
            if (y1 < range.getMin()) {
                x11 = x1 - (x1 - x0) * fraction(y11, y1, y0);
            }
            double wx00 = columnAxis.translateToWorld(x00, ww) + xOffset;
            double wx11 = columnAxis.translateToWorld(x11, ww) + xOffset;
            double wy0 = valueAxis.translateToWorld(y0, hh) + yOffset;
            double wy1 = valueAxis.translateToWorld(y1, hh) + yOffset;
            double wymin = valueAxis.translateToWorld(range.getMin(), hh) 
                    + yOffset;
            double wymax = valueAxis.translateToWorld(range.getMax(), hh) 
                    + yOffset;
            double wbase = valueAxis.translateToWorld(ybb, hh) + yOffset;
         
            double wz = rowAxis.translateToWorld(
                    rowAxis.getCategoryValue(rowKey), 
                    dimensions.getDepth()) + zOffset;
            world.add(createPositiveArea(color, wx00, wy0, wx11, wy1, wbase, 
                    wz, new Range(wymin, wymax), column == 0, 
                    column == dataset.getColumnCount() - 2));
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
        
            double wx00 = columnAxis.translateToWorld(x00, ww) + xOffset;
            double wx11 = columnAxis.translateToWorld(x11, ww) + xOffset;
            double wy0 = valueAxis.translateToWorld(y0, hh) + yOffset;
            double wy1 = valueAxis.translateToWorld(y1, hh) + yOffset;
            double wymin = valueAxis.translateToWorld(range.getMin(), hh) 
                    + yOffset;
            double wymax = valueAxis.translateToWorld(range.getMax(), hh) 
                    + yOffset;
            double wbase = valueAxis.translateToWorld(ybb, hh) + yOffset;
         
            double wz = rowAxis.translateToWorld(
                    rowAxis.getCategoryValue(rowKey), 
                    dimensions.getDepth()) + zOffset;
                       
            Object3D neg = createNegativeArea(color, wx00, wy0, wx11, wy1, wbase, wz, 
                    new Range(wymin, wymax), column == 0, 
                    column == dataset.getColumnCount() - 2);
            if (neg != null) {
                world.add(neg);
            }
        }
        
    }
        
    
    /**
     * Adds two objects to the world to show an area shape where the data
     * values cross the baseline.  All coordinates here have already been
     * translated to world coordinates.
     * 
     * @param world  the world.
     * @param wx0
     * @param wy0
     * @param wx1
     * @param wy1
     * @param wbase
     * @param wz
     * @param range
     * @param color
     * @param openingFace
     * @param closingFace 
     */
    private void composeItemWithCrossing(World world, double wx0, double wy0, 
            double wx1, double wy1, double wbase, double wz, Range range, 
            Color color, boolean openingFace, boolean closingFace) {
        
        // find the crossing point
        double ydelta = Math.abs(wy1 - wy0);
        double factor = 0;
        if (ydelta != 0.0) {
            factor = Math.abs(wy0 - wbase) / ydelta;
        }
        double xcross = wx0 + factor * (wx1 - wx0);
        
        // then process a regular shape before the crossing
        // and a regular shape after the crossing
        if (wy0 > wbase) {
            world.add(createPositiveArea(color, wx0, wy0, xcross, wbase, wbase, wz, 
                    range, openingFace, closingFace));
            Object3D neg = createNegativeArea(color, xcross, wbase, wx1, wy1, 
                    wbase, wz, range, openingFace, closingFace); 
            if (neg != null) {
                world.add(neg);
            }
        } else {
            Object3D neg = createNegativeArea(color, wx0, wy0, xcross, wbase, 
                    wbase, wz, range, openingFace, closingFace);
            if (neg != null) {
                world.add(neg);
            }
            world.add(createPositiveArea(color, xcross, wbase, wx1, wy1, wbase, 
                    wz, range, openingFace, closingFace));
        }
    }
    
    /**
     * Creates a 3D object to represent a positive "area", taking into account
     * that the visible range can be restricted.
     * 
     * @param color
     * @param wx0
     * @param wy0
     * @param wx1
     * @param wy1
     * @param wbase
     * @param wz
     * @param range
     * @param openingFace
     * @param closingFace
     * 
     * @return A 3D object or <code>null</code>. 
     */
    private Object3D createPositiveArea(Color color, double wx0, double wy0, 
            double wx1, double wy1, double wbase, double wz, Range range,
            boolean openingFace, boolean closingFace) {
        
        if (!range.intersects(wy0, wbase) && !range.intersects(wy1, wbase)) {
            return null;
        }
        double wy00 = range.peggedValue(wy0);
        double wy11 = range.peggedValue(wy1);
        double wbb = range.peggedValue(wbase);
        
        double wx00 = wx0;
        if (wy0 < range.getMin()) {
            wx00 = wx0 + (wx1 - wx0) * fraction(wy00, wy0, wy1);
        }
        double wx11 = wx1;
        if (wy1 < range.getMin()) {
            wx11 = wx1 - (wx1 - wx0) * fraction(wy11, wy1, wy0);
        }
        double wx22 = Double.NaN;  // bogus
        boolean p2required = Utils2D.spans(range.getMax(), wy0, wy1); 
        if (p2required) {
            wx22 = wx0 + (wx1 - wx0) * fraction(range.getMax(), wy0, wy1);
        }
        
        double delta = this.depth / 2.0;
                        
        // create an area shape
        Object3D obj = new Object3D();
        obj.addVertex(wx00, wbb, wz - delta);
        obj.addVertex(wx00, wbb, wz + delta);
        boolean leftSide = false;
        if (wy00 != wbb) {
            leftSide = true;
            obj.addVertex(wx00, wy00, wz - delta);
            obj.addVertex(wx00, wy00, wz + delta);
        }
        if (p2required) {
            obj.addVertex(wx22, range.getMax(), wz - delta);
            obj.addVertex(wx22, range.getMax(), wz + delta);
        }
        obj.addVertex(wx11, wy11, wz - delta);
        obj.addVertex(wx11, wy11, wz + delta);
        boolean rightSide = false;
        if (wy11 != wbb) {
            rightSide = true;
            obj.addVertex(wx11, wbb, wz - delta);
            obj.addVertex(wx11, wbb, wz + delta);
        }
        int vertices = obj.getVertexCount();
        
        if (vertices == 10) {
            obj.addFace(new int[] {0, 2, 4, 6, 8}, color, true);  // front
            obj.addFace(new int[] {1, 9, 7, 5, 3}, color, true);  // rear
            obj.addFace(new int[] {0, 8, 9, 1}, color, true);  // base
            obj.addFace(new int[] {2, 3, 5, 4}, color, true); // top 1
            obj.addFace(new int[] {4, 5, 7, 6}, color, true);  // top 2
            if (openingFace) {
                obj.addFace(new int[] {0, 1, 3, 2}, color, true);
            }
            if (closingFace) {
                obj.addFace(new int[] {6, 7, 9, 8}, color, true);
            }
        } else if (vertices == 8) {
            obj.addFace(new int[] {0, 2, 4, 6}, color, true);  // front
            obj.addFace(new int[] {7, 5, 3, 1}, color, true);  // rear
            if (!leftSide) {
                obj.addFace(new int[] {0, 1, 3, 2}, color, true);  // top left
            }
            obj.addFace(new int[] {2, 3, 5, 4}, color, true);  // top 1
            if (!rightSide) {
                obj.addFace(new int[] {4, 5, 7, 6}, color, true); // top 2 
            }
            obj.addFace(new int[] {1, 0, 6, 7}, color, true); // base
            if (openingFace) {
                obj.addFace(new int[] {0, 1, 3, 2}, color, true);
            }
            if (closingFace) {
                obj.addFace(new int[] {4, 5, 7, 6}, color, true);
            }
        } else if (vertices == 6) {
            obj.addFace(new int[] {0, 2, 4}, color, true); // front
            obj.addFace(new int[] {5, 3, 1}, color, true); // rear
            if (leftSide) {
                obj.addFace(new int[] {3, 5, 4, 2}, color, true); // top            
                if (openingFace) {
                    obj.addFace(new int[] {0, 1, 3, 2}, color, true);
                }
            } else {
                obj.addFace(new int[] {0, 1, 3, 2}, color, true); // top
                if (closingFace) {
                    obj.addFace(new int[] {2, 3, 5, 4}, color, true);
                }
            }
            obj.addFace(new int[] {0, 4, 5, 1}, color, true); // base            
        } else {
            obj.addFace(new int[] {0, 1, 3, 2}, color, true);
            obj.addFace(new int[] {2, 3, 1, 0}, color, true);
        }
        return obj;
    }
    
    private Object3D createNegativeArea(Color color, double wx0, double wy0, 
            double wx1, double wy1, double wbase, double wz, Range range,
            boolean openingFace, boolean closingFace) {
        
        if (!range.intersects(wy0, wbase) && !range.intersects(wy1, wbase)) {
            return null;
        }
        double wy00 = range.peggedValue(wy0);
        double wy11 = range.peggedValue(wy1);
        double wbb = range.peggedValue(wbase);
        
        double wx00 = wx0;
        if (wy0 > range.getMax()) {
            wx00 = wx0 + (wx1 - wx0) * fraction(wy00, wy0, wy1);
        }
        double wx11 = wx1;
        if (wy1 > range.getMax()) {
            wx11 = wx1 - (wx1 - wx0) * fraction(wy11, wy1, wy0);
        }
        double wx22 = (wx00 + wx11) / 2.0;  // bogus
        boolean p2required = Utils2D.spans(range.getMin(), wy0, wy1); 
        if (p2required) {
            wx22 = wx0 + (wx1 - wx0) * fraction(range.getMin(), wy0, wy1);
        }
        
        double delta = this.depth / 2.0;

        // create an area shape
        Object3D obj = new Object3D();
        obj.addVertex(wx00, wbb, wz - delta);
        obj.addVertex(wx00, wbb, wz + delta);
        boolean leftSide = false;
        if (wy00 != wbb) {
            leftSide = true;
            obj.addVertex(wx00, wy00, wz - delta);
            obj.addVertex(wx00, wy00, wz + delta);
        }
        if (p2required) {
            obj.addVertex(wx22, range.getMin(), wz - delta);
            obj.addVertex(wx22, range.getMin(), wz + delta);
        }
        obj.addVertex(wx11, wy11, wz - delta);
        obj.addVertex(wx11, wy11, wz + delta);
        boolean rightSide = false;
        if (wy11 != wbb) {
            obj.addVertex(wx11, wbb, wz - delta);
            obj.addVertex(wx11, wbb, wz + delta);
        }
        int vertices = obj.getVertexCount();
        if (vertices == 10) {
            obj.addFace(new int[] {8, 6, 4, 2, 0}, color, true);  // front
            obj.addFace(new int[] {1, 3, 5, 7, 9}, color, true);  // rear
            obj.addFace(new int[] {1, 9, 8, 0}, color, true);  // base
            obj.addFace(new int[] {4, 5, 3, 2}, color, true); // top 1
            obj.addFace(new int[] {6, 7, 5, 4}, color, true);  // top 2
            if (openingFace) {
                obj.addFace(new int[] {2, 3, 1, 0}, color, true);
            }
            if (closingFace) {
                obj.addFace(new int[] {8, 9, 7, 6}, color, true);
            }            
        } else if (vertices == 8) {
            obj.addFace(new int[] {2, 0, 6, 4}, color, true);  // front
            obj.addFace(new int[] {1, 3, 5, 7}, color, true);  // rear
            obj.addFace(new int[] {0, 1, 7, 6}, color, true);  // base
            if (!leftSide) {
                obj.addFace(new int[] {2, 3, 1, 0}, color, true);            
            }
            obj.addFace(new int[] {3, 2, 4, 5}, color, true);  // negative top
            if (!rightSide) {
                obj.addFace(new int[] {6, 7, 5, 4}, color, true);  
            }
            if (openingFace) {
                obj.addFace(new int[] {1, 0, 2, 3}, color, true);
            }
            if (closingFace) {
                obj.addFace(new int[] {5, 4, 6, 7}, color, true);
            }
        } else if (vertices == 6) {
            obj.addFace(new int[] {4, 2, 0}, color, true);  // front  
            obj.addFace(new int[] {1, 3, 5}, color, true);  // rear
            if (leftSide) {
                obj.addFace(new int[] {4, 5, 3, 2}, color, true);  // negative top
                if (openingFace) {
                    obj.addFace(new int[] {1, 0, 2, 3}, color, true);
                }
            } else {
                obj.addFace(new int[] {2, 3, 1, 0}, color, true); // negative top               
                if (closingFace) {
                    obj.addFace(new int[] {3, 2, 4, 5}, color, true);
                }
            }
            obj.addFace(new int[] {0, 1, 5, 4}, color, true);  // base
        } else {
            obj.addFace(new int[] {0, 1, 3, 2}, color, true);
            obj.addFace(new int[] {2, 3, 1, 0}, color, true);
        }
        return obj;
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
