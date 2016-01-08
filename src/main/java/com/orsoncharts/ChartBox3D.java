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

package com.orsoncharts;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.orsoncharts.graphics3d.Face;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.Point3D;
import com.orsoncharts.axis.TickData;
import com.orsoncharts.marker.MarkerData;
import com.orsoncharts.marker.MarkerDataType;
import com.orsoncharts.util.Anchor2D;
import com.orsoncharts.util.ArgChecks;

/**
 * A chart box is the container within which the chart elements are drawn.
 * The six faces of the box created so that they are visible only when they 
 * do not obscure the content of the chart (generally the back three faces 
 * will be visible and the front three faces will not be visible, although from
 * some angles four faces will be visible at one time).  There is also support 
 * provided for specifying gridlines on the visible faces, as well as markers
 * to label specific values and value ranges.
 */
public class ChartBox3D {

    private double xLength, yLength, zLength;
    private double xOffset, yOffset, zOffset;
    
    /** 
     * Tick info for the x-axis (or column axis). 
     */
    private List<TickData> xTicks;
    
    /** 
     * Tick info for the y-axis (or value axis). 
     */
    private List<TickData> yTicks;
    
    /** 
     * Tick info for the z-axis (or row axis). 
     */
    private List<TickData> zTicks;
    
    /** 
     * Incoming data for x-axis markers.  New instances are created and
     * assigned to chart box faces to track vertices.
     */
    private List<MarkerData> xMarkers;
    
    /** Required data for y-axis markers. */
    private List<MarkerData> yMarkers;
    
    /** Required data for z-axis markers. */
    private List<MarkerData> zMarkers;

    private Color color;

    private ChartBoxFace faceA;
    private ChartBoxFace faceB;
    private ChartBoxFace faceC;
    private ChartBoxFace faceD;
    private ChartBoxFace faceE;
    private ChartBoxFace faceF;

    /**
     * Creates a new chart box with the specified attributes.  When drawn, only
     * the faces at the rear of the box will be rendered, allowing the user to 
     * see the content of the box (typically the plot items).
     * 
     * @param xLength  the x-dimension.
     * @param yLength  the y-dimension.
     * @param zLength  the z-dimension.
     * @param xOffset  the x-offset.
     * @param yOffset  the y-offset.
     * @param zOffset  the z-offset.
     * @param color  the color for the sides of the box ({@code null} not 
     *     permitted).
     */
    public ChartBox3D(double xLength, double yLength, double zLength, 
            double xOffset, double yOffset, double zOffset, Color color) {
        ArgChecks.nullNotPermitted(color, "color");
        this.xLength = xLength;
        this.yLength = yLength;
        this.zLength = zLength;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;
        this.color = color;
        this.xTicks = new ArrayList<TickData>(0);
        this.yTicks = new ArrayList<TickData>(0);
        this.zTicks = new ArrayList<TickData>(0);
        this.xMarkers = new ArrayList<MarkerData>(0);
        this.yMarkers = new ArrayList<MarkerData>(0);
        this.zMarkers = new ArrayList<MarkerData>(0);
    }

    /**
     * Returns the list of tick data items for the x-axis.
     * 
     * @return The list of tick data items for the x-axis (possibly empty, but
     *     never {@code null}).
     * 
     * @since 1.2
     */
    public List<TickData> getXTicks() {
        return this.xTicks;
    }
    
    /**
     * Sets the list of tick data items for the x-axis.
     * 
     * @param ticks  the tick data ({@code null} not permitted).
     * 
     * @since 1.2
     */
    public void setXTicks(List<TickData> ticks) {
        ArgChecks.nullNotPermitted(ticks, "ticks");
        this.xTicks = ticks;
    }
    
    /**
     * Returns the list of tick data items for the y-axis.
     * 
     * @return The list of tick data items for the y-axis (possibly empty, but
     *     never {@code null}).
     * 
     * @since 1.2
     */
    public List<TickData> getYTicks() {
        return this.yTicks;
    }
    
    /**
     * Sets the list of tick data items for the y-axis.
     * 
     * @param ticks  the tick data ({@code null} not permitted).
     * 
     * @since 1.2
     */
    public void setYTicks(List<TickData> ticks) {
        ArgChecks.nullNotPermitted(ticks, "ticks");
        this.yTicks = ticks;
    }
    
    /**
     * Returns the list of tick data items for the z-axis.
     * 
     * @return The list of tick data items for the z-axis (possibly empty, but
     *     never {@code null}).
     * 
     * @since 1.2
     */
    public List<TickData> getZTicks() {
        return this.zTicks;
    }
    
    /**
     * Sets the list of tick data items for the z-axis.
     * 
     * @param ticks  the tick data ({@code null} not permitted).
     * 
     * @since 1.2
     */
    public void setZTicks(List<TickData> ticks) {
        ArgChecks.nullNotPermitted(ticks, "ticks");
        this.zTicks = ticks;
    }
    
    /**
     * Returns the marker data for the x-axis markers, if any.
     * 
     * @return The marker data for the x-axis markers (possibly empty but 
     *     never {@code null}).
     * 
     * @since 1.2
     */
    public List<MarkerData> getXMarkers() {
        return this.xMarkers;
    }
 
    /**
     * Sets the list of marker data items for the x-axis.
     * 
     * @param markers  the list of marker data items ({@code null} not 
     *     permitted).
     * 
     * @since 1.2
     */
    public void setXMarkers(List<MarkerData> markers) {
        ArgChecks.nullNotPermitted(markers, "markers");
        this.xMarkers = markers;
    }
 
