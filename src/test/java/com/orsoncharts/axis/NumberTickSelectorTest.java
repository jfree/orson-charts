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
import org.junit.Test;
import com.orsoncharts.TestUtils;

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
}
