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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.jfree.chart3d.TestUtils;

/**
 * Tests for the {@link DefaultKeyedValues} class.
 */
public class DefaultKeyedValuesTest {
    
    /**
     * Check for getValue(key) where key is not in the data structure (was
     * failing at one time).
     */
    @Test
    public void testGetValueForNonExistentKey() {
        DefaultKeyedValues<String, String> kv1 = new DefaultKeyedValues<>();
        assertNull(kv1.getValue("ABC"));
    }
    
    @Test
    public void testEquals() {
        DefaultKeyedValues<String, Number> kv1 = new DefaultKeyedValues<>();
        DefaultKeyedValues<String, Number> kv2 = new DefaultKeyedValues<>();
        assertTrue(kv1.equals(kv2));
        assertFalse(kv1.equals(null));
        
        kv1.put("A", 1.0);
        assertFalse(kv1.equals(kv2));
        kv2.put("A", 1.0);
        assertTrue(kv1.equals(kv2));
        
        kv1.put("B", null);
        assertFalse(kv1.equals(kv2));
        kv2.put("B", null);
        assertTrue(kv1.equals(kv2));
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void testSerialization() {
        DefaultKeyedValues<String, Number> kv1 = new DefaultKeyedValues<>();
        kv1.put("A", 1.0);
        kv1.put("B", 2.0);
        kv1.put("C", null);
        DefaultKeyedValues<String, Number> kv2 = (DefaultKeyedValues<String, 
                Number>) TestUtils.serialized(kv1);
        assertTrue(kv1.equals(kv2));
    }

}