    /**
     * Returns the marker data for the y-axis markers, if any.
     * 
     * @return The marker data for the y-axis markers (possibly empty but 
     *     never {@code null}).
     * 
     * @since 1.2
     */
    public List<MarkerData> getYMarkers() {
        return this.yMarkers;
    }

    /**
     * Sets the list of marker data items for the y-axis.
     * 
     * @param markers  the list of marker data items ({@code null} not 
     *     permitted).
     * 
     * @since 1.2
     */
    public void setYMarkers(List<MarkerData> markers) {
        ArgChecks.nullNotPermitted(markers, "markers");
        this.yMarkers = markers;
    }
 
    /**
     * Returns the marker data for the z-axis markers, if any.
     * 
     * @return The marker data for the z-axis markers (possibly empty but 
     *     never {@code null}).
     * 
     * @since 1.2
     */
    public List<MarkerData> getZMarkers() {
        return this.zMarkers;
    }

    /**
     * Sets the list of marker data items for the x-axis.
     * 
     * @param markers  the list of marker data items ({@code null} not 
     *     permitted).
     * 
     * @since 1.2
     */
    public void setZMarkers(List<MarkerData> markers) {
        ArgChecks.nullNotPermitted(markers, "markers");
        this.zMarkers = markers;
    }

    /**
     * Returns face A (the bottom face, in the XZ axis plane).
     * 
     * @return Face A. 
     */
    public ChartBoxFace faceA() {
        return this.faceA;
    }
  
    /**
     * Returns face B (the front face, in the XY axis plane).
     * 
     * @return Face B. 
     */
    public ChartBoxFace faceB() {
        return this.faceB;
    }
  
    /**
     * Returns face C (the top face, in the XZ axis plane).
     * 
     * @return Face C. 
     */
    public ChartBoxFace faceC() {
        return this.faceC;
    }
  
    /**
     * Returns face D (the rear face, in the XY axis plane).
     * 
     * @return Face D. 
     */
    public ChartBoxFace faceD() {
        return this.faceD;
    }

    /**
     * Returns face E (the left face, in the YZ axis plane).
     * 
     * @return Face E. 
     */
    public ChartBoxFace faceE() {
        return this.faceE;
    }
  
    /**
     * Returns face F (the right face, in the YZ axis plane).
     * 
     * @return Face F.
     */
    public ChartBoxFace faceF() {
        return this.faceF;
    }

