/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.graphics3d;

/**
 * A point in 3D space.
 */
public class Point3D {

  /** The x-coordinate. */
  public float x;
    
  /** The y-coordinate. */
  public float y;
    
  /** The z-coordinate. */
  public float z;
    
  /**
   * Creates a new point in 3D space.
   * 
   * @param x  the x-coordinate.
   * @param y  the y-coordinate.
   * @param z  the z-coordinate.
   */
  public Point3D(double x, double y, double z) {
    this.x = (float) x;
    this.y = (float) y;
    this.z = (float) z;
  }
    
  /**
   * Sets the coordinates for this point.
   * 
   * @param x  the x-coordinate.
   * @param y  the y-coordinate.
   * @param z  the z-coordinate.
   */
  public void setPoint(double x, double y, double z) {
    this.x = (float) x;
    this.y = (float) y;
    this.z = (float) z;        
  }
    
  public String toString() {
    return "[" + this.x + ", " + this.y + ", " + this.z + "]";
  }
  
}
