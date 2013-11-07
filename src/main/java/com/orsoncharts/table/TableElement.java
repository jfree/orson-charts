/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.table;

import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Map;

/**
 * An element (typically a single cell) in a table.  This interface defines
 * methods for determining the preferred size of the element, for laying out
 * the element (including sub-elements if there are any), and drawing the
 * element within specified bounds.  Various kinds of table elements will be
 * used to construct interesting tables.
 * <br><br>
 * It is important that these methods are implemented in a stateless manner. 
 * There is some redundancy in calculation between the layout and drawing 
 * methods in order to preserve the statelessness, but it is important to 
 * ensure that table elements can be rendered to multiple targets 
 * simultaneously.
 * 
 */

public interface TableElement {
    
    /**
     * Calculates the preferred size for the element, with the only constraint
     * being that the element fits within the specified bounds.
     * 
     * @param g2  the graphics target.
     * @param bounds  the bounds.
     * 
     * @return The preferred size.
     */
    Dimension2D preferredSize(Graphics2D g2, Rectangle2D bounds);
    
    /**
     * Returns the preferred size of the element, subject to the supplied
     * constraints.
     * 
     * @param g2  the graphics target.
     * @param bounds  the bounds.
     * @param constraints  the constraints.
     * 
     * @return The preferred size. 
     */
    Dimension2D preferredSize(Graphics2D g2, Rectangle2D bounds, 
            Map<String, Object> constraints);
    
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
    List<Rectangle2D> layoutElements(Graphics2D g2, Rectangle2D bounds, 
            Map<String, Object> constraints);

    /**
     * Draws the element within the specified bounds.
     * 
     * @param g2  the graphics target.
     * @param bounds  the bounds.
     */
    void draw(Graphics2D g2, Rectangle2D bounds);
    
}
