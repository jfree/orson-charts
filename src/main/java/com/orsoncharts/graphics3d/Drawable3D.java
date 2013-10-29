/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.graphics3d;

import java.awt.Graphics2D;
import com.orsoncharts.Chart3D;
import java.awt.geom.Rectangle2D;

/**
 * An object in 3D that can be viewed from an arbitrary viewpoint and rendered
 * to any {@link Graphics2D} instance.  The {@link Chart3D} class implements
 * this interface.
 */
public interface Drawable3D {

    /**
     * Returns the dimensions of the 3D object.
     * 
     * @return The dimensions. 
     */
    Dimension3D getDimensions();
    
    /**
     * Returns the view point.
     * 
     * @return The view point (never <code>null</code>).
     */
    ViewPoint3D getViewPoint();
    
    /**
     * Sets the view point.
     * 
     * @param viewPoint  the view point (<code>null</code> not permitted).
     */
    void setViewPoint(ViewPoint3D viewPoint);
    
    /**
     * Returns the 2D offset for the drawable.
     * 
     * @return The translation (never <code>null</code>). 
     */
    public Offset2D getTranslate2D();
    
    /**
     * Sets the translation offset.
     * 
     * @param offset  the offset (<code>null</code> not permitted). 
     */
    public void setTranslate2D(Offset2D offset);
    
    /**
     * Draws the scene to the supplied <code>Graphics2D</code> target.
     * 
     * @param g2  the graphics target (<code>null</code> not permitted).
     * @param bounds  the bounds (<code>null</code> not permitted).
     */
    public void draw(Graphics2D g2, Rectangle2D bounds);
    
}
