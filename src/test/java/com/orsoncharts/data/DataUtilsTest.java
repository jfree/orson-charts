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

package com.orsoncharts.data;

import com.orsoncharts.data.xyz.XYZSeries;
import com.orsoncharts.data.xyz.XYZSeriesCollection;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

/**
 * Tests for the {@link DataUtils} class.
 */
public class DataUtilsTest {
    
    private static final double EPSILON = 0.0000001;
    
    @Test
    public void testCount() {
        DefaultKeyedValues3D dataset = new DefaultKeyedValues3D();
        dataset.setValue(1.0, "S1", "R1", "C1");
        assertEquals(1, DataUtils.count(dataset, "S1"));
        dataset.setValue(2.0, "S1", "R2", "C1");
        assertEquals(2, DataUtils.count(dataset, "S1"));
        dataset.setValue(null, "S1", "R2", "C1");
        assertEquals(1, DataUtils.count(dataset, "S1"));
        dataset.setValue(null, "S1", "R1", "C1");
        assertEquals(0, DataUtils.count(dataset, "S1"));
    }
    
    @Test
    public void testCountForRow() {
        DefaultKeyedValues3D dataset = new DefaultKeyedValues3D();
        dataset.setValue(1.0, "S1", "R1", "C1");
        assertEquals(1, DataUtils.countForRow(dataset, "R1"));
        dataset.setValue(2.0, "S2", "R1", "C1");
        assertEquals(2, DataUtils.countForRow(dataset, "R1"));
        dataset.setValue(null, "S2", "R1", "C1");
        assertEquals(1, DataUtils.countForRow(dataset, "R1"));
        dataset.setValue(null, "S1", "R1", "C1");
        assertEquals(0, DataUtils.countForRow(dataset, "R1"));
    }
    
    @Test
    public void testCountForColumn() {
        DefaultKeyedValues3D dataset = new DefaultKeyedValues3D();
        dataset.setValue(1.0, "S1", "R1", "C1");
        assertEquals(1, DataUtils.countForColumn(dataset, "C1"));
        dataset.setValue(2.0, "S1", "R2", "C1");
        assertEquals(2, DataUtils.countForColumn(dataset, "C1"));
        dataset.setValue(null, "S1", "R2", "C1");
        assertEquals(1, DataUtils.countForColumn(dataset, "C1"));
        dataset.setValue(null, "S1", "R1", "C1");
        assertEquals(0, DataUtils.countForColumn(dataset, "C1"));
    }
    
    @Test
    public void testTotal() {
        DefaultKeyedValues values = new DefaultKeyedValues();
        assertEquals(0.0, DataUtils.total(values), EPSILON);
        
        values.put("K1", 1.0);
        assertEquals(1.0, DataUtils.total(values), EPSILON);
        values.put("K2", 2.0);
        assertEquals(3.0, DataUtils.total(values), EPSILON);
        values.put("K3", 3.0);
        assertEquals(6.0, DataUtils.total(values), EPSILON);
        values.put("K2", null);
        assertEquals(4.0, DataUtils.total(values), EPSILON);
    }
    
    @Test
    public void testTotal_KeyedValues3D() {
        DefaultKeyedValues3D<Double> data = new DefaultKeyedValues3D<Double>();
        data.setValue(1.0, "S1", "R1", "C1");
        assertEquals(1.0, DataUtils.total(data, "S1"), EPSILON);
        data.setValue(null, "S1", "R2", "C1");
        assertEquals(1.0, DataUtils.total(data, "S1"), EPSILON);
        data.setValue(2.0, "S1", "R2", "C1");
        assertEquals(3.0, DataUtils.total(data, "S1"), EPSILON);
    }
    
    @Test
    public void testTotal_XYZDataset() {
        XYZSeries s1 = new XYZSeries("S1");
        XYZSeriesCollection dataset = new XYZSeriesCollection();
        dataset.add(s1);
        assertEquals(0.0, DataUtils.total(dataset, "S1"), EPSILON);
        s1.add(1.0, 2.0, 3.0);
        assertEquals(2.0, DataUtils.total(dataset, "S1"), EPSILON);
        s1.add(11.0, 12.0, 13.0);
        assertEquals(14.0, DataUtils.total(dataset, "S1"), EPSILON);
    }
    
    @Test
    public void testStackSubTotal() {
        DefaultKeyedValues3D<Number> data = new DefaultKeyedValues3D<Number>();
        double[] result = DataUtils.stackSubTotal(data, 0.0, 0, 0, 0);
        assertArrayEquals(result, new double[] { 0.0, 0.0 }, EPSILON);
        result = DataUtils.stackSubTotal(data, -1.0, 0, 0, 0);
        assertArrayEquals(result, new double[] { -1.0, -1.0 }, EPSILON);
        
        data.setValue(1.0, "S0", "R1", "C1");
        result = DataUtils.stackSubTotal(data, 0.0, 0, 0, 0);
        assertArrayEquals(result, new double[] { 0.0, 0.0 }, EPSILON);
        result = DataUtils.stackSubTotal(data, 0.0, 1, 0, 0);
        assertArrayEquals(result, new double[] { 0.0, 1.0 }, EPSILON);
        
        data.setValue(2.0, "S1", "R1", "C1");
        result = DataUtils.stackSubTotal(data, 0.0, 0, 0, 0);
        assertArrayEquals(result, new double[] { 0.0, 0.0 }, EPSILON);
        result = DataUtils.stackSubTotal(data, 0.0, 1, 0, 0);
        assertArrayEquals(result, new double[] { 0.0, 1.0 }, EPSILON);
        result = DataUtils.stackSubTotal(data, 0.0, 2, 0, 0);
        assertArrayEquals(result, new double[] { 0.0, 3.0 }, EPSILON);
        
        data.setValue(-4.0, "S2", "R1", "C1");
        result = DataUtils.stackSubTotal(data, 0.0, 2, 0, 0);
        assertArrayEquals(result, new double[] { 0.0, 3.0 }, EPSILON);
        result = DataUtils.stackSubTotal(data, 0.0, 3, 0, 0);
        assertArrayEquals(result, new double[] { -4.0, 3.0 }, EPSILON);
        
        data.setValue(null, "S1", "R1", "C1");
        result = DataUtils.stackSubTotal(data, 0.0, 3, 0, 0);
        assertArrayEquals(result, new double[] { -4.0, 1.0 }, EPSILON);
        result = DataUtils.stackSubTotal(data, 2.0, 3, 0, 0);
        assertArrayEquals(result, new double[] { -2.0, 3.0 }, EPSILON);

    }
}
