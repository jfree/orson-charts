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
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import com.orsoncharts.Range;
import com.orsoncharts.data.category.StandardCategoryDataset3D;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.data.xyz.XYZSeries;
import com.orsoncharts.data.xyz.XYZSeriesCollection;

/**
 * Tests for the {@link DataUtils} class.
 */
public class DataUtilsTest {
    
    private static final double EPSILON = 0.0000001;
    
    @Test
    public void testCount() {
        DefaultKeyedValues3D<String, String, String, Number> dataset 
                = new DefaultKeyedValues3D<String, String, String, Number>();
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
        DefaultKeyedValues3D<String, String, String, Number> dataset 
                = new DefaultKeyedValues3D<String, String, String, Number>();
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
        DefaultKeyedValues3D<String, String, String, Number> dataset 
                = new DefaultKeyedValues3D<String, String, String, Number>();
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
        DefaultKeyedValues<String, Number> values 
                = new DefaultKeyedValues<String, Number>();
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
        DefaultKeyedValues3D<String, String, String, Double> data 
                = new DefaultKeyedValues3D<String, String, String, Double>();
        data.setValue(1.0, "S1", "R1", "C1");
        assertEquals(1.0, DataUtils.total(data, "S1"), EPSILON);
        data.setValue(null, "S1", "R2", "C1");
        assertEquals(1.0, DataUtils.total(data, "S1"), EPSILON);
        data.setValue(2.0, "S1", "R2", "C1");
        assertEquals(3.0, DataUtils.total(data, "S1"), EPSILON);
    }
    
    @Test
    public void testTotal_XYZDataset() {
        XYZSeries<String> s1 = new XYZSeries<String>("S1");
        XYZSeriesCollection<String> dataset = new XYZSeriesCollection<String>();
        dataset.add(s1);
        assertEquals(0.0, DataUtils.total(dataset, "S1"), EPSILON);
        s1.add(1.0, 2.0, 3.0);
        assertEquals(2.0, DataUtils.total(dataset, "S1"), EPSILON);
        s1.add(11.0, 12.0, 13.0);
        assertEquals(14.0, DataUtils.total(dataset, "S1"), EPSILON);
    }
    
