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

import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.Range;
import com.orsoncharts.axis.CategoryAxis3D;
import com.orsoncharts.axis.ValueAxis3D;
import com.orsoncharts.data.KeyedValues3DItemKey;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.Offset3D;
import com.orsoncharts.graphics3d.World;
import com.orsoncharts.label.ItemLabelPositioning;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.renderer.Renderer3DChangeEvent;
import com.orsoncharts.util.ObjectUtils;

/**
 * A renderer that can be used with the {@link CategoryPlot3D} class to create
 * 3D lines charts from data in a {@link CategoryDataset3D}.  The 
 * {@code createLineChart()} method in the {@link Chart3DFactory} class 
 * will construct a chart that uses this renderer.  Here is a sample:
 * <div>
 * <object id="ABC" data="../../../../doc-files/LineChart3DDemo1.svg"  
 * type="image/svg+xml" width="500" height="359"></object>
 * </div>
 * (refer to {@code LineChart3DDemo1.java} for the code to generate the
 * above chart).
 * <br><br>
 * Some attributes in the renderer are specified in "world units" - see the
 * {@link Chart3D} class description for more information about world units.
 * <br><br>
 * There is a factory method to create a chart using this renderer - see
 * {@link Chart3DFactory#createLineChart(String, String, CategoryDataset3D, 
 * String, String, String)}.
 * <br><br> 
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 */
@SuppressWarnings("serial")
public class LineRenderer3D extends AbstractCategoryRenderer3D 
        implements Serializable {

    /** The line width (in world units). */
    private double lineWidth;
    
    /** The line height (in world units). */
    private double lineHeight;

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
     * Creates a new instance with default attribute values.
     */
    public LineRenderer3D() {
        this.lineWidth = 0.4;
        this.lineHeight = 0.2;
        this.isolatedItemWidthPercent = 0.25;
        this.clipColorSource = new StandardCategoryColorSource(Color.RED);
    }
    
    /**
     * Returns the line width in world units.  The default value is 
     * {@code 0.4}.
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
        fireChangeEvent(true);
    }

    /**
     * Returns the line height in world units.  The default value is 
     * {@code 0.2}.
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
        fireChangeEvent(true);
    }

    /**
     * Returns the width for isolated data items as a percentage of the
     * category width.  The default value is 0.25 (twenty five percent).
     * 
     * @return The width percentage.
     * 
     * @since 1.3
     */
    public double getIsolatedItemWidthPercent() {
        return this.isolatedItemWidthPercent;
    }
    
    /**
     * Sets the width for isolated data items as a percentage of the category
     * width and sends a change event to all registered listeners.
     * 
     * @param percent  the new percentage.
     * 
     * @since 1.3
     */
    public void setIsolatedItemWidthPercent(double percent) {
        this.isolatedItemWidthPercent = percent;
        fireChangeEvent(true);
    }
    
    /**
     * Returns the color source used to determine the color used to highlight
     * clipping in the chart elements.  If the source is {@code null},
     * then the regular series color is used instead.
     * 
     * @return The color source (possibly {@code null}). 
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
     */
    public void setClipColorSource(CategoryColorSource source) {
        this.clipColorSource = source;
        fireChangeEvent(true);
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
    @Override
    @SuppressWarnings("unchecked")
    public void composeItem(CategoryDataset3D dataset, int series, int row, 
            int column, World world, Dimension3D dimensions, 
            double xOffset, double yOffset, double zOffset) {
        
        // there is a lot of brute force code underneath this compose method
        // because I haven't seen the pattern yet that will let me reduce it
        // to something more elegant...probably I'm not smart enough.
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

        // for any data value, we'll try to create two line segments, one to
        // the left of the value and one to the right of the value (each 
        // halfway to the adjacent data value).  If the adjacent data values
        // are null (or don't exist, as in the case of the first and last data
        // items, then we can create an isolated segment to represent the data
        // item.  The final consideration is whether the opening and closing
        // faces of each segment are filled or not (if the segment connects to
        // another segment, there is no need to fill the end face)
        boolean createLeftSegment, createRightSegment, createIsolatedSegment;
        boolean leftOpen = false;
        boolean leftClose = false;
        boolean rightOpen = false;
        boolean rightClose = false;
        if (column == 0) { // first column is a special case
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
        } else if (column == dataset.getColumnCount() - 1) { // last column
            createRightSegment = false; // never for the last item
            createLeftSegment = (y != null && yprev != null);
            leftOpen = false;
            leftClose = true;
            createIsolatedSegment = (y != null && yprev == null);
        } else { // this is the general case
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
        // for the construction
        double xw = columnAxis.translateToWorld(columnValue, ww) + xOffset;
        double yw = Double.NaN;
        if (y != null) {
            yw = valueAxis.translateToWorld(y.doubleValue(), hh) + yOffset; 
        }
        double zw = rowAxis.translateToWorld(rowValue, dd) + zOffset;
        double ywmin = valueAxis.translateToWorld(r.getMin(), hh) + yOffset;
        double ywmax = valueAxis.translateToWorld(r.getMax(), hh) + yOffset;
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
            double yprevw = valueAxis.translateToWorld(yprev.doubleValue(), hh) 
                    + yOffset; 
            double yl = (yprevw + yw) / 2.0;
            Object3D left = createSegment(xl, yl, xw, yw, zw, this.lineWidth, 
                    this.lineHeight, ywmin, ywmax, color, clipColor, leftOpen, 
                    leftClose);
            if (left != null) {
                left.setProperty(Object3D.ITEM_KEY, itemKey);
                world.add(left);
            }
        }
        if (createRightSegment) {
            Comparable<?> nextColumnKey = dataset.getColumnKey(column + 1);
            double nextColumnValue = columnAxis.getCategoryValue(nextColumnKey);
            double nextColumnX = columnAxis.translateToWorld(nextColumnValue, 
                    ww) + xOffset;
            double xr = (nextColumnX + xw) / 2.0;
            double ynextw = valueAxis.translateToWorld(ynext.doubleValue(), hh) 
                    + yOffset; 
            double yr = (ynextw + yw) / 2.0;
            Object3D right = createSegment(xw, yw, xr, yr, zw, this.lineWidth, 
                    this.lineHeight, ywmin, ywmax, color, clipColor, rightOpen, 
                    rightClose);
            if (right != null) {
                right.setProperty(Object3D.ITEM_KEY, itemKey);
                world.add(right);
            }
        }
        if (createIsolatedSegment) {
            double cw = columnAxis.getCategoryWidth() 
                    * this.isolatedItemWidthPercent;
            double cww = columnAxis.translateToWorld(cw, ww);
            Object3D isolated = Object3D.createBox(xw, cww, yw, this.lineHeight, 
                    zw, this.lineWidth, color);
            if (isolated != null) {
                isolated.setProperty(Object3D.ITEM_KEY, itemKey);
                world.add(isolated);
            }
        }
        
        if (getItemLabelGenerator() != null && !Double.isNaN(yw) 
                && yw >= ywmin && yw <= ywmax) {
            String label = getItemLabelGenerator().generateItemLabel(dataset, 
                    seriesKey, rowKey, columnKey);
            if (label != null) {
                ItemLabelPositioning positioning = getItemLabelPositioning();
                Offset3D offsets = getItemLabelOffsets();
                double dy = offsets.getDY() * dimensions.getHeight();
                if (positioning.equals(ItemLabelPositioning.CENTRAL)) {
                    Object3D labelObj = Object3D.createLabelObject(label, 
                            getItemLabelFont(), getItemLabelColor(), 
                            getItemLabelBackgroundColor(),
                            xw, yw + dy, zw, false, true);
                    labelObj.setProperty(Object3D.ITEM_KEY, itemKey);
                    world.add(labelObj);
                } else if (positioning.equals(
                        ItemLabelPositioning.FRONT_AND_BACK)) {
                    double dz = this.lineWidth ;
                    Object3D labelObj1 = Object3D.createLabelObject(label, 
                            getItemLabelFont(), getItemLabelColor(), 
                            getItemLabelBackgroundColor(),
                            xw, yw, zw - dz, false, false);
                    labelObj1.setProperty(Object3D.ITEM_KEY, itemKey);
                    world.add(labelObj1);
                    Object3D labelObj2 = Object3D.createLabelObject(label, 
                            getItemLabelFont(), getItemLabelColor(), 
                            getItemLabelBackgroundColor(),
                            xw, yw, zw + dz, true, false);
                    labelObj2.setProperty(Object3D.ITEM_KEY, itemKey);
                    world.add(labelObj2);
                }
            }
        }
    }

    /**
     * Creates a segment of a line between (x0, y0, z) and (x1, y1, z), with
     * the specified line width and height, taking into account the minimum
     * and maximum world coordinates (in the y-direction, because it is assumed
     * that we have the full x and z-range required).
     * 
     * @param x0  the starting x-coordinate.
     * @param y0  the starting x-coordinate.
     * @param x1  the ending x-coordinate.
     * @param y1  the ending y-coordinate.
     * @param z  the z-coordinate.
     * @param lineWidth  the line width (z-axis).
     * @param lineHeight  the line height (y-axis).
     * @param ymin  the lower bound for y-values.
     * @param ymax  the upper bound for y-values.
     * @param color  the segment color.
     * @param clipColor  the clip color (for the faces in the segment that are
     *     clipped against the edge of the world).
     * @param openingFace  is an opening face required?
     * @param closingFace  is a closing face required?
     * 
     * @return A 3D object that is a segment in a line.
     */
    private Object3D createSegment(double x0, double y0, double x1, double y1,
            double z, double lineWidth, double lineHeight, double ymin, 
            double ymax, Color color, Color clipColor, boolean openingFace,
            boolean closingFace) {
        double wdelta = lineWidth / 2.0;
        double hdelta = lineHeight / 2.0;
        double y0b = y0 - hdelta;
        double y0t = y0 + hdelta;
        double y1b = y1 - hdelta;
        double y1t = y1 + hdelta;
        double zf = z - wdelta;
        double zb = z + wdelta;
        double[] xpts = calcCrossPoints(x0, x1, y0b, y0t, y1b, y1t, ymin, ymax);
        Object3D seg = null;
        if (y0b >= ymax) {  // CASE A 
            seg = createSegmentA(x0, x1, xpts, y0b, y0t, y1b, y1t, 
            ymin, ymax, zf, zb, color, clipColor, false, closingFace);
        } else if (y0t > ymax && y0b > ymin) {  // CASE B
            seg = createSegmentB(x0, x1, xpts, y0b, y0t, y1b, y1t, ymin, ymax, 
                    zf, zb, color, clipColor, openingFace, closingFace);
        } else if (y0t > ymax && y0b <= ymin) {  // CASE C
            seg = createSegmentC(x0, x1, xpts, y0b, y0t, y1b, y1t, ymin, ymax, 
                    zf, zb, color, clipColor, openingFace, closingFace);
        } else if (y0t > ymin && y0b >= ymin) { // CASE D
            seg = createSegmentD(x0, x1, xpts, y0b, y0t, y1b, y1t, ymin, ymax, 
                    zf, zb, color, clipColor, openingFace, closingFace);                    
        } else if (y0t > ymin && y0b < ymin) { // CASE E
            seg = createSegmentE(x0, x1, xpts, y0b, y0t, y1b, y1t, ymin, ymax, 
                    zf, zb, color, clipColor, openingFace, closingFace);                    
        } else if (y0t <= ymin) {  // CASE F
            seg = createSegmentF(x0, x1, xpts, y0b, y0t, y1b, y1t, ymin, ymax, 
                    zf, zb, color, clipColor, false, closingFace);                   
        }
        return seg;
    }
    
    /**
     * Calculates the four intersection points between two horizontal lines
     * (ymin and ymax) and the lines (x0, y0b, x1, y1b) and (x0, y1t, x1, y1t)
     * and returns the x-coordinates in an array.
     * 
     * @param x0
     * @param x1
     * @param y0b
     * @param y0t
     * @param y1b
     * @param y1t
     * @param ymin
     * @param ymax
     * 
     * @return An array of 4 x-coordinates. 
     */
    private double[] calcCrossPoints(double x0, double x1, double y0b, 
            double y0t, double y1b, double y1t, double ymin, double ymax) {
        double[] xpts = new double[] { Double.NaN, Double.NaN, Double.NaN, 
            Double.NaN };
        double factor = (y0b - ymin) / (y0b - y1b);
        xpts[0] = x0 + factor * (x1 - x0);
        factor = (y0t - ymin) / (y0t - y1t);
        xpts[1] = x0 + factor * (x1 - x0);
        factor = (y0b - ymax) / (y0b - y1b);
        xpts[2] = x0 + factor * (x1 - x0);
        factor = (y0t - ymax) / (y0t - y1t);
        xpts[3] = x0 + factor * (x1 - x0);
        return xpts;
    }
    
    /**
     * Creates a segment for the case where the start of the segment is 
     * completely above the upper bound of the axis at the left side of the
     * chart.
     * 
     * @param x0
     * @param x1
     * @param xpts
     * @param y0b
     * @param y0t
     * @param y1b
     * @param y1t
     * @param wmin
     * @param wmax
     * @param zf
     * @param zb
     * @param color
     * @param clipColor
     * @param openingFace  ignored because there is no opening face for this
     *     case.
     * @param closingFace
     * 
     * @return A segment ({@code null} if the segment is entirely clipped). 
     */
    private Object3D createSegmentA(double x0, double x1, double[] xpts, 
            double y0b, double y0t, double y1b, double y1t, double wmin, 
            double wmax, double zf, double zb, Color color, Color clipColor, 
            boolean openingFace, boolean closingFace) {
        if (y1b > wmax) {
            return null;  // nothing is visible
        }
        if (y1t > wmax) {
            if (y1b >= wmin) {
                // create a triangle with the top and right
                Object3D seg = new Object3D(color, true);
                seg.setProperty(Object3D.COLOR_PREFIX + "clip", clipColor);
                seg.addVertex(xpts[2], wmax, zf);
                seg.addVertex(xpts[2], wmax, zb);
                seg.addVertex(x1, wmax, zf);
                seg.addVertex(x1, wmax, zb);
                seg.addVertex(x1, y1b, zf);
                seg.addVertex(x1, y1b, zb);
                seg.addFace(new int[] {0, 2, 4}); // front
                seg.addFace(new int[] {1, 5, 3}); // rear
                seg.addFace(new int[] {0, 1, 3, 2}, "clip"); // clip top
                seg.addFace(new int[] {4, 5, 1, 0}); // bottom
                if (closingFace) {
                    seg.addFace(new int[] {2, 3, 5, 4});
                }
                return seg;
            } else {
                Object3D seg = new Object3D(color, true);
                seg.setProperty(Object3D.COLOR_PREFIX + "clip", clipColor);
                seg.addVertex(xpts[2], wmax, zf);
                seg.addVertex(xpts[2], wmax, zb);
                seg.addVertex(x1, wmax, zf);
                seg.addVertex(x1, wmax, zb);
                seg.addVertex(x1, wmin, zf);
                seg.addVertex(x1, wmin, zb);
                seg.addVertex(xpts[0], wmin, zf);
                seg.addVertex(xpts[0], wmin, zb);
                seg.addFace(new int[] {0, 2, 4, 6}); // front
                seg.addFace(new int[] {1, 7, 5, 3}); // rear
                seg.addFace(new int[] {0, 1, 3, 2}); // clip top
                seg.addFace(new int[] {4, 5, 7, 6}, "clip"); // clip bottom
                seg.addFace(new int[] {6, 7, 1, 0}); // bottom
                if (closingFace) {
                    seg.addFace(new int[] {2, 3, 5, 4});
                }
                return seg;
            }
        } else if (y1t >= wmin) {
            if (y1b >= wmin) {
                Object3D seg = new Object3D(color, true);
                seg.setProperty(Object3D.COLOR_PREFIX + "clip", clipColor);
                seg.addVertex(xpts[2], wmax, zf);
                seg.addVertex(xpts[2], wmax, zb);
                seg.addVertex(xpts[3], wmax, zf);
                seg.addVertex(xpts[3], wmax, zb);
                seg.addVertex(x1, y1t, zf);
                seg.addVertex(x1, y1t, zb);
                seg.addVertex(x1, y1b, zf);
                seg.addVertex(x1, y1b, zb);
                seg.addFace(new int[] {0, 2, 4, 6}); // front
                seg.addFace(new int[] {1, 7, 5, 3}); // rear
                seg.addFace(new int[] {0, 1, 3, 2}, "clip"); // clip top
                seg.addFace(new int[] {2, 3, 5, 4}); // top
                seg.addFace(new int[] {6, 7, 1, 0}); // bottom
                if (closingFace) {
                    seg.addFace(new int[] {4, 5, 7, 6});
                }
                return seg;
            } else {
                Object3D seg = new Object3D(color, true);
                seg.setProperty(Object3D.COLOR_PREFIX + "clip", clipColor);
                seg.addVertex(xpts[2], wmax, zf);
                seg.addVertex(xpts[2], wmax, zb);
                seg.addVertex(xpts[3], wmax, zf);
                seg.addVertex(xpts[3], wmax, zb);
                seg.addVertex(x1, y1t, zf);
                seg.addVertex(x1, y1t, zb);
                seg.addVertex(x1, wmin, zf);
                seg.addVertex(x1, wmin, zb);
                seg.addVertex(xpts[0], wmin, zf);
                seg.addVertex(xpts[0], wmin, zb);
                seg.addFace(new int[] {0, 2, 4, 6, 8}); // front
                seg.addFace(new int[] {1, 9, 7, 5, 3}); // rear
                seg.addFace(new int[] {2, 3, 5, 4});
                seg.addFace(new int[] {0, 1, 3, 2}, "clip"); // clip top
                seg.addFace(new int[] {6, 7, 9, 8}, "clip"); // clip bottom
                seg.addFace(new int[] {8, 9, 1, 0});
                // there is no opening face in this case
                if (closingFace) {
                    seg.addFace(new int[] {4, 5, 7, 6});
                }
                return seg;
            }
        } else {
            Object3D seg = new Object3D(color, true);
            seg.setProperty(Object3D.COLOR_PREFIX + "clip", clipColor);
            seg.addVertex(xpts[2], wmax, zf);
            seg.addVertex(xpts[2], wmax, zb);
            seg.addVertex(xpts[3], wmax, zf);
            seg.addVertex(xpts[3], wmax, zb);
            seg.addVertex(xpts[1], wmin, zf);
            seg.addVertex(xpts[1], wmin, zb);
            seg.addVertex(xpts[0], wmin, zf);
            seg.addVertex(xpts[0], wmin, zb);
            seg.addFace(new int[] {0, 2, 4, 6}); // front
            seg.addFace(new int[] {1, 7, 5, 3}); // rear
            seg.addFace(new int[] {4, 2, 3, 5}); // top
            seg.addFace(new int[] {0, 6, 7, 1}); // bottom
            seg.addFace(new int[] {0, 1, 3, 2}, "clip"); // clip top
            seg.addFace(new int[] {4, 5, 7, 6}, "clip"); // clip bottom
            // there are no opening or closing faces in this case
            return seg;
        }
    }
    
    /**
     * Creates a segment for the case where the left end of the line spans
     * the axis maximum on the left side of the chart.
     * 
     * @param x0
     * @param x1
     * @param xpts
     * @param y0b
     * @param y0t
     * @param y1b
     * @param y1t
     * @param wmin
     * @param wmax
     * @param zf
     * @param zb
     * @param color
     * @param clipColor
     * @param openingFace
     * @param closingFace
     * 
     * @return A segment. 
     */
    private Object3D createSegmentB(double x0, double x1, double[] xpts, 
            double y0b, double y0t, double y1b, double y1t, double wmin, 
            double wmax, double zf, double zb, Color color, Color clipColor, 
            boolean openingFace, boolean closingFace) {
        
        if (y1b >= wmax) {
            Object3D seg = new Object3D(color, true);
            seg.setProperty(Object3D.COLOR_PREFIX + "clip", clipColor);
            seg.addVertex(x0, y0b, zf);
            seg.addVertex(x0, y0b, zb);
            seg.addVertex(x0, wmax, zf);
            seg.addVertex(x0, wmax, zb);
            seg.addVertex(xpts[2], wmax, zf);
            seg.addVertex(xpts[2], wmax, zb);
            seg.addFace(new int[] {0, 2, 4}); // front
            seg.addFace(new int[] {1, 5, 3});  // rear
            seg.addFace(new int[] {0, 4, 5, 1}); // bottom
            seg.addFace(new int[] {2, 3, 5, 4}, "clip"); // clip top
            if (openingFace) {
                seg.addFace(new int[] {0, 1, 3, 2}); 
            }
            // there is no closing face in this case
            return seg; 
        }
        if (y1t > wmax) {
            if (y1b >= wmin) {
                Object3D seg = new Object3D(color, true);
                seg.setProperty(Object3D.COLOR_PREFIX + "clip", clipColor);
                seg.addVertex(x0, y0b, zf);
                seg.addVertex(x0, y0b, zb);
                seg.addVertex(x0, wmax, zf);
                seg.addVertex(x0, wmax, zb);
                seg.addVertex(x1, wmax, zf);
                seg.addVertex(x1, wmax, zb);
                seg.addVertex(x1, y1b, zf);
                seg.addVertex(x1, y1b, zb);
                seg.addFace(new int[] {0, 2, 4, 6});  // front
                seg.addFace(new int[] {1, 7, 5, 3}); // rear
                seg.addFace(new int[] {2, 3, 5, 4}, "clip"); // clip top
                seg.addFace(new int[] {0, 6, 7, 1}); // bottom
                if (openingFace) {
                    seg.addFace(new int[] {0, 1, 3, 2});
                }
                if (closingFace) {
                    seg.addFace(new int[] {4, 5, 7, 6});
                }
                return seg;
            } else {
                Object3D seg = new Object3D(color, true);
                seg.setProperty(Object3D.COLOR_PREFIX + "clip", clipColor);
                seg.addVertex(x0, y0b, zf);
                seg.addVertex(x0, y0b, zb);
                seg.addVertex(x0, wmax, zf);
                seg.addVertex(x0, wmax, zb);
                seg.addVertex(x1, wmax, zf);
                seg.addVertex(x1, wmax, zb);
                seg.addVertex(x1, wmin, zf);
                seg.addVertex(x1, wmin, zb);
                seg.addVertex(xpts[0], wmin, zf);
                seg.addVertex(xpts[0], wmin, zb);
                seg.addFace(new int[] {0, 2, 4, 6, 8});  // front
                seg.addFace(new int[] {1, 9, 7, 5, 3}); // rear
                seg.addFace(new int[] {2, 3, 5, 4}, "clip"); // clip top
                seg.addFace(new int[] {8, 6, 7, 9}, "clip"); // clip bottom
                seg.addFace(new int[] {0, 8, 9, 1});
                if (openingFace) {
                    seg.addFace(new int[] {0, 1, 3, 2});
                }
                if (closingFace) {
                    seg.addFace(new int[] {4, 5, 7, 6});
                }
                return seg;
            }
        }
        if (y1t > wmin) {
            if (y1b >= wmin) {
                Object3D seg = new Object3D(color, true);
                seg.setProperty(Object3D.COLOR_PREFIX + "clip", clipColor);
                seg.addVertex(x0, y0b, zf);
                seg.addVertex(x0, y0b, zb);
                seg.addVertex(x0, wmax, zf);
                seg.addVertex(x0, wmax, zb);
                seg.addVertex(xpts[3], wmax, zf);
                seg.addVertex(xpts[3], wmax, zb);
                seg.addVertex(x1, y1t, zf);
                seg.addVertex(x1, y1t, zb);
                seg.addVertex(x1, y1b, zf);
                seg.addVertex(x1, y1b, zb);
                seg.addFace(new int[] {0, 2, 4, 6, 8}); // front
                seg.addFace(new int[] {1, 9, 7, 5, 3}); // rear
                seg.addFace(new int[] {2, 3, 5, 4}, "clip"); // clip top
                seg.addFace(new int[] {4, 5, 7, 6}); // top
                seg.addFace(new int[] {0, 8, 9, 1}); // bottom
                if (openingFace) {
                    seg.addFace(new int[] {0, 1, 3, 2});
                }
                if (closingFace) {
                    seg.addFace(new int[] {6, 7, 9, 8});
                }
                return seg;
            } else {
                Object3D seg = new Object3D(color, true);
                seg.setProperty(Object3D.COLOR_PREFIX + "clip", clipColor);
                seg.addVertex(x0, y0b, zf);
                seg.addVertex(x0, y0b, zb);
                seg.addVertex(x0, wmax, zf);
                seg.addVertex(x0, wmax, zb);
                seg.addVertex(xpts[3], wmax, zf);
                seg.addVertex(xpts[3], wmax, zb);
                seg.addVertex(x1, y1t, zf);
                seg.addVertex(x1, y1t, zb);
                seg.addVertex(x1, wmin, zf);
                seg.addVertex(x1, wmin, zb);
                seg.addVertex(xpts[0], wmin, zf);
                seg.addVertex(xpts[0], wmin, zb);
                seg.addFace(new int[] {0, 2, 4, 6, 8, 10}); // front
                seg.addFace(new int[] {1, 11, 9, 7, 5, 3}); // rear
                seg.addFace(new int[] {2, 3, 5, 4}, "clip"); // clip top
                seg.addFace(new int[] {4, 5, 7, 6}); // top
                seg.addFace(new int[] {8, 9, 11, 10}, "clip"); // clip bottom
                seg.addFace(new int[] {10, 11, 1, 0}); // bottom
                if (openingFace) {
                    seg.addFace(new int[] {0, 1, 3, 2});
                }
                if (closingFace) {
                    seg.addFace(new int[] {6, 7, 9, 8});
                }
                return seg;
            }
        }
        Object3D seg = new Object3D(color, true);
        seg.setProperty(Object3D.COLOR_PREFIX + "clip", clipColor);
        seg.addVertex(x0, y0b, zf);
        seg.addVertex(x0, y0b, zb);
        seg.addVertex(x0, wmax, zf);
        seg.addVertex(x0, wmax, zb);
        seg.addVertex(xpts[3], wmax, zf);
        seg.addVertex(xpts[3], wmax, zb);
        seg.addVertex(xpts[1], wmin, zf);
        seg.addVertex(xpts[1], wmin, zb);
        seg.addVertex(xpts[0], wmin, zf);
        seg.addVertex(xpts[0], wmin, zb);
        seg.addFace(new int[] {0, 2, 4, 6, 8}); // front
        seg.addFace(new int[] {1, 9, 7, 5, 3}); // rear
        seg.addFace(new int[] {2, 3, 5, 4}, "clip"); // clip top
        seg.addFace(new int[] {4, 5, 7, 6}); // top
        seg.addFace(new int[] {6, 7, 9, 8}, "clip"); // clip bottom
        seg.addFace(new int[] {8, 9, 1, 0}); // bottom
        if (openingFace) {
            seg.addFace(new int[] {0, 1, 3, 2});
        }
        // there is no closing face in this case
        return seg;
    }

    /**
     * Creates a segment for the case where the line end spans the entire axis
     * range at the left side of the chart.
     * 
     * @param x0
     * @param x1
     * @param xpts
     * @param y0b
     * @param y0t
     * @param y1b
     * @param y1t
     * @param wmin
     * @param wmax
     * @param zf
     * @param zb
     * @param color
     * @param clipColor
     * @param openingFace
     * @param closingFace
     * 
     * @return A segment. 
     */
    private Object3D createSegmentC(double x0, double x1, double[] xpts, 
            double y0b, double y0t, double y1b, double y1t, double wmin, 
            double wmax, double zf, double zb, Color color, Color clipColor, 
            boolean openingFace, boolean closingFace) {

        // the first 4 vertices and the opening face are common to all 
        // segments in this case
        Object3D seg = new Object3D(color, true);
        seg.setProperty(Object3D.COLOR_PREFIX + "clip", clipColor);
        seg.addVertex(x0, wmin, zf);
        seg.addVertex(x0, wmin, zb);
        seg.addVertex(x0, wmax, zf);
        seg.addVertex(x0, wmax, zb);
        if (openingFace) {
            seg.addFace(new int[] {0, 1, 3, 2});
        }
        
        if (y1b >= wmax) {
            seg.addVertex(xpts[2], wmax, zf);
            seg.addVertex(xpts[2], wmax, zb);
            seg.addVertex(xpts[0], wmin, zf);
            seg.addVertex(xpts[0], wmin, zb);
            seg.addFace(new int[] {0, 2, 4, 6}); // front
            seg.addFace(new int[] {1, 7, 5, 3}); // rear
            seg.addFace(new int[] {2, 3, 5, 4}, "clip"); // clip top
            seg.addFace(new int[] {4, 5, 7, 6}); // bottom
            seg.addFace(new int[] {7, 1, 0, 6}, "clip"); // bottom clip
            return seg;
        }
        if (y1t > wmax) {
            if (y1b >= wmin) {
                seg.addVertex(x1, wmax, zf);
                seg.addVertex(x1, wmax, zb);
                seg.addVertex(x1, y1b, zf);
                seg.addVertex(x1, y1b, zb);
                seg.addVertex(xpts[0], wmin, zf);
                seg.addVertex(xpts[0], wmin, zb);
                seg.addFace(new int[] {0, 2, 4, 6, 8}); // front
                seg.addFace(new int[] {1, 9, 7, 5, 3}); // rear
                seg.addFace(new int[] {2, 3, 5, 4}, "clip"); // top clip
                seg.addFace(new int[] {6, 7, 9, 8}); // bottom
                seg.addFace(new int[] {8, 9, 1, 0}, "clip"); // clip bottom
                if (closingFace) {
                    seg.addFace(new int[] {4, 5, 7, 6});
                }
                return seg;
            } else {
                seg.addVertex(x1, wmax, zf);
                seg.addVertex(x1, wmax, zb);
                seg.addVertex(x1, wmin, zf);
                seg.addVertex(x1, wmin, zb);
                seg.addFace(new int[] {0, 2, 4, 6}); // front
                seg.addFace(new int[] {1, 7, 5, 3}); // rear
                seg.addFace(new int[] {2, 3, 5, 4}, "clip"); // clip top
                seg.addFace(new int[] {4, 5, 7, 6}); // bottom
                seg.addFace(new int[] {7, 1, 0, 6}, "clip"); // bottom clip
                return seg;
            }
        }
        if (y1t > wmin) {
            if (y1b >= wmin) {
                return null; // in practice I don't think this case
                             // can occur
            } else {
                seg.addVertex(xpts[3], wmax, zf);
                seg.addVertex(xpts[3], wmax, zb);
                seg.addVertex(x1, y1t, zf);
                seg.addVertex(x1, y1t, zb);
                seg.addVertex(x1, wmin, zf);
                seg.addVertex(x1, wmin, zb);
                seg.addFace(new int[] {0, 2, 4, 6, 8}); // front
                seg.addFace(new int[] {1, 9, 7, 5, 3}); // rear
                seg.addFace(new int[] {2, 3, 5, 4}, "clip"); // clip top
                seg.addFace(new int[] {4, 5, 7, 6}); // top
                seg.addFace(new int[] {9, 1, 0, 8}, "clip"); // clip bottom
                if (closingFace) {
                    seg.addFace(new int[] {6, 7, 9, 8});
                }
                return seg; 
            }
        }
        seg.addVertex(xpts[3], wmax, zf);
        seg.addVertex(xpts[3], wmax, zb);
        seg.addVertex(xpts[1], wmin, zf);
        seg.addVertex(xpts[1], wmin, zb);
        seg.addFace(new int[] {0, 2, 4, 6}); // front
        seg.addFace(new int[] {1, 7, 5, 3}); // rear
        seg.addFace(new int[] {2, 3, 5, 4}, "clip"); // clip top
        seg.addFace(new int[] {4, 5, 7, 6}); // top
        seg.addFace(new int[] {6, 7, 1, 0}, "clip"); // clip bottom
        return seg;
    }
    
    /**
     * Creates a segment for the case where the segment is contained within
     * the axis range at the left side.
     * 
     * @param x0
     * @param x1
     * @param xpts
     * @param y0b
     * @param y0t
     * @param y1b
     * @param y1t
     * @param wmin
     * @param wmax
     * @param zf
     * @param zb
     * @param color
     * @param clipColor
     * @param openingFace
     * @param closingFace
     * 
     * @return A segment. 
     */
    private Object3D createSegmentD(double x0, double x1, double[] xpts, 
            double y0b, double y0t, double y1b, double y1t, double wmin, 
            double wmax, double zf, double zb, Color color, Color clipColor, 
            boolean openingFace, boolean closingFace) {

        Object3D seg = new Object3D(color, true);
        seg.setProperty(Object3D.COLOR_PREFIX + "clip", clipColor);
        seg.addVertex(x0, y0b, zf);
        seg.addVertex(x0, y0b, zb);
        seg.addVertex(x0, y0t, zf);
        seg.addVertex(x0, y0t, zb);
        if (y1b >= wmax) {
            seg.addVertex(xpts[3], wmax, zf);
            seg.addVertex(xpts[3], wmax, zb);
            seg.addVertex(xpts[2], wmax, zf);
            seg.addVertex(xpts[2], wmax, zb);
            seg.addFace(new int[] {0, 2, 4, 6});  // front
            seg.addFace(new int[] {1, 7, 5, 3});  // rear
            seg.addFace(new int[] {2, 3, 5, 4});  // top
            seg.addFace(new int[] {4, 5, 7, 6}, "clip"); // clip top
            seg.addFace(new int[] {0, 6, 7, 1}); // bottom
            if (openingFace) {
                seg.addFace(new int[] {0, 1, 3, 2});
            }
            // there is no closing face in this case
            return seg;
        }
        if (y1t > wmax) {
            if (y1b >= wmin) {
                seg.addVertex(xpts[3], wmax, zf);
                seg.addVertex(xpts[3], wmax, zb);
                seg.addVertex(x1, wmax, zf);
                seg.addVertex(x1, wmax, zb);
                seg.addVertex(x1, y1b, zf);
                seg.addVertex(x1, y1b, zb);
                seg.addFace(new int[] {0, 2, 4, 6, 8}); // front
                seg.addFace(new int[] {1, 9, 7, 5, 3}); // rear
                seg.addFace(new int[] {2, 3, 5, 4}); // top
                seg.addFace(new int[] {4, 5, 7, 6}, "clip"); // clip top
                seg.addFace(new int[] {0, 8, 9, 1});
                if (openingFace) {
                    seg.addFace(new int[] {0, 1, 3, 2});
                }
                if (closingFace) {
                    seg.addFace(new int[] {6, 7, 9, 8});
                }
                return seg;  
            } else {
                return null;  // this case should not be possible
            }
        }
        if (y1t > wmin) {
            if (y1b >= wmin) {
                // this is the regular segment, no clipping
                seg.addVertex(x1, y1t, zf);
                seg.addVertex(x1, y1t, zb);
                seg.addVertex(x1, y1b, zf);
                seg.addVertex(x1, y1b, zb);
                seg.addFace(new int[] {0, 2, 4, 6});  // front
                seg.addFace(new int[] {1, 7, 5, 3});  // rear
                seg.addFace(new int[] {2, 3, 5, 4});  // top
                seg.addFace(new int[] {0, 6, 7, 1});  // bottom
                if (openingFace) {
                    seg.addFace(new int[] {0, 1, 3, 2});
                }
                if (closingFace) {
                    seg.addFace(new int[] {4, 5, 7, 6});
                }
                return seg;
            } else {
                seg.addVertex(x1, y1t, zf);
                seg.addVertex(x1, y1t, zb);
                seg.addVertex(x1, wmin, zf);
                seg.addVertex(x1, wmin, zb);
                seg.addVertex(xpts[0], wmin, zf);
                seg.addVertex(xpts[0], wmin, zb);
                seg.addFace(new int[] {0, 2, 4, 6, 8}); // front
                seg.addFace(new int[] {1, 9, 7, 5, 3}); // rear
                seg.addFace(new int[] {2, 3, 5, 4}); // top
                seg.addFace(new int[] {0, 8, 9, 1});  // bottom
                seg.addFace(new int[] {6, 7, 9, 8}, "clip"); // clip bottom
                if (openingFace) {
                    seg.addFace(new int[] {0, 1, 3, 2});
                }
                if (closingFace) {
                    seg.addFace(new int[] {4, 5, 7, 6});
                }
                return seg;
            }
        } else {
            seg.addVertex(xpts[1], wmin, zf);
            seg.addVertex(xpts[1], wmin, zb);
            seg.addVertex(xpts[0], wmin, zf);
            seg.addVertex(xpts[0], wmin, zb);
            seg.addFace(new int[] {0, 2, 4, 6}); // front
            seg.addFace(new int[] {1, 7, 5, 3}); // rear
            seg.addFace(new int[] {2, 3, 5, 4}); // top
            seg.addFace(new int[] {0, 6, 7, 1}); // bottom
            seg.addFace(new int[] {4, 5, 7, 6}, "clip"); // clip bottom
            if (openingFace) {
                seg.addFace(new int[] {0, 1, 3, 2});
            }
            // there is no closing face in this case
            return seg;
        }
    }
    
    /**
     * Returns a segment for the case where the line height spans the lower 
     * bound of the axis range at the left side of the chart.
     * 
     * @param x0
     * @param x1
     * @param xpts
     * @param y0b
     * @param y0t
     * @param y1b
     * @param y1t
     * @param wmin
     * @param wmax
     * @param zf
     * @param zb
     * @param color
     * @param clipColor
     * @param openingFace
     * @param closingFace
     * 
     * @return The segment. 
     */
    private Object3D createSegmentE(double x0, double x1, double[] xpts, 
            double y0b, double y0t, double y1b, double y1t, double wmin, 
            double wmax, double zf, double zb, Color color, Color clipColor, 
            boolean openingFace, boolean closingFace) {
        if (y1b > wmax) {
            Object3D seg = new Object3D(color, true);
            seg.setProperty(Object3D.COLOR_PREFIX + "clip", clipColor);
            seg.addVertex(x0, wmin, zf);
            seg.addVertex(x0, wmin, zb);
            seg.addVertex(x0, y0t, zf);
            seg.addVertex(x0, y0t, zb);
            seg.addVertex(xpts[3], wmax, zf);
            seg.addVertex(xpts[3], wmax, zb);
            seg.addVertex(xpts[2], wmax, zf);
            seg.addVertex(xpts[2], wmax, zb);
            seg.addVertex(xpts[0], wmin, zf);
            seg.addVertex(xpts[0], wmin, zb);
            seg.addFace(new int[] {0, 2, 4, 6, 8}); // front
            seg.addFace(new int[] {1, 9, 7, 5, 3}); // rear
            seg.addFace(new int[] {2, 3, 5, 4}); // top
            seg.addFace(new int[] {4, 5, 7, 6}, "clip"); // clip top
            seg.addFace(new int[] {6, 7, 9, 8}); // bottom
            seg.addFace(new int[] {0, 8, 9, 1}, "clip"); // clip bottom
            if (openingFace) {
                seg.addFace(new int[] {0, 1, 3, 2});
            }
            return seg;
        }
        if (y1t > wmax) {
            if (y1b >= wmin) {
                Object3D seg = new Object3D(color, true);
                seg.setProperty(Object3D.COLOR_PREFIX + "clip", clipColor);
                seg.addVertex(x0, wmin, zf);
                seg.addVertex(x0, wmin, zb);
                seg.addVertex(x0, y0t, zf);
                seg.addVertex(x0, y0t, zb);
                seg.addVertex(xpts[3], wmax, zf);
                seg.addVertex(xpts[3], wmax, zb);
                seg.addVertex(x1, wmax, zf);
                seg.addVertex(x1, wmax, zb);
                seg.addVertex(x1, y1b, zf);
                seg.addVertex(x1, y1b, zb);
                seg.addVertex(xpts[0], wmin, zf);
                seg.addVertex(xpts[0], wmin, zb);
                seg.addFace(new int[] {0, 2, 4, 6, 8, 10}); // front
                seg.addFace(new int[] {1, 11, 9, 7, 5, 3}); // rear
                seg.addFace(new int[] {2, 3, 5, 4}); // top
                seg.addFace(new int[] {5, 7, 6, 4}, "clip"); // clip top
                seg.addFace(new int[] {8, 9, 11, 10}); // bottom
                seg.addFace(new int[] {1, 0, 10, 11}, "clip");
                if (openingFace) {
                    seg.addFace(new int[] {0, 1, 3, 2}); 
                }
                if (closingFace) {
                    seg.addFace(new int[] {6, 7, 9, 8}); 
                }
                return seg;
            } else {
                Object3D seg = new Object3D(color, true);
                seg.setProperty(Object3D.COLOR_PREFIX + "clip", clipColor);
                seg.addVertex(x0, wmin, zf);
                seg.addVertex(x0, wmin, zb);
                seg.addVertex(x0, y0t, zf);
                seg.addVertex(x0, y0t, zb);
                seg.addVertex(xpts[3], wmax, zf);
                seg.addVertex(xpts[3], wmax, zb);
                seg.addVertex(x1, wmax, zf);
                seg.addVertex(x1, wmax, zb);
                seg.addVertex(x1, wmin, zf);
                seg.addVertex(x1, wmin, zb);
                seg.addFace(new int[] {0, 2, 4, 6, 8}); // front
                seg.addFace(new int[] {1, 9, 7, 5, 3}); // rear
                seg.addFace(new int[] {2, 3, 5, 4}); // top
                seg.addFace(new int[] {5, 7, 6, 4}, "clip"); // clip top
                seg.addFace(new int[] {0, 8, 9, 1}, "clip"); // clip bottom
                if (openingFace) {
                    seg.addFace(new int[] {0, 1, 3, 2}); 
                }
                if (closingFace) {
                    seg.addFace(new int[] {6, 7, 9, 8}); 
                }
                return seg;  
            }
        }
        if (y1t > wmin) {
            if (y1b >= wmin) {
                Object3D seg = new Object3D(color, true);
                seg.setProperty(Object3D.COLOR_PREFIX + "clip", clipColor);
                seg.addVertex(x0, wmin, zf);
                seg.addVertex(x0, wmin, zb);
                seg.addVertex(x0, y0t, zf);
                seg.addVertex(x0, y0t, zb);
                seg.addVertex(x1, y1t, zf);                
                seg.addVertex(x1, y1t, zb);
                seg.addVertex(x1, y1b, zf);                
                seg.addVertex(x1, y1b, zb);
                seg.addVertex(xpts[0], wmin, zf);
                seg.addVertex(xpts[0], wmin, zb);
                seg.addFace(new int[] {0, 2, 4, 6, 8}); // front
                seg.addFace(new int[] {1, 9, 7, 5, 3}); // rear
                seg.addFace(new int[] {2, 3, 5, 4}); // top
                seg.addFace(new int[] {6, 7, 9, 8}); // bottom
                seg.addFace(new int[] {0, 8, 9, 1}, "clip"); // clip bottom
                if (openingFace) {
                    seg.addFace(new int[] {0, 1, 3, 2});
                }
                if (closingFace) {
                    seg.addFace(new int[] {4, 5, 7, 6});
                }
                return seg;
            } else {
                Object3D seg = new Object3D(color, true);
                seg.setProperty(Object3D.COLOR_PREFIX + "clip", clipColor);
                seg.addVertex(x0, wmin, zf);
                seg.addVertex(x0, wmin, zb);
                seg.addVertex(x0, y0t, zf);
                seg.addVertex(x0, y0t, zb);
                seg.addVertex(x1, y1t, zf);                
                seg.addVertex(x1, y1t, zb);
                seg.addVertex(x1, wmin, zf);
                seg.addVertex(x1, wmin, zb);
                seg.addFace(new int[] {0, 2, 4, 6}); // front
                seg.addFace(new int[] {1, 7, 5, 3}); // rear
                seg.addFace(new int[] {2, 3, 5, 4}); // top
                seg.addFace(new int[] {0, 6, 7, 1}, "clip"); // clip bottom
                if (openingFace) {
                    seg.addFace(new int[] {0, 1, 3, 2});
                }
                if (closingFace) {
                    seg.addFace(new int[] {4, 5, 7, 6});
                }
                return seg;
            }
        }
        Object3D seg = new Object3D(color, true);
        seg.setProperty(Object3D.COLOR_PREFIX + "clip", clipColor);
        seg.addVertex(x0, wmin, zf);
        seg.addVertex(x0, wmin, zb);
        seg.addVertex(x0, y0t, zf);
        seg.addVertex(x0, y0t, zb);
        seg.addVertex(xpts[1], wmin, zf);
        seg.addVertex(xpts[1], wmin, zb);
        seg.addFace(new int[] {0, 2, 4}); // front
        seg.addFace(new int[] {1, 5, 3}); // rear
        seg.addFace(new int[] {2, 3, 5, 4}); // top
        seg.addFace(new int[] {0, 4, 5, 1}, "clip"); // clip bottom
        if (openingFace) {
            seg.addFace(new int[] {0, 1, 3, 2});
        }
        // there is no closing face in this case
        return seg;
    }
    
    /**
     * Creates and returns a segment for the case where the line is completely
     * below the axis range at the left side.
     * 
     * @param x0
     * @param x1
     * @param xpts
     * @param y0b
     * @param y0t
     * @param y1b
     * @param y1t
     * @param wmin
     * @param wmax
     * @param zf
     * @param zb
     * @param color
     * @param clipColor
     * @param openingFace  ignored because there is no opening face in this 
     *     case.
     * @param closingFace
     * 
     * @return A segment. 
     */
    private Object3D createSegmentF(double x0, double x1, double[] xpts, 
            double y0b, double y0t, double y1b, double y1t, double wmin, 
            double wmax, double zf, double zb, Color color, Color clipColor, 
            boolean openingFace, boolean closingFace) {

        if (y1b > wmax) {
            Object3D seg = new Object3D(color, true);
            seg.setProperty(Object3D.COLOR_PREFIX + "clip", clipColor);
            seg.addVertex(xpts[1], wmin, zf);
            seg.addVertex(xpts[1], wmin, zb);
            seg.addVertex(xpts[3], wmax, zf);
            seg.addVertex(xpts[3], wmax, zb);
            seg.addVertex(xpts[2], wmax, zf);
            seg.addVertex(xpts[2], wmax, zb);
            seg.addVertex(xpts[0], wmin, zf);
            seg.addVertex(xpts[0], wmin, zb);
            seg.addFace(new int[] {0, 2, 4, 6}); // front
            seg.addFace(new int[] {1, 7, 5, 3}); // rear
            seg.addFace(new int[] {0, 1, 3, 2}); // top
            seg.addFace(new int[] {2, 3, 5, 4}, "clip"); // clip top
            seg.addFace(new int[] {4, 5, 7, 6}); // bottom
            seg.addFace(new int[] {0, 6, 7, 1}, "clip"); // clip bottom
            // there are no opening and closing faces for this case
            return seg;
        }
        if (y1t > wmax) {
            if (y1b > wmin) {
                Object3D seg = new Object3D(color, true);
                seg.setProperty(Object3D.COLOR_PREFIX + "clip", clipColor);
                seg.addVertex(xpts[1], wmin, zf);
                seg.addVertex(xpts[1], wmin, zb);
                seg.addVertex(xpts[3], wmax, zf);
                seg.addVertex(xpts[3], wmax, zb);
                seg.addVertex(x1, wmax, zf);
                seg.addVertex(x1, wmax, zb);
                seg.addVertex(x1, y1b, zf);
                seg.addVertex(x1, y1b, zb);
                seg.addVertex(xpts[0], wmin, zf);
                seg.addVertex(xpts[0], wmin, zb);
                seg.addFace(new int[] {0, 2, 4, 6, 8}); // front
                seg.addFace(new int[] {1, 9, 7, 5, 3}); // rear
                seg.addFace(new int[] {2, 3, 5, 4}); //clip top
                seg.addFace(new int[] {0, 1, 3, 2}); // top
                seg.addFace(new int[] {0, 8, 9, 1}, "clip"); // clip bottom
                seg.addFace(new int[] {6, 7, 9, 8}); // bottom
                // there is no opening face in this case
                if (closingFace) {
                    seg.addFace(new int[] {4, 5, 7, 6});
                }
                return seg;
            } else {
                Object3D seg = new Object3D(color, true);
                seg.setProperty(Object3D.COLOR_PREFIX + "clip", clipColor);
                seg.addVertex(xpts[1], wmin, zf);
                seg.addVertex(xpts[1], wmin, zb);
                seg.addVertex(xpts[3], wmax, zf);
                seg.addVertex(xpts[3], wmax, zb);
                seg.addVertex(x1, wmax, zf);
                seg.addVertex(x1, wmax, zb);
                seg.addVertex(x1, wmin, zf);
                seg.addVertex(x1, wmin, zb);
                seg.addFace(new int[] {0, 2, 4, 6}); // front
                seg.addFace(new int[] {1, 7, 5, 3}); // rear
                seg.addFace(new int[] {0, 1, 3, 2}); // top
                seg.addFace(new int[] {2, 3, 5, 4}, "clip"); // clip top
                seg.addFace(new int[] {6, 7, 1, 0}, "clip"); // clip bottom
                if (closingFace) {
                    seg.addFace(new int[] {4, 5, 7, 6});
                }
                return seg;
            }
        }
        if (y1t > wmin) {
            if (y1b >= wmin) {
                Object3D seg = new Object3D(color, true);
                seg.setProperty(Object3D.COLOR_PREFIX + "clip", clipColor);
                seg.addVertex(xpts[1], wmin, zf);
                seg.addVertex(xpts[1], wmin, zb);
                seg.addVertex(x1, y1t, zf);
                seg.addVertex(x1, y1t, zb);
                seg.addVertex(x1, y1b, zf);
                seg.addVertex(x1, y1b, zb);
                seg.addVertex(xpts[0], wmin, zf);
                seg.addVertex(xpts[0], wmin, zb);                
                seg.addFace(new int[] {0, 2, 4, 6}); // front
                seg.addFace(new int[] {1, 7, 5, 3}); // rear
                seg.addFace(new int[] {0, 1, 3, 2}); // top
                seg.addFace(new int[] {4, 5, 7, 6}); // bottom
                seg.addFace(new int[] {0, 6, 7, 1}, "clip"); // clip bottom
                if (closingFace) {
                    seg.addFace(new int[] {2, 3, 5, 4});
                }
                return seg;
            } else {
                Object3D seg = new Object3D(color, true);
                seg.setProperty(Object3D.COLOR_PREFIX + "clip", clipColor);
                seg.addVertex(xpts[1], wmin, zf);
                seg.addVertex(xpts[1], wmin, zb);
                seg.addVertex(x1, y1t, zf);
                seg.addVertex(x1, y1t, zb);
                seg.addVertex(x1, wmin, zf);
                seg.addVertex(x1, wmin, zb);
                seg.addFace(new int[] {0, 2, 4}); // front
                seg.addFace(new int[] {1, 5, 3}); // rear
                seg.addFace(new int[] {0, 1, 3, 2}); // top
                seg.addFace(new int[] {0, 4, 5, 1}, "clip"); // clip bottom
                // there is no opening face in this case
                if (closingFace) {
                    seg.addFace(new int[] {2, 3, 5, 4});
                }
                return seg;
            }
        }
        return null;  // nothing to see
    }

    /**
     * Tests this renderer for equality with an arbitrary object.
     * 
     * @param obj  the object ({@code null} not permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LineRenderer3D)) {
            return false;
        }
        LineRenderer3D that = (LineRenderer3D) obj;
        if (this.lineWidth != that.lineWidth) {
            return false;
        }
        if (this.lineHeight != that.lineHeight) {
            return false;
        }
        if (this.isolatedItemWidthPercent != that.isolatedItemWidthPercent) {
            return false;
        }
        if (!ObjectUtils.equals(this.clipColorSource, that.clipColorSource)) {
            return false;
        }
        return super.equals(obj);
    }
}

