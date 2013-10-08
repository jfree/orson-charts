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
    
    private static final double EPSILON = 0.0000001;
    
    @Test
    public void testGridPoint() {
        Range r = new Range(0, 10);
        assertEquals(0.0, r.gridPoint(0, 11), EPSILON);
        assertEquals(1.0, r.gridPoint(1, 11), EPSILON);
        assertEquals(10.0, r.gridPoint(10, 11), EPSILON);
    }
}
