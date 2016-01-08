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
import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 * Specifies the location and orientation of the view point in 3D space.  
 * Assumes the eye looks towards the origin in world coordinates.
 * <br><br>
 * There are four basic operations to move the view point:
 * <ul>
 * <li>{@link #panLeftRight(double)} - rotates around the scene horizontally 
 *     from the perspective of the viewer;</li>
 * <li>{@link #moveUpDown(double)} - rotates around the scene vertically from 
 *     the perspective of the viewer;</li>
 * <li>{@link #roll(double)} - maintains the same viewing location but rolls 
 *     by the specified angle (like tilting a camera);</li>
 * <li>{@link #setRho(double)} - sets the distance of the view location from
 *     the center of the 3D scene (zoom in and out).</li>
 * </ul>
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 */
@SuppressWarnings("serial")
public class ViewPoint3D implements Serializable {

    /**
     * Creates and returns a view point for looking at a chart from the 
     * front and above.
     * 
     * @param rho  the distance.
     * 
     * @return A view point. 
     */
    public static ViewPoint3D createAboveViewPoint(double rho) {
        return new ViewPoint3D(-Math.PI / 2, 9 * Math.PI / 8, rho, 0);    
    }

    /**
     * Creates and returns a view point for looking at a chart from the 
     * front and above and to the left.
     * 
     * @param rho  the distance.
     * 
     * @return A view point. 
     */
    public static ViewPoint3D createAboveLeftViewPoint(double rho) {
        ViewPoint3D vp = createAboveViewPoint(rho);
        vp.panLeftRight(-Math.PI / 6);
        return vp;    
    }

    /**
     * Creates and returns a view point for looking at a chart from the 
     * front and above and to the right.
     * 
     * @param rho  the distance.
     * 
     * @return A view point. 
     */
    public static ViewPoint3D createAboveRightViewPoint(double rho) {
        ViewPoint3D vp = createAboveViewPoint(rho);
        vp.panLeftRight(Math.PI / 6);
        return vp;    
    }
    
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
    
    /** Applies the rotation for the orientation of the view. */
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
     * @param orientation  the angle of rotation.
     */
    public ViewPoint3D(double theta, double phi, double rho, 
            double orientation) {
        this.theta = theta;
        this.phi = phi;
        this.rho = rho;
        updateMatrixElements();
        this.rotation = new Rotate3D( Point3D.ORIGIN, Point3D.UNIT_Z, 
                orientation);
        this.up = this.rotation.applyRotation(Point3D.createPoint3D(this.theta, 
                this.phi - Math.PI / 2, this.rho));
        this.workspace = new double[3];
    }
    
    /**
     * Creates a new instance using the specified point and orientation.
     * 
     * @param p  the viewing point.
     * @param orientation  the orientation.
     */
    public ViewPoint3D(Point3D p, double orientation) {
        this.rho = (float) Math.sqrt(p.x * p.x + p.y * p.y + p.z * p.z);
        if (Math.sqrt(p.x * p.x + p.y * p.y) > 0.000001) {
            this.theta = (float) Math.atan2(p.y, p.x);
        }
        this.phi = (float) Math.acos(p.z / this.rho);
        updateMatrixElements();
        this.rotation = new Rotate3D( Point3D.ORIGIN, Point3D.UNIT_Z, 
                orientation);
        this.up = this.rotation.applyRotation(Point3D.createPoint3D(this.theta, 
                this.phi - Math.PI / 2, this.rho));
        this.workspace = new double[3];
    }

   /**
     * Returns the angle of rotation from the x-axis about the z-axis, 
     * in radians.  This attribute is set via the constructor and updated
     * via the {@link #panLeftRight(double)} and {@link #moveUpDown(double)}
     * methods - there is no setter method, you cannot update it directly.
     * 
     * @return The angle (in radians). 
     */
    public final double getTheta() {
        return this.theta;
    }

    /**
     * Returns the angle of the viewing point down from the z-axis.  This 
     * attribute is set via the constructor and updated via the 
     * {@link #panLeftRight(double)} and {@link #moveUpDown(double)} methods 
     * - there is no setter method, you cannot update it directly.
     * 
     * @return The angle of the viewing point down from the z-axis.
     *     (in radians).
     */
    public final double getPhi() {
        return this.phi;
    }

    /**
     * Returns the distance of the viewing point from the origin.
     * 
     * @return The distance of the viewing point from the origin. 
     * 
     * @see #setRho(double) 
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
        this.up = Point3D.createPoint3D(this.up.getTheta(), this.up.getPhi(), 
                rho);
        updateMatrixElements();
    }

    /**
     * Returns the x-coordinate of the viewing point.  This value is 
     * calculated from the spherical coordinates.
     * 
     * @return The x-coordinate of the viewing point.
     */
    public final double getX() {
        return this.rho * Math.sin(this.phi) * Math.cos(this.theta);
    }
    
    /**
     * Returns the y-coordinate of the viewing point.  This value is 
     * calculated from the spherical coordinates.
     * 
     * @return The y-coordinate of the viewing point.
     */
    public final double getY() {
        return this.rho * Math.sin(this.phi) * Math.sin(this.theta);
    }
    
    /**
     * Returns the z-coordinate of the viewing point.  This value is 
     * calculated from the spherical coordinates.
     * 
     * @return The z-coordinate of the viewing point.
     */
    public final double getZ() {
        return this.rho * Math.cos(this.phi);
    }
    
    /**
     * Returns the location of the view point.  Note that a new instance of 
     * {@code Point3D} is created each time this method is called. 
     * 
     * @return The viewing point (never {@code null}).
     */
    public final Point3D getPoint() {
        return new Point3D(getX(), getY(), getZ());
    }
    
    /**
     * Returns the roll angle (orientation) for the view point.  This is 
     * calculated by reference to second point on the sphere that is a 
     * quarter turn from the view point location (this second point defines
     * the "up" direction for the view).
     * 
     * @return The roll angle (in radians).
     */
    public double calcRollAngle() {
        Point3D vp = getPoint();
        Point3D n1 = Utils3D.normal(vp, this.up, Point3D.ORIGIN);
        Point3D screenup = Point3D.createPoint3D(this.theta, 
               this.phi - (Math.PI / 2), this.rho);
        Point3D n2 = Utils3D.normal(vp, screenup, Point3D.ORIGIN);
        double angle = Utils3D.angle(n1, n2);
        if (Utils3D.scalarprod(n1, screenup) >= 0.0) {
            return angle;
        } else {
            return -angle;
        }    
    }

    /**
     * Moves the viewing point left or right around the 3D scene. 
     * 
     * @param delta  the angle (in radians).
     */
    public void panLeftRight(double delta) { 
        Point3D v = getVerticalRotationAxis();
        Rotate3D r = new Rotate3D(Point3D.ORIGIN, v, delta);
        Point3D p = r.applyRotation(getX(), getY(), getZ());
        this.theta = p.getTheta();
        this.phi = p.getPhi();
        updateMatrixElements();
        this.rotation.setAngle(calcRollAngle());
    }
    
    /**
     * Moves the viewing point up or down on the viewing sphere.
     * 
     * @param delta  the angle delta (in radians).
     */
    public void moveUpDown(double delta) {
        Point3D v = getHorizontalRotationAxis();
        Rotate3D r = new Rotate3D(Point3D.ORIGIN, v, delta);
        Point3D p = r.applyRotation(getX(), getY(), getZ());
        this.up = r.applyRotation(this.up);
        this.theta = p.getTheta();
        this.phi = p.getPhi();
        updateMatrixElements();
        this.rotation.setAngle(calcRollAngle());
    }
    
    /**
     * Rolls the view while leaving the location of the view point unchanged.
     * 
     * @param delta  the angle (in radians).
     */
    public void roll(double delta) {
        // we rotate the "up" point around the sphere by delta radians
        Rotate3D r = new Rotate3D(getPoint(), Point3D.ORIGIN, delta);
        this.up = r.applyRotation(this.up);
        this.rotation.setAngle(calcRollAngle());
    }
    
    /**
     * Converts a point in world coordinates to a point in eye coordinates.
     *
     * @param p  the point ({@code null} not permitted).
     *
     * @return The point in eye coordinates.
     */
    public Point3D worldToEye(Point3D p) {
        double x = this.v11 * p.x + this.v21 * p.y;
        double y = this.v12 * p.x + this.v22 * p.y + this.v32 * p.z;
        double z = this.v13 * p.x + this.v23 * p.y + this.v33 * p.z + this.v43;
        double[] rotated = this.rotation.applyRotation(x, y, z, this.workspace);
        return new Point3D(rotated[0], rotated[1], rotated[2]);
    }

    /**
     * Calculates and returns the screen coordinates for the specified point
     * in (world) 3D space.  
     *
     * @param p  the point.
     * @param d  the projection distance.
     *
     * @return The screen coordinate.
     */
    public Point2D worldToScreen(Point3D p, double d) {
        double x = this.v11 * p.x + this.v21 * p.y;
        double y = this.v12 * p.x + this.v22 * p.y + this.v32 * p.z;
        double z = this.v13 * p.x + this.v23 * p.y + this.v33 * p.z + this.v43;
        double[] rotated = this.rotation.applyRotation(x, y, z, this.workspace);        
        return new Point2D.Double(-d * rotated[0] / rotated[2], 
                -d * rotated[1] / rotated[2]);
    }

    /**
     * Calculate the distance that would render a box of the given dimensions 
     * within a screen area of the specified size.
     * 
     * @param target  the target dimension ({@code null} not permitted).
     * @param dim3D  the dimensions of the 3D content ({@code null} not 
     *     permitted).
     * @param projDist  the projection distance.
     * 
     * @return The optimal viewing distance. 
     */
    public float optimalDistance(Dimension2D target, Dimension3D dim3D,
            double projDist) {
        
        ViewPoint3D vp = new ViewPoint3D(this.theta, this.phi, this.rho, 
                calcRollAngle());
        float near = (float) dim3D.getDiagonalLength();
        float far = (float) near * 40;
        
        World w = new World();
        double ww = dim3D.getWidth();
        double hh = dim3D.getHeight();
        double dd = dim3D.getDepth();
        w.add(Object3D.createBox(0, ww, 0, hh, 0, dd, Color.RED));
               
        while (true) {
            vp.setRho(near);
            Point2D[] nearpts = w.calculateProjectedPoints(vp, projDist);
            Dimension neardim = Utils2D.findDimension(nearpts);
            double nearcover = coverage(neardim, target);
            vp.setRho(far);
            Point2D[] farpts = w.calculateProjectedPoints(vp, projDist);
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
            Point2D[] midpts = w.calculateProjectedPoints(vp, projDist);
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
                    return Math.max(wpercent, hpercent);
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
     * Returns the vector that points "up" in relation to the orientation of
     * the view point.  This vector can be used to rotate the viewing point
     * around the 3D scene (pan left / right).
     * 
     * @return The vector (never {@code null}). 
     */
    public Point3D getVerticalRotationAxis() {
        return this.up;
    }
    
    /**
     * Returns a vector at right angles to the viewing direction and the "up"
     * vector (this axis can be used to rotate forward and backwards).
     * 
     * @return A vector (never {@code null}). 
     */
    public Point3D getHorizontalRotationAxis() {
        return Utils3D.normal(getPoint(), this.up, Point3D.ORIGIN);
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

    /**
     * Tests this view point for equality with an arbitrary object.
     * 
     * @param obj  the object ({@code null} permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ViewPoint3D)) {
            return false;
        }
        ViewPoint3D that = (ViewPoint3D) obj;
        if (this.theta != that.theta) {
            return false;
        }
        if (this.phi != that.phi) {
            return false;
        }
        if (this.rho != that.rho) {
            return false;
        }
        if (!this.up.equals(that.up)) {
            return false;
        }
        return true;
    }

}
