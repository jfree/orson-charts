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
 * Tests for the {@link FlowElement} class.
 */
public class FlowElementTest {
    
    @Test
    public void testEquals() {
        FlowElement fe1 = new FlowElement();
        FlowElement fe2 = new FlowElement();
        assertTrue(fe1.equals(fe2));
        
        fe1.addElement(new TextElement("ABC"));
        assertFalse(fe1.equals(fe2));
        fe2.addElement(new TextElement("ABC"));
        assertTrue(fe1.equals(fe2));
        
        fe1.setHGap(33);
        assertFalse(fe1.equals(fe2));
        fe2.setHGap(33);
        assertTrue(fe1.equals(fe2));
        
    }
    
    @Test
    public void testSerialization() {
        FlowElement fe1 = new FlowElement();
        FlowElement fe2 = (FlowElement) TestUtils.serialized(fe1);
        assertTrue(fe1.equals(fe2));
    }
    
}
