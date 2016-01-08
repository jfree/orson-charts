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
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import com.orsoncharts.util.ArgChecks;

/**
 * Represents a face in one {@link Object3D}, defined in terms of vertex
 * indices.  It is expected (but not enforced) that all the vertices for
 * the face lie within a single plane.  The face will be visible from the 
 * "front" side only, which is a function of the order in which the vertices
 * are specified.  A special subclass, {@link DoubleSidedFace}, is visible
 * from both front and back.
 */
public class Face {

    /** The object that the face belongs to. */
    private Object3D owner;
    
    /** The offset into the global list of vertices. */
    private int offset;

    /** 
     * The indices of the vertices representing this face.  Normally a face
     * should have at least three vertices (a triangle) but we allow a special
     * case with just two vertices to represent a line.
     */
    private int[] vertices;

    /**
     * Creates a new face with the specified vertices that is part of the 3D
     * {@code owner} object.  Most faces will have at least three vertices,
     * but a special case with just two vertices (representing a line) is
     * permitted.
     *
     * @param owner  the object that owns the face ({@code null} not 
     *     permitted).
     * @param vertices  the indices of the vertices (array length &gt;= 2).
     * 
     * @since 1.3
     */
    public Face(Object3D owner, int[] vertices) {
        if (vertices.length < 2) {
            throw new IllegalArgumentException(
                    "Faces must have at least two vertices.");
        }
        ArgChecks.nullNotPermitted(owner, "owner");
        this.owner = owner;
        this.vertices = vertices;
        this.offset = 0;
    }

    /**
     * Returns the object that this face belongs too (as passed to the 
     * constructor).
     * 
     * @return The owner (never {@code null}).
     * 
     * @since 1.3
     */
    public Object3D getOwner() {
        return this.owner;
    }
    
    /**
     * Returns the offset to add to the vertex indices.
     *
     * @return The offset.
     */
    public int getOffset() {
        return this.offset;
    }

    /**
     * Sets the offset to add to the vertex indices.
     *
     * @param offset  the offset.
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * Returns the number of vertices in this face.
     *
     * @return The number of vertices in this face.
     */
    public int getVertexCount() {
        return this.vertices.length;
    }

    /**
     * Returns the index for the specified vertex.
     *
     * @param i  the vertex index.
     *
     * @return  The index.
     */
    public int getVertexIndex(int i) {
        return this.vertices[i] + this.offset;
    }
    
    /**
     * A convenience method that looks up and returns the color for this face 
     * (obtained by querying the object that owns the face).  The color is 
     * not stored as an attribute of the face, because typically an object
     * has many faces that are all the same color.
     * 
     * @return The color (never {@code null}).
     */
    public Color getColor() {
        return this.owner.getColor(this);
    }
    
    /**
     * Returns {@code true} if an outline should be drawn for this face,
     * and {@code false} otherwise.  The value is obtained by querying
     * the object that owns the face.
     * 
     * @return A boolean. 
     */
    public boolean getOutline() {
        return this.owner.getOutline(this);
    }
    
    /**
     * Returns the tag for this face (always {@code null} for this class,
     * subclasses may override).  The {@link TaggedFace} class overrides
     * this method.
     * 
     * @return {@code null}.
     * 
     * @since 1.3
     */
    public String getTag() {
        return null;
    }
    
    /**
     * Calculates the normal vector for this face.
     *
     * @param points  the vertices of the object that this face belongs to
     *     (these can be in world or eye coordinates).
     *
     * @return The normal vector.
     */
    public double[] calculateNormal(Point3D[] points) {
        int iA = this.vertices[0] + this.offset;
        int iB = this.vertices[1] + this.offset;
        int iC = this.vertices[2] + this.offset;
        double aX = points[iA].x;
        double aY = points[iA].y;
        double aZ = points[iA].z;
        double bX = points[iB].x;
        double bY = points[iB].y;
        double bZ = points[iB].z;
        double cX = points[iC].x;
        double cY = points[iC].y;
        double cZ = points[iC].z;
        double u1 = bX - aX, u2 = bY - aY, u3 = bZ - aZ;
        double v1 = cX - aX, v2 = cY - aY, v3 = cZ - aZ;
        double a = u2 * v3 - u3 * v2,
               b = u3 * v1 - u1 * v3,
               c = u1 * v2 - u2 * v1,
               len = Math.sqrt(a * a + b * b + c * c);
               a /= len; b /= len; c /= len;
        return new double[] {a, b, c};
    }

    /**
     * Returns the average z-value.
     *
     * @param points  the points.
     *
     * @return The average z-value.
     */
    public float calculateAverageZValue(Point3D[] points) {
        float total = 0.0f;
        for (int i = 0; i < this.vertices.length; i++) {
            total = total + (float) points[this.vertices[i] + this.offset].z;
        }
        return total / this.vertices.length;
    }

    /**
     * Returns {@code true} if this face is front facing, and 
     * {@code false} otherwise.
     * 
     * @param projPts  the projection points.
     * 
     * @return A boolean. 
     */
    public boolean isFrontFacing(Point2D[] projPts) {
        return Utils2D.area2(projPts[getVertexIndex(0)], 
                projPts[getVertexIndex(1)], projPts[getVertexIndex(2)]) > 0;  
    }

    /**
     * Creates and returns a path for the outline of this face.
     * 
     * @param pts  the projected points for the world ({@code null} not 
     *     permitted).
     * 
     * @return A path.
     * 
     * @since 1.3
     */
    public Path2D createPath(Point2D[] pts) {
        Path2D path = new Path2D.Float();
        for (int v = 0; v < getVertexCount(); v++) {
            Point2D pt = pts[getVertexIndex(v)];
            if (v == 0) {
                path.moveTo(pt.getX(), pt.getY());
            } else {
                path.lineTo(pt.getX(), pt.getY());
            }
        }
        path.closePath();
        return path;
    }
    
    /**
     * Returns a string representation of this instance, primarily for
     * debugging purposes.
     * 
     * @return A string. 
     */
    @Override
    public String toString() {
        String result = "[";
        for (int i = 0; i < this.vertices.length; i++) {
            result = result + this.vertices[i];
            if (i < this.vertices.length - 1) {
                result = result + ", ";
            }
        }
        return result + "]";
    }
}
