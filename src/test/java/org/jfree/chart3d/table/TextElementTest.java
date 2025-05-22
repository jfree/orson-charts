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

package org.jfree.chart3d.table;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import org.junit.jupiter.api.Test;
import org.jfree.chart3d.TestUtils;

/**
 * Tests for the {@link TextElement} class.
 */
public class TextElementTest {
    
    @Test
    public void testEquals() {
        TextElement t1 = new TextElement("A");
        TextElement t2 = new TextElement("A");
        assertTrue(t1.equals(t2));
        assertFalse(t1.equals(null));
        
        // insets
        t1.setInsets(new Insets(1, 2, 3, 4));
        assertFalse(t1.equals(t2));
        t2.setInsets(new Insets(1, 2, 3, 4));
        assertTrue(t1.equals(t2));
        
        // background color
        t1.setBackgroundColor(Color.GREEN);
        assertFalse(t1.equals(t2));
        t2.setBackgroundColor(Color.GREEN);
        assertTrue(t1.equals(t2));
        
        // color
        t1.setColor(Color.MAGENTA);
        assertFalse(t1.equals(t2));
        t2.setColor(Color.MAGENTA);
        assertTrue(t1.equals(t2));

        t1.setFont(new Font("Dialog", Font.PLAIN, 2));
        assertFalse(t1.equals(t2));
        t2.setFont(new Font("Dialog", Font.PLAIN, 2));
        assertTrue(t1.equals(t2));
        
        t1.setHorizontalAligment(HAlign.RIGHT);
        assertFalse(t1.equals(t2));
        t2.setHorizontalAligment(HAlign.RIGHT);
        assertTrue(t1.equals(t2));
    }
    
    @Test
    public void testSerialization() {
        TextElement t1 = new TextElement("A");
        TextElement t2 = (TextElement) TestUtils.serialized(t1);
        assertTrue(t1.equals(t2));
    }
    

}
