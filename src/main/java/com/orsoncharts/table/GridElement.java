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

package com.orsoncharts.table;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.awt.Insets;

import com.orsoncharts.data.DefaultKeyedValues2D;

/**
 * A table element that contains a grid of elements.  
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 */
@SuppressWarnings("serial")
public class GridElement<R extends Comparable<R>, C extends Comparable<C>> 
        extends AbstractTableElement 
        implements TableElement, Serializable {

    private static final Color TRANSPARENT_COLOR = new Color(0, 0, 0, 0);
    
    /** Storage for the cell elements. */
    private DefaultKeyedValues2D<R, C, TableElement> elements;
    
    /**
     * Creates a new empty grid.
     */
    public GridElement() {
        this.elements = new DefaultKeyedValues2D<R, C, TableElement>();
        setBackgroundColor(TRANSPARENT_COLOR);
    }
    
    /**
     * Adds (or updates) a cell in the grid.
     * 
     * @param element  the element ({@code null} permitted).
     * @param rowKey  the row key ({@code null} not permitted).
     * @param columnKey  the column key ({@code null} not permitted).
     */
    public void setElement(TableElement element, R rowKey, C columnKey) {
        // defer argument checking
        this.elements.setValue(element, rowKey, columnKey);
    }
    
    /**
     * Receives a visitor by calling the visitor's {@code visit()} method 
     * for each of the children in the grid, and finally for the grid itself. 
     * 
     * @param visitor  the visitor ({@code null} not permitted).
     * 
     * @since 1.2
     */
    @Override
    public void receive(TableElementVisitor visitor) {
        for (int r = 0; r < this.elements.getRowCount(); r++) {
            for (int c = 0; c < this.elements.getColumnCount(); c++) {
                TableElement element = this.elements.getValue(r, c);
                if (element != null) {
                    element.receive(visitor);
                }
            }
        }
        visitor.visit(this);
    }
    
    /**
     * Finds the cell dimensions.
     * 
     * @param g2  the graphics target (required to calculate font sizes).
     * @param bounds  the bounds.
     * 
     * @return The cell dimensions (result[0] is the widths, result[1] is the 
     *     heights). 
     */
    private double[][] findCellDimensions(Graphics2D g2, Rectangle2D bounds) {
        int rowCount = this.elements.getRowCount();
        int columnCount = this.elements.getColumnCount();
        double[] widths = new double[columnCount];
        double[] heights = new double[rowCount];
        // calculate the maximum width for each column
        for (int r = 0; r < elements.getRowCount(); r++) {
            for (int c = 0; c < this.elements.getColumnCount(); c++) {
                TableElement element = this.elements.getValue(r, c);
                if (element == null) {
                    continue;
                }
                Dimension2D dim = element.preferredSize(g2, bounds);
                widths[c] = Math.max(widths[c], dim.getWidth());
                heights[r] = Math.max(heights[r], dim.getHeight());
            }
        }
        return new double[][] { widths, heights };
    }
    

    /**
     * Returns the preferred size of the element (including insets).
     * 
     * @param g2  the graphics target.
     * @param bounds  the bounds.
     * @param constraints  the constraints (ignored for now).
     * 
     * @return The preferred size. 
     */
    @Override
    public Dimension2D preferredSize(Graphics2D g2, Rectangle2D bounds, 
            Map<String, Object> constraints) {
        Insets insets = getInsets();
        double[][] cellDimensions = findCellDimensions(g2, bounds);
        double[] widths = cellDimensions[0];
        double[] heights = cellDimensions[1];
        double w = insets.left + insets.right;
        for (int i = 0; i < widths.length; i++) {
            w = w + widths[i];
        }
        double h = insets.top + insets.bottom;
        for (int i = 0; i < heights.length; i++) {
            h = h + heights[i];
        }
        return new Dimension((int) w, (int) h);
    }

    /**
     * Performs a layout of this table element, returning a list of bounding
     * rectangles for the element and its subelements.
     * 
     * @param g2  the graphics target.
     * @param bounds  the bounds.
     * @param constraints  the constraints (if any).
     * 
     * @return A list of bounding rectangles. 
     */
    @Override
    public List<Rectangle2D> layoutElements(Graphics2D g2, Rectangle2D bounds, 
            Map<String, Object> constraints) {
        double[][] cellDimensions = findCellDimensions(g2, bounds);
        double[] widths = cellDimensions[0];
        double[] heights = cellDimensions[1];
        List<Rectangle2D> result = new ArrayList<Rectangle2D>(
                this.elements.getRowCount() * this.elements.getColumnCount());
        double y = bounds.getY() + getInsets().top;
        for (int r = 0; r < elements.getRowCount(); r++) {
            double x = bounds.getX() + getInsets().left;
            for (int c = 0; c < this.elements.getColumnCount(); c++) {
                result.add(new Rectangle2D.Double(x, y, widths[c], heights[r]));
                x += widths[c];
            }
            y = y + heights[r];
        }
        return result;
    }

    /**
     * Draws the element within the specified bounds.
     * 
     * @param g2  the graphics target.
     * @param bounds  the bounds.
     */
    @Override
    public void draw(Graphics2D g2, Rectangle2D bounds) {
        draw(g2, bounds, null);
    }
    
    /**
     * Draws the element within the specified bounds.  If the 
     * {@code recordBounds} flag is set, this element and each of its
     * children will have their {@code BOUNDS_2D} property updated with 
     * the current bounds.
     * 
     * @param g2  the graphics target ({@code null} not permitted).
     * @param bounds  the bounds ({@code null} not permitted).
     * @param onDrawHandler  an object that will receive notification before 
     *     and after the element is drawn ({@code null} permitted).
     * 
     * @since 1.3
     */
    @Override
    public void draw(Graphics2D g2, Rectangle2D bounds, 
            TableElementOnDraw onDrawHandler) {
        if (onDrawHandler != null) {
            onDrawHandler.beforeDraw(this, g2, bounds);
        }
        if (getBackground() != null) {
            getBackground().fill(g2, bounds);
        }
        List<Rectangle2D> positions = layoutElements(g2, bounds, null);
        for (int r = 0; r < this.elements.getRowCount(); r++) {
            for (int c = 0; c < this.elements.getColumnCount(); c++) {
                TableElement element = this.elements.getValue(r, c);
                if (element == null) {
                    continue;
                }
                Rectangle2D pos = positions.get(r * elements.getColumnCount() 
                        + c);
                element.draw(g2, pos, onDrawHandler);
            }
        }
        if (onDrawHandler != null) {
            onDrawHandler.afterDraw(this, g2, bounds);
        }
    }
    
    /**
     * Tests this element for equality with an arbitrary object.
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
        if (!(obj instanceof GridElement)) {
            return false;
        }
        GridElement that = (GridElement) obj;
        if (!this.elements.equals(that.elements)) {
            return false;
        }
        return true;
    }
 
    /**
     * Returns a string representation of this element, primarily for
     * debugging purposes.
     * 
     * @return A string representation of this element. 
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("GridElement[rowCount=").append(this.elements.getRowCount());
        sb.append(", columnCount=").append(this.elements.getColumnCount());
        sb.append("]");
        return sb.toString();
    }
    
}
