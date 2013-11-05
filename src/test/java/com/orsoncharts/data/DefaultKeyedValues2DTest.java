/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.data;

import com.orsoncharts.TestUtils;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Some checks for the {@link DefaultKeyedValues2D} class.
 */
public class DefaultKeyedValues2DTest {
    
    private static final double EPSILON = 0.00000001;
    
    @Test
    public void testSetValue() {
        
        // empty data
        DefaultKeyedValues2D<Number> data = new DefaultKeyedValues2D<Number>();
        data.setValue(1.0, "X1", "Y1");
        assertEquals(1.0, data.getValue("X1", "Y1").doubleValue(), EPSILON);
        assertEquals(1, data.getXCount());
        assertEquals(1, data.getYCount());
        
        // existing keys
        data.setValue(2.0, "X1", "Y1");
        assertEquals(2.0, data.getValue("X1", "Y1").doubleValue(), EPSILON);
        assertEquals(1, data.getXCount());
        assertEquals(1, data.getYCount());

        // existing x-key, new y-key
        data.setValue(3.0, "X1", "Y2");
        assertEquals(3.0, data.getValue("X1", "Y2").doubleValue(), EPSILON);
        assertEquals(1, data.getXCount());
        assertEquals(2, data.getYCount());
        
        // existing y-key, new x-key
        data.setValue(4.0, "X2", "Y2");
        assertEquals(4.0, data.getValue("X2", "Y2").doubleValue(), EPSILON);
        assertEquals(2, data.getXCount());
        assertEquals(2, data.getYCount());
        assertNull(data.getValue("X2", "Y1"));
        
        // new x-key and new y-key
        data.setValue(5.0, "X3", "Y3");
        assertEquals(5.0, data.getValue("X3", "Y3").doubleValue(), EPSILON);
        assertEquals(3, data.getXCount());
        assertEquals(3, data.getYCount());
        assertNull(data.getValue("X1", "Y3"));
    }
    
    @Test
    public void testEquals() {
        DefaultKeyedValues2D<Number> d1 = new DefaultKeyedValues2D<Number>();
        DefaultKeyedValues2D<Number> d2 = new DefaultKeyedValues2D<Number>();
        assertTrue(d1.equals(d2));
        assertFalse(d1.equals(null));
        
        d1.setValue(1.0, "X1", "Y1");
        assertFalse(d1.equals(d2));
        d2.setValue(1.0, "X1", "Y1");
        assertTrue(d1.equals(d2));
    }
    
    /**
     * Check for serialization support.
     */
    @Test
    public void testSerialization() {
        DefaultKeyedValues2D<Number> d1 = new DefaultKeyedValues2D<Number>();
        d1.setValue(1.0, "R1", "C1");
        DefaultKeyedValues2D<Number> d2 
                = (DefaultKeyedValues2D<Number>) TestUtils.serialized(d1);
        assertEquals(d1, d2);
    }
    
}
