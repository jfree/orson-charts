/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2020, by Object Refinery Limited.  All rights reserved.
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

package org.jfree.chart3d.label;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import org.jfree.chart3d.TestUtils;

/**
 * Tests for the {@link StandardCategoryLabelGenerator} class.
 */
public class StandardCategoryLabelGeneratorTest {
    
    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        StandardCategoryLabelGenerator lg1 
                = new StandardCategoryLabelGenerator();
        StandardCategoryLabelGenerator lg2 
                = new StandardCategoryLabelGenerator();
        assertTrue(lg1.equals(lg2));
        assertFalse(lg1.equals(null));
        
        lg1 = new StandardCategoryLabelGenerator("%s TEST");
        assertFalse(lg1.equals(lg2));
        lg2 = new StandardCategoryLabelGenerator("%s TEST");
        assertTrue(lg1.equals(lg2));
    }
    
    /**
     * Checks for serialization support.
     */
    @Test
    public void testSerialization() {
        StandardCategoryLabelGenerator lg1 
                = new StandardCategoryLabelGenerator();
        StandardCategoryLabelGenerator lg2 
                = (StandardCategoryLabelGenerator) TestUtils.serialized(lg1);
        assertTrue(lg1.equals(lg2));
    }
}

