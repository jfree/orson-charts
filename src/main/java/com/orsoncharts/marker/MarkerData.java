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

package com.orsoncharts.marker;

import com.orsoncharts.util.ArgChecks;
import java.awt.geom.Point2D;
import java.util.HashMap;

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
    private HashMap<String, Object> data;
    
    /**
     * Creates marker data for the case where there is a single line.
     * 
     * @param key  the key for the marker (<code>null</code> not permitted).
     * @param pos  the relative position along the axis (in the range 0.0 to 
     *     1.0).
     */
    public MarkerData(String key, double pos) {
        ArgChecks.nullNotPermitted(key, "key");
        this.markerKey = key;
        this.type = MarkerDataType.VALUE;
        this.data = new HashMap<String, Object>();
        this.data.put("valueLine", new MarkerLine(pos));
    }
    
    /**
     * Creates marker data for the case where there are two lines.
     * 
     * @param key
     * @param startPos
     * @param endPos
     */
    public MarkerData(String key, double startPos, double endPos) {
        ArgChecks.nullNotPermitted(key, "key");
        this.markerKey = key;
        this.type = MarkerDataType.RANGE;
        this.data = new HashMap<String, Object>();
        this.data.put("startLine", new MarkerLine(startPos));
        this.data.put("endLine", new MarkerLine(endPos));
    }
    
    /**
     * Creates a new instance based on an existing source that has type
     * <code>MarkerDataType.VALUE</code>.  
     * 
     * @param source  the source (<code>null</code> not permitted).
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
        MarkerLine valueLine = new MarkerLine(pos, v0, v1);
        this.data.put("valueLine", valueLine);
    }
    
    /**
     * Creates a new instance based on an existing source that has type
     * <code>MarkerDataType.Range</code>.  
     * 
     * @param source  the source (<code>null</code> not permitted).
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
        MarkerLine startLine = new MarkerLine(startPos, v0, v1);
        this.data.put("startLine", startLine);
        double endPos = source.getEndLine().getPos();
        MarkerLine endLine = new MarkerLine(endPos, v2, v3);
        this.data.put("endLine", endLine);
    }
    
    /**
     * Returns the marker key (allows retrieval of the original marker object
     * when required).
     * 
     * @return The marker key (never <code>null</code>). 
     */
    public String getMarkerKey() {
        return this.markerKey;
    }
    
    /**
     * Returns the type of marker data (value or range).
     * 
     * @return The type (never <code>null</code>).
     */
    public MarkerDataType getType() {
        return this.type;
    }
    
    /**
     * A convenience method that returns the value line data for a value marker.
     * 
     * @return The value line (or <code>null</code>).
     */
    public MarkerLine getValueLine() {
        return (MarkerLine) this.data.get("valueLine");
    }
    
    /**
     * A convenience method that returns the start line data for a range marker.
     * 
     * @return The start line (or <code>null</code>).
     */
    public MarkerLine getStartLine() {
        return (MarkerLine) this.data.get("startLine");
    }
    
    /**
     * A convenience method that returns the end line data for a range marker.
     * 
     * @return The end line (or <code>null</code>).
     */
    public MarkerLine getEndLine() {
        return (MarkerLine) this.data.get("endLine");
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
        // if range then update the 2D points for the start and end lines
        System.out.println("updateProjection...");
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MarkerData[key=");
        sb.append(this.markerKey); // TODO : fill this out
        sb.append("]");
        return sb.toString();
    }
}
