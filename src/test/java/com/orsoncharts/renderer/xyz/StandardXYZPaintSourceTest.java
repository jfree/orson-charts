/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer.xyz;

import com.orsoncharts.TestUtils;
import java.awt.Color;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Tests for the {@link DefaultXYZPaintSource} class.
 */
public class StandardXYZPaintSourceTest {
    
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
