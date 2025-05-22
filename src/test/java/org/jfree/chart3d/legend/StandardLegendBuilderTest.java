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
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts home page:
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 */

package org.jfree.chart3d.legend;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import org.jfree.chart3d.TestUtils;
import org.jfree.chart3d.table.HAlign;

/**
 * Tests for the {@link StandardLegendBuilder} class.
 */
public class StandardLegendBuilderTest {
    
    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        StandardLegendBuilder lb1 = new StandardLegendBuilder();
        StandardLegendBuilder lb2 = new StandardLegendBuilder();
        assertTrue(lb1.equals(lb2));
        assertFalse(lb1.equals(null));
        
        lb1.setHeader("XYZ");
        assertFalse(lb1.equals(lb2));
        lb2.setHeader("XYZ");
        assertTrue(lb1.equals(lb2));

        lb1.setHeaderAlignment(HAlign.CENTER);
        assertFalse(lb1.equals(lb2));
        lb2.setHeaderAlignment(HAlign.CENTER);
        assertTrue(lb1.equals(lb2));

        lb1.setFooter("ABC");
        assertFalse(lb1.equals(lb2));
        lb2.setFooter("ABC");
        assertTrue(lb1.equals(lb2));

        lb1.setFooterAlignment(HAlign.CENTER);
        assertFalse(lb1.equals(lb2));
        lb2.setFooterAlignment(HAlign.CENTER);
        assertTrue(lb1.equals(lb2));
    }
    
    /**
     * Checks for serialization support.
     */
    @Test
    public void testSerialization() {
        StandardLegendBuilder lb1 = new StandardLegendBuilder();
        StandardLegendBuilder lb2 = (StandardLegendBuilder) 
                TestUtils.serialized(lb1);
        assertTrue(lb1.equals(lb2));
    }
}
