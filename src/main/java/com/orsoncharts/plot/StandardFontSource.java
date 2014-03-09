/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of this source file is prohibited.
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
public final class StandardFontSource implements FontSource, Serializable {

    private static Font DEFAULT_FONT = new Font("Dialog", Font.PLAIN, 12);
    
    /** Storage for the fonts assigned to keys. */
    private DefaultKeyedValues<Font> fonts;

    /** The default font (never <code>null</code>). */
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
     * @param defaultFont  the default font (<code>null</code> not permitted). 
     */
    public StandardFontSource(Font defaultFont) {
        ArgChecks.nullNotPermitted(defaultFont, "defaultFont");
        this.defaultFont = defaultFont;
        this.fonts = new DefaultKeyedValues<Font>();
    }
    
    /**
     * Returns the default font.  The default value is {@link #DEFAULT_FONT}.
     * 
     * @return The default font (never <code>null</code>). 
     */
    public Font getDefaultFont() {
        return this.defaultFont;
    }
    
    /**
     * Sets the default font.
     * 
     * @param font  the font (<code>null</code> not permitted).
     */
    public void setDefaultFont(Font font) {
        ArgChecks.nullNotPermitted(font, "font");
        this.defaultFont = font;
    }
    
    /**
     * Returns the font for the specified key.
     * 
     * @param key  the key (<code>null</code> not permitted).
     * 
     * @return The font (never <code>null</code>). 
     */
    @Override
    public Font getFont(Comparable<?> key) {
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
     * @param key  the key (<code>null</code> not permitted).
     * @param font  the font (<code>null</code> permitted).
     */
    @Override
    public void setFont(Comparable<?> key, Font font) {
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
     * @param font  the font (<code>null</code> not permitted).
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
     * @param obj  the object (<code>null</code> permitted).
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