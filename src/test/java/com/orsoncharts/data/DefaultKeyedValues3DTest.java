/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Some tests for the {@link DefaultKeyedValues3D} class.
 */
public class DefaultKeyedValues3DTest {
    
    private static final double EPSILON = 0.000000001;
    
    @Test
    public void testSetValue() {
        DefaultKeyedValues3D<Number> data = new DefaultKeyedValues3D<Number>();
        
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
}
