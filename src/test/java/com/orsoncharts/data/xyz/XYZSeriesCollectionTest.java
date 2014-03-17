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

package com.orsoncharts.data.xyz;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import com.orsoncharts.TestUtils;

/**
 * Tests for the {@link XYZSeriesCollection} class.
 */
public class XYZSeriesCollectionTest {
    
    @Test
    public void testEquals() {
        XYZSeriesCollection c1 = new XYZSeriesCollection();
        XYZSeriesCollection c2 = new XYZSeriesCollection();
        assertTrue(c1.equals(c2));
        assertFalse(c1.equals(null));
    }
    
    @Test
    public void testSerialization() {
        XYZSeries s1 = new XYZSeries("S");
        s1.add(1.0, 2.0, 3.0);
        XYZSeriesCollection c1 = new XYZSeriesCollection();
        c1.add(s1);
        XYZSeriesCollection c2 = (XYZSeriesCollection) TestUtils.serialized(c1);
        assertEquals(c1, c2);
    }
    
    @Test
    public void checkToString() {
        XYZSeriesCollection c = new XYZSeriesCollection();
        assertEquals("{}", c.toString());
        
        XYZSeries s1 = new XYZSeries("S1");
        c.add(s1);
        assertEquals("{\"S1\": []}", c.toString());
        
        s1.add(1.0, 2.0, 3.0);
        assertEquals("{\"S1\": [[1.0, 2.0, 3.0]]}", c.toString());
     
        s1.add(4.0, 5.0, 6.0);
        assertEquals("{\"S1\": [[1.0, 2.0, 3.0], [4.0, 5.0, 6.0]]}", 
                c.toString());
        
        XYZSeries s2 = new XYZSeries("S2");
        c.add(s2);
        assertEquals("{\"S1\": [[1.0, 2.0, 3.0], [4.0, 5.0, 6.0]], \"S2\": []}", 
                c.toString());
        
        s2.add(7.0, Double.NaN, 9.0);
        assertEquals("{\"S1\": [[1.0, 2.0, 3.0], [4.0, 5.0, 6.0]], " 
                + "\"S2\": [[7.0, null, 9.0]]}", c.toString());
    }
    
}
