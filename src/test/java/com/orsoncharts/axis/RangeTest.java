/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.axis;

import com.orsoncharts.Range;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

/**
 * Some tests for the {@link Range} class.
 */
public class RangeTest {

    @Test
    public void testEquals() {
        Range range1 = new Range(0, 10);
        Range range2 = new Range(0, 10);
        assertEquals(range1, range2);
    
        range1 = new Range(2, 4);
        assertNotEquals(range1, range2);
        range2 = new Range(2, 4);
        assertEquals(range1, range2);
    }
}
