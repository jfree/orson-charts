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

package org.jfree.chart3d.renderer.category;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.awt.Color;
import org.junit.jupiter.api.Test;
import org.jfree.chart3d.TestUtils;

/**
 * Tests for the {@link AreaRenderer3D} class.
 */
public class AreaRenderer3DTest {

    @Test
    public void testEquals() {
        AreaRenderer3D r1 = new AreaRenderer3D();
        AreaRenderer3D r2 = new AreaRenderer3D();
        assertTrue(r1.equals(r2));
        assertFalse(r1.equals(null));
        
        // base
        r1.setBase(9.9);
        assertFalse(r1.equals(r2));
        r2.setBase(9.9);
        assertTrue(r1.equals(r2));

        // baseColor
        r1.setBaseColor(Color.BLUE);
        assertFalse(r1.equals(r2));
        r2.setBaseColor(Color.BLUE);
        assertTrue(r1.equals(r2));
        
        // depth
        r1.setDepth(8.8);
        assertFalse(r1.equals(r2));
        r2.setDepth(8.8);
        assertTrue(r1.equals(r2));
        
        // notify
        r1.setNotify(false);
        assertFalse(r1.equals(r2));
        r2.setNotify(false);
        assertTrue(r1.equals(r2));
    }
    
    @Test
    public void testSerialization() {
        AreaRenderer3D r1 = new AreaRenderer3D();
        AreaRenderer3D r2 = (AreaRenderer3D) TestUtils.serialized(r1);
        assertTrue(r1.equals(r2));
    }
}
