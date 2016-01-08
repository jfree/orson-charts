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

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.text.AttributedString;

/**
 * Utility methods for working with text.
 */
public class TextUtils {

    private TextUtils() {
        // no need to instantiate this
    }
    
    /**
     * Draws a string such that the specified anchor point is aligned to the
     * given {@code (x, y)} location, and returns a bounding rectangle 
     * for the text.
     *
     * @param text  the text.
     * @param g2  the graphics device ({@code null} not permitted).
     * @param x  the x coordinate (Java 2D).
     * @param y  the y coordinate (Java 2D).
     * @param anchor  the anchor location ({@code null} not permitted).
     *
     * @return The text bounds (adjusted for the text position).
     */
    public static Rectangle2D drawAlignedString(String text,
            Graphics2D g2, float x, float y, TextAnchor anchor) {

        Rectangle2D textBounds = new Rectangle2D.Double();
        float[] adjust = deriveTextBoundsAnchorOffsets(g2, text, anchor,
                textBounds);
        // adjust text bounds to match string position
        textBounds.setRect(x + adjust[0], y + adjust[1] + adjust[2],
            textBounds.getWidth(), textBounds.getHeight());
        g2.drawString(text, x + adjust[0], y + adjust[1]);
        return textBounds;
    }

    /**
     * Returns the bounds of an aligned string.
     * 
     * @param text  the string ({@code null} not permitted).
     * @param g2  the graphics target ({@code null} not permitted).
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param anchor  the anchor point on the text that will be aligned to 
     *     {@code (x, y)} ({@code null} not permitted).
     * 
     * @return The text bounds (never {@code null}).
     * 
     * @since 1.3
     */
    public static Rectangle2D calcAlignedStringBounds(String text,
            Graphics2D g2, float x, float y, TextAnchor anchor) {

        Rectangle2D textBounds = new Rectangle2D.Double();
        float[] adjust = deriveTextBoundsAnchorOffsets(g2, text, anchor,
                textBounds);
        // adjust text bounds to match string position
        textBounds.setRect(x + adjust[0], y + adjust[1] + adjust[2],
            textBounds.getWidth(), textBounds.getHeight());
        return textBounds;
    }
    
    /**
     * A utility method that calculates the anchor offsets for a string.
     * Normally, the {@code (x, y)} coordinate for drawing text is a point on 
     * the baseline at the left of the text string.  If you add these offsets 
     * to {@code (x, y)} and draw the string, then the anchor point should 
     * coincide with the {@code (x, y)} point.
     *
     * @param g2  the graphics device (not {@code null}).
     * @param text  the text.
     * @param anchor  the anchor point ({@code null} not permitted).
     *
     * @return  The offsets.
     */
    private static float[] deriveTextBoundsAnchorOffsets(Graphics2D g2,
            String text, TextAnchor anchor) {

        float[] result = new float[2];
        FontRenderContext frc = g2.getFontRenderContext();
        Font f = g2.getFont();
        FontMetrics fm = g2.getFontMetrics(f);
        Rectangle2D bounds = getTextBounds(text, fm);
        LineMetrics metrics = f.getLineMetrics(text, frc);
        float ascent = metrics.getAscent();
        float halfAscent = ascent / 2.0f;
        float descent = metrics.getDescent();
        float leading = metrics.getLeading();
        float xAdj = 0.0f;
        float yAdj = 0.0f;

        if (anchor.isHorizontalCenter()) {
            xAdj = (float) -bounds.getWidth() / 2.0f;
        } else if (anchor.isRight()) {
            xAdj = (float) -bounds.getWidth();
        }

        if (anchor.isTop()) {
            yAdj = -descent - leading + (float) bounds.getHeight();
        } else if (anchor.isHalfAscent()) {
            yAdj = halfAscent;
        } else if (anchor.isHalfHeight()) {
            yAdj = -descent - leading + (float) (bounds.getHeight() / 2.0);
        } else if (anchor.isBaseline()) {
            yAdj = 0.0f;
        } else if (anchor.isBottom()) {
            yAdj = -metrics.getDescent() - metrics.getLeading();
        }
        result[0] = xAdj;
        result[1] = yAdj;
        return result;
    }

