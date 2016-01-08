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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.Chart3D;

/**
 * Provides a default implementation of the {@link Drawable3D} interface.
 * This is not used directly in Orson Charts, since the {@link Chart3D} class
 * implements the {@link Drawable3D} interface itself.  However, it is used
 * in testing to ensure that the {@code com.orsoncharts.graphics3d}
 * package can function on a stand-alone basis.
 */
public class DefaultDrawable3D implements Drawable3D {

    /** 
     * The default projection distance. 
     * 
     * @since 1.2
     */
    public static final double DEFAULT_PROJ_DIST = 1500.0;

    /** The viewing point. */
    private ViewPoint3D viewPoint;
    
    /** The projection distance. */
    private double projDist;
    
    /** The 3D world being drawn. */
    private World world;

    private Offset2D offset;

    /**
     * Creates a new instance to display the content of the specified
     * {@code world}.
     * 
     * @param world  the world to view ({@code null} not permitted). 
     */
    public DefaultDrawable3D(World world) {
        ArgChecks.nullNotPermitted(world, "world");
        this.viewPoint = new ViewPoint3D((float) (3 * Math.PI / 2.0), 
                (float) Math.PI / 6, 40.0f, 0.0);
        this.projDist = DEFAULT_PROJ_DIST;
        this.world = world;
        this.offset = new Offset2D();
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
     * @return The view point (never {@code null}). 
     */
    @Override
    public ViewPoint3D getViewPoint() {
        return this.viewPoint;
    }

    /**
     * Sets the view point.
     * 
     * @param viewPoint  the view point ({@code null} not permitted).
     */
    @Override
    public void setViewPoint(ViewPoint3D viewPoint) {
        ArgChecks.nullNotPermitted(viewPoint, "viewPoint");
        this.viewPoint = viewPoint;
    }

    /** 
     * Returns the projection distance.  The default value is 
     * {@link #DEFAULT_PROJ_DIST}, higher numbers flatten out the perspective 
     * and reduce distortion in the projected image.
     * 
     * @return The projection distance.
     * 
     * @since 1.2
     */
    @Override
    public double getProjDistance() {
        return this.projDist;
    }
    
    /**
     * Sets the projection distance.  
     * 
     * @param dist  the distance.
     * 
     * @since 1.2
     */
    @Override
    public void setProjDistance(double dist) {
        this.projDist = dist;
    }

    @Override
    public Offset2D getTranslate2D() {
        return this.offset;
    }

    @Override
    public void setTranslate2D(Offset2D offset) {
        ArgChecks.nullNotPermitted(offset, "offset");
        this.offset = offset;
    }
    
    /**
     * Draws the current view to a {@code Graphics2D} instance.
     * 
     * @param g2  the graphics target ({@code null} not permitted).
     * @param bounds  the bounds ({@code null} not permitted).
     * 
     * @return The rendering state.
     */
    @Override
    public RenderingInfo draw(Graphics2D g2, Rectangle2D bounds) {
        ArgChecks.nullNotPermitted(g2, "g2");
        g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));
        g2.setPaint(Color.WHITE);
        g2.fill(bounds);
        AffineTransform saved = g2.getTransform();
        double dx = bounds.getWidth() / 2;
        double dy = bounds.getHeight() / 2;
        g2.translate(dx, dy);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        Point3D[] eyePts = this.world.calculateEyeCoordinates(this.viewPoint);

        Point2D[] pts = this.world.calculateProjectedPoints(this.viewPoint,
                    this.projDist);
        List<Face> facesInPaintOrder = new ArrayList<Face>(
                this.world.getFaces());

        // sort faces by z-order
        Collections.sort(facesInPaintOrder, new ZOrderComparator(eyePts));

        for (Face f : facesInPaintOrder) {
            double[] plane = f.calculateNormal(eyePts);
            double inprod = plane[0] * this.world.getSunX() + plane[1]
                    * this.world.getSunY() + plane[2] * this.world.getSunZ();
            double shade = (inprod + 1) / 2.0;
            if (Utils2D.area2(pts[f.getVertexIndex(0)],
                    pts[f.getVertexIndex(1)], pts[f.getVertexIndex(2)]) > 0) {
                Color c = f.getColor();
                if (c != null) {
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
                    g2.setPaint(new Color((int) (c.getRed() * shade),
                        (int) (c.getGreen() * shade),
                        (int) (c.getBlue() * shade), c.getAlpha()));
                    g2.fill(p);
                    g2.draw(p);
                }
            } 
        }
        g2.setTransform(saved);
        RenderingInfo info = new RenderingInfo(facesInPaintOrder, pts, dx, dy);
        return info;
    }
    
}
