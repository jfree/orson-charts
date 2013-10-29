/* ===========
 * OrsonCharts
 * ===========
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
        StandardXYZPaintSource s1 = new StandardXYZPaintSource();
        StandardXYZPaintSource s2 = new StandardXYZPaintSource();
        assertTrue(s1.equals(s2));
        assertFalse(s1.equals(null));
        
        s1 = new StandardXYZPaintSource(Color.BLUE);
        assertFalse(s1.equals(s2));
        s2 = new StandardXYZPaintSource(Color.BLUE);
        assertTrue(s1.equals(s2));
    }
    
    /**
     * Some checks for serialization support.
     */
    @Test
    public void testSerialization() {
        StandardXYZPaintSource ps1 = new StandardXYZPaintSource();
        StandardXYZPaintSource ps2 
                = (StandardXYZPaintSource) TestUtils.serialized(ps1);
        assertTrue(ps1.equals(ps2));
    }

}
