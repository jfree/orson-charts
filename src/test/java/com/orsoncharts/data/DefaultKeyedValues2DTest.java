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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import com.orsoncharts.TestUtils;
import com.orsoncharts.data.category.StandardCategoryDataset3D;

/**
 * Some checks for the {@link DefaultKeyedValues2D} class.
 */
public class DefaultKeyedValues2DTest {
    
    private static final double EPSILON = 0.00000001;
    
    @Test
    public void testGetValue() {
        DefaultKeyedValues2D<String, String, Number> kv2d 
                = new DefaultKeyedValues2D<String, String, Number>();
        kv2d.setValue(1.0, "R1", "C1");
        kv2d.setValue(2.0, "R1", "C2");
        kv2d.setValue(3.0, "R1", "C3");
        kv2d.setValue(4.0, "R2", "C1");
        kv2d.setValue(5.0, "R2", "C2");
        kv2d.setValue(6.0, "R2", "C3");

        assertEquals(1.0, kv2d.getValue("R1", "C1"));
        assertEquals(2.0, kv2d.getValue("R1", "C2"));
        assertEquals(3.0, kv2d.getValue("R1", "C3"));
        assertEquals(4.0, kv2d.getValue("R2", "C1"));
        assertEquals(5.0, kv2d.getValue("R2", "C2"));
        assertEquals(6.0, kv2d.getValue("R2", "C3"));
    }
    
    @Test
    public void testSetValue() {
        // empty data
        DefaultKeyedValues2D<String, String, Number> data 
                = new DefaultKeyedValues2D<String, String, Number>();
        data.setValue(1.0, "R1", "C1");
        assertEquals(1.0, data.getValue("R1", "C1").doubleValue(), EPSILON);
        assertEquals(1, data.getRowCount());
        assertEquals(1, data.getColumnCount());
        
        // existing keys
        data.setValue(2.0, "R1", "C1");
        assertEquals(2.0, data.getValue("R1", "C1").doubleValue(), EPSILON);
        assertEquals(1, data.getRowCount());
        assertEquals(1, data.getColumnCount());

        // existing row key, new column key
        data.setValue(3.0, "R1", "C2");
        assertEquals(3.0, data.getValue("R1", "C2").doubleValue(), EPSILON);
        assertEquals(1, data.getRowCount());
        assertEquals(2, data.getColumnCount());
        
        // existing column key, new row key
        data.setValue(4.0, "R2", "C2");
        assertEquals(4.0, data.getValue("R2", "C2").doubleValue(), EPSILON);
        assertEquals(2, data.getRowCount());
        assertEquals(2, data.getColumnCount());
        assertNull(data.getValue("R2", "C1"));
        
        // new row key and new column key
        data.setValue(5.0, "R3", "C3");
        assertEquals(5.0, data.getValue("R3", "C3").doubleValue(), EPSILON);
        assertEquals(3, data.getRowCount());
        assertEquals(3, data.getColumnCount());
        assertNull(data.getValue("R1", "C3"));
    }
    
    @Test
    public void testEquals() {
        DefaultKeyedValues2D<String, String, Number> d1 
                = new DefaultKeyedValues2D<String, String, Number>();
        DefaultKeyedValues2D<String, String, Number> d2 
                = new DefaultKeyedValues2D<String, String, Number>();
        assertTrue(d1.equals(d2));
        assertFalse(d1.equals(null));
        
        d1.setValue(1.0, "R1", "C1");
        assertFalse(d1.equals(d2));
        d2.setValue(1.0, "R1", "C1");
        assertTrue(d1.equals(d2));
    }
    
    /**
     * Check for serialization support.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testSerialization() {
        DefaultKeyedValues2D<String, String, Number> d1 
                = new DefaultKeyedValues2D<String, String, Number>();
        d1.setValue(1.0, "R1", "C1");
        DefaultKeyedValues2D<String, String, Number> d2 
                = (DefaultKeyedValues2D<String, String, Number>) 
                TestUtils.serialized(d1);
        assertEquals(d1, d2);
    }
    
}
