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

package com.orsoncharts.data;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import com.orsoncharts.TestUtils;

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
        DefaultKeyedValues<String, String> kv1 = new DefaultKeyedValues<String,
                String>();
        assertNull(kv1.getValue("ABC"));
    }
    
    @Test
    public void testEquals() {
        DefaultKeyedValues<String, Number> kv1 = new DefaultKeyedValues<String, 
                Number>();
        DefaultKeyedValues<String, Number> kv2 = new DefaultKeyedValues<String, 
                Number>();
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
        DefaultKeyedValues<String, Number> kv1 = new DefaultKeyedValues<String, 
                Number>();
        kv1.put("A", 1.0);
        kv1.put("B", 2.0);
        kv1.put("C", null);
        DefaultKeyedValues<String, Number> kv2 = (DefaultKeyedValues<String, 
                Number>) 
        TestUtils.serialized(kv1);
        assertTrue(kv1.equals(kv2));
    }

}
