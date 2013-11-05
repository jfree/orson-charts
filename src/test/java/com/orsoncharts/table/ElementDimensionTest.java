/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.table;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import com.orsoncharts.TestUtils;

/**
 * Tests for the {@link ElementDimension} class.
 */
public class ElementDimensionTest {
    
    @Test
    public void testEquals() {
        ElementDimension ed1 = new ElementDimension(1.0, 2.0);
        ElementDimension ed2 = new ElementDimension(1.0, 2.0);
        assertTrue(ed1.equals(ed2));
        assertFalse(ed1.equals(null));
        
        ed1 = new ElementDimension(3.0, 2.0);
        assertFalse(ed1.equals(ed2));
        ed2 = new ElementDimension(3.0, 2.0);
        assertTrue(ed1.equals(ed2));

        ed1 = new ElementDimension(3.0, 4.0);
        assertFalse(ed1.equals(ed2));
        ed2 = new ElementDimension(3.0, 4.0);
        assertTrue(ed1.equals(ed2));
    }
    
    @Test
    public void testSerialization() {
        ElementDimension ed1 = new ElementDimension(1.0, 2.0);
        ElementDimension ed2 = (ElementDimension) TestUtils.serialized(ed1);
        assertTrue(ed1.equals(ed2));
    }

}
