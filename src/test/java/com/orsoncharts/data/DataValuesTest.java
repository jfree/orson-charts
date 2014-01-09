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
        assertNull(DataUtils.findValueRange(data));
        data.setValue(1.5, "S1", "R1", "C1");
        assertEquals(new Range(1.5, 1.5), DataUtils.findValueRange(data));
        data.setValue(-1.5, "S2", "R1", "C1");
        assertEquals(new Range(-1.5, 1.5), DataUtils.findValueRange(data));
    }
    
    @Test
    public void testFindValueRangeWithBase() {
        DefaultKeyedValues3D data = new DefaultKeyedValues3D();
        assertNull(DataUtils.findValueRange(data));
        data.setValue(1.5, "S1", "R1", "C1");
        assertEquals(new Range(1.0, 1.5), DataUtils.findValueRange(data, 
                1.0));
        data.setValue(-1.5, "S2", "R1", "C1");
        assertEquals(new Range(-1.5, 1.5), DataUtils.findValueRange(data, 
                1.0));
    }

}
