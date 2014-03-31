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
 * Tests for the {@link KeyedValues3DItemKey} class.
 */
public class KeyedValues3DItemKeyTest {
    
    @Test
    public void testEquals() {
        KeyedValues3DItemKey k1 = new KeyedValues3DItemKey("A", "B", "C");
        KeyedValues3DItemKey k2 = new KeyedValues3DItemKey("A", "B", "C");
        assertTrue(k1.equals(k2));
        assertFalse(k1.equals(null));
        
        k1 = new KeyedValues3DItemKey("AA", "B", "C");
        assertFalse(k1.equals(k2));
        k2 = new KeyedValues3DItemKey("AA", "B", "C");
        assertTrue(k1.equals(k2));

        k1 = new KeyedValues3DItemKey("AA", "BB", "C");
        assertFalse(k1.equals(k2));
        k2 = new KeyedValues3DItemKey("AA", "BB", "C");
        assertTrue(k1.equals(k2));

        k1 = new KeyedValues3DItemKey("AA", "BB", "CC");
        assertFalse(k1.equals(k2));
        k2 = new KeyedValues3DItemKey("AA", "BB", "CC");
        assertTrue(k1.equals(k2));
    }
    
    @Test
    public void testSerialization() {
        KeyedValues3DItemKey k1 = new KeyedValues3DItemKey("A", "B", "C");
        KeyedValues3DItemKey k2 = (KeyedValues3DItemKey) 
                TestUtils.serialized(k1);
        assertTrue(k1.equals(k2));
    }
}
