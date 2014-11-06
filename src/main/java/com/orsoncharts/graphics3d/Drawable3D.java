/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of these source files is prohibited.
 * 
 */

package com.orsoncharts.graphics3d;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import com.orsoncharts.Chart3D;
import com.orsoncharts.graphics3d.swing.Panel3D;

/**
 * A three dimensional scene that can be viewed from an arbitrary viewpoint 
 * and rendered to any {@link Graphics2D} instance.  The {@link Chart3D} class 
 * implements this interface.
 * 
 * @see Panel3D
 */
public interface Drawable3D {

    /**
     * Returns the aggregate dimensions of the objects in the 3D scene.  This
     * will be a bounding box for all the objects.  One use for this 
     * information is to determine a suitable default viewing distance for
     * a given scene (one that sizes the 2D projection to something appropriate
     * for the available drawing space).
     * 
     * @return The dimensions (never {@code null}). 
     */
    Dimension3D getDimensions();
    
    /**
     * Returns the point from which the 3D scene is viewed.  The viewing point
     * determines how the 3D scene is projected onto the 2D viewing plane in
     * the {@link #draw(java.awt.Graphics2D, java.awt.geom.Rectangle2D)} method.
     * 
     * @return The view point (never {@code null}).
     */
    ViewPoint3D getViewPoint();
    
    /**
     * Sets a new view point.  Note that the {@code ViewPoint3D} class is
     * implemented so that its position and orientation can be updated directly,
     * so you should use this method only when you want to set an entirely
     * new view point.
     * 
     * @param viewPoint  the view point ({@code null} not permitted).
     */
    void setViewPoint(ViewPoint3D viewPoint);
    
    /** 
     * Returns the projection distance.  A typical value is {@code 1500}, 
     * higher numbers flatten out the perspective and reduce distortion in the
     * projected image.
     * 
     * @return The projection distance.
     * 
     * @since 1.2
     */
    double getProjDistance();
    
    /**
     * Sets the projection distance.  A typical value is {@code 1500} (but this
     * will depend on the dimensions of the scene), higher numbers flatten out
     * the perspective and reduce distortion in the projected image.
     * 
     * @param dist  the distance.
     * 
     * @since 1.2
     */
    void setProjDistance(double dist);
    
    /**
     * Returns the 2D offset for the scene.  Normally this will default
     * to {@code (0, 0)}.
     * 
     * @return The translation offset (never {@code null}). 
     */
    Offset2D getTranslate2D();
    
    /**
     * Sets the translation offset.  This is typically used to allow the user
     * to modify the offset of a 2D projection on-screen by dragging with the 
     * mouse.
     * 
     * @param offset  the translation offset ({@code null} not permitted). 
     */
    void setTranslate2D(Offset2D offset);
    
    /**
     * Draws the scene to the supplied {@code Graphics2D} target and returns
     * an object containing state information about the rendering.
     * 
     * @param g2  the graphics target ({@code null} not permitted).
     * @param bounds  the bounds ({@code null} not permitted).
     * 
     * @return State information about the 3D scene that has been drawn 
     *         (never {@code null}).
     */
    RenderingInfo draw(Graphics2D g2, Rectangle2D bounds);
    
}
