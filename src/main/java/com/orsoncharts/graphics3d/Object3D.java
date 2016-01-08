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

package com.orsoncharts.graphics3d;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.orsoncharts.util.ArgChecks;

/**
 * An object defined in 3D space by (a) a list of coordinates, and (b) a list
 * of faces.  This class has methods to calculate projected points in 2D when
 * a {@link ViewPoint3D} is provided.
 * <br><br>
 * This class also contains a collection of static methods for constructing
 * common 3D objects.
 */
public class Object3D {

    /**
     * The key for storing the object class as an optional property for this 
     * object.
     * 
     * @since 1.4
     */
    public static final String CLASS_KEY = "class";
    
    /**
     * The key for storing item keys as property values.
     * 
     * @since 1.3
     */
    public static final String ITEM_KEY = "key";

    /** 
     * A prefix used for setting color properties for an object.
     * 
     * @since 1.3
     */
    public static final String COLOR_PREFIX = "color/";
    
    /** World coordinates. */
    private List<Point3D> vertices;

    /** Faces for the object, specified by indices to the world coords. */
    private List<Face> faces;
    
    /** The primary color for the object. */
    private Color color;
    
    /** 
     * A flag that indicates whether or not faces for this object have their
     * outlines drawn (that is, the shape is filled then drawn versus just 
     * filled only).
     */
    private boolean outline;
 
    /**
     * A map containing properties for the object.  If there are no properties
     * defined, then we leave this as {@code null} as an empty map would 
     * consume memory unnecessarily.
     */
    private Map<String, Object> properties;
    
    /**
     * Creates a new object, initially with no vertices or faces.
     * 
     * @param color  the default face color ({@code null} not permitted).
     * 
     * @since 1.3
     */
    public Object3D(Color color) {
        this(color, false);
    }
    
    /**
     * Creates a new object, initially with no vertices or faces.
     * 
     * @param color  the default face color ({@code null} not permitted).
     * @param outline  the default flag that determines whether face outlines
     *     are drawn.
     * 
     * @since 1.3
     */
    public Object3D(Color color, boolean outline) {
        ArgChecks.nullNotPermitted(color, "color");
        this.color = color;
        this.outline = outline;
        this.vertices = new java.util.ArrayList<Point3D>();
        this.faces = new java.util.ArrayList<Face>();
    }

    /**
     * Returns the default face color as specified in the constructor.
     * 
     * @return The color (never {@code null}).
     * 
     * @since 1.3
     */
    public Color getColor() {
        return this.color;
    }
    
    /**
     * Returns the outline flag.
     * 
     * @return The outline flag.
     * 
     * @since 1.3
     */
    public boolean getOutline() {
        return this.outline;
    }
    
    /**
     * Sets the outline flag.  This determines the default setting for whether
     * or not the faces of this object have their outlines drawn when rendered.
     * 
     * @param outline  the new flag value. 
     * 
     * @since 1.3
     */
    public void setOutline(boolean outline) {
        this.outline = outline;
    }
    
    /**
     * Returns the value of the property with the specified key, or 
     * {@code null} if there is no property defined for that key.
     * 
     * @param key  the property key ({@code null} not permitted).
     * 
     * @return The value (possibly {@code null}).
     * 
     * @since 1.3
     */
    public Object getProperty(String key) {
        ArgChecks.nullNotPermitted(key, "key");
        if (this.properties == null) {
            return null;
        } else {
            return this.properties.get(key);
        }
    }

    /**
     * Sets the value of a property, overwriting any existing value.  One 
     * application for this is storing item key references to link a 3D object
     * back to the data item that it represents (the key for this is
     * {@link Object3D#ITEM_KEY}).
     * 
     * @param key  the key ({@code null} not permitted).
     * @param value  the value ({@code null} permitted).
     * 
     * @since 1.3
     */
    public void setProperty(String key, Object value) {
        ArgChecks.nullNotPermitted(key, "key");
        if (this.properties == null) {
            this.properties = new HashMap<String, Object>();
        }
        this.properties.put(key, value);
    }
    
