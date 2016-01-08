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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.util.ObjectUtils;
import com.orsoncharts.util.RefPt2D;

/**
 * A base class that can be used to implement a {@link TableElement}.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 */
@SuppressWarnings("serial")
public abstract class AbstractTableElement implements Serializable {
    
    /** The default background color. */
    private static final Color DEFAULT_BACKGROUND_COLOR 
            = new Color(255, 255, 255, 127);
    
    /** The reference point used to align the element when rendering. */
    private RefPt2D refPt;
    
    /** The insets. */
    private Insets insets;
    
    /** The background paint (this can be {@code null}). */
    private RectanglePainter background;
    
    /** A tag that can be used to identify the class of element. */
    private String tag;
    
    /** Stores properties for the element. */
    private HashMap<String, Object> properties;
    
    /**
     * Creates a new instance.
     */
    public AbstractTableElement() {
        this.refPt = RefPt2D.CENTER;
        this.insets = new Insets(2, 2, 2, 2);
        this.background = new StandardRectanglePainter(
                DEFAULT_BACKGROUND_COLOR);
        this.tag = "";
        this.properties = new HashMap<String, Object>();
    }

    /**
     * Returns the anchor point used to align the element with the bounding
     * rectangle within which it is drawn.  The default value is 
     * {@link RefPt2D#CENTER}.
     * 
     * @return The anchor point (never {@code null}). 
     * 
     * @since 1.1
     */
    public RefPt2D getRefPoint() {
        return this.refPt;
    }
    
    /**
     * Sets the reference point.
     * 
     * @param refPt  the reference point ({@code null} not permitted).
     * 
     * @since 1.1
     */
    public void setRefPoint(RefPt2D refPt) {
        ArgChecks.nullNotPermitted(refPt, "refPt");
        this.refPt = refPt;
    }

    /**
     * Returns the insets.  The default value is {@code Insets(2, 2, 2, 2)}.
     * 
     * @return The insets (never {@code null}).
     */
    public Insets getInsets() {
        return this.insets;
    }
    
    /**
     * Sets the insets.
     * 
     * @param insets  the insets ({@code null} not permitted). 
     */
    public void setInsets(Insets insets) {
        ArgChecks.nullNotPermitted(insets, "insets");
        this.insets = insets;
    }

    /**
     * Returns the background painter for the element.
     * 
     * @return The background painter (possibly {@code null}). 
     */
    public RectanglePainter getBackground() {
        return this.background;
    }
    
    /**
     * Sets the background for the element.
     * 
     * @param background  the new background ({@code null} permitted).
     */
    public void setBackground(RectanglePainter background) {
        this.background = background;
    }
    
    /**
     * Sets the background painter to fill the element with the specified 
     * color.  If the color is {@code null}, the background painter will
     * be set to {@code null}.
     * 
     * @param color  the color ({@code null} permitted). 
     * 
     * @since 1.2
     */
    public void setBackgroundColor(Color color) {
        if (color != null) {
            this.background = new StandardRectanglePainter(color);
        } else {
            this.background = null;
        }
    }
    
    /**
     * Returns the tag for this element.  The default value is an empty string.
     * 
     * @return The tag (never {@code null}). 
     * 
     * @since 1.2
     */
    public String getTag() {
        return this.tag;
    }
    
    /**
     * Sets the tag.
     * 
     * @param tag  the tag ({@code null} not permitted).
     * 
     * @since 1.2
     */
    public void setTag(String tag) {
        ArgChecks.nullNotPermitted(tag, "tag");
        this.tag = tag;
    }
    
    /**
     * Returns the value of the property with the specified key, or 
     * {@code null}.
     * 
     * @param key  the key ({@code null} not permitted).
     * 
     * @return The property value or {@code null}. 
     * 
     * @since 1.3
     */
    public Object getProperty(String key) {
        return this.properties.get(key);
    }
    
    /**
     * Sets the value of the property with the specified key.
     * 
     * @param key  the key ({@code null} not permitted).
     * @param value  the value ({@code null} permitted).
     * 
     * @since 1.3
     */
    public void setProperty(String key, Object value) {
        ArgChecks.nullNotPermitted(key, "key");
        this.properties.put(key, value);
    }
    
    /**
     * Returns the preferred size of the element (including insets).
     * 
     * @param g2  the graphics target.
     * @param bounds  the bounds.
     * 
     * @return The preferred size. 
     */
    public Dimension2D preferredSize(Graphics2D g2, Rectangle2D bounds) {
        return preferredSize(g2, bounds, null);
    }

    /**
     * Returns the preferred size of the element (including insets).
     * 
     * @param g2  the graphics target.
     * @param bounds  the bounds.
     * @param constraints  the constraints (ignored for now).
     * 
     * @return The preferred size. 
     */
    public abstract Dimension2D preferredSize(Graphics2D g2, Rectangle2D bounds, 
            Map<String, Object> constraints);
    
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
        if (!(obj instanceof AbstractTableElement)) {
            return false;
        }
        AbstractTableElement that = (AbstractTableElement) obj;
        if (!this.insets.equals(that.insets)) {
            return false;
        }
        if (!ObjectUtils.equals(this.background, that.background)) {
            return false;
        }
        return true;
    }
   
}
