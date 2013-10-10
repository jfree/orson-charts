/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.table;

import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.util.Map;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.awt.Insets;
import com.orsoncharts.data.DefaultKeyedValues2D;

/**
 * A table element that contains a grid of elements.  
 */
public class GridElement extends AbstractTableElement implements TableElement {

    /** Storage for the cell elements. */
    private DefaultKeyedValues2D<TableElement> elements;
    
    /**
     * Creates a new empty grid.
     */
    public GridElement() {
        this.elements = new DefaultKeyedValues2D<TableElement>();  
    }
    
    /**
     * Adds (or updates) a cell in the grid.
     * 
     * @param element  the element (<code>null</code> permitted).
     * @param rowKey  the row key (<code>null</code> not permitted).
     * @param columnKey  the column key (<code>null</code> not permitted).
     */
    public void setElement(TableElement element, Comparable rowKey, 
            Comparable columnKey) {
        // defer argument checking
        this.elements.setValue(element, rowKey, columnKey);
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
        int rowCount = this.elements.getXCount();
        int columnCount = this.elements.getYCount();
        double[] widths = new double[columnCount];
        double[] heights = new double[rowCount];
        // calculate the maximum width for each column
        for (int r = 0; r < elements.getXCount(); r++) {
            for (int c = 0; c < this.elements.getYCount(); c++) {
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
                this.elements.getXCount() * this.elements.getYCount());
        double y = bounds.getY() + getInsets().top;
        for (int r = 0; r < elements.getXCount(); r++) {
            double x = bounds.getX() + getInsets().left;
            for (int c = 0; c < this.elements.getYCount(); c++) {
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
        double[][] cellDimensions = findCellDimensions(g2, bounds);
        double[] widths = cellDimensions[0];
        double[] heights = cellDimensions[1];
        double y = bounds.getY() + getInsets().top;
        for (int r = 0; r < elements.getXCount(); r++) {
            double x = bounds.getX() + getInsets().left;
            for (int c = 0; c < this.elements.getYCount(); c++) {
                TableElement element = this.elements.getValue(r, c);
                if (element == null) {
                    continue;
                }
                element.draw(g2, new Rectangle2D.Double(x, y, widths[c], 
                        heights[r]));
                x += widths[c];
            }
            y = y + heights[r];
        }        
    }
    
}
