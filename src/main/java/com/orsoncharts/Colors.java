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

package com.orsoncharts;

import java.awt.Color;

/**
 * A utility class that creates and returns color swatches that can be used
 * in charts.  The "i want hue" utility has been used to generate a number 
 * of these color sets.  
 * See <a href="http://tools.medialab.sciences-po.fr/iwanthue/">
 * http://tools.medialab.sciences-po.fr/iwanthue/</a>. 
 */
public class Colors {

    private Colors() {
        // no need to instantiate this class
    }
    
    /**
     * Returns the default colors.
     * 
     * @return The default colors. 
     */
    public static Color[] getDefaultColors() {
        return createShadesColors();
    }

    /**
     * Returns a palette of ten colors created using the "i want hue" utility,
     * using the preset "Fancy (light background)" settings.  A new array 
     * instance is created for each call to this method.  A link to the 
     * "i want hue" utility is given in the class description.
     * 
     * @return An array of ten colors (never {@code null}). 
     * 
     * @since 1.2
     */
    public static Color[] createFancyLightColors() {
        Color[] result = new Color[10]; 
        result[0] = new Color(239, 164, 127);
        result[1] = new Color(140, 228, 139);
        result[2] = new Color(155, 208, 227);
        result[3] = new Color(221, 228, 95);
        result[4] = new Color(118, 223, 194);
        result[5] = new Color(240, 166, 184);
        result[6] = new Color(231, 185, 98);
        result[7] = new Color(186, 214, 150);
        result[8] = new Color(217, 184, 226);
        result[9] = new Color(201, 212, 116);
        return result; 
    }
    
    /**
     * Returns a palette of ten colors created using the "i want hue" utility,
     * using the preset "Fancy (dark background)" settings.  A new array 
     * instance is created for each call to this method.  A link to the 
     * "i want hue" utility is given in the class description.
     * 
     * @return An array of ten colors (never {@code null}). 
     * 
     * @since 1.2
     */
    public static Color[] createFancyDarkColors() {
        Color[] result = new Color[10]; 
        result[0] = new Color(78, 81, 97);
        result[1] = new Color(91, 104, 51);
        result[2] = new Color(138, 75, 65);
        result[3] = new Color(72, 62, 34);
        result[4] = new Color(58, 100, 75);
        result[5] = new Color(39, 63, 59);
        result[6] = new Color(105, 68, 75);
        result[7] = new Color(120, 90, 120);
        result[8] = new Color(119, 90, 50);
        result[9] = new Color(59, 103, 111);
        return result; 
    }
    
    /**
     * Returns a palette of ten colors created using the "i want hue" utility,
     * using the preset "Shades" settings.  A new array instance is created for
     * each call to this method.  A link to the "i want hue" utility is given 
     * in the class description.
     * 
     * @return An array of ten colors (never {@code null}). 
     * 
     * @since 1.2
     */
    public static Color[] createShadesColors() {
        Color[] result = new Color[10]; 
        result[0] = new Color(137, 132, 104);
        result[1] = new Color(217, 232, 208);
        result[2] = new Color(53, 48, 40);
        result[3] = new Color(240, 225, 172);
        result[4] = new Color(196, 160, 128);
        result[5] = new Color(92, 96, 87);
        result[6] = new Color(136, 141, 136);
        result[7] = new Color(106, 93, 66);
        result[8] = new Color(205, 199, 168);
        result[9] = new Color(158, 168, 143);
        return result;
    }

    /**
     * Returns a palette of ten colors created using the "i want hue" utility,
     * using the preset "Tarnish" settings.  A new array instance is created for
     * each call to this method.  A link to the "i want hue" utility is given 
     * in the class description.
     * 
     * @return An array of ten colors (never {@code null}). 
     * 
     * @since 1.2
     */
    public static Color[] createTarnishColors() {
        Color[] result = new Color[10]; 
        result[0] = new Color(148, 129, 121);
        result[1] = new Color(179, 181, 136);
        result[2] = new Color(204, 163, 140);
        result[3] = new Color(102, 93, 80);
        result[4] = new Color(164, 178, 159);
        result[5] = new Color(156, 130, 100);
        result[6] = new Color(129, 142, 124);
        result[7] = new Color(186, 168, 159);
        result[8] = new Color(144, 148, 108);
        result[9] = new Color(189, 169, 131);
        return result;
    }
    