    @Test
    public void testStackSubTotal() {
        DefaultKeyedValues3D<String, String, String, Number> data 
                = new DefaultKeyedValues3D<String, String, String, Number>();
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
    
    @Test
    public void checkExtractXYZDatasetFromColumns() {
        StandardCategoryDataset3D<String, String, String> source 
                = new StandardCategoryDataset3D<String, String, String>();
        source.addValue(1.0, "S1", "R1", "C1");
        source.addValue(2.0, "S1", "R1", "C2");
        source.addValue(3.0, "S1", "R1", "C3");
        source.addValue(4.0, "S1", "R2", "C1");
        source.addValue(5.0, "S1", "R2", "C2");
        source.addValue(6.0, "S1", "R2", "C3");
        source.addValue(11.0, "S2", "R1", "C1");
        source.addValue(12.0, "S2", "R1", "C2");
        source.addValue(13.0, "S2", "R1", "C3");
        source.addValue(14.0, "S2", "R2", "C1");
        source.addValue(15.0, "S2", "R2", "C2");
        source.addValue(16.0, "S2", "R2", "C3");
        XYZDataset dataset = DataUtils.extractXYZDatasetFromColumns(source, 
                "C3", "C1", "C2");
        assertEquals(2, dataset.getSeriesCount());
        assertEquals("S1", dataset.getSeriesKey(0));
        assertEquals("S2", dataset.getSeriesKey(1));
        assertEquals(2, dataset.getItemCount(0));
        assertEquals(2, dataset.getItemCount(1));
        assertEquals(3.0, dataset.getX(0, 0), EPSILON);
        assertEquals(1.0, dataset.getY(0, 0), EPSILON);
        assertEquals(2.0, dataset.getZ(0, 0), EPSILON);
        assertEquals(6.0, dataset.getX(0, 1), EPSILON);
        assertEquals(4.0, dataset.getY(0, 1), EPSILON);
        assertEquals(5.0, dataset.getZ(0, 1), EPSILON);
        assertEquals(13.0, dataset.getX(1, 0), EPSILON);
        assertEquals(11.0, dataset.getY(1, 0), EPSILON);
        assertEquals(12.0, dataset.getZ(1, 0), EPSILON);
        assertEquals(16.0, dataset.getX(1, 1), EPSILON);
        assertEquals(14.0, dataset.getY(1, 1), EPSILON);
        assertEquals(15.0, dataset.getZ(1, 1), EPSILON);
    }
    
    @Test
    public void testFindXRange() {
        XYZSeries<String> s1 = new XYZSeries<String>("S1");
        XYZSeriesCollection<String> dataset = new XYZSeriesCollection<String>();
        dataset.add(s1);
        assertNull(DataUtils.findXRange(dataset));
        assertNull(DataUtils.findXRange(dataset, Double.NaN));
        assertNull(DataUtils.findXRange(dataset, Double.NaN, false));
        assertEquals(new Range(1.0, 1.0), DataUtils.findXRange(dataset, 1.0));
        assertEquals(new Range(1.0, 1.0), DataUtils.findXRange(dataset, 1.0, 
                false));
        
        s1.add(5.0, 6.0, 7.0);
        assertEquals(new Range(5.0, 5.0), DataUtils.findXRange(dataset));
        assertEquals(new Range(-1.0, 5.0), DataUtils.findXRange(dataset, -1.0));
        assertEquals(new Range(-1.0, 5.0), DataUtils.findXRange(dataset, -1.0, 
                false));

        s1.add(Double.NaN, 8.0, 9.0);
        assertEquals(new Range(5.0, 5.0), DataUtils.findXRange(dataset));
        assertEquals(new Range(-1.0, 5.0), DataUtils.findXRange(dataset, -1.0));
        assertEquals(new Range(-1.0, 5.0), DataUtils.findXRange(dataset, -1.0, 
                false));

        s1.add(Double.NEGATIVE_INFINITY, 8.0, 9.0);
        assertEquals(new Range(5.0, 5.0), DataUtils.findXRange(dataset));
        assertEquals(new Range(-1.0, 5.0), DataUtils.findXRange(dataset, -1.0));
        assertEquals(new Range(Double.NEGATIVE_INFINITY, 5.0), 
                DataUtils.findXRange(dataset, -1.0, false));

        s1.add(Double.POSITIVE_INFINITY, 10.0, 11.0);
        assertEquals(new Range(5.0, 5.0), DataUtils.findXRange(dataset));
        assertEquals(new Range(-1.0, 5.0), DataUtils.findXRange(dataset, -1.0));
        assertEquals(new Range(Double.NEGATIVE_INFINITY, 
                Double.POSITIVE_INFINITY), DataUtils.findXRange(dataset, -1.0, 
                false));
    }

    @Test
    public void testFindYRange() {
        XYZSeries<String> s1 = new XYZSeries<String>("S1");
        XYZSeriesCollection<String> dataset = new XYZSeriesCollection<String>();
        dataset.add(s1);
        assertNull(DataUtils.findYRange(dataset));
        assertNull(DataUtils.findYRange(dataset, Double.NaN));
        assertNull(DataUtils.findYRange(dataset, Double.NaN, false));
        assertEquals(new Range(1.0, 1.0), DataUtils.findYRange(dataset, 1.0));
        assertEquals(new Range(1.0, 1.0), DataUtils.findYRange(dataset, 1.0, 
                false));
        
        s1.add(5.0, 6.0, 7.0);
        assertEquals(new Range(6.0, 6.0), DataUtils.findYRange(dataset));
        assertEquals(new Range(-1.0, 6.0), DataUtils.findYRange(dataset, -1.0));
        assertEquals(new Range(-1.0, 6.0), DataUtils.findYRange(dataset, -1.0, 
                false));

        s1.add(8.0, Double.NaN, 9.0);
        assertEquals(new Range(6.0, 6.0), DataUtils.findYRange(dataset));
        assertEquals(new Range(-1.0, 6.0), DataUtils.findYRange(dataset, -1.0));
        assertEquals(new Range(-1.0, 6.0), DataUtils.findYRange(dataset, -1.0, 
                false));

        s1.add(8.0, Double.NEGATIVE_INFINITY, 9.0);
        assertEquals(new Range(6.0, 6.0), DataUtils.findYRange(dataset));
        assertEquals(new Range(-1.0, 6.0), DataUtils.findYRange(dataset, -1.0));
        assertEquals(new Range(Double.NEGATIVE_INFINITY, 6.0), 
                DataUtils.findYRange(dataset, -1.0, false));

        s1.add(10.0, Double.POSITIVE_INFINITY, 11.0);
        assertEquals(new Range(6.0, 6.0), DataUtils.findYRange(dataset));
        assertEquals(new Range(-1.0, 6.0), DataUtils.findYRange(dataset, -1.0));
        assertEquals(new Range(Double.NEGATIVE_INFINITY, 
                Double.POSITIVE_INFINITY), DataUtils.findYRange(dataset, -1.0, 
                false));
    }
    
    @Test
    public void testFindZRange() {
        XYZSeries<String> s1 = new XYZSeries<String>("S1");
        XYZSeriesCollection<String> dataset = new XYZSeriesCollection<String>();
        dataset.add(s1);
        assertNull(DataUtils.findZRange(dataset));
        assertNull(DataUtils.findZRange(dataset, Double.NaN));
        assertNull(DataUtils.findZRange(dataset, Double.NaN, false));
        assertEquals(new Range(1.0, 1.0), DataUtils.findZRange(dataset, 1.0));
        assertEquals(new Range(1.0, 1.0), DataUtils.findZRange(dataset, 1.0, 
                false));
        
        s1.add(5.0, 6.0, 7.0);
        assertEquals(new Range(7.0, 7.0), DataUtils.findZRange(dataset));
        assertEquals(new Range(-1.0, 7.0), DataUtils.findZRange(dataset, -1.0));
        assertEquals(new Range(-1.0, 7.0), DataUtils.findZRange(dataset, -1.0, 
                false));

        s1.add(8.0, 9.0, Double.NaN);
        assertEquals(new Range(7.0, 7.0), DataUtils.findZRange(dataset));
        assertEquals(new Range(-1.0, 7.0), DataUtils.findZRange(dataset, -1.0));
        assertEquals(new Range(-1.0, 7.0), DataUtils.findZRange(dataset, -1.0, 
                false));

        s1.add(8.0, 9.0, Double.NEGATIVE_INFINITY);
        assertEquals(new Range(7.0, 7.0), DataUtils.findZRange(dataset));
        assertEquals(new Range(-1.0, 7.0), DataUtils.findZRange(dataset, -1.0));
        assertEquals(new Range(Double.NEGATIVE_INFINITY, 7.0), 
                DataUtils.findZRange(dataset, -1.0, false));

        s1.add(10.0, 11.0, Double.POSITIVE_INFINITY);
        assertEquals(new Range(7.0, 7.0), DataUtils.findZRange(dataset));
        assertEquals(new Range(-1.0, 7.0), DataUtils.findZRange(dataset, -1.0));
        assertEquals(new Range(Double.NEGATIVE_INFINITY, 
                Double.POSITIVE_INFINITY), DataUtils.findZRange(dataset, -1.0, 
                false));
    }
}
