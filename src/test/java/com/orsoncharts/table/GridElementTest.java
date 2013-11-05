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
 * Tests for the {@link GridElement} class.
 */
public class GridElementTest {
    
    @Test
    public void testEquals() {
        GridElement e1 = new GridElement();
        GridElement e2 = new GridElement();
        assertTrue(e1.equals(e2));
        
        e1.setElement(new TextElement("A"), "R1", "C1");
        assertFalse(e1.equals(e2));
        e2.setElement(new TextElement("A"), "R1", "C1");
        assertTrue(e1.equals(e2));
    }
    
    /**
     * A check for serialization.
     */
    @Test
    public void testSerialization() {
        GridElement e1 = new GridElement();
        GridElement e2 = (GridElement) TestUtils.serialized(e1);
        assertTrue(e1.equals(e2));        
    }
}
