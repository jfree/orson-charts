/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2022, by David Gilbert.  All rights reserved.
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
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts home page:
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 */

package org.jfree.chart3d.renderer.xyz;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.jfree.chart3d.TestUtils;

/**
 * Tests for the {@link ScatterXYZRenderer} class.
 */
public class ScatterXYZRendererTest {
    
    @Test
    public void testEquals() {
        ScatterXYZRenderer r1 = new ScatterXYZRenderer();
        ScatterXYZRenderer r2 = new ScatterXYZRenderer();
        assertTrue(r1.equals(r2));
        assertFalse(r1.equals(null));
        
        r1.setSize(1.0);
        assertFalse(r1.equals(r2));
        r2.setSize(1.0);
        assertTrue(r1.equals(r2));
    }
    /**
     * Some checks for serialization support.
     */
    @Test
    public void testSerialization() {
        ScatterXYZRenderer r1 = new ScatterXYZRenderer();
        ScatterXYZRenderer r2 = (ScatterXYZRenderer) TestUtils.serialized(r1);
        assertTrue(r1.equals(r2));
    }
}
