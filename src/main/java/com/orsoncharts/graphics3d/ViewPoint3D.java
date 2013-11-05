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
import java.io.Serializable;

/**
 * Specifies the location of the view point in 3D space.  Assumes the eye looks
 * towards the origin in world coordinates.
 */
public class ViewPoint3D implements Serializable {

    /** The rotation of the viewing point from the x-axis around the z-axis. */
    private double theta;

    /** The rotation (up and down) of the viewing point. */
    private double phi;

    /** The distance of the viewing point from the origin. */
    private double rho;

    /** Transformation matrix elements. */
    private double v11, v12, v13, v21, v22, v23, v32, v33, v43;
    
    /** 
     * A point 1/4 turn "upwards" on the sphere, to define the camera
     * orientation.  
     */
    private Point3D up; 
    
    private Rotate3D rotation;
    
    /** A workspace for calling the Rotate3D class. */
    private double[] workspace;
    
    /**
     * Creates a new viewing point.
     *
     * @param theta  the rotation of the viewing point from the x-axis around
     *     the z-axis (in radians)
     * @param phi  the rotation of the viewing point up and down (from the
     *     XZ plane, in radians)
     * @param rho  the distance of the viewing point from the origin.
     */
    public ViewPoint3D(double theta, double phi, double rho, double angle) {
        this.theta = theta;
        this.phi = phi;
        this.rho = rho;
        updateMatrixElements();
        this.rotation = new Rotate3D( Point3D.ORIGIN, Point3D.UNIT_Z, 
                angle);
        this.up = this.rotation.applyRotation(Point3D.createPoint3D(this.theta, 
                this.phi - Math.PI / 2, this.rho));
        this.workspace = new double[3];
    }
    
    public ViewPoint3D(Point3D p, double angle) {
        // FIXME : there is a special case when x and y are both 0
        // how do we handle that?
        this.rho = (float) Math.sqrt(p.x * p.x + p.y * p.y + p.z * p.z);
        if (Math.sqrt(p.x * p.x + p.y * p.y) > 0.000001) {
            this.theta = (float) Math.atan2(p.y, p.x);
        }
        this.phi = (float) Math.acos(p.z / this.rho);
        updateMatrixElements();
        this.rotation = new Rotate3D( Point3D.ORIGIN, Point3D.UNIT_Z, 
                angle);
        this.up = this.rotation.applyRotation(Point3D.createPoint3D(this.theta, 
                this.phi - Math.PI / 2, this.rho));
        this.workspace = new double[3];
    }
    
    /**
     * Returns the x-coordinate of the viewing point.
     * 
     * @return The x-coordinate of the viewing point.
     */
    public final double getX() {
        return this.rho * Math.sin(this.phi) * Math.cos(this.theta);
    }
    
    /**
     * Returns the y-coordinate of the viewing point.
     * 
     * @return The y-coordinate of the viewing point.
     */
    public final double getY() {
        return this.rho * Math.sin(this.phi) * Math.sin(this.theta);
    }
    
    /**
     * Returns the z-coordinate of the viewing point.
     * 
     * @return The z-coordinate of the viewing point.
     */
    public final double getZ() {
        return this.rho * Math.cos(this.phi);
    }
    
    /**
     * Returns the viewing point (creates a new instance each time). 
     * 
     * @return 
     */
    public Point3D getPoint() {
        return new Point3D(getX(), getY(), getZ());
    }
    
    /**
     * Returns the angle of the orientation of the viewing point.
     * 
     * @return The angle.
     */
    public double getAngle() {
        // get the normal of the plane between the viewPoint, origin and upPoint
        Point3D vp = getPoint();
        Point3D n1 = Utils2D.normal(vp, this.up, Point3D.ORIGIN);
        // get the normal of the viewPt, UNIT_Z and ORIGIN
        Point3D screenup = Point3D.createPoint3D(this.theta, 
               this.phi - (Math.PI / 2), this.rho); //Utils2D.normal(vp, Point3D.UNIT_Z, Point3D.ORIGIN);
        Point3D n2 = Utils2D.normal(vp, screenup, Point3D.ORIGIN);
        // return the angle between them
        double angle = Utils2D.angle(n1, n2);
        if (Utils2D.cross(n1, screenup) >= 0.0) {
            return angle;
        } else {
          return -angle;
        }    
    }

