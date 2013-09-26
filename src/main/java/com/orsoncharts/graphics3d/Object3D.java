/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.graphics3d;

import com.orsoncharts.ArgChecks;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * An object defined in 3D space by (a) a list of coordinates, and (b) a list
 * of faces.  This class has methods to calculate projected points in 2D when
 * a {@link ViewPoint3D} is specified.
 */
public class Object3D {

    /** World coordinates. */
    private List<Point3D> vertices;

    /** Faces for the object, specified by indices to the world coords. */
    private List<Face> faces;
 
    /**
     * Creates a square flat surface in the x-z plane (constant y).
     * 
     * @param dim  the sheet size.
     * @param xOffset
     * @param yOffset
     * @param zOffset
     * @param color
     * @return 
     */
    public static Object3D createYSheet(double dim, double xOffset, 
            double yOffset, double zOffset, Color color, boolean invert) {
        Object3D sheet = new Object3D();
        double half = dim / 2.0;
        sheet.addVertex(new Point3D(half + xOffset, yOffset, -half + zOffset));
        sheet.addVertex(new Point3D(half + xOffset, yOffset, half + zOffset));
        sheet.addVertex(new Point3D(-half + xOffset, yOffset, half + zOffset));
        sheet.addVertex(new Point3D(-half + xOffset, yOffset, -half + zOffset));
        if (invert) {
            sheet.addFace(new Face(new int[] {3, 2, 1, 0}, color));   
        } else {
            sheet.addFace(new Face(new int[] {0, 1, 2, 3}, color));
        }
        return sheet;
    }
  
    /**
     * Creates a square flat surface in the x-y plane (constant z).
     * 
     * @param dim  the sheet size.
     * @param xOffset
     * @param yOffset
     * @param zOffset
     * @param color
     * @return 
     */
    public static Object3D createZSheet(double dim, double xOffset, 
            double yOffset, double zOffset, Color color) {
        Object3D sheet = new Object3D();
        double half = dim / 2.0;
        sheet.addVertex(new Point3D(dim + xOffset, -dim + yOffset, zOffset));
        sheet.addVertex(new Point3D(dim + xOffset, dim + yOffset, zOffset));
        sheet.addVertex(new Point3D(-dim + xOffset, dim + yOffset, zOffset));
        sheet.addVertex(new Point3D(-dim + xOffset, -dim + yOffset, zOffset));
        sheet.addFace(new Face(new int[] {0, 1, 2, 3}, color));
        return sheet;
    }

    /**
     * A box where the sides are invisible when they are in the foreground.  This
     * is a typical "background" in 3D charts, so we call it a chart box.
     *
     * @param x
     * @param y
     * @param z
     * @param xOffset
     * @param yOffset
     * @param zOffset
     * @param color
     * @return
     */  
    public static Object3D createChartBox(int x, int y, int z, double xOffset, 
            double yOffset, double zOffset, Color color) {
        Object3D sheet = new Object3D();
        Point3D v0 = new Point3D(xOffset, yOffset, zOffset);
        Point3D v1 = new Point3D(x + xOffset, yOffset, zOffset);
        Point3D v2 = new Point3D(x + xOffset, y + yOffset, zOffset);
        Point3D v3 = new Point3D(xOffset, y + yOffset, zOffset);
        Point3D v4 = new Point3D(xOffset, y + yOffset, z + zOffset);
        Point3D v5 = new Point3D(xOffset, yOffset, z + zOffset);
        Point3D v6 = new Point3D(x + xOffset, yOffset, z + zOffset);
        Point3D v7 = new Point3D(x + xOffset, y + yOffset, z + zOffset);

        sheet.addVertex(v0);   // 0, 0, 0
        sheet.addVertex(v1);   // 1, 0, 0
        sheet.addVertex(v2);   // 1, 1, 0
        sheet.addVertex(v3);   // 0, 1, 0

        sheet.addVertex(v4);   // 0, 1, 1
        sheet.addVertex(v5);   // 0, 0, 1
        sheet.addVertex(v6);   // 1, 0, 1
        sheet.addVertex(v7);   // 1, 1, 1
        Face fA = new Face(new int[] {0, 5, 6, 1}, color);
        Face fB = new Face(new int[] {0, 1, 2, 3}, color);
        Face fC = new Face(new int[] {7, 4, 3, 2}, color);
        Face fD = new Face(new int[] {5, 4, 7, 6}, color);
        Face fE = new Face(new int[] {0, 3, 4, 5}, color);
        Face fF = new Face(new int[] {6, 7, 2, 1}, color);
        sheet.addFace(fA);
        sheet.addFace(fB);
        sheet.addFace(fC);
        sheet.addFace(fD);
        sheet.addFace(fE);
        sheet.addFace(fF);
        return sheet;
    }

