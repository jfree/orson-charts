/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.graphics3d;

import java.io.Serializable;

/**
 * Performs rotations about along an arbitrary axis (defined by two 
 * {@link Point3D} instances).  This file is derived from code published in 
 * "Computer Graphics for Java Programmers (Second Edition)" by Leen Ammeraal 
 * and Kang Zhang.
 */
public class Rotate3D implements Serializable {

    /** The first point defining the axis of rotation. */
    Point3D a;
    
    /** The second point defining the axis of rotation. */
    Point3D b;
    
    /** The angle of rotation in radians. */
    double angle;
    
    /** 
     * Values in the transformation matrix (these need to be recalculated if 
     * the axis of rotation or the angle are changed).
     */
    double r11, r12, r13;
    double r21, r22, r23;
    double r31, r32, r33;
    double r41, r42, r43;
    
    /**
     * Creates a new instance that will rotate points about the axis passing
     * through points a and b, by the specified angle.
     * 
     * @param a  the first point defining the axis of rotation 
     *     (<code>null</code> not permitted).
     * @param b  the second point defining the axis of rotation 
     *     (<code>null</code> not permitted).
     * @param angle  the rotation angle (in radians).
     */
    public Rotate3D(Point3D a, Point3D b, double angle) {
        this.a = a;
        this.b = b;
        this.angle = angle;
        double v1 = b.x - a.x;
        double v2 = b.y - a.y;
        double v3 = b.z - a.z;
        double theta = Math.atan2(v2, v1);
        double phi = Math.atan2(Math.sqrt(v1 * v1 + v2 * v2), v3);
        initRotate(a, theta, phi, angle);        
    }

    /**
     * Returns the angle of rotation, in radians.
     * 
     * @return The angle of rotation, in radians. 
     */
    public double getAngle() {
        return this.angle;
    }
    
    /**
     * Sets the angle of rotation (in radians) and (internally) updates the 
     * values of the transformation matrix ready for the next call(s) to 
     * the <code>applyRotation</code> methods.
     * 
     * @param angle  the angle (in radians). 
     */
    public void setAngle(double angle) {
        this.angle = angle;
        double v1 = b.x - a.x;
        double v2 = b.y - a.y;
        double v3 = b.z - a.z;
        double theta = Math.atan2(v2, v1);
        double phi = Math.atan2(Math.sqrt(v1 * v1 + v2 * v2), v3);
        initRotate(this.a, theta, phi, angle);
    }
    
    /**
     * Computes the transformation matrix values.
     * 
     * @param a  the start point of the axis of rotation.
     * @param theta  theta (defines direction of axis).
     * @param phi  phi (defines direction of axis).
     * @param alpha  alpha (angle of rotation).
     */
    private void initRotate(Point3D a, double theta, double phi, double alpha) {
        double cosAlpha = Math.cos(alpha);
        double sinAlpha = Math.sin(alpha);
        double cosPhi = Math.cos(phi);
        double cosPhi2 = cosPhi * cosPhi;
        double sinPhi = Math.sin(phi);
        double sinPhi2 = sinPhi * sinPhi;
        double cosTheta = Math.cos(theta);
        double cosTheta2 = cosTheta * cosTheta;
        double sinTheta = Math.sin(theta);
        double sinTheta2 = sinTheta * sinTheta;
        double a1 = a.x;
        double a2 = a.y;
        double a3 = a.z;
        double c = 1.0 - cosAlpha;
        this.r11 = cosTheta2 * (cosAlpha * cosPhi2 + sinPhi2) 
                + cosAlpha * sinTheta2;
        this.r12 = sinAlpha * cosPhi + c * sinPhi2 * cosTheta * sinTheta;
        this.r13 = sinPhi * (cosPhi * cosTheta * c - sinAlpha * sinTheta);
        this.r21 = sinPhi2 * cosTheta * sinTheta * c - sinAlpha * cosPhi;
        this.r22 = sinTheta2 * (cosAlpha * cosPhi2 + sinPhi2) 
                + cosAlpha * cosTheta2;
        this.r23 = sinPhi * (cosPhi * sinTheta * c + sinAlpha * cosTheta);
        this.r31 = sinPhi * (cosPhi * cosTheta * c + sinAlpha * sinTheta);
        this.r32 = sinPhi * (cosPhi * sinTheta * c - sinAlpha * cosTheta);
        this.r33 = cosAlpha * sinPhi2 + cosPhi2;
        this.r41 = a1 - a1 * this.r11 - a2 * this.r21 - a3 * this.r31;
        this.r42 = a2 - a1 * this.r12 - a2 * this.r22 - a3 * this.r32;
        this.r43 = a3 - a1 * this.r13 - a2 * this.r23 - a3 * this.r33;
    }
    
    /**
     * Creates and returns a new point that is the rotation of <code>p</code>
     * about the axis that was specified via the constructor.
     * 
     * @param p  the point (<code>null</code> not permitted).
     * 
     * @return A new point.
     */
    public Point3D applyRotation(Point3D p) {
        return applyRotation(p.x, p.y, p.z);
    }
    
    /**
     * Creates an returns a new point that is the rotation of the point 
     * <code>(x, y, z)</code> about the axis that was specified via the 
     * constructor.
     * 
     * @param x  the x-coordinate of the point to transform.
     * @param y  the y-coordinate of the point to transform.
     * @param z  the z-coordinate of the point to transform.
     * 
     * @return The transformed point (never <code>null</code>). 
     */
    public Point3D applyRotation(double x, double y, double z) {
        return new Point3D(
                x * this.r11 + y * this.r21 + z * this.r31 + this.r41,
                x * this.r12 + y * this.r22 + z * this.r32 + this.r42,
                x * this.r13 + y * this.r23 + z * this.r33 + this.r43);        
    }
    
    /**
     * Returns the coordinates of a point that is the rotation of the point
     * <code>(x, y, z)</code> about the axis that was specified via the 
     * constructor.
     * 
     * @param x  the x-coordinate of the point to transform.
     * @param y  the y-coordinate of the point to transform.
     * @param z  the z-coordinate of the point to transform.
     * @param result  an array to carry the result (<code>null</code> permitted).
     * 
     * @return The transformed coordinates (in the <code>result</code> array if 
     * one was supplied, otherwise in a newly allocated array). 
     */
    public double[] applyRotation(double x, double y, double z, 
            double[] result) {
        if (result == null) {
            result = new double[3];
        }
        result[0] = x * this.r11 + y * this.r21 + z * this.r31 + this.r41;
        result[1] = x * this.r12 + y * this.r22 + z * this.r32 + this.r42;
        result[2] = x * this.r13 + y * this.r23 + z * this.r33 + this.r43;
        return result;
    }
}
