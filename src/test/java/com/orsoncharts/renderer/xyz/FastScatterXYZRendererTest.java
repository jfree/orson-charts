/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer.xyz;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import com.orsoncharts.TestUtils;

/**
 * Tests for the {@link FastScatterXYZRendererTest}.
 */
public class FastScatterXYZRendererTest {
    
    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        FastScatterXYZRenderer r1 = new FastScatterXYZRenderer();
        FastScatterXYZRenderer r2 = new FastScatterXYZRenderer();
        assertTrue(r1.equals(r2));
        assertFalse(r1.equals(null));
    }
    
    /**
     * Some checks for serialization support.
     */
    @Test
    public void testSerialization() {
        FastScatterXYZRenderer r1 = new FastScatterXYZRenderer();
        FastScatterXYZRenderer r2 
                = (FastScatterXYZRenderer) TestUtils.serialized(r1);
        assertTrue(r1.equals(r2));
    }
 
}
