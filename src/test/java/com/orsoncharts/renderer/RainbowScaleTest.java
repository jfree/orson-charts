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

package com.orsoncharts.renderer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import com.orsoncharts.Range;
import com.orsoncharts.TestUtils;

/**
 * Tests for the {@link RainbowScale} class.
 */
public class RainbowScaleTest {
    
    @Test
    public void testEquals() {
        RainbowScale rs1 = new RainbowScale(new Range(5.0, 10.0));
        RainbowScale rs2 = new RainbowScale(new Range(5.0, 10.0));
        assertTrue(rs1.equals(rs2));
        assertFalse(rs1.equals(null));
        
    }    
    
    @Test
    public void testSerialization() {
        RainbowScale rs1 = new RainbowScale(new Range(5.0, 10.0));
        RainbowScale rs2 = (RainbowScale) TestUtils.serialized(rs1);
        assertTrue(rs1.equals(rs2));
    }


}