    /**
     * A utility method that calculates the anchor offsets for a string.
     * Normally, the {@code (x, y)} coordinate for drawing text is a point on 
     * the baseline at the left of the text string.  If you add these offsets 
     * to {@code (x, y)} and draw the string, then the anchor point should 
     * coincide with the {@code (x, y)} point.
     *
     * @param g2  the graphics device (not {@code null}).
     * @param text  the text.
     * @param anchor  the anchor point ({@code null} not permitted).
     * @param textBounds  the text bounds (if not {@code null}, this
     *                    object will be updated by this method to match the
     *                    string bounds).
     *
     * @return  The offsets.
     */
    private static float[] deriveTextBoundsAnchorOffsets(Graphics2D g2,
            String text, TextAnchor anchor, Rectangle2D textBounds) {

        float[] result = new float[3];
        FontRenderContext frc = g2.getFontRenderContext();
        Font f = g2.getFont();
        FontMetrics fm = g2.getFontMetrics(f);
        Rectangle2D bounds = getTextBounds(text, fm);
        LineMetrics metrics = f.getLineMetrics(text, frc);
        float ascent = metrics.getAscent();
        result[2] = -ascent;
        float halfAscent = ascent / 2.0f;
        float descent = metrics.getDescent();
        float leading = metrics.getLeading();
        float xAdj = 0.0f;
        float yAdj = 0.0f;

        if (anchor.isHorizontalCenter()) {
            xAdj = (float) -bounds.getWidth() / 2.0f;
        } else if (anchor.isRight()) {
            xAdj = (float) -bounds.getWidth();
        }

        if (anchor.isTop()) {
            yAdj = -descent - leading + (float) bounds.getHeight();
        } else if (anchor.isHalfAscent()) {
            yAdj = halfAscent;
        } else if (anchor.isHorizontalCenter()) {
            yAdj = -descent - leading + (float) (bounds.getHeight() / 2.0);
        } else if (anchor.isBaseline()) {
            yAdj = 0.0f;
        } else if (anchor.isBottom()) {
            yAdj = -metrics.getDescent() - metrics.getLeading();
        }
        if (textBounds != null) {
            textBounds.setRect(bounds);
        }
        result[0] = xAdj;
        result[1] = yAdj;
        return result;
    }
    
    /**
     * Returns the bounds for the specified text.  The supplied text is
     * assumed to be on a single line (no carriage return or newline 
     * characters).
     *
     * @param text  the text ({@code null} not permitted).
     * @param fm  the font metrics ({@code null} not permitted).
     *
     * @return The text bounds.
     */
    public static Rectangle2D getTextBounds(String text, FontMetrics fm) {
        return getTextBounds(text, 0.0, 0.0, fm);
    }
    
    /**
     * Returns the bounds for the specified text when it is drawn with the 
     * left-baseline aligned to the point {@code (x, y)}.
     * 
     * @param text  the text ({@code null} not permitted).
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param fm  the font metrics ({@code null} not permitted).
     * 
     * @return The bounding rectangle (never {@code null}). 
     */
    public static Rectangle2D getTextBounds(String text, double x, double y,
            FontMetrics fm) {
        ArgChecks.nullNotPermitted(text, "text");
        ArgChecks.nullNotPermitted(fm, "fm");
        double width = fm.stringWidth(text);
        double height = fm.getHeight();
        return new Rectangle2D.Double(x, y - fm.getAscent(), width, height);
    }
    
    /**
     * Draws a string that is aligned by one anchor point and rotated about
     * another anchor point.
     *
     * @param text  the text ({@code null} not permitted).
     * @param g2  the graphics target ({@code null} not permitted).
     * @param x  the x-coordinate for positioning the text.
     * @param y  the y-coordinate for positioning the text.
     * @param textAnchor  the text anchor ({@code null} not permitted).
     * @param angle  the rotation angle.
     * @param rotationX  the x-coordinate for the rotation anchor point.
     * @param rotationY  the y-coordinate for the rotation anchor point.
     * 
     * @return The text bounds (never {@code null}).
     */
    public static Shape drawRotatedString(String text, Graphics2D g2, float x,
            float y, TextAnchor textAnchor, double angle,
            float rotationX, float rotationY) {
        ArgChecks.nullNotPermitted(text, "text");
        float[] textAdj = deriveTextBoundsAnchorOffsets(g2, text, textAnchor);
        return drawRotatedString(text, g2, x + textAdj[0], y + textAdj[1], 
                angle, rotationX, rotationY);
    }

