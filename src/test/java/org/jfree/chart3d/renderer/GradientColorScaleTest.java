/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2022, by David Gilbert.  All rights reserved.
 * 
 * https://github.com/jfree/orson-charts
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

package org.jfree.chart3d.renderer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import org.jfree.chart3d.TestUtils;
import org.jfree.chart3d.data.Range;

/**
 * Tests for the {@link GradientColorScale} class.
 */
public class GradientColorScaleTest {
    
    @Test
    public void testGeneral() {
        GradientColorScale gcs = new GradientColorScale(new Range(0.0, 1.0), 
                Color.YELLOW, Color.RED);
        assertEquals(Color.YELLOW, gcs.valueToColor(0.0));
        assertEquals(Color.RED, gcs.valueToColor(1.0));
    }
    
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