    /**  
     * Creates a cube.
     *
     * @param size  the size.
     * @param xOffset  the x-offset.
     * @param yOffset  the y-offset.
     * @param zOffset  the z-offset.
     * @param color  the color.
     *
     * @return The object.
     */
    public static Object3D createCube(double size, double xOffset, 
            double yOffset, double zOffset, Color color) {
        Object3D cube = new Object3D();
        cube.addVertex(new Point3D(size + xOffset, -size + yOffset, -size
                + zOffset));
        cube.addVertex(new Point3D(size + xOffset, size + yOffset, -size
                + zOffset));
        cube.addVertex(new Point3D(-size + xOffset, size + yOffset, -size
                + zOffset));
        cube.addVertex(new Point3D(-size + xOffset, -size + yOffset, -size
                + zOffset));
        cube.addVertex(new Point3D(size + xOffset, -size + yOffset, size
                + zOffset));
        cube.addVertex(new Point3D(size + xOffset, size + yOffset, size
                + zOffset));
        cube.addVertex(new Point3D(-size + xOffset, size + yOffset, size
                + zOffset));
        cube.addVertex(new Point3D(-size + xOffset, -size + yOffset, size
                + zOffset));
        cube.addFace(new Face(new int[] {0, 1, 5, 4}, color));
        cube.addFace(new Face(new int[] {1, 2, 6, 5}, color));
        cube.addFace(new Face(new int[] {2, 3, 7, 6}, color));
        cube.addFace(new Face(new int[] {0, 4, 7, 3}, color));
        cube.addFace(new Face(new int[] {4, 5, 6, 7}, color));
        cube.addFace(new Face(new int[] {3, 2, 1, 0}, color));
        cube.addFace(new Face(new int[] {3, 7, 4, 0}, color));
        cube.addFace(new Face(new int[] {7, 6, 5, 4}, color));
        cube.addFace(new Face(new int[] {0, 1, 2, 3}, color));
        return cube;
    }

    /**
     * Creates a bar with the specified dimensions and color.
     * 
     * @param xWidth  the x-width of the bar.
     * @param zWidth  the z-width (or depth) of the bar.
     * @param x  the x-coordinate for the center of the bar.
     * @param y  the y-coordinate for the top of the bar.
     * @param z  the z-coordinate for the center of the bar.
     * @param zero  the y-coordinate for the bottom of the bar.
     * @param color  the color for the bar.
     * 
     * @return A 3D object that can represent a bar in a bar chart. 
     */
    public static Object3D createBar(double xWidth, double zWidth, double x, 
            double y, double z, double zero, Color color) {
        ArgChecks.nullNotPermitted(color, "color");
        Object3D bar = new Object3D();
        double xdelta = xWidth / 2.0;
        double zdelta = zWidth / 2.0;
        bar.addVertex(new Point3D(x - xdelta, zero, z - zdelta));
        bar.addVertex(new Point3D(x + xdelta, zero, z - zdelta));
        bar.addVertex(new Point3D(x + xdelta, zero, z + zdelta));
        bar.addVertex(new Point3D(x - xdelta, zero, z + zdelta));
        bar.addVertex(new Point3D(x - xdelta, y, z - zdelta));
        bar.addVertex(new Point3D(x + xdelta, y, z - zdelta));
        bar.addVertex(new Point3D(x + xdelta, y, z + zdelta));
        bar.addVertex(new Point3D(x - xdelta, y, z + zdelta));

        bar.addFace(new Face(new int[] {0, 1, 5, 4}, color));
        bar.addFace(new Face(new int[] {4, 5, 1, 0}, color));
        bar.addFace(new Face(new int[] {1, 2, 6, 5}, color));
        bar.addFace(new Face(new int[] {5, 6, 2, 1}, color));
        bar.addFace(new Face(new int[] {2, 3, 7, 6}, color));
        bar.addFace(new Face(new int[] {6, 7, 3, 2}, color));
        bar.addFace(new Face(new int[] {0, 4, 7, 3}, color));
        bar.addFace(new Face(new int[] {3, 7, 4, 0}, color));
        bar.addFace(new Face(new int[] {4, 5, 6, 7}, color));
        bar.addFace(new Face(new int[] {3, 2, 1, 0}, color));
        bar.addFace(new Face(new int[] {7, 6, 5, 4}, color));
        bar.addFace(new Face(new int[] {0, 1, 2, 3}, color));
        return bar;      
    }

