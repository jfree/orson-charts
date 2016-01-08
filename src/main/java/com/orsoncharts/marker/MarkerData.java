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

package com.orsoncharts.marker;

import java.awt.geom.Point2D;
import java.util.Map;
import java.util.HashMap;
import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.util.Anchor2D;

/**
 * A record holder for data relating to markers that needs to be passed
 * to the 3D engine.
 * 
 * @since 1.2
 */
public class MarkerData {
    
    /** 
     * The key used to store the marker on the axis.  We can use this key
     * to retrieve the actual marker to get the label, font, color etc.
     */
    private String markerKey;
    
    /** 
     * The type of marker data (value or range).  A value marker will have
     * a 'valueLine' stored in the data map.  A range marker will have
     * a 'startLine' and an 'endLine' stored in the data map.
     */
    private MarkerDataType type;
    
    /** Storage for data values (using a map for future expansion). */
    private Map<String, Object> data;
    
    /**
     * Creates marker data for the case where there is a single line
     * (for example, the {@link NumberMarker} class).
     * 
     * @param key  the key for the marker ({@code null} not permitted).
     * @param pos  the relative position along the axis (in the range 0.0 to 
     *     1.0).
     */
    public MarkerData(String key, double pos) {
        ArgChecks.nullNotPermitted(key, "key");
        this.markerKey = key;
        this.type = MarkerDataType.VALUE;
        this.data = new HashMap<String, Object>();
        this.data.put("valueLine", new MarkerLine(pos, false));
    }
    
    /**
     * Creates marker data for the case where there are two lines.
     * (for example, the {@link RangeMarker} class).
     * 
     * @param key  the key ({@code null} not permitted).
     * @param startPos  the relative start position.
     * @param startPegged  is the start position pegged?
     * @param endPos  the relative end position.
     * @param endPegged  is the end position pegged?
     */
    public MarkerData(String key, double startPos, boolean startPegged, 
            double endPos, boolean endPegged) {
        ArgChecks.nullNotPermitted(key, "key");
        this.markerKey = key;
        this.type = MarkerDataType.RANGE;
        this.data = new HashMap<String, Object>();
        this.data.put("startLine", new MarkerLine(startPos, startPegged));
        this.data.put("endLine", new MarkerLine(endPos, endPegged));
    }
    
    /**
     * Creates a new instance based on an existing source that has type
     * {@code MarkerDataType.VALUE}.  
     * 
     * @param source  the source ({@code null} not permitted).
     * @param v0  the vertex index for the start of the line.
     * @param v1  the vertex index for the end of the line.
     */
    public MarkerData(MarkerData source, int v0, int v1) {
        ArgChecks.nullNotPermitted(source, "source");
        if (!source.getType().equals(MarkerDataType.VALUE)) {
            throw new IllegalArgumentException("Must be MarkerDataType.VALUE");
        }
        this.markerKey = source.markerKey;
        this.type = source.type;
        this.data = new HashMap<String, Object>(source.data);
        double pos = source.getValueLine().getPos();
        MarkerLine valueLine = new MarkerLine(pos, false, v0, v1);
        this.data.put("valueLine", valueLine);
    }
    
    /**
     * Creates a new instance based on an existing source that has type
     * {@code MarkerDataType.Range}.  
     * 
     * @param source  the source ({@code null} not permitted).
     * @param v0  the vertex index for the start of the first line.
     * @param v1  the vertex index for the end of the first line.
     * @param v2  the vertex index for the start of the second line.
     * @param v3  the vertex index for the end of the second line.
     */
    public MarkerData(MarkerData source, int v0, int v1, int v2, int v3) {
        ArgChecks.nullNotPermitted(source, "source");
        if (!source.getType().equals(MarkerDataType.RANGE)) {
            throw new IllegalArgumentException("Must be MarkerDataType.RANGE");
        }
        this.markerKey = source.markerKey;
        this.type = MarkerDataType.RANGE;
        this.data = new HashMap<String, Object>(source.data);
        double startPos = source.getStartLine().getPos();
        boolean startPegged = source.getStartLine().isPegged();
        MarkerLine startLine = new MarkerLine(startPos, startPegged, v0, v1);
        this.data.put("startLine", startLine);
        double endPos = source.getEndLine().getPos();
        boolean endPegged = source.getEndLine().isPegged();
        MarkerLine endLine = new MarkerLine(endPos, endPegged, v2, v3);
        this.data.put("endLine", endLine);
    }
    
