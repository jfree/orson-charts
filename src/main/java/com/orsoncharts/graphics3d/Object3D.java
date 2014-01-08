/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of these source files is prohibited.
 * 
 */

package com.orsoncharts.graphics3d;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import com.orsoncharts.util.ArgChecks;

/**
 * An object defined in 3D space by (a) a list of coordinates, and (b) a list
 * of faces.  This class has methods to calculate projected points in 2D when
 * a {@link ViewPoint3D} is specified.
 * <br><br>
 * This class also contains a collection of static methods for constructing
 * common 3D objects.
 */
public class Object3D {

    /** World coordinates. */
    private List<Point3D> vertices;

    /** Faces for the object, specified by indices to the world coords. */
    private List<Face> faces;
 
    /**
     * Creates a new object, initially with no vertices or faces.
     */
    public Object3D() {
        this.vertices = new java.util.ArrayList<Point3D>();
        this.faces = new java.util.ArrayList<Face>();
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
     * Adds a new object vertex with the specified coordinates.
     * 
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param z  the z-coordinate.
     */
    public void addVertex(double x, double y, double z) {
        addVertex(new Point3D(x, y, z));    
    }
    
    /**
     * Adds a new object vertex.
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

    /**
     * Adds a face for the given vertices (specified by index value) and 
     * color.
     * 
     * @param vertices  the vertices (all should lie in a plane).
     * @param color  the color (<code>null</code> not permitted).
     * @param outline  draw the face outline.
     */
    public void addFace(int[] vertices, Color color, boolean outline) {
        // defer the arg checks...
        addFace(new Face(vertices, color, outline));
    }
    
    /**
     * Adds a double-sided face for the given vertices (specified by index 
     * value) and color.
     * 
     * @param vertices  the vertices (all should lie in a plane).
     * @param color  the color (<code>null</code> not permitted).
     * @param outline  draw face outlines.
     * 
     * @since 1.1
     */
    public void addDoubleSidedFace(int[] vertices, Color color, 
            boolean outline) {
        addFace(new DoubleSidedFace(vertices, color, outline));
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
     * Returns the faces.  Note that the list returned is a direct reference
     * to the internal storage for this <code>Object3D</code> instance, so
     * callers should take care not to modify this list unintentionally.
     *
     * @return The faces.
     */
    public List<Face> getFaces() {
        return this.faces;
    }

    /**
     * Calculates the projected points for the object's vertices, for the
     * given viewpoint.
     *
     * @param viewPoint  the view point (<code>null</code> not permitted).
     * @param d  the projection distance.
     *
     * @return The projected points.
     */
    public Point2D[] calculateProjectedPoints(ViewPoint3D viewPoint, double d) {
        ArgChecks.nullNotPermitted(viewPoint, "viewPoint");
        Point2D[] result = new Point2D[this.vertices.size()];
        int vertexCount = this.vertices.size();
        for (int i = 0; i < vertexCount; i++) {
            Point3D p = this.vertices.get(i);
            result[i] = viewPoint.worldToScreen(p, d);
        }
        return result;
    }

    /**
     * Returns the eye coordinates of the object's vertices.
     *
     * @param viewPoint  the view point (<code>null</code> not permitted).
     *
     * @return The eye coordinates.
     */
    public Point3D[] calculateEyeCoordinates(ViewPoint3D viewPoint) {
        ArgChecks.nullNotPermitted(viewPoint, "viewPoint");
        Point3D[] result = new Point3D[this.vertices.size()];
        int i = 0;
        for (Point3D vertex : this.vertices) {
            result[i] = viewPoint.worldToEye(vertex);
            i++;
        }
        return result;
    }
    
    /**
     * Creates a square flat surface in the x-z plane (constant y) with a 
     * single face.
     * 
     * @param size  the sheet size.
     * @param x  the x-coordinate for the center of the square.
     * @param y  the y-coordinate.
     * @param z  the z-coordinate for the center of the square.
     * @param color  the color (<code>null</code> not permitted).
     * @param invert  invert the order of the face
     * 
     * @return  The sheet.
     */
    public static Object3D createYSheet(double size, double x, double y, 
            double z, Color color, boolean invert) {
        ArgChecks.nullNotPermitted(color, "color");
        Object3D sheet = new Object3D();
        double delta = size / 2.0;
        sheet.addVertex(new Point3D(x + delta, y, z - delta));
        sheet.addVertex(new Point3D(x + delta, y, z + delta));
        sheet.addVertex(new Point3D(x - delta, y, z + delta));
        sheet.addVertex(new Point3D(x - delta, y, z - delta));
        if (invert) {
            sheet.addFace(new Face(new int[] {3, 2, 1, 0}, color, false));   
        } else {
            sheet.addFace(new Face(new int[] {0, 1, 2, 3}, color, false));
        }
        return sheet;
    }
  
    /**
     * Creates a square flat surface in the x-y plane (constant z).
     * 
     * @param size  the sheet size.
     * @param x  the x-coordinate of a point on the surface.
     * @param y  the y-coordinate of a point on the surface.
     * @param z  the z-coordinate of a point on the surface.
     * @param color  the color.
     * 
     * @return The sheet. 
     */
    public static Object3D createZSheet(double size, double x, double y, 
            double z, Color color) {
        Object3D sheet = new Object3D();
        double delta = size / 2.0;
        sheet.addVertex(new Point3D(x + delta, y - delta, z));
        sheet.addVertex(new Point3D(x + delta, y + delta, z));
        sheet.addVertex(new Point3D(x - delta, y + delta, z));
        sheet.addVertex(new Point3D(x - delta, y - delta, z));
        sheet.addFace(new Face(new int[] {0, 1, 2, 3}, color, false));
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
        return createBox(xOffset, size, yOffset, size, zOffset, size, color);
    }

    /**  
     * Creates a box.
     *
     * @param x  the x-coordinate.
     * @param xdim  the length of the box in the x-dimension.
     * @param y  the y-coordinate.
     * @param ydim  the length of the box in the y-dimension.
     * @param z  the z-coordinate.
     * @param zdim  the length of the box in the y-dimension.
     * @param color  the color.
     *
     * @return The box.
     */
    public static Object3D createBox(double x, double xdim, 
            double y, double ydim, double z, double zdim, 
            Color color) {
        Object3D box = new Object3D();
        double xdelta = xdim / 2.0;
        double ydelta = ydim / 2.0;
        double zdelta = zdim / 2.0;
        box.addVertex(new Point3D(x - xdelta, y - ydelta, z - zdelta));
        box.addVertex(new Point3D(x + xdelta, y - ydelta, z - zdelta));
        box.addVertex(new Point3D(x + xdelta, y - ydelta, z + zdelta));
        box.addVertex(new Point3D(x - xdelta, y - ydelta, z + zdelta));
        box.addVertex(new Point3D(x - xdelta, y + ydelta, z - zdelta));
        box.addVertex(new Point3D(x + xdelta, y + ydelta, z - zdelta));
        box.addVertex(new Point3D(x + xdelta, y + ydelta, z + zdelta));
        box.addVertex(new Point3D(x - xdelta, y + ydelta, z + zdelta));
        box.addFace(new Face(new int[] {4, 5, 1, 0}, color, false));
        box.addFace(new Face(new int[] {5, 6, 2, 1}, color, false));
        box.addFace(new Face(new int[] {6, 7, 3, 2}, color, false));
        box.addFace(new Face(new int[] {3, 7, 4, 0}, color, false));
        box.addFace(new Face(new int[] {7, 6, 5, 4}, color, false));
        box.addFace(new Face(new int[] {0, 1, 2, 3}, color, false));
        return box;
    }

    /**
     * Creates a tetrahedron.
     * 
     * @param size  the size.
     * @param xOffset  the x-offset.
     * @param yOffset  the y-offset.
     * @param zOffset  the z-offset.
     * @param color  the color (<code>null</code> not permitted).
     * 
     * @return A tetrahedron.
     */
    public static Object3D createTetrahedron(double size, double xOffset,
            double yOffset, double zOffset, Color color) {
        Object3D tetra = new Object3D();
        tetra.addVertex(new Point3D(size + xOffset, -size + yOffset, 
                -size + zOffset));
        tetra.addVertex(new Point3D(-size + xOffset, size + yOffset, 
                -size + zOffset));
        tetra.addVertex(new Point3D(size + xOffset, size + yOffset, 
                size + zOffset));
        tetra.addVertex(new Point3D(-size + xOffset, -size + yOffset, 
                size + zOffset));
        tetra.addFace(new Face(new int[] {0, 1, 2}, color, false));
        tetra.addFace(new Face(new int[] {1, 3, 2}, color, false));
        tetra.addFace(new Face(new int[] {0, 3, 1}, color, false));
        tetra.addFace(new Face(new int[] {0, 2, 3}, color, false));
        return tetra;
    }

    /**
     * Creates an octahedron.
     * 
     * @param size  the size.
     * @param xOffset  the x-offset.
     * @param yOffset  the y-offset.
     * @param zOffset  the z-offset.
     * @param color  the color (<code>null</code> not permitted).
     * 
     * @return An octahedron.
     */
    public static Object3D createOctahedron(double size, double xOffset,
            double yOffset, double zOffset, Color color) {
        Object3D octa = new Object3D();
        octa.addVertex(new Point3D(size + xOffset, 0 + yOffset, 0 + zOffset));
        octa.addVertex(new Point3D(0 + xOffset, size + yOffset, 0 + zOffset));
        octa.addVertex(new Point3D(-size + xOffset, 0 + yOffset, 0 + zOffset));
        octa.addVertex(new Point3D(0 + xOffset, -size + yOffset, 0 + zOffset));
        octa.addVertex(new Point3D(0 + xOffset, 0 + yOffset, -size + zOffset));
        octa.addVertex(new Point3D(0 + xOffset, 0 + yOffset, size + zOffset));

        octa.addFace(new Face(new int[] {0, 1, 5}, color, false));
        octa.addFace(new Face(new int[] {1, 2, 5}, color, false));
        octa.addFace(new Face(new int[] {2, 3, 5}, color, false));
        octa.addFace(new Face(new int[] {3, 0, 5}, color, false));
        octa.addFace(new Face(new int[] {1, 0, 4}, color, false));
        octa.addFace(new Face(new int[] {2, 1, 4}, color, false));
        octa.addFace(new Face(new int[] {3, 2, 4}, color, false));
        octa.addFace(new Face(new int[] {0, 3, 4}, color, false));
        return octa;
    }

    /**
     * Creates an approximation of a sphere.
     * 
     * @param radius  the radius of the sphere (in world units).
     * @param n  the number of layers.
     * @param x  the x-coordinate of the center of the sphere. 
     * @param y  the y-coordinate of the center of the sphere.
     * @param z  the z-coordinate of the center of the sphere.
     * @param extColor  the exterior color (<code>null</code> not permitted).
     * @param intColor  the interior color (<code>null</code> not permitted).
     * 
     * @return A sphere. 
     */
    public static Object3D createSphere(double radius, int n,
            double x, double y, double z, Color extColor, Color intColor) {
        Object3D sphere = new Object3D();
        double theta = Math.PI / n;
        Point3D[] prevLayer = new Point3D[n * 2 + 1];
        for (int i = 0; i <= n * 2; i++) {
            prevLayer[i] = new Point3D(x, y + radius, z);
            if (i != n * 2) {
                sphere.addVertex(prevLayer[i]);
            }
        }

        for (int layer = 1; layer < n; layer++) {
            Point3D[] currLayer = new Point3D[n * 2 + 1];
            for (int i = 0; i <= n * 2; i++) {
                double xx = radius * Math.cos(i * theta) 
                        * Math.sin(layer * theta);
                double yy = radius * Math.cos(layer * theta);
                double zz = radius * Math.sin(i * theta) 
                        * Math.sin(layer * theta);
                currLayer[i] = new Point3D(x + xx, y + yy, z + zz);
                if (i != n * 2) {
                    sphere.addVertex(currLayer[i]);
                }
                if (i > 0 && layer > 1) {
                    if (i != n * 2) {
                        Face f = new Face(new int[] {
                            (layer - 1) * n * 2 + i - 1, 
                            (layer - 1) * n * 2 + i, layer * n * 2 + i, 
                            layer * n * 2 + i - 1}, extColor, false);
                        sphere.addFace(f);
                        f = new Face(new int[] {layer * n * 2 + i - 1, 
                            layer * n * 2 + i, (layer - 1) * n * 2 + i, 
                            (layer - 1) * n * 2 + i - 1}, intColor, false);
                        sphere.addFace(f);
                    }
                    else {
                        sphere.addFace(new Face(new int[] {
                            (layer - 1) * n * 2 + i - 1, (layer - 1) * n * 2, 
                            layer * n * 2, layer * n * 2 + i - 1}, extColor, false));
                        sphere.addFace(new Face(new int[] {
                            layer * n * 2 + i - 1, layer * n * 2, 
                            (layer - 1) * n * 2, (layer - 1) * n * 2 + i - 1}, 
                                intColor, false));
                    }
                }
            }
            prevLayer = currLayer;
        }
        return sphere;
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
        float cx = (float) centre.x;
        float cz = (float) centre.z;
        segment.addVertex(new Point3D(cx + 0.0, base, cz + 0.0));
        segment.addVertex(new Point3D(cx + 0.0, base + height, cz + 0.0));
        Point3D v0 = new Point3D(cx + radius * Math.cos(angle1), base, 
                cz + radius * Math.sin(angle1));
        Point3D v1 = new Point3D(cx + radius * Math.cos(angle1), base + height, 
                cz + radius * Math.sin(angle1));
        segment.addVertex(v0);
        segment.addVertex(v1);
        segment.addFace(new Face(new int[] {1, 3, 2, 0}, color, false));
        int vc = 4; // vertex count
        double theta = angle1 + inc;
        while (theta < angle2) {
            Point3D v2 = new Point3D(cx + radius * Math.cos(theta), base, 
                    cz + radius * Math.sin(theta));
            Point3D v3 = new Point3D(cx + radius * Math.cos(theta), 
                    base + height, cz + radius * Math.sin(theta));
            segment.addVertex(v2);
            segment.addVertex(v3);
            vc = vc + 2;

            // outside edge
            segment.addFace(new Face(new int[] {vc - 2, vc - 4, vc - 3, vc - 1},
                    color, true));

            // top and bottom
            segment.addFace(new Face(new int[] {0,  vc - 4, vc - 2, 0}, color, 
                    true));
            segment.addFace(new Face(new int[] {1,  vc - 1, vc - 3, 1}, color, 
                    true));
            theta = theta + inc;
        }
        v0 = new Point3D(cx + radius * Math.cos(angle2), base, 
                cz + radius * Math.sin(angle2));
        v1 = new Point3D(cx + radius * Math.cos(angle2), base + height, 
                cz + radius * Math.sin(angle2));
        segment.addVertex(v0);
        segment.addVertex(v1);
        vc = vc + 2;
        segment.addFace(new Face(new int[] {vc - 2, vc - 4, vc - 3, vc - 1}, 
                color, true));

        // top and bottom
        segment.addFace(new Face(new int[] {0,  vc - 4, vc - 2, 0}, color, 
                true));
        segment.addFace(new Face(new int[] {1,  vc - 1, vc - 3, 1}, color, 
                true));

        // closing side
        segment.addFace(new Face(new int[] {1, 0, vc-2, vc-1}, color, false));
        return segment;
    }

    /**
     * Returns two 3D objects (sheets in the y-plane) that can be used as
     * alignment anchors for the labels of a pie segment.  One sheet is on the
     * front face of the segment, and the other is on the back face.  Depending
     * on the viewing point, only one of the sheets will be showing, and this
     * is the one that the pie segment label will be attached to.
     * 
     * @param radius  the pie segment radius (in world units).
     * @param explodeRadius  the pie segment explode radius (in world units).
     * @param base  the base of the pie segment.
     * @param height  the height of the pie segment.
     * @param angle1  the start angle of the segment (in radians).
     * @param angle2  the end angle of the segment (in radians).
     * 
     * @return A list containing the two 3D objects to be used as pie label
     *     markers.
     */
    public static List<Object3D> createPieLabelMarkers(double radius, 
            double explodeRadius, double base, double height, 
            double angle1, double angle2) {
        List<Object3D> result = new ArrayList<Object3D>();
        double angle = (angle1 + angle2) / 2.0;
        Point3D centre = new Point3D(explodeRadius * Math.cos(angle), 
                base, explodeRadius * Math.sin(angle));
        float cx = (float) centre.x;
        float cz = (float) centre.z;
        double r = radius * 0.9;
        Point3D v0 = new Point3D(cx + r * Math.cos(angle), base, 
                cz + r * Math.sin(angle));
        Point3D v1 = new Point3D(cx + r * Math.cos(angle), base + height, 
                cz + r * Math.sin(angle));
        result.add(Object3D.createYSheet(2.0, v0.x, v0.y, v0.z, Color.RED, 
                false));
        result.add(Object3D.createYSheet(2.0, v1.x, v1.y, v1.z, Color.BLUE, 
                true));
        return result;
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
     * @param barColor  the color for the bar (<code>null</code> not permitted).
     * @param baseColor  the color for the base of the bar (if 
     *     <code>null</code>, the <code>color</code> is used instead).
     * @param topColor  the color for the top of the bar (if 
     *     <code>null</code>, the <code>color</code> is used instead).
     * @param inverted  a flag that determines whether the baseColor and 
     *     topColor should be swapped in their usage.
     * 
     * @return A 3D object that can represent a bar in a bar chart. 
     */
    public static Object3D createBar(double xWidth, double zWidth, double x, 
            double y, double z, double zero, Color barColor, Color baseColor,
            Color topColor, boolean inverted) {
        ArgChecks.nullNotPermitted(barColor, "barColor");
        Color c0 = baseColor;
        Color c1 = topColor;
        if (inverted) {
            Color cc = c1;
            c1 = c0;
            c0 = cc;
        }
        if (c0 == null) {
            c0 = barColor;
        }
        if (c1 == null) {
            c1 = barColor;
        }
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

        bar.addFace(new Face(new int[] {0, 1, 5, 4}, barColor, false));
        bar.addFace(new Face(new int[] {4, 5, 1, 0}, barColor, false));
        bar.addFace(new Face(new int[] {1, 2, 6, 5}, barColor, false));
        bar.addFace(new Face(new int[] {5, 6, 2, 1}, barColor, false));
        bar.addFace(new Face(new int[] {2, 3, 7, 6}, barColor, false));
        bar.addFace(new Face(new int[] {6, 7, 3, 2}, barColor, false));
        bar.addFace(new Face(new int[] {0, 4, 7, 3}, barColor, false));
        bar.addFace(new Face(new int[] {3, 7, 4, 0}, barColor, false));
        bar.addFace(new Face(new int[] {4, 5, 6, 7}, barColor, false));
        bar.addFace(new Face(new int[] {3, 2, 1, 0}, barColor, false));
        bar.addFace(new Face(new int[] {7, 6, 5, 4}, c1, false));
        bar.addFace(new Face(new int[] {0, 1, 2, 3}, c0, false));
        return bar;      
    }

}
