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
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import com.orsoncharts.TestUtils;

/**
 * Tests for the {@link KeyedValuesItemKey} class.
 */
public class KeyedValuesItemKeyTest {
    
    @Test
    public void testEquals() {
        KeyedValuesItemKey k1 = new KeyedValuesItemKey("A");
        KeyedValuesItemKey k2 = new KeyedValuesItemKey("A");
        assertTrue(k1.equals(k2));
        assertFalse(k1.equals(null));
        
        k1 = new KeyedValuesItemKey("AA");
        assertFalse(k1.equals(k2));
        k2 = new KeyedValuesItemKey("AA");
        assertTrue(k1.equals(k2));
    }
    
    @Test
    public void testSerialization() {
        KeyedValuesItemKey k1 = new KeyedValuesItemKey("A");
        KeyedValuesItemKey k2 = (KeyedValuesItemKey) 
                TestUtils.serialized(k1);
        assertTrue(k1.equals(k2));
    }
    
}
