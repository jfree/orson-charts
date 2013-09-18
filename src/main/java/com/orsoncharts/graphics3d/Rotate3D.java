package com.orsoncharts.graphics3d;

/**
 * Performs rotations about the x, y and z axes.
 */
public class Rotate3D {

    static double r11, r12, r13;
    static double r21, r22, r23;
    static double r31, r32, r33;
    static double r41, r42, r43;
    
    public static void initRotate(Point3D a, Point3D b, double alpha) {
        double v1 = b.x - a.x;
        double v2 = b.y - a.y;
        double v3 = b.z - a.z;
        double theta = Math.atan2(v2, v1);
        double phi = Math.atan2(Math.sqrt(v1 * v1 + v2 * v2), v3);
        initRotate(a, theta, phi, alpha);
    }

    public static void initRotate(Point3D a, double theta, double phi, 
            double alpha) {
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
    public static Point3D rotate(Point3D p) {
        return new Point3D(p.x * r11 + p.y * r21 + p.z * r31 + r41,
                p.x * r12 + p.y * r22 + p.z * r32 + r42,
                p.x * r13 + p.y * r23 + p.z * r33 + r43);
    }
}
