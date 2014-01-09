/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of these source files is prohibited.
 * 
 */

package com.orsoncharts.label;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.orsoncharts.TestUtils;

/**
 * Tests for the {@link StandardPieLabelGenerator} class.
 */
public class StandardPieLabelGeneratorTest {
    
    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        StandardPieLabelGenerator lg1 = new StandardPieLabelGenerator();
        StandardPieLabelGenerator lg2 = new StandardPieLabelGenerator();
        assertTrue(lg1.equals(lg2));
        assertFalse(lg1.equals(null));
        
        lg1 = new StandardPieLabelGenerator("%s");
        assertFalse(lg1.equals(lg2));
        lg2 = new StandardPieLabelGenerator("%s");
        assertTrue(lg1.equals(lg2));
    }
    
    /**
     * Checks for serialization support.
     */
    @Test
    public void testSerialization() {
        StandardPieLabelGenerator lg1 = new StandardPieLabelGenerator();
        StandardPieLabelGenerator lg2 = new StandardPieLabelGenerator();
        TestUtils.serialized(lg1);
        assertTrue(lg1.equals(lg2));
    }
}
