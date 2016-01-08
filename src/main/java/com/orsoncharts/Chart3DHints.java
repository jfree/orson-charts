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

package com.orsoncharts;

import java.util.Map;

/**
 * Special rendering hints that used internally by Orson Charts to provide
 * links between rendered items and the chart elements that they represent.
 * Most {@code Graphics2D} implementations will ignore these hints but
 * some, for example the {@code SVGGraphics2D} class in 
 * <a href="http://www.jfree.org/jfreesvg">JFreeSVG</a>, will use the hints 
 * to drive the output content.
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
         * Returns {@code true} if {@code val} is a value that is
         * compatible with this key, and {@code false} otherwise.
         * 
         * @param val  the value ({@code null} permitted).
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
