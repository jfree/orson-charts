/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.table;

import java.awt.geom.Dimension2D;

/**
 * A dimension.
 */
public class ElementDimension extends Dimension2D {

    private double width;
    
    private double height;
    
    public ElementDimension() {
        super();
    }
    
    public ElementDimension(double width, double height) {
        super();
        this.width = width;
        this.height = height;
    }
    
    @Override
    public double getWidth() {
        return this.width;
    }

    @Override
    public double getHeight() {
        return this.height;
    }

    @Override
    public void setSize(double width, double height) {
        this.width = width;
        this.height = height;
    }
    
}
