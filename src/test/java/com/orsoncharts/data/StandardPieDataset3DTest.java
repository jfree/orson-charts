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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import com.orsoncharts.TestUtils;

/**
 * Tests for the {@link StandardPieDataset} class.
 */
public class StandardPieDataset3DTest {
    
    @Test
    public void testEquals() {
        StandardPieDataset3D d1 = new StandardPieDataset3D();
        StandardPieDataset3D d2 = new StandardPieDataset3D();
        assertTrue(d1.equals(d2));
        assertFalse(d1.equals(null));
        
        d1.add("K1", 1.0);
        assertFalse(d1.equals(d2));
        d2.add("K1", 1.0);
        assertTrue(d1.equals(d2));
        
        d1.add("K2", null);
        assertFalse(d1.equals(d2));
        d2.add("K2", null);
        assertTrue(d1.equals(d2));
    }
    
    @Test
    public void testSerialization() {
        StandardPieDataset3D d1 = new StandardPieDataset3D();
        StandardPieDataset3D d2 
                = (StandardPieDataset3D) TestUtils.serialized(d1);
        assertTrue(d1.equals(d2));
        
        d1.add("K1", 1.0);
        d2 = (StandardPieDataset3D) TestUtils.serialized(d1);
        assertTrue(d1.equals(d2));
        
        d1.add("K2", null);
        d2 = (StandardPieDataset3D) TestUtils.serialized(d1);
        assertTrue(d1.equals(d2));
    }
    
    /**
     * Checks for the toString() method.  Since this uses the 
     * {@link JSONUtils#writeKeyedValues3DToJSON(com.orsoncharts.data.KeyedValues3D)}
     * method, we are really checking the JSON conversion.
     */
    @Test
    public void checkToString() {
        StandardPieDataset3D dataset = new StandardPieDataset3D();
        assertEquals("[]", dataset.toString());
        dataset.add("key", 1.0);
        assertEquals("[[\"key\", 1.0]]", dataset.toString());
        dataset.add("key", null);
        assertEquals("[[\"key\", null]]", dataset.toString());
        dataset.add("another key", 2.0);
        assertEquals("[[\"key\", null], [\"another key\", 2.0]]", 
                dataset.toString());
    }
}
