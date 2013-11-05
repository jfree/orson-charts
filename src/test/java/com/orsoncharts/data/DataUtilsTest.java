/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;

/**
 * Tests for the {@link DataUtils} class.
 */
public class DataUtilsTest {
    
    private static final double EPSILON = 0.0000001;
    
    @Test
    public void testTotal() {
        DefaultKeyedValues values = new DefaultKeyedValues();
        assertEquals(0.0, DataUtils.total(values), EPSILON);
        
        values.put("K1", 1.0);
        assertEquals(1.0, DataUtils.total(values), EPSILON);
        values.put("K2", 2.0);
        assertEquals(3.0, DataUtils.total(values), EPSILON);
        values.put("K3", 3.0);
        assertEquals(6.0, DataUtils.total(values), EPSILON);
        values.put("K2", null);
        assertEquals(4.0, DataUtils.total(values), EPSILON);
    }
    
    public void testStackSubTotal() {
        DefaultKeyedValues3D<Number> data = new DefaultKeyedValues3D<Number>();
        double[] result = DataUtils.stackSubTotal(data, 0.0, 0, 0, 0);
        assertArrayEquals(result, new double[] { 0.0, 0.0 }, EPSILON);
        result = DataUtils.stackSubTotal(data, -1.0, 0, 0, 0);
        assertArrayEquals(result, new double[] { -1.0, -1.0 }, EPSILON);
        
        data.setValue(1.0, "S0", "R1", "C1");
        result = DataUtils.stackSubTotal(data, 0.0, 0, 0, 0);
        assertArrayEquals(result, new double[] { 0.0, 0.0 }, EPSILON);
        result = DataUtils.stackSubTotal(data, 0.0, 1, 0, 0);
        assertArrayEquals(result, new double[] { 0.0, 1.0 }, EPSILON);
        
        data.setValue(2.0, "S1", "R1", "C1");
        result = DataUtils.stackSubTotal(data, 0.0, 0, 0, 0);
        assertArrayEquals(result, new double[] { 0.0, 0.0 }, EPSILON);
        result = DataUtils.stackSubTotal(data, 0.0, 1, 0, 0);
        assertArrayEquals(result, new double[] { 0.0, 1.0 }, EPSILON);
        result = DataUtils.stackSubTotal(data, 0.0, 2, 0, 0);
        assertArrayEquals(result, new double[] { 0.0, 3.0 }, EPSILON);
        
        data.setValue(-4.0, "S2", "R1", "C1");
        result = DataUtils.stackSubTotal(data, 0.0, 2, 0, 0);
        assertArrayEquals(result, new double[] { 0.0, 3.0 }, EPSILON);
        result = DataUtils.stackSubTotal(data, 0.0, 3, 0, 0);
        assertArrayEquals(result, new double[] { -4.0, 3.0 }, EPSILON);
        
        data.setValue(null, "S1", "R1", "C1");
        result = DataUtils.stackSubTotal(data, 0.0, 3, 0, 0);
        assertArrayEquals(result, new double[] { -4.0, 1.0 }, EPSILON);
        result = DataUtils.stackSubTotal(data, 2.0, 3, 0, 0);
        assertArrayEquals(result, new double[] { -2.0, 3.0 }, EPSILON);

    }
}
