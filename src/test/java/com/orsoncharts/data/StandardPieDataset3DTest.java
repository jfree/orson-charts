/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.data;

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
}