    /**
     * Creates an {@link Object3D} that contains the six faces for the 
     * chart box, plus the vertices for the tick marks along the edges of
     * each face.
     * 
     * @return A 3D object. 
     */
    public Object3D createObject3D() {
        Object3D box = new Object3D(this.color);
        Point3D v0 = new Point3D(xOffset, yOffset, zOffset);
        Point3D v1 = new Point3D(xLength + xOffset, yOffset, zOffset);
        Point3D v2 = new Point3D(xLength + xOffset, yLength + yOffset, zOffset);
        Point3D v3 = new Point3D(xOffset, yLength + yOffset, zOffset);
        Point3D v4 = new Point3D(xOffset, yLength + yOffset, zLength + zOffset);
        Point3D v5 = new Point3D(xOffset, yOffset, zLength + zOffset);
        Point3D v6 = new Point3D(xLength + xOffset, yOffset, zLength + zOffset);
        Point3D v7 = new Point3D(xLength + xOffset, yLength + yOffset, zLength 
                + zOffset);

        box.addVertex(v0);   // 0, 0, 0
        box.addVertex(v1);   // 1, 0, 0
        box.addVertex(v2);   // 1, 1, 0
        box.addVertex(v3);   // 0, 1, 0

        box.addVertex(v4);   // 0, 1, 1
        box.addVertex(v5);   // 0, 0, 1
        box.addVertex(v6);   // 1, 0, 1
        box.addVertex(v7);   // 1, 1, 1
                
        this.faceA = new ChartBoxFace(box, new int[] {0, 5, 6, 1}); // XZ
        this.faceB = new ChartBoxFace(box, new int[] {0, 1, 2, 3}); // XY
        this.faceC = new ChartBoxFace(box, new int[] {7, 4, 3, 2}); // XZ
        this.faceD = new ChartBoxFace(box, new int[] {5, 4, 7, 6}); // XY
        this.faceE = new ChartBoxFace(box, new int[] {0, 3, 4, 5}); // YZ
        this.faceF = new ChartBoxFace(box, new int[] {6, 7, 2, 1}); // YZ
        box.addFace(faceA);
        box.addFace(faceB);
        box.addFace(faceC);
        box.addFace(faceD);
        box.addFace(faceE);
        box.addFace(faceF);

        // add vertices for the x-grid lines (ABCD)
        int base = 8;
        for (TickData t : this.xTicks) {
            double xx = this.xOffset + this.xLength * t.getPos();
            box.addVertex(xx, yOffset, zOffset);
            box.addVertex(xx, yOffset, zOffset + zLength);
            box.addVertex(xx, yOffset + yLength,  zOffset + zLength);
            box.addVertex(xx, yOffset + yLength, zOffset);
            TickData td0 = new TickData(t, base);
            TickData td1 = new TickData(t, base + 1);
            TickData td2 = new TickData(t, base + 2);
            TickData td3 = new TickData(t, base + 3);
            this.faceA.addXTicks(td0, td1);
            this.faceB.addXTicks(td0, td3);
            this.faceC.addXTicks(td3, td2);
            this.faceD.addXTicks(td2, td1);
            base += 4;
        }
        
        // add vertices for the y-grid lines (BDEF)
        for (TickData t : this.yTicks) {
            double yy = this.yOffset + this.yLength * t.getPos();
            box.addVertex(xOffset, yy, zOffset);
            box.addVertex(xOffset + xLength, yy, zOffset);
            box.addVertex(xOffset + xLength, yy, zOffset + zLength);
            box.addVertex(xOffset, yy, zOffset + zLength);
            TickData td0 = new TickData(t, base);
            TickData td1 = new TickData(t, base + 1);
            TickData td2 = new TickData(t, base + 2);
            TickData td3 = new TickData(t, base + 3);
            this.faceB.addYTicks(td0, td1);
            this.faceD.addYTicks(td2, td3);
            this.faceE.addYTicks(td0, td3);
            this.faceF.addYTicks(td1, td2);
            base += 4;
        }

        // add vertices for the z-grid lines (ACEF)
        for (TickData t : this.zTicks) {
            double zz = this.zOffset + this.zLength * t.getPos();
            box.addVertex(xOffset, yOffset, zz);
            box.addVertex(xOffset + xLength, yOffset, zz);
            box.addVertex(xOffset + xLength, yOffset + yLength, zz);
            box.addVertex(xOffset, yOffset + yLength, zz);
            TickData td0 = new TickData(t, base);
            TickData td1 = new TickData(t, base + 1);
            TickData td2 = new TickData(t, base + 2);
            TickData td3 = new TickData(t, base + 3);
            this.faceA.addZTicks(td0, td1);
            this.faceC.addZTicks(td3, td2);
            this.faceE.addZTicks(td0, td3);
            this.faceF.addZTicks(td1, td2);
            base += 4;
        }
        
        // add vertices for the x-markers
        for (MarkerData m : this.xMarkers) {
            if (m.getType().equals(MarkerDataType.VALUE)) {
                double xpos = this.xOffset + xLength 
                        * m.getValueLine().getPos();
                base += addXMarker(box, m, xpos, base);
            } else if (m.getType().equals(MarkerDataType.RANGE)) {
                double startX = this.xOffset + xLength 
                        * m.getStartLine().getPos();
                double endX = this.xOffset + xLength * m.getEndLine().getPos();
                base += addXRangeMarker(box, m, startX, endX, base);
            }
        }
        
        // add vertices for the y-markers
        for (MarkerData m : this.yMarkers) {
            if (m.getType().equals(MarkerDataType.VALUE)) {
                double ypos = this.yOffset + yLength 
                        * m.getValueLine().getPos();
                base += addYMarker(box, m, ypos, base);
            } else if (m.getType().equals(MarkerDataType.RANGE)) {
                double startY = this.yOffset + yLength 
                        * m.getStartLine().getPos();
                double endY = this.yOffset + yLength * m.getEndLine().getPos();
                base += addYRangeMarker(box, m, startY, endY, base);
            }
        }
        
        // add vertices for the z-markers
        for (MarkerData m : this.zMarkers) {
            if (m.getType().equals(MarkerDataType.VALUE)) {
                double zpos = this.zOffset + zLength 
                        * m.getValueLine().getPos();
                base += addZMarker(box, m, zpos, base);
            } else if (m.getType().equals(MarkerDataType.RANGE)) {
                double startZ = this.zOffset + zLength 
                        * m.getStartLine().getPos();
                double endZ = this.zOffset + zLength * m.getEndLine().getPos();
                base += addZRangeMarker(box, m, startZ, endZ, base);
            }            
        }
        
        return box;
    }

    /**
     * Adds the vertices required for an x-marker (VALUE type), creates the 
     * marker data records and adds them to the four faces that will be 
     * required to draw the marker.  If there is a label for the marker, this 
     * method will also add vertices to track the label anchor points. 
     * 
     * @param box  the chart box.
     * @param m  the marker data record.
     * @param x  the x position for the marker.
     * @param base  the base vertex index.
     */
    private int addXMarker(Object3D box, MarkerData m, double x, int base) {
        int result = 4;
        Point3D v0 = new Point3D(x, yOffset, zOffset);
        Point3D v1 = new Point3D(x, yOffset, zOffset + zLength);
        Point3D v2 = new Point3D(x, yOffset + yLength,  zOffset + zLength);
        Point3D v3 = new Point3D(x, yOffset + yLength, zOffset);
        box.addVertex(v0);
        box.addVertex(v1);
        box.addVertex(v2);
        box.addVertex(v3);
        MarkerData md0 = new MarkerData(m, base, base + 1); // A
        MarkerData md1 = new MarkerData(m, base + 1, base + 2); // D 
        MarkerData md2 = new MarkerData(m, base + 2, base + 3); // C
        MarkerData md3 = new MarkerData(m, base + 3, base);  // B
        if (m.getLabelAnchor() != null) {
            // add vertices for the label anchor
            Point3D v4 = calcAnchorXY(m.getLabelAnchor(), v0, v3, xLength); // B
            Point3D v5 = calcAnchorXY(m.getLabelAnchor(), v2, v1, xLength); // D
            Point3D v6 = calcAnchorXZ(m.getLabelAnchor(), v1, v0, xLength); // A
            Point3D v7 = calcAnchorXZ(m.getLabelAnchor(), v3, v2, xLength);
            box.addVertex(v4);
            box.addVertex(v5);
            box.addVertex(v6);
            box.addVertex(v7);
            // now write back the label vertex indices to the marker data
            md3.setLabelVertexIndex(base + 4);
            md1.setLabelVertexIndex(base + 5);
            md0.setLabelVertexIndex(base + 6);
            md2.setLabelVertexIndex(base + 7);
            result += 4;
        }
        this.faceA.addXMarker(md0);
        this.faceD.addXMarker(md1);
        this.faceC.addXMarker(md2);
        this.faceB.addXMarker(md3);
        return result;
    }
    
