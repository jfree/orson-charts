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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.orsoncharts.TestUtils;

/**
 * Some tests for the {@link DefaultKeyedValues3D} class.
 */
public class DefaultKeyedValues3DTest {
    
    private static final double EPSILON = 0.000000001;
    
    @Test
    public void testSetValue() {
        DefaultKeyedValues3D<String, String, String, Number> data 
                = new DefaultKeyedValues3D<String, String, String, Number>();
        
        // adding to an empty dataset...
        data.setValue(1.0, "S1", "R1", "C1");
        assertEquals(1.0, data.getValue("S1", "R1", "C1").doubleValue(), 
                EPSILON);
        assertEquals(1, data.getSeriesCount());
        assertEquals(1, data.getRowCount());
        assertEquals(1, data.getColumnCount());
        
        // updating the existing value...
        data.setValue(2.0, "S1", "R1", "C1");
        assertEquals(2.0, data.getValue("S1", "R1", "C1").doubleValue(), 
                EPSILON);
        assertEquals(1, data.getSeriesCount());
        assertEquals(1, data.getRowCount());
        assertEquals(1, data.getColumnCount());
        
        // row and column exist, but creating a new series
        data.setValue(3.0, "S2", "R1", "C1");
        assertEquals(3.0, data.getValue("S2", "R1", "C1").doubleValue(), 
                EPSILON);
        assertEquals(2, data.getSeriesCount());
        assertEquals(1, data.getRowCount());
        assertEquals(1, data.getColumnCount());
        assertEquals(2.0, data.getValue("S1", "R1", "C1"));
        
        // series and row exists, but column does not
        data.setValue(4.0, "S2", "R1", "C2");
        assertEquals(4.0, data.getValue("S2", "R1", "C2").doubleValue(), 
                EPSILON);
        assertEquals(2, data.getSeriesCount());
        assertEquals(1, data.getRowCount());
        assertEquals(2, data.getColumnCount());
        assertNull(data.getValue("S1", "R1", "C2"));
        
        // row exists, but series an column do not
        data.setValue(5.0, "S3", "R1", "C3");
        assertEquals(5.0, data.getValue("S3", "R1", "C3").doubleValue(), 
                EPSILON);
        assertEquals(3, data.getSeriesCount());
        assertEquals(1, data.getRowCount());
        assertEquals(3, data.getColumnCount());
        assertNull(data.getValue("S1", "R1", "C3"));
        assertNull(data.getValue("S2", "R1", "C3"));
        
        // series and column exists, but row does not
        data.setValue(6.0, "S1", "R2", "C1");
        assertEquals(6.0, data.getValue("S1", "R2", "C1").doubleValue(), 
                EPSILON);
        assertEquals(3, data.getSeriesCount());
        assertEquals(2, data.getRowCount());
        assertEquals(3, data.getColumnCount());
        assertNull(data.getValue("S2", "R2", "C1"));
        assertNull(data.getValue("S3", "R2", "C1"));
        
        // column exists, but series and row does not
        data.setValue(7.0, "S4", "R3", "C1");
        assertEquals(7.0, data.getValue("S4", "R3", "C1").doubleValue(), 
                EPSILON);
        assertEquals(4, data.getSeriesCount());
        assertEquals(3, data.getRowCount());
        assertEquals(3, data.getColumnCount());
        assertNull(data.getValue("S1", "R3", "C1"));
        assertNull(data.getValue("S2", "R3", "C1"));
        assertNull(data.getValue("S3", "R3", "C1"));
        
        // series exists, but row and column does not
        data.setValue(8.0, "S1", "R4", "C4");
        assertEquals(8.0, data.getValue("S1", "R4", "C4").doubleValue(), 
                EPSILON);
        assertEquals(4, data.getSeriesCount());
        assertEquals(4, data.getRowCount());
        assertEquals(4, data.getColumnCount());
        assertNull(data.getValue("S2", "R4", "C4"));
        assertNull(data.getValue("S3", "R4", "C4"));
        assertNull(data.getValue("S4", "R4", "C4"));

        // series, row and column does not exist
        data.setValue(9.0, "S5", "R5", "C5");
        assertEquals(9.0, data.getValue("S5", "R5", "C5").doubleValue(), 
                EPSILON);
        assertEquals(5, data.getSeriesCount());
        assertEquals(5, data.getRowCount());
        assertEquals(5, data.getColumnCount());
        assertNull(data.getValue("S1", "R5", "C5"));
    }
    
    @Test
    public void testEquals() {
        DefaultKeyedValues3D<String, String, String, Number> d1 
                = new DefaultKeyedValues3D<String, String, String, Number>();
        DefaultKeyedValues3D<String, String, String, Number> d2 
                = new DefaultKeyedValues3D<String, String, String, Number>();
        assertTrue(d1.equals(d2));
        assertFalse(d1.equals(null));
        
        d1.setValue(1.0, "S1", "R1", "C1");
        assertFalse(d1.equals(d2));
        d2.setValue(1.0, "S1", "R1", "C1");
        assertTrue(d1.equals(d2));
    }

    /**
     * Check for serialization support.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testSerialization() {
        DefaultKeyedValues3D<String, String, String, Number> d1 
                = new DefaultKeyedValues3D<String, String, String, Number>();
        d1.setValue(1.0, "S1", "R1", "C1");
        DefaultKeyedValues3D<String, String, String, Number> d2 
                = (DefaultKeyedValues3D<String, String, String, Number>) 
                TestUtils.serialized(d1);
        assertEquals(d1, d2);
    }

}
