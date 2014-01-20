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

package com.orsoncharts.legend;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import org.junit.Test;

import com.orsoncharts.TestUtils;
import com.orsoncharts.table.HAlign;

/**
 * Tests for the {@link StandardLegendBuilder} class.
 */
public class StandardLegendBuilderTest {
    
    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        StandardLegendBuilder lb1 = new StandardLegendBuilder();
        StandardLegendBuilder lb2 = new StandardLegendBuilder();
        assertTrue(lb1.equals(lb2));
        assertFalse(lb1.equals(null));
        
        lb1.setHeader("XYZ");
        assertFalse(lb1.equals(lb2));
        lb2.setHeader("XYZ");
        assertTrue(lb1.equals(lb2));

        lb1.setHeaderAlignment(HAlign.CENTER);
        assertFalse(lb1.equals(lb2));
        lb2.setHeaderAlignment(HAlign.CENTER);
        assertTrue(lb1.equals(lb2));

        lb1.setFooter("ABC");
        assertFalse(lb1.equals(lb2));
        lb2.setFooter("ABC");
        assertTrue(lb1.equals(lb2));

        lb1.setFooterAlignment(HAlign.CENTER);
        assertFalse(lb1.equals(lb2));
        lb2.setFooterAlignment(HAlign.CENTER);
        assertTrue(lb1.equals(lb2));
        
    }
    
    /**
     * Checks for serialization support.
     */
    @Test
    public void testSerialization() {
        StandardLegendBuilder lb1 = new StandardLegendBuilder();
        StandardLegendBuilder lb2 = (StandardLegendBuilder) 
                TestUtils.serialized(lb1);
        assertTrue(lb1.equals(lb2));
    }
}
