/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts;

import java.awt.Color;

/**
 * A utility class that creates and returns color swatches that can be used
 * in charts.
 */
public class Colors {

    /**
     * Returns the default colors.
     * 
     * @return The default colors. 
     */
    public static Color[] getDefaultColors() {
        return getSAPMultiColor();
    }

    /**
     * Returns a set of colors sourced from 
     * http://www.sapdesignguild.org/goodies/diagram_guidelines/index.html.
     * 
     * @return A color array.
     */
    public static Color[] getSAPMultiColor() {
        return new Color[] {
            new Color(255, 248, 163),
            new Color(169, 204, 143),
            new Color(178, 200, 217),
            new Color(190, 163, 122),
            new Color(243, 170, 121),
            new Color(181, 181, 169),
            new Color(230, 165, 164),
            new Color(248, 215, 83),
            new Color(92, 151, 70),
            new Color(62, 117, 167),
            new Color(122, 101, 62),
            new Color(225, 102, 42),
            new Color(116, 121, 111),
            new Color(196, 56, 79)
        };
    }
    
    /**
     * 
     * @return 
     */
    public static Color[] getColors1() {
        return new Color[] { new Color(0, 55, 122),  
            new Color(24, 123, 58), Color.RED, Color.YELLOW };
    }
    
    public static Color[] getColors2() {
        return new Color[] {new Color(0x1A9641), new Color(0xA6D96A), 
                    new Color(0xFDAE61), new Color(0xFFFFBF)};
    }
        
    public static Color[] getDesignSeedsShells() {
        return new Color[] {
            new Color(228, 233, 239),
            new Color(184, 197, 219),
            new Color(111, 122, 143),
            new Color(95, 89, 89),
            new Color(206, 167, 145),
            new Color(188, 182, 173)
        };
    }
    
    public static Color[] getDesignSeedsPepper() {
        return new Color[] {
            new Color(255, 219, 142),
            new Color(220, 21, 20),
            new Color(149, 0, 1),
            new Color(82, 102, 41),
            new Color(142, 101, 72),
            new Color(199, 169, 128)
        };
    }
}
