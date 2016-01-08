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

package com.orsoncharts.util;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

/**
 * A specification for the alignment and fitting of one rectangle (the source 
 * rectangle) with reference to another (the target rectangle).  Instances of
 * this class are immutable.
 * <br><br>
 * One application for this is to specify how the background image for a chart 
 * should be aligned and scaled.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 */
@SuppressWarnings("serial")
public class Fit2D implements Serializable {

    /** 
     * Aligns a source rectangle to the center of a target rectangle, without
     * resizing it.
     * 
     * @since 1.1
     */
    public static final Fit2D CENTER_NO_SCALING = new Fit2D(Anchor2D.CENTER, 
            Scale2D.NONE);
 
    /** 
     * Fits a source rectangle to the top left of a target rectangle, without
     * resizing it.
     * 
     * @since 1.1
     */
    public static final Fit2D TOP_LEFT_NO_SCALING 
            = new Fit2D(Anchor2D.TOP_LEFT, Scale2D.NONE);

    /** 
     * Fits a source rectangle to the top center of a target rectangle, without
     * resizing it.
     * 
     * @since 1.1
     */
    public static final Fit2D TOP_CENTER_NO_SCALING 
            = new Fit2D(Anchor2D.TOP_CENTER, Scale2D.NONE);

    /** 
     * Fits a source rectangle to the top right of a target rectangle, without
     * resizing it.
     * 
     * @since 1.1
     */
    public static final Fit2D TOP_RIGHT_NO_SCALING 
            = new Fit2D(Anchor2D.TOP_RIGHT, Scale2D.NONE);

    /** 
     * Fits a source rectangle to the center left of a target rectangle, 
     * without resizing it.
     * 
     * @since 1.1
     */
    public static final Fit2D CENTER_LEFT_NO_SCALING 
            = new Fit2D(Anchor2D.CENTER_LEFT, Scale2D.NONE);

    /** 
     * Fits a source rectangle to the center right of a target rectangle, 
     * without resizing it.
     * 
     * @since 1.1
     */
    public static final Fit2D CENTER_RIGHT_NO_SCALING 
            = new Fit2D(Anchor2D.CENTER_RIGHT, Scale2D.NONE);

    /** 
     * Fits a source rectangle to the bottom left of a target rectangle, 
     * without resizing it.
     * 
     * @since 1.1
     */
    public static final Fit2D BOTTOM_LEFT_NO_SCALING 
            = new Fit2D(Anchor2D.BOTTOM_LEFT, Scale2D.NONE);

    /** 
     * Fits a source rectangle to the bottom center of a target rectangle, 
     * without resizing it.
     * 
     * @since 1.1
     */
    public static final Fit2D BOTTOM_CENTER_NO_SCALING 
            = new Fit2D(Anchor2D.BOTTOM_CENTER, Scale2D.NONE);

    /** 
     * Fits a source rectangle to the bottom right of a target rectangle, 
     * without resizing it.
     * 
     * @since 1.1
     */
    public static final Fit2D BOTTOM_RIGHT_NO_SCALING 
            = new Fit2D(Anchor2D.BOTTOM_RIGHT, Scale2D.NONE);
    
    /**
     * Returns a fitter for the specified reference point.
     * 
     * @param refPt  the reference point ({@code null} not permitted).
     * 
     * @return A fitter.
     * 
     * @since 1.1
     */
    public static Fit2D getNoScalingFitter(RefPt2D refPt) {
        switch (refPt) {
            case TOP_LEFT : return Fit2D.TOP_LEFT_NO_SCALING;
            case TOP_CENTER : return Fit2D.TOP_CENTER_NO_SCALING;
            case TOP_RIGHT : return Fit2D.TOP_RIGHT_NO_SCALING;
            case CENTER_LEFT : return Fit2D.CENTER_LEFT_NO_SCALING;
            case CENTER : return Fit2D.CENTER_NO_SCALING;
            case CENTER_RIGHT : return Fit2D.CENTER_RIGHT_NO_SCALING;
            case BOTTOM_LEFT : return Fit2D.BOTTOM_LEFT_NO_SCALING;
            case BOTTOM_CENTER : return Fit2D.BOTTOM_CENTER_NO_SCALING;
            case BOTTOM_RIGHT : return Fit2D.BOTTOM_RIGHT_NO_SCALING;
        }
        throw new IllegalStateException("RefPt2D not recognised : " + refPt); 
    }
    
    /**
     * Scale the source rectangle to fit the target rectangle.
     * 
     * @since 1.1
     */
    public static final Fit2D SCALE_TO_FIT_TARGET 
            = new Fit2D(Anchor2D.CENTER, Scale2D.SCALE_BOTH);

    /** The anchor point for alignment. */
    private Anchor2D anchor;
    
    /** The scaling to apply. */
    private Scale2D scale;
    
    /**
     * Creates a new instance.
     * 
     * @param anchor  the anchor point ({@code null} not permitted).
     * @param scale  the scaling ({@code null} not permitted).
     */
    public Fit2D(Anchor2D anchor, Scale2D scale) {
        ArgChecks.nullNotPermitted(anchor, "anchor");
        ArgChecks.nullNotPermitted(scale, "scale");
        this.anchor = anchor;
        this.scale = scale;
    }
    
    /**
     * Returns the anchor.
     * 
     * @return The anchor (never {@code null}).
     * 
     * @since 1.1
     */
    public Anchor2D getAnchor() {
        return this.anchor;
    }
    
    /**
     * Returns the scaling.
     * 
     * @return The scaling (never {@code null}).
     * 
     * @since 1.1
     */
    public Scale2D getScale() {
        return this.scale;
    }
    
    /**
     * Fits a rectangle of the specified dimension to the target rectangle,
     * aligning and scaling according to the attributes of this instance.
     * 
     * @param srcDim  the dimensions of the source rectangle ({@code null}
     *     not permitted).
     * @param target  the target rectangle ({@code null} not permitted).
     * 
     * @return The bounds of the fitted rectangle (never {@code null}). 
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
    
    /**
     * Tests this instance for equality with an arbitrary object.
     * 
     * @param obj  the object ({@code null} permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Fit2D)) {
            return false;
        }
        Fit2D that = (Fit2D) obj;
        if (!this.anchor.equals(that.anchor)) {
            return false;
        }
        if (!this.scale.equals(that.scale)) {
            return false;
        }
        return true;
    }
}
