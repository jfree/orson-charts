/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */
package com.orsoncharts.graphics3d;

import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * A set of methods for a three dimensional scene that can be rendered to
 * a {@link Graphics2D} instance.
 */
public interface Drawable3D {

    /**
     * Returns the view point.
     * 
     * @return The view point (never <code>null</code>).
     */
    public ViewPoint3D getViewPoint();
    
    /**
     * Sets the view point.
     * 
     * @param viewPoint  the view point (<code>null</code> not permitted).
     */
    public void setViewPoint(ViewPoint3D viewPoint);

//    /**
//     * Returns the world.  // TODO: do we need to expose this in the API?
//     * 
//     * @return The world. 
//     */
//    public World getWorld();
//    
//    /**
//     * Sets the world.  // TODO: do we need to expose this in the API?
//     * 
//     * @param world  the world. 
//     */
//    public void setWorld(World world);
//    
    /**
     * Draws the scene to the supplied <code>Graphics2D</code> target.
     * 
     * @param g2
     * @param bounds 
     */
    public void draw(Graphics2D g2, Rectangle bounds);
    
}
