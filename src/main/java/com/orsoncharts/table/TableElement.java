/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.table;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Map;

/**
 * An element in a table.
 */
public interface TableElement {
    
    Rectangle2D preferredSize(Graphics2D g2, Rectangle2D bounds);
    
    Rectangle2D preferredSize(Graphics2D g2, Rectangle2D bounds, 
            Map<String, Object> constraints);
    
    void render(Graphics2D g2, Rectangle2D bounds);
    
}
