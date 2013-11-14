/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts;

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
import java.awt.Dimension;
import java.awt.Image;

/**
 * A {@link RectanglePainter} that fills the rectangle with a 
 * color or image.  Instances of this class are immutable.
 * <br><br>
 * Note that it is possible to use gradient paint with this painter, but it is
 * usually better to use {@link GradientRectanglePainter} since it provides
 * options to transform the gradient to fit the chart background size.
 */
public class StandardRectanglePainter implements RectanglePainter, 
        Serializable {
    
    /** The paint (never <code>null</code>). */
    private transient Paint paint;
    
    /** A background image for the chart, if any. */
    private transient Image image;
    
    private Fit2D fit;
    
    /**
     * Creates a new painter that will fill a rectangle with the specified
     * paint.
     * 
     * @param paint  the fill paint (<code>null</code> not permitted).
     */
    public StandardRectanglePainter(Paint paint) {
        this(paint, null, null);
    }
    
    /**
     * Creates a new painter that will draw an image within the specified
     * rectangle.
     * 
     * @param paint  the background paint (<code>null</code> not permitted).
     * @param image  the image (<code>null</code> permitted).
     * @param imageFit  the fit (<code>null</code> permitted).
     */
    public StandardRectanglePainter(Paint paint, Image image, Fit2D imageFit) {
        ArgChecks.nullNotPermitted(paint, "paint");
        this.paint = paint;
        this.image = image;
        this.fit = new Fit2D(TitleAnchor.TOP_CENTER, Scale2D.SCALE_BOTH);
        if (imageFit != null) {
            this.fit = imageFit;
        }
    }

    /**
     * Returns the paint that will be used to fill rectangles.
     * 
     * @return The paint (never <code>null</code>).
     */
    public Paint getPaint() {
        return this.paint;
    }
    
    /**
     * Returns the image.
     * 
     * @return The image (possibly <code>null</code>). 
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
        return this.fit;
    }
    
    /**
     * Fills the rectangle with the paint specified in the constructor.
     * 
     * @param g2  the graphics target (<code>null</code> not permitted).
     * @param bounds  the rectangle (<code>null</code> not permitted).
     */
    @Override
    public void fill(Graphics2D g2, Rectangle2D bounds) {
        Paint saved = g2.getPaint();
        g2.setPaint(this.paint);
        g2.fill(bounds);
        if (this.image != null) {
            int w = this.image.getWidth(null);
            int h = this.image.getHeight(null);
            Rectangle2D imageBounds = this.fit.fit(new Dimension(w, h), bounds);
            g2.drawImage(this.image, (int) imageBounds.getX(), 
                    (int) imageBounds.getY(), (int) imageBounds.getWidth(),
                    (int) imageBounds.getHeight(), null);
        }
        g2.setPaint(saved);
    }

    /**
     * Tests this painter for equality with an arbitrary object.
     * 
     * @param obj  the object (<code>null</code> permitted).
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
