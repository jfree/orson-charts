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

package com.orsoncharts.graphics3d;

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
     * A key for the 'bounds2d' property (the value is a 
     * <code>Rectangle2D</code>). 
     */
    public static final String BOUNDS_2D = "bounds2d";
    
    /** Properties for the element. */
    private Map<String, Object> properties;
    
    /**
     * Creates a new interactive element with the specified type.
     * 
     * @param type  the type (<code>null</code> not permitted). 
     */
    public RenderedElement(Object type) {
        ArgChecks.nullNotPermitted(type, "type");
        this.properties = new HashMap<String, Object>();
        this.properties.put(TYPE, type);
    }
    
    /**
     * Returns the value of the property with the specified key, or 
     * <code>null</code>).
     * 
     * @param key  the key (<code>null</code> not permitted).
     * 
     * @return The property value. 
     */
    public Object getProperty(String key) {
        return this.properties.get(key);
    }
    
    /**
     * Sets the value for a property.
     * 
     * @param key  the property key (<code>null</code> not permitted).
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
        sb.append("RenderedElement[").append(type).append("]");
        return sb.toString();
    }
    
}
