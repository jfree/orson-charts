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

package com.orsoncharts.data;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;

import com.orsoncharts.Range;

import org.junit.Test;

/**
 * Some checks for the {@link DataUtilities} class.
 */
public class DataValuesTest {

    @Test
    public void testFindValueRange() {
        DefaultKeyedValues3D<String, String, String, Number> data 
                = new DefaultKeyedValues3D<String, String, String, Number>();
        assertNull(DataUtils.findValueRange(data));
        data.setValue(1.5, "S1", "R1", "C1");
        assertEquals(new Range(1.5, 1.5), DataUtils.findValueRange(data));
        data.setValue(-1.5, "S2", "R1", "C1");
        assertEquals(new Range(-1.5, 1.5), DataUtils.findValueRange(data));
    }
    
    @Test
    public void testFindValueRangeWithBase() {
        DefaultKeyedValues3D<String, String, String, Number> data 
                = new DefaultKeyedValues3D<String, String, String, Number>();
        assertNull(DataUtils.findValueRange(data));
        data.setValue(1.5, "S1", "R1", "C1");
        assertEquals(new Range(1.0, 1.5), DataUtils.findValueRange(data, 
                1.0));
        data.setValue(-1.5, "S2", "R1", "C1");
        assertEquals(new Range(-1.5, 1.5), DataUtils.findValueRange(data, 
                1.0));
    }

}
