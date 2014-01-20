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

package com.orsoncharts.style;

import java.awt.Color;
import java.awt.Font;
import com.orsoncharts.Colors;
import com.orsoncharts.StandardRectanglePainter;

/**
 * Some predefined chart styles.
 * 
 * @since 1.2
 */
public class ChartStyles {
    
    /**
     * Creates and returns a new instance of the "Orson 1" chart style.
     * 
     * @return A chart style (never <code>null</code>). 
     */
    public static ChartStyle createOrson1Style() {
        StandardChartStyle s = new StandardChartStyle();
        s.setStandardColors(Colors.createFancyLightColors());
        return s;
    }
    
    /**
     * Creates and returns a new instance of the "Orson 2" chart style. This
     * style has a black background and uses shades of blue for the data
     * colors.
     * 
     * @return A chart style (never <code>null</code>). 
     */
    public static ChartStyle createOrson2Style() {
        StandardChartStyle s = new StandardChartStyle();
        Color bgcolor = new Color(50, 50, 50, 150);
        s.setTitlePaint(Color.LIGHT_GRAY);
        s.setTitleBackgroundPaint(bgcolor);
        s.setSubtitlePaint(Color.LIGHT_GRAY);
        s.setSubtitleBackgroundPaint(bgcolor);
        s.setChartBoxColor(new Color(200, 200, 255, 50));
        s.setSectionLabelColor(Color.LIGHT_GRAY);
        s.setAxisLabelColor(Color.LIGHT_GRAY);
        s.setAxisTickLabelColor(Color.LIGHT_GRAY);
        s.setLegendHeaderColor(Color.LIGHT_GRAY);
        s.setLegendItemColor(Color.LIGHT_GRAY);
        s.setLegendHeaderBackgroundColor(bgcolor);
        s.setLegendItemBackgroundColor(bgcolor);
        s.setLegendFooterColor(Color.LIGHT_GRAY);
        s.setLegendFooterBackgroundColor(bgcolor);
        s.setStandardColors(Colors.createBlueOceanColors());
        s.setBackgroundPainter(new StandardRectanglePainter(Color.BLACK));
        return s;
    }

    /**
     * Creates and returns a new instance of the "Pastel" chart style.
     * 
     * @return A chart style (never <code>null</code>). 
     */
    public static ChartStyle createPastelStyle() {
        StandardChartStyle s = new StandardChartStyle();
        s.setStandardColors(Colors.createPastelColors());
        return s;
    }
    
    /**
     * Creates and returns a new instance of the "Logical Font" chart style.
     * This style uses the Java logical fonts.
     * 
     * @return A chart style (never <code>null</code>). 
     */
    public static ChartStyle createLogicalFontStyle() {
        StandardChartStyle s = new StandardChartStyle();
        s.setTitleFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
        s.setSubtitleFont(new Font(Font.SERIF, Font.PLAIN, 16));
        s.setSectionLabelFont(new Font(Font.SERIF, Font.PLAIN, 16));
        s.setAxisLabelFont(new Font(Font.SERIF, Font.BOLD, 16));
        s.setAxisTickLabelFont(new Font(Font.SERIF, Font.PLAIN, 14));
        s.setLegendHeaderFont(new Font(Font.SERIF, Font.BOLD, 16));
        s.setLegendItemFont(new Font(Font.SERIF, Font.PLAIN, 14));
        s.setLegendFooterFont(new Font(Font.SERIF, Font.ITALIC, 10));

        s.setStandardColors(Colors.createShadesColors());

        return s;
    }

}
