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

import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.Range;
import com.orsoncharts.axis.CategoryAxis3D;
import com.orsoncharts.axis.ValueAxis3D;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.Utils2D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.renderer.Renderer3DChangeEvent;

/**
 * A renderer that can be used with the {@link CategoryPlot3D} class to create
 * 3D lines charts from data in a {@link CategoryDataset3D}.  The 
 * <code>createLineChart()</code> method in the {@link Chart3DFactory} class 
 * will construct a chart that uses this renderer.
 * <br><br>
 * Some attributes in the renderer are specified in "world units" - see the
 * {@link Chart3D} class description for more information about world units.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 */
public class LineRenderer3D2 extends AbstractCategoryRenderer3D 
        implements Serializable {

    /** The line width (in world units). */
    private double lineWidth;
    
    /** The line height (in world units). */
    private double lineHeight;
    
    /**
     * Creates a new instance with default attribute values.
     */
    public LineRenderer3D2() {
        this.lineWidth = 0.4;
        this.lineHeight = 0.2;
    }
    
    /**
     * Returns the line width in world units.  The default value is 
     * <code>0.4</code>.
     * 
     * @return The line width in world units. 
     */
    public double getLineWidth() {
        return this.lineWidth;
    }
    
    /**
     * Sets the line width (in world units) and sends a 
     * {@link Renderer3DChangeEvent} to all registered listeners.
     * 
     * @param width  the width (in world units). 
     */
    public void setLineWidth(double width) {
        this.lineWidth = width;
        fireChangeEvent();
    }

    /**
     * Returns the line height in world units.  The default value is 
     * <code>0.2</code>.
     * 
     * @return The line height in world units. 
     */
    public double getLineHeight() {
        return this.lineHeight;
    }
    
    /**
     * Sets the line height (in world units) and sends a 
     * {@link Renderer3DChangeEvent} to all registered listeners.
     * 
     * @param height  the height (in world units). 
     */
    public void setLineHeight(double height) {
        this.lineHeight = height;
        fireChangeEvent();
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
        ValueAxis3D valueAxis = plot.getValueAxis();
        Range r = valueAxis.getRange();
        
        Comparable rowKey = dataset.getRowKey(row);
        Comparable columnKey = dataset.getColumnKey(column);
        double rowValue = rowAxis.getCategoryValue(rowKey);
        double columnValue = columnAxis.getCategoryValue(columnKey);
        double ww = dimensions.getWidth();
        double hh = dimensions.getHeight();
        double dd = dimensions.getDepth();

        // for all but the last item, we will add a segment extending from the
        // current data value to the next data value
        if (column < dataset.getColumnCount() - 1) {
            double wdelta = this.lineWidth / 2.0;
            double x0 = columnAxis.translateToWorld(columnValue, ww) + xOffset;
            double y0 = valueAxis.translateToWorld(value, hh) + yOffset;
            double z0 = rowAxis.translateToWorld(rowValue, dd) + zOffset;
            double wmin = valueAxis.translateToWorld(r.getMin(), hh) + yOffset;
            double wmax = valueAxis.translateToWorld(r.getMax(), hh) + yOffset;
    
            Comparable nextColumnKey = dataset.getColumnKey(column + 1);
            double nextColumnValue = columnAxis.getCategoryValue(nextColumnKey);
            double x1 = columnAxis.translateToWorld(nextColumnValue, ww) + xOffset;
            double value1 = dataset.getDoubleValue(series, row, column + 1);
            double y1 = valueAxis.translateToWorld(value1, hh) + yOffset;
            
            Color color = getColorSource().getColor(series, row, column);
            
            // create a line shape
            Object3D obj = new Object3D();
            if (this.lineHeight > 0.0) {
                double hdelta = this.lineHeight / 2.0;
                double y00 = y0 - hdelta;
                double y01 = y0 + hdelta;
                double y10 = y1 - hdelta;
                double y11 = y1 + hdelta;
                double xleftmin = Double.NaN;
                double xleftmax = Double.NaN;
                double xrightmin = Double.NaN;
                double xrightmax = Double.NaN;
                if (y00 >= wmax) {  // CASE A 
                    
                } else if (y01 > wmax && y00 > wmin) {  // CASE B
                    
                } else if (y01 > wmax && y00 <= wmin) {
                    
                } else if (y01 > wmin && y00 >= wmin) { // CASE C
                    
                } else if (y01 > wmin && y00 < wmin) { // CASE D
                    
                } else if (y01 <= wmin) {  // CASE E
                    
                }
                double xleft = Double.NaN;
                if (y00 != y10 && Utils2D.spans(wmin, y00, y10)) {
                    double factor = (y00 - wmin) / (y00 - y10);
                    double xa = x0 + factor * (x1 - x0);
                }
                double xb = Double.NaN;
                if (y01 != y11 && Utils2D.spans(wmin, y01, y11)) {
                    double factor = (y01 - wmin) / (y01 - y11);
                    xb = x0 + factor * (x1 - x0);
                }
                double xc = Double.NaN;
                if (y00 != y10 && Utils2D.spans(wmax, y00, y10)) {
                    double factor = (y00 - wmax) / (y00 - y10);
                    xc = x0 + factor * (x1 - x0);
                }
                double xd = Double.NaN;
                if (y01 != y11 && Utils2D.spans(wmax, y01, y11)) {
                    double factor = (y01 - wmax) / (y01 - y11);
                    xd = x0 + factor * (x1 - x0);
                }
                // DEFINE:
                // xa is the x value of the intersection 
                
                obj.addVertex(x0, y0 - hdelta, z0 - wdelta);
                obj.addVertex(x0, y0 + hdelta, z0 - wdelta);
                obj.addVertex(x0, y0 - hdelta, z0 + wdelta);
                obj.addVertex(x0, y0 + hdelta, z0 + wdelta);
                obj.addVertex(x1, y1 - hdelta, z0 + wdelta);
                obj.addVertex(x1, y1 + hdelta, z0 + wdelta);
                obj.addVertex(x1, y1 - hdelta, z0 - wdelta);
                obj.addVertex(x1, y1 + hdelta, z0 - wdelta);
                obj.addFace(new int[] {1, 3, 5, 7}, color, true);
                obj.addFace(new int[] {1, 7, 6, 0}, color, true);
                obj.addFace(new int[] {6, 4, 2, 0}, color, true);
                obj.addFace(new int[] {2, 4, 5, 3}, color, true);
                if (column == 0) {
                    obj.addFace(new int[] {2, 3, 1, 0}, color, true);
                }
                if (column == dataset.getColumnCount() - 2) {
                    obj.addFace(new int[] {7, 5, 4, 6}, color, true);
                }
            } else {
                // ribbon
                obj.addVertex(x0, y0, z0 - wdelta);
                obj.addVertex(x0, y0, z0 + wdelta);
                obj.addVertex(x1, y1, z0 + wdelta);
                obj.addVertex(x1, y1, z0 - wdelta);
                obj.addFace(new int[] {0, 1, 2, 3}, color, true);
                obj.addFace(new int[] {3, 2, 1, 0}, color, true);
            }
            world.add(obj);
        } 
    }

    
    /**
     * Tests this renderer for equality with an arbitrary object.
     * 
     * @param obj  the object (<code>null</code> not permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LineRenderer3D2)) {
            return false;
        }
        LineRenderer3D2 that = (LineRenderer3D2) obj;
        if (this.lineWidth != that.lineWidth) {
            return false;
        }
        if (this.lineHeight != that.lineHeight) {
            return false;
        }
        return super.equals(obj);
    }
}

