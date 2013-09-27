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

/**
 * An element in a table.
 */
public interface TableElement {
    
    /**
     * Calculates the preferred size for the element, with the only constraint
     * being that the element fits within the specified bounds.
     * 
     * @param g2  the graphics target.
     * @param bounds  the bounds.
     * 
     * @return The  
     */
    Dimension2D preferredSize(Graphics2D g2, Rectangle2D bounds);
    
    Dimension2D preferredSize(Graphics2D g2, Rectangle2D bounds, 
            Map<String, Object> constraints);
    
    void draw(Graphics2D g2, Rectangle2D bounds);
    
}
