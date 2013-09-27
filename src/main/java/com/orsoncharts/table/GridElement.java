/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.table;

import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.util.Map;
import com.orsoncharts.data.DefaultKeyedValues2D;
import java.awt.Dimension;

/**
 * A table element that contains a grid of elements.  
 */
public class GridElement extends AbstractTableElement implements TableElement {

    DefaultKeyedValues2D<TableElement> elements;
    
    public GridElement() {
        this.elements = new DefaultKeyedValues2D<TableElement>();  
    }
    
    public void setElement(TableElement element, Comparable rowKey, Comparable columnKey) {
        this.elements.setValue(element, rowKey, columnKey);
    }
    
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
    

    @Override
    public Dimension2D preferredSize(Graphics2D g2, Rectangle2D bounds, 
            Map<String, Object> constraints) {
        double[][] cellDimensions = findCellDimensions(g2, bounds);
        double[] widths = cellDimensions[0];
        double[] heights = cellDimensions[1];
        double w = 0.0;
        for (int i = 0; i < widths.length; i++) {
            w = w + widths[i];
        }
        double h = 0.0;
        for (int i = 0; i < heights.length; i++) {
            h = h + heights[i];
        }
        return new Dimension((int) w, (int) h);
    }

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
                element.draw(g2, new Rectangle2D.Double(x, y, widths[c], widths[r]));
                x += widths[c];
            }
            y = y + heights[r];
        }        
    }
    
}
