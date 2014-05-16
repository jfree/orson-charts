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

package com.orsoncharts.axis;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import com.orsoncharts.TestUtils;
import static org.junit.Assert.fail;

/**
 * Tests for the {@link NumberTickSelector} class.
 */
public class NumberTickSelectorTest {
    
    @Test
    public void testEquals() {
        NumberTickSelector s1 = new NumberTickSelector();
        NumberTickSelector s2 = new NumberTickSelector();
        assertTrue(s1.equals(s2));
        
        s1 = new NumberTickSelector(true);
        assertFalse(s1.equals(s2));
        s2 = new NumberTickSelector(true);
        assertTrue(s1.equals(s2));
    }
    
    /**
     * Checks for serialization support.
     */
    @Test
    public void testSerialization() {
        NumberTickSelector s1 = new NumberTickSelector();
        NumberTickSelector s2 = (NumberTickSelector) TestUtils.serialized(s1);
        assertTrue(s1.equals(s2));
    }
    
    public static final double EPSILON = 0.0000001;
    
    @Test
    public void testSelect() {
        NumberTickSelector s = new NumberTickSelector();
        assertEquals(1.0, s.getCurrentTickSize(), EPSILON);
        double d = s.getCurrentTickSize();
        s.select(10);
        assertEquals(s.getCurrentTickSize(), 10.0, EPSILON);
        s.select(14);
        assertEquals(s.getCurrentTickSize(), 100.0, EPSILON);
        s.select(1000000000000000000.0);
        assertEquals(s.getCurrentTickSize(), 1000000000000000000.0, EPSILON);
        
        try {
            s.select(0);
            fail("Expected an IllegalArgumentException for this case.");
        } catch (IllegalArgumentException e) {
            // expected
        }
        
        try {
            s.select(Double.POSITIVE_INFINITY);
            fail("Expected an IllegalArgumentException for this case.");
        } catch (IllegalArgumentException e) {
            // expected
        }

        try {
            s.select(Double.NEGATIVE_INFINITY);
            fail("Expected an IllegalArgumentException for this case.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