    /**
     * Returns the color for a specific face.  If the face has a tag, then
     * this method will look for a property with the key COLOR_PREFIX + tag
     * and return that color, otherwise it returns the default color for the
     * object.
     * 
     * @param face  the face ({@code null} not permitted).
     * 
     * @return The color for the specified face (never {@code null}).
     * 
     * @since 1.3
     */
    public Color getColor(Face face) {
        if (face.getTag() != null) {
            // see if there is a custom color defined for the tag
            Object obj = getProperty(COLOR_PREFIX + face.getTag());
            if (obj != null) {
                return (Color) obj;
            }
        }
        return this.color;
    }
    
    /**
     * Returns {@code true} if an outline should be drawn for the 
     * specified face, and {@code false} otherwise.
     * 
     * @param face  the face ({@code null} not permitted).
     * 
     * @return A boolean.
     * 
     * @since 1.3
     */
    public boolean getOutline(Face face) {
        return this.outline;
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
     * @param vertex  the vertex ({@code null} not permitted).
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
     * Adds a face for the given vertices (specified by index value).
     * 
     * @param vertices  the vertices (all should lie in a plane).
     * 
     * @since 1.3
     */
    public void addFace(int[] vertices) {
        // defer the arg checks...
        addFace(new Face(this, vertices));
    }
    
    /**
     * Adds a tagged face for the given vertices (specified by index value).
     * 
     * @param vertices  the vertices (all should lie in a plane).
     * @param tag  the tag ({@code null} not permitted).
     * 
     * @since 1.3
     */
    public void addFace(int[] vertices, String tag) {
        addFace(new TaggedFace(this, vertices, tag));
    }
    
    /**
     * Adds a double-sided face for the given vertices (specified by index 
     * value) and color.
     * 
     * @param vertices  the vertices (all should lie in a plane).
     * 
     * @since 1.3
     */
    public void addDoubleSidedFace(int[] vertices) {
        addFace(new DoubleSidedFace(this, vertices));
    }
    
    /**
     * Adds a face for this object.
     *
     * @param face  the face ({@code null} not permitted).
     */
    public void addFace(Face face) {
        ArgChecks.nullNotPermitted(face, "face");
        this.faces.add(face);
    }

    /**
     * Returns the faces for this object.  Note that the list returned is a 
     * direct reference to the internal storage for this {@code Object3D} 
     * instance, so callers should take care not to modify this list 
     * unintentionally.
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
     * @param viewPoint  the view point ({@code null} not permitted).
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
     * @param viewPoint  the view point ({@code null} not permitted).
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
     * @param color  the color ({@code null} not permitted).
     * @param invert  invert the order of the face
     * 
     * @return  The sheet.
     */
    public static Object3D createYSheet(double size, double x, double y, 
            double z, Color color, boolean invert) {
        ArgChecks.nullNotPermitted(color, "color");
        Object3D sheet = new Object3D(color);
        double delta = size / 2.0;
        sheet.addVertex(new Point3D(x + delta, y, z - delta));
        sheet.addVertex(new Point3D(x + delta, y, z + delta));
        sheet.addVertex(new Point3D(x - delta, y, z + delta));
        sheet.addVertex(new Point3D(x - delta, y, z - delta));
        if (invert) {
            sheet.addFace(new Face(sheet, new int[] {3, 2, 1, 0}));   
        } else {
            sheet.addFace(new Face(sheet, new int[] {0, 1, 2, 3}));
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
        Object3D sheet = new Object3D(color);
        double delta = size / 2.0;
        sheet.addVertex(new Point3D(x + delta, y - delta, z));
        sheet.addVertex(new Point3D(x + delta, y + delta, z));
        sheet.addVertex(new Point3D(x - delta, y + delta, z));
        sheet.addVertex(new Point3D(x - delta, y - delta, z));
        sheet.addFace(new Face(sheet, new int[] {0, 1, 2, 3}));
        return sheet;
    }

    /**  
     * Creates a cube centered on {@code (x, y, z)} with the specified 
     * {@code size}.
     *
     * @param size  the size.
     * @param x  the x-offset.
     * @param y  the y-offset.
     * @param z  the z-offset.
     * @param color  the color ({@code null} not permitted).
     *
     * @return The cube (never {@code null}).
     */
    public static Object3D createCube(double size, double x, 
            double y, double z, Color color) {
        return createBox(x, size, y, size, z, size, color);
    }

    /**  
     * Creates a box centered on {@code (x, y, z)} with the specified 
     * dimensions.  
     *
     * @param x  the x-coordinate.
     * @param xdim  the length of the box in the x-dimension.
     * @param y  the y-coordinate.
     * @param ydim  the length of the box in the y-dimension.
     * @param z  the z-coordinate.
     * @param zdim  the length of the box in the y-dimension.
     * @param color  the color ({@code null} not permitted).
     *
     * @return The box (never {@code null}).
     * 
     * @see #createCube(double, double, double, double, java.awt.Color) 
     */
    public static Object3D createBox(double x, double xdim, 
            double y, double ydim, double z, double zdim, 
            Color color) {
        ArgChecks.nullNotPermitted(color, "color");
        Object3D box = new Object3D(color);
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
        box.addFace(new Face(box, new int[] {4, 5, 1, 0}));
        box.addFace(new Face(box, new int[] {5, 6, 2, 1}));
        box.addFace(new Face(box, new int[] {6, 7, 3, 2}));
        box.addFace(new Face(box, new int[] {3, 7, 4, 0}));
        box.addFace(new Face(box, new int[] {7, 6, 5, 4}));
        box.addFace(new Face(box, new int[] {0, 1, 2, 3}));
        return box;
    }

    /**
     * Creates a tetrahedron.
     * 
     * @param size  the size.
     * @param xOffset  the x-offset.
     * @param yOffset  the y-offset.
     * @param zOffset  the z-offset.
     * @param color  the color ({@code null} not permitted).
     * 
     * @return A tetrahedron.
     */
    public static Object3D createTetrahedron(double size, double xOffset,
            double yOffset, double zOffset, Color color) {
        ArgChecks.nullNotPermitted(color, "color");
        Object3D tetra = new Object3D(color);
        tetra.addVertex(new Point3D(size + xOffset, -size + yOffset, 
                -size + zOffset));
        tetra.addVertex(new Point3D(-size + xOffset, size + yOffset, 
                -size + zOffset));
        tetra.addVertex(new Point3D(size + xOffset, size + yOffset, 
                size + zOffset));
        tetra.addVertex(new Point3D(-size + xOffset, -size + yOffset, 
                size + zOffset));
        tetra.addFace(new Face(tetra, new int[] {0, 1, 2}));
        tetra.addFace(new Face(tetra, new int[] {1, 3, 2}));
        tetra.addFace(new Face(tetra, new int[] {0, 3, 1}));
        tetra.addFace(new Face(tetra, new int[] {0, 2, 3}));
        return tetra;
    }

    /**
     * Creates an octahedron.
     * 
     * @param size  the size.
     * @param xOffset  the x-offset.
     * @param yOffset  the y-offset.
     * @param zOffset  the z-offset.
     * @param color  the color ({@code null} not permitted).
     * 
     * @return An octahedron.
     */
    public static Object3D createOctahedron(double size, double xOffset,
            double yOffset, double zOffset, Color color) {
        ArgChecks.nullNotPermitted(color, "color");
        Object3D octa = new Object3D(color);
        octa.addVertex(new Point3D(size + xOffset, 0 + yOffset, 0 + zOffset));
        octa.addVertex(new Point3D(0 + xOffset, size + yOffset, 0 + zOffset));
        octa.addVertex(new Point3D(-size + xOffset, 0 + yOffset, 0 + zOffset));
        octa.addVertex(new Point3D(0 + xOffset, -size + yOffset, 0 + zOffset));
        octa.addVertex(new Point3D(0 + xOffset, 0 + yOffset, -size + zOffset));
        octa.addVertex(new Point3D(0 + xOffset, 0 + yOffset, size + zOffset));

        octa.addFace(new Face(octa, new int[] {0, 1, 5}));
        octa.addFace(new Face(octa, new int[] {1, 2, 5}));
        octa.addFace(new Face(octa, new int[] {2, 3, 5}));
        octa.addFace(new Face(octa, new int[] {3, 0, 5}));
        octa.addFace(new Face(octa, new int[] {1, 0, 4}));
        octa.addFace(new Face(octa, new int[] {2, 1, 4}));
        octa.addFace(new Face(octa, new int[] {3, 2, 4}));
        octa.addFace(new Face(octa, new int[] {0, 3, 4}));
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
     * @param extColor  the exterior color ({@code null} not permitted).
     * @param intColor  the interior color ({@code null} not permitted).
     * 
     * @return A sphere. 
     */
    public static Object3D createSphere(double radius, int n,
            double x, double y, double z, Color extColor, Color intColor) {
        Object3D sphere = new Object3D(extColor);
        sphere.setProperty(COLOR_PREFIX + "interior", intColor);
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
                        Face f = new Face(sphere, new int[] {
                            (layer - 1) * n * 2 + i - 1, 
                            (layer - 1) * n * 2 + i, layer * n * 2 + i, 
                            layer * n * 2 + i - 1});
                        sphere.addFace(f);
                        f = new TaggedFace(sphere, new int[] {
                            layer * n * 2 + i - 1, layer * n * 2 + i, 
                            (layer - 1) * n * 2 + i, 
                            (layer - 1) * n * 2 + i - 1}, "interior");
                        sphere.addFace(f);
                    } else {
                        sphere.addFace(new Face(sphere, new int[] {
                            (layer - 1) * n * 2 + i - 1, (layer - 1) * n * 2, 
                            layer * n * 2, layer * n * 2 + i - 1}));
                        sphere.addFace(new TaggedFace(sphere, new int[] {
                            layer * n * 2 + i - 1, layer * n * 2, 
                            (layer - 1) * n * 2, (layer - 1) * n * 2 + i - 1}, 
                            "interior"));
                    }
                }
            }
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
     * @param color  the color ({@code null} not permitted).
     * 
     * @return  A pie segment object. 
     */
    public static Object3D createPieSegment(double radius, double explodeRadius, 
            double base, double height, double angle1, double angle2, 
            double inc, Color color) {
        ArgChecks.nullNotPermitted(color, "color");
        Object3D segment = new Object3D(color, true);
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
        segment.addFace(new Face(segment, new int[] {1, 3, 2, 0}));
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
            segment.addFace(new Face(segment, 
                    new int[] {vc - 2, vc - 4, vc - 3, vc - 1}));

            // top and bottom
            segment.addFace(new Face(segment, 
                    new int[] {0,  vc - 4, vc - 2, 0}));
            segment.addFace(new Face(segment, 
                    new int[] {1,  vc - 1, vc - 3, 1}));
            theta = theta + inc;
        }
        v0 = new Point3D(cx + radius * Math.cos(angle2), base, 
                cz + radius * Math.sin(angle2));
        v1 = new Point3D(cx + radius * Math.cos(angle2), base + height, 
                cz + radius * Math.sin(angle2));
        segment.addVertex(v0);
        segment.addVertex(v1);
        vc = vc + 2;
        segment.addFace(new Face(segment, 
                new int[] {vc - 2, vc - 4, vc - 3, vc - 1}));

        // top and bottom
        segment.addFace(new Face(segment, new int[] {0,  vc - 4, vc - 2, 0}));
        segment.addFace(new Face(segment, new int[] {1,  vc - 1, vc - 3, 1}));

        // closing side
        segment.addFace(new Face(segment, new int[] {1, 0, vc-2, vc-1}));
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
     * @param barColor  the color for the bar ({@code null} not permitted).
     * @param baseColor  the color for the base of the bar (if {@code null}, 
     *     the {@code color} is used instead).
     * @param topColor  the color for the top of the bar (if 
     *     {@code null}, the {@code color} is used instead).
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
        Object3D bar = new Object3D(barColor);
        if (c0 != null) {
            bar.setProperty(COLOR_PREFIX + "c0", c0);
        }
        if (c1 != null) {
            bar.setProperty(COLOR_PREFIX + "c1", c1);
        }
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

        bar.addFace(new Face(bar, new int[] {0, 1, 5, 4}));
        bar.addFace(new Face(bar, new int[] {4, 5, 1, 0}));
        bar.addFace(new Face(bar, new int[] {1, 2, 6, 5}));
        bar.addFace(new Face(bar, new int[] {5, 6, 2, 1}));
        bar.addFace(new Face(bar, new int[] {2, 3, 7, 6}));
        bar.addFace(new Face(bar, new int[] {6, 7, 3, 2}));
        bar.addFace(new Face(bar, new int[] {0, 4, 7, 3}));
        bar.addFace(new Face(bar, new int[] {3, 7, 4, 0}));
        bar.addFace(new Face(bar, new int[] {4, 5, 6, 7}));
        bar.addFace(new Face(bar, new int[] {3, 2, 1, 0}));
        if (c1 != null) {
            bar.addFace(new TaggedFace(bar, new int[] {7, 6, 5, 4}, "c1"));
        } else {
            bar.addFace(new Face(bar, new int[] {7, 6, 5, 4}));
        }
        if (c0 != null) {
            bar.addFace(new TaggedFace(bar, new int[] {0, 1, 2, 3}, "c0"));    
        } else {
            bar.addFace(new Face(bar, new int[] {0, 1, 2, 3}));                
        }
        
        return bar;      
    }
    
