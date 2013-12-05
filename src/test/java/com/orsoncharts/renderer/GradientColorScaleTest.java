/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import java.awt.Color;
import com.orsoncharts.TestUtils;
import com.orsoncharts.Range;

/**
 * Tests for the {@link GradientColorScale} class.
 */
public class GradientColorScaleTest {
    
    @Test
    public void testEquals() {
        GradientColorScale gcs1 = new GradientColorScale(new Range(0.0, 1.0), 
                Color.YELLOW, Color.RED);
        GradientColorScale gcs2 = new GradientColorScale(new Range(0.0, 1.0), 
                Color.YELLOW, Color.RED);
        assertTrue(gcs1.equals(gcs2));
        assertFalse(gcs1.equals(null));
        
        gcs1 = new GradientColorScale(new Range(0.0, 1.0), Color.BLUE, 
                Color.WHITE);
        assertFalse(gcs1.equals(gcs2));
        gcs2 = new GradientColorScale(new Range(0.0, 1.0), Color.BLUE, 
                Color.WHITE);
        assertTrue(gcs1.equals(gcs2));
    }    
    
    @Test
    public void testSerialization() {
        GradientColorScale gcs1 = new GradientColorScale(new Range(0.0, 1.0), 
                Color.YELLOW, Color.RED);
        GradientColorScale gcs2 = (GradientColorScale) TestUtils.serialized(
                gcs1);
        assertTrue(gcs1.equals(gcs2));
    }

}
