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

import org.jfree.chart3d.TestUtils;
import java.awt.Color;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link DefaultXYZPaintSource} class.
 */
public class StandardXYZColorSourceTest {
    
    @Test
    public void testConstructors() {
        StandardXYZColorSource scs = new StandardXYZColorSource(Color.RED);
        assertEquals(Color.RED, scs.getColor(0, 0));
        assertEquals(Color.RED, scs.getColor(1, 0));
        
        scs = new StandardXYZColorSource(Color.RED, Color.GREEN, Color.BLUE);
        assertEquals(Color.RED, scs.getColor(0, 0));
        assertEquals(Color.GREEN, scs.getColor(1, 0));
        assertEquals(Color.BLUE, scs.getColor(2, 0));
        
        try {
            scs = new StandardXYZColorSource((Color[]) null);
            fail("Should have thrown an exception.");
        } catch (IllegalArgumentException e) {
            // expected
        }
        
        try {
            scs = new StandardXYZColorSource(Color.RED, null);
            fail("Should have thrown an exception");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
    
    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        StandardXYZColorSource s1 = new StandardXYZColorSource();
        StandardXYZColorSource s2 = new StandardXYZColorSource();
        assertTrue(s1.equals(s2));
        assertFalse(s1.equals(null));
        
        s1 = new StandardXYZColorSource(Color.BLUE);
        assertFalse(s1.equals(s2));
        s2 = new StandardXYZColorSource(Color.BLUE);
        assertTrue(s1.equals(s2));
    }
    
    /**
     * Some checks for serialization support.
     */
    @Test
    public void testSerialization() {
        StandardXYZColorSource ps1 = new StandardXYZColorSource();
        StandardXYZColorSource ps2 
                = (StandardXYZColorSource) TestUtils.serialized(ps1);
        assertTrue(ps1.equals(ps2));
    }

}
