/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.legend;

import com.orsoncharts.TestUtils;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests for the {@link StandardLegendBuilder} class.
 */
public class StandardLegendBuilderTest {
    
    @Test
    public void testEquals() {
        StandardLegendBuilder lb1 = new StandardLegendBuilder();
        StandardLegendBuilder lb2 = new StandardLegendBuilder();
        assertTrue(lb1.equals(lb2));
        assertFalse(lb1.equals(null));
    }
    
    @Test
    public void testSerialization() {
        StandardLegendBuilder lb1 = new StandardLegendBuilder();
        StandardLegendBuilder lb2 = (StandardLegendBuilder) 
                TestUtils.serialized(lb1);
        assertTrue(lb1.equals(lb2));
    }
}
