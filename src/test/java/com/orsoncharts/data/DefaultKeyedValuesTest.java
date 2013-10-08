/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.data;

import com.orsoncharts.TestUtils;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests for the {@link DefaultKeyedValues} class.
 */
public class DefaultKeyedValuesTest {
    
    @Test
    public void testEquals() {
        DefaultKeyedValues<Number> kv1 = new DefaultKeyedValues<Number>();
        DefaultKeyedValues<Number> kv2 = new DefaultKeyedValues<Number>();
        assertTrue(kv1.equals(kv2));
        assertFalse(kv1.equals(null));
        
        kv1.addValue("A", 1.0);
        assertFalse(kv1.equals(kv2));
        kv2.addValue("A", 1.0);
        assertTrue(kv1.equals(kv2));
        
        kv1.addValue("B", null);
        assertFalse(kv1.equals(kv2));
        kv2.addValue("B", null);
        assertTrue(kv1.equals(kv2));
    }
    
    @Test
    public void testSerialization() {
        DefaultKeyedValues<Number> kv1 = new DefaultKeyedValues<Number>();
        kv1.addValue("A", 1.0);
        kv1.addValue("B", 2.0);
        kv1.addValue("C", null);
        DefaultKeyedValues<Number> kv2 = (DefaultKeyedValues<Number>) 
                TestUtils.serialized(kv1);
        assertTrue(kv1.equals(kv2));
    }

}
