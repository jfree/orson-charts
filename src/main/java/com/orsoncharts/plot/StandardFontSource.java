/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
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
public final class StandardFontSource implements FontSource, Serializable {

    private static Font DEFAULT_FONT = new Font("Dialog", Font.PLAIN, 12);
    
    /** Storage for the fonts assigned to keys. */
    private DefaultKeyedValues<Font> fonts;

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
    
    public Font getDefaultFont() {
        return this.defaultFont;
    }
    
    public void setDefaultFont(Font font) {
        this.defaultFont = font;
    }
    
    @Override
    public Font getFont(Comparable key) {
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
    public void setFont(Comparable key, Font font) {
        if (font != null) {
            this.fonts.put(key, font);
        } else {
            this.fonts.remove(key);
        }
    }

    public void put(Comparable key, Font font) {
        if (font != null) {
            this.fonts.put(key, font);
        } else {
            this.fonts.remove(key);        
        }
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