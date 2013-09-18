/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts;

import java.awt.Color;
import com.orsoncharts.graphics3d.Face;
import com.orsoncharts.graphics3d.Object3D;
import com.orsoncharts.graphics3d.Point3D;

/**
 * A utility class to create and track a chart box.
 */
public class ChartBox3D {

  private double xLength, yLength, zLength;
  private double xOffset, yOffset, zOffset;
  private Color color;

  private Face faceA;
  private Face faceB;
  private Face faceC;
  private Face faceD;
  private Face faceE;
  private Face faceF;

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
  }

  /**
   * Returns the 3D object for the chart box.
   * 
   * @return The 3D object for the chart box. 
   */
  public Object3D getObject3D() {
    return createObject3D();
  }

  public Face faceA() {
    return this.faceA;
  }
  public Face faceB() {
    return this.faceB;
  }
  public Face faceC() {
    return this.faceC;
  }
  public Face faceD() {
    return this.faceD;
  }
  public Face faceE() {
    return this.faceE;
  }
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
    Point3D v7 = new Point3D(xLength + xOffset, yLength + yOffset, zLength + zOffset);

    box.addVertex(v0);   // 0, 0, 0
    box.addVertex(v1);   // 1, 0, 0
    box.addVertex(v2);   // 1, 1, 0
    box.addVertex(v3);   // 0, 1, 0

    box.addVertex(v4);   // 0, 1, 1
    box.addVertex(v5);   // 0, 0, 1
    box.addVertex(v6);   // 1, 0, 1
    box.addVertex(v7);   // 1, 1, 1
    faceA = new CBFace(new int[] {0, 5, 6, 1}, color);
    faceB = new CBFace(new int[] {0, 1, 2, 3}, color);
    faceC = new CBFace(new int[] {7, 4, 3, 2}, color);
    faceD = new CBFace(new int[] {5, 4, 7, 6}, color);
    faceE = new CBFace(new int[] {0, 3, 4, 5}, color);
    faceF = new CBFace(new int[] {6, 7, 2, 1}, color);
    box.addFace(faceA);
    box.addFace(faceB);
    box.addFace(faceC);
    box.addFace(faceD);
    box.addFace(faceE);
    box.addFace(faceF);
    return box;
  }

  static class CBFace extends Face {
      
    public CBFace(int[] vertices, Color color) {
      super(vertices, color);
    }

    @Override
    public float calculateAverageZValue(Point3D[] points) {
      return -123456f;
    }
      
  }
}
