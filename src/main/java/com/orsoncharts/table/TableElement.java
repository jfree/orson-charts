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
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Map;
import com.orsoncharts.util.RefPt2D;

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
    
    final String CLASS = "class";
    
    /** 
     * The 'bounds' property key.  Values for this property should be 
     * instances of Rectangle2D. 
     */
    final String BOUNDS = "bounds";
    
    /**
     * Calculates the preferred size for the element, with reference to the 
     * specified bounds.  The preferred size can exceed the bounds, but the
     * bounds might influence how sub-elements are sized and/or positioned.
     * For example, in a {@link FlowElement}, the width of the bounds will
     * determine when the flow layout wraps.
     * 
     * @param g2  the graphics target (<code>null</code> not permitted).
     * @param bounds  the bounds (<code>null</code> not permitted).
     * 
     * @return The preferred size (never <code>null</code>).
     */
    Dimension2D preferredSize(Graphics2D g2, Rectangle2D bounds);
    
    /**
     * Returns the preferred size of the element, subject to the supplied
     * constraints.
     * 
     * @param g2  the graphics target (<code>null</code> not permitted).
     * @param bounds  the bounds (<code>null</code> not permitted).
     * @param constraints  the constraints (<code>null</code> permitted).
     * 
     * @return The preferred size. 
     */
    Dimension2D preferredSize(Graphics2D g2, Rectangle2D bounds, 
            Map<String, Object> constraints);
    
    /**
     * Returns the reference point used to align the element with the bounding
     * rectangle within which it is drawn.
     * 
     * @return The anchor point (never <code>null</code>). 
     */
    RefPt2D getRefPoint();
    
    /**
     * Performs a layout of this table element, returning a list of bounding
     * rectangles for the element and its subelements.  This method is
     * typically called by the {@link #draw(java.awt.Graphics2D, 
     * java.awt.geom.Rectangle2D)} method.
     * 
     * @param g2  the graphics target (<code>null</code> not permitted).
     * @param bounds  the bounds (<code>null</code> not permitted).
     * @param constraints  the constraints (if any).
     * 
     * @return A list of bounding rectangles. 
     */
    List<Rectangle2D> layoutElements(Graphics2D g2, Rectangle2D bounds, 
            Map<String, Object> constraints);

    /**
     * Draws the element within the specified bounds.
     * 
     * @param g2  the graphics target (<code>null</code> not permitted).
     * @param bounds  the bounds (<code>null</code> not permitted).
     */
    void draw(Graphics2D g2, Rectangle2D bounds);
    
    /**
     * Draws the element within the specified bounds.  If the 
     * <code>recordBounds</code> flag is set, this element and each of its
     * children will have their <code>BOUNDS_2D</code> property updated with 
     * the current bounds.
     * 
     * @param g2  the graphics target (<code>null</code> not permitted).
     * @param bounds  the bounds (<code>null</code> not permitted).
     * @param recordBounds  record the bounds?
     * 
     * @since 1.3
     */
    void draw(Graphics2D g2, Rectangle2D bounds, boolean recordBounds);
    
    /**
     * Returns the value of the property with the specified key, or 
     * <code>null</code>.
     * 
     * @param key  the key (<code>null</code> not permitted).
     * 
     * @return The property value or <code>null</code>. 
     * 
     * @since 1.3
     */
    Object getProperty(String key);
    
    /**
     * Sets the value of the property with the specified key.
     * 
     * @param key  the key (<code>null</code> not permitted).
     * @param value  the value (<code>null</code> permitted).
     * 
     * @since 1.3
     */
    void setProperty(String key, Object value);
    
    /**
     * Receives a {@link TableElementVisitor}.  The visitor will have its
     * <code>visit(TableElement)</code> method called for each child element 
     * of this table element (if it has children) and then for this element.
     * 
     * @param visitor  the visitor (<code>null</code> not permitted).
     * 
     * @since 1.2
     */
    void receive(TableElementVisitor visitor);
    
}
