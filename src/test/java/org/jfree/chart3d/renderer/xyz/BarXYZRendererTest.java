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

package org.jfree.chart3d.renderer.xyz;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.awt.Color;
import org.junit.jupiter.api.Test;
import org.jfree.chart3d.TestUtils;

/**
 * Tests for the {@link BarXYZRenderer} class.
 */
public class BarXYZRendererTest {
 
    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        BarXYZRenderer r1 = new BarXYZRenderer();
        BarXYZRenderer r2 = new BarXYZRenderer();
        assertTrue(r1.equals(r2));
        assertFalse(r1.equals(null));
        
        r1.setBase(1.1);
        assertFalse(r1.equals(r2));
        r2.setBase(1.1);
        assertTrue(r1.equals(r2));

        r1.setBarXWidth(2.2);
        assertFalse(r1.equals(r2));
        r2.setBarXWidth(2.2);
        assertTrue(r1.equals(r2));

        r1.setBarZWidth(3.3);
        assertFalse(r1.equals(r2));
        r2.setBarZWidth(3.3);
        assertTrue(r1.equals(r2));

        // baseColorSource
        r1.setBaseColorSource(null);
        assertFalse(r1.equals(r2));
        r2.setBaseColorSource(null);
        assertTrue(r1.equals(r2));
        
        r1.setBaseColorSource(new StandardXYZColorSource(Color.CYAN));
        assertFalse(r1.equals(r2));
        r2.setBaseColorSource(new StandardXYZColorSource(Color.CYAN));
        assertTrue(r1.equals(r2));
        
        // topPaintSource
        r1.setBaseColorSource(null);
        assertFalse(r1.equals(r2));
        r2.setBaseColorSource(null);
        assertTrue(r1.equals(r2));
        
        r1.setBaseColorSource(new StandardXYZColorSource(Color.YELLOW));
        assertFalse(r1.equals(r2));
        r2.setBaseColorSource(new StandardXYZColorSource(Color.YELLOW));
        assertTrue(r1.equals(r2));
        
        // regular paintSource
        r1.setBaseColorSource(new StandardXYZColorSource(Color.RED));
        assertFalse(r1.equals(r2));
        r2.setBaseColorSource(new StandardXYZColorSource(Color.RED));
        assertTrue(r1.equals(r2));
    }

    /**
     * Some checks for serialization support.
     */
    @Test
    public void testSerialization() {
        BarXYZRenderer r1 = new BarXYZRenderer();
        BarXYZRenderer r2 = (BarXYZRenderer) TestUtils.serialized(r1);
        assertTrue(r1.equals(r2));
    }

}
