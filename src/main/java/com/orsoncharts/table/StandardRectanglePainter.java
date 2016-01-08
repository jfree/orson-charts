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

package com.orsoncharts.table;

import com.orsoncharts.util.Anchor2D;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.util.Fit2D;
import com.orsoncharts.util.ObjectUtils;
import com.orsoncharts.util.Scale2D;
import com.orsoncharts.util.SerialUtils;

/**
 * A {@link RectanglePainter} that fills the rectangle with a 
 * color or image.  Instances of this class are immutable.
 * <br><br>
 * Note that it is possible to use gradient paint with this painter, but it is
 * usually better to use {@link GradientRectanglePainter} since it provides
 * options to transform the gradient to fit the chart background size.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 */
@SuppressWarnings("serial")
public class StandardRectanglePainter implements RectanglePainter, 
        Serializable {
    
    /** The paint (never {@code null}). */
    private transient Paint paint;
    
    /** A background image for the chart, if any. */
    private transient Image image;
    
    private Fit2D imageFit;
    
    /**
     * Creates a new painter that will fill a rectangle with the specified
     * paint.
     * 
     * @param paint  the fill paint ({@code null} not permitted).
     */
    public StandardRectanglePainter(Paint paint) {
        this(paint, null, null);
    }
    
    /**
     * Creates a new painter that will draw an image within the specified
     * rectangle.
     * 
     * @param paint  the background paint ({@code null} not permitted).
     * @param image  the image ({@code null} permitted).
     * @param imageFit  the fit ({@code null} permitted).
     */
    public StandardRectanglePainter(Paint paint, Image image, Fit2D imageFit) {
        ArgChecks.nullNotPermitted(paint, "paint");
        this.paint = paint;
        this.image = image;
        this.imageFit = new Fit2D(Anchor2D.TOP_CENTER, Scale2D.SCALE_BOTH);
        if (imageFit != null) {
            this.imageFit = imageFit;
        }
    }

    /**
     * Returns the paint that will be used to fill rectangles.
     * 
     * @return The paint (never {@code null}).
     */
    public Paint getPaint() {
        return this.paint;
    }
    
    /**
     * Returns the image.
     * 
     * @return The image (possibly {@code null}). 
     */
    public Image getImage() {
        return this.image;
    }
    
    /**
     * Returns the image fit specification.
     * 
     * @return The image fit specification. 
     */
    public Fit2D getImageFit() {
        return this.imageFit;
    }
    
    /**
     * Fills the rectangle with the paint specified in the constructor.
     * 
     * @param g2  the graphics target ({@code null} not permitted).
     * @param bounds  the rectangle ({@code null} not permitted).
     */
    @Override
    public void fill(Graphics2D g2, Rectangle2D bounds) {
        Paint saved = g2.getPaint();
        g2.setPaint(this.paint);
        g2.fill(bounds);
        if (this.image != null) {
            int w = this.image.getWidth(null);
            int h = this.image.getHeight(null);
            Rectangle2D imageBounds = this.imageFit.fit(new Dimension(w, h), 
                    bounds);
            g2.drawImage(this.image, (int) imageBounds.getX(), 
                    (int) imageBounds.getY(), (int) imageBounds.getWidth(),
                    (int) imageBounds.getHeight(), null);
        }
        g2.setPaint(saved);
    }

    /**
     * Tests this painter for equality with an arbitrary object.
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
        if (!(obj instanceof StandardRectanglePainter)) {
            return false;
        }
        StandardRectanglePainter that = (StandardRectanglePainter) obj;
        if (!ObjectUtils.equalsPaint(this.paint, that.paint)) {
            return false;
        }
        return true;
    }
    
    /**
     * Provides serialization support.
     *
     * @param stream  the output stream.
     *
     * @throws IOException  if there is an I/O error.
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        SerialUtils.writePaint(this.paint, stream);
        // TODO : serialize the image 
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the input stream.
     *
     * @throws IOException  if there is an I/O error.
     * @throws ClassNotFoundException  if there is a classpath problem.
     */
    private void readObject(ObjectInputStream stream)
        throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.paint = SerialUtils.readPaint(stream);
        // deserialize the image
    }
}
