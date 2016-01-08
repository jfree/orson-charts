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

import java.awt.geom.Dimension2D;
import com.orsoncharts.util.ArgChecks;

/**
 * An object that pairs an element with its dimension for one specific
 * graphics target.
 * 
 * @since 1.1
 */
public class ElementInfo {
    
    /** The element. */
    private TableElement element;
    
    /** The element's size. */
    private Dimension2D dimension;
    
    /**
     * Creates a new instance.
     * 
     * @param element  the element ({@code null} not permitted).
     * @param dimension  the dimension ({@code null} not permitted).
     */
    public ElementInfo(TableElement element, Dimension2D dimension) {
        ArgChecks.nullNotPermitted(element, "element");
        ArgChecks.nullNotPermitted(dimension, "dimension");
        this.element = element;
        this.dimension = dimension;
    }
    
    /**
     * Returns the element.
     * 
     * @return The element (never {@code null}). 
     */
    public TableElement getElement() {
        return this.element;
    }
    
    /**
     * Returns the element's size.
     * 
     * @return The element's size (never {@code null}). 
     */
    public Dimension2D getDimension() {
        return this.dimension;
    }
    
}
