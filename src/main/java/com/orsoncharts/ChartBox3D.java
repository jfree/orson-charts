/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts;

import java.awt.Color;

import com.orsoncharts.graphics3d.Face;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.Point3D;
import java.awt.BasicStroke;
import java.awt.Paint;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

/**
 * A chart box is the container within which the chart elements are drawn.
 * The six faces of the box created so that they are visible only when they 
 * do not obscure the content of the chart (generally the back three faces 
 * will be visible and the front three faces will not be visible).
 */
public class ChartBox3D {

    private double xLength, yLength, zLength;
    private double xOffset, yOffset, zOffset;

    private Color color;

    private CBFace faceA;
    private CBFace faceB;
    private CBFace faceC;
    private CBFace faceD;
    private CBFace faceE;
    private CBFace faceF;

    private Range xGridRange;
    private int xGridCount;
    private Paint xGridPaint;
    private Stroke xGridStroke;
    private Range yGridRange;
    private int yGridCount;
    private Paint yGridPaint;
    private Stroke yGridStroke;
    private Range zGridRange;
    private int zGridCount;
    private Paint zGridPaint;
    private Stroke zGridStroke;

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
     * @param color  the color for the sides of the box.
     */
    public ChartBox3D(double xLength, double yLength, double zLength, 
            double xOffset, double yOffset,
            double zOffset, Color color) {
        this.xLength = xLength;
        this.yLength = yLength;
        this.zLength = zLength;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;
        this.color = color;
        
        this.xGridRange = new Range(xOffset, xOffset + xLength);
        this.xGridCount = 5;
        this.xGridPaint = Color.WHITE;
        this.xGridStroke = new BasicStroke(0.2f, BasicStroke.CAP_ROUND, 
                BasicStroke.JOIN_ROUND, 1f, new float[] { 3f, 3f }, 0f);

        this.yGridRange = new Range(yOffset, yOffset + yLength);
        this.yGridCount = 5;
        this.yGridPaint = Color.WHITE;
        this.yGridStroke = new BasicStroke(0.2f, BasicStroke.CAP_ROUND, 
                BasicStroke.JOIN_ROUND, 1f, new float[] { 3f, 3f }, 0f);

        this.zGridRange = new Range(zOffset, zOffset + zLength);
        this.zGridCount = 5;
        this.zGridPaint = Color.WHITE;
        this.zGridStroke = new BasicStroke(0.2f, BasicStroke.CAP_ROUND, 
                BasicStroke.JOIN_ROUND, 1f, new float[] { 3f, 3f }, 0f);
    }

    /**
     * Returns the 3D object for the chart box.
     * 
     * @return The 3D object for the chart box. 
     */
    public Object3D getObject3D() {
        return createObject3D();
    }

    /**
     * Returns face A (the bottom face, in the XZ axis plane).
     * 
     * @return Face A. 
     */
    public Face faceA() {
        return this.faceA;
    }
  
    /**
     * Returns face B (the front face, in the XY axis plane).
     * 
     * @return Face B. 
     */
    public Face faceB() {
        return this.faceB;
    }
  
    /**
     * Returns face C (the top face, in the XZ axis plane).
     * 
     * @return Face C. 
     */
    public Face faceC() {
        return this.faceC;
    }
  
    /**
     * Returns face D (the rear face, in the XY axis plane).
     * 
     * @return Face D. 
     */
    public Face faceD() {
        return this.faceD;
    }

    /**
     * Returns face E (the left face, in the YZ axis plane).
     * 
     * @return Face E. 
     */
    public Face faceE() {
        return this.faceE;
    }
  
    /**
     * Returns face F (the right face, in the YZ axis plane).
     * 
     * @return Face F.
     */
    public Face faceF() {
        return this.faceF;
    }

    private Object3D createObject3D() {
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
                
        this.faceA = new CBFace(new int[] {0, 5, 6, 1}, color);  // XZ
        this.faceB = new CBFace(new int[] {0, 1, 2, 3}, color);  // XY
        this.faceC = new CBFace(new int[] {7, 4, 3, 2}, color);  // XZ
        this.faceD = new CBFace(new int[] {5, 4, 7, 6}, color);  // XY
        this.faceE = new CBFace(new int[] {0, 3, 4, 5}, color);  // YZ
        this.faceF = new CBFace(new int[] {6, 7, 2, 1}, color);  // YZ
        box.addFace(faceA);
        box.addFace(faceB);
        box.addFace(faceC);
        box.addFace(faceD);
        box.addFace(faceE);
        box.addFace(faceF);

        // add vertices for the x-grid lines (ABCD)
        int base = 8;
        for (int i = 0; i < this.xGridCount; i++) {
            double xx = this.xGridRange.gridPoint(i, this.xGridCount);
            box.addVertex(xx, yOffset, zOffset);
            box.addVertex(xx, yOffset, zOffset + zLength);
            box.addVertex(xx, yOffset + yLength,  zOffset + zLength);
            box.addVertex(xx, yOffset + yLength, zLength);
        }
        // add vertices for the y-grid lines (BDEF)
        base += this.xGridCount * 4;
        for (int i = 0; i < this.yGridCount; i++) {
            double yy = this.yGridRange.gridPoint(i, this.yGridCount);
            box.addVertex(xOffset, yy, zOffset);
            box.addVertex(xOffset + xLength, yy, zOffset);
            box.addVertex(xOffset + xLength, yy, zOffset + zLength);
            box.addVertex(xOffset, yy, zOffset + zLength);
            this.faceB.addGridLine(base + 4 * i, base + 4 * i + 1);
        }
        // add vertices for the z-grid lines (ACEF)
        for (int i = 0; i < this.zGridCount; i++) {
            double zz = this.zGridRange.gridPoint(i, this.zGridCount);
            box.addVertex(xOffset, yOffset, zz);
            box.addVertex(xOffset + xLength, yOffset, zz);
            box.addVertex(xOffset + xLength, yOffset + yLength, zz);
            box.addVertex(xLength, yOffset + yLength, zz);
        }
        
        return box;
    }

    /**
     * A special subclass of Face that is used by the ChartBox so that when
     * faces are sorted by z-order, the chart box sides are always drawn 
     * first (furthest in the background).
     */
    static class CBFace extends Face {
      
        List<int[]> gridLines;
        
        Paint gridLinePaint = Color.WHITE;
        
        Stroke gridLineStroke = new BasicStroke(0.2f, BasicStroke.CAP_ROUND, 
                BasicStroke.JOIN_ROUND, 1f, new float[] { 3f, 3f }, 0f);;
        
        public CBFace(int[] vertices, Color color) {
            super(vertices, color);
            this.gridLines = new ArrayList<int[]>();
        }
        
        public void addGridLine(int start, int end) {
            this.gridLines.add(new int[] {start, end});
        }
        
        public List<int[]> getGridLines() {
            return this.gridLines;
        }

        @Override
        public float calculateAverageZValue(Point3D[] points) {
            return -123456f;
        }
  }
}
