/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.graphics3d;

/**
 * A dimension in 3D (width, height and depth).
 */
public class Dimension3D {
  
    private double width;
  
    private double height;
  
    private double depth;
  
    /**
     * Creates a new Dimension3D instance.  Instances of this class are 
     * immutable.
     * 
     * @param width  the width.
     * @param height  the height.
     * @param depth   the depth.
     */
    public Dimension3D(double width, double height, double depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
    }
  
    /**
     * Returns the width.
     * 
     * @return The width. 
     */
    public double getWidth() {
        return this.width;
    }
  
    /**
     * Returns the height.
     * 
     * @return The height.
     */
    public double getHeight() {
        return this.height;
    }
  
    /**
     * Returns the depth.
     * 
     * @return The depth. 
     */
    public double getDepth() {
        return this.depth;
    }

    /**
     * Tests this instance for equality with an arbitrary object.
     * 
     * @param obj  the object to test against (<code>null</code> permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Dimension3D other = (Dimension3D) obj;
        if (Double.doubleToLongBits(this.width) != Double.doubleToLongBits(
                other.width)) {
            return false;
        }
        if (Double.doubleToLongBits(this.height) != Double.doubleToLongBits(
                other.height)) {
            return false;
        }
        if (Double.doubleToLongBits(this.depth) != Double.doubleToLongBits(
                other.depth)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a hash code for this instance.
     * 
     * @return A hash code. 
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (int) (Double.doubleToLongBits(this.width) 
                ^ (Double.doubleToLongBits(this.width) >>> 32));
        hash = 17 * hash + (int) (Double.doubleToLongBits(this.height) 
                ^ (Double.doubleToLongBits(this.height) >>> 32));
        hash = 17 * hash + (int) (Double.doubleToLongBits(this.depth) 
                ^ (Double.doubleToLongBits(this.depth) >>> 32));
        return hash;
    }

}