    public static Object3D createTetrahedron(double size, double xOffset,
            double yOffset, double zOffset, Color color) {
        Object3D tetra = new Object3D();
        tetra.addVertex(new Point3D(size + xOffset, -size + yOffset, -size + zOffset));
        tetra.addVertex(new Point3D(-size + xOffset, size + yOffset, -size + zOffset));
        tetra.addVertex(new Point3D(size + xOffset, size + yOffset, size + zOffset));
        tetra.addVertex(new Point3D(-size + xOffset, -size + yOffset, size + zOffset));
        tetra.addFace(new Face(new int[] {0, 1, 2}, color));
        tetra.addFace(new Face(new int[] {1, 3, 2}, color));
        tetra.addFace(new Face(new int[] {0, 3, 1}, color));
        tetra.addFace(new Face(new int[] {0, 2, 3}, color));
        return tetra;
    }

    public static Object3D createOctahedron(double size, double xOffset,
            double yOffset, double zOffset, Color color) {
        Object3D octa = new Object3D();
        octa.addVertex(new Point3D(size + xOffset, 0 + yOffset, 0 + zOffset));
        octa.addVertex(new Point3D(0 + xOffset, size + yOffset, 0 + zOffset));
        octa.addVertex(new Point3D(-size + xOffset, 0 + yOffset, 0 + zOffset));
        octa.addVertex(new Point3D(0 + xOffset, -size + yOffset, 0 + zOffset));
        octa.addVertex(new Point3D(0 + xOffset, 0 + yOffset, -size + zOffset));
        octa.addVertex(new Point3D(0 + xOffset, 0 + yOffset, size + zOffset));

        octa.addFace(new Face(new int[] {0, 1, 5}, color));
        octa.addFace(new Face(new int[] {1, 2, 5}, color));
        octa.addFace(new Face(new int[] {2, 3, 5}, color));
        octa.addFace(new Face(new int[] {3, 0, 5}, color));
        octa.addFace(new Face(new int[] {1, 0, 4}, color));
        octa.addFace(new Face(new int[] {2, 1, 4}, color));
        octa.addFace(new Face(new int[] {3, 2, 4}, color));
        octa.addFace(new Face(new int[] {0, 3, 4}, color));
        return octa;
    }

