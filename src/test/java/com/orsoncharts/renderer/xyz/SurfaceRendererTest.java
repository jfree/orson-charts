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
import com.orsoncharts.data.function.Function3D;
import com.orsoncharts.renderer.FixedColorScale;
import java.awt.Color;

/**
 * Tests for the {@link SurfaceRenderer} class.
 */
public class SurfaceRendererTest {
    
    @Test
    public void testEquals() {
        Function3D f = new Function3D() {
            @Override
            public double getValue(double x, double z) {
                return x + z;
            }
        };
        SurfaceRenderer r1 = new SurfaceRenderer(f);
        SurfaceRenderer r2 = new SurfaceRenderer(f);
        assertTrue(r1.equals(r2));
        assertFalse(r1.equals(null));
        
        r1.setXSamples(123);
        assertFalse(r1.equals(r2));
        r2.setXSamples(123);
        assertTrue(r1.equals(r2));
        
        r1.setZSamples(22);
        assertFalse(r1.equals(r2));
        r2.setZSamples(22);
        assertTrue(r1.equals(r2));
        
        r1.setColorScale(new FixedColorScale(Color.CYAN));
        assertFalse(r1.equals(r2));
        r2.setColorScale(new FixedColorScale(Color.CYAN));
        assertTrue(r1.equals(r2));
        
        r1.setDrawFaceOutlines(false);
        assertFalse(r1.equals(r2));
        r2.setDrawFaceOutlines(false);
        assertTrue(r1.equals(r2));
    }
    
    /**
     * Some checks for serialization support.
     */
    @Test
    public void testSerialization() {
        SurfaceRenderer r1 = new SurfaceRenderer(new F3D());
        SurfaceRenderer r2 = (SurfaceRenderer) TestUtils.serialized(r1);
        assertTrue(r1.equals(r2));
    }

    static final class F3D implements Function3D {

        public F3D() { 
        }
        
        @Override
        public double getValue(double x, double z) {
            return x + z;
        }
        
        @Override
        public boolean equals(Object obj) {
            return obj instanceof F3D;
        }
    }
}
