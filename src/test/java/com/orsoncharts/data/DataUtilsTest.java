/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.data;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Tests for the {@link DataUtils} class.
 */
public class DataUtilsTest {
    
    private static final double EPSILON = 0.0000001;
    
    @Test
    public void testCalcTotal() {
        DefaultKeyedValues values = new DefaultKeyedValues();
        assertEquals(0.0, DataUtils.calcTotal(values), EPSILON);
        
        values.addValue("K1", 1.0);
        assertEquals(1.0, DataUtils.calcTotal(values), EPSILON);
        values.addValue("K2", 2.0);
        assertEquals(3.0, DataUtils.calcTotal(values), EPSILON);
        values.addValue("K3", 3.0);
        assertEquals(6.0, DataUtils.calcTotal(values), EPSILON);
        values.addValue("K2", null);
        assertEquals(4.0, DataUtils.calcTotal(values), EPSILON);
    }
}
