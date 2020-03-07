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

package com.orsoncharts.plot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Color;
import org.junit.Test;

/**
 * Some tests for the {@link StandardColorSource} class.
 */
public class StandardColorSourceTest {

    @Test
    public void testConstructors() {
        StandardColorSource<String> scs = new StandardColorSource<String>(Color.RED);
        assertEquals(Color.RED, scs.getColor("A"));
        
        scs = new StandardColorSource<String>(Color.RED, Color.GREEN, Color.BLUE);
        assertEquals(Color.RED, scs.getColor("A"));
        assertEquals(Color.GREEN, scs.getColor("B"));
        assertEquals(Color.BLUE, scs.getColor("C"));
        
        try {
            scs = new StandardColorSource<String>((Color[]) null);
            fail("Should have thrown an exception.");
        } catch (IllegalArgumentException e) {
            // expected
        }
        
        try {
            scs = new StandardColorSource<String>(Color.RED, null);
            fail("Should have thrown an exception");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testEquals() {
        StandardColorSource<String> scs1 = new StandardColorSource<String>(Color.RED);
        StandardColorSource<String> scs2 = new StandardColorSource<String>(Color.RED);
        assertTrue(scs1.equals(scs2));
        
        scs1.setColor("A", Color.YELLOW);
        assertFalse(scs1.equals(scs2));
        scs2.setColor("A", Color.YELLOW);
        assertTrue(scs1.equals(scs2));
    }
}