    /**
     * Adds the vertices required for an x-marker (RANGE type), creates the 
     * marker data records and adds them to the four faces that will be 
     * required to draw the marker.  If there is a label for the marker, this 
     * method will also add vertices to track the label anchor points. 
     * 
     * @param box  the chart box.
     * @param m  the marker data record.
     * @param startX  the starting x position for the marker.
     * @param endX  the ending x position for the marker.
     * @param base  the base vertex index.
     */
    private int addXRangeMarker(Object3D box, MarkerData m, double startX,
            double endX, int base) {
        int result = 8; // number of vertices added
        Point3D v0 = new Point3D(startX, yOffset, zOffset);
        Point3D v1 = new Point3D(startX, yOffset, zOffset + zLength);
        Point3D v2 = new Point3D(startX, yOffset + yLength,  zOffset + zLength);
        Point3D v3 = new Point3D(startX, yOffset + yLength, zOffset);
        Point3D v4 = new Point3D(endX, yOffset, zOffset);
        Point3D v5 = new Point3D(endX, yOffset, zOffset + zLength);
        Point3D v6 = new Point3D(endX, yOffset + yLength,  zOffset + zLength);
        Point3D v7 = new Point3D(endX, yOffset + yLength, zOffset);
        box.addVertex(v0);
        box.addVertex(v1);
        box.addVertex(v2);
        box.addVertex(v3);
        box.addVertex(v4);
        box.addVertex(v5);
        box.addVertex(v6);
        box.addVertex(v7);
        MarkerData md0 = new MarkerData(m, base, base + 1, base + 4, 
                base + 5); // A
        MarkerData md1 = new MarkerData(m, base + 1, base + 2, base + 5, 
                base + 6); // D 
        MarkerData md2 = new MarkerData(m, base + 2, base + 3, base + 6, 
                base + 7); // C
        MarkerData md3 = new MarkerData(m, base + 3, base + 0, base + 7, 
                base + 4);  // B
        if (m.getLabelAnchor() != null) {
            // add vertices for the label anchor
            Point3D v8 = calcRangeAnchorXY(m.getLabelAnchor(), v2, v1, v6, v5);
            Point3D v9 = calcRangeAnchorXY(m.getLabelAnchor(), v0, v3, v4, v7); 
            Point3D v10 = calcRangeAnchorXZ(m.getLabelAnchor(), v3, v2, v7, v6);
            Point3D v11 = calcRangeAnchorXZ(m.getLabelAnchor(), v1, v0, v5, v4);
            box.addVertex(v8);
            box.addVertex(v9);
            box.addVertex(v10);
            box.addVertex(v11);
            // now write back the label vertex indices to the marker data
            md1.setLabelVertexIndex(base + 8);
            md3.setLabelVertexIndex(base + 9);
            md2.setLabelVertexIndex(base + 10);
            md0.setLabelVertexIndex(base + 11);
            result += 4;
        }
        this.faceA.addXMarker(md0);
        this.faceD.addXMarker(md1);
        this.faceC.addXMarker(md2);
        this.faceB.addXMarker(md3);
        return result;
    }
    
    /**
     * Adds the vertices required for an y-marker (VALUE type), creates the 
     * marker data records and adds them to the four faces that will be 
     * required to draw the marker.  If there is a label for the marker, this 
     * method will also add vertices to track the label anchor points. 
     * 
     * @param box  the chart box.
     * @param m  the marker data record.
     * @param y  the y position for the marker.
     * @param base  the base vertex index.
     */
    private int addYMarker(Object3D box, MarkerData m, double y, int base) {
        int result = 4; // number of vertices added
        Point3D v0 = new Point3D(xOffset, y, zOffset);
        Point3D v1 = new Point3D(xOffset, y, zOffset + zLength);
        Point3D v2 = new Point3D(xOffset + xLength, y,  zOffset + zLength);
        Point3D v3 = new Point3D(xOffset + xLength, y, zOffset);
        box.addVertex(v0);
        box.addVertex(v1);
        box.addVertex(v2);
        box.addVertex(v3);
        MarkerData md0 = new MarkerData(m, base, base + 1); // E
        MarkerData md1 = new MarkerData(m, base + 1, base + 2); // D 
        MarkerData md2 = new MarkerData(m, base + 2, base + 3); // F
        MarkerData md3 = new MarkerData(m, base + 3, base);  // B
        if (m.getLabelAnchor() != null) {
            // add vertices for the label anchor
            Point3D v4 = calcAnchorYX(m.getLabelAnchor(), v1, v2, yLength); // D
            Point3D v5 = calcAnchorYX(m.getLabelAnchor(), v3, v0, yLength); // B
            Point3D v6 = calcAnchorYZ(m.getLabelAnchor(), v0, v1, yLength); // E 
            Point3D v7 = calcAnchorYZ(m.getLabelAnchor(), v2, v3, yLength); // F
            box.addVertex(v4);
            box.addVertex(v5);
            box.addVertex(v6);
            box.addVertex(v7);
            // now write back the label vertex indices to the marker data
            md1.setLabelVertexIndex(base + 4);
            md3.setLabelVertexIndex(base + 5);
            md0.setLabelVertexIndex(base + 6);
            md2.setLabelVertexIndex(base + 7);
            result += 4;
        }
        this.faceE.addYMarker(md0);
        this.faceD.addYMarker(md1);
        this.faceF.addYMarker(md2);
        this.faceB.addYMarker(md3);
        return result;
    }

