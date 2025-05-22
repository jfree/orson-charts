/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-present, by David Gilbert.  All rights reserved.
 * 
 * https://github.com/jfree/orson-charts
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
 * license is available to sponsors (higher tiers only) of the JFree projects.
 * For details, please see visit:
 *
 * https://github.com/sponsors/jfree
 * 
 */

package org.jfree.chart3d.data;

import org.jfree.chart3d.TestUtils;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Some checks for the {@link DefaultKeyedValue} class.
 */
public class DefaultKeyedValueTest {
    
    @Test
    public void testEquals() {
        DefaultKeyedValue<String, Number> kv1 = new DefaultKeyedValue<>("K1", Double.valueOf(1.0));
        DefaultKeyedValue<String, Number> kv2 = new DefaultKeyedValue<>("K1", 1.0);
        assertEquals(kv1, kv2);
        assertFalse(kv1.equals(null));
        
        kv1 = new DefaultKeyedValue<>("K2", 1.0);
        assertFalse(kv1.equals(kv2));
        kv2 = new DefaultKeyedValue<>("K2", 1.0);
        assertTrue(kv1.equals(kv2));
       
        kv1 = new DefaultKeyedValue<>("K2", 2.0);
        assertFalse(kv1.equals(kv2));
        kv2 = new DefaultKeyedValue<>("K2", 2.0);
        assertTrue(kv1.equals(kv2));
        
        kv1 = new DefaultKeyedValue<>("K2", null);
        assertFalse(kv1.equals(kv2));
        kv2 = new DefaultKeyedValue<>("K2", null);
        assertTrue(kv1.equals(kv2));
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void testSerialization() {
        DefaultKeyedValue<String, Number> kv1 = new DefaultKeyedValue<>("K1", 1.0);
        DefaultKeyedValue<String, Number> kv2 = (DefaultKeyedValue<String, 
                Number>) TestUtils.serialized(kv1);
        assertTrue(kv1.equals(kv2));
        
        kv1 = new DefaultKeyedValue<>("K1", null);
        kv2 = (DefaultKeyedValue<String, Number>) TestUtils.serialized(kv1);
        assertTrue(kv1.equals(kv2));
    }
    
}
