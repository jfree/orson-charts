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

package com.orsoncharts.renderer.category;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.awt.Color;
import org.junit.Test;
import com.orsoncharts.TestUtils;

/**
 * Tests for the {@link AreaRenderer3D} class.
 */
public class AreaRenderer3DTest {

    @Test
    public void testEquals() {
        AreaRenderer3D r1 = new AreaRenderer3D();
        AreaRenderer3D r2 = new AreaRenderer3D();
        assertTrue(r1.equals(r2));
        assertFalse(r1.equals(null));
        
        // base
        r1.setBase(9.9);
        assertFalse(r1.equals(r2));
        r2.setBase(9.9);
        assertTrue(r1.equals(r2));

        // baseColor
        r1.setBaseColor(Color.BLUE);
        assertFalse(r1.equals(r2));
        r2.setBaseColor(Color.BLUE);
        assertTrue(r1.equals(r2));
        
        // depth
        r1.setDepth(8.8);
        assertFalse(r1.equals(r2));
        r2.setDepth(8.8);
        assertTrue(r1.equals(r2));
        
        // notify
        r1.setNotify(false);
        assertFalse(r1.equals(r2));
        r2.setNotify(false);
        assertTrue(r1.equals(r2));
    }
    
    @Test
    public void testSerialization() {
        AreaRenderer3D r1 = new AreaRenderer3D();
        AreaRenderer3D r2 = (AreaRenderer3D) TestUtils.serialized(r1);
        assertTrue(r1.equals(r2));
    }
}
