/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */
package com.orsoncharts.data;

import com.orsoncharts.TestUtils;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Some checks for the {@link DefaultKeyedValue} class.
 */
public class DefaultKeyedValueTest {
    
    @Test
    public void testEquals() {
        DefaultKeyedValue<Number> kv1 = new DefaultKeyedValue<Number>("K1", 
                Double.valueOf(1.0));
        DefaultKeyedValue<Number> kv2 = new DefaultKeyedValue<Number>("K1", 
                Double.valueOf(1.0));
        assertEquals(kv1, kv2);
        assertFalse(kv1.equals(null));
        
        kv1 = new DefaultKeyedValue<Number>("K2", Double.valueOf(1.0));
        assertFalse(kv1.equals(kv2));
        kv2 = new DefaultKeyedValue<Number>("K2", Double.valueOf(1.0));
        assertTrue(kv1.equals(kv2));
       
        kv1 = new DefaultKeyedValue<Number>("K2", Double.valueOf(2.0));
        assertFalse(kv1.equals(kv2));
        kv2 = new DefaultKeyedValue<Number>("K2", Double.valueOf(2.0));
        assertTrue(kv1.equals(kv2));
        
        kv1 = new DefaultKeyedValue<Number>("K2", null);
        assertFalse(kv1.equals(kv2));
        kv2 = new DefaultKeyedValue<Number>("K2", null);
        assertTrue(kv1.equals(kv2));
    }
    
    @Test
    public void testSerialization() {
        DefaultKeyedValue<Number> kv1 = new DefaultKeyedValue<Number>("K1", 
                1.0);
        DefaultKeyedValue<Number> kv2 = (DefaultKeyedValue<Number>) 
                TestUtils.serialized(kv1);
        assertTrue(kv1.equals(kv2));
        
        kv1 = new DefaultKeyedValue<Number>("K1", null);
        kv2 = (DefaultKeyedValue<Number>) TestUtils.serialized(kv1);
        assertTrue(kv1.equals(kv2));
    }
    
}
