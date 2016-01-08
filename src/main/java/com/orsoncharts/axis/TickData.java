/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2016, by Object Refinery Limited.  All rights reserved.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 * 
 * If you do not wish to be bound by the terms of the GPL, an alternative
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts home page:
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 */

package com.orsoncharts.axis;

import java.awt.geom.Point2D;
import com.orsoncharts.util.ArgChecks;

/**
 * Data related to the tick marks and labels on a chart.
 */
public class TickData {
    
    /** The position along the axis as a percentage. */
    private double pos;
    
    /** The key for the tick (used for CategoryAxis3D, null otherwise). */
    private Comparable<?> key;
    
    /** 
     * The label used for the category key (used for CategoryAxis3D, null 
     * otherwise). 
     */
    private String keyLabel;
    
    /** The data value (used for ValueAxis3D). */
    private double dataValue;

    /** The vertex in the ChartBox that is the anchor point for this tick. */
    private int vertexIndex;
    
    /** The anchor point. */
    private Point2D anchorPt;
    
    /**
     * Creates a new instance.  This constructor is used for category axes.
     * 
     * @param pos  the position along the axis as a percentage of the range.
     * @param key  the key.
     * @param keyLabel  the key label.
     */
    public TickData(double pos, Comparable<?> key, String keyLabel) {
        this.pos = pos;
        this.key = key;
        this.keyLabel = keyLabel;
        this.dataValue = Double.NaN;
        this.vertexIndex = -1;
        this.anchorPt = null;
    }
    
    /**
     * Creates a new instance.  This constructor is used for numerical axes.
     * 
     * @param pos  the position along the axis as a percentage of the range.
     * @param dataValue  the data value.
     */
    public TickData(double pos, double dataValue) {
        this.pos = pos;
        this.dataValue = dataValue;
        this.key = null;
        this.keyLabel = null;
        this.vertexIndex = -1;
        this.anchorPt = null;
    }
    
    /**
     * Creates a new instance by copying an existing instance but altering 
     * the vertex index.
     * 
     * @param source  a source to copy ({@code null} not permitted).
     * @param vertexIndex  the vertex index.
     */
    public TickData(TickData source, int vertexIndex) {
        ArgChecks.nullNotPermitted(source, "source");
        this.pos = source.pos;
        this.dataValue = source.dataValue;
        this.key = source.key;
        this.keyLabel = source.keyLabel;
        this.anchorPt = source.anchorPt;
        this.vertexIndex = vertexIndex;
    }
    
    /**
     * Returns the position of the tick, as a percentage along the axis (for 
     * example, 0.5 is halfway along the axis).
     * 
     * @return The position. 
     */
    public double getPos() {
        return this.pos;
    }
    
    /**
     * Returns the key when the tick data is for a {@link CategoryAxis3D}, 
     * and {@code null} otherwise.
     * 
     * @return The key (possibly {@code null}).  
     */
    public Comparable<?> getKey() {
        return this.key;
    }
    
    /**
     * Returns the key label.
     * 
     * @return The key label (possibly {@code null}).
     * 
     * @since 1.2
     */
    public String getKeyLabel() {
        return this.keyLabel;
    }
    
    /**
     * Returns the data value when the tick data is for a {@link NumberAxis3D},
     * and {@code Double.NaN} otherwise.
     * 
     * @return The data value.
     */
    public double getDataValue() {
        return this.dataValue;
    }
    
    /**
     * Returns the vertex index that is the anchor point for the tick mark.
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
