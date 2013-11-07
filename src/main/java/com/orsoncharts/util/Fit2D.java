/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.util;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * A specification for the alignment and fitting of one rectangle (the source 
 * rectangle) with reference to another (the target rectangle).  One application
 * for this is to specify how the background image for a chart should be 
 * aligned and scaled.
 */
public class Fit2D {
    
    /** The anchor point for alignment. */
    private Anchor2D anchor;
    
    /** The scaling to apply. */
    private Scale2D scale;
    
    /**
     * Creates a new instance.
     * 
     * @param anchor  the anchor point (<code>null</code> not permitted).
     * @param scale  the scaling (<code>null</code> not permitted).
     */
    public Fit2D(Anchor2D anchor, Scale2D scale) {
        this.anchor = anchor;
        this.scale = scale;
    }
    
    /**
     * Fits a rectangle of the specified dimension to the target rectangle,
     * aligning and scaling according to the attributes of this instance.
     * 
     * @param srcDim  the dimensions of the source rectangle (<code>null</code>
     *     not permitted).
     * @param target  the target rectangle (<code>null</code> not permitted).
     * 
     * @return The bounds of the fitted rectangle (never <code>null</code>). 
     */
    public Rectangle2D fit(Dimension2D srcDim, Rectangle2D target) {
        Rectangle2D result = new Rectangle2D.Double();
        if (this.scale == Scale2D.SCALE_BOTH) {
            result.setFrame(target);
            return result;
        }
        double width = srcDim.getWidth();
        if (this.scale == Scale2D.SCALE_HORIZONTAL) {
            width = target.getWidth();
            if (!this.anchor.getRefPt().isHorizontalCenter()) {
                width -= 2 * this.anchor.getOffset().getDX();
            }
        }
        double height = srcDim.getHeight();
        if (this.scale == Scale2D.SCALE_VERTICAL) {
            height = target.getHeight();
            if (!this.anchor.getRefPt().isVerticalCenter()) {
                height -= 2 * this.anchor.getOffset().getDY();
            }
        }
        Point2D pt = this.anchor.getAnchorPoint(target);
        double x = Double.NaN; 
        if (this.anchor.getRefPt().isLeft()) {
            x = pt.getX();
        } else if (this.anchor.getRefPt().isHorizontalCenter()) {
            x = target.getCenterX() - width / 2;
        } else if (this.anchor.getRefPt().isRight()) {
            x = pt.getX() - width;
        }
        double y = Double.NaN;
        if (this.anchor.getRefPt().isTop()) {
            y = pt.getY();
        } else if (this.anchor.getRefPt().isVerticalCenter()) {
            y = target.getCenterY() - height / 2;
        } else if (this.anchor.getRefPt().isBottom()) {
            y = pt.getY() - height;
        }
        result.setRect(x, y, width, height);
        return result;
    }
}