    /**
     * Returns a palette of ten colors created using the "i want hue" utility,
     * using the preset "Pastel" settings.  A new array instance is created for
     * each call to this method.  A link to the "i want hue" utility is given 
     * in the class description.
     * 
     * @return An array of ten colors (never {@code null}). 
     * 
     * @since 1.2
     */
    public static Color[] createPastelColors() {
        Color[] result = new Color[10]; 
        result[0] = new Color(232, 177, 165);
        result[1] = new Color(207, 235, 142);
        result[2] = new Color(142, 220, 220);
        result[3] = new Color(228, 186, 115);
        result[4] = new Color(187, 200, 230);
        result[5] = new Color(157, 222, 177);
        result[6] = new Color(234, 183, 210);
        result[7] = new Color(213, 206, 169);
        result[8] = new Color(202, 214, 205);
        result[9] = new Color(195, 204, 133);
        return result;
    }
    
    /**
     * Returns a palette of ten colors created using the "i want hue" utility,
     * using the preset "Pimp" settings.  A new array instance is created for
     * each call to this method.  A link to the "i want hue" utility is given 
     * in the class description.
     * 
     * @return An array of ten colors (never {@code null}). 
     * 
     * @since 1.2
     */
    public static Color[] createPimpColors() {
        Color[] result = new Color[10]; 
        result[0] = new Color(197, 75, 103);
        result[1] = new Color(85, 154, 48);
        result[2] = new Color(122, 110, 206);
        result[3] = new Color(190, 100, 50);
        result[4] = new Color(201, 79, 209);
        result[5] = new Color(95, 127, 170);
        result[6] = new Color(147, 129, 39);
        result[7] = new Color(63, 142, 96);
        result[8] = new Color(186, 84, 150);
        result[9] = new Color(219, 66, 52);
        return result;
    }
    
    /**
     * Returns a palette of ten colors created using the "i want hue" utility,
     * using the preset "Intense" settings.  A new array instance is created for
     * each call to this method.  A link to the "i want hue" utility is given 
     * in the class description.
     * 
     * @return An array of ten colors (never {@code null}). 
     * 
     * @since 1.2
     */
    public static Color[] createIntenseColors() {
        Color[] result = new Color[10]; 
        result[0] = new Color(107, 122, 160);
        result[1] = new Color(99, 176, 67);
        result[2] = new Color(214, 85, 52);
        result[3] = new Color(202, 79, 200);
        result[4] = new Color(184, 149, 57);
        result[5] = new Color(82, 168, 146);
        result[6] = new Color(194, 84, 128);
        result[7] = new Color(77, 102, 50);
        result[8] = new Color(132, 108, 197);
        result[9] = new Color(144, 74, 61);
        return result;
    }
    
    /**
     * Returns a palette of ten colors created using the "i want hue" utility,
     * using the preset "Fluo" settings.  A new array instance is created for
     * each call to this method.  A link to the "i want hue" utility is given 
     * in the class description.
     * 
     * @return An array of ten colors (never {@code null}). 
     * 
     * @since 1.2
     */
    public static Color[] createFluoColors() {
        Color[] result = new Color[10]; 
        result[0] = new Color(108, 236, 137);
        result[1] = new Color(253, 187, 46);
        result[2] = new Color(56, 236, 216);
        result[3] = new Color(171, 231, 51);
        result[4] = new Color(221, 214, 74);
        result[5] = new Color(106, 238, 70);
        result[6] = new Color(172, 230, 100);
        result[7] = new Color(242, 191, 82);
        result[8] = new Color(221, 233, 56);
        result[9] = new Color(242, 206, 47);
        return result;
    }
    
