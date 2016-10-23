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

package com.orsoncharts.data.xyz;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import com.orsoncharts.TestUtils;
import com.orsoncharts.data.Dataset3DChangeEvent;
import com.orsoncharts.data.Dataset3DChangeListener;

/**
 * Tests for the {@link XYZSeriesCollection} class.
 */
public class XYZSeriesCollectionTest implements Dataset3DChangeListener {

    private Dataset3DChangeEvent lastEvent;
    
    @Before
    public void setup() {
        this.lastEvent = null;
    }
    
    @Override
    public void datasetChanged(Dataset3DChangeEvent event) {
        this.lastEvent = event;
    }
    
    @Test
    public void testGeneral() {
        XYZSeriesCollection<String> dataset = new XYZSeriesCollection<String>();
        assertTrue(dataset.isNotify());               
    }
    
    @Test
    public void testAdd() {
        XYZSeriesCollection<String> dataset = new XYZSeriesCollection<String>();
        XYZSeries<String> s = new XYZSeries<String>("S1");
        dataset.add(s);
        assertEquals(1, dataset.getSeriesCount());
        
        try {
            dataset.add(new XYZSeries<String>("S1"));
            fail("Adding a series with the same name not permitted.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
    
    /**
     * Modifying a dataset should trigger a dataset change event.
     */
    @Test
    public void testEventNotification() {
        XYZSeriesCollection<String> dataset = new XYZSeriesCollection<String>();
        dataset.addChangeListener(this);

        assertNull(this.lastEvent);
        XYZSeries<String> s = new XYZSeries<String>("S1");
        dataset.add(s);
        assertNotNull(this.lastEvent);
        
        this.lastEvent = null;
        s.add(1.0, 2.0, 3.0);
        assertNotNull(this.lastEvent);
        
        this.lastEvent = null;
        s.add(new XYZDataItem(1.0, 2.0, 3.0));
        assertNotNull(this.lastEvent);        

        this.lastEvent = null;
        s.remove(1);
        assertNotNull(this.lastEvent);        
    }

    @Test
    public void testEquals() {
        XYZSeriesCollection<String> c1 = new XYZSeriesCollection<String>();
        XYZSeriesCollection<String> c2 = new XYZSeriesCollection<String>();
        assertTrue(c1.equals(c2));
        assertFalse(c1.equals(null));
        
        XYZSeries<String> s1 = new XYZSeries<String>("S");
        s1.add(1.0, 2.0, 3.0);
        c1.add(s1);
        assertFalse(c1.equals(c2));
        XYZSeries<String> s2 = new XYZSeries<String>("S");
        s2.add(1.0, 2.0, 3.0);
        c2.add(s2);
        assertEquals(c1, c2);
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void testSerialization() {
        XYZSeries<String> s1 = new XYZSeries<String>("S");
        s1.add(1.0, 2.0, 3.0);
        XYZSeriesCollection<String> c1 = new XYZSeriesCollection<String>();
        c1.add(s1);
        XYZSeriesCollection<String> c2 = (XYZSeriesCollection) TestUtils.serialized(c1);
        assertEquals(c1, c2);
    }
    
    @Test
    public void checkToString() {
        XYZSeriesCollection<String> c = new XYZSeriesCollection<String>();
        assertEquals("[]", c.toString());
        
        XYZSeries<String> s1 = new XYZSeries<String>("S1");
        c.add(s1);
        assertEquals("[[\"S1\", []]]", c.toString());
        
        s1.add(1.0, 2.0, 3.0);
        assertEquals("[[\"S1\", [[1.0, 2.0, 3.0]]]]", c.toString());
     
        s1.add(4.0, 5.0, 6.0);
        assertEquals("[[\"S1\", [[1.0, 2.0, 3.0], [4.0, 5.0, 6.0]]]]", 
                c.toString());
        
        XYZSeries<String> s2 = new XYZSeries<String>("S2");
        c.add(s2);
        assertEquals("[[\"S1\", [[1.0, 2.0, 3.0], [4.0, 5.0, 6.0]]], "
                + "[\"S2\", []]]", c.toString());
        
        s2.add(7.0, Double.NaN, 9.0);
        assertEquals("[[\"S1\", [[1.0, 2.0, 3.0], [4.0, 5.0, 6.0]]], " 
                + "[\"S2\", [[7.0, null, 9.0]]]]", c.toString());
    }

}
