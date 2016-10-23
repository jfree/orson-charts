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

package com.orsoncharts.data.category;

import com.orsoncharts.TestUtils;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests for the {@link StandardCategoryDataset3D} class.
 */
public class StandardCategoryDataset3DTest {
    
    @Test
    public void checkGeneral() {
        StandardCategoryDataset3D dataset = new StandardCategoryDataset3D();
        assertEquals(0, dataset.getSeriesCount());
        assertEquals(0, dataset.getRowCount());
        assertEquals(0, dataset.getColumnCount());
        assertTrue(dataset.getSeriesKeys().isEmpty());
        assertTrue(dataset.getRowKeys().isEmpty());
        assertTrue(dataset.getColumnKeys().isEmpty());
        assertTrue(dataset.isNotify());
    }
    
    @Test
    public void checkEquals() {
        StandardCategoryDataset3D<String, String, String> d1 
                = new StandardCategoryDataset3D<String, String, String>();
        StandardCategoryDataset3D<String, String, String> d2 
                = new StandardCategoryDataset3D<String, String, String>();
        assertTrue(d1.equals(d2));
        assertFalse(d1.equals(null));
        
        d1.addValue(1.0, "S1", "R1", "C1");
        assertFalse(d1.equals(d2));
        d2.addValue(1.0, "S1", "R1", "C1");
        assertTrue(d1.equals(d2));
        
        d1.addValue(null, "S1", "R2", "C2");
        assertFalse(d1.equals(d2));
        d2.addValue(null, "S1", "R2", "C2");
        assertTrue(d1.equals(d2));
    }
    
    /**
     * A check for serialization.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void checkSerialization() {
        StandardCategoryDataset3D<String, String, String> d1 
                = new StandardCategoryDataset3D<String, String, String>();
        d1.addValue(1.0, "S1", "R1", "C1");
        StandardCategoryDataset3D<String, String, String> d2 
                = (StandardCategoryDataset3D) TestUtils.serialized(d1);
        assertEquals(d1, d2);
    }
    
    @Test
    public void checkToString() {
        StandardCategoryDataset3D<String, String, String> dataset 
                = new StandardCategoryDataset3D<String, String, String>();
        assertEquals("{}", dataset.toString());
        
        dataset.addValue(1.0, "S1", "R1", "C1");
        assertEquals("{\"columnKeys\": [\"C1\"], \"rowKeys\": [\"R1\"], "
                + "\"series\": [{\"seriesKey\": \"S1\", "
                + "\"rows\": [[\"R1\", [1.0]]]}]}", dataset.toString());
        
        dataset.addValue(2.0, "S1", "R1", "C2");
        assertEquals("{\"columnKeys\": [\"C1\", \"C2\"], \"rowKeys\": [\"R1\"], "
                + "\"series\": [{\"seriesKey\": \"S1\", " 
                + "\"rows\": [[\"R1\", [1.0, 2.0]]]}]}",
                dataset.toString());
        
        dataset.addValue(3.0, "S1", "R2", "C2");
        assertEquals("{\"columnKeys\": [\"C1\", \"C2\"], "
                + "\"rowKeys\": [\"R1\", \"R2\"], "
                + "\"series\": [{\"seriesKey\": \"S1\", "
                + "\"rows\": [[\"R1\", [1.0, 2.0]], [\"R2\", [null, 3.0]]]}]}",
                dataset.toString());

        dataset.addValue(4.0, "S2", "R2", "C2");
        assertEquals("{\"columnKeys\": [\"C1\", \"C2\"], "
                + "\"rowKeys\": [\"R1\", \"R2\"], "
                + "\"series\": [{\"seriesKey\": \"S1\", " 
                + "\"rows\": [[\"R1\", [1.0, 2.0]], [\"R2\", [null, 3.0]]]}, "
                + "{\"seriesKey\": \"S2\", \"rows\": [[\"R2\", [null, 4.0]]]}]}",
                dataset.toString());
    }
  
}
