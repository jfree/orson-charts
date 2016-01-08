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

import java.awt.Color;
import java.awt.Font;
import com.orsoncharts.util.ArgChecks;

/**
 * A face that carries a label (and is itself invisible).
 * 
 * @since 1.3
 */
public class LabelFace extends Face {
    
    /** A transparent color. */
    private static final Color TRANSPARENT = new Color(0, 0, 0, 0);

    /** The label. */
    private String label;
    
    /** The font for the label. */
    private Font font;
    
    /** The foreground color for the text. */
    private Color textColor;
    
    /** The background color. */
    private Color backgroundColor;
    
    /**
     * Creates a new instance.
     * 
     * @param owner  the object that this face belongs to ({@code null} 
     *     not permitted).
     * @param vertices  the vertices that define the face ({@code null} 
     *     not permitted).
     * @param label  the label ({@code null} not permitted).
     * @param font  the font ({@code null} not permitted).
     * @param textColor  the foreground color ({@code null} not permitted).
     * @param backgroundColor   the background color for the label 
     *     ({@code null} not permitted).
     */
    public LabelFace(Object3D owner, int[] vertices, String label, Font font, 
            Color textColor, Color backgroundColor) {
        super(owner, vertices);
        ArgChecks.nullNotPermitted(label, "label");
        ArgChecks.nullNotPermitted(font, "font");
        ArgChecks.nullNotPermitted(textColor, "textColor");
        ArgChecks.nullNotPermitted(backgroundColor, "backgroundColor");
        this.label = label;
        this.font = font;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
    }

    /**
     * Returns a transparent color, so that the face is not visible.
     * 
     * @return A transparent color. 
     */
    @Override
    public Color getColor() {
        return TRANSPARENT;
    }

    /**
     * Returns the label.
     * 
     * @return The label (never {@code null}). 
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * Sets the label.
     * 
     * @param label  the new label ({@code null} not permitted). 
     */
    public void setLabel(String label) {
        ArgChecks.nullNotPermitted(label, "label");
        this.label = label;
    }

    /**
     * Returns the font.
     * 
     * @return The font (never {@code null}). 
     */
    public Font getFont() {
        return this.font;
    }

    /**
     * Sets the font.
     * 
     * @param font  the font ({@code null} not permitted). 
     */
    public void setFont(Font font) {
        ArgChecks.nullNotPermitted(font, "font");
        this.font = font;
    }

    /**
     * Returns the foreground color for the label text.
     * 
     * @return The foreground color (never {@code null}). 
     */
    public Color getTextColor() {
        return this.textColor;
    }

    /**
     * Sets the foreground color for the label text.
     * 
     * @param color  the color ({@code null} not permitted).
     */
    public void setTextColor(Color color) {
        this.textColor = color;
    }

    /**
     * Returns the background color.  The default value is a fully transparent
     * color.
     * 
     * @return The background color (never {@code null}). 
     */
    public Color getBackgroundColor() {
        return this.backgroundColor;
    }

    /**
     * Sets the background color.
     * 
     * @param color  the color ({@code null} not permitted). 
     */
    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
    }

}
