/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orsoncharts.axis;

import java.awt.geom.Point2D;

/**
 *
 * @author dgilbert
 */
public class TickData {
    
    /** The position along the axis as a percentage. */
    private double pos;
    
    private Comparable key;
    
    private double dataValue;

    private int vertexIndex;
    
    /** The anchor point. */
    private Point2D anchorPt;
    
    public TickData(double pos, Comparable key) {
        this.pos = pos;
        this.key = key;
        this.dataValue = Double.NaN;
        this.vertexIndex = -1;
        this.anchorPt = null;
    }
    
    public TickData(double pos, double dataValue) {
        this.pos = pos;
        this.dataValue = dataValue;
        this.key = null;
        this.vertexIndex = -1;
        this.anchorPt = null;
    }
    
    public TickData(double pos, double dataValue, int vertexIndex) {
        this.pos = pos;
        this.dataValue = dataValue;
        this.vertexIndex = vertexIndex;
    }
    
    public double getPos() {
        return this.pos;
    }
    
    public Comparable getKey() {
        return this.key;
    }
    
    public double getDataValue() {
        return this.dataValue;
    }
    
    public int getVertexIndex() {
        return this.vertexIndex;
    }
    
    public void setVertexIndex(int index) {
        this.vertexIndex = index;
    }
    public Point2D getAnchorPt() {
        return this.anchorPt;
    }
    
    public void setAnchor(Point2D anchorPt) {
        this.anchorPt = anchorPt;
    }
}
