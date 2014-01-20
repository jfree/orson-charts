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

/**
 * A table element visitor.  This is a general purpose mechanism to traverse
 * a hierarchy of table elements.
 * 
 * @since 1.2
 */
public interface TableElementVisitor {
    
    /**
     * Performs the visitor's operation on the table element.
     * 
     * @param element  the element (<code>null</code> not permitted). 
     */
    void visit(TableElement element);
    
}
