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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.awt.Stroke;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Serialization support methods.
 */
public class SerialUtils {
    
    private SerialUtils() {
        // no need to instantiate this class
    }
    
    /**
     * Reads a {@code Paint} object that has been serialized by the
     * {@link SerialUtils#writePaint(Paint, ObjectOutputStream)} method.
     *
     * @param stream  the input stream ({@code null} not permitted).
     *
     * @return The paint object (possibly {@code null}).
     *
     * @throws IOException  if there is an I/O problem.
     * @throws ClassNotFoundException  if there is a problem loading a class.
     */
    public static Paint readPaint(ObjectInputStream stream) throws IOException,
            ClassNotFoundException {

        ArgChecks.nullNotPermitted(stream, "stream");
        Paint result = null;
        boolean isNull = stream.readBoolean();
        if (!isNull) {
            Class<?> c = (Class<?>) stream.readObject();
            if (Serializable.class.isAssignableFrom(c)) {
                result = (Paint) stream.readObject();
            } else if (c.equals(GradientPaint.class)) {
                float x1 = stream.readFloat();
                float y1 = stream.readFloat();
                Color c1 = (Color) stream.readObject();
                float x2 = stream.readFloat();
                float y2 = stream.readFloat();
                Color c2 = (Color) stream.readObject();
                boolean isCyclic = stream.readBoolean();
                result = new GradientPaint(x1, y1, c1, x2, y2, c2, isCyclic);
            }
        }
        return result;
    }

    /**
     * Serializes a {@code Paint} object.
     *
     * @param paint  the paint object ({@code null} permitted).
     * @param stream  the output stream ({@code null} not permitted).
     *
     * @throws IOException if there is an I/O error.
     */
    public static void writePaint(Paint paint, ObjectOutputStream stream)
        throws IOException {

        ArgChecks.nullNotPermitted(stream, "stream");
        if (paint != null) {
            stream.writeBoolean(false);
            stream.writeObject(paint.getClass());
            if (paint instanceof Serializable) {
                stream.writeObject(paint);
            } else if (paint instanceof GradientPaint) {
                GradientPaint gp = (GradientPaint) paint;
                stream.writeFloat((float) gp.getPoint1().getX());
                stream.writeFloat((float) gp.getPoint1().getY());
                stream.writeObject(gp.getColor1());
                stream.writeFloat((float) gp.getPoint2().getX());
                stream.writeFloat((float) gp.getPoint2().getY());
                stream.writeObject(gp.getColor2());
                stream.writeBoolean(gp.isCyclic());
            }
        } else {
            stream.writeBoolean(true);
        }
    }

    /**
     * Reads a {@code Stroke} object that has been serialized by the
     * {@link SerialUtils#writeStroke(Stroke, ObjectOutputStream)} method.
     *
     * @param stream  the input stream ({@code null} not permitted).
     *
     * @return The stroke object (possibly {@code null}).
     *
     * @throws IOException  if there is an I/O problem.
     * @throws ClassNotFoundException  if there is a problem loading a class.
     */
    public static Stroke readStroke(ObjectInputStream stream)
        throws IOException, ClassNotFoundException {

        ArgChecks.nullNotPermitted(stream, "stream");
        Stroke result = null;
        boolean isNull = stream.readBoolean();
        if (!isNull) {
            Class<?> c = (Class<?>) stream.readObject();
            if (c.equals(BasicStroke.class)) {
                float width = stream.readFloat();
                int cap = stream.readInt();
                int join = stream.readInt();
                float miterLimit = stream.readFloat();
                float[] dash = (float[]) stream.readObject();
                float dashPhase = stream.readFloat();
                result = new BasicStroke(width, cap, join, miterLimit, dash, 
                        dashPhase);
            } else {
                result = (Stroke) stream.readObject();
            }
        }
        return result;
    }

    /**
     * Serializes a {@code Stroke} object.  This code handles the
     * {@code BasicStroke} class which is the only {@code Stroke}
     * implementation provided by the JDK (and isn't directly
     * {@code Serializable}).
     *
     * @param stroke  the stroke object ({@code null} permitted).
     * @param stream  the output stream ({@code null} not permitted).
     *
     * @throws IOException if there is an I/O error.
     */
    public static void writeStroke(Stroke stroke, ObjectOutputStream stream)
            throws IOException {

        ArgChecks.nullNotPermitted(stream, "stream");
        if (stroke != null) {
            stream.writeBoolean(false);
            if (stroke instanceof BasicStroke) {
                BasicStroke s = (BasicStroke) stroke;
                stream.writeObject(BasicStroke.class);
                stream.writeFloat(s.getLineWidth());
                stream.writeInt(s.getEndCap());
                stream.writeInt(s.getLineJoin());
                stream.writeFloat(s.getMiterLimit());
                stream.writeObject(s.getDashArray());
                stream.writeFloat(s.getDashPhase());
            } else {
                stream.writeObject(stroke.getClass());
                stream.writeObject(stroke);
            }
        } else {
            stream.writeBoolean(true);
        }
    }

}
