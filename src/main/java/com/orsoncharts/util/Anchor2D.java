/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orsoncharts.util;

/**
 *
 * @author dgilbert
 */
public class Anchor2D {
    
    private ReferencePoint2D refPt;
    
    private Offset2D offset;
    
    public Anchor2D() {
        this(ReferencePoint2D.TOP_LEFT, new Offset2D(4, 4));
    }
    
    public Anchor2D(ReferencePoint2D refPt, Offset2D offset) {
        this.refPt = refPt;
        this.offset = offset;
    }
    
    public ReferencePoint2D getRefPt() {
        return this.refPt;
    }
    
    public Offset2D getOffset() {
        return this.offset;
    }
}
