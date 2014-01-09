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

package com.orsoncharts.renderer.xyz;

import com.orsoncharts.TestUtils;
import java.awt.Color;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 * Tests for the {@link DefaultXYZPaintSource} class.
 */
public class StandardXYZColorSourceTest {
    
    @Test
    public void testConstructors() {
        StandardXYZColorSource scs = new StandardXYZColorSource(Color.RED);
        assertEquals(Color.RED, scs.getColor(0, 0));
        assertEquals(Color.RED, scs.getColor(1, 0));
        
        scs = new StandardXYZColorSource(Color.RED, Color.GREEN, Color.BLUE);
        assertEquals(Color.RED, scs.getColor(0, 0));
        assertEquals(Color.GREEN, scs.getColor(1, 0));
        assertEquals(Color.BLUE, scs.getColor(2, 0));
        
        try {
            scs = new StandardXYZColorSource((Color[]) null);
            fail("Should have thrown an exception.");
        } catch (IllegalArgumentException e) {
            // expected
        }
        
        try {
            scs = new StandardXYZColorSource(Color.RED, null);
            fail("Should have thrown an exception");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
    
    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        StandardXYZColorSource s1 = new StandardXYZColorSource();
        StandardXYZColorSource s2 = new StandardXYZColorSource();
        assertTrue(s1.equals(s2));
        assertFalse(s1.equals(null));
        
        s1 = new StandardXYZColorSource(Color.BLUE);
        assertFalse(s1.equals(s2));
        s2 = new StandardXYZColorSource(Color.BLUE);
        assertTrue(s1.equals(s2));
    }
    
    /**
     * Some checks for serialization support.
     */
    @Test
    public void testSerialization() {
        StandardXYZColorSource ps1 = new StandardXYZColorSource();
        StandardXYZColorSource ps2 
                = (StandardXYZColorSource) TestUtils.serialized(ps1);
        assertTrue(ps1.equals(ps2));
    }

}