    /**
     * Adds the vertices required for an y-marker (RANGE type), creates the 
     * marker data records and adds them to the four faces that will be 
     * required to draw the marker.  If there is a label for the marker, this 
     * method will also add vertices to track the label anchor points. 
     * 
     * @param box  the chart box.
     * @param m  the marker data record.
     * @param startY  the starting y position for the marker.
     * @param endY  the ending y position for the marker.
     * @param base  the base vertex index.
     */
    private int addYRangeMarker(Object3D box, MarkerData m, double startY,
            double endY, int base) {
        int result = 8; // number of vertices added
        Point3D v0 = new Point3D(xOffset, startY, zOffset);
        Point3D v1 = new Point3D(xOffset, startY, zOffset + zLength);
        Point3D v2 = new Point3D(xOffset + xLength, startY,  zOffset + zLength);
        Point3D v3 = new Point3D(xOffset + xLength, startY, zOffset);
        Point3D v4 = new Point3D(xOffset, endY, zOffset);
        Point3D v5 = new Point3D(xOffset, endY, zOffset + zLength);
        Point3D v6 = new Point3D(xOffset + xLength, endY,  zOffset + zLength);
        Point3D v7 = new Point3D(xOffset + xLength, endY, zOffset);
        box.addVertex(v0);
        box.addVertex(v1);
        box.addVertex(v2);
        box.addVertex(v3);
        box.addVertex(v4);
        box.addVertex(v5);
        box.addVertex(v6);
        box.addVertex(v7);
        MarkerData md0 = new MarkerData(m, base, base + 1, base + 4, 
                base + 5); // E
        MarkerData md1 = new MarkerData(m, base + 1, base + 2, base + 5, 
                base + 6); // D 
        MarkerData md2 = new MarkerData(m, base + 2, base + 3, base + 6, 
                base + 7); // F
        MarkerData md3 = new MarkerData(m, base + 3, base, base + 7, 
                base + 4);  // B
        if (m.getLabelAnchor() != null) {
            // add vertices for the label anchor
            Point3D v8 = calcRangeAnchorYX(m.getLabelAnchor(), v1, v2, v5, v6);
            Point3D v9 = calcRangeAnchorYX(m.getLabelAnchor(), v3, v0, v7, v4);
            Point3D v10 = calcRangeAnchorYZ(m.getLabelAnchor(), v2, v3, v6, v7);
            Point3D v11 = calcRangeAnchorYZ(m.getLabelAnchor(), v0, v1, v4, v5);
            box.addVertex(v8);
            box.addVertex(v9);
            box.addVertex(v10);
            box.addVertex(v11);
            // now write back the label vertex indices to the marker data
            md1.setLabelVertexIndex(base + 8);
            md3.setLabelVertexIndex(base + 9);
            md2.setLabelVertexIndex(base + 10);
            md0.setLabelVertexIndex(base + 11);
            result += 4;
        }        
        this.faceE.addYMarker(md0);
        this.faceD.addYMarker(md1);
        this.faceF.addYMarker(md2);
        this.faceB.addYMarker(md3);
        return result;
    }

    /**
     * Adds the vertices required for an z-marker (VALUE type), creates the 
     * marker data records and adds them to the four faces that will be 
     * required to draw the marker.  If there is a label for the marker, this 
     * method will also add vertices to track the label anchor points. 
     * 
     * @param box  the chart box.
     * @param m  the marker data record.
     * @param z  the z position for the marker.
     * @param base  the base vertex index.
     */
    private int addZMarker(Object3D box, MarkerData m, double z, int base) {
        int result = 4; // number of vertices added
        Point3D v0 = new Point3D(xOffset, yOffset, z);
        Point3D v1 = new Point3D(xOffset + xLength, yOffset, z);
        Point3D v2 = new Point3D(xOffset + xLength, yOffset + yLength,  z);
        Point3D v3 = new Point3D(xOffset, yOffset + yLength, z);
        box.addVertex(v0);  // A
        box.addVertex(v1);
        box.addVertex(v2);
        box.addVertex(v3);
        MarkerData md0 = new MarkerData(m, base, base + 1); // A
        MarkerData md1 = new MarkerData(m, base + 1, base + 2); // F 
        MarkerData md2 = new MarkerData(m, base + 2, base + 3); // C
        MarkerData md3 = new MarkerData(m, base + 3, base);  // E
        if (m.getLabelAnchor() != null) {
            // add vertices for the label anchor
            Point3D v4 = calcAnchorZX(m.getLabelAnchor(), v0, v1, zLength); // A 
            Point3D v5 = calcAnchorZX(m.getLabelAnchor(), v2, v3, zLength); // C 
            Point3D v6 = calcAnchorZY(m.getLabelAnchor(), v1, v2, zLength); // F  
            Point3D v7 = calcAnchorZY(m.getLabelAnchor(), v3, v0, zLength); // E 
            box.addVertex(v4);
            box.addVertex(v5);
            box.addVertex(v6);
            box.addVertex(v7);
            // now write back the label vertex indices to the marker data
            md0.setLabelVertexIndex(base + 4);
            md2.setLabelVertexIndex(base + 5);
            md1.setLabelVertexIndex(base + 6);
            md3.setLabelVertexIndex(base + 7);
            result += 4;
        }
        this.faceA.addZMarker(md0);
        this.faceF.addZMarker(md1);
        this.faceC.addZMarker(md2);
        this.faceE.addZMarker(md3);
        return result;
    }

