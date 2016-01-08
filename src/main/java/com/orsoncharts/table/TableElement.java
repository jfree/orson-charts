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
    
    /**
     * A property key for the class of a table element.
     * 
     * @since 1.3
     */
    final String CLASS = "class";
    
    /**
     * Calculates the preferred size for the element, with reference to the 
     * specified bounds.  The preferred size can exceed the bounds, but the
     * bounds might influence how sub-elements are sized and/or positioned.
     * For example, in a {@link FlowElement}, the width of the bounds will
     * determine when the flow layout wraps.
     * 
     * @param g2  the graphics target ({@code null} not permitted).
     * @param bounds  the bounds ({@code null} not permitted).
     * 
     * @return The preferred size (never {@code null}).
     */
    Dimension2D preferredSize(Graphics2D g2, Rectangle2D bounds);
    
    /**
     * Returns the preferred size of the element, subject to the supplied
     * constraints.
     * 
     * @param g2  the graphics target ({@code null} not permitted).
     * @param bounds  the bounds ({@code null} not permitted).
     * @param constraints  the constraints ({@code null} permitted).
     * 
     * @return The preferred size. 
     */
    Dimension2D preferredSize(Graphics2D g2, Rectangle2D bounds, 
            Map<String, Object> constraints);
    
    /**
     * Returns the reference point used to align the element with the bounding
     * rectangle within which it is drawn.
     * 
     * @return The anchor point (never {@code null}). 
     */
    RefPt2D getRefPoint();
    
    /**
     * Performs a layout of this table element, returning a list of bounding
     * rectangles for the element and its subelements.  This method is
     * typically called by the {@link #draw(java.awt.Graphics2D, 
     * java.awt.geom.Rectangle2D)} method.
     * 
     * @param g2  the graphics target ({@code null} not permitted).
     * @param bounds  the bounds ({@code null} not permitted).
     * @param constraints  the constraints (if any).
     * 
     * @return A list of bounding rectangles. 
     */
    List<Rectangle2D> layoutElements(Graphics2D g2, Rectangle2D bounds, 
            Map<String, Object> constraints);

    /**
     * Draws the element within the specified bounds.
     * 
     * @param g2  the graphics target ({@code null} not permitted).
     * @param bounds  the bounds ({@code null} not permitted).
     */
    void draw(Graphics2D g2, Rectangle2D bounds);
    
    /**
     * Draws the element within the specified bounds.  The 
     * {@code onDrawHandler} provides (optional) access to all elements 
     * before and after they are rendered.
     * 
     * @param g2  the graphics target ({@code null} not permitted).
     * @param bounds  the bounds ({@code null} not permitted).
     * @param onDrawHandler  an object that will receive notification before 
     *     and after the element is drawn ({@code null} permitted).
     * 
     * @since 1.3
     */
    void draw(Graphics2D g2, Rectangle2D bounds, 
            TableElementOnDraw onDrawHandler);
    
    /**
     * Returns the value of the property with the specified key, or 
     * {@code null}.
     * 
     * @param key  the key ({@code null} not permitted).
     * 
     * @return The property value or {@code null}. 
     * 
     * @since 1.3
     */
    Object getProperty(String key);
    
    /**
     * Sets the value of the property with the specified key.
     * 
     * @param key  the key ({@code null} not permitted).
     * @param value  the value ({@code null} permitted).
     * 
     * @since 1.3
     */
    void setProperty(String key, Object value);
    
    /**
     * Receives a {@link TableElementVisitor}.  The visitor will have its
     * {@code visit(TableElement)} method called for each child element 
     * of this table element (if it has children) and then for this element.
     * 
     * @param visitor  the visitor ({@code null} not permitted).
     * 
     * @since 1.2
     */
    void receive(TableElementVisitor visitor);
    
}