    /**
     * Draws a string that is aligned by one anchor point and rotated about
     * another anchor point, and returns a bounding shape for the text.
     *
     * @param text  the text ({@code null} not permitted).
     * @param g2  the graphics device ({@code null} not permitted).
     * @param x  the x-coordinate for positioning the text.
     * @param y  the y-coordinate for positioning the text.
     * @param textAnchor  the text anchor ({@code null} not permitted).
     * @param angle  the rotation angle (in radians).
     * @param rotationAnchor  the rotation anchor ({@code null} not permitted).
     * 
     * @return A bounding shape for the text.
     */
    public static Shape drawRotatedString(String text, Graphics2D g2,
            float x, float y, TextAnchor textAnchor,
            double angle, TextAnchor rotationAnchor) {

        ArgChecks.nullNotPermitted(text, "text");
        float[] textAdj = deriveTextBoundsAnchorOffsets(g2, text, textAnchor);
        float[] rotateAdj = deriveRotationAnchorOffsets(g2, text,
                rotationAnchor);
        return drawRotatedString(text, g2, x + textAdj[0], y + textAdj[1],
                angle, x + textAdj[0] + rotateAdj[0],
                y + textAdj[1] + rotateAdj[1]);
    }

    /**
     * A utility method that calculates the rotation anchor offsets for a
     * string.  These offsets are relative to the text starting coordinate
     * ({@code BASELINE_LEFT}).
     *
     * @param g2  the graphics device ({@code null} not permitted).
     * @param text  the text ({@code null} not permitted).
     * @param anchor  the anchor point ({@code null} not permitted).
     *
     * @return  The offsets.
     */
    private static float[] deriveRotationAnchorOffsets(Graphics2D g2,
            String text, TextAnchor anchor) {

        float[] result = new float[2];
        FontRenderContext frc = g2.getFontRenderContext();
        LineMetrics metrics = g2.getFont().getLineMetrics(text, frc);
        FontMetrics fm = g2.getFontMetrics();
        Rectangle2D bounds = TextUtils.getTextBounds(text, fm);
        float ascent = metrics.getAscent();
        float halfAscent = ascent / 2.0f;
        float descent = metrics.getDescent();
        float leading = metrics.getLeading();
        float xAdj = 0.0f;
        float yAdj = 0.0f;

        if (anchor.isLeft()) {
            xAdj = 0.0f;
        } else if (anchor.isHorizontalCenter()) {
            xAdj = (float) bounds.getWidth() / 2.0f;
        } else if (anchor.isRight()) {
            xAdj = (float) bounds.getWidth();
        }

        if (anchor.isTop()) {
            yAdj = descent + leading - (float) bounds.getHeight();
        } else if (anchor.isHalfHeight()) {
            yAdj = descent + leading - (float) (bounds.getHeight() / 2.0);
        } else if (anchor.isHalfAscent()) {
            yAdj = -halfAscent;
        } else if (anchor.isBaseline()) {
            yAdj = 0.0f;
        } else if (anchor.isBottom()) {
            yAdj = metrics.getDescent() + metrics.getLeading();
        }
        result[0] = xAdj;
        result[1] = yAdj;
        return result;
    }
    
    /**
     * A utility method for drawing rotated text.
     * <P>
     * A common rotation is {@code -Math.PI/2} which draws text 'vertically' 
     * (with the top of the characters on the left).
     *
     * @param text  the text ({@code null} not permitted)
     * @param g2  the graphics target ({@code null} not permitted).
     * @param angle  the angle of the (clockwise) rotation (in radians).
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * 
     * @return The text bounds.
     */
    public static Shape drawRotatedString(String text, Graphics2D g2,
            double angle, float x, float y) {
        return drawRotatedString(text, g2, x, y, angle, x, y);
    }

