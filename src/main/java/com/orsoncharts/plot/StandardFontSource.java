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

package com.orsoncharts.plot;

import java.awt.Font;
import java.io.Serializable;

import com.orsoncharts.data.DefaultKeyedValues;
import com.orsoncharts.util.ArgChecks;

/**
 * A standard implementation of the {@link FontSource} interface.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 */
@SuppressWarnings("serial")
public final class StandardFontSource<K extends Comparable<K>> 
        implements FontSource<K>, Serializable {

    private static Font DEFAULT_FONT = new Font("Dialog", Font.PLAIN, 12);
    
    /** Storage for the fonts assigned to keys. */
    private DefaultKeyedValues<K, Font> fonts;

    /** The default font (never {@code null}). */
    private Font defaultFont;
    
    /**
     * Creates a new instance with default fonts.
     */
    public StandardFontSource() {
        this(DEFAULT_FONT);
    }
    
    /**
     * Creates a new font source with the specified default font.
     * 
     * @param defaultFont  the default font ({@code null} not permitted). 
     */
    public StandardFontSource(Font defaultFont) {
        ArgChecks.nullNotPermitted(defaultFont, "defaultFont");
        this.defaultFont = defaultFont;
        this.fonts = new DefaultKeyedValues<K, Font>();
    }
    
    /**
     * Returns the default font.  The default value is {@link #DEFAULT_FONT}.
     * 
     * @return The default font (never {@code null}). 
     */
    public Font getDefaultFont() {
        return this.defaultFont;
    }
    
    /**
     * Sets the default font.
     * 
     * @param font  the font ({@code null} not permitted).
     */
    public void setDefaultFont(Font font) {
        ArgChecks.nullNotPermitted(font, "font");
        this.defaultFont = font;
    }
    
    /**
     * Returns the font for the specified key.
     * 
     * @param key  the key ({@code null} not permitted).
     * 
     * @return The font (never {@code null}). 
     */
    @Override
    public Font getFont(K key) {
        Font result = this.fonts.getValue(key);
        if (result != null) {
            return result;
        } else {
            return this.defaultFont;
        }
    }
    
    /**
     * Sets the font associated with the specified key.
     * 
     * @param key  the key ({@code null} not permitted).
     * @param font  the font ({@code null} permitted).
     */
    @Override
    public void setFont(K key, Font font) {
        if (font != null) {
            this.fonts.put(key, font);
        } else {
            this.fonts.remove(key);
        }
    }
    
    /**
     * Clears existing font settings and sets the default font to the 
     * supplied value.  This method is used by the framework and is not
     * normally called by client code.
     * 
     * @param font  the font ({@code null} not permitted).
     * 
     * @since 1.2
     */
    @Override
    public void style(Font font) {
        ArgChecks.nullNotPermitted(font, "font");
        this.defaultFont = font;
        this.fonts.clear();
    }

    /**
     * Tests this paint source for equality with an arbitrary object.
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
        if (!(obj instanceof StandardFontSource)) {
            return false;
        }
        StandardFontSource that = (StandardFontSource) obj;
        if (!this.defaultFont.equals(that.defaultFont)) {
            return false;
        }
        if (!this.fonts.equals(that.fonts)) {
            return false;
        }
        return true;
    }

}