/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of this source file is prohibited.
 * 
 */

package com.orsoncharts.table;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * An interface that provides access to the {@link TableElement} rendering 
 * process.
 * 
 * @since 1.3
 */
public interface TableElementOnDraw {
    
    /**
     * A callback method that is called before an element is drawn.
     * 
     * @param element  the element (<code>null</code> not permitted).
     * @param g2  the graphics target (<code>null</code> not permitted).
     * @param bounds  the bounds (<code>null</code> not permitted).
     */
    void beforeDraw(TableElement element, Graphics2D g2, Rectangle2D bounds);
    
    /**
     * A callback method that is called after an element is drawn.
     * 
     * @param element  the element (<code>null</code> not permitted).
     * @param g2  the graphics target (<code>null</code> not permitted).
     * @param bounds  the bounds (<code>null</code> not permitted).
     */
    void afterDraw(TableElement element, Graphics2D g2, Rectangle2D bounds);
    
}
