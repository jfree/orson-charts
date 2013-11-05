/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orsoncharts.util;

import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;

/**
 * A specification for the alignment and fitting of one rectangle (the source 
 * rectangle) with reference to another (the target rectangle).  One application
 * for this is to specify how the background image for a chart should be 
 * aligned and scaled.
 */
public class Fit2D {
    
    /** The anchor point for alignment. */
    private Anchor2D anchor;
    
    private Scale2D scale;
    
    public Fit2D(Anchor2D anchor, Scale2D scale) {
        this.anchor = anchor;
        this.scale = scale;
    }
    
    public Rectangle2D fit(Dimension2D srcDim, Rectangle2D target) {
        Anchor2D anchor;
        
        return null;
    }
}