    /**
     * Returns a palette of ten colors created using the "i want hue" utility,
     * using the preset "Red Roses" settings.  A new array instance is created 
     * for each call to this method.  A link to the "i want hue" utility is 
     * given in the class description.
     * 
     * @return An array of ten colors (never {@code null}). 
     * 
     * @since 1.2
     */
    public static Color[] createRedRosesColors() {
        Color[] result = new Color[10]; 
        result[0] = new Color(230, 129, 128);
        result[1] = new Color(233, 56, 39);
        result[2] = new Color(225, 45, 102);
        result[3] = new Color(172, 79, 55);
        result[4] = new Color(214, 154, 128);
        result[5] = new Color(156, 96, 81);
        result[6] = new Color(190, 77, 91);
        result[7] = new Color(228, 121, 91);
        result[8] = new Color(216, 63, 80);
        result[9] = new Color(209, 75, 46);
        return result;
    }
    
    /**
     * Returns a palette of ten colors created using the "i want hue" utility,
     * using the preset "Ochre Sand" settings.  A new array instance is created 
     * for each call to this method.  A link to the "i want hue" utility is 
     * given in the class description.
     * 
     * @return An array of ten colors (never {@code null}). 
     * 
     * @since 1.2
     */
    public static Color[] createOchreSandColors() {
        Color[] result = new Color[10]; 
        result[0] = new Color(218, 180, 125);
        result[1] = new Color(245, 184, 36);
        result[2] = new Color(159, 103, 28);
        result[3] = new Color(124, 96, 55);
        result[4] = new Color(224, 132, 56);
        result[5] = new Color(185, 143, 48);
        result[6] = new Color(229, 171, 97);
        result[7] = new Color(232, 165, 54);
        result[8] = new Color(171, 102, 53);
        result[9] = new Color(160, 122, 71);
        return result;
    }
    
    /**
     * Returns a palette of ten colors created using the "i want hue" utility,
     * using the preset "Yellow Lime" settings.  A new array instance is created 
     * for each call to this method.  A link to the "i want hue" utility is 
     * given in the class description.
     * 
     * @return An array of ten colors (never {@code null}). 
     * 
     * @since 1.2
     */
    public static Color[] createYellowLimeColors() {
        Color[] result = new Color[10]; 
        result[0] = new Color(235, 203, 59);
        result[1] = new Color(113, 108, 56);
        result[2] = new Color(222, 206, 134);
        result[3] = new Color(169, 166, 62);
        result[4] = new Color(214, 230, 54);
        result[5] = new Color(225, 221, 105);
        result[6] = new Color(128, 104, 23);
        result[7] = new Color(162, 151, 86);
        result[8] = new Color(117, 121, 25);
        result[9] = new Color(183, 179, 40);
        return result;
    }
    
    /**
     * Returns a palette of ten colors created using the "i want hue" utility,
     * using the preset "Green Mint" settings.  A new array instance is created 
     * for each call to this method.  A link to the "i want hue" utility is 
     * given in the class description.
     * 
     * @return An array of ten colors (never {@code null}). 
     * 
     * @since 1.2
     */
    public static Color[] createGreenMintColors() {
        Color[] result = new Color[10]; 
        result[0] = new Color(99, 224, 113);
        result[1] = new Color(98, 132, 83);
        result[2] = new Color(145, 234, 49);
        result[3] = new Color(181, 215, 158);
        result[4] = new Color(95, 171, 43);
        result[5] = new Color(100, 208, 142);
        result[6] = new Color(172, 222, 84);
        result[7] = new Color(75, 139, 53);
        result[8] = new Color(177, 216, 123);
        result[9] = new Color(83, 223, 60);
        return result;
    }
    
