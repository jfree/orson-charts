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
        DefaultKeyedValues<String> kv1 = new DefaultKeyedValues<String>();
        assertNull(kv1.getValue("ABC"));
    }
    
    @Test
    public void testEquals() {
        DefaultKeyedValues<Number> kv1 = new DefaultKeyedValues<Number>();
        DefaultKeyedValues<Number> kv2 = new DefaultKeyedValues<Number>();
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
    public void testSerialization() {
        DefaultKeyedValues<Number> kv1 = new DefaultKeyedValues<Number>();
        kv1.put("A", 1.0);
        kv1.put("B", 2.0);
        kv1.put("C", null);
        DefaultKeyedValues<Number> kv2 = (DefaultKeyedValues<Number>) 
                TestUtils.serialized(kv1);
        assertTrue(kv1.equals(kv2));
    }

}
