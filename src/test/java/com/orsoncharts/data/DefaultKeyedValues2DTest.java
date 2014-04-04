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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import com.orsoncharts.TestUtils;
import com.orsoncharts.data.category.StandardCategoryDataset3D;

/**
 * Some checks for the {@link DefaultKeyedValues2D} class.
 */
public class DefaultKeyedValues2DTest {
    
    private static final double EPSILON = 0.00000001;
    
    @Test
    public void testGetValue() {
        DefaultKeyedValues2D<Number> kv2d = new DefaultKeyedValues2D<Number>();
        kv2d.setValue(1.0, "R1", "C1");
        kv2d.setValue(2.0, "R1", "C2");
        kv2d.setValue(3.0, "R1", "C3");
        kv2d.setValue(4.0, "R2", "C1");
        kv2d.setValue(5.0, "R2", "C2");
        kv2d.setValue(6.0, "R2", "C3");

        assertEquals(1.0, kv2d.getValue("R1", "C1"));
        assertEquals(2.0, kv2d.getValue("R1", "C2"));
        assertEquals(3.0, kv2d.getValue("R1", "C3"));
        assertEquals(4.0, kv2d.getValue("R2", "C1"));
        assertEquals(5.0, kv2d.getValue("R2", "C2"));
        assertEquals(6.0, kv2d.getValue("R2", "C3"));
    }
    
    @Test
    public void testSetValue() {
        // empty data
        DefaultKeyedValues2D<Number> data = new DefaultKeyedValues2D<Number>();
        data.setValue(1.0, "R1", "C1");
        assertEquals(1.0, data.getValue("R1", "C1").doubleValue(), EPSILON);
        assertEquals(1, data.getRowCount());
        assertEquals(1, data.getColumnCount());
        
        // existing keys
        data.setValue(2.0, "R1", "C1");
        assertEquals(2.0, data.getValue("R1", "C1").doubleValue(), EPSILON);
        assertEquals(1, data.getRowCount());
        assertEquals(1, data.getColumnCount());

        // existing row key, new column key
        data.setValue(3.0, "R1", "C2");
        assertEquals(3.0, data.getValue("R1", "C2").doubleValue(), EPSILON);
        assertEquals(1, data.getRowCount());
        assertEquals(2, data.getColumnCount());
        
        // existing column key, new row key
        data.setValue(4.0, "R2", "C2");
        assertEquals(4.0, data.getValue("R2", "C2").doubleValue(), EPSILON);
        assertEquals(2, data.getRowCount());
        assertEquals(2, data.getColumnCount());
        assertNull(data.getValue("R2", "C1"));
        
        // new row key and new column key
        data.setValue(5.0, "R3", "C3");
        assertEquals(5.0, data.getValue("R3", "C3").doubleValue(), EPSILON);
        assertEquals(3, data.getRowCount());
        assertEquals(3, data.getColumnCount());
        assertNull(data.getValue("R1", "C3"));
    }
    
    @Test
    public void testEquals() {
        DefaultKeyedValues2D<Number> d1 = new DefaultKeyedValues2D<Number>();
        DefaultKeyedValues2D<Number> d2 = new DefaultKeyedValues2D<Number>();
        assertTrue(d1.equals(d2));
        assertFalse(d1.equals(null));
        
        d1.setValue(1.0, "R1", "C1");
        assertFalse(d1.equals(d2));
        d2.setValue(1.0, "R1", "C1");
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