    /**
     * Returns a palette of ten colors created using the "i want hue" utility,
     * using the preset "Ice Cube" settings.  A new array instance is created 
     * for each call to this method.  A link to the "i want hue" utility is 
     * given in the class description.
     * 
     * @return An array of ten colors (never {@code null}). 
     * 
     * @since 1.2
     */
    public static Color[] createIceCubeColors() {
        Color[] result = new Color[10]; 
        result[0] = new Color(112, 235, 233);
        result[1] = new Color(54, 110, 100);
        result[2] = new Color(211, 232, 208);
        result[3] = new Color(94, 230, 191);
        result[4] = new Color(76, 154, 155);
        result[5] = new Color(156, 181, 157);
        result[6] = new Color(67, 152, 126);
        result[7] = new Color(112, 135, 119);
        result[8] = new Color(155, 213, 192);
        result[9] = new Color(80, 195, 190);
        return result;
    }
    
    /**
     * Returns a palette of ten colors created using the "i want hue" utility,
     * using the preset "Blue Ocean" settings.  A new array instance is created 
     * for each call to this method.  A link to the "i want hue" utility is 
     * given in the class description.
     * 
     * @return An array of ten colors (never {@code null}). 
     * 
     * @since 1.2
     */
    public static Color[] createBlueOceanColors() {
        Color[] result = new Color[10];
        result[0] = new Color(53, 84, 154);
        result[1] = new Color(41, 46, 57);
        result[2] = new Color(115, 124, 151);
        result[3] = new Color(38, 52, 91);
        result[4] = new Color(84, 117, 211);
        result[5] = new Color(76, 125, 181);
        result[6] = new Color(109, 108, 112);
        result[7] = new Color(48, 105, 134);
        result[8] = new Color(72, 82, 107);
        result[9] = new Color(91, 99, 144);
        return result;
    }
    
    /**
     * Returns a palette of ten colors created using the "i want hue" utility,
     * using the preset "Indigo Night" settings.  A new array instance is 
     * created for each call to this method.  A link to the "i want hue" 
     * utility is given in the class description.
     * 
     * @return An array of ten colors (never {@code null}). 
     * 
     * @since 1.2
     */
    public static Color[] createIndigoNightColors() {
        Color[] result = new Color[10];
        result[0] = new Color(127, 88, 147);
        result[1] = new Color(201, 67, 217);
        result[2] = new Color(112, 97, 218);
        result[3] = new Color(219, 134, 222);
        result[4] = new Color(154, 80, 172);
        result[5] = new Color(170, 106, 231);
        result[6] = new Color(142, 111, 210);
        result[7] = new Color(194, 149, 235);
        result[8] = new Color(152, 118, 188);
        result[9] = new Color(214, 101, 237);
        return result;
    }
    
    /**
     * Returns a palette of ten colors created using the "i want hue" utility,
     * using the preset "Purple Wine" settings.  A new array instance is created 
     * for each call to this method.  A link to the "i want hue" utility is 
     * given in the class description.
     * 
     * @return An array of ten colors (never {@code null}). 
     * 
     * @since 1.2
     */
    public static Color[] createPurpleWineColors() {
        Color[] result = new Color[10];
        result[0] = new Color(116, 28, 93);
        result[1] = new Color(112, 79, 75);
        result[2] = new Color(178, 37, 101);
        result[3] = new Color(109, 24, 56);
        result[4] = new Color(167, 42, 140);
        result[5] = new Color(66, 30, 40);
        result[6] = new Color(128, 70, 95);
        result[7] = new Color(78, 20, 56);
        result[8] = new Color(155, 62, 111);
        result[9] = new Color(139, 61, 75);
        return result;
    }

    /**
     * Returns a palette of 7 colors with earth tones.
     * 
     * @return An array of 7 colors (never {@code null}). 
     */
     public static Color[] getEarthColors() {
         Color[] result = new Color[7];
         result[0] = new Color(98, 98, 98);
         result[1] = new Color(159, 87, 43);
         result[2] = new Color(194, 176, 46);
         result[3] = new Color(134, 155, 64);
         result[4] = new Color(57, 118, 40);
         result[5] = new Color(40, 114, 110);
         result[6] = new Color(78, 79, 62);
         return result;
    } 