    /**
     * Adds the vertices required for an x-marker (RANGE type), creates the 
     * marker data records and adds them to the four faces that will be 
     * required to draw the marker.  If there is a label for the marker, this 
     * method will also add vertices to track the label anchor points. 
     * 
     * @param box  the chart box.
     * @param m  the marker data record.
     * @param startZ  the starting z position for the marker.
     * @param endZ  the ending z position for the marker.
     * @param base  the base vertex index.
     */
    private int addZRangeMarker(Object3D box, MarkerData m, double startZ, 
            double endZ, int base) {
        int result = 8;
        Point3D v0 = new Point3D(xOffset, yOffset, startZ);
        Point3D v1 = new Point3D(xOffset + xLength, yOffset, startZ);
        Point3D v2 = new Point3D(xOffset + xLength, yOffset + yLength,  startZ);
        Point3D v3 = new Point3D(xOffset, yOffset + yLength, startZ);
        Point3D v4 = new Point3D(xOffset, yOffset, endZ);
        Point3D v5 = new Point3D(xOffset + xLength, yOffset, endZ);
        Point3D v6 = new Point3D(xOffset + xLength, yOffset + yLength, endZ);
        Point3D v7 = new Point3D(xOffset, yOffset + yLength, endZ);
        box.addVertex(v0);  // A
        box.addVertex(v1);
        box.addVertex(v2);
        box.addVertex(v3);
        box.addVertex(v4);  // A
        box.addVertex(v5);
        box.addVertex(v6);
        box.addVertex(v7);
        MarkerData md0 = new MarkerData(m, base, base + 1, base + 4, 
                base + 5); // A
        MarkerData md1 = new MarkerData(m, base + 1, base + 2, base + 5, 
                base + 6); // F 
        MarkerData md2 = new MarkerData(m, base + 2, base + 3, base + 6, 
                base + 7); // C
        MarkerData md3 = new MarkerData(m, base + 3, base, base + 7, 
                base + 4);  // E
        if (m.getLabelAnchor() != null) {
            // add vertices for the label anchor
            Point3D v8 = calcRangeAnchorZX(m.getLabelAnchor(), v0, v1, v4, v5);
            Point3D v9 = calcRangeAnchorZX(m.getLabelAnchor(), v2, v3, v6, v7);
            Point3D v10 = calcRangeAnchorZY(m.getLabelAnchor(), v3, v0, v7, v4);
            Point3D v11 = calcRangeAnchorZY(m.getLabelAnchor(), v1, v2, v5, v6);
            box.addVertex(v8);
            box.addVertex(v9);
            box.addVertex(v10);
            box.addVertex(v11);
            // now write back the label vertex indices to the marker data
            md0.setLabelVertexIndex(base + 8);
            md2.setLabelVertexIndex(base + 9);
            md3.setLabelVertexIndex(base + 10);
            md1.setLabelVertexIndex(base + 11);
            result += 4;
        }        
        this.faceA.addZMarker(md0);
        this.faceF.addZMarker(md1);
        this.faceC.addZMarker(md2);
        this.faceE.addZMarker(md3);
        return result;
    }

    /**
     * Returns the horizontal offset for an anchor assuming that (a) the delta 
     * is expressed as a percentage, and (b) the length in the offset direction 
     * is {@code length}.
     * 
     * @param anchor  the anchor.
     * @param length  the length.
     * 
     * @return The offset. 
     */
    private double hoffset(Anchor2D anchor, double length) {
        double offset = 0.0;
        if (anchor.getRefPt().isLeft()) {
            offset = length * anchor.getOffset().getDX();
        } else if (anchor.getRefPt().isRight()) {
            offset = -length * anchor.getOffset().getDX();
        }
        return offset;
    }

    /**
     * Returns the vertical offset for an anchor assuming that (a) the delta is
     * expressed as a percentage, and (b) the length in the offset direction 
     * is {@code length}.
     * 
     * @param anchor  the anchor.
     * @param length  the length.
     * 
     * @return The offset. 
     */
    private double voffset(Anchor2D anchor, double length) {
        double offset = 0.0;
        if (anchor.getRefPt().isTop()) {
            offset = length * anchor.getOffset().getDY();
        } else if (anchor.getRefPt().isBottom()) {
            offset = -length * anchor.getOffset().getDY();
        }
        return offset;
    }
    
    /**
     * Returns the horizontal position along the line based on the anchor 
     * point.
     * 
     * @param anchor  the anchor ({@code null} not permitted).
     * @param start  the start value.
     * @param end  the end value.
     * 
     * @return The position. 
     */
    private double hpos(Anchor2D anchor, double start, double end) {
        if (anchor.getRefPt().isLeft()) {
            return start;
        } else if (anchor.getRefPt().isRight()) {
            return end;
        }
        return (start + end) / 2.0;
    }
    
    // anchor for x-marker label where line runs parallel to y-axis
    private Point3D calcAnchorXY(Anchor2D anchor, Point3D start, Point3D end, 
            double xLength) {
        double dx = hoffset(anchor, end.getY() - start.getY());
        double dy = voffset(anchor, xLength);
        double y = hpos(anchor, start.getY(), end.getY());
        return new Point3D(start.getX() + dy, y + dx, start.getZ());
    }
    
