/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
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
     * @param element  the element (<code>null</code> not permitted).
     * @param dimension  the dimension (<code>null</code> not permitted).
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
     * @return The element (never <code>null</code>). 
     */
    public TableElement getElement() {
        return this.element;
    }
    
    /**
     * Returns the element's size.
     * 
     * @return The element's size (never <code>null</code>). 
     */
    public Dimension2D getDimension() {
        return this.dimension;
    }
    
}
