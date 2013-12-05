/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.plot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Color;
import org.junit.Test;

/**
 * Some tests for the {@link StandardColorSource} class.
 */
public class StandardColorSourceTest {

    @Test
    public void testConstructors() {
        StandardColorSource scs = new StandardColorSource(Color.RED);
        assertEquals(Color.RED, scs.getColor("A"));
        
        scs = new StandardColorSource(Color.RED, Color.GREEN, Color.BLUE);
        assertEquals(Color.RED, scs.getColor("A"));
        assertEquals(Color.GREEN, scs.getColor("B"));
        assertEquals(Color.BLUE, scs.getColor("C"));
        
        try {
            scs = new StandardColorSource((Color[]) null);
            fail("Should have thrown an exception.");
        } catch (IllegalArgumentException e) {
            // expected
        }
        
        try {
            scs = new StandardColorSource(Color.RED, null);
            fail("Should have thrown an exception");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testEquals() {
        StandardColorSource scs1 = new StandardColorSource(Color.RED);
        StandardColorSource scs2 = new StandardColorSource(Color.RED);
        assertTrue(scs1.equals(scs2));
        
        scs1.setColor("A", Color.YELLOW);
        assertFalse(scs1.equals(scs2));
        scs2.setColor("A", Color.YELLOW);
        assertTrue(scs1.equals(scs2));
    }
}