    // anchor for x-marker label where line runs parallel to z-axis
    private Point3D calcAnchorXZ(Anchor2D anchor, Point3D start, Point3D end, 
            double xLength) {
        double dx = hoffset(anchor, end.getZ() - start.getZ());
        double dy = voffset(anchor, xLength);
        double z = hpos(anchor, start.getZ(), end.getZ());
        return new Point3D(start.getX() + dy, start.getY(), z + dx);
    }
    
    // anchor for y-marker label where line runs parallel to x-axis
    private Point3D calcAnchorYX(Anchor2D anchor, Point3D start, Point3D end, 
            double yLength) {
        double dx = hoffset(anchor, end.getX() - start.getX());
        double dy = voffset(anchor, yLength);
        double x = hpos(anchor, start.getX(), end.getX());
        return new Point3D(x + dx, start.getY() + dy, start.getZ());
    }
    
    // anchor for y-marker label where line runs parallel to z-axis
    private Point3D calcAnchorYZ(Anchor2D anchor, Point3D start, Point3D end, 
            double yLength) {
        double dx = hoffset(anchor, end.getZ() - start.getZ());
        double dy = voffset(anchor, yLength);
        double z = hpos(anchor, start.getZ(), end.getZ());
        return new Point3D(start.getX(), start.getY() + dy, z + dx);
    }
    
    // anchor for z-marker label where line runs parallel to x-axis
    private Point3D calcAnchorZX(Anchor2D anchor, Point3D start, Point3D end, 
            double zLength) {
        double dx = hoffset(anchor, end.getX() - start.getX());
        double dy = voffset(anchor, zLength);
        double x = hpos(anchor, start.getX(), end.getX());
        return new Point3D(x + dx, start.getY(), start.getZ() + dy);
    }
    
    // anchor for z-marker label where line runs parallel to y-axis
    private Point3D calcAnchorZY(Anchor2D anchor, Point3D start, Point3D end, 
            double zLength) {
        double dx = hoffset(anchor, end.getY() - start.getY());
        double dy = voffset(anchor, zLength);
        double y = hpos(anchor, start.getY(), end.getY());
        return new Point3D(start.getX(), y + dx, start.getZ() + dy);
    }
 
    // anchor for x-marker label where band runs parallel to y-axis
    private Point3D calcRangeAnchorXY(Anchor2D anchor, Point3D start1, 
            Point3D end1, Point3D start2, Point3D end2) {
        Point2D p = anchor.resolveAnchorWithPercentOffset(start1.getY(), 
                start1.getX(), end2.getY(), end2.getX());
        return new Point3D(p.getY(), p.getX(), end1.getZ());
    }
    
    // anchor for x-marker label where line runs parallel to z-axis
    private Point3D calcRangeAnchorXZ(Anchor2D anchor, Point3D start1, 
            Point3D end1, Point3D start2, Point3D end2) {
        Point2D p = anchor.resolveAnchorWithPercentOffset(start1.getZ(), 
                start1.getX(), end2.getZ(), end2.getX());
        return new Point3D(p.getY(), end1.getY(), p.getX());
    }
    
//    // anchor for y-marker label where line runs parallel to x-axis
    private Point3D calcRangeAnchorYX(Anchor2D anchor, Point3D start1, 
            Point3D end1, Point3D start2, Point3D end2) {
        Point2D p = anchor.resolveAnchorWithPercentOffset(start1.getX(), 
                start1.getY(), end2.getX(), end2.getY());
        return new Point3D(p.getX(), p.getY(), end1.getZ());
    }
    
//    // anchor for y-marker label where line runs parallel to z-axis
    private Point3D calcRangeAnchorYZ(Anchor2D anchor, Point3D start1, 
            Point3D end1, Point3D start2, Point3D end2) {
        Point2D p = anchor.resolveAnchorWithPercentOffset(start1.getZ(), 
                start1.getY(), end2.getZ(), end2.getY());
        return new Point3D(start1.getX(), p.getY(), p.getX());
    }
    
    // anchor for z-marker label where line runs parallel to x-axis
    private Point3D calcRangeAnchorZX(Anchor2D anchor, Point3D start1, 
            Point3D end1, Point3D start2, Point3D end2) {
        Point2D p = anchor.resolveAnchorWithPercentOffset(start1.getX(), 
                start1.getZ(), end2.getX(), end2.getZ());
        return new Point3D(p.getX(), end1.getY(), p.getY());
    }
    
    // anchor for z-marker label where line runs parallel to y-axis
    private Point3D calcRangeAnchorZY(Anchor2D anchor, Point3D start1, 
            Point3D end1, Point3D start2, Point3D end2) {
        Point2D p = anchor.resolveAnchorWithPercentOffset(start1.getY(), 
                start1.getZ(), end2.getY(), end2.getZ());
        return new Point3D(end1.getX(), p.getX(), p.getY());
    }
    
    /**
     * A special subclass of {@link Face} that is used by the {@link ChartBox3D} 
     * so that when faces are sorted by z-order, the chart box sides are always 
     * drawn first (furthest in the background).  Also, these faces track 
     * tick marks, values and anchor points.
     */
    public static final class ChartBoxFace extends Face {

        /** Info about the x-axis ticks on edge A. */
        private List<TickData> xTicksA;
        
        /** Info about the x-axis ticks on edge B. */
        private List<TickData> xTicksB;
        
        /** Info about the y-axis ticks on edge A. */
        private List<TickData> yTicksA;
        
        /** Info about the y-axis ticks on edge B. */
        private List<TickData> yTicksB;
        
