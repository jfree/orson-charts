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

package com.orsoncharts.label;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.orsoncharts.TestUtils;

/**
 * Tests for the {@link StandardCategoryItemLabelGenerator} class.
 */
public class StandardCategoryItemLabelGeneratorTest {
 
    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        StandardCategoryItemLabelGenerator g1 
                = new StandardCategoryItemLabelGenerator();
        StandardCategoryItemLabelGenerator g2 
                = new StandardCategoryItemLabelGenerator();
        assertTrue(g1.equals(g2));
        assertFalse(g1.equals(null));
        
        g1 = new StandardCategoryItemLabelGenerator("%s TEST");
        assertFalse(g1.equals(g2));
        g2 = new StandardCategoryItemLabelGenerator("%s TEST");
        assertTrue(g1.equals(g2));
    }
    
    /**
     * Checks for serialization support.
     */
    @Test
    public void testSerialization() {
        StandardCategoryItemLabelGenerator g1 
                = new StandardCategoryItemLabelGenerator();
        StandardCategoryItemLabelGenerator g2 
                = (StandardCategoryItemLabelGenerator) TestUtils.serialized(g1);
        assertTrue(g1.equals(g2));
    }    
}
