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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import com.orsoncharts.data.category.StandardCategoryDataset3D;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.data.xyz.XYZSeries;
import com.orsoncharts.data.xyz.XYZSeriesCollection;

/**
 * Some tests for the {@link JSONUtils} class.
 */
public class JSONUtilsTest {
    
    /**
     * Some checks for the readKeyedValues() method.
     */
    @Test
    public void checkReadKeyedValues() {
        String json = "[]";
        KeyedValues<String, ? extends Number> dkv 
                = JSONUtils.readKeyedValues(json);
        assertTrue(dkv.getItemCount() == 0);
        
        json = "[[\"Key 1\", 1.23], [\"Key 2\", null], [\"NaN\", null], " 
                + "[\"MIN_VALUE\", 4.9E-324], " 
                + "[\"MAX_VALUE\", 1.7976931348623157E308], " 
                + "[\"POSITIVE_INFINITY\", null]]";
        dkv = JSONUtils.readKeyedValues(json);
        assertTrue(dkv.getItemCount() == 6);
        assertEquals(1.23, dkv.getValue("Key 1"));
        assertEquals(1.23, dkv.getValue(0));
        assertNull(dkv.getValue("Key 2"));
        assertNull(dkv.getValue(1));
        assertNull(dkv.getValue("NaN"));
        assertNull(dkv.getValue(2));
        assertEquals(Double.MIN_VALUE, dkv.getValue("MIN_VALUE"));
        assertEquals(Double.MIN_VALUE, dkv.getValue(3));
        assertEquals(Double.MAX_VALUE, dkv.getValue("MAX_VALUE"));
        assertEquals(Double.MAX_VALUE, dkv.getValue(4));
        assertNull(dkv.getValue("POSITIVE_INFINITY"));
        assertNull(dkv.getValue(5));
    }
    
    @Test
    public void checkWriteKeyedValues() {
        // some standard cases are already checked in the tests for the
        // toString() method in StandardPieDataset3D
        DefaultKeyedValues<String, Number> dkv 
                = new DefaultKeyedValues<String, Number>();
        dkv.put("\"", 1.0);
        assertEquals("[[\"\\\"\", 1.0]]", JSONUtils.writeKeyedValues(dkv));
        dkv.clear();
        
        dkv.put("\\", 1.0);
        assertEquals("[[\"\\\\\", 1.0]]", JSONUtils.writeKeyedValues(dkv));
        dkv.clear();

        dkv.put("\t", 1.0);
        assertEquals("[[\"\\t\", 1.0]]", JSONUtils.writeKeyedValues(dkv));
        dkv.clear();
        
        dkv.put("\n", 1.0);
        assertEquals("[[\"\\n\", 1.0]]", JSONUtils.writeKeyedValues(dkv));
        dkv.clear();
        
        dkv.put("\f", 1.0);
        assertEquals("[[\"\\f\", 1.0]]", JSONUtils.writeKeyedValues(dkv));
        dkv.clear();

        dkv.put("\b", 1.0);
        assertEquals("[[\"\\b\", 1.0]]", JSONUtils.writeKeyedValues(dkv));
        dkv.clear();

        dkv.put("\r", 1.0);
        assertEquals("[[\"\\r\", 1.0]]", JSONUtils.writeKeyedValues(dkv));
        dkv.clear();

        dkv.put("/", 1.0);
        assertEquals("[[\"\\/\", 1.0]]", JSONUtils.writeKeyedValues(dkv));
        dkv.clear();
    }
    
    @Test
    public void checkReadKeyedValues2D() {
        String json = "{}";
        KeyedValues2D<String, String, Number> dkv2d 
                = JSONUtils.readKeyedValues2D(json);
        assertEquals(0, dkv2d.getRowCount());
        assertEquals(0, dkv2d.getColumnCount());
        
        json = "{\"columnKeys\": [\"C1\", \"C2\", \"C3\"], \"rows\": "
                + "[[\"R1\", [1.0, 2.0, 3.0]], [\"R2\", [4.0, 5.0, 6.0]]]}";
        dkv2d = JSONUtils.readKeyedValues2D(json);
        assertEquals(2, dkv2d.getRowCount());
        assertEquals(3, dkv2d.getColumnCount());
        assertEquals(1.0, dkv2d.getValue("R1", "C1"));
        assertEquals(2.0, dkv2d.getValue("R1", "C2"));
        assertEquals(3.0, dkv2d.getValue("R1", "C3"));
        assertEquals(4.0, dkv2d.getValue("R2", "C1"));
        assertEquals(5.0, dkv2d.getValue("R2", "C2"));
        assertEquals(6.0, dkv2d.getValue("R2", "C3"));
    }
    
    @Test 
    public void checkWriteKeyedValues2D() {
        DefaultKeyedValues2D<String, String, Number> dkv2d 
                = new DefaultKeyedValues2D<String, String, Number>();
        String expected = "{}";
        assertEquals(expected, JSONUtils.writeKeyedValues2D(dkv2d));
        
        dkv2d.setValue(1.0, "R1", "C1");
        expected = "{\"columnKeys\": [\"C1\"], \"rows\": [[\"R1\", [1.0]]]}";
        assertEquals(expected, JSONUtils.writeKeyedValues2D(dkv2d));        
    }
    
