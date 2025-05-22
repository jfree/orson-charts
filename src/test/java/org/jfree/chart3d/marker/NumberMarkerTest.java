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

package org.jfree.chart3d.marker;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import org.jfree.chart3d.TestUtils;

/**
 * Tests for the {@link NumberMarker} class.
 */
public class NumberMarkerTest {
    
    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        NumberMarker nm1 = new NumberMarker(1.0); 
        NumberMarker nm2 = new NumberMarker(1.0); 
        assertTrue(nm1.equals(nm2));
        assertFalse(nm1.equals(null));
        
        nm1 = new NumberMarker(1.1);
        assertFalse(nm1.equals(nm2));
        nm2 = new NumberMarker(1.1);
        assertTrue(nm1.equals(nm2));
    }
    
    /**
     * Checks for serialization support.
     */
    @Test
    public void testSerialization() {
        NumberMarker nm1 = new NumberMarker(1.0);  
        NumberMarker nm2 = (NumberMarker) TestUtils.serialized(nm1);
        assertTrue(nm1.equals(nm2));
    }
}
