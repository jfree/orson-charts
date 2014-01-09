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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.awt.Color;
import org.junit.Test;
import com.orsoncharts.TestUtils;

/**
 * Tests for the {@link BarXYZRenderer} class.
 */
public class BarXYZRendererTest {
 
    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        BarXYZRenderer r1 = new BarXYZRenderer();
        BarXYZRenderer r2 = new BarXYZRenderer();
        assertTrue(r1.equals(r2));
        assertFalse(r1.equals(null));
        
        r1.setBase(1.1);
        assertFalse(r1.equals(r2));
        r2.setBase(1.1);
        assertTrue(r1.equals(r2));

        r1.setBarXWidth(2.2);
        assertFalse(r1.equals(r2));
        r2.setBarXWidth(2.2);
        assertTrue(r1.equals(r2));

        r1.setBarZWidth(3.3);
        assertFalse(r1.equals(r2));
        r2.setBarZWidth(3.3);
        assertTrue(r1.equals(r2));

        // baseColorSource
        r1.setBaseColorSource(null);
        assertFalse(r1.equals(r2));
        r2.setBaseColorSource(null);
        assertTrue(r1.equals(r2));
        
        r1.setBaseColorSource(new StandardXYZColorSource(Color.CYAN));
        assertFalse(r1.equals(r2));
        r2.setBaseColorSource(new StandardXYZColorSource(Color.CYAN));
        assertTrue(r1.equals(r2));
        
        // topPaintSource
        r1.setBaseColorSource(null);
        assertFalse(r1.equals(r2));
        r2.setBaseColorSource(null);
        assertTrue(r1.equals(r2));
        
        r1.setBaseColorSource(new StandardXYZColorSource(Color.YELLOW));
        assertFalse(r1.equals(r2));
        r2.setBaseColorSource(new StandardXYZColorSource(Color.YELLOW));
        assertTrue(r1.equals(r2));
        
        // regular paintSource
        r1.setBaseColorSource(new StandardXYZColorSource(Color.RED));
        assertFalse(r1.equals(r2));
        r2.setBaseColorSource(new StandardXYZColorSource(Color.RED));
        assertTrue(r1.equals(r2));
    }

    /**
     * Some checks for serialization support.
     */
    @Test
    public void testSerialization() {
        BarXYZRenderer r1 = new BarXYZRenderer();
        BarXYZRenderer r2 = (BarXYZRenderer) TestUtils.serialized(r1);
        assertTrue(r1.equals(r2));
    }

}
