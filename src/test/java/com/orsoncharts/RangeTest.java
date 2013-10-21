/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
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
   
    @Test
    public void testContains() {
        Range r = new Range(0, 10);
        assertFalse(r.contains(-0.5));
        assertTrue(r.contains(0.0));
        assertTrue(r.contains(5.0));
        assertTrue(r.contains(10.0));
        assertFalse(r.contains(10.5));
    }
    
    @Test
    public void testContainsInterval() {
        Range r = new Range(0, 10);
        assertFalse(r.containsInterval(-0.5, -0.1));
        assertTrue(r.containsInterval(-0.5, 0.0));
        assertTrue(r.containsInterval(0.0, 5.0));
        assertTrue(r.containsInterval(0.0, 10.0));
        assertTrue(r.containsInterval(0.0, 11.0));
        assertTrue(r.containsInterval(5.0, 9.0));
        assertTrue(r.containsInterval(5.0, 10.0));
        assertTrue(r.containsInterval(5.0, 11.0));
        assertTrue(r.containsInterval(10.0, 11.0));
        assertFalse(r.containsInterval(10.5, 11.0));        
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