    /**
     * Returns a newly created array containing 9 colors from the the 
     * ColorBrewer tool.  This is a high-contrast set of colors, good for
     * pie charts.
     * 
     * http://colorbrewer2.org/?type=qualitative&amp;scheme=Set1&amp;n=9
     * 
     * @return A color array.
     */
    public static Color[] getBrewerQualitativeSet1N9Colors() {
        Color[] result = new Color[9];
        result[0] = new Color(228, 26, 28);
        result[1] = new Color(55, 126, 184);
        result[2] = new Color(77, 175, 74);
        result[3] = new Color(152, 78, 163);
        result[4] = new Color(255, 127, 0);
        result[5] = new Color(255, 255, 51);
        result[6] = new Color(166, 86, 40);
        result[7] = new Color(247, 129, 191);
        result[8] = new Color(153, 153, 153);
        return result;
    }
  
    /**
     * Returns a newly created array containing 12 colors from the the 
     * ColorBrewer tool.
     * 
     * http://colorbrewer2.org/?type=qualitative&amp;scheme=Paired&amp;n=12
     * 
     * @return A color array.
     */
    public static Color[] getBrewerQualitativePairedN12Colors() {
        Color[] result = new Color[12];
        result[0] = new Color(166, 206, 227);
        result[1] = new Color(31, 120, 180);
        result[2] = new Color(178, 223, 138);
        result[3] = new Color(51, 160, 44);
        result[4] = new Color(251, 154, 153);
        result[5] = new Color(227, 26, 28);
        result[6] = new Color(253, 191, 111);
        result[7] = new Color(255, 127, 0);
        result[8] = new Color(202, 178, 214);
        result[9] = new Color(106, 61, 154);
        result[10] = new Color(255, 255, 153);
        result[11] = new Color(177, 89, 40);
        return result;
    }
    
    /**
     * Returns a newly created array containing 11 colors from the the 
     * ColorBrewer tool.  Good for pie charts and bar charts, not so good for 
     * scatter plots.
     * 
     * http://colorbrewer2.org/?type=qualitative&amp;scheme=Set3&amp;n=12 
     * 
     * @return A color array.
     */
    public static Color[] getBrewerQualitativeSet3N12Colors() {
        Color[] result = new Color[12];
        result[0] = new Color(141, 211, 199);
        result[1] = new Color(255, 255, 179);
        result[2] = new Color(190, 186, 218);
        result[3] = new Color(251, 128, 114);
        result[4] = new Color(128, 177, 211);
        result[5] = new Color(253, 180, 98);
        result[6] = new Color(179, 222, 105);
        result[7] = new Color(252, 205, 229);
        result[8] = new Color(217, 217, 217);
        result[9] = new Color(188, 128, 189);
        result[10] = new Color(204, 235, 197);
        result[11] = new Color(255, 237, 111);
        return result;
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
     * Returns an array of four colors.
     * 
     * @return An array of four colors. 
     */
    public static Color[] getColors1() {
        return new Color[] { new Color(0, 55, 122),  
            new Color(24, 123, 58), Color.RED, Color.YELLOW };
    }
    
    /**
     * Returns an array of four colors.
     * 
     * @return An array of four colors. 
     */
    public static Color[] getColors2() {
        return new Color[] {new Color(0x1A9641), new Color(0xA6D96A), 
                    new Color(0xFDAE61), new Color(0xFFFFBF)};
    }
        
    /**
     * Returns an array of six colors 
     * (source: http://blog.design-seeds.com/generating-color/).
     * 
     * @return An array of six colors. 
     */
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
    
    /**
     * Returns an array of six colors 
     * (source: http://blog.design-seeds.com/generating-color/).
     * 
     * @return An array of six colors. 
     */
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
