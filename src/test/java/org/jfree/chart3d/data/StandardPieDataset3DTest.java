/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-present, by David Gilbert.  All rights reserved.
 * 
 * https://github.com/jfree/orson-charts
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
 * license is available to sponsors (higher tiers only) of the JFree projects.
 * For details, please see visit:
 *
 * https://github.com/sponsors/jfree
 * 
 */

package org.jfree.chart3d.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.jfree.chart3d.TestUtils;

/**
 * Tests for the {@link StandardPieDataset} class.
 */
public class StandardPieDataset3DTest {
    
    @Test
    public void testGeneral() {
        StandardPieDataset3D<String> d1 = new StandardPieDataset3D<>();
        assertTrue(d1.isNotify());
    }
    
    @Test
    public void testEquals() {
        StandardPieDataset3D<String> d1 = new StandardPieDataset3D<>();
        StandardPieDataset3D<String> d2 = new StandardPieDataset3D<>();
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
    @SuppressWarnings("unchecked")
    public void testSerialization() {
        StandardPieDataset3D<String> d1 = new StandardPieDataset3D<>();
        StandardPieDataset3D<String> d2 
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
     * {@link JSONUtils#writeKeyedValues3DToJSON(org.jfree.chart3d.data.KeyedValues3D)}
     * method, we are really checking the JSON conversion.
     */
    @Test
    public void checkToString() {
        StandardPieDataset3D<String> dataset = new StandardPieDataset3D<>();
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