    /**
     * Creates a label object, which has a single transparent face in the 
     * Z-plane plus associated label attributes.  These faces are used to 
     * track the location and visibility of labels in a 3D scene.
     * 
     * @param label  the label ({@code null} not permitted).
     * @param font  the font ({@code null} not permitted).
     * @param fgColor  the label foreground color ({@code null} not permitted).
     * @param bgColor  the label background color ({@code null} not permitted).
     * @param x  the x-coordinate in 3D space.
     * @param y  the y-coordinate in 3D space.
     * @param z  the z-coordinate in 3D space.
     * @param reversed  reverse the order of the vertices?
     * @param doubleSided  is the face double-sided (visible from either side)?
     * 
     * @return A new label object (never {@code null}).
     * 
     * @since 1.3
     */
    public static Object3D createLabelObject(String label, Font font, 
            Color fgColor, Color bgColor, double x, double y, double z, 
            boolean reversed, boolean doubleSided) {
        Object3D labelObj = new Object3D(bgColor);
        labelObj.setProperty(Object3D.CLASS_KEY, "ItemLabel");
        labelObj.addVertex(x - 0.1, y, z);
        labelObj.addVertex(x + 0.1, y, z);
        labelObj.addVertex(x + 0.1, y + 0.1, z);
        labelObj.addVertex(x - 0.1, y + 0.1, z);
        
        if (!reversed || doubleSided) {
            labelObj.addFace(new LabelFace(labelObj, new int[] {0, 1, 2, 3}, 
                    label, font, fgColor, bgColor));
        }
        if (reversed || doubleSided) {
            labelObj.addFace(new LabelFace(labelObj, new int[] {3, 2, 1, 0}, 
                    label, font, fgColor, bgColor));
        }
        return labelObj;
    }

}
