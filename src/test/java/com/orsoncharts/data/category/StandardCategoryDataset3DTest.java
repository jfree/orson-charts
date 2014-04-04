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
    }
    
    @Test
    public void checkEquals() {
        StandardCategoryDataset3D d1 = new StandardCategoryDataset3D();
        StandardCategoryDataset3D d2 = new StandardCategoryDataset3D();
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
    public void checkSerialization() {
        StandardCategoryDataset3D d1 = new StandardCategoryDataset3D();
        d1.addValue(1.0, "S1", "R1", "C1");
        StandardCategoryDataset3D d2 
                = (StandardCategoryDataset3D) TestUtils.serialized(d1);
        assertEquals(d1, d2);
    }
    
    @Test
    public void checkToString() {
        StandardCategoryDataset3D dataset = new StandardCategoryDataset3D();
        assertEquals("{}", dataset.toString());
        
        dataset.addValue(1.0, "S1", "R1", "C1");
        assertEquals("{\"columnKeys\": [\"C1\"], \"rowKeys\": [\"R1\"], "
                + "\"data\": [{\"seriesKey\": \"S1\", "
                + "\"rows\": [[\"R1\", [1.0]]]}]}", dataset.toString());
        
        dataset.addValue(2.0, "S1", "R1", "C2");
        assertEquals("{\"columnKeys\": [\"C1\", \"C2\"], \"rowKeys\": [\"R1\"], "
                + "\"data\": [{\"seriesKey\": \"S1\", " 
                + "\"rows\": [[\"R1\", [1.0, 2.0]]]}]}",
                dataset.toString());
        
        dataset.addValue(3.0, "S1", "R2", "C2");
        assertEquals("{\"columnKeys\": [\"C1\", \"C2\"], "
                + "\"rowKeys\": [\"R1\", \"R2\"], "
                + "\"data\": [{\"seriesKey\": \"S1\", "
                + "\"rows\": [[\"R1\", [1.0, 2.0]], [\"R2\", [null, 3.0]]]}]}",
                dataset.toString());

        dataset.addValue(4.0, "S2", "R2", "C2");
        assertEquals("{\"columnKeys\": [\"C1\", \"C2\"], "
                + "\"rowKeys\": [\"R1\", \"R2\"], "
                + "\"data\": [{\"seriesKey\": \"S1\", " 
                + "\"rows\": [[\"R1\", [1.0, 2.0]], [\"R2\", [null, 3.0]]]}, "
                + "{\"seriesKey\": \"S2\", \"rows\": [[\"R2\", [null, 4.0]]]}]}",
                dataset.toString());
    }
  
}
