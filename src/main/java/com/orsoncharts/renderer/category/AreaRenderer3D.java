/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2016, by Object Refinery Limited.  All rights reserved.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 * 
 * If you do not wish to be bound by the terms of the GPL, an alternative
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts home page:
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 */

package com.orsoncharts.renderer.category;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.orsoncharts.axis.CategoryAxis3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.Range;
import com.orsoncharts.axis.ValueAxis3D;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.data.DataUtils;
import com.orsoncharts.data.Values3D;
import com.orsoncharts.data.KeyedValues3DItemKey;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.Offset3D;
import com.orsoncharts.graphics3d.Utils2D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.label.ItemLabelPositioning;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.renderer.Renderer3DChangeEvent;
import com.orsoncharts.util.ObjectUtils;

/**
 * A renderer for creating 3D area charts from data in a 
 * {@link CategoryDataset3D} (for use with a {@link CategoryPlot3D}). For 
 * example:
 * <div>
 * <object id="ABC" data="../../../../doc-files/AreaChart3DDemo1.svg"  
 * type="image/svg+xml" width="500" height="359"> 
 * </object>
 * </div>
 * (refer to {@code AreaChart3DDemo1.java} for the code to generate the
 * above chart).
 * <br><br>
 * There is a factory method to create a chart using this renderer - see 
 * {@link Chart3DFactory#createAreaChart(String, String, CategoryDataset3D, 
 * String, String, String)}.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 */
