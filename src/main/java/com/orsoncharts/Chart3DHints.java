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

package com.orsoncharts;

import java.util.Map;

/**
 * Special rendering hints that used internally by Orson Charts to provide
 * links between rendered items and the chart elements that they represent.
 * Most <code>Graphics2D</code> implementations will ignore these hints but
 * some, for example JFreeSVG's SVGGraphics2D class, will use the hints to
 * drive the output content.
 * 
 * @since 1.3
 */
public final class Chart3DHints {

    private Chart3DHints() {
        // no need to instantiate this    
    }
    
    /**
     * The key for a hint to signal the beginning of an element.  The value
     * should be a string containing the element id or, alternatively, a Map 
     * containing the 'id' (String) and 'ref' (String in JSON format).
     */
    public static final Key KEY_BEGIN_ELEMENT = new Chart3DHints.Key(0);
    
    /**
     * The key for a hint that ends an element.
     */
    public static final Key KEY_END_ELEMENT = new Chart3DHints.Key(1);
    
    /**
     * A key for rendering hints that can be used with Orson Charts (in 
     * addition to the regular Java2D rendering hints).
     */
    public static class Key extends java.awt.RenderingHints.Key {

        /**
         * Creates a new key.
         * 
         * @param privateKey  the private key. 
         */
        public Key(int privateKey) {
            super(privateKey);    
        }
    
        /**
         * Returns <code>true</code> if <code>val</code> is a value that is
         * compatible with this key, and <code>false</code> otherwise.
         * 
         * @param val  the value.
         * 
         * @return A boolean. 
         */
        @Override
        public boolean isCompatibleValue(Object val) {
            switch (intKey()) {
                case 0:
                    return val == null || val instanceof String 
                            || val instanceof Map;
                case 1:
                    return val == null || val instanceof Object;
                default:
                    throw new RuntimeException("Not possible!");
            }
        }
    }
    
}
