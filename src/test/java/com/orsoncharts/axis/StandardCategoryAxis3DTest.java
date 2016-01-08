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

package com.orsoncharts.axis;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;

import org.junit.Test;

import com.orsoncharts.TestUtils;
import com.orsoncharts.label.StandardCategoryLabelGenerator;
import com.orsoncharts.marker.CategoryMarker;

/**
 * Tests for the {@link StandardCategoryAxis3D} class.
 */
public class StandardCategoryAxis3DTest {
    
    @Test
    public void testEquals() {
        StandardCategoryAxis3D axis1 = new StandardCategoryAxis3D("Label");   
        StandardCategoryAxis3D axis2 = new StandardCategoryAxis3D("Label"); 
        assertTrue(axis1.equals(axis2));
        assertFalse(axis1.equals(null));
        
        // axis label
        axis1.setLabel("Label2");
        assertFalse(axis1.equals(axis2));
        axis2.setLabel("Label2");
        assertTrue(axis1.equals(axis2));
        
        // axis label can be null also
        axis1.setLabel(null);
        assertFalse(axis1.equals(axis2));
        axis2.setLabel(null);
        assertTrue(axis1.equals(axis2));

        // label font
        axis1.setLabelFont(new Font("Dialog", Font.PLAIN, 2));
        assertFalse(axis1.equals(axis2));
        axis2.setLabelFont(new Font("Dialog", Font.PLAIN, 2));
        assertTrue(axis1.equals(axis2));
        
        // label color
        axis1.setLabelColor(Color.RED);
        assertFalse(axis1.equals(axis2));
        axis2.setLabelColor(Color.RED);
        assertTrue(axis1.equals(axis2));
        
        // line stroke
        axis1.setLineStroke(new BasicStroke(0.5f));
        assertFalse(axis1.equals(axis2));
        axis2.setLineStroke(new BasicStroke(0.5f));
        assertTrue(axis1.equals(axis2));
        
        // line color
        axis1.setLineColor(Color.BLUE);
        assertFalse(axis1.equals(axis2));
        axis2.setLineColor(Color.BLUE);
        assertTrue(axis1.equals(axis2));
        
        // tickLabelsVisible
        axis1.setTickLabelsVisible(false);
        assertFalse(axis1.equals(axis2));
        axis2.setTickLabelsVisible(false);
        assertTrue(axis1.equals(axis2));
        
        // tick label font
        axis1.setTickLabelFont(new Font("Dialog", Font.PLAIN, 5));
        assertFalse(axis1.equals(axis2));
        axis2.setTickLabelFont(new Font("Dialog", Font.PLAIN, 5));
        assertTrue(axis1.equals(axis2));
        
        // tick label color
        axis1.setTickLabelColor(Color.GREEN);
        assertFalse(axis1.equals(axis2));
        axis2.setTickLabelColor(Color.GREEN);
        assertTrue(axis1.equals(axis2));

        // axis visible
        axis1.setVisible(false);
        assertFalse(axis1.equals(axis2));
        axis2.setVisible(false);
        assertTrue(axis1.equals(axis2));
        
        axis1.setLowerMargin(0.123);
        assertFalse(axis1.equals(axis2));
        axis2.setLowerMargin(0.123);
        assertTrue(axis1.equals(axis2));
        
        axis1.setUpperMargin(0.321);
        assertFalse(axis1.equals(axis2));
        axis2.setUpperMargin(0.321);
        assertTrue(axis1.equals(axis2));

        axis1.setFirstCategoryHalfWidth(true);
        assertFalse(axis1.equals(axis2));
        axis2.setFirstCategoryHalfWidth(true);
        assertTrue(axis1.equals(axis2));
        
        axis1.setLastCategoryHalfWidth(true);
        assertFalse(axis1.equals(axis2));
        axis2.setLastCategoryHalfWidth(true);
        assertTrue(axis1.equals(axis2));
        
        axis1.setTickMarkLength(123);
        assertFalse(axis1.equals(axis2));
        axis2.setTickMarkLength(123);
        assertTrue(axis1.equals(axis2));
        
        axis1.setTickMarkPaint(new GradientPaint(1f, 2f, Color.BLUE, 3f, 4f,
                Color.GREEN));
        assertFalse(axis1.equals(axis2));
        axis2.setTickMarkPaint(new GradientPaint(1f, 2f, Color.BLUE, 3f, 4f,
                Color.GREEN));
        assertTrue(axis1.equals(axis2));
        
        axis1.setTickMarkStroke(new BasicStroke(1.23f));
        assertFalse(axis1.equals(axis2));
        axis2.setTickMarkStroke(new BasicStroke(1.23f));
        assertTrue(axis1.equals(axis2));
        
        axis1.setTickLabelGenerator(
                new StandardCategoryLabelGenerator("{0} {1}"));
        assertFalse(axis1.equals(axis2));
        axis2.setTickLabelGenerator(
                new StandardCategoryLabelGenerator("{0} {1}"));
        assertTrue(axis1.equals(axis2));
        
        axis1.setTickLabelOffset(1.23);
        assertFalse(axis1.equals(axis2));
        axis2.setTickLabelOffset(1.23);
        assertTrue(axis1.equals(axis2));
        
        axis1.setTickLabelOrientation(LabelOrientation.PERPENDICULAR);
        assertFalse(axis1.equals(axis2));
        axis2.setTickLabelOrientation(LabelOrientation.PERPENDICULAR);
        assertTrue(axis1.equals(axis2));
        
        axis1.setTickLabelFactor(1.23);
        assertFalse(axis1.equals(axis2));
        axis2.setTickLabelFactor(1.23);
        assertTrue(axis1.equals(axis2));
        
        axis1.setMaxTickLabelLevels(5);
        assertFalse(axis1.equals(axis2));
        axis2.setMaxTickLabelLevels(5);
        assertTrue(axis1.equals(axis2));
        
        axis1.setMarker("M1", new CategoryMarker("A"));
        assertFalse(axis1.equals(axis2));
        axis2.setMarker("M1", new CategoryMarker("A"));
        assertTrue(axis1.equals(axis2));
    }
    
    /**
     * Checks for serialization support.
     */
    @Test
    public void testSerialization() {
        StandardCategoryAxis3D axis1 = new StandardCategoryAxis3D("T");
        StandardCategoryAxis3D axis2 = (StandardCategoryAxis3D) 
                TestUtils.serialized(axis1);
        assertTrue(axis1.equals(axis2));
        
        axis1.setTickMarkPaint(new GradientPaint(9f, 10f, Color.PINK, 11f, 12f,
                Color.BLACK));
        axis2 = (StandardCategoryAxis3D) TestUtils.serialized(axis1);
        assertTrue(axis1.equals(axis2));
        
        axis1.setMarker("M1", new CategoryMarker("C1"));
        axis2 = (StandardCategoryAxis3D) TestUtils.serialized(axis1);
        assertTrue(axis1.equals(axis2));
    }

}
