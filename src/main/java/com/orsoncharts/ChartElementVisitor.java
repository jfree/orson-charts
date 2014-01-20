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

package com.orsoncharts;

/**
 * A visitor for a {@link ChartElement}.
 */
public interface ChartElementVisitor {
    
    /**
     * Visit a chart element.
     * 
     * @param element  the chart element (<code>null</code> not permitted). 
     */
    void visit(ChartElement element);
    
}
