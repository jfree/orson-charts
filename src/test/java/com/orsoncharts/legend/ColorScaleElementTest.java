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

package com.orsoncharts.legend;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.awt.Color;
import java.awt.Font;
import org.junit.Test;
import com.orsoncharts.TestUtils;
import com.orsoncharts.renderer.FixedColorScale;
import com.orsoncharts.util.Orientation;

/**
 * Tests for the {@link ColorScaleElement} class.
 */
public class ColorScaleElementTest {
    
    @Test
    public void testEquals() {
        ColorScaleElement cs1 = new ColorScaleElement(
                new FixedColorScale(Color.BLUE), Orientation.HORIZONTAL, 1.0, 
                2.0, new Font(Font.SERIF, Font.PLAIN, 10));
        ColorScaleElement cs2 = new ColorScaleElement(
                new FixedColorScale(Color.BLUE), Orientation.HORIZONTAL, 1.0, 
                2.0, new Font(Font.SERIF, Font.PLAIN, 10));
        assertTrue(cs1.equals(cs2));
        assertFalse(cs1.equals(null));
        
        cs1 = new ColorScaleElement(
                new FixedColorScale(Color.RED), Orientation.HORIZONTAL, 1.0, 
                2.0, new Font(Font.SERIF, Font.PLAIN, 10));
        assertFalse(cs1.equals(cs2));
        cs2 = new ColorScaleElement(
                new FixedColorScale(Color.RED), Orientation.HORIZONTAL, 1.0, 
                2.0, new Font(Font.SERIF, Font.PLAIN, 10));
        assertTrue(cs1.equals(cs2));
        
        cs1 = new ColorScaleElement(
                new FixedColorScale(Color.RED), Orientation.VERTICAL, 1.0, 
                2.0, new Font(Font.SERIF, Font.PLAIN, 10));
        assertFalse(cs1.equals(cs2));
        cs2 = new ColorScaleElement(
                new FixedColorScale(Color.RED), Orientation.VERTICAL, 1.0, 
                2.0, new Font(Font.SERIF, Font.PLAIN, 10));
        assertTrue(cs1.equals(cs2));

        cs1 = new ColorScaleElement(
                new FixedColorScale(Color.RED), Orientation.VERTICAL, 1.1, 
                2.0, new Font(Font.SERIF, Font.PLAIN, 10));
        assertFalse(cs1.equals(cs2));
        cs2 = new ColorScaleElement(
                new FixedColorScale(Color.RED), Orientation.VERTICAL, 1.1, 
                2.0, new Font(Font.SERIF, Font.PLAIN, 10));
        assertTrue(cs1.equals(cs2));

        cs1 = new ColorScaleElement(
                new FixedColorScale(Color.RED), Orientation.VERTICAL, 1.1, 
                2.2, new Font(Font.SERIF, Font.PLAIN, 10));
        assertFalse(cs1.equals(cs2));
        cs2 = new ColorScaleElement(
                new FixedColorScale(Color.RED), Orientation.VERTICAL, 1.1, 
                2.2, new Font(Font.SERIF, Font.PLAIN, 10));
        assertTrue(cs1.equals(cs2));

        cs1 = new ColorScaleElement(
                new FixedColorScale(Color.RED), Orientation.VERTICAL, 1.1, 
                2.2, new Font(Font.SERIF, Font.PLAIN, 22));
        assertFalse(cs1.equals(cs2));
        cs2 = new ColorScaleElement(
                new FixedColorScale(Color.RED), Orientation.VERTICAL, 1.1, 
                2.2, new Font(Font.SERIF, Font.PLAIN, 22));
        assertTrue(cs1.equals(cs2));
    }
    
    @Test
    public void testSerialization() {
        ColorScaleElement cs1 = new ColorScaleElement(
                new FixedColorScale(Color.BLUE), Orientation.HORIZONTAL, 1.0, 
                2.0, new Font(Font.SERIF, Font.PLAIN, 10));
        ColorScaleElement cs2 = (ColorScaleElement) TestUtils.serialized(cs1);
        assertTrue(cs1.equals(cs2));
    }

}
