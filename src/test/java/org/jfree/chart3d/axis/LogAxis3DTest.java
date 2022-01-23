/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * Copyright 2013-2022, by David Gilbert. All rights reserved.
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

package org.jfree.chart3d.axis;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.text.DecimalFormat;

import org.junit.jupiter.api.Test;

import org.jfree.chart3d.TestUtils;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link LogAxis3D} class.
 */
public class LogAxis3DTest {
    
    @Test
    public void testEquals() {
        LogAxis3D axis1 = new LogAxis3D("Label");   
        LogAxis3D axis2 = new LogAxis3D("Label");
        assertEquals(axis1, axis2);
        assertNotEquals(null, axis1);
        
        // axis label
        axis1.setLabel("Label2");
        assertNotEquals(axis1, axis2);
        axis2.setLabel("Label2");
        assertEquals(axis1, axis2);
        
        // axis label can be null also
        axis1.setLabel(null);
        assertNotEquals(axis1, axis2);
        axis2.setLabel(null);
        assertEquals(axis1, axis2);

        // label font
        axis1.setLabelFont(new Font("Dialog", Font.PLAIN, 2));
        assertNotEquals(axis1, axis2);
        axis2.setLabelFont(new Font("Dialog", Font.PLAIN, 2));
        assertEquals(axis1, axis2);
        
        // label color
        axis1.setLabelColor(Color.RED);
        assertNotEquals(axis1, axis2);
        axis2.setLabelColor(Color.RED);
        assertEquals(axis1, axis2);
        
        // line stroke
        axis1.setLineStroke(new BasicStroke(0.5f));
        assertNotEquals(axis1, axis2);
        axis2.setLineStroke(new BasicStroke(0.5f));
        assertEquals(axis1, axis2);
        
        // line color
        axis1.setLineColor(Color.BLUE);
        assertNotEquals(axis1, axis2);
        axis2.setLineColor(Color.BLUE);
        assertEquals(axis1, axis2);
        
        // tickLabelsVisible
        axis1.setTickLabelsVisible(false);
        assertNotEquals(axis1, axis2);
        axis2.setTickLabelsVisible(false);
        assertEquals(axis1, axis2);
        
        // tick label font
        axis1.setTickLabelFont(new Font("Dialog", Font.PLAIN, 5));
        assertNotEquals(axis1, axis2);
        axis2.setTickLabelFont(new Font("Dialog", Font.PLAIN, 5));
        assertEquals(axis1, axis2);
        
        // tick label color
        axis1.setTickLabelColor(Color.GREEN);
        assertNotEquals(axis1, axis2);
        axis2.setTickLabelColor(Color.GREEN);
        assertEquals(axis1, axis2);

        // axis visible
        axis1.setVisible(false);
        assertNotEquals(axis1, axis2);
        axis2.setVisible(false);
        assertEquals(axis1, axis2);
        
        axis1.setLowerMargin(0.123);
        assertNotEquals(axis1, axis2);
        axis2.setLowerMargin(0.123);
        assertEquals(axis1, axis2);
        
        axis1.setUpperMargin(0.321);
        assertNotEquals(axis1, axis2);
        axis2.setUpperMargin(0.321);
        assertEquals(axis1, axis2);
        
        axis1.setTickMarkLength(123);
        assertNotEquals(axis1, axis2);
        axis2.setTickMarkLength(123);
        assertEquals(axis1, axis2);
        
        axis1.setTickMarkPaint(new GradientPaint(1f, 2f, Color.BLUE, 3f, 4f,
                Color.GREEN));
        assertNotEquals(axis1, axis2);
        axis2.setTickMarkPaint(new GradientPaint(1f, 2f, Color.BLUE, 3f, 4f,
                Color.GREEN));
        assertEquals(axis1, axis2);
        
        axis1.setTickMarkStroke(new BasicStroke(1.23f));
        assertNotEquals(axis1, axis2);
        axis2.setTickMarkStroke(new BasicStroke(1.23f));
        assertEquals(axis1, axis2);
        
        axis1.setBase(5.0);
        assertNotEquals(axis1, axis2);
        axis2.setBase(5.0);
        assertEquals(axis1, axis2);
        
        axis1.setSmallestValue(10.0);
        assertNotEquals(axis1, axis2);
        axis2.setSmallestValue(10.0);
        assertEquals(axis1, axis2);
        
        axis1.setBaseSymbol("x");
        assertNotEquals(axis1, axis2);
        axis2.setBaseSymbol("x");
        assertEquals(axis1, axis2);
        
        axis1.setBaseFormatter(new DecimalFormat("0.00"));
        assertNotEquals(axis1, axis2);
        axis2.setBaseFormatter(new DecimalFormat("0.00"));
        assertEquals(axis1, axis2);
        
        axis1.setTickSelector(null);
        assertNotEquals(axis1, axis2);
        axis2.setTickSelector(null);
        assertEquals(axis1, axis2);
        
        axis1.setTickSize(12.3);
        assertNotEquals(axis1, axis2);
        axis2.setTickSize(12.3);
        assertEquals(axis1, axis2);
        
        axis1.setTickLabelFormatter(new DecimalFormat("0.000"));
        assertNotEquals(axis1, axis2);
        axis2.setTickLabelFormatter(new DecimalFormat("0.000"));
        assertEquals(axis1, axis2);
        
        axis1.setTickLabelFactor(1.2);
        assertNotEquals(axis1, axis2);
        axis2.setTickLabelFactor(1.2);
        assertEquals(axis1, axis2);
    }
    
    /**
     * Checks for serialization support.
     */
    @Test
    public void testSerialization() {
        LogAxis3D axis1 = new LogAxis3D("T");
        LogAxis3D axis2 = (LogAxis3D) TestUtils.serialized(axis1);
        assertEquals(axis1, axis2);
        
        axis1.setTickMarkPaint(new GradientPaint(9f, 10f, Color.PINK, 11f, 12f,
                Color.BLACK));
        axis2 = (LogAxis3D) TestUtils.serialized(axis1);
        assertEquals(axis1, axis2);
    }

}