    /**
     * A utility method for drawing rotated text.
     * <P>
     * A common rotation is {@code -Math.PI/2} which draws text 'vertically' 
     * (with the top of the characters on the left).
     *
     * @param text  the text ({@code null} not permitted).
     * @param g2  the graphics device ({@code null} not permitted).
     * @param textX  the x-coordinate for the text (before rotation).
     * @param textY  the y-coordinate for the text (before rotation).
     * @param angle  the angle of the (clockwise) rotation (in radians).
     * @param rotateX  the point about which the text is rotated.
     * @param rotateY  the point about which the text is rotated.
     * 
     * @return The bounds for the rotated text (never {@code null}).
     */
    public static Shape drawRotatedString(String text, Graphics2D g2,
            float textX, float textY, double angle,
            float rotateX, float rotateY) {
        ArgChecks.nullNotPermitted(text, "text");
        AffineTransform saved = g2.getTransform();
        Rectangle2D rect = TextUtils.getTextBounds(text, textX, textY, 
                g2.getFontMetrics());
        AffineTransform rotate = AffineTransform.getRotateInstance(
                angle, rotateX, rotateY);
        Shape bounds = rotate.createTransformedShape(rect);
        g2.transform(rotate);
        g2.drawString(text, textX, textY);
        g2.setTransform(saved);
        return bounds;
    }
    
    /**
     * Draws the attributed string at {@code (x, y)}, rotated by the 
     * specified angle about {@code (x, y)}.
     * 
     * @param text  the attributed string ({@code null} not permitted).
     * @param g2  the graphics output target ({@code null} not permitted).
     * @param angle  the angle.
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * 
     * @return The text bounds (never {@code null}).
     * 
     * @since 1.2
     */
    public static Shape drawRotatedString(AttributedString text, Graphics2D g2, 
            double angle, float x, float y) {
        return drawRotatedString(text, g2, x, y, angle, x, y);
    }
    
    /**
     * Draws the attributed string at {@code (textX, textY)}, rotated by 
     * the specified angle about {@code (rotateX, rotateY)}.
     * 
     * @param text  the attributed string ({@code null} not permitted).
     * @param g2  the graphics output target ({@code null} not permitted).
     * @param textX  the x-coordinate for the text alignment point.
     * @param textY  the y-coordinate for the text alignment point.
     * @param angle  the rotation angle (in radians).
     * @param rotateX  the x-coordinate for the rotation point.
     * @param rotateY  the y-coordinate for the rotation point.
     * 
     * @return The text bounds (never {@code null}).
     * 
     * @since 1.2
     */
    public static Shape drawRotatedString(AttributedString text, Graphics2D g2, 
            float textX, float textY, double angle, float rotateX, 
            float rotateY) {
        
        ArgChecks.nullNotPermitted(text, "text");
        AffineTransform saved = g2.getTransform();
        AffineTransform rotate = AffineTransform.getRotateInstance(angle, 
                rotateX, rotateY);
        g2.transform(rotate);
        TextLayout tl = new TextLayout(text.getIterator(),
                    g2.getFontRenderContext());
        Rectangle2D rect = tl.getBounds();
        tl.draw(g2, textX, textY);
        g2.setTransform(saved);
        return rotate.createTransformedShape(rect);
    }

    /**
     * Draws the attributed string aligned to the point {@code (x, y)}, 
     * rotated by the specified angle about {@code rotationAnchor}.
     * 
     * @param text  the attributed string ({@code null} not permitted).
     * @param g2  the graphics target ({@code null} not permitted).
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param textAnchor  the text anchor ({@code null} not permitted).
     * @param angle  the rotation angle (in radians).
     * @param rotationAnchor  the rotation anchor ({@code null} not 
     *     permitted).
     * @param nonRotatedBounds  if not {@code null} this rectangle will 
     *     be updated with the non-rotated bounds of the text for the caller
     *     to use.
     * 
     * @return The text bounds (never {@code null}).
     * 
     * @since 1.2
     */
    public static Shape drawRotatedString(AttributedString text, Graphics2D g2,
            float x, float y, TextAnchor textAnchor,
            double angle, TextAnchor rotationAnchor, 
            Rectangle2D nonRotatedBounds) {
        ArgChecks.nullNotPermitted(text, "text");
        float[] textAdj = deriveTextBoundsAnchorOffsets(g2, text, textAnchor, 
                nonRotatedBounds);
        float[] rotateAdj = deriveRotationAnchorOffsets(g2, text, 
                rotationAnchor);
        return drawRotatedString(text, g2, x + textAdj[0], y + textAdj[1],
                angle, x + textAdj[0] + rotateAdj[0],
                y + textAdj[1] + rotateAdj[1]);
    }

