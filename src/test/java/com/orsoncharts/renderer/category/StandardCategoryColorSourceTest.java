/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of this source file is prohibited.
 * 
 */

package com.orsoncharts.renderer.category;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import java.awt.Color;
import com.orsoncharts.TestUtils;

/**
 * Tests for the {@link StandardCategoryColorSource} class.
 */
public class StandardCategoryColorSourceTest {
    
    @Test
    public void testEquals() {
        StandardCategoryColorSource s1 = new StandardCategoryColorSource();
        StandardCategoryColorSource s2 = new StandardCategoryColorSource();
        assertTrue(s1.equals(s2));
        assertFalse(s1.equals(null));
        
        s1 = new StandardCategoryColorSource(new Color[] { Color.BLUE });
        assertFalse(s1.equals(s2));
        s2 = new StandardCategoryColorSource(new Color[] { Color.BLUE });
        assertTrue(s1.equals(s2)); 
    }
    
    @Test
    public void testSerialization() {
        StandardCategoryColorSource s1 = new StandardCategoryColorSource();
        StandardCategoryColorSource s2 = (StandardCategoryColorSource) 
                TestUtils.serialized(s1);
        assertTrue(s1.equals(s2));
    }
        
}
