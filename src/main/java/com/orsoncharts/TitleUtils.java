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
import java.awt.Font;
import com.orsoncharts.interaction.InteractiveElementType;
import com.orsoncharts.table.GridElement;
import com.orsoncharts.table.HAlign;
import com.orsoncharts.table.TableElement;
import com.orsoncharts.table.TextElement;
import com.orsoncharts.util.Anchor2D;

/**
 * Some utility methods for creating chart titles.
 */
public class TitleUtils {
    
    /** The default title font. */
    public static final Font DEFAULT_TITLE_FONT = new Font("Dialog", Font.BOLD, 
            20);
    
    /** The default foreground color for titles. */
    public static final Color DEFAULT_TITLE_COLOR = Color.BLACK;
    
    /** The default sub-title font. */
    public static final Font DEFAULT_SUBTITLE_FONT = new Font("Dialog", 
            Font.PLAIN, 12);
    
    private TitleUtils() {
        // no need to instantiate this class
    }
    
    /**
     * Creates a chart title using the default font and alignment.
     * 
     * @param title  the chart title ({@code null} not permitted).
     * 
     * @return The chart title. 
     */
    public static TableElement createTitle(String title) {
        return createTitle(title, null);    
    }
    
    /**
     * Creates a chart title and subtitle using default fonts and left 
     * alignment.  The {@code subtitle} is optional.
     * 
     * @param title  the title text ({@code null} not permitted).
     * @param subtitle  the subtitle text ({@code null} permitted).
     * 
     * @return A composite title. 
     */
    public static TableElement createTitle(String title, String subtitle) {
        return createTitle(title, subtitle, TitleAnchor.TOP_LEFT);
    }
    
    /**
     * Creates a chart title and subtitle (optional) using default fonts and 
     * alignment that is standard for the specified anchor point (that is, left 
     * alignment when the title is anchored left, center alignment when the 
     * title is anchored centrally, and right alignment when the title is 
     * anchored to the right).
     * 
     * @param title  the title text ({@code null} not permitted).
     * @param subtitle  the subtitle text ({@code null} permitted).
     * @param anchor  the anchor point ({@code null} not permitted).
     * 
     * @return A composite title. 
     */
    public static TableElement createTitle(String title, String subtitle,
            Anchor2D anchor) {
        HAlign alignment = HAlign.LEFT;
        if (anchor.getRefPt().isHorizontalCenter()) {
            alignment = HAlign.CENTER;
        } else if (anchor.getRefPt().isRight()) {
            alignment = HAlign.RIGHT;
        }
        return createTitle(title, DEFAULT_TITLE_FONT, subtitle, 
                DEFAULT_SUBTITLE_FONT, alignment);
    }
    
    /**
     * Creates a chart title and subtitle using the specified fonts and 
     * alignment.
     * 
     * @param title  the title text ({@code null} not permitted).
     * @param titleFont  the title font ({@code null} not permitted).
     * @param subtitle  the subtitle text ({@code null} permitted).
     * @param subtitleFont  the subtitle font ({@code null} permitted).
     * @param alignment  the horizontal alignment ({@code null} not 
     *     permitted).
     * 
     * @return A chart title (never {@code null}). 
     */
    public static TableElement createTitle(String title, Font titleFont,
            String subtitle, Font subtitleFont, HAlign alignment) {
        
        TextElement t = new TextElement(title, titleFont);
        t.setHorizontalAligment(alignment);
        t.setColor(DEFAULT_TITLE_COLOR);
        t.setTag("CHART_TITLE");
        t.setProperty(TableElement.CLASS, InteractiveElementType.TITLE);
        if (subtitle == null) {
            return t;
        }
        if (subtitleFont == null) {
            throw new IllegalArgumentException(
                    "A subtitleFont is required when there is a subtitle.");
        }
        GridElement<String, String> compositeTitle 
                = new GridElement<String, String>();
        TextElement st = new TextElement(subtitle, subtitleFont);
        st.setHorizontalAligment(alignment);
        st.setColor(DEFAULT_TITLE_COLOR);
        st.setTag("CHART_SUBTITLE");
        st.setProperty(TableElement.CLASS, InteractiveElementType.SUBTITLE);
        compositeTitle.setElement(t, "R1", "C1");
        compositeTitle.setElement(st, "R2", "C1");
        return compositeTitle;
    }

}