    /**
     * Creates a pie segment with the specified attributes.
     * 
     * @param radius  the radius.
     * @param explodeRadius  the explode radius (0.0 if not exploded).
     * @param base  the base.
     * @param height  the height.
     * @param angle1  the start angle (radians).
     * @param angle2  the end angle (radians).
     * @param inc  the increment.
     * @param color  the color.
     * 
     * @return  A pie segment object. 
     */
    public static Object3D createPieSegment(double radius, double explodeRadius, 
            double base, double height, double angle1, double angle2, 
            double inc, Color color) {
        Object3D segment = new Object3D();
        double angleCentre = (angle1 + angle2) / 2.0;
        Point3D centre = new Point3D(explodeRadius * Math.cos(angleCentre), 
                base, explodeRadius * Math.sin(angleCentre));
        float cx = centre.x;
        float cz = centre.z;
        segment.addVertex(new Point3D(cx + 0.0, base, cz + 0.0));
        segment.addVertex(new Point3D(cx + 0.0, base + height, cz + 0.0));
        Point3D v0 = new Point3D(cx + radius * Math.cos(angle1), base, cz + radius * Math.sin(angle1));
        Point3D v1 = new Point3D(cx + radius * Math.cos(angle1), base + height, cz + radius * Math.sin(angle1));
        segment.addVertex(v0);
        segment.addVertex(v1);
        segment.addFace(new Face(new int[] {1, 3, 2, 0}, color));
        //segment.addFace(new Face(new int[] {0, 2, 3, 1}, color));
        int vc = 4; // vertex count
        double theta = angle1 + inc;
        while (theta < angle2) {
            Point3D v2 = new Point3D(cx + radius * Math.cos(theta), base, cz + radius * Math.sin(theta));
            Point3D v3 = new Point3D(cx + radius * Math.cos(theta), base + height, cz + radius * Math.sin(theta));
            segment.addVertex(v2);
            segment.addVertex(v3);
            vc = vc + 2;

            // outside edge
            segment.addFace(new Face(new int[] {vc - 2, vc - 4, vc - 3, vc - 1}, color));

            // top and bottom
            segment.addFace(new Face(new int[] {0,  vc - 4, vc - 2, 0}, color));
            segment.addFace(new Face(new int[] {1,  vc - 1, vc - 3, 1}, color));
            theta = theta + inc;
        }
        v0 = new Point3D(cx + radius * Math.cos(angle2), base, cz + radius * Math.sin(angle2));
        v1 = new Point3D(cx + radius * Math.cos(angle2), base + height, cz + radius * Math.sin(angle2));
        segment.addVertex(v0);
        segment.addVertex(v1);
        vc = vc + 2;
        segment.addFace(new Face(new int[] {vc - 2, vc - 4, vc - 3, vc - 1}, color));

        // top and bottom
        segment.addFace(new Face(new int[] {0,  vc - 4, vc - 2, 0}, color));
        segment.addFace(new Face(new int[] {1,  vc - 1, vc - 3, 1}, color));

        // closing side
        segment.addFace(new Face(new int[] {1, 0, vc-2, vc-1}, color));

        double j = angle1;
        return segment;
    }

    public static List<Object3D> createPieLabelMarkers(double radius, 
            double explodeRadius, double base, double height, 
            double angle1, double angle2) {
        List<Object3D> result = new ArrayList<Object3D>();
        double angleCentre = (angle1 + angle2) / 2.0;
        Point3D centre = new Point3D(explodeRadius * Math.cos(angleCentre), 
                base, explodeRadius * Math.sin(angleCentre));
        float cx = centre.x;
        float cz = centre.z;
        double angle = (angle1 + angle2) / 2.0;
        double r = radius * 0.9;
        Point3D v0 = new Point3D(cx + r * Math.cos(angle), base, cz + r * Math.sin(angle));
        Point3D v1 = new Point3D(cx + r * Math.cos(angle), base + height, cz + r * Math.sin(angle));
        result.add(Object3D.createYSheet(6.0, v0.x, v0.y, v0.z, Color.RED, false));
        result.add(Object3D.createYSheet(6.0, v1.x, v1.y, v1.z, Color.BLUE, true));
        return result;
    }

    public static List<Object3D> createPieSegmentMarkers(double radius, 
            double explodeRadius, double base, double height, 
            double angle1, double angle2) {
        List<Object3D> result = new ArrayList<Object3D>();
        double angleCentre = (angle1 + angle2) / 2.0;
        Point3D centre = new Point3D(explodeRadius * Math.cos(angleCentre), 
                base, explodeRadius * Math.sin(angleCentre));
        float cx = centre.x;
        float cz = centre.z;
        double angle = (angle1 + angle2) / 2.0;
        double r = radius * 0.9;
        Point3D v0 = new Point3D(cx + r * Math.cos(angle), base, cz + r * Math.sin(angle));
        Point3D v1 = new Point3D(cx + r * Math.cos(angle), base + height, cz + r * Math.sin(angle));
        result.add(new Dot3D(v0.x, v0.y, v0.z, Color.RED));
        result.add(new Dot3D(v1.x, v1.y, v1.z, Color.BLUE));
        return result;
    }

