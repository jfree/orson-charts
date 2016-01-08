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

import java.awt.Shape;
import java.util.HashMap;
import java.util.Map;
import com.orsoncharts.util.ArgChecks;

/**
 * Represents an item or element that has been rendered by the graphics engine.
 * Properties can be assigned to the elements in order to provide the 
 * information required for such things as end user interactivity.
 * 
 * @since 1.3
 */
public class RenderedElement {
    
    /** A key for the 'type' property.  The value is an Object. */
    public static final String TYPE = "type";

    /** 
     * A key for the 'bounds' property (the value is a {@code Shape}). 
     */
    public static final String BOUNDS = "bounds";
    
    /** Properties for the element. */
    private final Map<String, Object> properties;
    
    /**
     * Creates a new interactive element with the specified type.
     * 
     * @param type  the type ({@code null} not permitted). 
     * @param bounds  the bounds ({@code null} permitted).
     */
    public RenderedElement(Object type, Shape bounds) {
        ArgChecks.nullNotPermitted(type, "type");
        this.properties = new HashMap<String, Object>();
        this.properties.put(TYPE, type);
        this.properties.put(RenderedElement.BOUNDS, bounds);
    }

    /**
     * Returns the element type, as specified in the constructor.
     * 
     * @return The element type. 
     */
    public Object getType() {
        return this.properties.get(TYPE);
    }

    /**
     * Returns the value of the property with the specified key, or 
     * {@code null}).
     * 
     * @param key  the key ({@code null} not permitted).
     * 
     * @return The property value. 
     */
    public Object getProperty(String key) {
        return this.properties.get(key);
    }
    
    /**
     * Sets the value for a property.
     * 
     * @param key  the property key ({@code null} not permitted).
     * @param value  the property value.
     */
    public void setProperty(String key, Object value) {
        this.properties.put(key, value);
    }
    
    /**
     * Clears all properties for this element.
     */
    public void clearProperties() {
        this.properties.clear();
    }
    
    /**
     * Returns a string representation of the element, primarily for 
     * debugging purposes.
     * 
     * @return A string. 
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object type = this.properties.get(TYPE);
        sb.append("RenderedElement[").append(type).append(",")
                .append(this.properties.entrySet()).append("]");
        return sb.toString();
    }
    
}
