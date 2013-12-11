/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.table;

/**
 * A {@link TableElement} that contains other elements (provides the
 * <code>addElement()</code> method).
 * 
 * @since 1.1
 */
public interface ContainerElement extends TableElement {
    
    /**
     * Adds a sub-element to the container element.
     * 
     * @param element  the element (<code>null</code> not permitted). 
     */
    void addElement(TableElement element);
    
}