        /** Info about the z-axis ticks on edge A. */
        private List<TickData> zTicksA;
        
        /** Info about the z-axis ticks on edge B. */
        private List<TickData> zTicksB;
        
        private List<MarkerData> xMarkers;
        
        private List<MarkerData> yMarkers;
        
        private List<MarkerData> zMarkers;
        
        /**
         * Creates a new face for a {@link ChartBox3D}.
         * 
         * @param owner  the object that the new face belongs to ({@code null} 
         *     not permitted).
         * @param vertices  the indices of the vertices.
         * 
         * @since 1.3
         */
        public ChartBoxFace(Object3D owner, int[] vertices) {
            super(owner, vertices);
            this.xTicksA = new ArrayList<TickData>();
            this.xTicksB = new ArrayList<TickData>();
            this.yTicksA = new ArrayList<TickData>();
            this.yTicksB = new ArrayList<TickData>();
            this.zTicksA = new ArrayList<TickData>();
            this.zTicksB = new ArrayList<TickData>();
            this.xMarkers = new ArrayList<MarkerData>();
            this.yMarkers = new ArrayList<MarkerData>();
            this.zMarkers = new ArrayList<MarkerData>();
        }
        
        /**
         * Clears the ticks for the x-axis.
         */
        public void clearXTicks() {
            this.xTicksA.clear();
            this.xTicksB.clear();
        }
        
        /**
         * Returns the x-axis tick data for edge A.
         * 
         * @return The x-axis tick data for edge A.
         */
        public List<TickData> getXTicksA() {
            return this.xTicksA;
        }
        
        /**
         * Adds tick data for edges A and B.
         * 
         * @param a  data for a tick on edge A.
         * @param b  data for a tick on edge B.
         */
        public void addXTicks(TickData a, TickData b) {
            this.xTicksA.add(a);
            this.xTicksB.add(b);
        }
        
        /**
         * Returns the x-axis tick data for the edge B.
         * 
         * @return The x-axis tick data for the edge B. 
         */
        public List<TickData> getXTicksB() {
            return this.xTicksB;
        }
        
        /**
         * Adds tick data items for the y-axis.
         * 
         * @param a  data for a tick on edge A.
         * @param b  data for a tick on edge B.
         */
        public void addYTicks(TickData a, TickData b) {
            this.yTicksA.add(a);
            this.yTicksB.add(b);
        }
        
        /**
         * Returns the y-axis tick data for the edge A.
         * 
         * @return The y-axis tick data for the edge A.
         */
        public List<TickData> getYTicksA() {
            return this.yTicksA;
        }
        
        /**
         * Returns the y-axis tick data for the edge B.
         * 
         * @return The y-axis tick data for the edge B.
         */
        public List<TickData> getYTicksB() {
            return this.yTicksB;
        }

        /**
         * Adds tick data items for the z-axis.
         * 
         * @param a  data for a tick on edge A.
         * @param b  data for a tick on edge B.
         */
        public void addZTicks(TickData a, TickData b) {
            this.zTicksA.add(a);
            this.zTicksB.add(b);
        }
        
        /**
         * Returns the z-axis tick data for the edge A.
         * 
         * @return The z-axis tick data for the edge A.
         */
        public List<TickData> getZTicksA() {
            return this.zTicksA;
        }
        
        /**
         * Returns the z-axis tick data for the edge B.
         * 
         * @return The z-axis tick data for the edge B. 
         */
        public List<TickData> getZTicksB() {
            return this.zTicksB;
        }

        /**
         * Adds marker data for the x-dimension.
         * 
         * @param marker  the marker data ({@code null} not permitted). 
         */
        public void addXMarker(MarkerData marker) {
            ArgChecks.nullNotPermitted(marker, "marker");
            this.xMarkers.add(marker);
        }

        /**
         * Returns a list of marker data for the x-dimension (the list is not
         * modifiable).
         * 
         * @return The markers (never {@code null}). 
         */
        public List<MarkerData> getXMarkers() {
            return Collections.unmodifiableList(this.xMarkers);
        }
        
        /**
         * Adds marker data for the y-dimension.
         * 
         * @param marker  the marker data ({@code null} not permitted). 
         */
        public void addYMarker(MarkerData marker) {
            ArgChecks.nullNotPermitted(marker, "marker");
            this.yMarkers.add(marker);
        }
        
        /**
         * Returns a list of marker data for the y-dimension (the list is not
         * modifiable).
         * 
         * @return The markers (never {@code null}). 
         */
        public List<MarkerData> getYMarkers() {
            return Collections.unmodifiableList(this.yMarkers);
        }
        
        /**
         * Adds marker data for the z-dimension.
         * 
         * @param marker  the marker data ({@code null} not permitted). 
         */
        public void addZMarker(MarkerData marker) {
            ArgChecks.nullNotPermitted(marker, "marker");
            this.zMarkers.add(marker);
        }
        
        /**
         * Returns a list of marker data for the z-dimension (the list is not
         * modifiable).
         * 
         * @return The markers (never {@code null}). 
         */
        public List<MarkerData> getZMarkers() {
            return Collections.unmodifiableList(this.zMarkers);
        }
        
        /**
         * Returns {@code -123456f} which ensures that the chart box face 
         * is always drawn first (before any data items).
         * 
         * @param points  the points (ignored here).
         * 
         * @return {@code -123456f}. 
         */
        @Override
        public float calculateAverageZValue(Point3D[] points) {
            return -123456f;
        }
    }

}
