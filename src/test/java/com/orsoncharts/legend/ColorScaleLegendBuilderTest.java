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


import org.junit.Test;

import com.orsoncharts.TestUtils;

/**
 * Tests for the {@link ColorScaleLegendBuilder} class.
 */
public class ColorScaleLegendBuilderTest {
    
    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        ColorScaleLegendBuilder lb1 = new ColorScaleLegendBuilder();
        ColorScaleLegendBuilder lb2 = new ColorScaleLegendBuilder();
        assertTrue(lb1.equals(lb2));
        assertFalse(lb1.equals(null));
        
        lb1.setBarWidth(123);
        assertFalse(lb1.equals(lb2));
        lb2.setBarWidth(123);
        assertTrue(lb1.equals(lb2));
        
        lb1.setBarLength(321);
        assertFalse(lb1.equals(lb2));
        lb2.setBarLength(321);
        assertTrue(lb1.equals(lb2));

    }
    
    /**
     * Checks for serialization support.
     */
    @Test
    public void testSerialization() {
        ColorScaleLegendBuilder lb1 = new ColorScaleLegendBuilder();
        ColorScaleLegendBuilder lb2 = (ColorScaleLegendBuilder) 
                TestUtils.serialized(lb1);
        assertTrue(lb1.equals(lb2));
    }
}
