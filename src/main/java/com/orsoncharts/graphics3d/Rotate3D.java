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
 * Performs rotations about the x, y and z axes.  This file is derived from
 * code published in "Computer Graphics for Java Programmers (Second Edition)" 
 * by Leen Ammeraal and Kang Zhang.
 */
public class Rotate3D implements Serializable {

    Point3D a;
    
    Point3D b;
    
    double angle;
    
    double r11, r12, r13;
    double r21, r22, r23;
    double r31, r32, r33;
    double r41, r42, r43;
    
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

    public double getAngle() {
        return this.angle;
    }
    
    public void setAngle(double angle) {
        this.angle = angle;
        double v1 = b.x - a.x;
        double v2 = b.y - a.y;
        double v3 = b.z - a.z;
        double theta = Math.atan2(v2, v1);
        double phi = Math.atan2(Math.sqrt(v1 * v1 + v2 * v2), v3);
        initRotate(a, theta, phi, angle);                
    }
    
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
        r11 = cosTheta2 * (cosAlpha * cosPhi2 + sinPhi2) + cosAlpha * sinTheta2;
        r12 = sinAlpha * cosPhi + c * sinPhi2 * cosTheta * sinTheta;
        r13 = sinPhi * (cosPhi * cosTheta * c - sinAlpha * sinTheta);
        r21 = sinPhi2 * cosTheta * sinTheta * c - sinAlpha * cosPhi;
        r22 = sinTheta2 * (cosAlpha * cosPhi2 + sinPhi2) + cosAlpha * cosTheta2;
        r23 = sinPhi * (cosPhi * sinTheta * c + sinAlpha * cosTheta);
        r31 = sinPhi * (cosPhi * cosTheta * c + sinAlpha * sinTheta);
        r32 = sinPhi * (cosPhi * sinTheta * c - sinAlpha * cosTheta);
        r33 = cosAlpha * sinPhi2 + cosPhi2;
        r41 = a1 - a1 * r11 - a2 * r21 - a3 * r31;
        r42 = a2 - a1 * r12 - a2 * r22 - a3 * r32;
        r43 = a3 - a1 * r13 - a2 * r23 - a3 * r33;
    }
    
    /**
     * Creates and returns a new point that is the rotation of <code>p</code>.
     * 
     * @param p  the point.
     * 
     * @return A new point.
     */
    public Point3D applyRotation(Point3D p) {
        return applyRotation(p.x, p.y, p.z);
    }
    
    public Point3D applyRotation(double x, double y, double z) {
        return new Point3D(x * r11 + y * r21 + z * r31 + r41,
                x * r12 + y * r22 + z * r32 + r42,
                x * r13 + y * r23 + z * r33 + r43);        
    }
    
    public double[] applyRotation(double x, double y, double z, 
            double[] result) {
        if (result == null) {
            result = new double[3];
        }
        result[0] = x * r11 + y * r21 + z * r31 + r41;
        result[1] = x * r12 + y * r22 + z * r32 + r42;
        result[2] = x * r13 + y * r23 + z * r33 + r43;
        return result;
    }
}