    /**
     * Returns the marker key (allows retrieval of the original marker object
     * when required).
     * 
     * @return The marker key (never {@code null}). 
     */
    public String getMarkerKey() {
        return this.markerKey;
    }
    
    /**
     * Returns the type of marker data (value or range).
     * 
     * @return The type (never {@code null}).
     */
    public MarkerDataType getType() {
        return this.type;
    }
    
    /**
     * A convenience method that returns the value line data for a value marker.
     * 
     * @return The value line (or {@code null}).
     */
    public MarkerLine getValueLine() {
        return (MarkerLine) this.data.get("valueLine");
    }
    
    /**
     * A convenience method that returns the start line data for a range marker.
     * 
     * @return The start line (or {@code null}).
     */
    public MarkerLine getStartLine() {
        return (MarkerLine) this.data.get("startLine");
    }
    
    /**
     * A convenience method that returns the end line data for a range marker.
     * 
     * @return The end line (or {@code null}).
     */
    public MarkerLine getEndLine() {
        return (MarkerLine) this.data.get("endLine");
    }
    
    /**
     * Returns the label anchor.
     * 
     * @return The label anchor. 
     */
    public Anchor2D getLabelAnchor() {
        return (Anchor2D) this.data.get("labelAnchor");
    }
    
    /**
     * Sets the label anchor.
     * 
     * @param anchor  the label anchor.
     */
    public void setLabelAnchor(Anchor2D anchor) {
        this.data.put("labelAnchor", anchor);
    }
    
    /**
     * Returns the label vertex index.
     * 
     * @return The label vertex index.
     */
    public int getLabelVertexIndex() {
        Integer i = (Integer) this.data.get("labelVertexIndex");
        return (i != null ? i.intValue() : -1);
    }
    
    /**
     * Sets the label vertex index.
     * 
     * @param labelVertexIndex  the label vertex index.
     */
    public void setLabelVertexIndex(int labelVertexIndex) {
        this.data.put("labelVertexIndex", Integer.valueOf(labelVertexIndex));    
    }
    
    /**
     * Returns the label projection point.
     * 
     * @return The label projection point (possibly {@code null}). 
     */
    public Point2D getLabelPoint() {
        return (Point2D) this.data.get("labelPoint");
    }
    
    /**
     * Updates the projected points for this marker.  This needs to be done
     * before the markers can be drawn.
     * 
     * @param pts  the projected points for the world. 
     */
    public void updateProjection(Point2D[] pts) {
        if (this.type.equals(MarkerDataType.VALUE)) {
            MarkerLine line = getValueLine();
            line.setStartPoint(pts[line.getV0()]);
            line.setEndPoint(pts[line.getV1()]);
        } else if (this.type.equals(MarkerDataType.RANGE)) {
            MarkerLine startLine = getStartLine();
            startLine.setStartPoint(pts[startLine.getV0()]);
            startLine.setEndPoint(pts[startLine.getV1()]);
            MarkerLine endLine = getEndLine();
            endLine.setStartPoint(pts[endLine.getV0()]);
            endLine.setEndPoint(pts[endLine.getV1()]);
        }
        int labelVertex = getLabelVertexIndex();
        if (labelVertex >= 0) {
            this.data.put("labelPoint", pts[labelVertex]);
        } else {
            this.data.put("labelPoint", null);
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MarkerData[key=");
        sb.append(this.markerKey); 
        sb.append("]");
        return sb.toString();
    }
}
