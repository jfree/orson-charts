/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.graphics3d;

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
     * @param theta
     * @param phi
     * @param rho
     */
    public ViewPoint3D(float theta, float phi, float rho) {
        this.theta = theta;
        this.phi = phi;
        this.rho = rho;
        updateMatrixElements();
    }

   /**
     * Returns theta, the angle of rot
     * @return 
     */
    public float getTheta() {
        return this.theta;
    }

    public void setTheta(float theta) {
        this.theta = theta;
        updateMatrixElements();
    }

    public float getRho() {
        return this.rho;
    }

    public void setRho(float rho) {
        this.rho = rho;
        updateMatrixElements();
    }

    public float getPhi() {
        return this.phi;
    }

    public void setPhi(float phi) {
        this.phi = phi;
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

    public String toString() {
        return "[theta=" + this.theta + ", phi=" + this.phi + ", rho=" 
                + this.rho + "]";
    }

}
