/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.data;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;

import com.orsoncharts.Range;

import org.junit.Test;

/**
 * Some checks for the {@link DataUtilities} class.
 */
public class DataValuesTest {

    @Test
    public void testFindValueRange() {
        DefaultKeyedValues3D data = new DefaultKeyedValues3D();
        assertNull(DataUtilities.findValueRange(data));
        data.setValue(1.5, "S1", "R1", "C1");
        assertEquals(new Range(1.5, 1.5), DataUtilities.findValueRange(data));
        data.setValue(-1.5, "S2", "R1", "C1");
        assertEquals(new Range(-1.5, 1.5), DataUtilities.findValueRange(data));
    }
    
    @Test
    public void testFindValueRangeWithBase() {
        DefaultKeyedValues3D data = new DefaultKeyedValues3D();
        assertNull(DataUtilities.findValueRange(data));
        data.setValue(1.5, "S1", "R1", "C1");
        assertEquals(new Range(2.5, 2.5), DataUtilities.findValueRange(data, 
                1.0));
        data.setValue(-1.5, "S2", "R1", "C1");
        assertEquals(new Range(-0.5, 2.5), DataUtilities.findValueRange(data, 
                1.0));
    }

}
