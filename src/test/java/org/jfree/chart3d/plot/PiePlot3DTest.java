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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.awt.Color;
import java.awt.Font;

import com.orsoncharts.data.StandardPieDataset3D;
import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.TestUtils;
import com.orsoncharts.label.StandardPieLabelGenerator;

/** 
 * Some tests for the {@link PiePlot3D} class.
 */
public class PiePlot3DTest implements Plot3DChangeListener {

    @Test
    public void checkSetRadiusFiresChangeEvent() {
        PiePlot3D plot = createPiePlot3D();
        plot.addChangeListener(this);
        assertNull(this.lastEvent);
    
        plot.setRadius(10.2);
        assertNotNull(this.lastEvent);
        this.lastEvent = null;
    }
  
    @Test
    public void checkSetDepthFiresChangeEvent() {
        PiePlot3D plot = createPiePlot3D();
        plot.addChangeListener(this);
        assertNull(this.lastEvent);
    
        plot.setDepth(0.123);
        assertNotNull(this.lastEvent);
        this.lastEvent = null;
    }
  
    @Test
    @SuppressWarnings("unchecked")
    public void checkSetDatasetFiresChangeEvent() {
        PiePlot3D plot = createPiePlot3D();
        plot.addChangeListener(this);
        assertNull(this.lastEvent);
     
        plot.setDataset(createNewDataset());
        assertNotNull(this.lastEvent);
        this.lastEvent = null;
    }
  
    @Test
    @SuppressWarnings("unchecked")
    public void checkSetDatasetRemovesPreviousListener() {
        StandardPieDataset3D<String> dataset1 = createNewDataset();
        PiePlot3D plot = new PiePlot3D(dataset1);
        assertTrue(dataset1.hasListener(plot));
        StandardPieDataset3D<String> dataset2 = createNewDataset();
        plot.setDataset(dataset2);
        assertFalse(dataset1.hasListener(plot));
        assertTrue(dataset2.hasListener(plot));
    }
  
    /**
     * Check that all the required fields are taken into a account by the
     * equals() method.
     */
    @Test
    public void checkEquals() {
        StandardPieDataset3D<String> dataset1 = createNewDataset();
        PiePlot3D p1 = new PiePlot3D(dataset1);
        PiePlot3D p2 = new PiePlot3D(dataset1);
        assertEquals(p1, p2);
    
        // radius
        p1.setRadius(12.3);
        assertFalse(p1.equals(p2));
        p2.setRadius(12.3);
        assertTrue(p1.equals(p2));
        
        // depth
        p1.setDepth(3.21);
        assertFalse(p1.equals(p2));
        p2.setDepth(3.21);
        assertTrue(p1.equals(p2));
        
        // section label generator
        p1.setSectionLabelGenerator(new StandardPieLabelGenerator("Key: %s"));
        assertFalse(p1.equals(p2));
        p2.setSectionLabelGenerator(new StandardPieLabelGenerator("Key: %s"));
        assertTrue(p1.equals(p2));
        
        // section color source
        p1.setSectionColorSource(new StandardColorSource(Color.CYAN));
        assertFalse(p1.equals(p2));
        p2.setSectionColorSource(new StandardColorSource(Color.CYAN));
        assertTrue(p1.equals(p2));
        
        // section label font source
        p1.setSectionLabelFontSource(new StandardFontSource(new Font("Dialog", 
                Font.PLAIN, 9)));
        assertFalse(p1.equals(p2));
        p2.setSectionLabelFontSource(new StandardFontSource(new Font("Dialog", 
                Font.PLAIN, 9)));
        assertTrue(p1.equals(p2));
        
        // section label color source
        p1.setSectionLabelColorSource(new StandardColorSource(Color.BLUE));
        assertFalse(p1.equals(p2));
        p2.setSectionLabelColorSource(new StandardColorSource(Color.BLUE));
        assertTrue(p1.equals(p2));
        
        // legend label generator
        p1.setLegendLabelGenerator(new StandardPieLabelGenerator("Key: %s"));
        assertFalse(p1.equals(p2));
        p2.setLegendLabelGenerator(new StandardPieLabelGenerator("Key: %s"));
        assertTrue(p1.equals(p2));

        // tool tip generator
        p1.setToolTipGenerator(new StandardPieLabelGenerator("Tool tip: %s"));
        assertFalse(p1.equals(p2));
        p2.setToolTipGenerator(new StandardPieLabelGenerator("Tool tip: %s"));
        assertTrue(p1.equals(p2));

        // segments
        p1.setSegmentCount(123);
        assertFalse(p1.equals(p2));
        p2.setSegmentCount(123);
        assertTrue(p1.equals(p2));
    }
    
    /**
     * A check for serialization.
     */
    @Test
    public void testSerializationPieChart() {
        Chart3D c1 = Chart3DFactory.createPieChart("title", "subtitle", 
                createNewDataset());
        Chart3D c2 = (Chart3D) TestUtils.serialized(c1);
        assertEquals(c1, c2);
    }
  
    /**
     * Creates and returns a new dataset (it is important for the tests that 
     * this method always returns a new dataset).
     * 
     * @return A new dataset. 
     */
    private StandardPieDataset3D<String> createNewDataset() {
        StandardPieDataset3D<String> dataset = new StandardPieDataset3D<String>();
        dataset.add("A", 123.4);
        return dataset;
    }
  
    private PiePlot3D createPiePlot3D() {
        return new PiePlot3D(createNewDataset());
    }

    private Plot3DChangeEvent lastEvent = null;
  
    @Override
    public void plotChanged(Plot3DChangeEvent event) {
        this.lastEvent = event;
    }
}
