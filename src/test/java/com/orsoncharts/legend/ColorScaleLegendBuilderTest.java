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

import java.awt.Font;

import org.junit.Test;

import com.orsoncharts.TestUtils;

/**
 * Tests for the {@link ColorScaleLegendBuilder} class.
 */
public class ColorScaleLegendBuilderTest {
    
    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        ColorScaleLegendBuilder lb1 = new ColorScaleLegendBuilder();
        ColorScaleLegendBuilder lb2 = new ColorScaleLegendBuilder();
        assertTrue(lb1.equals(lb2));
        assertFalse(lb1.equals(null));
        
        lb1.setBarWidth(123);
        assertFalse(lb1.equals(lb2));
        lb2.setBarWidth(123);
        assertTrue(lb1.equals(lb2));
        
        lb1.setBarLength(321);
        assertFalse(lb1.equals(lb2));
        lb2.setBarLength(321);
        assertTrue(lb1.equals(lb2));

        lb1.setItemFont(new Font("Dialog", Font.PLAIN, 4));
        assertFalse(lb1.equals(lb2));
        lb2.setItemFont(new Font("Dialog", Font.PLAIN, 4));
        assertTrue(lb1.equals(lb2));
    }
    
    /**
     * Checks for serialization support.
     */
    @Test
    public void testSerialization() {
        ColorScaleLegendBuilder lb1 = new ColorScaleLegendBuilder();
        ColorScaleLegendBuilder lb2 = (ColorScaleLegendBuilder) 
                TestUtils.serialized(lb1);
        assertTrue(lb1.equals(lb2));
    }
}