    @Test
    public void checkReadKeyedValues3D() {
        String json = "{}";
        KeyedValues3D<String, String, String, ? extends Number> dkv3d 
                = JSONUtils.readKeyedValues3D(json);
        assertEquals(0, dkv3d.getRowCount());
        assertEquals(0, dkv3d.getColumnCount());
        
        json = "{\"columnKeys\": [\"C1\", \"C2\"], \"rowKeys\": "
                + "[\"R1\", \"R2\"], \"data\": [{\"seriesKey\": \"S1\", "
                + "\"rows\": {\"R1\": [1.0, 2.0], \"R2\": [3.0, 4.0]}}, "
                + "{\"seriesKey\": \"S2\", \"rows\": {\"R2\": [5.0, 6.0]}}]}";
        dkv3d = JSONUtils.readKeyedValues3D(json);
        assertEquals(2, dkv3d.getSeriesCount());
        assertEquals("S1", dkv3d.getSeriesKey(0));
        assertEquals("S2", dkv3d.getSeriesKey(1));
        assertEquals(2, dkv3d.getRowCount());
        assertEquals("R1", dkv3d.getRowKey(0));
        assertEquals("R2", dkv3d.getRowKey(1));
        assertEquals(2, dkv3d.getColumnCount());
        assertEquals("C1", dkv3d.getColumnKey(0));
        assertEquals("C2", dkv3d.getColumnKey(1));
        assertEquals(1.0, dkv3d.getValue("S1", "R1", "C1"));
        assertEquals(2.0, dkv3d.getValue("S1", "R1", "C2"));
        assertEquals(3.0, dkv3d.getValue("S1", "R2", "C1"));
        assertEquals(4.0, dkv3d.getValue("S1", "R2", "C2"));
        assertEquals(5.0, dkv3d.getValue("S2", "R2", "C1"));
        assertEquals(6.0, dkv3d.getValue("S2", "R2", "C2"));
    }
    
    @Test
    public void checkWriteKeyedValues3D() {
        StandardCategoryDataset3D<String, String, String> dataset 
                = new StandardCategoryDataset3D<String, String, String>();
        dataset.addValue(1.0, "S1", "R1", "C1");
        dataset.addValue(2.0, "S1", "R1", "C2");
        dataset.addValue(3.0, "S1", "R2", "C1");
        dataset.addValue(4.0, "S1", "R2", "C2");
        // series 2 only has data in row 2
        dataset.addValue(5.0, "S2", "R2", "C1");
        dataset.addValue(6.0, "S2", "R2", "C2");
        String expected = "{\"columnKeys\": [\"C1\", \"C2\"], \"rowKeys\": "
                + "[\"R1\", \"R2\"], \"series\": [{\"seriesKey\": \"S1\", "
                + "\"rows\": [[\"R1\", [1.0, 2.0]], [\"R2\", [3.0, 4.0]]]}, "
                + "{\"seriesKey\": \"S2\", \"rows\": [[\"R2\", [5.0, 6.0]]]}]}";
        assertEquals(expected, JSONUtils.writeKeyedValues3D(dataset));
    }
    
    private static final double EPSILON = 0.0000001;
    
    /**
     * Tests for reading XYZDatasets from JSON format.
     */
    @Test
    public void checkReadXYZDataset() {
        String json = "[]";
        XYZDataset dataset = JSONUtils.readXYZDataset(json);
        assertEquals(0, dataset.getSeriesCount());

        json = "[[\"Series 1\", [[1.0, 2.0, 3.0], [4.0, 5.0, 6.0]]], " 
                + "[\"Series 2\", [[5.5, 6.6, 7.7], [8.8, 9.9, 9.9], " 
                + "[8.4, 9.2, 9.1]]]]";
        dataset = JSONUtils.readXYZDataset(json);
        assertEquals(2, dataset.getSeriesCount());
        assertEquals("Series 1", dataset.getSeriesKey(0));
        assertEquals("Series 2", dataset.getSeriesKey(1));
        assertEquals(2, dataset.getItemCount(0));
        assertEquals(3, dataset.getItemCount(1));
        assertEquals(1.0, dataset.getX(0, 0), EPSILON);
        assertEquals(2.0, dataset.getY(0, 0), EPSILON);
        assertEquals(3.0, dataset.getZ(0, 0), EPSILON);
        assertEquals(4.0, dataset.getX(0, 1), EPSILON);
        assertEquals(5.0, dataset.getY(0, 1), EPSILON);
        assertEquals(6.0, dataset.getZ(0, 1), EPSILON);
        
        assertEquals(5.5, dataset.getX(1, 0), EPSILON);
        assertEquals(6.6, dataset.getY(1, 0), EPSILON);
        assertEquals(7.7, dataset.getZ(1, 0), EPSILON);
        assertEquals(8.8, dataset.getX(1, 1), EPSILON);
        assertEquals(9.9, dataset.getY(1, 1), EPSILON);
        assertEquals(9.9, dataset.getZ(1, 1), EPSILON);
        assertEquals(8.4, dataset.getX(1, 2), EPSILON);
        assertEquals(9.2, dataset.getY(1, 2), EPSILON);
        assertEquals(9.1, dataset.getZ(1, 2), EPSILON);
    }
    
    /**
     * Tests for writing XYZDatasets to JSON format.
     */
    @Test
    public void checkWriteXYZDataset() {
        XYZSeriesCollection<String> dataset = new XYZSeriesCollection<String>();
        assertEquals("[]", JSONUtils.writeXYZDataset(dataset));
        
        XYZSeries<String> s1 = new XYZSeries<String>("S1");
        dataset.add(s1);
        assertEquals("[[\"S1\", []]]", JSONUtils.writeXYZDataset(dataset));

        s1.add(1.0, 2.0, 3.0);
        assertEquals("[[\"S1\", [[1.0, 2.0, 3.0]]]]", 
                JSONUtils.writeXYZDataset(dataset));
        
        XYZSeries<String> s2 = new XYZSeries<String>("S2");
        dataset.add(s2);
        assertEquals("[[\"S1\", [[1.0, 2.0, 3.0]]], [\"S2\", []]]", 
                JSONUtils.writeXYZDataset(dataset));
    }
}