    /**
     * Calculates the x and y offsets required to align the text with the
     * specified {@code anchor}.
     * 
     * @param g2  the graphics target ({@code null} not permitted).
     * @param text  the text ({@code null} not permitted).
     * @param anchor  the anchor ({@code null} not permitted).
     * @param textBounds  if not {@code null}, this rectangle will be
     *     updated with the bounds of the text (for the caller to use).
     * 
     * @return An array of two floats dx and dy.
     */
    private static float[] deriveTextBoundsAnchorOffsets(Graphics2D g2,
            AttributedString text, TextAnchor anchor, Rectangle2D textBounds) {

        TextLayout layout = new TextLayout(text.getIterator(), 
                g2.getFontRenderContext());
        Rectangle2D bounds = layout.getBounds();

        float[] result = new float[3];
        float ascent = layout.getAscent();
        result[2] = -ascent;
        float halfAscent = ascent / 2.0f;
        float descent = layout.getDescent();
        float leading = layout.getLeading();
        float xAdj = 0.0f;
        float yAdj = 0.0f;
        
        if (anchor.isHorizontalCenter()) {
            xAdj = (float) -bounds.getWidth() / 2.0f;
        } else if (anchor.isRight()) {
            xAdj = (float) -bounds.getWidth();
        }

        if (anchor.isTop()) {
            yAdj = -descent - leading + (float) bounds.getHeight();
        } else if (anchor.isHalfAscent()) {
            yAdj = halfAscent;
        } else if (anchor.isHalfHeight()) {
            yAdj = -descent - leading + (float) (bounds.getHeight() / 2.0);
        } else if (anchor.isBaseline()) {
            yAdj = 0.0f;
        } else if (anchor.isBottom()) {
            yAdj = -descent - leading;
        }
        if (textBounds != null) {
            textBounds.setRect(bounds);
        }
        result[0] = xAdj;
        result[1] = yAdj;
        return result;
    }
    
    /**
     * A utility method that calculates the rotation anchor offsets for a
     * string.  These offsets are relative to the text starting coordinate
     * ({@code BASELINE_LEFT}).
     *
     * @param g2  the graphics device ({@code null} not permitted).
     * @param text  the text ({@code null} not permitted).
     * @param anchor  the anchor point ({@code null} not permitted).
     *
     * @return  The offsets.
     */
    private static float[] deriveRotationAnchorOffsets(Graphics2D g2, 
            AttributedString text, TextAnchor anchor) {

        float[] result = new float[2];
        
        TextLayout layout = new TextLayout(text.getIterator(), 
                g2.getFontRenderContext());
        Rectangle2D bounds = layout.getBounds();
        float ascent = layout.getAscent();
        float halfAscent = ascent / 2.0f;
        float descent = layout.getDescent();
        float leading = layout.getLeading();
        float xAdj = 0.0f;
        float yAdj = 0.0f;

        if (anchor.isLeft()) {
            xAdj = 0.0f;
        } else if (anchor.isHorizontalCenter()) {
            xAdj = (float) bounds.getWidth() / 2.0f;
        } else if (anchor.isRight()) {
            xAdj = (float) bounds.getWidth();
        }

        if (anchor.isTop()) {
            yAdj = descent + leading - (float) bounds.getHeight();
        } else if (anchor.isHalfHeight()) {
            yAdj = descent + leading - (float) (bounds.getHeight() / 2.0);
        } else if (anchor.isHalfAscent()) {
            yAdj = -halfAscent;
        } else if (anchor.isBaseline()) {
            yAdj = 0.0f;
        } else if (anchor.isBottom()) {
            yAdj = descent + leading;
        }
        result[0] = xAdj;
        result[1] = yAdj;
        return result;
    }

}