    public static Object3D createSphere(double size, int n,
            double xOffset, double yOffset, double zOffset, Color extColor, Color intColor) {
        Object3D sphere = new Object3D();
        double theta = Math.PI / n;
        Point3D[] prevLayer = new Point3D[n * 2 + 1];
        for (int i = 0; i <= n * 2; i++) {
            prevLayer[i] = new Point3D(0 + xOffset, size + yOffset, 0 + zOffset);
            if (i != n * 2) {
                sphere.addVertex(prevLayer[i]);
            }
        }

        for (int layer = 1; layer < n; layer++) {
            Point3D[] currLayer = new Point3D[n * 2 + 1];
            for (int i = 0; i <= n * 2; i++) {
                double xx = size * Math.cos(i * theta) * Math.sin(layer * theta);
                double yy = size * Math.cos(layer * theta);
                double zz = size * Math.sin(i * theta) * Math.sin(layer * theta);
                currLayer[i] = new Point3D(xx + xOffset, yy + yOffset, zz + zOffset);
                if (i != n * 2) {
                    sphere.addVertex(currLayer[i]);
                }
                if (i > 0 && layer > 1) {
                    if (i != n * 2) {
                        Face f = new Face(new int[] {(layer - 1) * n * 2 + i - 1, (layer - 1) * n * 2 + i, layer * n * 2 + i, layer * n * 2 + i - 1}, extColor);
                        sphere.addFace(f);
                        //System.out.println(f);
                        f = new Face(new int[] {layer * n * 2 + i - 1, layer * n * 2 + i, (layer - 1) * n * 2 + i, (layer - 1) * n * 2 + i - 1}, intColor);
                        //System.out.println(">" + f);
                        sphere.addFace(f);
                    }
                    else {
                        sphere.addFace(new Face(new int[] {(layer - 1) * n * 2 + i - 1, (layer - 1) * n * 2, layer * n * 2, layer * n * 2 + i - 1}, extColor));
                        sphere.addFace(new Face(new int[] {layer * n * 2 + i - 1, layer * n * 2, (layer - 1) * n * 2, (layer - 1) * n * 2 + i - 1}, intColor));
                    }
                }
            }
            prevLayer = currLayer;

        }
        return sphere;
    }

    /**
     * Creates a new object, initially with no vertices or faces.
     */
    public Object3D() {
        this.vertices = new java.util.ArrayList();
        this.faces = new java.util.ArrayList();
    }

    /**
     * Returns the number of vertices for this object.
     *
     * @return The number of vertices.
     */
    public int getVertexCount() {
        return this.vertices.size();
    }

    /**
     * Adds a new vertex for the object.
     * 
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param z  the z-coordinate.
     */
    public void addVertex(double x, double y, double z) {
        addVertex(new Point3D(x, y, z));    
    }
    
    /**
     * Adds a new vertex for the object.
     *
     * @param vertex  the vertex (<code>null</code> not permitted).
     */
    public void addVertex(Point3D vertex) {
        ArgChecks.nullNotPermitted(vertex, "vertex");
        this.vertices.add(vertex);
    }

    /**
     * Returns the number of faces.
     *
     * @return The number of faces.
     */
    public int getFaceCount() {
        return this.faces.size();
    }

    public void addFace(int[] vertices, Color color) {
        addFace(new Face(vertices, color));
    }
    
    /**
     * Adds a face.
     *
     * @param face  the face (<code>null</code> not permitted).
     */
    public void addFace(Face face) {
        ArgChecks.nullNotPermitted(face, "face");
        this.faces.add(face);
    }

  /**
   * Returns the faces.
   *
   * @return The faces.
   */
  public List<Face> getFaces() {
    return this.faces;
  }

  /**
   * Calculates the projected points for the object.  This is only useful
   * for a wire-frame model with no hidden line removal.
   *
   * @param viewPoint
   * @param d
   *
   * @return The projected points.
   */
  public Point2D[] calculateProjectedPoints(ViewPoint3D viewPoint, float d) {
    Point2D[] result = new Point2D[this.vertices.size()];
    int vertexCount = this.vertices.size();
    for (int i = 0; i < vertexCount; i++) {
      Point3D p = (Point3D) this.vertices.get(i);
      result[i] = viewPoint.worldToScreen(p, d);
    }
    return result;
  }

  /**
   * Returns the eye coordinates.
   *
   * @param viewPoint  the view point.
   *
   * @return The eye coordinates.
   */
  public Point3D[] calculateEyeCoordinates(ViewPoint3D viewPoint) {
    Point3D[] result = new Point3D[this.vertices.size()];
    int i = 0;
    for (Point3D vertex : this.vertices) {
      result[i] = viewPoint.worldToEye(vertex);
      i++;
    }
    return result;
  }

}
