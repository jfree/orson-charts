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

package com.orsoncharts.graphics3d;

import java.awt.Color;
import java.awt.geom.Point2D;

import com.orsoncharts.util.ArgChecks;

/**
 * Represents a face in one {@link Object3D}, defined in terms of vertex
 * indices.
 */
public class Face {

    /** The object that the face belongs to. */
    private Object3D owner;
    
    /** The offset into the global list of vertices. */
    private int offset;

    /** The indices of the vertices representing this face. */
    private int[] vertices;

    /**
     * Creates a new face.
     *
     * @param owner  the object that owns the face (<code>null</code> not 
     *     permitted).
     * @param vertices  the indices of the vertices.
     * 
     * @since 1.3
     */
    public Face(Object3D owner, int[] vertices) {
        if (vertices.length < 3) {
            throw new IllegalArgumentException("Faces must have at least 3 vertices.");
        }
        ArgChecks.nullNotPermitted(owner, "owner");
        this.owner = owner;
        this.vertices = vertices;
        this.offset = 0;
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
     * @return The color (never <code>null</code>).
     */
    public Color getColor() {
        return this.owner.getColor(this);
    }
    
    /**
     * Returns <code>true</code> if an outline should be drawn for this face,
     * and <code>false</code> otherwise.  The value is obtained by querying
     * the object that owns the face.
     * 
     * @return A boolean. 
     */
    public boolean getOutline() {
        return this.owner.getOutline(this);
    }
    
    /**
     * Returns the tag for this face (always <code>null</code> for this class,
     * subclasses may override).
     * 
     * @return <code>null</code>.
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
     * Returns <code>true</code> if this face is front facing, and 
     * <code>false</code> otherwise.
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
