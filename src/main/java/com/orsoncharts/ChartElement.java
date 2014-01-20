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
 * A chart element used to implement the Visitor pattern for applying changes
 * to the chart.  This is used by the chart styling feature.
 * 
 * @since 1.2
 */
public interface ChartElement {
    
    /**
     * Receives a visitor to the element.
     * 
     * @param visitor  the visitor (<code>null</code> not permitted). 
     */
    void receive(ChartElementVisitor visitor);

}
