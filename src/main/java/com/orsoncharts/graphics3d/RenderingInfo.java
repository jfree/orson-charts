/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of this source file is prohibited.
 * 
 */

package com.orsoncharts.graphics3d;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.List;

/**
 * Rendering info returned from the {@link Drawable3D} draw() method.
 * 
 * @since 1.3
 */
public class RenderingInfo {
    
    /**
     * A list of the faces drawn in order of rendering.
     */
    private List<Face> faces;
    
    /** The projected points for the vertices in the faces. */
    Point2D[] projPts;
    
    /** The x-translation. */
    private double dx;
    
    /** The y-translation. */
    public double dy;
    
    /**
     * Creates a new instance.
     * 
     * @param faces  the rendered faces (in order of rendering).
     * @param projPts  the projected points for all vertices in the 3D model.
     * @param dx  the x-delta.
     * @param dy  the y-delta.
     */
    public RenderingInfo(List<Face> faces, Point2D[] projPts, double dx, 
            double dy) {
        this.faces = faces;
        this.projPts = projPts;
        this.dx = dx;
        this.dy = dy;
    }
    
    /**
     * Returns the list of faces rendered.
     * 
     * @return The list of faces.
     */
    public List<Face> getFaces() {
        return this.faces;
    }
    
    /**
     * Returns the projected points.
     * 
     * @return The projected points. 
     */
    public Point2D[] getProjectedPoints() {
        return this.projPts;
    }
    
    /**
     * Returns the x-translation amount.  All projected points are centered
     * on (0, 0) but the rendering to the screen (or other Graphics2D target)
     * performs two translations: the first is to the center of the bounding
     * rectangle, and the second is to apply the translate2D attribute of the
     * chart.  The result of these translations is stored here and used in the
     * fetchObjectAt(x, y) method.
     * 
     * @return The x-translation. 
     */
    public double getDX() {
        return this.dx;
    }
    
    /**
     * Returns the y-translation amount.
     * 
     * @return The y-translation. 
     */
    public double getDY() {
        return this.dy;
    }
    
    /**
     * Fetches the object, if any, that is rendered at (x, y).
     * 
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * 
     * @return The object (or <code>null</code>). 
     */
    public Object3D fetchObjectAt(double x, double y) {
        for (int i = this.faces.size() - 1; i >= 0; i--) {
            Face f = this.faces.get(i);
            Path2D p = f.createPath(this.projPts);
            if (p.contains(x - dx, y - dy)) {
                return f.getOwner();
            }
        }
        return null;
    }
    
}
