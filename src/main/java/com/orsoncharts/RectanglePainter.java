/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of these source files is prohibited.
 * 
 */

package com.orsoncharts;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * An object that can paint a rectangular region with a color, gradient, image
 * or anything.  
 * <br><br>
 * NOTE: It is recommended that classes that implement this interface are 
 * designed to be <code>Serializable</code> and immutable.  Immutability is 
 * desirable because painters are assigned to {@link Chart3D} instances, and 
 * there is no change notification if the painter can be modified directly.
 */
public interface RectanglePainter {
    
    /**
     * Fills the specified rectangle (where "fill" is some arbitrary drawing
     * operation defined by the class that implements this interface).
     * 
     * @param g2  the graphics target (<code>null</code> not permitted).
     * @param bounds  the rectangle (<code>null</code> not permitted).
     */
    public void fill(Graphics2D g2, Rectangle2D bounds);

}
