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

package com.orsoncharts.table;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import org.junit.Test;
import com.orsoncharts.TestUtils;

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