@SuppressWarnings("serial")
public class AreaRenderer3D extends AbstractCategoryRenderer3D 
        implements Serializable {
    
    /** The base for the areas (defaults to 0.0). */
    private double base;
    
    /** 
     * The color used to paint the underside of the area object (if 
     * {@code null}, the regular series color is used).
     */
    private Color baseColor;
    
    /** The depth of the area. */
    private double depth;

    /** 
     * For isolated data values this attribute controls the width (x-axis) of 
     * the box representing the data item, it is expressed as a percentage of
     * the category width.
     */
    private double isolatedItemWidthPercent;
    
    /**
     * The color source that determines the color used to highlight clipped
     * items in the chart.
     */
    private CategoryColorSource clipColorSource;

    /** 
     * A flag that controls whether or not outlines are drawn for the faces 
     * making up the area segments. 
     */
    private boolean drawFaceOutlines;
    
    /**
     * Default constructor.
     */
    public AreaRenderer3D() {
        this.base = 0.0;
        this.baseColor = null;
        this.depth = 0.6;
        this.isolatedItemWidthPercent = 0.25;
        this.clipColorSource = new StandardCategoryColorSource(Color.RED);
        this.drawFaceOutlines = true;
    }

    /**
     * Returns the y-value for the base of the area.  The default value is 
     * {@code 0.0}.
     * 
     * @return The base value. 
     */
    public double getBase() {
        return this.base;
    }
    
    /**
     * Sets the base value and sends a change event to all registered listeners.
     * 
     * @param base  the base value. 
     */
    public void setBase(double base) {
        this.base = base;
        fireChangeEvent(true);
    }
    
    /**
     * Returns the color used to paint the underside of the area polygons.
     * The default value is {@code null} (which means the undersides are
     * painted using the regular series color).
     * 
     * @return The color (possibly {@code null}). 
     * 
     * @see #setBaseColor(java.awt.Color) 
     */
    public Color getBaseColor() {
        return this.baseColor;
    }
    
    /**
     * Sets the color for the underside of the area shapes and sends a
     * change event to all registered listeners.  If you set
     * this to {@code null} the base will be painted with the regular
     * series color.
     * 
     * @param color  the color ({@code null} permitted). 
     */
    public void setBaseColor(Color color) {
        this.baseColor = color;
        fireChangeEvent(true);
    }
    
    /**
     * Returns the depth (in 3D) for the area (in world units).  The 
     * default value is {@code 0.6}.
     * 
     * @return The depth.
     */
    public double getDepth() {
        return this.depth;
    }
    
    /**
     * Sets the depth (in 3D) and sends a change event to all registered 
     * listeners.
     * 
     * @param depth  the depth. 
     */
    public void setDepth(double depth) {
        this.depth = depth;
        fireChangeEvent(true);
    }

    /**
     * Returns the color source used to determine the color used to highlight
     * clipping in the chart elements.  If the source is {@code null},
     * then the regular series color is used instead.
     * 
     * @return The color source (possibly {@code null}).
     * 
     * @since 1.3
     */
    public CategoryColorSource getClipColorSource() {
        return this.clipColorSource;
    }
    
    /**
     * Sets the color source that determines the color used to highlight
     * clipping in the chart elements, and sends a {@link Renderer3DChangeEvent}
     * to all registered listeners.
     * 
     * @param source  the source ({@code null} permitted). 
     * 
     * @since 1.3
     */
    public void setClipColorSource(CategoryColorSource source) {
        this.clipColorSource = source;
        fireChangeEvent(true);
    }
    
    /**
     * Returns the flag that controls whether or not the faces making up area
     * segments will be drawn with outlines.  The default value is 
     * {@code true}.  When anti-aliasing is on, the fill area for the 
     * faces will have some gray shades around the edges, and these will show
     * up on the chart as thin lines (usually not visible if you turn off
     * anti-aliasing).  To mask this, the rendering engine can draw an outline
     * around each face in the same color (this usually results in cleaner 
     * output, but it is slower and can introduce some minor visual artifacts
     * as well depending on the output target).
     * 
     * @return A boolean.
     * 
     * @since 1.3
     */
    public boolean getDrawFaceOutlines() {
        return this.drawFaceOutlines;
    }
    
    /**
     * Sets the flag that controls whether or not outlines are drawn for the
     * faces making up the area segments and sends a change event to all
     * registered listeners.
     * 
     * @param outline  the new flag value.
     * 
     * @since 1.3
     */
    public void setDrawFaceOutlines(boolean outline) {
        this.drawFaceOutlines = outline;
        fireChangeEvent(true);
    }
    
    /**
     * Returns the range (for the value axis) that is required for this 
     * renderer to show all the values in the specified data set.  This method
     * is overridden to ensure that the range includes the base value (normally
     * 0.0) set for the renderer.
     * 
     * @param data  the data ({@code null} not permitted).
     * 
     * @return The range. 
     */
    @Override
    public Range findValueRange(Values3D<? extends Number> data) {
        return DataUtils.findValueRange(data, this.base);
    }

    /**
     * Constructs and places one item from the specified dataset into the given 
     * world.  This method will be called by the {@link CategoryPlot3D} class
     * while iterating over the items in the dataset.
     * 
     * @param dataset  the dataset ({@code null} not permitted).
     * @param series  the series index.
     * @param row  the row index.
     * @param column  the column index.
     * @param world  the world ({@code null} not permitted).
     * @param dimensions  the plot dimensions ({@code null} not permitted).
     * @param xOffset  the x-offset.
     * @param yOffset  the y-offset.
     * @param zOffset  the z-offset.
     */
    @Override @SuppressWarnings("unchecked")
    public void composeItem(CategoryDataset3D dataset, int series, int row, 
            int column, World world, Dimension3D dimensions, 
            double xOffset, double yOffset, double zOffset) {
        
        Number y = (Number) dataset.getValue(series, row, column);
        Number yprev = null;
        if (column > 0) {
            yprev = (Number) dataset.getValue(series, row, column - 1);
        }
        Number ynext = null;
        if (column < dataset.getColumnCount() - 1) {
            ynext = (Number) dataset.getValue(series, row, column + 1);
        }

        CategoryPlot3D plot = getPlot();
        CategoryAxis3D rowAxis = plot.getRowAxis();
        CategoryAxis3D columnAxis = plot.getColumnAxis();
        ValueAxis3D valueAxis = plot.getValueAxis();
        Range r = valueAxis.getRange();
        
        Comparable<?> seriesKey = dataset.getSeriesKey(series);
        Comparable<?> rowKey = dataset.getRowKey(row);
        Comparable<?> columnKey = dataset.getColumnKey(column);
        double rowValue = rowAxis.getCategoryValue(rowKey);
        double columnValue = columnAxis.getCategoryValue(columnKey);
        double ww = dimensions.getWidth();
        double hh = dimensions.getHeight();
        double dd = dimensions.getDepth();

        // for any data value, we'll try to create two area segments, one to
        // the left of the value and one to the right of the value (each 
        // halfway to the adjacent data value).  If the adjacent data values
        // are null (or don't exist, as in the case of the first and last data
        // items), then we can create an isolated segment to represent the data
        // item.  The final consideration is whether the opening and closing
        // faces of each segment are filled or not (if the segment connects to
        // another segment, there is no need to fill the end face)
        boolean createLeftSegment, createRightSegment, createIsolatedSegment;
        boolean leftOpen = false;
        boolean leftClose = false;
        boolean rightOpen = false;
        boolean rightClose = false;
        
        // for the first column there is no left segment, we also handle the
        // special case where there is just one column of data in which case
        // the renderer can only show an isolated data value
        if (column == 0) {
            createLeftSegment = false;  // never for first item
            if (dataset.getColumnCount() == 1) {
                createRightSegment = false; 
                createIsolatedSegment = (y != null);
            } else {
                createRightSegment = (y != null && ynext != null);
                rightOpen = true;
                rightClose = false;
                createIsolatedSegment = (y != null && ynext == null);
            }
        } 
        
        // for the last column there is no right segment
        else if (column == dataset.getColumnCount() - 1) { // last column
            createRightSegment = false; // never for the last item
            createLeftSegment = (y != null && yprev != null);
            leftOpen = false;
            leftClose = true;
            createIsolatedSegment = (y != null && yprev == null);
        } 
        
        // for the general case we handle left and right segments or an 
        // isolated segment if the surrounding data values are null
        else { 
            createLeftSegment = (y != null && yprev != null);
            leftOpen = false;
            leftClose = (createLeftSegment && ynext == null);
            createRightSegment = (y != null && ynext != null);
            rightOpen = (createRightSegment && yprev == null);
            rightClose = false;
            createIsolatedSegment = (y != null 
                    && yprev == null && ynext == null);
        }

        // now that we know what we have to create, we'll need some info 
        // for the construction...world coordinates are required
        double xw = columnAxis.translateToWorld(columnValue, ww) + xOffset;
        double yw = Double.NaN;
        if (y != null) {
            yw = valueAxis.translateToWorld(y.doubleValue(), hh) + yOffset; 
        }
        double zw = rowAxis.translateToWorld(rowValue, dd) + zOffset;
        double ywmin = valueAxis.translateToWorld(r.getMin(), hh) + yOffset;
        double ywmax = valueAxis.translateToWorld(r.getMax(), hh) + yOffset;
        double basew = valueAxis.translateToWorld(this.base, hh) + yOffset;
        Color color = getColorSource().getColor(series, row, column);
        Color clipColor = color;  
        if (getClipColorSource() != null) {
            Color c = getClipColorSource().getColor(series, row, column);
            if (c != null) {
                clipColor = c;
            }
        }
        KeyedValues3DItemKey itemKey = new KeyedValues3DItemKey(seriesKey, 
                rowKey, columnKey);
 
        if (createLeftSegment) {
            Comparable<?> prevColumnKey = dataset.getColumnKey(column - 1);
            double prevColumnValue = columnAxis.getCategoryValue(prevColumnKey);
            double prevColumnX = columnAxis.translateToWorld(prevColumnValue, 
                    ww) + xOffset;
            double xl = (prevColumnX + xw) / 2.0;
            assert yprev != null;  // we know this because createLeftSegment is 
                                   // not 'true' otherwise
            double yprevw = valueAxis.translateToWorld(yprev.doubleValue(), hh) 
                    + yOffset; 
            double yl = (yprevw + yw) / 2.0;
            List<Object3D> leftObjs = createSegment(xl, yl, xw, yw, zw, 
                    basew, ywmin, ywmax, color, this.baseColor, clipColor, 
                    leftOpen, leftClose);
            for (Object3D obj : leftObjs) {
                obj.setProperty(Object3D.ITEM_KEY, itemKey);
                obj.setOutline(this.drawFaceOutlines);
                world.add(obj);
            }
        }

        if (createRightSegment) {
            Comparable<?> nextColumnKey = dataset.getColumnKey(column + 1);
            double nextColumnValue = columnAxis.getCategoryValue(nextColumnKey);
            double nextColumnX = columnAxis.translateToWorld(nextColumnValue, 
                    ww) + xOffset;
            double xr = (nextColumnX + xw) / 2.0;
            assert ynext != null; // we know this because createRightSegment is
                                  // not 'true' otherwise
            double ynextw = valueAxis.translateToWorld(ynext.doubleValue(), hh) 
                    + yOffset; 
            double yr = (ynextw + yw) / 2.0;
            List<Object3D> rightObjs = createSegment(xw, yw, xr, yr, zw, 
                    basew, ywmin, ywmax, color, this.baseColor, clipColor, 
                    rightOpen, rightClose);
            for (Object3D obj : rightObjs) {
                obj.setProperty(Object3D.ITEM_KEY, itemKey);
                obj.setOutline(this.drawFaceOutlines);
                world.add(obj);
            }
        }

        if (createIsolatedSegment) {
            double cw = columnAxis.getCategoryWidth() 
                    * this.isolatedItemWidthPercent;
            double cww = columnAxis.translateToWorld(cw, ww);
            double h = yw - basew;
            Object3D isolated = Object3D.createBox(xw, cww, yw - h / 2, h, 
                    zw, this.depth, color);
            isolated.setOutline(this.drawFaceOutlines);
            isolated.setProperty(Object3D.ITEM_KEY, itemKey);
            world.add(isolated);
        }
        
        if (getItemLabelGenerator() != null && !Double.isNaN(yw) 
                && yw >= ywmin && yw <= ywmax) {
            String label = getItemLabelGenerator().generateItemLabel(dataset, 
                    seriesKey, rowKey, columnKey);
            ItemLabelPositioning positioning = getItemLabelPositioning();
            Offset3D offsets = getItemLabelOffsets();
            double ydelta = dimensions.getHeight() * offsets.getDY();
            if (yw < basew) {
                ydelta = -ydelta;
            }
            if (positioning.equals(ItemLabelPositioning.CENTRAL)) {
                Object3D labelObj = Object3D.createLabelObject(label, 
                        getItemLabelFont(), getItemLabelColor(), 
                        getItemLabelBackgroundColor(), xw, yw + ydelta, zw, 
                        false, true);
                
                labelObj.setProperty(Object3D.ITEM_KEY, itemKey);
                world.add(labelObj);
            } else if (positioning.equals(
                    ItemLabelPositioning.FRONT_AND_BACK)) {
                double zdelta = this.depth / 2 * offsets.getDZ();
                Object3D labelObj1 = Object3D.createLabelObject(label, 
                        getItemLabelFont(), getItemLabelColor(), 
                        getItemLabelBackgroundColor(), xw, yw + ydelta, 
                        zw - zdelta, false, false);
                labelObj1.setProperty(Object3D.CLASS_KEY, "ItemLabel");
                labelObj1.setProperty(Object3D.ITEM_KEY, itemKey);
                world.add(labelObj1);
                Object3D labelObj2 = Object3D.createLabelObject(label, 
                        getItemLabelFont(), getItemLabelColor(), 
                        getItemLabelBackgroundColor(), xw, yw + ydelta, 
                        zw + zdelta, true, false);
                labelObj2.setProperty(Object3D.CLASS_KEY, "ItemLabel");
                labelObj2.setProperty(Object3D.ITEM_KEY, itemKey);
                world.add(labelObj2);
            } 
        }
    }

    /**
     * Creates objects to represent the area segment between (x0, y0) and
     * (x1, y1).
     * 
     * @param x0
     * @param y0
     * @param x1
     * @param y1
     * @param z
     * @param lineWidth
     * @param lineHeight
     * @param ymin
     * @param ymax
     * @param color
     * @param clipColor
     * @param openingFace
     * @param closingFace
     * 
     * @return A list of objects making up the segment. 
     */
    private List<Object3D> createSegment(double x0, double y0, double x1, 
            double y1, double z, double base, double ymin, double ymax, 
            Color color, Color baseColor, Color clipColor, boolean openingFace, 
            boolean closingFace) {
        
        List<Object3D> result = new ArrayList<Object3D>(2);
        // either there is a crossing or there is not
        if (!isBaselineCrossed(y0, y1, base)) {
            Object3D segment = createSegmentWithoutCrossing(x0, y0, x1, y1, 
                    z, base, ymin, ymax, color, baseColor, clipColor, 
                    openingFace, closingFace);
            result.add(segment);
        } else {
            result.addAll(createSegmentWithCrossing(x0, y0, x1, y1, 
                    z, base, ymin, ymax, color, baseColor, clipColor, 
                    openingFace, closingFace));
        }
        return result;    
    }

    /**
     * Returns {@code true} if the two values are on opposite sides of 
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
    
    private Object3D createSegmentWithoutCrossing(double x0, double y0, 
            double x1, double y1, double z, double base, double ymin, 
            double ymax, Color color, Color baseColor, Color clipColor, 
            boolean openingFace, boolean closingFace) {
   
        boolean positive = y0 > base || y1 > base;
        if (positive) {            
            Object3D pos = createPositiveArea(x0, y0, x1, y1, base, 
                    z, new Range(ymin, ymax), color, openingFace, 
                    closingFace);
            return pos;
        } else {
            Object3D neg = createNegativeArea(x0, y0, x1, y1, base, z, 
                    new Range(ymin, ymax), color, openingFace, closingFace);
            return neg;
        }
    }
    
    private List<Object3D> createSegmentWithCrossing(double x0, double y0, 
            double x1, double y1, double z, double base, double ymin, 
            double ymax, Color color, Color baseColor, Color clipColor, 
            boolean openingFace, boolean closingFace) {
        List<Object3D> result = new ArrayList<Object3D>(2);
        Range range = new Range(ymin, ymax);
        // find the crossing point
        double ydelta = Math.abs(y1 - y0);
        double factor = 0;
        if (ydelta != 0.0) {
            factor = Math.abs(y0 - base) / ydelta;
        }
        double xcross = x0 + factor * (x1 - x0);
        if (y0 > base) {
            Object3D pos = createPositiveArea(x0, y0, xcross, base, base, z, 
                    range, color, openingFace, closingFace);
            if (pos != null) {
                result.add(pos);
            }
            Object3D neg = createNegativeArea(xcross, base, x1, y1, 
                    base, z, range, color, openingFace, closingFace);
            if (neg != null) {
                result.add(neg);
            }
        } else {
            Object3D neg = createNegativeArea(x0, y0, xcross, base, 
                    base, z, range, color, openingFace, closingFace);
            if (neg != null) {
                result.add(neg);
            }
            Object3D pos = createPositiveArea(xcross, base, x1, y1, base, 
                    z, range, color, openingFace, closingFace);
            if (pos != null) {
                result.add(pos);
            }
        }
        return result;
    }
    
    /**
     * A utility method that returns the fraction (x - x0) / (x1 - x0), which 
     * is used for some interpolation calculations.
     * 
     * @param x  the x-value.
     * @param x0  the start of a range.
     * @param x1  the end of a range.
     * 
     * @return The fractional value of x along the range x0 to x1. 
     */
    private double fraction(double x, double x0, double x1) {
        double dist = x - x0;
        double length = x1 - x0;
        return dist / length;
    }

    /** 
     * A value in world units that is considered small enough to be not
     * significant.  We use this to check if two coordinates are "more or less"
     * the same.
     */
    private static final double EPSILON = 0.001;
    
    /**
     * Creates a 3D object to represent a positive "area", taking into account
     * that the visible range can be restricted.
     * 
     * @param color  the color ({@code null} not permitted).
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
     * @return A 3D object or {@code null}. 
     */
    private Object3D createPositiveArea(double wx0, double wy0, 
            double wx1, double wy1, double wbase, double wz, Range range,
            Color color, boolean openingFace, boolean closingFace) {

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
        Object3D obj = new Object3D(color, true);
        obj.addVertex(wx00, wbb, wz - delta);
        obj.addVertex(wx00, wbb, wz + delta);
        boolean leftSide = false;
        if (Math.abs(wy00 - wbb) > EPSILON) {
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
        if (Math.abs(wy11 - wbb) > EPSILON) {
            rightSide = true;
            obj.addVertex(wx11, wbb, wz - delta);
            obj.addVertex(wx11, wbb, wz + delta);
        }
        int vertices = obj.getVertexCount();
        
        if (vertices == 10) {
            obj.addFace(new int[] {0, 2, 4, 6, 8});  // front
            obj.addFace(new int[] {1, 9, 7, 5, 3});  // rear
            obj.addFace(new int[] {0, 8, 9, 1});  // base
            obj.addFace(new int[] {2, 3, 5, 4}); // top 1
            obj.addFace(new int[] {4, 5, 7, 6});  // top 2
            if (openingFace) {
                obj.addFace(new int[] {0, 1, 3, 2});
            }
            if (closingFace) {
                obj.addFace(new int[] {6, 7, 9, 8});
            }
        } else if (vertices == 8) {
            obj.addFace(new int[] {0, 2, 4, 6});  // front
            obj.addFace(new int[] {7, 5, 3, 1});  // rear
            if (!leftSide) {
                obj.addFace(new int[] {0, 1, 3, 2});  // top left
            }
            obj.addFace(new int[] {2, 3, 5, 4});  // top 1
            if (!rightSide) {
                obj.addFace(new int[] {4, 5, 7, 6}); // top 2 
            }
            obj.addFace(new int[] {1, 0, 6, 7}); // base
            if (openingFace) {
                obj.addFace(new int[] {0, 1, 3, 2});
            }
            if (closingFace) {
                obj.addFace(new int[] {4, 5, 7, 6});
            }
        } else if (vertices == 6) {
            obj.addFace(new int[] {0, 2, 4}); // front
            obj.addFace(new int[] {5, 3, 1}); // rear
            if (leftSide) {
                obj.addFace(new int[] {3, 5, 4, 2}); // top            
                if (openingFace) {
                    obj.addFace(new int[] {0, 1, 3, 2});
                }
            } else {
                obj.addFace(new int[] {0, 1, 3, 2}); // top
                if (closingFace) {
                    obj.addFace(new int[] {2, 3, 5, 4});
                }
            }
            obj.addFace(new int[] {0, 4, 5, 1}); // base            
        } else {
            obj.addFace(new int[] {0, 1, 3, 2});
            obj.addFace(new int[] {2, 3, 1, 0});
        }
        return obj;
    }
    
    /**
     * Creates a negative area shape from (wx0, wy0) to (wx1, wy1) with the
     * base at wbase (it is assumed that both wy0 and wy1 are less than wbase).
     * 
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
     * 
     * @return An object representing the area shape (or {@code null}). 
     */
    private Object3D createNegativeArea(double wx0, double wy0, 
            double wx1, double wy1, double wbase, double wz, Range range,
            Color color, boolean openingFace, boolean closingFace) {
        
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
        Object3D obj = new Object3D(color, true);
        obj.addVertex(wx00, wbb, wz - delta);
        obj.addVertex(wx00, wbb, wz + delta);
        boolean leftSide = false;
        if (Math.abs(wy00 - wbb) > EPSILON) {
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
        if (Math.abs(wy11 - wbb) > EPSILON) {
            obj.addVertex(wx11, wbb, wz - delta);
            obj.addVertex(wx11, wbb, wz + delta);
        }
        int vertices = obj.getVertexCount();
        if (vertices == 10) {
            obj.addFace(new int[] {8, 6, 4, 2, 0});  // front
            obj.addFace(new int[] {1, 3, 5, 7, 9});  // rear
            obj.addFace(new int[] {1, 9, 8, 0});  // base
            obj.addFace(new int[] {4, 5, 3, 2}); // top 1
            obj.addFace(new int[] {6, 7, 5, 4});  // top 2
            if (openingFace) {
                obj.addFace(new int[] {2, 3, 1, 0});
            }
            if (closingFace) {
                obj.addFace(new int[] {8, 9, 7, 6});
            }
        } else if (vertices == 8) {
            obj.addFace(new int[] {2, 0, 6, 4});  // front
            obj.addFace(new int[] {1, 3, 5, 7});  // rear
            obj.addFace(new int[] {0, 1, 7, 6});  // base
            if (!leftSide) {
                obj.addFace(new int[] {2, 3, 1, 0});            
            }
            obj.addFace(new int[] {3, 2, 4, 5});  // negative top
            if (!rightSide) {
                obj.addFace(new int[] {6, 7, 5, 4});
            }
            if (openingFace) {
                obj.addFace(new int[] {1, 0, 2, 3});
            }
            if (closingFace) {
                obj.addFace(new int[] {5, 4, 6, 7});
            }
        } else if (vertices == 6) {
            obj.addFace(new int[] {4, 2, 0});  // front  
            obj.addFace(new int[] {1, 3, 5});  // rear
            if (leftSide) {
                obj.addFace(new int[] {4, 5, 3, 2});  // negative top
                if (openingFace) {
                    obj.addFace(new int[] {1, 0, 2, 3});
                }
            } else {
                obj.addFace(new int[] {2, 3, 1, 0}); // negative top               
                if (closingFace) {
                    obj.addFace(new int[] {3, 2, 4, 5});
                }
            }
            obj.addFace(new int[] {0, 1, 5, 4});  // base
        } else {
            obj.addFace(new int[] {0, 1, 3, 2});
            obj.addFace(new int[] {2, 3, 1, 0});
        }
        return obj;
    }
    
    /**
     * Tests this renderer for equality with an arbitrary object.
     * 
     * @param obj  the object ({@code null} permitted).
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
