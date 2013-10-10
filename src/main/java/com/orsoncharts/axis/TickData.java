/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.axis;

import java.awt.geom.Point2D;

/**
 * Data related to the tick marks and labels on a chart.
 */
public class TickData {
    
    /** The position along the axis as a percentage. */
    private double pos;
    
    /** The key for the tick (used for CategoryAxis3D, null otherwise). */
    private Comparable key;
    
    /** The data value (used for ValueAxis3D). */
    private double dataValue;

    /** The vertex in the ChartBox that is the anchor point for this tick. */
    private int vertexIndex;
    
    /** The anchor point. */
    private Point2D anchorPt;
    
    /**
     * Creates a new instance.
     * 
     * @param pos  the position along the axis as a percentage of the range.
     * @param key  the key.
     */
    public TickData(double pos, Comparable key) {
        this.pos = pos;
        this.key = key;
        this.dataValue = Double.NaN;
        this.vertexIndex = -1;
        this.anchorPt = null;
    }
    
    /**
     * Creates a new instance.
     * 
     * @param pos  the position along the axis as a percentage of the range.
     * @param dataValue  the data value.
     */
    public TickData(double pos, double dataValue) {
        this.pos = pos;
        this.dataValue = dataValue;
        this.key = null;
        this.vertexIndex = -1;
        this.anchorPt = null;
    }
    
    public TickData(TickData source, int vertexIndex) {
        this.pos = source.pos;
        this.dataValue = source.dataValue;
        this.key = source.key;
        this.anchorPt = source.anchorPt;
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
    
    public void setAnchorPt(Point2D anchorPt) {
        this.anchorPt = anchorPt;
    }
}