   /**
     * Returns the angle of rotation from the x-axis about the z-axis, 
     * in radians.
     * 
     * @return Theta (in radians). 
     */
    public final double getTheta() {
        return this.theta;
    }

//    /**
//     * Sets theta, the angle of rotation from the x-axis about the z-axis.
//     * 
//     * @param theta  the angle in radians. 
//     */
//    public void setTheta(float theta) {
//        this.theta = theta;
//        updateMatrixElements();
//    }

    /**
     * Returns the angle of the viewing point down from the z-axis.
     * 
     * @return The angle of the viewing point down from the z-axis.
     *     (in radians).
     */
    public final double getPhi() {
        return this.phi;
    }

//    /**
//     * Sets the angle of the viewing point up or down from the XZ plane.
//     * 
//     * @param phi  the angle (in radians). 
//     */
//    public void setPhi(float phi) {
//        this.phi = phi;
//        updateMatrixElements();
//    }

    /**
     * Returns the distance of the viewing point from the origin.
     * 
     * @return The distance of the viewing point from the origin. 
     */
    public final double getRho() {
        return this.rho;
    }

    /**
     * Sets the distance of the viewing point from the origin.
     * 
     * @param rho  the new distance. 
     */
    public void setRho(double rho) {
        this.rho = rho;
        //this.up = Point3D.createPoint3D(this.up.getTheta(), this.up.getPhi(), rho);
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
        double x = this.v11 * p.x + this.v21 * p.y;
        double y = this.v12 * p.x + this.v22 * p.y + this.v32 * p.z;
        double z = this.v13 * p.x + this.v23 * p.y + this.v33 * p.z + this.v43;
        double[] rotated = this.rotation.applyRotation(x, y, z, workspace);
        return new Point3D(rotated[0], rotated[1], rotated[2]);
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
        double x = this.v11 * p.x + this.v21 * p.y;
        double y = this.v12 * p.x + this.v22 * p.y + this.v32 * p.z;
        double z = this.v13 * p.x + this.v23 * p.y + this.v33 * p.z + this.v43;
        double[] rotated = this.rotation.applyRotation(x, y, z, workspace);        
        //return new Point2D.Double(-d * x / z, 
        //        -d * y / z);
        return new Point2D.Double(-d * rotated[0] / rotated[2], 
                -d * rotated[1] / rotated[2]);
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
        
        ViewPoint3D vp = new ViewPoint3D(this.theta, this.phi, this.rho, getAngle());
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
     * Moves the viewing point left or right around the sphere.
     * 
     * @param delta  the angle.
     * 
     * @return 
     */
    public void moveLeftRight(double delta) { 
        Point3D v = getVerticalRotationAxis();
        Rotate3D r = new Rotate3D(Point3D.ORIGIN, v, delta);
        double x = getX();
        double y = getY();
        double z = getZ();
        Point3D p = r.applyRotation(x, y, z);
        this.theta = p.getTheta();
        this.phi = p.getPhi();
        updateMatrixElements();
        this.rotation.setAngle(getAngle());
    }
    
    /**
     * Moves the viewing point up or down on the viewing sphere.
     * 
     * @param delta
     * @return 
     */
    public void moveUpDown(double delta) {
        Point3D v = getHorizontalRotationAxis();
        Rotate3D r = new Rotate3D(Point3D.ORIGIN, v, delta);
        Point3D p = r.applyRotation(getX(), getY(), getZ());
        this.up = r.applyRotation(this.up);
        this.theta = p.getTheta();
        this.phi = p.getPhi();
        updateMatrixElements();
        this.rotation.setAngle(getAngle());
    }
    
    /**
     * Rotates the viewing orientation while leaving the view point unchanged.
     * 
     * @param delta  the angle.
     * 
     * @return 
     */
    public ViewPoint3D rotate(double delta) {
        Rotate3D r = new Rotate3D(getPoint(), Point3D.ORIGIN, delta);
        this.up = r.applyRotation(this.up);
        this.rotation.setAngle(getAngle());
        return this;
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
    
    public Point3D getVerticalRotationAxis() {
        return this.up;
    }
    
    public Point3D getHorizontalRotationAxis() {
        return Utils2D.normal(getPoint(), this.up, Point3D.ORIGIN);
    }

}
