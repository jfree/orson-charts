/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2016, by Object Refinery Limited.  All rights reserved.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 * 
 * If you do not wish to be bound by the terms of the GPL, an alternative
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts home page:
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
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
                2.0, new Font(Font.SERIF, Font.PLAIN, 10), Color.BLACK);
        ColorScaleElement cs2 = new ColorScaleElement(
                new FixedColorScale(Color.BLUE), Orientation.HORIZONTAL, 1.0, 
                2.0, new Font(Font.SERIF, Font.PLAIN, 10), Color.BLACK);
        assertTrue(cs1.equals(cs2));
        assertFalse(cs1.equals(null));
        
        cs1 = new ColorScaleElement(
                new FixedColorScale(Color.RED), Orientation.HORIZONTAL, 1.0, 
                2.0, new Font(Font.SERIF, Font.PLAIN, 10), Color.BLACK);
        assertFalse(cs1.equals(cs2));
        cs2 = new ColorScaleElement(
                new FixedColorScale(Color.RED), Orientation.HORIZONTAL, 1.0, 
                2.0, new Font(Font.SERIF, Font.PLAIN, 10), Color.BLACK);
        assertTrue(cs1.equals(cs2));
        
        cs1 = new ColorScaleElement(
                new FixedColorScale(Color.RED), Orientation.VERTICAL, 1.0, 
                2.0, new Font(Font.SERIF, Font.PLAIN, 10), Color.BLACK);
        assertFalse(cs1.equals(cs2));
        cs2 = new ColorScaleElement(
                new FixedColorScale(Color.RED), Orientation.VERTICAL, 1.0, 
                2.0, new Font(Font.SERIF, Font.PLAIN, 10), Color.BLACK);
        assertTrue(cs1.equals(cs2));

        cs1 = new ColorScaleElement(
                new FixedColorScale(Color.RED), Orientation.VERTICAL, 1.1, 
                2.0, new Font(Font.SERIF, Font.PLAIN, 10), Color.BLACK);
        assertFalse(cs1.equals(cs2));
        cs2 = new ColorScaleElement(
                new FixedColorScale(Color.RED), Orientation.VERTICAL, 1.1, 
                2.0, new Font(Font.SERIF, Font.PLAIN, 10), Color.BLACK);
        assertTrue(cs1.equals(cs2));

        cs1 = new ColorScaleElement(
                new FixedColorScale(Color.RED), Orientation.VERTICAL, 1.1, 
                2.2, new Font(Font.SERIF, Font.PLAIN, 10), Color.BLACK);
        assertFalse(cs1.equals(cs2));
        cs2 = new ColorScaleElement(
                new FixedColorScale(Color.RED), Orientation.VERTICAL, 1.1, 
                2.2, new Font(Font.SERIF, Font.PLAIN, 10), Color.BLACK);
        assertTrue(cs1.equals(cs2));

        cs1 = new ColorScaleElement(
                new FixedColorScale(Color.RED), Orientation.VERTICAL, 1.1, 
                2.2, new Font(Font.SERIF, Font.PLAIN, 22), Color.BLACK);
        assertFalse(cs1.equals(cs2));
        cs2 = new ColorScaleElement(
                new FixedColorScale(Color.RED), Orientation.VERTICAL, 1.1, 
                2.2, new Font(Font.SERIF, Font.PLAIN, 22), Color.BLACK);
        assertTrue(cs1.equals(cs2));
    }
    
    @Test
    public void testSerialization() {
        ColorScaleElement cs1 = new ColorScaleElement(
                new FixedColorScale(Color.BLUE), Orientation.HORIZONTAL, 1.0, 
                2.0, new Font(Font.SERIF, Font.PLAIN, 10), Color.BLACK);
        ColorScaleElement cs2 = (ColorScaleElement) TestUtils.serialized(cs1);
        assertTrue(cs1.equals(cs2));
    }

}
