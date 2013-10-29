/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.graphics3d;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

/**
 * Specifies the location of the view point in 3D space.  Assumes the eye looks
 * towards the origin in world coordinates.
 */
public class ViewPoint3D {

    /** The rotation of the viewing point from the x-axis around the z-axis. */
    private float theta;

    /** The rotation (up and down) of the viewing point. */
    private float phi;

    /** The distance of the viewing point from the origin. */
    private float rho;

    /** Transformation matrix elements. */
    private float v11, v12, v13, v21, v22, v23, v32, v33, v43;
    
    /**
     * Creates a new viewing point.
     *
     * @param theta  the rotation of the viewing point from the x-axis around
     *     the z-axis (in radians)
     * @param phi  the rotation of the viewing point up and down (from the
     *     XZ plane, in radians)
     * @param rho  the distance of the viewing point from the origin.
     */
    public ViewPoint3D(float theta, float phi, float rho) {
        this.theta = theta;
        this.phi = phi;
        this.rho = rho;
        updateMatrixElements();
    }

   /**
     * Returns the angle of rotation from the x-axis about the z-axis, 
     * in radians.
     * 
     * @return Theta (in radians). 
     */
    public float getTheta() {
        return this.theta;
    }

    /**
     * Sets theta, the angle of rotation from the x-axis about the z-axis.
     * 
     * @param theta  the angle in radians. 
     */
    public void setTheta(float theta) {
        this.theta = theta;
        updateMatrixElements();
    }

    /**
     * Returns the angle of the viewing point down from the z-axis.
     * 
     * @return The angle of the viewing point down from the z-axis.
     *     (in radians).
     */
    public float getPhi() {
        return this.phi;
    }

    /**
     * Sets the angle of the viewing point up or down from the XZ plane.
     * 
     * @param phi  the angle (in radians). 
     */
    public void setPhi(float phi) {
        this.phi = phi;
        updateMatrixElements();
    }

    /**
     * Returns the distance of the viewing point from the origin.
     * 
     * @return The distance of the viewing point from the origin. 
     */
    public float getRho() {
        return this.rho;
    }

    /**
     * Sets the distance of the viewing point from the origin.
     * 
     * @param rho  the new distance. 
     */
    public void setRho(float rho) {
        this.rho = rho;
        updateMatrixElements();
    }

    /**
     * Converts a point in world coordinates to a point in eye coordinates.
     *
     * @param p  the point (<code>null</code> not permitted).
     *
     * @return The point in eye coordinates.
     */
    public Point3D worldToEye(Point3D p) {
        float x = this.v11 * p.x + this.v21 * p.y;
        float y = this.v12 * p.x + this.v22 * p.y + this.v32 * p.z;
        float z = this.v13 * p.x + this.v23 * p.y + this.v33 * p.z + this.v43;
        return new Point3D(x, y, z);
    }

    /**
     * Calculates and returns the screen coordinates for the specified point
     * in (world) 3D space.  
     *
     * @param p  the point.
     * @param d  the distance (explain this better).
     *
     * @return The screen coordinate.
     */
    public Point2D worldToScreen(Point3D p, float d) {
        float x = this.v11 * p.x + this.v21 * p.y;
        float y = this.v12 * p.x + this.v22 * p.y + this.v32 * p.z;
        float z = this.v13 * p.x + this.v23 * p.y + this.v33 * p.z + this.v43;
        return new Point2D.Float(-d * x / z, -d * y / z);
    }

    /**
     * Calculate the distance that would render a box of the given dimensions 
     * within a screen area of the specified size.
     * 
     * @param target  the target dimension (<code>null/<code> not permitted).
     * @param dim3D  the dimensions of the 3D content (<code>null</code> not 
     *     permitted).
     * 
     * @return The optimal viewing distance. 
     */
    public float optimalDistance(Dimension2D target, Dimension3D dim3D) {
        
        ViewPoint3D vp = new ViewPoint3D(this.theta, this.phi, this.rho);
        float near = (float) dim3D.getDiagonalLength();
        float far = (float) near * 40;
        
        World w = new World();
        double ww = dim3D.getWidth();
        double hh = dim3D.getHeight();
        double dd = dim3D.getDepth();
        w.add(Object3D.createBox(-ww / 2.0, ww, -hh / 2.0, hh, -dd / 2.0, dd, 
                Color.RED));
                
        while (true) {
            vp.setRho(near);
            Point2D[] nearpts = w.calculateProjectedPoints(vp, 1000f);
            Dimension neardim = Utils2D.findDimension(nearpts);
            double nearcover = coverage(neardim, target);
            vp.setRho(far);
            Point2D[] farpts = w.calculateProjectedPoints(vp, 1000f);
            Dimension fardim = Utils2D.findDimension(farpts);
            double farcover = coverage(fardim, target);
            if (nearcover <= 1.0) {
                return near;
            }
            if (farcover >= 1.0) {
                return far;
            }
            // bisect near and far until we get close enough to the specified 
            // dimension
            float mid = (near + far) / 2.0f;
            vp.setRho(mid);
            Point2D[] midpts = w.calculateProjectedPoints(vp, 1000f);
            Dimension middim = Utils2D.findDimension(midpts);
            double midcover = coverage(middim, target);
            if (midcover >= 1.0) {
                near = mid;
            } else {
                far = mid;
            }
        }  
    }
    
    private double coverage(Dimension2D d, Dimension2D target) {
        double wpercent = d.getWidth() / target.getWidth();
        double hpercent = d.getHeight() / target.getHeight();
        if (wpercent <= 1.0 && hpercent <= 1.0) {
            return Math.max(wpercent, hpercent);
        } else {
            if (wpercent >= 1.0) {
                if (hpercent >= 1.0) {
                    return Math.min(wpercent, hpercent);
                } else {
                    return wpercent;
                }
            } else {
                return hpercent;  // don't think it will matter
            }
        }
    }
    
    /**
     * Updates the matrix elements.
     */
    private void updateMatrixElements() {
        float cosTheta = (float) Math.cos(this.theta);
        float sinTheta = (float) Math.sin(this.theta);
        float cosPhi = (float) Math.cos(this.phi);
        float sinPhi = (float) Math.sin(this.phi);
        this.v11 = -sinTheta;
        this.v12 = -cosPhi * cosTheta;
        this.v13 = sinPhi * cosTheta;
        this.v21 = cosTheta;
        this.v22 = -cosPhi * sinTheta;
        this.v23 = sinPhi * sinTheta;
        this.v32 = sinPhi;
        this.v33 = cosPhi;
        this.v43 = -this.rho;
    }

    /**
     * Returns a string representation of this instance, primarily for 
     * debugging purposes.
     * 
     * @return A string. 
     */
    @Override
    public String toString() {
        return "[theta=" + this.theta + ", phi=" + this.phi + ", rho=" 
                + this.rho + "]";
    }

}
