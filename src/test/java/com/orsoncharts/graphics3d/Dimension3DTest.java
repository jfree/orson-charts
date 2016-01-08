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

package com.orsoncharts.graphics3d;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;
import com.orsoncharts.TestUtils;

/**
 * Tests for the {@link Dimension3D} class.
 */
public class Dimension3DTest {
    
    @Test
    public void checkEquals() {
        Dimension3D dim1 = new Dimension3D(1, 2, 3);
        Dimension3D dim2 = new Dimension3D(1, 2, 3);
        assertEquals(dim1, dim2);
    
        dim1 = new Dimension3D(2, 2, 3);
        assertNotEquals(dim1, dim2);
        dim2 = new Dimension3D(2, 2, 3);
        assertEquals(dim1, dim2);
    
        dim1 = new Dimension3D(2, 1, 3);
        assertNotEquals(dim1, dim2);
        dim2 = new Dimension3D(2, 1, 3);
        assertEquals(dim1, dim2);
  
        dim1 = new Dimension3D(2, 1, 4);
        assertNotEquals(dim1, dim2);
        dim2 = new Dimension3D(2, 1, 4);
        assertEquals(dim1, dim2);
    }
    
    /**
     * A check for serialization.
     */
    @Test
    public void testSerialization() {
        Dimension3D dim1 = new Dimension3D(1, 2, 3);
        Dimension3D dim2 = (Dimension3D) TestUtils.serialized(dim1);
        assertEquals(dim1, dim2);
    }
}
