/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.graphics3d;

import com.orsoncharts.Chart3D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.orsoncharts.util.ArgChecks;
import java.awt.geom.Rectangle2D;

/**
 * Provides a default implementation of the {@link Drawable3D} interface.
 * This is not used directly in Orson Charts, since the {@link Chart3D} class
 * implements the {@link Drawable3D} interface.  
 */
public class DefaultDrawable3D implements Drawable3D {

    /** The viewing point. */
    private ViewPoint3D viewPoint;
    
    /** The 3D world being drawn. */
    private World world;
    
    /**
     * Creates a new instance to display the content of the specified
     * <code>world</code>.
     * 
     * @param world  the world to view (<code>null</code> not permitted). 
     */
    public DefaultDrawable3D(World world) {
        ArgChecks.nullNotPermitted(world, "world");
        this.viewPoint = new ViewPoint3D((float) (3 * Math.PI / 2.0), 
                (float) Math.PI / 6, 40.0f, 0.0);
        this.world = world;    
    }
    
    /**
     * Returns the dimensions of the 3D object.
     * 
     * @return The dimensions. 
     */
    @Override
    public Dimension3D getDimensions() {
        return new Dimension3D(1.0, 1.0, 1.0);  // FIXME
    }
    
    /**
     * Returns the view point.
     * 
     * @return The view point (never <code>null</code>). 
     */
    @Override
    public ViewPoint3D getViewPoint() {
        return this.viewPoint;
    }

    /**
     * Sets the view point.
     * 
     * @param viewPoint  the view point (<code>null</code> not permitted).
     */
    @Override
    public void setViewPoint(ViewPoint3D viewPoint) {
        ArgChecks.nullNotPermitted(viewPoint, "viewPoint");
        this.viewPoint = viewPoint;
    }

    @Override
    public Offset2D getTranslate2D() {
        // TODO
        return new Offset2D();
    }

    @Override
    public void setTranslate2D(Offset2D offset) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Draws the current view to a <code>Graphics2D</code> instance.
     * 
     * @param g2  the graphics target (<code>null</code> not permitted).
     * @param bounds  the bounds.
     */
    @Override
    public void draw(Graphics2D g2, Rectangle2D bounds) {
        ArgChecks.nullNotPermitted(g2, "g2");
        g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));
        g2.setPaint(Color.WHITE);
        g2.fill(bounds);
        AffineTransform saved = g2.getTransform();
        g2.translate(bounds.getWidth() / 2, bounds.getHeight() / 2);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        Point3D[] eyePts = this.world.calculateEyeCoordinates(this.viewPoint);

        Point2D[] pts = this.world.calculateProjectedPoints(this.viewPoint,
                    1000f);
        List<Face> facesInPaintOrder = new ArrayList<Face>(
                this.world.getFaces());

        // sort faces by z-order
        Collections.sort(facesInPaintOrder, new ZOrderComparator(eyePts));

        for (Face f : facesInPaintOrder) {
            GeneralPath p = new GeneralPath();
            for (int v = 0; v < f.getVertexCount(); v++) {
                if (v == 0) {
                    p.moveTo(pts[f.getVertexIndex(v)].getX(),
                            pts[f.getVertexIndex(v)].getY());
                }
                else {
                    p.lineTo(pts[f.getVertexIndex(v)].getX(),
                            pts[f.getVertexIndex(v)].getY());
                }
            }
            p.closePath();

            double[] plane = f.calculateNormal(eyePts);
            double inprod = plane[0] * this.world.getSunX() + plane[1]
                    * this.world.getSunY() + plane[2] * this.world.getSunZ();
            double shade = (inprod + 1) / 2.0;
            if (Utils2D.area2(pts[f.getVertexIndex(0)],
                    pts[f.getVertexIndex(1)], pts[f.getVertexIndex(2)]) > 0) {
                Color c = f.getColor();
                if (c != null) {
                    g2.setPaint(new Color((int) (c.getRed() * shade),
                        (int) (c.getGreen() * shade),
                        (int) (c.getBlue() * shade), c.getAlpha()));
                    g2.fill(p);
                    g2.draw(p);
                }
            } 
        }
        g2.setTransform(saved);      
    }
    
}
