/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orsoncharts.graphics3d;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Provides a default implementation of the {@link Drawable3D} interface.
 */
public class DefaultDrawable3D implements Drawable3D {

    private ViewPoint3D viewPoint;
    
    private World world;
    
    public DefaultDrawable3D() {
        this(new World());
    }
    
    public DefaultDrawable3D(World world) {
        this.viewPoint = new ViewPoint3D((float) (3 * Math.PI / 2.0), 
                (float) Math.PI / 6, 40.0f);
        this.world = world;    
    }
    
    @Override
    public ViewPoint3D getViewPoint() {
        return this.viewPoint;
    }

    @Override
    public void setViewPoint(ViewPoint3D viewPoint) {
        this.viewPoint = viewPoint;
    }

//    @Override
//    public World getWorld() {
//        return this.world;
//    }
//
//    @Override
//    public void setWorld(World world) {
//        this.world = world;
//    }
    
    @Override
    public void draw(Graphics2D g2, Rectangle bounds) {
        
        g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));
        g2.setPaint(Color.WHITE);
        g2.fill(bounds);
        AffineTransform saved = g2.getTransform();
        g2.translate(bounds.width / 2, bounds.height / 2);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        Point3D[] eyePts = this.world.calculateEyeCoordinates(this.viewPoint);

        Point2D[] pts = this.world.calculateProjectedPoints(this.viewPoint,
                    1000f);
        List<Face> facesInPaintOrder = new ArrayList<Face>(this.world.getFaces());

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
            if (Tools2D.area2(pts[f.getVertexIndex(0)],
                    pts[f.getVertexIndex(1)], pts[f.getVertexIndex(2)]) > 0) {
                Color c = f.getColor();
                if (c != null) {
                    g2.setPaint(new Color((int) (c.getRed() * shade),
                        (int) (c.getGreen() * shade),
                        (int) (c.getBlue() * shade), c.getAlpha()));
                    g2.fill(p);
                    g2.draw(p);
                }
                f.setRendered(true);
            } else {
                f.setRendered(false);
            }
        }
        g2.setTransform(saved);      
    }
    
}
