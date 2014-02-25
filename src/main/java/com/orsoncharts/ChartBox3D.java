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

package com.orsoncharts;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

import com.orsoncharts.graphics3d.Face;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.Point3D;
import com.orsoncharts.axis.TickData;
import com.orsoncharts.marker.MarkerData;
import com.orsoncharts.marker.MarkerDataType;
import com.orsoncharts.util.ArgChecks;

/**
 * A chart box is the container within which the chart elements are drawn.
 * The six faces of the box created so that they are visible only when they 
 * do not obscure the content of the chart (generally the back three faces 
 * will be visible and the front three faces will not be visible, although from
 * some angles four faces will be visible at one time).  There is also support 
 * provided for specifying gridlines on the visible faces.
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

    private CBFace faceA;
    private CBFace faceB;
    private CBFace faceC;
    private CBFace faceD;
    private CBFace faceE;
    private CBFace faceF;

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
     * @param color  the color for the sides of the box (<code>null</code>
     *     not permitted).
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
     * 
     * @return
     * 
     * @since 1.2
     */
    public List<TickData> getXTicks() {
        return this.xTicks;
    }
    
    /**
     * 
     * @param ticks
     * 
     * @since 1.2
     */
    public void setXTicks(List<TickData> ticks) {
        ArgChecks.nullNotPermitted(ticks, "ticks");
        this.xTicks = ticks;
    }
    
    /**
     * 
     * @return
     * 
     * @since 1.2
     */
    public List<TickData> getYTicks() {
        return this.yTicks;
    }
    
    /**
     * 
     * @param ticks
     * 
     * @since 1.2
     */
    public void setYTicks(List<TickData> ticks) {
        ArgChecks.nullNotPermitted(ticks, "ticks");
        this.yTicks = ticks;
    }
    
    /**
     * 
     * @return
     * 
     * @since 1.2
     */
    public List<TickData> getZTicks() {
        return this.zTicks;
    }
    
    /**
     * 
     * @param ticks
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
     *     never <code>null</code>).
     * 
     * @since 1.2
     */
    public List<MarkerData> getXMarkers() {
        return this.xMarkers;
    }
 
    /**
     * 
     * @param markers
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
     *     never <code>null</code>).
     * 
     * @since 1.2
     */
    public List<MarkerData> getYMarkers() {
        return this.yMarkers;
    }

    /**
     * 
     * @param markers
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
     *     never <code>null</code>).
     * 
     * @since 1.2
     */
    public List<MarkerData> getZMarkers() {
        return this.zMarkers;
    }

    /**
     * 
     * @param markers
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
    public CBFace faceA() {
        return this.faceA;
    }
  
    /**
     * Returns face B (the front face, in the XY axis plane).
     * 
     * @return Face B. 
     */
    public CBFace faceB() {
        return this.faceB;
    }
  
    /**
     * Returns face C (the top face, in the XZ axis plane).
     * 
     * @return Face C. 
     */
    public CBFace faceC() {
        return this.faceC;
    }
  
    /**
     * Returns face D (the rear face, in the XY axis plane).
     * 
     * @return Face D. 
     */
    public CBFace faceD() {
        return this.faceD;
    }

    /**
     * Returns face E (the left face, in the YZ axis plane).
     * 
     * @return Face E. 
     */
    public CBFace faceE() {
        return this.faceE;
    }
  
    /**
     * Returns face F (the right face, in the YZ axis plane).
     * 
     * @return Face F.
     */
    public CBFace faceF() {
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
        Object3D box = new Object3D();
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
                
        this.faceA = new CBFace(new int[] {0, 5, 6, 1}, this.color);  // XZ
        this.faceB = new CBFace(new int[] {0, 1, 2, 3}, this.color);  // XY
        this.faceC = new CBFace(new int[] {7, 4, 3, 2}, this.color);  // XZ
        this.faceD = new CBFace(new int[] {5, 4, 7, 6}, this.color);  // XY
        this.faceE = new CBFace(new int[] {0, 3, 4, 5}, this.color);  // YZ
        this.faceF = new CBFace(new int[] {6, 7, 2, 1}, this.color);  // YZ
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
                double xpos = this.xOffset + xLength * m.getValueLine().getPos();
                addXMarker(box, m, xpos, base);
                base += 4;
            } else if (m.getType().equals(MarkerDataType.RANGE)) {
                double startX = this.xOffset + xLength * m.getStartLine().getPos();
                double endX = this.xOffset + xLength * m.getEndLine().getPos();
                addXRangeMarker(box, m, startX, endX, base);
                base += 8;
            }
        }
        
        // add vertices for the y-markers
        for (MarkerData m : this.yMarkers) {
            if (m.getType().equals(MarkerDataType.VALUE)) {
                double ypos = this.yOffset + yLength * m.getValueLine().getPos();
                addYMarker(box, m, ypos, base);
                base += 4;
            } else if (m.getType().equals(MarkerDataType.RANGE)) {
                double startY = this.yOffset + yLength * m.getStartLine().getPos();
                double endY = this.yOffset + yLength * m.getEndLine().getPos();
                addYRangeMarker(box, m, startY, endY, base);
                base += 8;
            }
        }
        
        // add vertices for the z-markers
        for (MarkerData m : this.zMarkers) {
            if (m.getType().equals(MarkerDataType.VALUE)) {
                double zpos = this.zOffset + zLength * m.getValueLine().getPos();
                addZMarker(box, m, zpos, base);
                base += 4;
            } else if (m.getType().equals(MarkerDataType.RANGE)) {
                double startZ = this.zOffset + zLength * m.getStartLine().getPos();
                double endZ = this.zOffset + zLength * m.getEndLine().getPos();
                addZRangeMarker(box, m, startZ, endZ, base);
                base += 8;
            }            
        }
        
        return box;
    }

    private void addXMarker(Object3D box, MarkerData m, double x, int base) {
        box.addVertex(x, yOffset, zOffset);
        box.addVertex(x, yOffset, zOffset + zLength);
        box.addVertex(x, yOffset + yLength,  zOffset + zLength);
        box.addVertex(x, yOffset + yLength, zOffset);
        MarkerData md0 = new MarkerData(m, base, base + 1); // A
        MarkerData md1 = new MarkerData(m, base + 1, base + 2); // D 
        MarkerData md2 = new MarkerData(m, base + 2, base + 3); // C
        MarkerData md3 = new MarkerData(m, base + 3, base);  // B
        this.faceA.addXMarker(md0);
        this.faceD.addXMarker(md1);
        this.faceC.addXMarker(md2);
        this.faceB.addXMarker(md3);
    }
    
    private void addXRangeMarker(Object3D box, MarkerData m, double startX,
            double endX, int base) {
        box.addVertex(startX, yOffset, zOffset);
        box.addVertex(startX, yOffset, zOffset + zLength);
        box.addVertex(startX, yOffset + yLength,  zOffset + zLength);
        box.addVertex(startX, yOffset + yLength, zOffset);
        box.addVertex(endX, yOffset, zOffset);
        box.addVertex(endX, yOffset, zOffset + zLength);
        box.addVertex(endX, yOffset + yLength,  zOffset + zLength);
        box.addVertex(endX, yOffset + yLength, zOffset);
        MarkerData md0 = new MarkerData(m, base, base + 1, base + 4, base + 5); // A
        MarkerData md1 = new MarkerData(m, base + 1, base + 2, base + 5, base + 6); // D 
        MarkerData md2 = new MarkerData(m, base + 2, base + 3, base + 6, base + 7); // C
        MarkerData md3 = new MarkerData(m, base + 3, base, base + 7, base + 4);  // B
        this.faceA.addXMarker(md0);
        this.faceD.addXMarker(md1);
        this.faceC.addXMarker(md2);
        this.faceB.addXMarker(md3);        
    }
    
    private void addYMarker(Object3D box, MarkerData m, double y, int base) {
        box.addVertex(xOffset, y, zOffset);
        box.addVertex(xOffset, y, zOffset + zLength);
        box.addVertex(xOffset + xLength, y,  zOffset + zLength);
        box.addVertex(xOffset + xLength, y, zOffset);
        MarkerData md0 = new MarkerData(m, base, base + 1); // E
        MarkerData md1 = new MarkerData(m, base + 1, base + 2); // D 
        MarkerData md2 = new MarkerData(m, base + 2, base + 3); // F
        MarkerData md3 = new MarkerData(m, base + 3, base);  // B
        this.faceE.addYMarker(md0);
        this.faceD.addYMarker(md1);
        this.faceF.addYMarker(md2);
        this.faceB.addYMarker(md3);
    }

    private void addYRangeMarker(Object3D box, MarkerData m, double startY,
            double endY, int base) {
        box.addVertex(xOffset, startY, zOffset);
        box.addVertex(xOffset, startY, zOffset + zLength);
        box.addVertex(xOffset + xLength, startY,  zOffset + zLength);
        box.addVertex(xOffset + xLength, startY, zOffset);
        box.addVertex(xOffset, endY, zOffset);
        box.addVertex(xOffset, endY, zOffset + zLength);
        box.addVertex(xOffset + xLength, endY,  zOffset + zLength);
        box.addVertex(xOffset + xLength, endY, zOffset);
        MarkerData md0 = new MarkerData(m, base, base + 1, base + 4, base + 5); // E
        MarkerData md1 = new MarkerData(m, base + 1, base + 2, base + 5, base + 6); // D 
        MarkerData md2 = new MarkerData(m, base + 2, base + 3, base + 6, base + 7); // F
        MarkerData md3 = new MarkerData(m, base + 3, base, base + 7, base + 4);  // B
        this.faceE.addYMarker(md0);
        this.faceD.addYMarker(md1);
        this.faceF.addYMarker(md2);
        this.faceB.addYMarker(md3);        
    }

    private void addZMarker(Object3D box, MarkerData m, double z, int base) {
        box.addVertex(xOffset, yOffset, z);  // A
        box.addVertex(xOffset + xLength, yOffset, z);
        box.addVertex(xOffset + xLength, yOffset + yLength,  z);
        box.addVertex(xOffset, yOffset + yLength, z);
        MarkerData md0 = new MarkerData(m, base, base + 1); // A
        MarkerData md1 = new MarkerData(m, base + 1, base + 2); // F 
        MarkerData md2 = new MarkerData(m, base + 2, base + 3); // C
        MarkerData md3 = new MarkerData(m, base + 3, base);  // E
        this.faceA.addZMarker(md0);
        this.faceF.addZMarker(md1);
        this.faceC.addZMarker(md2);
        this.faceE.addZMarker(md3);
    }

    private void addZRangeMarker(Object3D box, MarkerData m, double startZ, 
            double endZ, int base) {
        box.addVertex(xOffset, yOffset, startZ);  // A
        box.addVertex(xOffset + xLength, yOffset, startZ);
        box.addVertex(xOffset + xLength, yOffset + yLength,  startZ);
        box.addVertex(xOffset, yOffset + yLength, startZ);
        box.addVertex(xOffset, yOffset, endZ);  // A
        box.addVertex(xOffset + xLength, yOffset, endZ);
        box.addVertex(xOffset + xLength, yOffset + yLength, endZ);
        box.addVertex(xOffset, yOffset + yLength, endZ);
        MarkerData md0 = new MarkerData(m, base, base + 1, base + 4, base + 5); // A
        MarkerData md1 = new MarkerData(m, base + 1, base + 2, base + 5, base + 6); // F 
        MarkerData md2 = new MarkerData(m, base + 2, base + 3, base + 6, base + 7); // C
        MarkerData md3 = new MarkerData(m, base + 3, base, base + 7, base + 4);  // E
        this.faceA.addZMarker(md0);
        this.faceF.addZMarker(md1);
        this.faceC.addZMarker(md2);
        this.faceE.addZMarker(md3);
    }

    /**
     * A special subclass of {@link Face} that is used by the {@link ChartBox3D} 
     * so that when faces are sorted by z-order, the chart box sides are always 
     * drawn first (furthest in the background).  Also, these faces track 
     * tick marks, values and anchor points.
     */
    public static final class CBFace extends Face {

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
         * @param vertices  the indices of the vertices.
         * @param color  the color (<code>null</code> not permitted).
         */
        public CBFace(int[] vertices, Color color) {
            super(vertices, color, false);
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
        
        public void addXMarker(MarkerData marker) {
            this.xMarkers.add(marker);
        }
        
        public List<MarkerData> getXMarkers() {
            return this.xMarkers;
        }
        
        public void addYMarker(MarkerData marker) {
            this.yMarkers.add(marker);
        }
        
        public List<MarkerData> getYMarkers() {
            return this.yMarkers;
        }
        
        public void addZMarker(MarkerData marker) {
            this.zMarkers.add(marker);
        }
        
        public List<MarkerData> getZMarkers() {
            return this.zMarkers;
        }
        
        /**
         * Returns <code>-123456f</code> which ensures that the chart box face 
         * is always drawn first (before any data items).
         * 
         * @param points  the points (ignored here).
         * 
         * @return <code>-123456f</code>. 
         */
        @Override
        public float calculateAverageZValue(Point3D[] points) {
            return -123456f;
        }
    }

}
