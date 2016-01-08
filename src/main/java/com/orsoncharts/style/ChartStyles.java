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

package com.orsoncharts.style;

import java.awt.Color;
import java.awt.Font;
import com.orsoncharts.Colors;
import com.orsoncharts.table.StandardRectanglePainter;

/**
 * Some predefined chart styles.
 * 
 * @since 1.2
 */
public class ChartStyles {
    
    /**
     * Creates and returns a new instance of the "Orson 1" chart style.
     * 
     * @return A chart style (never {@code null}). 
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
     * @return A chart style (never {@code null}). 
     */
    public static ChartStyle createOrson2Style() {
        StandardChartStyle s = new StandardChartStyle();
        Color bgcolor = new Color(50, 50, 50, 150);
        s.setTitleColor(Color.LIGHT_GRAY);
        s.setTitleBackgroundColor(bgcolor);
        s.setSubtitleColor(Color.LIGHT_GRAY);
        s.setSubtitleBackgroundColor(bgcolor);
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
        s.setMarkerLabelColor(Color.LIGHT_GRAY);
        s.setMarkerLineColor(Color.LIGHT_GRAY);
        s.setMarkerFillColor(new Color(100, 100, 255, 32));
        return s;
    }

    /**
     * Creates and returns a new instance of the "Pastel" chart style.
     * 
     * @return A chart style (never {@code null}). 
     */
    public static ChartStyle createPastelStyle() {
        StandardChartStyle s = new StandardChartStyle();
        s.setStandardColors(Colors.createPastelColors());
        return s;
    }
    
    /**
     * Creates and returns a new instance of the "Pastel" chart style.
     * 
     * @return A chart style (never {@code null}). 
     */
    public static ChartStyle createIceCubeStyle() {
        StandardChartStyle s = new StandardChartStyle();
        s.setStandardColors(Colors.createIceCubeColors());
        s.setBackgroundPainter(new StandardRectanglePainter(Color.WHITE));
        return s;
    }
    
    /**
     * Creates and returns a new instance of the "Logical Font" chart style.
     * This style uses the Java logical fonts, but is otherwise the same as
     * the "Orson 1" chart style.
     * 
     * @return A chart style (never {@code null}). 
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
        s.setMarkerLabelFont(new Font(Font.SERIF, Font.PLAIN, 10));
        s.setStandardColors(Colors.createFancyLightColors());
        return s;
    }

}
