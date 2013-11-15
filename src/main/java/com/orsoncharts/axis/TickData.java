/* ============
 * Orson Charts
 * ============
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
    
    /**
     * Creates a new instance.
     * 
     * @param source  a source to copy.
     * @param vertexIndex  the vertex index.
     */
    public TickData(TickData source, int vertexIndex) {
        this.pos = source.pos;
        this.dataValue = source.dataValue;
        this.key = source.key;
        this.anchorPt = source.anchorPt;
        this.vertexIndex = vertexIndex;
    }
    
    /**
     * Returns the position.
     * 
     * @return The position. 
     */
    public double getPos() {
        return this.pos;
    }
    
    /**
     * Returns the key.
     * 
     * @return The key. 
     */
    public Comparable getKey() {
        return this.key;
    }
    
    /**
     * Returns the data value.
     * 
     * @return The data value.
     */
    public double getDataValue() {
        return this.dataValue;
    }
    
    /**
     * Returns the vertex index.
     * 
     * @return The vertex index. 
     */
    public int getVertexIndex() {
        return this.vertexIndex;
    }
    
    /**
     * Sets the vertex index.  The vertex is a point in 3D space that is 
     * the anchor point for the tick mark, after this is projected to 
     * 2D space we have a reference point for the tick mark.
     * 
     * @param index  the index. 
     */
    public void setVertexIndex(int index) {
        this.vertexIndex = index;
    }
    
    /**
     * Returns the anchor point.
     * 
     * @return The anchor point.
     */
    public Point2D getAnchorPt() {
        return this.anchorPt;
    }
    
    /**
     * Sets the anchor point.  This is the projected point in 2D space of the
     * vertex we track in 3D space.
     * 
     * @param anchorPt  the anchor point. 
     */
    public void setAnchorPt(Point2D anchorPt) {
        this.anchorPt = anchorPt;
    }
}
