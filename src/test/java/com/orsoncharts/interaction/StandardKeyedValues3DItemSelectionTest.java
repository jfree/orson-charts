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

package com.orsoncharts.interaction;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.orsoncharts.TestUtils;

/**
 * Tests for the {@link StandardKeyedValues3DItemSelection} class.
 */
public class StandardKeyedValues3DItemSelectionTest {
    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        StandardKeyedValues3DItemSelection s1 
                = new StandardKeyedValues3DItemSelection();
        StandardKeyedValues3DItemSelection s2 
                = new StandardKeyedValues3DItemSelection();
        assertTrue(s1.equals(s2));
        assertFalse(s1.equals(null));
    }
    
    /**
     * Checks for serialization support.
     */
    @Test
    public void testSerialization() {
        StandardKeyedValues3DItemSelection s1 
                = new StandardKeyedValues3DItemSelection();
        StandardKeyedValues3DItemSelection s2 
                = (StandardKeyedValues3DItemSelection) TestUtils.serialized(s1);
        assertTrue(s1.equals(s2));
    }    
    
}
